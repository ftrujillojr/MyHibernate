package org.me;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


public class ServletContext implements ServletContextListener {

    public ServletContext() {
    }

    @Override
    public void contextInitialized(ServletContextEvent contextEvent) {
        System.out.println("\n*** Context Created\n");
    }

    @Override
    public void contextDestroyed(ServletContextEvent contextEvent) {
        System.out.println("\n*** Context Destroyed\n");
        HibernateUtil4.shutdown();
    }

}
