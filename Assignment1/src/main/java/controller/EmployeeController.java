package controller;

import model.Client;
import model.User;
import model.builder.ClientBuilder;
import model.validation.Notification;
import repository.EntityNotFoundException;
import repository.user.AuthenticationException;
import service.client.ClientService;
import view.EmployeeView;

import javax.print.attribute.standard.JobOriginatingUserName;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;


public class EmployeeController {

    private EmployeeView employeeView;
    private ClientService clientService;

    public EmployeeController(ClientService clientService){
        this.employeeView = new EmployeeView();
        this.clientService=clientService;
        employeeView.setUpdateButtonListener(new UpdateButtonListener());
        employeeView.setAddButtonListener(new AddButtonListener());
        employeeView.setViewButtonListener(new ViewButtonListener());
        employeeView.setVisible(false);
    }

    private class UpdateButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String clientname = employeeView.getClientName();
            System.out.println("client \n" + clientname);

            try {
                Client c = clientService.findByName(clientname);
            } catch (EntityNotFoundException e1) {
                e1.printStackTrace();
            }
        }
    }

    private class ViewButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            Vector<Vector<String>> data = clientService.getAllClientsTable();
            employeeView.setTable(data);
//            employeeView.setVisible(true);
//            System.out.println(data.toString());
//            System.out.println("size= "+data.size());
//            employeeView.getTable().setVisible(true);
        }
    }

    private class AddButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String clientname = employeeView.getClientName();
            System.out.println("client \n"+clientname);
            Client c=new ClientBuilder ()
                    .setID((long)0.0)
                    .setName("daldal")
                    .setIdCard((long)1.0)
                    .setPersNumCode((long)2.0)
                    .setAddress("fantanele")
                    .build();
            clientService.save(c);
        }
    }

    public void showUI(){
        employeeView.setVisible(true);
    }

}
