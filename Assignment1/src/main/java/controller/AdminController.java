package controller;

import mapper.UserTableMapper;
import model.User;
import model.validation.Notification;
import service.user.AuthenticationService;
import service.user.UserService;
import view.AdminView;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class AdminController {
    private AdminView adminView;
    private UserService userService;
    private AuthenticationService authenticationService;
    private UserTableMapper userTableMapper;

    public AdminController(AdminView adminView, UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
        this.adminView = adminView;
        adminView.setViewButtonListener(new ViewButtonListener());
        adminView.setAddButtonListener(new AddButtonListener());
        adminView.setUpdateButtonListener(new UpdateButtonListener());
        adminView.setDeleteButtonListener(new DeleteButtonListener());
//        adminView.setTableMouseListener(new TableMouseListener());
        WindowAdapter windowAdapter = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
                //TODO show LogInUI
                //TODO track activity
            }
        };
        adminView.addWindowListener(windowAdapter);
        adminView.setVisible(false);
        userTableMapper = new UserTableMapper();
    }


    private class ViewButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            userTableMapper.setUsers(userService.findAll());
            adminView.setEmplTable(userTableMapper.formatUserTable());
            if (adminView.getIdTf().chars().allMatch(Character::isDigit) && !adminView.getIdTf().equalsIgnoreCase("")) {
                List<User> userList = new ArrayList<>();
                long userId = Long.parseLong(adminView.getIdTf());
                User user = userService.findById(userId);
                userList.add(user);
                userTableMapper.setUsers(userList);
                adminView.setEmplTable(userTableMapper.formatUserTable());
            } else {
                userTableMapper.setUsers(userService.findAll());
                adminView.setEmplTable(userTableMapper.formatUserTable());
            }
        }
    }

    private class AddButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = adminView.getUsernameTf();
            String pass = adminView.getPassTf();
            String role = adminView.getRolesCombo();
            Notification<Boolean> registerNotification = authenticationService.registerUser(username, pass, role);
            if (registerNotification.hasErrors()) {
                JOptionPane.showMessageDialog(adminView.getContentPane(), registerNotification.getFormattedErrors());
            } else {
                if (!registerNotification.getResult()) {
                    JOptionPane.showMessageDialog(adminView.getContentPane(), "Registration not successful, please try again later.");
                } else {
                    JOptionPane.showMessageDialog(adminView.getContentPane(), "Registration successful( " + role + " ) !");

                }
            }
            userTableMapper.setUsers(userService.findAll());
            adminView.setEmplTable(userTableMapper.formatUserTable());
        }
    }

    private class UpdateButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int row = adminView.getRowClicked();
            int col = adminView.getColClicked();
            userTableMapper.setUsers(userService.findAll());
            Long id = userTableMapper.getID(row);
            String newValue = String.valueOf(adminView.getEmplTable().getValueAt(row, col));
            userService.updateUser(id, col, newValue);
            userTableMapper.setUsers(userService.findAll());
            adminView.setEmplTable(userTableMapper.formatUserTable());
        }
    }

    private class DeleteButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            int row = adminView.getRowClicked();
            userTableMapper.setUsers(userService.findAll());
            Long id = userTableMapper.getID(row);
            userService.deleteUser(id);
            userTableMapper.setUsers(userService.findAll());
            adminView.setEmplTable(userTableMapper.formatUserTable());
        }
    }


    public void showUI() {
        adminView.setVisible(true);
    }

}
