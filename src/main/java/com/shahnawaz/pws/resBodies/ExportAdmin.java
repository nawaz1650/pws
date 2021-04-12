package com.shahnawaz.pws.resBodies;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
@Data
@AllArgsConstructor
public class ExportAdmin {
    private int user_id;
    private String mobile;
    private int order_id;
    private int qty;
    private String address;
    private Date order_date;
    private Date created_date;

}
