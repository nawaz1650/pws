package com.shahnawaz.pws.reqBodies;

import lombok.Data;

@Data
public class CreateOrderDTO {
    private int qty;
    private String timestamp;
    private String mobile;
    private String address;

}