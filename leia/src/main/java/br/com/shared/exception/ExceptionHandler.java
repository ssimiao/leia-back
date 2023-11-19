package br.com.shared.exception;

import io.quarkus.logging.Log;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.Map;

@Provider
public class ExceptionHandler implements ExceptionMapper<RuntimeException> {

    @Override
    public Response toResponse(RuntimeException e) {
        Log.error("Erro na requisição", e);
        return getStatus(e)
                .entity(Map.of("message", e.getMessage())).build();
    }

    private static Response.ResponseBuilder getStatus(RuntimeException e) {
        if (e instanceof ResourceNotFoundException)
            return Response.status(422);

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR);
    }
}