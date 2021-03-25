package com.example.study.controller.api;

import com.example.study.ifs.Crudinterface;
import com.example.study.model.network.Header;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserApiController implements Crudinterface {

    @Override
    @PostMapping("")        // /api/user
    public Header create() {
        return null;
    }

    @Override
    @GetMapping("{id}")     // /api/user/{id}
    public Header read(@PathVariable(name = "id") Long id) {
        return null;
    }

    @Override
    @PutMapping("")         // /api/user
    public Header update() {
        return null;
    }

    @Override
    @DeleteMapping("{id}")  // /api/user/{id}
    public Header delete(@PathVariable Long id) {
        return null;
    }
}
