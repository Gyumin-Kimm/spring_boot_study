package com.example.study.repository;

import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.AdminUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AdminUserRepositoryTest extends StudyApplicationTests {

    @Autowired
    private AdminUserRepository adminUserRepository;

    @Test
    @Transactional
    public void create(){
        AdminUser adminUser = AdminUser.builder()
                .account("AdminUser01")
                .password("AdminUser01")
                .status("REGISTERED")
                .role("SUPER")
//                .createdAt(LocalDateTime.now())
//                .createdBy("AdminServer")
                .build();

        AdminUser newAdminUser = adminUserRepository.save(adminUser);
        assertNotNull(newAdminUser);
    }

    @Test
    @Transactional
    public void read(){
        Optional<AdminUser> optional = adminUserRepository.findById(1L);

        optional.ifPresent(adminUser -> {
            System.out.println(adminUser.getAccount() + ", " + adminUser.getPassword());
        });
    }

    @Test
    @Transactional
    public void update(){
        Optional<AdminUser> optional = adminUserRepository.findById(1L);

        optional.ifPresent(adminUser -> {
            adminUser.setAccount("CHANGE");
            AdminUser newAdminUser = adminUserRepository.save(adminUser);

            assertNotNull(newAdminUser);
        });
    }
}