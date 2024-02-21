package com.example.demo.service;

import com.example.demo.Exception.UserException;
import com.example.demo.model.User;

public interface UserService {

    public User findUserById(Long userId) throws UserException;

    public User findUserProfileByJwt(String jwt) throws UserException;


        }
