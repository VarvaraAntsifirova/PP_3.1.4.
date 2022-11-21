package ru.kata.spring.boot_security.demo.init;

import org.springframework.boot.CommandLineRunner;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

public class CommandLineRunnerImpl implements CommandLineRunner {
    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    public CommandLineRunnerImpl(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
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
        admin.setPassword("$2y$10$aHyB0C.gDd4p2U.jLBCS8ep6U4eh/CFSvZUCM8OTAcuAUb2h5Crgq"); // пароль 100
        admin.setFirstName("Admin");
        admin.setLastName("Admin");
        admin.setAge(18);
        admin.setRoles(new String[]{"ROLE_ADMIN", "ROLE_USER"});
        userRepository.save(admin);

        User user = new User();
        user.setUsername("user@mail.ru");
        user.setPassword("$2y$10$cTd02aDowqkoLP/x8W.tuubXUsKlEcc06/EoDafv6g66SEwxxbYw2"); // пароль 200
        user.setFirstName("User");
        user.setLastName("User");
        user.setAge(24);
        user.setRoles(new String[]{"ROLE_USER"});
        userRepository.save(user);
    }
}
