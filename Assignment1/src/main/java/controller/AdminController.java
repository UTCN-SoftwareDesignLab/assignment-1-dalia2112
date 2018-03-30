package controller;

import database.Constants;
import jdk.nashorn.internal.scripts.JO;
import mapper.UserTableMapper;
import model.Activity;
import model.User;
import model.builder.ActivityBuilder;
import model.builder.UserBuilder;
import model.validation.Notification;
import model.validation.UserValidator;
import service.activity.ActivityService;
import service.user.AuthenticationService;
import service.user.UserService;
import view.AdminView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class AdminController {
    private AdminView adminView;
    private UserService userService;
    private ActivityService activityService;
    private AuthenticationService authenticationService;
    private UserTableMapper userTableMapper;
    private User loggedUser;

    public AdminController(AdminView adminView, UserService userService, AuthenticationService authenticationService, ActivityService activityService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
        this.activityService = activityService;
        this.adminView = adminView;
        adminView.setViewButtonListener(new ViewButtonListener());
        adminView.setAddButtonListener(new AddButtonListener());
        adminView.setUpdateButtonListener(new UpdateButtonListener());
        adminView.setDeleteButtonListener(new DeleteButtonListener());
        adminView.setActivityButtonListener(new ActivityButtonListener());
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
                addActivity(Constants.Activities.VIEW_USER+user.getUsername());
            } else {
                userTableMapper.setUsers(userService.findAll());
                adminView.setEmplTable(userTableMapper.formatUserTable());
                addActivity(Constants.Activities.VIEW_USER+" : all users");
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
                return;
            } else {
                if (!registerNotification.getResult()) {
                    JOptionPane.showMessageDialog(adminView.getContentPane(), "Registration not successful, please try again later.");
                } else {
                    addActivity("added "+role+" username");
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
            UserValidator userValidator=new UserValidator();
            if(!userValidator.validateUpdate(col,newValue)){
                JOptionPane.showMessageDialog(null,userValidator.getFormattedErrors());
                return;
            }
            userService.updateUser(id, userTableMapper.getColumn(col), newValue);
            userTableMapper.setUsers(userService.findAll());
            adminView.setEmplTable(userTableMapper.formatUserTable());
            addActivity(" updated "+userTableMapper.getColumn(col)+"= "+newValue+" of user with id "+id);
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
            addActivity(" deleted user with id "+id);
        }
    }

    private class ActivityButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Date fromDate = null;
            Date toDate = null;
            int row = adminView.getRowClicked();
            userTableMapper.setUsers(userService.findAll());
            Long id = userTableMapper.getID(row);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                fromDate = sdf.parse(adminView.getFromDate());
                toDate = sdf.parse(adminView.getToDate());
            } catch (java.text.ParseException e1) {
                e1.printStackTrace();
            }
            System.out.println(fromDate);
            System.out.println(toDate);
            String descriptions = activityService.formatActivities(activityService.findByUserId(id, fromDate, toDate));
            adminView.showActivities(descriptions);
        }
    }

    private void addActivity(String description){
        Activity activity=new ActivityBuilder()
                .setDescription("User "+loggedUser.getUsername()+" : "+description)
                .setDate(new Date())
                .setUserId(loggedUser.getId())
                .build();
        activityService.addActivity(activity);
    }

    public void showUI() {
        addActivity(" logged in");
        adminView.setVisible(true);
    }
    public void hideUI(){
        addActivity(" logged off.");
        adminView.setVisible(false);
    }
    public void setWindowListener(WindowAdapter windowAdapter){adminView.addWindowListener(windowAdapter);}

    public void setLoggedUser(User loggedUser){
        this.loggedUser = loggedUser;
        System.out.println(loggedUser.getUsername());
    }

}
