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
        if(debug) {
            HibernateUtil.setDebug(true);
        } else {
            HibernateUtil.setDebug(false);
        }
        
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        
        // http://docs.jboss.org/hibernate/orm/4.3/devguide/en-US/html_single/#session-per-request

        session = sessionFactory.getCurrentSession();
        
        if (session != null && session.isOpen()) {
            tx = session.getTransaction();
            tx.setTimeout(timeout);
            tx.begin();
        }
    }

    protected void commitTransaction() {
        System.out.println("\n SESSION " + session.toString() + " \n");
        if (session != null && session.isOpen()) {
            session.flush();
            session.clear();
        }
        if (tx != null && tx.isActive()) {
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
        if (tx != null && tx.isActive()) {
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
        if (debug) {
            System.out.println("\n*** Calling closeSession()\n");
            System.out.println("\n SESSION " + session.toString() + " \n");
        }
        
//        The Hibernate session is bound to the current "thread".   
//        Therefore, when the transaction is committed, the session is closed also.  
//                
//        The next block will not execute if you used sessionFactory.getCurrentSession();
        
        if (session != null && session.isOpen()) {
            session.close();
            if (debug) {
                System.out.println("\n*** CLOSE session\n");
            }
        }
    }

}
