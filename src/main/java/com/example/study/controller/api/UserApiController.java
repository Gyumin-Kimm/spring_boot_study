package com.example.study.controller.api;

import com.example.study.controller.CrudController;
import com.example.study.model.entity.User;
import com.example.study.model.enumclass.UserStatus;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.UserApiRequest;
import com.example.study.model.network.response.UserApiResponse;
import com.example.study.model.network.response.UserOrderInfoApiResponse;
import com.example.study.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;


//409 중복 충돌 status code
@ResponseStatus(code = HttpStatus.CONFLICT)
class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException(String message) {
        super(message);
    }
}

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserApiController extends CrudController<UserApiRequest, UserApiResponse, User> {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{id}/orderInfo")
    public Header<UserOrderInfoApiResponse> orderInfo(@PathVariable Long id){
        return baseService.orderInfo(id);
    }

    @PostMapping("/checkemail")
    public Header<UserApiResponse> checkEmailCreate(@RequestBody Header<UserApiRequest> request){

        UserApiRequest body = request.getData();

        Optional<User> optional = userRepository.findFirstByEmail(body.getEmail());

        if(optional.isPresent()){
            throw new AlreadyExistsException("duplicate email");
        }

        User user = User.builder()
                .account(body.getAccount())
                .password(body.getPassword())
                .status(UserStatus.REGISTERED)
                .phoneNumber(body.getPhoneNumber())
                .email(body.getEmail())
                .registeredAt(LocalDateTime.now())
                .build();

        User newUser = userRepository.save(user);

        UserApiResponse userApiResponse = UserApiResponse.builder()
                .id(newUser.getId())
                .account(newUser.getAccount())
                .password(newUser.getPassword())
                .email(newUser.getEmail())
                .phoneNumber(newUser.getPhoneNumber())
                .status(newUser.getStatus())
                .registeredAt(newUser.getRegisteredAt())
                .unregisteredAt(newUser.getUnregisteredAt())
                .build();

        return Header.OK(userApiResponse);
    }
}
