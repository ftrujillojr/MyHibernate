A subset of the key files in this EXAMPLE project.

/home/me/NetBeansProjects/Examples/MyHibernate
|-- pom.xml                                                Maven: Project Dependencies.
`-- src
    |-- main
    |   |-- java
    |   |   `-- org
    |   |       `-- me
    |   |           |-- HibernateResourceAbstract.java     Hibernate: JAX-RS resource will extend this class.
    |   |           |-- HibernateUtil4.java                Hibernate: (bootstrap) SessionFactory, Session, Transaction
    |   |           |-- HibernateUtil5.java                (not used in this example, but ready if we migrate to version 5)
    |   |           |-- models
    |   |           |   |-- Department.java                Hibernate: Annotated class to database.
    |   |           |   `-- Employee.java                  Hibernate: Annotated class to database.
    |   |           |-- MyApplication.java                 JAX-RS: (bootstrap) Configuration.
    |   |           |-- rest
    |   |           |   |-- EmployeeResource.java          JAX-RS: Controller.
    |   |           |   |-- filters
    |   |           |   |   `-- CustomLoggingFilter.java   JAX-RS: Logging.
    |   |           |   `-- GenericExceptionMapper.java    JAX-RS: Catch exceptions.
    |   |           |-- ServletContext.java                Application startup/shutdown
    |   |-- resources
    |   |   |-- hibernate.cfg.xml                          Hibernate: Configuration.
    |   |   `-- logging.properties                         Application logging.
    |   `-- webapp
    |       |-- index.jsp                                  Web page
    |       |-- META-INF
    |       |   `-- context.xml                            Web Context for deployment.                      
    |       `-- WEB-INF
    |           `-- web.xml                                J2EE Web Application Configuration.
    `-- test
        `-- java
            `-- org
                `-- me
                    `-- test
                        `-- TemplateTest.java              JUnit 4 example.
