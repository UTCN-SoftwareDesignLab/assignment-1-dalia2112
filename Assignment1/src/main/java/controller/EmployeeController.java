package controller;

import model.Client;
import model.User;
import model.builder.ClientBuilder;
import model.validation.Notification;
import repository.EntityNotFoundException;
import repository.user.AuthenticationException;
import service.client.ClientService;
import view.EmployeeView;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;


public class EmployeeController {

    private EmployeeView employeeView;
    private ClientService clientService;

    public EmployeeController(EmployeeView employeeView,ClientService clientService){
        this.employeeView=employeeView;
        this.clientService=clientService;
        employeeView.setUpdateButtonListener(new UpdateButtonListener());
        employeeView.setViewButtonListener(new ViewButtonListener());
        employeeView.setAddButtonListener(new AddButtonListener());
    }

    private class UpdateButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String clientname = employeeView.getClientName();
            System.out.println("client \n"+clientname);

            try {
                Client c=clientService.findByName(clientname);
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
            employeeView.setVisible(true);
            //System.out.println(dtm.getDataVector());
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

}
