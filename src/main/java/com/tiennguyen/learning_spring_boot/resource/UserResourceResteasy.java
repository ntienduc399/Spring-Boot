package com.tiennguyen.learning_spring_boot.resource;

import com.tiennguyen.learning_spring_boot.model.User;
import com.tiennguyen.learning_spring_boot.service.UserService;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

@Validated
@Component
@Path("/api/v1/users")
public class UserResourceResteasy {
    private UserService userService;
    @Autowired
    public UserResourceResteasy(UserService userService){
        this.userService = userService;
    }

    @GET
    @Produces(APPLICATION_JSON)
    public List<User> fetchUsers(@QueryParam("gender") String gender){
        return userService.getAllUser(Optional.ofNullable(gender)); //in case gender parameter can be null
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path ("{userUid}")
    public User fetchUser(@PathParam("userUid") UUID userUid){
        return userService.getUser(userUid)
                .orElseThrow(()-> new NotFoundException("user " + userUid + " not found"));


    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON) //only accept json type request
    @Produces(MediaType.APPLICATION_JSON)
    public void insertNewUser(@Valid User user){
         userService.insertUser(user);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON) //only accept json type request
    @Produces(MediaType.APPLICATION_JSON)
    public void updateUser( User user){
         userService.updateUser(user);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{userUid}")
    public void deleteUser(@PathParam("userUid") UUID userUid){
        userService.removeUser(userUid);
    }
    private Response getIntegerResponseEntity(int result){
        if(result==1){
            return Response.ok().build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}
