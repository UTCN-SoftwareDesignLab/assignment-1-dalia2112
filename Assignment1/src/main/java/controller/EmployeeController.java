package controller;

import model.Client;
import model.builder.ClientBuilder;
import service.client.ClientService;
import view.EmployeeView;

import java.awt.event.*;
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
        employeeView.setDeleteButtonListener(new DeleteButtonListener());
        employeeView.setTableMouseListener(new TableMouseListener());
        employeeView.setVisible(false);
    }

    private  void writeTable(){
        Vector<Vector<String>> data = clientService.getAllClientsTable();
        employeeView.setTable(data);
    }
    private class TableMouseListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {

            employeeView.setRowClicked(employeeView.getTable().getSelectedRow());
            employeeView.setColClicked(employeeView.getTable().getSelectedColumn());
        }
    }

    private class UpdateButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int row=employeeView.getRowClicked();
            int col=employeeView.getColClicked();
            String idd=String.valueOf(employeeView.getTable().getModel().getValueAt(row,0));
            Long id=Long.parseLong(idd);
            String nv= String.valueOf(employeeView.getTable().getValueAt(row,col));
            clientService.updateClient(id,col,nv);
            writeTable();
        }
    }

    private class DeleteButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int row=employeeView.getRowClicked();
            String idd=String.valueOf(employeeView.getTable().getModel().getValueAt(row,0));
            Long id=Long.parseLong(idd);
            clientService.deleteClient(id);
            writeTable();
        }
    }

    private class ViewButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            writeTable();
        }
    }

    private class AddButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String clientname = employeeView.getClientName();
            String clientAddress=employeeView.getClientAddress();
            Long clientIDcard=employeeView.getClientId_card_nr();
            Long clientPersNum=employeeView.getClientPersNr();
            Client c=new ClientBuilder ()
                    .setName(clientname)
                    .setIdCard(clientIDcard)
                    .setPersNumCode(clientPersNum)
                    .setAddress(clientAddress)
                    .build();
            clientService.save(c);
            writeTable();
        }
    }

    public void showUI(){
        employeeView.setVisible(true);
    }

}
