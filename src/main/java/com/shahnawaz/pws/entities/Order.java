package com.shahnawaz.pws.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue
    private int order_id;

    private int qty;
    private Date order_date;

    private String address;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date created_date;
    @JsonBackReference
    @ManyToOne
    private PwsUser user;
}
