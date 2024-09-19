package com.fredo.LMS.software.Controller;

import com.fredo.LMS.software.dto.LoginRequest;
import com.fredo.LMS.software.dto.Response;
import com.fredo.LMS.software.model.User;
import com.fredo.LMS.software.service.interfac.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private IUserService iUserService;

    @PostMapping("/registerUser")
    public Response register(@RequestBody User newuser){
        return iUserService.registerUser(newuser);
    }

    @PostMapping("/logInUser")
    public Response logIn(@RequestBody LoginRequest loginRequest){
        return iUserService.logInUser(loginRequest);
    }
}
