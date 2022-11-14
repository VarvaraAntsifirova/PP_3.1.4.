package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void createUser(User user) {
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(Integer id) {
        userRepository.delete(userRepository.findById(id).get());
    }

    @Override
    @Transactional
    public void updateUser(Integer id, User user) {
        User userToBeUpdate = showUser(id);
        userToBeUpdate.setUsername(user.getUsername());
        userToBeUpdate.setPassword(user.getPassword());
        userToBeUpdate.setFirstName(user.getFirstName());
        userToBeUpdate.setLastName(user.getLastName());
        userToBeUpdate.setAge(user.getAge());
        userToBeUpdate.setRole(user.getRoles());
        userRepository.save(userToBeUpdate);
    }

    @Override
    public User showUser(Integer id) {
        return userRepository.findById(id).get();
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, noRollbackFor = Exception.class)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);


        if (user == null) {
            throw new UsernameNotFoundException("User not found!");
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    @Override
    public Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getRole())).collect(Collectors.toList());
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }
}
