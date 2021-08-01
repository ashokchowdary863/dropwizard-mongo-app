package com.ashok.service;

import com.ashok.dao.UserDAO;
import com.ashok.dto.UserDto;
import com.ashok.entity.User;
import com.ashok.exception.AppError;
import com.ashok.exception.AppException;
import com.ashok.models.AppResponse;
import com.ashok.util.AppUtil;
import com.google.inject.Inject;
import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Inject
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public AppResponse<UserDto> createUser(UserDto user) throws AppException {
        User existingUser = userDAO.getUser(user.getUserId());
        if (existingUser != null && !AppUtil.isSameUser(user, existingUser)) {
            LOGGER.error("User already exists with id : {}, ", user.getUserId());
            throw new AppException(AppError.USER_ALREADY_EXIST, "UserId %s already exists...", user.getUserId());
        } else if (existingUser!=null && AppUtil.isSameUser(user, existingUser)) {
            LOGGER.info("User already exists and the request is idempotent");
            return new AppResponse<UserDto>(HttpStatus.OK_200, user);
        }
        userDAO.createUser(AppUtil.userDtoToEntity(user));
        LOGGER.info("User {} created successfully..", user.getUserId());
        return new AppResponse<UserDto>(HttpStatus.CREATED_201, user);
    }

    @Override
    public AppResponse<UserDto> updateUser(UserDto user) throws AppException {
        User existingUser = userDAO.getUser(user.getUserId());
        if (existingUser == null) {
            LOGGER.error("User not found : user id {}", user.getUserId());
            throw new AppException(AppError.USER_NOT_FOUND, "User %s not exists...", user.getUserId());
        }
        if (AppUtil.isSameUser(user, existingUser)) {
            LOGGER.info("User is already updated with the data : user id {}", user.getUserId());
            return new AppResponse<UserDto>(HttpStatus.OK_200, user);
        }
        userDAO.updateUser(AppUtil.userDtoToEntity(user));
        LOGGER.info("User updated successfullly... : user id  {}", user.getUserId());
        return new AppResponse<UserDto>(HttpStatus.OK_200, user);
    }

    @Override
    public AppResponse<UserDto> getUser(String userId) throws AppException {
        User existingUser = userDAO.getUser(userId);
        if (existingUser == null) {
            LOGGER.error("User not found : user id {}", userId);
            throw new AppException(AppError.USER_NOT_FOUND, "User %s does not exists...", userId);
        }
        return new AppResponse<UserDto>(HttpStatus.OK_200, AppUtil.userEntityToDto(existingUser));
    }

    @Override
    public AppResponse<List<UserDto>> getUsers() {
        List<User> users = userDAO.getUsers();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(AppUtil.userEntityToDto(user));
        }

        return new AppResponse<>(HttpStatus.OK_200, userDtos);
    }

    @Override
    public void deleteUser(String userId) throws AppException {
        User existingUser = userDAO.getUser(userId);
        if (existingUser == null) {
            LOGGER.error("User not found : user id {}", userId);
            throw new AppException(AppError.USER_NOT_FOUND, "User %s not exists...", userId);
        }
        userDAO.deleteUser(userId);
        LOGGER.info("User {} deleted successfully...", userId);
    }
}
