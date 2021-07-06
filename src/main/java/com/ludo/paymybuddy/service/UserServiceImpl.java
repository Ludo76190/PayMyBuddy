package com.ludo.paymybuddy.service;

import com.ludo.paymybuddy.model.User;
import com.ludo.paymybuddy.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Override
    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!(principal instanceof UserDetails)) {
            logger.debug("getCurrentUser - Principal data issue");
            return null;
        }
        String username = ((UserDetails)principal).getUsername();
        return userRepository.findByEmail(username);
    }

    @Override
    public User findByEmail(String email) {
        logger.info("Recherche du user pour l'email " + email);
        return userRepository.findByEmail(email);
    }

    public User saveUser(User user) {

        User foundUser = userRepository.findByEmail(user.getEmail());
        if (foundUser != null) {
            logger.error("Registration failed. Email entered already exist");
            return null;
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        logger.info("sauvegarde du user " + user.getId());
        userRepository.save(user);

        return user;
    }

    @Override
    public User addFriend(String friendEmail) {

        User owner = userService.getCurrentUser();
        if (owner.getEmail().equals(friendEmail)) {
            logger.error("Failed- Same mail");
            return null;
        }

        User friend = userRepository.findByEmail(friendEmail);

        if (friend == null) {
            logger.error("No buddy registered with this Email");
            return null;
        }
        if (owner.getFriends().contains(friend)) {
            logger.error("This friend already registered for this owner");
            return null;
        }

        owner.getFriends().add(friend);

        logger.info("Sauvegarde du user " + owner.getId());
        return userRepository.save(owner);
    }

    @Override
    public void deleteFriend(User owner, Integer friendID) {

        List<User> friends = owner.getFriends();
        friends.removeIf(u -> u.getId().equals(friendID));

        logger.info("Suppression du user " + owner.getId());
        userRepository.save(owner);

    }

}
