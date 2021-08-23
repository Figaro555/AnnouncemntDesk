package com.announcementdesk.services;

import com.announcementdesk.domain.Role;
import com.announcementdesk.domain.User;
import com.announcementdesk.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;

@Service
@Transactional
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean addUser(User user) {
        User userFromDb = userRepository.findByEmail(user.getEmail());
        if (userFromDb != null) {
            return false;
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);

        return true;
    }

    public User findByName(String name) {
        return userRepository.findByName(name);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByName(username);
        if (user == null) {
            System.out.println("Misha vse huynya");
            System.out.println(username);
            throw new UsernameNotFoundException("User ne nayden");
        }
        return user;
    }
}
