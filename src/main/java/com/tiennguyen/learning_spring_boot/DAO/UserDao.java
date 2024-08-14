package com.tiennguyen.learning_spring_boot.DAO;

import com.tiennguyen.learning_spring_boot.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserDao {
    List<User> selectAllUsers();
    Optional<User> selectUserByUid(UUID userUid); // optional whether there is that user or not, if not, return null
    int updateUser(User user);
    int deleteUserByUserUid(UUID userUid);
    int insertUser(UUID userUid, User user);
}
