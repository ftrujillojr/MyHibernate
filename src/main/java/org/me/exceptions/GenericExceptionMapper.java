package org.me.exceptions;

import org.me.json.JsonSQLResults;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable e) {
        JsonSQLResults jsonResults = new JsonSQLResults();
        jsonResults.setErrorMessage("ThrowableMapper: " + e.getMessage());
        Response response = Response.status(Response.Status.BAD_REQUEST)
                .type(MediaType.APPLICATION_JSON)
                .entity(jsonResults.toJson())
                .build();
        return response;
    }

}
