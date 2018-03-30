package mapper;

import model.Account;
import model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class UserTableMapper {

    private List<User> users;

    public UserTableMapper() {
        users = new ArrayList<>();
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Vector<Vector<String>> formatUserTable() {
        Vector<Vector<String>> usersList = new Vector<>();
        for (User user : users) {
            Vector<String> data = new Vector<>();
            data.add(user.getId().toString());
            data.add(user.getUsername());
            data.add(user.getPassword());
            data.add(user.getRoles().get(0).getRole());
            usersList.add(data);
        }
        return usersList;
    }

    public long getID(int row){
        return users.get(row).getId();
    }
}

