package com.shahnawaz.pws.security;

import com.shahnawaz.pws.entities.PwsUser;
import com.shahnawaz.pws.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailService implements UserDetailsService {
@Autowired
    UserRepo userrepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        PwsUser user=userrepo.findByMobile(username);
        return new MyUserDetail(user);
    }
}
