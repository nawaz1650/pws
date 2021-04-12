package com.shahnawaz.pws.entities;


import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Data
@RequiredArgsConstructor
public class PwsUser {
	@Id
	@GeneratedValue
	private int user_id;
	
	private String mobile;
	private String otp;
	private String Address;



@JsonManagedReference
	@ManyToMany(fetch = FetchType.EAGER)
@JoinTable(name = "user_role",
		joinColumns =  @JoinColumn(name="user_id"),inverseJoinColumns = @JoinColumn(name="role_id"))
	private List<Role> roles;
	
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	@Column(nullable = false,updatable = false)
	private Date created_date;
	
	@Temporal(TemporalType.TIMESTAMP)
		@UpdateTimestamp
	private Date updated_date;
@JsonManagedReference
	@OneToMany(mappedBy = "user")
	private List<Order> orders;


	@Override
	public String toString() {
		return "PwsUser{" +
				"user_id=" + user_id +
				", mobile='" + mobile + '\'' +
				", otp='" + otp + '\'' +
				", Address='" + Address + '\'' +
				", created_date=" + created_date +
				", updated_date=" + updated_date +
				'}';
	}
	

}
