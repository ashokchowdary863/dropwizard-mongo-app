package com.ashok.exception;

import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Arrays;
import java.util.StringJoiner;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConstraintViolationExceptionMapper.class);
    @Override
    public Response toResponse(ConstraintViolationException e) {
        LOGGER.error("Exception : {}, Stack : {}", e.getMessage(), Arrays.asList(e.getStackTrace()).toString());
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponse(AppError.BAD_REQUEST.name(), prepareMessage(e)))
                .build();
    }

    private String prepareMessage(ConstraintViolationException e) {
        StringJoiner stringJoiner = new StringJoiner(",");
        for(ConstraintViolation cv : e.getConstraintViolations()){
            stringJoiner.add(cv.getMessage());
        }
        return stringJoiner.toString();
    }


}
