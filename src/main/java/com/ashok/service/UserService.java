package com.ashok.service;

import com.ashok.dto.UserDto;
import com.ashok.exception.AppException;
import com.ashok.models.AppResponse;

import java.util.List;

public interface UserService {
    AppResponse<UserDto> createUser(UserDto user) throws AppException;
    AppResponse<UserDto> updateUser(UserDto user) throws AppException;
    AppResponse<UserDto> getUser(String userId) throws AppException;
    AppResponse<List<UserDto>> getUsers();
    void deleteUser(String userId) throws AppException;
}
