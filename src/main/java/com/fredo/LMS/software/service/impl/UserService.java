package com.fredo.LMS.software.service.impl;

import com.fredo.LMS.software.Utils.JWTUtils;
import com.fredo.LMS.software.Utils.Utils;
import com.fredo.LMS.software.dto.*;
import com.fredo.LMS.software.model.User;
import com.fredo.LMS.software.repository.BorrowRepository;
import com.fredo.LMS.software.repository.UserRepository;
import com.fredo.LMS.software.service.interfac.IUserService;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BorrowRepository borrowRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public Response registerUser(User newUser){
        Response response = new Response();
        try{
            if(userRepository.existsByEmail(newUser.getEmail())){
                response.setStatusCode(400); // Bad Request
                response.setMessage("Email is already registered");
                return response;
            };
            User user = new User();
            user.setName(newUser.getName());
            user.setEmail(newUser.getEmail());
            user.setPassword(passwordEncoder.encode(newUser.getPassword()));
            user.setUserRole(newUser.getUserRole());
            User savedUser = userRepository.save(user);
            var token = jwtUtils.generateToken(user);
            UserDto userDto = Utils.mapUserEntityToUserDto(savedUser);
            response.setUserDto(userDto);
            response.setToken(token);
            response.setMessage("Registered succesfully");
            response.setRole(userDto.getRole());
            response.setStatusCode(200);
        } catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Internal server error " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response logInUser(LoginRequest loginRequest){
        Response response = new Response();
//        System.out.println("Login request " + loginRequest);
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));
            User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(()-> new Exception(loginRequest.getEmail() + " Not found"));
            var token = jwtUtils.generateToken(user);
            response.setStatusCode(200);
            response.setExpirationTime("7 days");
            response.setToken(token);
            response.setMessage("Login successfully");
        } catch (Exception e){
            System.out.println("Exception " + e);
            response.setStatusCode(500);
            response.setMessage("Internal server error " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getUser2(){
        Response response = new Response();
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String email = userDetails.getUsername();
            User user = userRepository.findByEmail(email).orElseThrow(()->new Exception("User not found.."));
            UserDto userDto = Utils.mapUserEntityToUserDto(user);
            response.setUserDto(userDto);
            response.setStatusCode(200);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Internal server error " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getUserById(Long userId){
        Response response = new Response();
        try{
            User user = userRepository.findById(userId).orElseThrow(()-> new Exception(userId + "not found"));
            UserDto userDto = Utils.mapUserEntityToUserDto(user);
            response.setStatusCode(200);
            response.setUserDto(userDto);
            response.setMessage("User found successfully");
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Internal server error " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response updateUserById(User updatedUser, Long userId){
        Response response = new Response();
        try{
            User user = userRepository.findById(userId).orElseThrow(()-> new Exception(userId + " not found"));
            if(updatedUser.getName() != null) user.setName(updatedUser.getName());
            if(updatedUser.getEmail() != null) user.setEmail(updatedUser.getEmail());
            if(updatedUser.getPassword() != null) user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            userRepository.save(user);
            UserDto userDto = Utils.mapUserEntityToUserDto(user);
            response.setStatusCode(200);
            response.setUserDto(userDto);
            response.setMessage("Updated Successfully");
        } catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Internal server error " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getUsersBorrowings() {
        Response response = new Response();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        try {
            User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(()-> new Exception("User not found"));
            // Fetch the list of BorrowDetailsDto directly from the query
            List<BorrowDetailsDto> borrowDetailsDtoList = borrowRepository.findByUserId(user.getId());
            System.out.println(borrowDetailsDtoList);
            // Set the response if data is successfully fetched
            response.setStatusCode(200);
            response.setBorrowingDetails(borrowDetailsDtoList);
            response.setMessage("Data fetched successfully");

        } catch (Exception e) {
            // Handle any exceptions and return an error message
            response.setStatusCode(500);
            response.setMessage("Internal server error: " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response getAllUsers() {
        Response response = new Response();
        try {
            List<User> userList = userRepository.findAll();
            if (userList.isEmpty()) {
                throw new Exception("Resource not found");
            }
            List<UserDto> userDtoList = Utils.mapUserListEntityToUserDtoList(userList);
            response.setStatusCode(200);
            response.setMessage("Returned successfully");
            response.setUserDtoList(userDtoList);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Internal server error " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response deleteUser(Long userId) {
        Response response = new Response();
        try {
            userRepository.deleteById(userId);
            response.setStatusCode(200);
            response.setMessage("User deleted successfully");
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Internal server error " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAllUsersAndBorrowings() {
        Response response = new Response();
        try {
            List<LibrarianNeedsDto> librarianNeedsDtos = borrowRepository.findLibrarianNeeds();
            response.setLibrarianNeedsDtos(librarianNeedsDtos);
            response.setStatusCode(200);
            response.setMessage("Data sent successfully");
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Internal server error: " + e.getMessage());
        }
        return response;
    }
}


