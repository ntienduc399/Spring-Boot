package com.tiennguyen.learning_spring_boot.service;

import com.google.common.collect.ImmutableList;
import com.tiennguyen.learning_spring_boot.DAO.FakeDataDao;
import com.tiennguyen.learning_spring_boot.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
//import com.google.common.collect.ImmutableList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;


import static org.assertj.core.api.Assertions.assertThat;
//import static org.awaitility.Awaitility.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

class UserServiceTest {
    @Mock
    private FakeDataDao fakeDataDao;
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(fakeDataDao);
    }

    @Test
    public void ShouldGetAllUser() {
        UUID annaUserID = UUID.randomUUID();
        User anna = new User(annaUserID,"anna","montana", User.Gender.FEMALE,
                30, "anna@gmail.com");
        ImmutableList<User> users = new ImmutableList.Builder<User>().add(anna).build();
        given(fakeDataDao.selectAllUsers()).willReturn(users);
        List<User> allUsers = userService.getAllUser();
        assertThat(allUsers).hasSize(1);
        User user = allUsers.get(0);
        assertUser(user);
    }

    @Test
   public void ShouldGetUser() {
        UUID annaUid = UUID.randomUUID();
        User anna = new User(annaUid,"anna","montana", User.Gender.FEMALE,
                30, "anna@gmail.com");
        given(fakeDataDao.selectUserByUid(annaUid)).willReturn(Optional.of(anna));
       Optional<User> userOptional = userService.getUser(annaUid);
       assertThat(userOptional.isPresent()).isTrue();
       User user = userOptional.get();
        assertUser(user);
    }

    private static void assertUser(User user) {
        assertThat(user.getAge()).isEqualTo(30);
        assertThat(user.getFirstName()).isEqualTo("anna");
        assertThat(user.getLastName()).isEqualTo("montana");
        assertThat(user.getGender()).isEqualTo(User.Gender.FEMALE);
        assertThat(user.getEmail()).isEqualTo("anna@gmail.com");
        assertThat(user.getUserUid()).isNotNull();
        assertThat(user.getUserUid()).isInstanceOf(UUID.class);
    }

    @Test
   public void updateUser() {
        UUID annaUid = UUID.randomUUID();
        User anna = new User(annaUid,"anna","montana", User.Gender.FEMALE,
                30, "anna@gmail.com");
        given(fakeDataDao.selectUserByUid(annaUid)).willReturn(Optional.of(anna));
        given(fakeDataDao.updateUser(anna)).willReturn(1);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        int updateResult = userService.updateUser(anna);

        verify(fakeDataDao).selectUserByUid(annaUid);
        verify(fakeDataDao).updateUser(captor.capture());

        User user = captor.getValue();

        assertUser(user);
        assertThat(updateResult).isEqualTo(1);
    }

    @Test
   public void ShouldRemoveUser() {
        UUID annaUid = UUID.randomUUID();
        User anna = new User(annaUid,"anna","montana", User.Gender.FEMALE,
                30, "anna@gmail.com");
        given(fakeDataDao.selectUserByUid(annaUid)).willReturn(Optional.of(anna));
        given(fakeDataDao.deleteUserByUserUid(annaUid)).willReturn(1);

        int deleteResult = userService.removeUser(annaUid);

        verify(fakeDataDao).selectUserByUid(annaUid);
        verify(fakeDataDao).deleteUserByUserUid(annaUid);

        assertThat(deleteResult).isEqualTo(1);
    }

    @Test
   public void ShouldInsertUser() {
        User anna = new User(null,"anna","montana", User.Gender.FEMALE,
                30, "anna@gmail.com");
        given(fakeDataDao.insertUser(any(UUID.class),eq(anna))).willReturn(1);
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

        int insertResult = userService.insertUser(anna);
        verify(fakeDataDao).insertUser(any(UUID.class), captor.capture());
        User user = captor.getValue();

        assertUser(user);
        assertThat(insertResult).isEqualTo(1);
    }
}