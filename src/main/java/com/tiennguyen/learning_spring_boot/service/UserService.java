package com.tiennguyen.learning_spring_boot.service;

import com.google.common.annotations.VisibleForTesting;
import com.tiennguyen.learning_spring_boot.DAO.FakeDataDao;
import com.tiennguyen.learning_spring_boot.DAO.UserDao;
import com.tiennguyen.learning_spring_boot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
        return -1;
    }

    public int removeUser(UUID userUid) {
        Optional<User> optionalUser = getUser(userUid);
        if(optionalUser .isPresent()){
            return userDao.deleteUserByUserUid(userUid);
        }
        return -1;
    }


    public int insertUser( User user) {
        UUID userUid = UUID.randomUUID();
        user.setUserUid(userUid);
        return userDao.insertUser(userUid,user);
    }
}
