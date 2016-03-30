package org.me.rest;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

    public GenericExceptionMapper() {
    }

    @Override
    public Response toResponse(Throwable e) {
        Response response = Response.status(Response.Status.BAD_REQUEST)
                .type(MediaType.APPLICATION_JSON)
                .entity("{ 'hello': 'world' }")
                .build();
        return response;
    }

}
