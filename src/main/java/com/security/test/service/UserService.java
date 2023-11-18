package com.security.test.service;

import com.security.test.entity.User;
import com.security.test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);
        return userOptional.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
    }

    public User save(User user) {
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        // Crea un nuevo usuario con la contraseña encriptada
        user.setPassword(encryptedPassword);
        return userRepository.save(user);
    }

    public boolean userNotExists(){
        return userRepository.findAll().isEmpty();
    }
}


