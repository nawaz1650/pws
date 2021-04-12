package com.shahnawaz.pws.reqBodies;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GenerateOtpReq
{
    private String mobile;
    private String otp;
}
