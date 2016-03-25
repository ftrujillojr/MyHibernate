package org.me.orm;

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
            // loads configuration and mappings
            Configuration configuration = new Configuration()
                    .addAnnotatedClass(org.me.orm.Employee.class)
                    .addAnnotatedClass(org.me.orm.Department.class)
                    .configure("hibernate.cfg.xml");

            System.out.println("Hibernate Configuration loaded");

            ServiceRegistry serviceRegistry
                    = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .build();

            System.out.println("Hibernate serviceRegistry created");
            
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);

            System.out.println("Hibernate sessionFactory created");
        }

        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }

}
