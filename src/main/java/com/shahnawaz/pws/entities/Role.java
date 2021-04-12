package com.shahnawaz.pws.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
@Data
public class Role {
    @Id
    @GeneratedValue
    private int role_id;


    private String name;
    @JsonBackReference
    @ManyToMany(mappedBy ="roles" )
    private List<PwsUser> users;
}
