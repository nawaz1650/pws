package com.shahnawaz.pws.repos;

import com.shahnawaz.pws.entities.PwsUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<PwsUser,Integer> {
    PwsUser findByMobile(String mobile);
}
