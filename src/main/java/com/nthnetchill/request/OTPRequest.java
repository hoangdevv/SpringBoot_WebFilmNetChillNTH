package com.nthnetchill.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OTPRequest {
    private String token;
    private String otp;
}
