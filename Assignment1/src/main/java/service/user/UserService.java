package service.user;

import model.User;
import repository.EntityNotFoundException;

import javax.swing.*;
import java.util.List;
import java.util.Vector;

public interface UserService {

    List<User> findAll();

    User findById(Long id) /*throws EntityNotFoundException*/;

    boolean save(User user);

    void updateUser(Long id,int col,String newval);

    void deleteUser(Long id);

    Vector<Vector<String>> getAllUserTable();
    Vector<Vector<String>> writeUserTable(String idGiven);
}
