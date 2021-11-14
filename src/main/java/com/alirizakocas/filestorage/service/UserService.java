package com.alirizakocas.filestorage.service;

import com.alirizakocas.filestorage.model.User;

public interface UserService {
    void createUser(User user);
    User getUserById(long id);
}
