package org.me;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.TracingConfig;
import org.glassfish.jersey.server.filter.HttpMethodOverrideFilter;
import org.glassfish.jersey.server.wadl.WadlFeature;

@ApplicationPath("/api")
public class MyApplication extends ResourceConfig {

    private static final String LOGGING_PROPERTIES_FILE = "logging.properties";
    private static final Logger LOGGER = Logger.getLogger(MyApplication.class.getName());

    public MyApplication() {

        this.initLoggingProperties();

        // This is the package where our REST api resources (controllers) reside.
        // All classes in this package will be scanned for annotations.
        packages("org.me.rest");

        // WADL output via GET    http://localhost:8085/MyHibernate/api/application.wadl
        register(WadlFeature.class);
        property(ServerProperties.WADL_FEATURE_DISABLE, false);  // set to TRUE to disable

        register(MultiPartFeature.class);

        // MVC                    https://jersey.java.net/documentation/2.22.1/mvc.html
        //register(MvcFeature.class);

        // TEMPLATE
        //register(JspMvcFeature.class);
        //property(JspMvcFeature.TEMPLATE_BASE_PATH, "/templates");
        // https://dzone.com/articles/mustaches-world-java
        // http://mustache.github.io/mustache.5.html
        //register(MustacheMvcFeature.class);
        //property(MustacheMvcFeature.TEMPLATE_BASE_PATH, "/templates");

        //register(FreemarkerMvcFeature.class);
        //property(FreemarkerMvcFeature.TEMPLATE_BASE_PATH, "/templates");
        
        // I am using @Provider to annotate this class as my Custom filter.
        //register(CustomLoggingFilter.class);
        
        // https://jersey.java.net/apidocs/2.22.1/jersey/org/glassfish/jersey/server/filter/HttpMethodOverrideFilter.html
        HttpMethodOverrideFilter overrideFilter = new HttpMethodOverrideFilter();
        register(overrideFilter);

        // Tracing support.  https://blogs.oracle.com/sandoz/entry/tracing_in_jersey
        property(ServerProperties.TRACING, TracingConfig.ON_DEMAND.name());
    }

    private void initLoggingProperties() {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(LOGGING_PROPERTIES_FILE).getFile());

        if (file.exists()) {
            try {
                System.out.println("\n\nSTARTING\n\n");
                System.setProperty("java.util.logging.config.file", file.getAbsolutePath());

                String prop1 = System.getProperty("java.util.logging.config.file");
                if (prop1 != null) {
                    String msg = "****** " + prop1 + " ********";
                    LOGGER.log(Level.INFO, msg);
                }

                LogManager logManager = LogManager.getLogManager();
                logManager.readConfiguration();
            } catch (IOException ex) {
                String msg = "IOException on file " + file.getAbsolutePath();
                LOGGER.log(Level.SEVERE, msg);
            }
        }
    }

}
