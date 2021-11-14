package com.alirizakocas.filestorage.jwt;


import com.alirizakocas.filestorage.model.User;
import com.alirizakocas.filestorage.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// Spring'in Auth Manager'ı default bu sınıfı çağıracaktır
@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepository.findByUsername(username);
        //user databasede yok ise
        if(user == null){
            throw new UsernameNotFoundException(username); // username yok ise not found exception fırlattık.
        }
        return user;
    }
}
