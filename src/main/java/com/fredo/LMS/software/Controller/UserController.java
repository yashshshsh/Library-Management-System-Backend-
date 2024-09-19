package com.fredo.LMS.software.Controller;

import com.fredo.LMS.software.dto.Response;
import com.fredo.LMS.software.model.User;
import com.fredo.LMS.software.service.interfac.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserService iUserService;

    @GetMapping("/getUser")
    public Response getUser2(){
        return iUserService.getUser2();
    }

    @GetMapping("/getUser/{userId}")
    public Response getUser(@PathVariable Long userId){
        return iUserService.getUserById(userId);
    }

    @PutMapping("/updateUser/{userId}")
    public Response updateUser(@PathVariable Long userId,@RequestBody User updatedUser){
        return iUserService.updateUserById(updatedUser,userId);
    }

    @GetMapping("/getAllUsers")
    @PreAuthorize("hasAuthority('LIBRARIAN')")
    public Response getAllUsers(){
        return  iUserService.getAllUsers();
    }

    @DeleteMapping("/deleteUser/{userId}")
    @PreAuthorize("hasAuthority('LIBRARIAN')")
    public Response deleteUser(@PathVariable Long userId){
        return iUserService.deleteUser(userId);
    }

    @GetMapping("/getUsersBorrowings")
    public Response getUserBorrowings(){
        return iUserService.getUsersBorrowings();
    }

    @GetMapping("/getAllUsersAndBorrowings")
    public Response getAllUsersAndBorrowings(){
        return iUserService.getAllUsersAndBorrowings();
    }
}
