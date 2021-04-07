package com.example.study.service;

import com.example.study.model.entity.User;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.UserApiRequest;
import com.example.study.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class User2ApiLogicService {

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity checkEmail(Header<UserApiRequest> request) {

        UserApiRequest body = request.getData();
        String user_email = body.getEmail();

//        for (User user : userRepository.findAll()) {
//            log.info("r_mail:{} u_mail:{}", user_email, user.getEmail());
//            if (user.getEmail().equals(user_email)) {
//                return new ResponseEntity(HttpStatus.CONFLICT);
//            }
//        }

        User user = userRepository.findFirstByEmail(user_email);
//        log.info("{}", user);
        if (user == null) {
            return new ResponseEntity(HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.CONFLICT);
    }
}
