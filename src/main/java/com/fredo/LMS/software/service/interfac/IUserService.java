package com.fredo.LMS.software.service.interfac;

import com.fredo.LMS.software.dto.LoginRequest;
import com.fredo.LMS.software.dto.Response;
import com.fredo.LMS.software.model.User;

public interface IUserService {

    Response registerUser(User newUser);

    Response logInUser(LoginRequest loginRequest);

    Response getUser2();

    Response getUserById(Long userId);

    Response updateUserById(User updatedUser, Long userId);

    Response getUsersBorrowings();

    Response getAllUsers();

    Response deleteUser(Long userId);

    Response getAllUsersAndBorrowings();
}
