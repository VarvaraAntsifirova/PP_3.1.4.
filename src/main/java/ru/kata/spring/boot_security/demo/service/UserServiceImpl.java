package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getRoleName())).collect(Collectors.toList());
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @PostConstruct
    public void initialCreatingOfEntities() {
        Role roleAdmin = new Role();
        roleAdmin.setRoleName("ROLE_ADMIN");
        roleAdmin.setId(1);
        roleRepository.save(roleAdmin);

        Role roleUser = new Role();
        roleUser.setRoleName("ROLE_USER");
        roleUser.setId(2);
        roleRepository.save(roleUser);

        User admin = new User();
        admin.setUsername("admin@mail.ru");
        admin.setPassword("$2y$10$aHyB0C.gDd4p2U.jLBCS8ep6U4eh/CFSvZUCM8OTAcuAUb2h5Crgq"); //100
        admin.setFirstName("Admin");
        admin.setLastName("Admin");
        admin.setAge(18);
        admin.setRoles(new String[]{"ROLE_ADMIN", "ROLE_USER"});
        userRepository.save(admin);

        User user = new User();
        user.setUsername("user@mail.ru");
        user.setPassword("$2y$10$cTd02aDowqkoLP/x8W.tuubXUsKlEcc06/EoDafv6g66SEwxxbYw2"); //200
        user.setFirstName("User");
        user.setLastName("User");
        user.setAge(24);
        user.setRoles(new String[]{"ROLE_USER"});
        userRepository.save(user);
    }
}
