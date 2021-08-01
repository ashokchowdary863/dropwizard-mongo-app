package com.ashok.filter;

import com.ashok.models.Constants;
import org.slf4j.MDC;

import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import java.io.IOException;
import java.util.UUID;

@Priority(1)
public class AppFilter implements ContainerRequestFilter, ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        if(containerRequestContext.getHeaders().containsKey(Constants.REQ_ID)){
            MDC.put(Constants.REQ_ID, containerRequestContext.getHeaders().getFirst(Constants.REQ_ID));
        }else{
            MDC.put(Constants.REQ_ID, UUID.randomUUID().toString());
        }
    }

    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) throws IOException {
        MDC.clear();
    }
}
