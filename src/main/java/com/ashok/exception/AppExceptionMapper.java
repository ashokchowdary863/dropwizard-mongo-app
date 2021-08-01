package com.ashok.exception;

import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Arrays;

@Provider
public class AppExceptionMapper implements ExceptionMapper<Exception> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppExceptionMapper.class);
    @Override
    public Response toResponse(Exception e) {
        LOGGER.error("Exception : {}, Stack : {}", e.getMessage(), Arrays.asList(e.getStackTrace()).toString());
        if(e instanceof AppException){
            AppException myAppException = (AppException) e;
            return Response.status(myAppException.getError().getCode())
                    .entity(new ErrorResponse(((AppException) e).getError().name(), e.getMessage())).build();
        }else{
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500)
                    .entity(new ErrorResponse(AppError.SYSTEM_ERROR.name(), AppError.SYSTEM_ERROR.getMessage()))
                    .build();
        }
    }
}
