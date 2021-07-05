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

//    @Override
//    public void save(User user) {
//        userRepository.save(user);
//    }

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

//    public List<User> getUsers() {
//        return userRepository.findAll();
//    }

//    public User getUser(int id) {
//        return userRepository.findById(id);
//    }

//    @Override
//    public User findByLastName(String lastName) {
//        User user = userRepository.findByLastname(lastName);
//        if (user == null) {
//            return null;
//        }
//        return user;
//    }

    @Override
    public User findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return null;
        }
        return user;
    }

    public void deleteUser(int id) {
        userRepository.deleteById(id);
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

        userRepository.save(user);

        return user;
    }

//    public User updateUser(User user) {
//        return userRepository.save(user);
//    }

    @Override
    public User addFriend(String friendEmail) {

        User owner = userService.getCurrentUser();
        if (owner.getEmail().equals(friendEmail)) {
            logger.error("Failed- Same mail: ");
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

        return userRepository.save(owner);
    }

    @Override
    public void deleteFriend(User owner, Integer friendID) {

        List<User> friends = owner.getFriends();
        friends.removeIf(u -> u.getId().equals(friendID));

        userRepository.save(owner);

    }

}
