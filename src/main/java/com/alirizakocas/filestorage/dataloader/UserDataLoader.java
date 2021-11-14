package com.alirizakocas.filestorage.dataloader;

import com.alirizakocas.filestorage.model.User;
import com.alirizakocas.filestorage.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDataLoader implements CommandLineRunner {

    private final UserService userService;

    @Override
    public void run(String... args) throws Exception {
        User user1 = new User("test1","pass1");
        User user2 = new User("test2","pass2");

        //userlar oluşturuldu
        userService.createUser(user1);
        userService.createUser(user2);


    }
}
