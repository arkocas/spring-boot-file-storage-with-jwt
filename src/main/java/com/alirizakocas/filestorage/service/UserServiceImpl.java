package com.alirizakocas.filestorage.service;

import com.alirizakocas.filestorage.error.NotFoundException;
import com.alirizakocas.filestorage.model.User;
import com.alirizakocas.filestorage.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public void createUser(User user){
        userRepository.save(user);
    }

    public User getUserById(long id){
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User does not exist"));
    }
}
