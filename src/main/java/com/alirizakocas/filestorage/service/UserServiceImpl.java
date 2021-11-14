package com.alirizakocas.filestorage.service;

import com.alirizakocas.filestorage.error.NotFoundException;
import com.alirizakocas.filestorage.model.User;
import com.alirizakocas.filestorage.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public void createUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));//password encode edildi
        userRepository.save(user);
    }

    public User getUserById(long id){
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User does not exist"));
    }
}
