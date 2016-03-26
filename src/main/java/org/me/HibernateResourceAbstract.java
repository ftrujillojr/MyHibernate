package org.me;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public abstract class HibernateResourceAbstract {

    protected boolean debug;
    protected Session session = null;
    protected Transaction tx = null;

    public HibernateResourceAbstract() {
        this.debug = false;
    }

    protected void setDebug(boolean debug) {
        this.debug = debug;
    }

    protected void beginTransaction(int timeout) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        session = sessionFactory.getCurrentSession();
        if (session != null && session.isOpen()) {
            tx = session.getTransaction();
            tx.setTimeout(timeout);
            tx.begin();
        }
    }

    protected void commitTransaction() {
        if (session != null && session.isOpen()) {
            session.flush();
            session.clear();
            tx.commit();
            if (debug) {
                System.out.println("\n*** COMMIT !!!\n");
            }
        }
    }

    protected void rollbackTransaction(Exception ex) {
        // If the Session throws an exception, the transaction must be 
        // rolled back and the session discarded. The internal state of the 
        // Session might not be consistent with the database after the 
        // exception occurs.
        if (tx != null) {
            tx.rollback();
            if (debug) {
                System.out.println("\nROLLBACK!!\n");
            }
        }
        if (debug) {
            System.out.println("\n*** EXCEPTION \n" + ex.getMessage());
        }
    }

    protected void closeSession() {
        if (session != null && session.isOpen()) {
            session.flush();
            session.clear();
            tx.commit();
            if (debug) {
                System.out.println("\n*** COMMIT !!!\n");
            }
            session.close();
            if (debug) {
                System.out.println("\n*** CLOSE session\n");
            }
            session = null;
            tx = null;
        }
    }

}
