package com.tiennguyen.learning_spring_boot.DAO;

import com.tiennguyen.learning_spring_boot.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class FakeDataDaoTest {
    private FakeDataDao fakeDataDao;

    @BeforeEach
    void setUp() {
        fakeDataDao = new FakeDataDao();
    }

    @Test
    void shouldSelectAllUsers() {
        List<User> users = fakeDataDao.selectAllUsers();
        assertThat(users).hasSize(1);
        User user = users.get(0);
        assertThat(user.getAge()).isEqualTo(22);
        assertThat(user.getFirstName()).isEqualTo("Joe");
        assertThat(user.getLastName()).isEqualTo("Jones");
        assertThat(user.getGender()).isEqualTo(User.Gender.MALE);
        assertThat(user.getEmail()).isEqualTo("joe.jones@gmail.com");
        assertThat(user.getUserUid()).isNotNull();
    }

    @Test
    void shouldSelectUserByUid() {
        UUID annaUserID = UUID.randomUUID();
        User anna = new User(annaUserID,"anna","montana", User.Gender.FEMALE,30, "anna@gmail.com");
        fakeDataDao.insertUser(annaUserID, anna);
        assertThat(fakeDataDao.selectAllUsers()).hasSize(2);
        Optional<User> annaOptional = fakeDataDao.selectUserByUid(annaUserID);
        assertThat(annaOptional.isPresent()).isTrue();
        assertThat(annaOptional.get()).isEqualToComparingFieldByField(anna);
    }
    @Test
    void shouldNotSelectUserByRandomUserUid() {
        Optional <User> user2 = fakeDataDao.selectUserByUid(UUID.randomUUID());
        assertThat(user2.isPresent()).isFalse();

    }

    @Test
    void updateUser() {
        UUID joeUserUid = fakeDataDao.selectAllUsers().get(0).getUserUid();
        User newJoe = new User(joeUserUid,"anna","montana", User.Gender.FEMALE,30, "anna@gmail.com");
        fakeDataDao.updateUser(newJoe);
        Optional<User> user = fakeDataDao.selectUserByUid(joeUserUid);
        assertThat(user.isPresent()).isTrue();
        assertThat(fakeDataDao.selectAllUsers()).hasSize(1);
        assertThat(user.get()).isEqualToComparingFieldByField(newJoe);
    }

    @Test
    void deleteUserByUserUid() {
        UUID joeUserUid = fakeDataDao.selectAllUsers().get(0).getUserUid();
        fakeDataDao.deleteUserByUserUid(joeUserUid);
        assertThat(fakeDataDao.selectUserByUid(joeUserUid).isPresent()).isFalse();
        assertThat(fakeDataDao.selectAllUsers()).isEmpty();

    }

    @Test
    void insertUser() {
        UUID userUid = UUID.randomUUID();
        User user = new User(userUid,"anna","montana",
                User.Gender.FEMALE,30, "anna@gmail.com");
        fakeDataDao.insertUser(userUid, user);
        assertThat(fakeDataDao.selectAllUsers()).hasSize(2);
        assertThat(fakeDataDao.selectUserByUid(userUid).get()).isEqualToComparingFieldByField(user);
    }
}