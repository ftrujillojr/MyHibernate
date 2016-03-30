package org.me;

import javax.ws.rs.WebApplicationException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.metamodel.Metadata;
import org.hibernate.metamodel.MetadataSources;

/**
 * This is NOT tested yet.  Saving for Hibernate 5.x migration later.
 * 
 * http://stackoverflow.com/questions/33005348/hibernate-5-org-hibernate-mappingexception-unknown-entity
 * 
 * @author ftrujillo
 */
public class HibernateUtil5 {

    public HibernateUtil5() {
    }

    private static SessionFactory sessionFactory;
    private static boolean debug = false;

    public static void setDebug(boolean debug) {
        HibernateUtil5.debug = debug;
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
                        .configure("hibernate.cfg.xml")
                        .build();

                Metadata metadata = new MetadataSources(standardRegistry)
                        .getMetadataBuilder()
                        .build();

                sessionFactory = metadata.getSessionFactoryBuilder().build();

            } catch (Exception ex) {
                String msg = "\n*** Hey, Exception!!\n";
                msg += ex.getMessage();
                System.out.println(msg);
                throw new WebApplicationException(msg);
            }
        }

        if (sessionFactory != null && debug) {
            System.out.println("\n*** Hibernate: sessionFactory returned.\n");
        }
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
        if (debug) {
            System.out.println("*** Hibernate shutdown!");
        }
    }

}
