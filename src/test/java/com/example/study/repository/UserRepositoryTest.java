package com.example.study.repository;


import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Optional;

public class UserRepositoryTest extends StudyApplicationTests {

    // Dependency Injection(DI)
    @Autowired
    private UserRepository userRepository;

    @Test
    public void create(){
        User user = new User();
//        user.setId(); GeneratedValue
        user.setAccount("TestUser03");
        user.setEmail("TestUser03@gmail.com");
        user.setPhoneNumber("010-1111-1111");
        user.setCreatedAt(LocalDateTime.now());
        user.setCreatedBy("TestUser3");

        User newUser = userRepository.save(user);
        System.out.println("newUser : " + newUser);
    }

    @Test
    public void read(){
        Optional<User> user = userRepository.findById(2L);
        user.ifPresent(selectUser ->{
            System.out.println("user :" + selectUser);
            System.out.println("email : " + selectUser.getEmail());
        });
    }

    @Test
    @Transactional
    public void update(){
        Optional<User> user = userRepository.findById(3L);
        user.ifPresent(selectUser ->{
           selectUser.setAccount("PPPP");
           selectUser.setUpdatedAt(LocalDateTime.now());
           selectUser.setUpdatedBy("update method()");

           userRepository.save(selectUser);
        });
    }

    @Test
    @Transactional
    public void delete(){
        Optional<User> user = userRepository.findById(3L);

        assertTrue(user.isPresent()); // true

        user.ifPresent(selectUser->{
            userRepository.delete(selectUser);
        });

        Optional<User> deleteUser = userRepository.findById(2L);

        assertFalse(deleteUser.isPresent()); // false
    }
}
