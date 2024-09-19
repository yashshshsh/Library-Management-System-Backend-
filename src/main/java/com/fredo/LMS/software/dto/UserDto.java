package com.fredo.LMS.software.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    private long userId;
    private String name;
    private String email;
    private String role;
}
