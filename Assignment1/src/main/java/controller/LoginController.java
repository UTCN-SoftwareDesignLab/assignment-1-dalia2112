package controller;

import database.Constants;
import model.Role;
import model.User;
import model.validation.Notification;
import repository.user.AuthenticationException;
import service.user.AuthenticationService;
import view.AdminView;
import view.EmployeeView;
import view.LoginView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collections;

/**
 * Created by Alex on 18/03/2017.
 */
public class LoginController {
    private final LoginView loginView;
    private EmployeeController employeeController;
    private AdminController adminController;
    private final AuthenticationService authenticationService;
    private boolean admin;

    public LoginController(LoginView loginView,AuthenticationService authenticationService, AdminController adminController, EmployeeController employeeController) {
        this.loginView = loginView;
        this.authenticationService = authenticationService;
        this.adminController = adminController;
        this.employeeController = employeeController;
        adminController.setWindowListener(new ControllerWindowListener());
        employeeController.setWindowListener(new ControllerWindowListener());
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


            } catch (AuthenticationException e1) {
                JOptionPane.showMessageDialog(null,loginNotification.getFormattedErrors());
                e1.printStackTrace();
            }

            if (loginNotification != null) {
                if (loginNotification.hasErrors()) {
                    JOptionPane.showMessageDialog(loginView.getContentPane(), loginNotification.getFormattedErrors());
                } else {
                    role = loginNotification.getResult().getRoles().get(0);
                    JOptionPane.showMessageDialog(loginView.getContentPane(), "Login successful (" + role.getRole() + ") !");
                    loginView.setVisible(false);

                    //SET THE LOGGED USER
                    User loggedUser=authenticationService.findByUsername(username);
                    employeeController.setLoggedUser(loggedUser);
                    adminController.setLoggedUser(loggedUser);

                    if (role.getRole().equalsIgnoreCase("employee")) {   //FOR EMPLOYEEE
                        admin=false;
                        SwingUtilities.invokeLater(() -> {
                            employeeController.showUI();
                        });
                    } else {        //FOR ADMIN
                        admin=true;
                        SwingUtilities.invokeLater(() -> {
                            adminController.showUI();
                        });
                    }
                }
            }
        }
    }

    private class ControllerWindowListener extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            if(admin) adminController.hideUI();
            else employeeController.hideUI();
        }

        @Override
        public void windowClosed(WindowEvent e) {
            super.windowClosed(e);
            loginView.setVisible(true);
        }
    }

}
