package com.ashok.util;

import com.ashok.dao.UserDAO;
import com.ashok.dto.UserDto;
import com.ashok.entity.User;

public class AppUtil {
    public static boolean isSameUser(UserDto userDto, User user) {
        return userDto.getUserId().equals(user.getUserId()) && userDto.getName().equals(user.getName());
    }

    public static UserDto userEntityToDto(User user) {
        return new UserDto(user.getUserId(), user.getName());
    }

    public static User userDtoToEntity(UserDto userDto) {
        return new User(userDto.getUserId(), userDto.getName());
    }
}
