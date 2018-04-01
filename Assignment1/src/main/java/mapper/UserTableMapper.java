package mapper;

import database.Constants;
import model.Account;
import model.Role;
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

    public String getColumn(int col) {
        String column = "";
        switch (col) {
            case 0:
                column = "id";
            case 1:
                column = "username";
                break;
            case 2:
                column = "password";
                break;
        }
        return column;
    }

    public Vector<Vector<String>> formatUserTable() {
        Vector<Vector<String>> usersList = new Vector<>();
        for (User user : users) {
            Vector<String> data = new Vector<>();
            for (String column : Constants.Columns.USER_COLS) {
                data.add(getValueAtColumn(column, user));
            }
            usersList.add(data);
        }
        return usersList;
    }

    public String getValueAtColumn(String column, User user) {
        switch (column) {
            case "Id":
                return user.getId().toString();
            case "Username":
                return user.getUsername();
            case "Password":
                return user.getPassword().toString();
            case "Role":
                String roles = "";
                for (Role role : user.getRoles())
                    roles += role.getRole() + " ";
                return roles;
        }
        return "";
    }

    public long getID(int row) {
        return users.get(row).getId();
    }
}

