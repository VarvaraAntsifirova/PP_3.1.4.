/*
package ru.kata.spring.boot_security.demo.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final UserRepository userRepository;

    @Autowired
    public CommandLineRunnerImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
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
*/
