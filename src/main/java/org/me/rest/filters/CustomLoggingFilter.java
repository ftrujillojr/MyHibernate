package org.me.rest.filters;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import org.glassfish.jersey.message.internal.ReaderWriter;


// http://howtodoinjava.com/jersey/jersey-custom-logging-request-and-response-entities-using-filter/
@Provider
public class CustomLoggingFilter implements ContainerRequestFilter, ContainerResponseFilter {
    
    private static final Logger LOGGER = Logger.getLogger(CustomLoggingFilter.class.getName());
    
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        
        StringBuilder sb = new StringBuilder();
        Principal principal = requestContext.getSecurityContext().getUserPrincipal();
        String path = requestContext.getUriInfo().getPath();
        String method = requestContext.getMethod().toUpperCase();
        MultivaluedMap<String, String> headers = requestContext.getHeaders();
        
        sb.append("HTTP REQUEST: ");
        sb.append(" User: ").append(principal == null ? "unknown" : principal);
        sb.append(" | Path: ").append(path);
        sb.append(" | Method: ").append(method);
        sb.append(" | Header: ").append(headers);
        sb.append(" | Entity: ").append(getEntityBody(requestContext));
        LOGGER.log(Level.INFO, sb.toString());
    }
    
    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        // Add stuff to headers
        responseContext.getHeaders().add("X-Developers", "Wile E Coyote, BugsBunny");

        // Build the response for the logger.
        StringBuilder sb = new StringBuilder();
        
        MultivaluedMap<String, String> headers = responseContext.getStringHeaders();
        Integer status = responseContext.getStatus();
        
        sb.append("HTTP RESPONSE: STATUS ").append(status).append(" ");
        sb.append(" | Header: ").append(headers);
        
        LOGGER.log(Level.INFO, sb.toString());
    }
    
    private String getEntityBody(ContainerRequestContext requestContext) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        InputStream in = requestContext.getEntityStream();
        
        final StringBuilder b = new StringBuilder();
        try {
            ReaderWriter.writeTo(in, out);
            
            byte[] requestEntity = out.toByteArray();
            if (requestEntity.length == 0) {
                b.append("");
            } else {
                b.append(new String(requestEntity)).append("\n");
            }
            requestContext.setEntityStream(new ByteArrayInputStream(requestEntity));
            
        } catch (IOException ex) {
            //Handle logging error
        }
        return b.toString();
    }
    
}

