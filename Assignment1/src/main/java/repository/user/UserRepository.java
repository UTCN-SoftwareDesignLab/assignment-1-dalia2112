package repository.user;

import model.User;
import model.validation.Notification;
import repository.EntityNotFoundException;

import java.util.List;

/**
 * Created by Alex on 11/03/2017.
 */
public interface UserRepository {

    List<User> findAll();

    Notification<User> findByUsernameAndPassword(String username, String password) throws AuthenticationException;
    User findByUsername(String username);

    boolean save(User user);

    void removeAll();

    User findById(Long id) /*throws EntityNotFoundException*/;

    void updateUser(Long id, String col, String newval);

    void deleteUser(Long id);

}
