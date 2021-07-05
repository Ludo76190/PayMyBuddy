package com.ludo.paymybuddy.service;

import com.ludo.paymybuddy.model.User;
import com.ludo.paymybuddy.security.MyUserDetails;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserService userService;

    private static final Logger logger = LogManager.getLogger(UserDetailsServiceImpl.class);

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        User user = userService.findByEmail(mail);

        if(user == null){
            logger.debug("User not found! " + mail);
            throw new UsernameNotFoundException("L'utilisateur n'existe pas");
        }

        return new MyUserDetails(user);
    }

}
