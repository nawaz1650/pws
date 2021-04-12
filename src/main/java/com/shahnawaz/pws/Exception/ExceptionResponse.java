package com.shahnawaz.pws.Exception;

import lombok.Data;

import java.util.Date;
@Data
public class ExceptionResponse {
    private Date timeStamp;
    private String message;
    private String details;


    public ExceptionResponse(Date timeStamp, String message, String details) {
        super();
        this.timeStamp = timeStamp;
        this.message = message;
        this.details = details;
    }



    public Date getTimeStamp() {
        return timeStamp;
    }



    public String getMessage() {
        return message;
    }



    public String getDetails() {
        return details;
    }



    public ExceptionResponse() {

    }


}
