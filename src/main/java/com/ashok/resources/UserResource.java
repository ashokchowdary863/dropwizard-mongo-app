package com.ashok.resources;

import com.ashok.dto.UserDto;
import com.ashok.exception.AppException;
import com.ashok.models.AppResponse;
import com.ashok.service.UserService;
import com.codahale.metrics.annotation.ResponseMetered;
import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/users")
@Api("Users")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserResource.class);
    @Inject
    private UserService userService;

    @POST
    @Timed(name = "addUser_api")
    @ResponseMetered(name = "addUser_statusCodes")
    public Response createUser(@ApiParam @Valid @NotNull(message = "Data can't be null") UserDto user) throws AppException {
        LOGGER.debug("Create user input request : {}", user);
        AppResponse<UserDto> response = userService.createUser(user);
        return Response.status(response.getStatus()).entity(response.getResponse()).build();
    }

    @PUT
    @Timed(name = "updateUser_api")
    @ResponseMetered(name = "updateUser_statusCodes")
    public Response updateUser(@ApiParam @Valid @NotNull(message = "Data can't be null") UserDto user) throws AppException {
        AppResponse<UserDto> response = userService.updateUser(user);
        return Response.status(response.getStatus()).entity(response.getResponse()).build();
    }

    @GET
    @Timed(name = "getUsers_api")
    @ResponseMetered(name = "getUsers_statusCodes")
    public Response getUsers() {
        AppResponse<List<UserDto>> response = userService.getUsers();
        return Response.status(response.getStatus()).entity(response.getResponse()).build();
    }

    @GET
    @Timed(name = "getUser_api")
    @Path("/user/{userId}")
    @ResponseMetered(name = "getUser_statusCodes")
    public Response getUser(@NotBlank(message = "userId cannot be empty")  @PathParam("userId") String userId) throws AppException {
        AppResponse<UserDto> response = userService.getUser(userId);
        return Response.status(response.getStatus()).entity(response.getResponse()).build();
    }

    @DELETE
    @Timed(name = "deleteUser_api")
    @Path("/user/{userId}")
    @ResponseMetered(name = "deleteUser_statusCodes")
    public Response deleteUsers(@NotBlank @PathParam("userId") String userId) throws AppException {
        userService.deleteUser(userId);
        return Response.status(Response.Status.OK)
                .build();
    }

}
