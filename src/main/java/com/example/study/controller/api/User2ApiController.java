package com.example.study.controller.api;

import com.example.study.model.network.Header;
import com.example.study.model.network.request.UserApiRequest;
import com.example.study.service.User2ApiLogicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user2")
public class User2ApiController {

    @Autowired
    private User2ApiLogicService user2ApiLogicService;

    @PostMapping
    public ResponseEntity checkEmail(@RequestBody Header<UserApiRequest> request){
        return user2ApiLogicService.checkEmail(request);
    }
}
