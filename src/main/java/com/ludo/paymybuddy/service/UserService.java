package com.ludo.paymybuddy.service;

import com.ludo.paymybuddy.model.User;

public interface UserService {

    User getCurrentUser();
    User findByEmail(String email);
    User saveUser(User user);
    User addFriend(String friendEmail);
    void deleteFriend(User owner, Integer friendID);

}
