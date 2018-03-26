package service.user;

import model.Role;
import model.User;
import model.validation.Notification;
import repository.user.AuthenticationException;

import java.util.List;

/**
 * Created by Alex on 11/03/2017.
 */
public interface AuthenticationService {

    Notification<Boolean> register(String username, String password);

    Notification<Boolean> registerUser(String username, String password,boolean admin);

    Notification<User> login(String username, String password) throws AuthenticationException;

    boolean logout(User user);

    public Role findRoleForUserId(Long userId);

}
