package com.example.study.repository;


import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.User;
import com.example.study.model.enumclass.UserStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class UserRepositoryTest extends StudyApplicationTests {

    // Dependency Injection(DI) - 의존성 주입 : spring 객체 직접관리
    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    public void create(){
        String account = "Test03";
        String password = "Test03";
        UserStatus status = UserStatus.REGISTERED;
        String email = "Test01@gmail.com";
        String phoneNumber = "010-1111-3333";
        LocalDateTime now = LocalDateTime.now();
        String createdBy = "AdminServer";

        User user = User.builder()
                .account(account)
                .password(password)
                .status(status)
                .email(email)
                .phoneNumber(phoneNumber)
                .registeredAt(now)
                .createdBy(createdBy)
                .build();

        User newUser = userRepository.save(user);

        assertNotNull(newUser);
    }

    @Test
    @Transactional
    public void read(){
        Optional<User> user = userRepository.findFirstByPhoneNumberOrderByIdDesc("010-1111-0016");

        user.ifPresent(selectUser ->{
            selectUser.getOrderGroupList().forEach(orderGroup -> {
                System.out.println("-----OrderGroup-----");
//                System.out.println(orderGroup.getRevName());
//                System.out.println(orderGroup.getRevAddress());
                System.out.println(orderGroup.getTotalPrice());
                System.out.println(orderGroup.getTotalQuantity());

                orderGroup.getOrderDetailList().forEach(orderDetail -> {
                    System.out.println("-----OrderDetail-----");
//                    System.out.println(orderDetail.getStatus());
//                    System.out.println(orderDetail.getArrivalDate());
                    System.out.println(orderDetail.getTotalPrice());

                    System.out.println(orderDetail.getItem().getName());
//                    System.out.println(orderDetail.getItem().getPartner().getCallCenter());

//                    System.out.println(orderDetail.getItem().getPartner().getCategory().getTitle());
                });
            });
        });

    }

    @Test
    @Transactional
    public void update(){
        Optional<User> user = userRepository.findById(3L);
        user.ifPresent(selectUser ->{
           selectUser.setAccount("PP");
           selectUser.setUpdatedAt(LocalDateTime.now());
           selectUser.setUpdatedBy("update method()");

           User updateUser = userRepository.save(selectUser);

           assertNotNull(updateUser);
        });
    }

    @Test
    @Transactional
    public void delete(){
        Optional<User> user = userRepository.findById(3L);
        assertTrue(user.isPresent()); // true

        user.ifPresent(selectUser-> userRepository.delete(selectUser));

        Optional<User> deleteUser = userRepository.findById(3L);
        assertFalse(deleteUser.isPresent()); // false
    }
}
