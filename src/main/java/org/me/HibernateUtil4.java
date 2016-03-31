package org.me;

import java.io.File;
import javax.ws.rs.WebApplicationException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * This bootstrap works for Hibernate 4.3.11.Final
 *
 * http://www.codejava.net/frameworks/hibernate/building-hibernate-sessionfactory-from-service-registry
 *
 * @author ftrujillo
 */
public class HibernateUtil4 {

    private static SessionFactory sessionFactory;
    private static boolean debug = false;
    private static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    public HibernateUtil4() {
    }

    public static void setDebug(boolean debug) {
        HibernateUtil4.debug = debug;
    }


    public static SessionFactory getSessionFactory() {
        ClassLoader classLoader = HibernateUtil4.class.getClassLoader();
        File hibernateConfigFile = new File(classLoader.getResource(HIBERNATE_CFG_FILE).getFile());

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
