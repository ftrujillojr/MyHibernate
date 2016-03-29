package org.me;

import javax.ws.rs.WebApplicationException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * This bootstrap works for Hibernate 4.3.11.Final
 * 
 * @author ftrujillo
 */
public class HibernateUtil {

    private static SessionFactory sessionFactory;
    private static boolean debug = false;

    public HibernateUtil() {
    }

    public static void setDebug(boolean debug) {
        HibernateUtil.debug = debug;
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                // loads configuration and mappings
                Configuration configuration = new Configuration().configure("hibernate.cfg.xml");

                if (debug) {
                    System.out.println("*** Hibernate Configuration loaded");
                }

                ServiceRegistry serviceRegistry
                        = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties())
                        .build();

                if (debug) {
                    System.out.println("*** Hibernate serviceRegistry created");
                }

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);

                if (debug) {
                    System.out.println("*** Hibernate sessionFactory created");
                }

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
