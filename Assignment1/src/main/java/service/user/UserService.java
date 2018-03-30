package service.user;

import model.User;
import repository.EntityNotFoundException;

import javax.swing.*;
import java.util.List;
import java.util.Vector;

public interface UserService {

    List<User> findAll();

    User findById(Long id) /*throws EntityNotFoundException*/;

    User findByUsername(String username);

    boolean save(User user);

    void updateUser(Long id, String column, String newval);

    void deleteUser(Long id);

}
