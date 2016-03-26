package org.me;

import javax.ws.rs.WebApplicationException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    public HibernateUtil() {
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                // loads configuration and mappings
                Configuration configuration = new Configuration()
                        .addAnnotatedClass(org.me.models.Employee.class)
                        .addAnnotatedClass(org.me.models.Department.class)
                        .configure("hibernate.cfg.xml");

                System.out.println("*** Hibernate Configuration loaded");

                ServiceRegistry serviceRegistry
                        = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties())
                        .build();

                System.out.println("*** Hibernate serviceRegistry created");

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);

                System.out.println("*** Hibernate sessionFactory created");

            } catch (Exception ex) {
                String msg = "\n*** Hey, Exception!!\n";
                msg += ex.getMessage();
                System.out.println(msg);
                throw new WebApplicationException(msg);
            }
        }

        if(sessionFactory != null) {
            System.out.println("\n*** Hibernate: sessionFactory returned.\n");
        }
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
        System.out.println("*** Hibernate shutdown!");
    }
}
