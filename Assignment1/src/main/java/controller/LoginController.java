package controller;

import database.Constants;
import model.Role;
import model.User;
import model.validation.Notification;
import repository.user.AuthenticationException;
import service.user.AuthenticationService;
import view.AdminView;
import view.LoginView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;

/**
 * Created by Alex on 18/03/2017.
 */
public class LoginController {
    private final LoginView loginView;
    private EmployeeController employeeController;
    private AdminController adminController;
    private final AuthenticationService authenticationService;

    public LoginController(LoginView loginView, AuthenticationService authenticationService, AdminController adminController, EmployeeController employeeController) {
        this.loginView = loginView;
        this.authenticationService = authenticationService;
        this.adminController = adminController;
        this.employeeController = employeeController;
        loginView.setLoginButtonListener(new LoginButtonListener());
    }

    private class LoginButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<User> loginNotification = null;
            Role role = null;
            try {
                loginNotification = authenticationService.login(username, password);
                role = loginNotification.getResult().getRoles().get(0);
            } catch (AuthenticationException e1) {
                e1.printStackTrace();
            }

            if (loginNotification != null) {
                if (loginNotification.hasErrors()) {
                    JOptionPane.showMessageDialog(loginView.getContentPane(), loginNotification.getFormattedErrors());
                } else {

                    JOptionPane.showMessageDialog(loginView.getContentPane(), "Login successful (" + role.getRole() + ") !");
                    loginView.setVisible(false);
                    if (role.getRole().equalsIgnoreCase("employee")) {   //FOR EMPLOYEEE
                        SwingUtilities.invokeLater(() -> {
                            employeeController.showUI();
                        });
                    } else {        //FOR ADMIN
                        SwingUtilities.invokeLater(() -> {
                            adminController.showUI();
                        });
                    }
                }
            }
        }
    }

}
