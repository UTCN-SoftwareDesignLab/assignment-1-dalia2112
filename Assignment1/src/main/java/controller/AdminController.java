package controller;

import database.Constants;
import model.Role;
import model.User;
import model.builder.UserBuilder;
import model.validation.Notification;
import repository.EntityNotFoundException;
import repository.security.RightsRolesRepository;
import service.user.AuthenticationService;
import service.user.UserService;
import view.AdminView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

public class AdminController {
    private AdminView adminView;
    private UserService userService;
    private AuthenticationService authenticationService;

    public AdminController(UserService userService,AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService=authenticationService;
        adminView=new AdminView();
        adminView.setViewButtonListener(new ViewButtonListener());
        adminView.setAddButtonListener(new AddButtonListener());
        adminView.setUpdateButtonListener(new UpdateButtonListener());
        adminView.setDeleteButtonListener(new DeleteButtonListener());
        adminView.setTableMouseListener(new TableMouseListener());
        adminView.setVisible(false);
    }

    private  void writeUserTable() {
        if(adminView.getIdTf().getText().equals("")) {
            Vector<Vector<String>> data = userService.getAllUserTable();
            adminView.setEmplTable(data);
        }
        else {
            Vector<Vector<String>> data= new Vector<>();
            Vector<String> d=new Vector<>();
            Long id=Long.parseLong(adminView.getIdTf().getText());
            User u=userService.findById(id);
            d.add(u.getId().toString());
            d.add(u.getUsername());
            d.add((u.getPassword()));
            d.add(u.getRoles().get(0).getRole());
            data.add(d);
            adminView.setEmplTable(data);
        }
    }

    private class ViewButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
                writeUserTable();
        }
    }

    private class AddButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = adminView.getUsernameTf().getText();
            String pass=adminView.getPassTf().getText();
            String role=adminView.getRolesCombo().getSelectedItem().toString();
            Notification<Boolean> registerNotification = authenticationService.registerUser(username,pass,role);
            if (registerNotification.hasErrors()) {
                JOptionPane.showMessageDialog(adminView.getContentPane(), registerNotification.getFormattedErrors());
            } else {
                if (!registerNotification.getResult()) {
                    JOptionPane.showMessageDialog(adminView.getContentPane(), "Registration not successful, please try again later.");
                } else {
                    JOptionPane.showMessageDialog(adminView.getContentPane(), "Registration successful( "+role+" ) !");

                }
            }
            writeUserTable();
        }
    }

    private class UpdateButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int row=adminView.getRowClicked();
            int col=adminView.getColClicked();
            String idd=String.valueOf(adminView.getEmplTable().getModel().getValueAt(row,0));
            Long id=Long.parseLong(idd);
            String nv= String.valueOf(adminView.getEmplTable().getValueAt(row,col));
            System.out.println("New val "+nv);
            userService.updateUser(id,col,nv);
            writeUserTable();
        }
    }

    private class DeleteButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            int row=adminView.getRowClicked();
            String idd=String.valueOf(adminView.getEmplTable().getModel().getValueAt(row,0));
            Long id=Long.parseLong(idd);
            userService.deleteUser(id);
            writeUserTable();
        }
    }

    private class TableMouseListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {

            adminView.setRowClicked(adminView.getEmplTable().getSelectedRow());
            adminView.setColClicked(adminView.getEmplTable().getSelectedColumn());
        }
    }
    public void showUI(){
        adminView.setVisible(true);
    }

}
