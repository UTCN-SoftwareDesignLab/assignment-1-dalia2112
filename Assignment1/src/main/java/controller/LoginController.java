package controller;

import database.Constants;
import model.Role;
import model.User;
import model.validation.Notification;
import repository.user.AuthenticationException;
import service.user.AuthenticationService;
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

    public LoginController(LoginView loginView, AuthenticationService authenticationService) {
        this.loginView = loginView;
        this.authenticationService = authenticationService;
        loginView.setLoginButtonListener(new LoginButtonListener());
        loginView.setRegisterButtonListener(new RegisterButtonListener());
    }

    private class LoginButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<User> loginNotification = null;
            Role role=null;
            try {
                loginNotification = authenticationService.login(username, password);
                role=loginNotification.getResult().getRoles().get(0);
                //role=authenticationService.findRoleForUserId(loginNotification.getResult().getId());
            } catch (AuthenticationException e1) {
                e1.printStackTrace();
            }

            if (loginNotification != null) {
                if (loginNotification.hasErrors()) {
                    JOptionPane.showMessageDialog(loginView.getContentPane(), loginNotification.getFormattedErrors());
                } else {

                    JOptionPane.showMessageDialog(loginView.getContentPane(), "Login successful("+role.getRole()+") !");
                    loginView.setVisible(false);
                    if(role.getRole()==Constants.Roles.EMPLOYEE) {   //FOR EMPLOYEEE
                        SwingUtilities.invokeLater(() -> {
                            employeeController.showUI();
                        });
                    }
                    else{        //FOR ADMIN
                        SwingUtilities.invokeLater(() -> {
                            adminController.showUI();
                        });
                    }
                }
            }
        }
    }

    private class RegisterButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();
            boolean admin=loginView.getRdBtn().isSelected();
//            Notification<Boolean> registerNotification = authenticationService.register(username, password);
            Notification<Boolean> registerNotification = authenticationService.registerUser(username,password,admin);
            if (registerNotification.hasErrors()) {
                JOptionPane.showMessageDialog(loginView.getContentPane(), registerNotification.getFormattedErrors());
            } else {
                if (!registerNotification.getResult()) {
                    JOptionPane.showMessageDialog(loginView.getContentPane(), "Registration not successful, please try again later.");
                } else {
                    String role="";
                    if(admin)
                        role="admin";
                    else role="employee";
                    JOptionPane.showMessageDialog(loginView.getContentPane(), "Registration successful("+role+") !");

                }
            }
        }
    }

    public void attachEmployeeController(EmployeeController employeeController){
        this.employeeController = employeeController;
    }

    public void attachAdminController(AdminController adminController){
        this.adminController=adminController;
    }
}
