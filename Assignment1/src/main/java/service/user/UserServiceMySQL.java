package service.user;

import model.User;
import repository.EntityNotFoundException;
import repository.user.UserRepository;

import javax.swing.*;
import java.util.List;
import java.util.Vector;

public class UserServiceMySQL implements UserService{
    private final UserRepository userRepository;

    public UserServiceMySQL(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User findById(Long id) /*throws EntityNotFoundException*/{
        return userRepository.findById(id);
    }

    public boolean save(User user){
        return userRepository.save(user);
    }

    public void updateUser(Long id,int col,String newval){

        String column="";
        switch (col) {
            case 0:
                JOptionPane.showMessageDialog(null,"Cannot change id!");
                return;
            case 1:
                column="username";
                break;
            case 2:
                column="password";
                break;
            default: column="username";
        }
        userRepository.updateUser(id,column,newval);
    }

    public void deleteUser(Long id){
        userRepository.deleteUser(id);
    }

    public Vector<Vector<String>> getAllUserTable(){
        Vector<Vector<String>> users = new Vector<>();
        for(User c:findAll()){
            Vector<String> data = new Vector<>();
            data.add(c.getId().toString());
            data.add(c.getUsername());
            data.add(c.getPassword());
            data.add(c.getRoles().get(0).getRole());
            users.add(data);
        }
        return users;
    }

    public Vector<Vector<String>> writeUserTable(String idGiven) {
        if(idGiven.chars().allMatch(Character::isDigit) && !idGiven.equalsIgnoreCase("")){
            Vector<Vector<String>> data= new Vector<>();
            Vector<String> d=new Vector<>();
            Long id=Long.parseLong(idGiven);
            User u=findById(id);
            d.add(u.getId().toString());
            d.add(u.getUsername());
            d.add((u.getPassword()));
            d.add(u.getRoles().get(0).getRole());
            data.add(d);
//            adminView.setEmplTable(data);
            return  data;
        }
        else {
             return getAllUserTable();
            //adminView.setEmplTable(data);
        }
    }
}
