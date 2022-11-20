package ru.kata.spring.boot_security.demo.service;

import org.springframework.context.annotation.DependsOn;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.Collection;
import java.util.List;

@Service
public interface UserService extends UserDetailsService {

    List<User> getAllUsers();

    void createUser(User user);

    void deleteUser(Integer id);

    void updateUser(Integer id, User user);

    User showUser(Integer id);

    Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles);

    User findByUsername(String username);
}
