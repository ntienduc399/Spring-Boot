package com.tiennguyen.learning_spring_boot.clientproxy;

import com.tiennguyen.learning_spring_boot.model.User;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.UUID;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public interface UserResourceV1 {
    @GET
    @Produces(APPLICATION_JSON)
    List<User> fetchUsers(@QueryParam("gender") String gender);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{userUid}")
    User fetchUser(@PathParam("userUid") UUID userUid);

    @POST
    @Consumes(MediaType.APPLICATION_JSON) //only accept json type request
    @Produces(MediaType.APPLICATION_JSON)
    void insertNewUser( User user);

    @PUT
    @Consumes(MediaType.APPLICATION_JSON) //only accept json type request
    @Produces(MediaType.APPLICATION_JSON)
    void updateUser( User user);

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{userUid}")
    void deleteUser(@PathParam("userUid") UUID userUid);
}
