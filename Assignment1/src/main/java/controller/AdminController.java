package controller;

import model.validation.Notification;
import service.user.AuthenticationService;
import service.user.UserService;
import view.AdminView;

import javax.swing.*;
import java.awt.event.*;

public class AdminController {
    private AdminView adminView;
    private UserService userService;
    private AuthenticationService authenticationService;

    public AdminController(AdminView adminView, UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
        this.adminView = adminView;
        adminView.setViewButtonListener(new ViewButtonListener());
        adminView.setAddButtonListener(new AddButtonListener());
        adminView.setUpdateButtonListener(new UpdateButtonListener());
        adminView.setDeleteButtonListener(new DeleteButtonListener());
        adminView.setTableMouseListener(new TableMouseListener());
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
    }


    private class ViewButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            adminView.setEmplTable(userService.writeUserTable(adminView.getIdTf()));
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
            adminView.setEmplTable(userService.writeUserTable(adminView.getIdTf()));
        }
    }

    private class UpdateButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int row = adminView.getRowClicked();
            int col = adminView.getColClicked();
            String idd = String.valueOf(adminView.getEmplTable().getModel().getValueAt(row, 0));
            Long id = Long.parseLong(idd);
            String nv = String.valueOf(adminView.getEmplTable().getValueAt(row, col));
            System.out.println("New val " + nv);
            userService.updateUser(id, col, nv);
            adminView.setEmplTable(userService.writeUserTable(adminView.getIdTf()));
        }
    }

    private class DeleteButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            int row = adminView.getRowClicked();
            String idd = String.valueOf(adminView.getEmplTable().getModel().getValueAt(row, 0));
            Long id = Long.parseLong(idd);
            userService.deleteUser(id);
            adminView.setEmplTable(userService.writeUserTable(adminView.getIdTf()));
        }
    }

    private class TableMouseListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {

            adminView.setRowClicked(adminView.getEmplTable().getSelectedRow());
            adminView.setColClicked(adminView.getEmplTable().getSelectedColumn());
        }
    }

    public void showUI() {
        adminView.setVisible(true);
    }

}
