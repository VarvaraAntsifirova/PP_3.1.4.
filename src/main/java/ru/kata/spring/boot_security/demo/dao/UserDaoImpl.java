package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> getAllUsers() {
        return entityManager
                .createQuery("SELECT u FROM User u ", User.class).getResultList();
    }

    @Override
    public void createUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public void deleteUser(int id) {
        entityManager.remove(entityManager.contains(entityManager.find(User.class, id)) ? entityManager.find(User.class, id) :
                entityManager.merge(entityManager.find(User.class, id)));
    }

    @Override
    public void updateUser(int id, User user) {
        User userToBeUpdate = entityManager.find(User.class, id);
        userToBeUpdate.setUsername(user.getUsername());
        userToBeUpdate.setPassword(user.getPassword());
        userToBeUpdate.setFirstName(user.getFirstName());
        userToBeUpdate.setLastName(user.getLastName());
        userToBeUpdate.setAge(user.getAge());
        entityManager.merge(userToBeUpdate);
    }

    @Override
    public User showUser(int id) {
        return entityManager.find(User.class, id);
    }

}
