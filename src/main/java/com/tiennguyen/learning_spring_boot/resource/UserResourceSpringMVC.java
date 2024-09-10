package com.tiennguyen.learning_spring_boot.resource;

import com.tiennguyen.learning_spring_boot.model.User;
import com.tiennguyen.learning_spring_boot.service.UserService;
import jakarta.ws.rs.QueryParam;
import org.springframework.http.MediaType;
//import jakarta.ws.rs.core.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

//@RestController
//@RequestMapping(
//        path = "/api/v1/users"
//)

public class UserResourceSpringMVC {
    private UserService userService;
    @Autowired
    public UserResourceSpringMVC(UserService userService){
        this.userService = userService;
    }
    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<User> fetchUsers(@QueryParam("gender") String gender){
        return userService.getAllUser(Optional.ofNullable(gender)); //in case gender parameter can be null
    }
    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE,
            path = "{userUid}"
    )
    public ResponseEntity<?> fetchUser(@PathVariable("userUid")UUID userUid){
        Optional<User> optionalUser = userService.getUser(userUid);
        if(optionalUser.isPresent()){
            return ResponseEntity.ok(optionalUser.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body( new ErrorMessage("user " + userUid + " was not found."));

    }
    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, //only accept json type request
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Integer> insertNewUser(@RequestBody User user){
        int result = userService.insertUser(user);
        if(result==1){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();

    }
    @RequestMapping(
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE, //only accept json type request
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Integer> updateUser(@RequestBody User user){
        int result = userService.updateUser(user);
        if (result == 1) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
    @RequestMapping(
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            path = "{userUid}"
    )
    public ResponseEntity<Integer> deleteUser(@PathVariable("userUid") UUID userUid){
        int result = userService.removeUser(userUid);
        if (result == 1) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
