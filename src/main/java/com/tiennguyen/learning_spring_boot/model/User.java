package com.tiennguyen.learning_spring_boot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)

public class User {
    //user id
    private final UUID userUid;
    @NotNull
    private final String firstName;
    @NotNull
    private final String lastName;
    @NotNull
    private final Gender gender;
    @NotNull
    @Max(value = 112)
    @Min(value = 0)
    private final Integer age;
    @NotNull
    @Email
    private final String email;
    public User(@JsonProperty("userUid") UUID userUid,
                @JsonProperty("firstName")String firstName,
                @JsonProperty("lastName") String lastName,
                @JsonProperty("gender") Gender gender,
                @JsonProperty("age") Integer age,
                @JsonProperty("email") String email){
        this.userUid = userUid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.age = age;
        this.email = email;
    }
   // public User(){}
   // @JsonProperty("id")
    public UUID getUserUid(){return userUid;}

   /* public void setUserUid(UUID userUid){
        this.userUid = userUid;
    }*/

    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public Integer getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }
    public String getFullName(){
        return firstName +" "+ lastName;
    }
    public static User newUser(UUID userUid, User user){
        return new User(userUid, user.getFirstName(), user.getLastName(), user.getGender(),user.getAge(),  user.getEmail());
    }
    public int getDateOfBirth(){
        return LocalDate.now().minusYears(age).getYear();
    }

    @Override
    public String toString() {
        return "User{" +
                "userUid=" + userUid +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender=" + gender +
                ", age=" + age +
                ", email='" + email + '\'' +
                '}';
    }

    public enum Gender{
        MALE,
        FEMALE
    }
}
