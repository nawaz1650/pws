package com.shahnawaz.pws;

import com.shahnawaz.pws.entities.PwsUser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;

@SpringBootApplication
public class PatelWaterSupplyApplication {

	public static void main(String[] args) {
		SpringApplication.run(PatelWaterSupplyApplication.class, args);

				Date date=new Date(1617906600000L);
				System.out.println(date);

		System.out.println(System.getenv("PATH"));
		//pws.setName("shahnawaz");
		//System.out.println(pws.getName());
	}

	//@PostMapping("/generateOtp")
	//public


}
