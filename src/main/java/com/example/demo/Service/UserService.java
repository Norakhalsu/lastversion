package com.example.demo.Service;

import com.example.demo.Api.ApiException;
import com.example.demo.Model.User;
import com.example.demo.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;


    // ADMIN
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    // ADMIN
    public User getUserById(Integer id) {
        User u1 = userRepository.findUserById(id);
        if (u1 == null) {
            throw new ApiException("User Not found");
        }
        return u1;
    }


    public void Register(User user) {
        user.setRole("USER");
        String hash = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(hash);
        userRepository.save(user);
    }

    //update
    public void updateUser(Integer id,User user) {
        User user1 = userRepository.findUserById(id);
        if (user1 == null){
            throw new ApiException("User not found");
        }
        String hash = new BCryptPasswordEncoder().encode(user.getPassword());
        user1.setPassword(hash);
        user1.setEmail(user.getEmail());
        user1.setFullName(user.getFullName());
        userRepository.save(user1);
    }
    //delete
    public void deleteUser(Integer id) {
        User user = userRepository.findUserById(id);
        if (user == null){
            throw new ApiException("User not found");
        }
        userRepository.deleteById(id);
    }
   // ---------------- end point ----------------------

}
