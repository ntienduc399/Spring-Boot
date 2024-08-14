package com.tiennguyen.learning_spring_boot.service;

import com.tiennguyen.learning_spring_boot.DAO.FakeDataDao;
import com.tiennguyen.learning_spring_boot.DAO.UserDao;
import com.tiennguyen.learning_spring_boot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private UserDao userDao;
    @Autowired // will automatically map userDao with new FakeDataDao
    public UserService(UserDao userDao){
        this.userDao = userDao ;
    }

    public List<User> getAllUser(){

        return userDao.selectAllUsers();
    }

    public Optional<User> getUser(UUID userUid) {
        return userDao.selectUserByUid(userUid);
    }


    public int updateUser(User user) {
        Optional<User> optionalUser = getUser(user.getUserUid());
        if(optionalUser.isPresent()){
            userDao.updateUser(user);
            return 1;
        }
        return -1;
    }

    public int removeUser(UUID userUid) {
        Optional<User> optionalUser = getUser(userUid);
        if(optionalUser .isPresent()){
            userDao.deleteUserByUserUid(userUid);
            return 1;
        }
        return -1;
    }


    public int insertUser( User user) {
        return userDao.insertUser(UUID.randomUUID(),user);
    }
}
