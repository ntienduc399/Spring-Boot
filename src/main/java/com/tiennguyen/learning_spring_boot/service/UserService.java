package com.tiennguyen.learning_spring_boot.service;

import com.tiennguyen.learning_spring_boot.DAO.UserDao;
import com.tiennguyen.learning_spring_boot.model.User;
import jakarta.ws.rs.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserDao userDao;
    @Autowired // will automatically map userDao with new FakeDataDao
    public UserService(UserDao userDao){
        this.userDao = userDao ;
    }

    public List<User> getAllUser(Optional<String> gender){
        List<User> users = userDao.selectAllUsers();
        if(!gender.isPresent()){
            return users;
        }
        try{
            User.Gender theGender = User.Gender.valueOf(gender.get().toUpperCase());
            return users.stream()
                    .filter(user -> user.getGender().equals(theGender))
                    .collect(Collectors.toList());
        }catch (Exception e){
            throw new IllegalStateException("Invalid gender", e);
        }

    }

    public Optional<User> getUser(UUID userUid) {
        return userDao.selectUserByUid(userUid);
    }


    public int updateUser(User user) {
        Optional<User> optionalUser = getUser(user.getUserUid());
        if(optionalUser.isPresent()){
            return userDao.updateUser(user);
        }
        throw new NotFoundException("user " + user.getUserUid() + " not found");
    }

    public int removeUser(UUID uid) {
        UUID userUid = getUser(uid)
                .map(User::getUserUid)
                .orElseThrow(()-> new NotFoundException("user " + uid + " not found"));
            return userDao.deleteUserByUserUid(userUid);
    }


    public int insertUser( User user) {
        UUID userUid = user.getUserUid()== null? UUID.randomUUID(): user.getUserUid();
        //validateUser(user);
        return userDao.insertUser(userUid,User.newUser(userUid, user));
    }

    private static void validateUser(User user) {
        Objects.requireNonNull(user.getFirstName(), "first name required");
        Objects.requireNonNull(user.getLastName(), "last name required");
        Objects.requireNonNull(user.getAge(), "age required");
        Objects.requireNonNull(user.getEmail(), "email required");
        Objects.requireNonNull(user.getGender(), "gender required");
    }
}
