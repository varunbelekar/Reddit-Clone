package com.reddit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.stream.IntStream;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class LoginRequest {
    private String userName;
    private String password;
}
