package com.ashok.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;

public class UserDto {
    @NotEmpty(message = "userId can't be empty")
    @JsonProperty("userId")
    private String userId;
    @NotEmpty(message = "name can't be empty")
    @JsonProperty("name")
    private String name;

    public UserDto() {
    }

    public UserDto(String id, String name) {
        this.userId = id;
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("UserDto{");
        sb.append("userId='").append(userId).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
