package controller;

import model.Account;
import model.Client;
import model.builder.ClientBuilder;
import model.builder.AccountBuilder;
import service.account.AccountService;
import service.client.ClientService;
import view.EmployeeView;

import javax.swing.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.Vector;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;


public class EmployeeController {

    private EmployeeView employeeView;
    private ClientService clientService;
    private AccountService accountService;

    public EmployeeController(ClientService clientService, AccountService accountService){
        this.employeeView = new EmployeeView();
        this.clientService=clientService;
        this.accountService=accountService;
        employeeView.setUpdateButtonListener(new UpdateButtonListener());
        employeeView.setAddButtonListener(new AddButtonListener());
        employeeView.setViewButtonListener(new ViewButtonListener());
        employeeView.setDeleteButtonListener(new DeleteButtonListener());
        employeeView.setTableMouseListener(new TableMouseListener());
        employeeView.setUpdateAccountButtonListener(new UpdateAccountListener());
        employeeView.setAddAccountButtonListener(new AddAccountButtonListener());
        employeeView.setViewAccountButtonListener(new ViewAccountButtonListener());
        employeeView.setAccTableMouseListener(new AccTableMouseListener());
        employeeView.setDeleteAccountButtonListener(new DeleteAccountButtonListener());
        employeeView.setVisible(false);
    }

    private  void writeClientTable(){
        Vector<Vector<String>> data = clientService.getAllClientsTable();
        employeeView.setTable(data);
    }

    private  void writeAccountTable(){
        Vector<Vector<String>> data = accountService.getAllAccountsTable();
        employeeView.setAccTable(data);
        setOwnerCombo();
    }

    private void setOwnerCombo(){
        Vector<Vector<String>> clients=clientService.getAllClientsTable();
        DefaultComboBoxModel dcm=new DefaultComboBoxModel();
        for(int i=0;i<clients.size();i++){
            String nm=clients.elementAt(i).elementAt(0)+" "+clients.elementAt(i).elementAt(1);
            dcm.addElement(nm);
        }
        employeeView.addOwnerCombo(dcm);

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
            writeClientTable();
        }
    }

    private class DeleteButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int row=employeeView.getRowClicked();
            String idd=String.valueOf(employeeView.getTable().getModel().getValueAt(row,0));
            Long id=Long.parseLong(idd);
            clientService.deleteClient(id);
            writeClientTable();
        }
    }

    private class ViewButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            writeClientTable();
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
            writeClientTable();
        }
    }

    private class UpdateAccountListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int row=employeeView.getRowAccClicked();
            int col=employeeView.getColAccClicked();
            String idd=String.valueOf(employeeView.getAccTable().getModel().getValueAt(row,0));
            Long id=Long.parseLong(idd);
            String nv= String.valueOf(employeeView.getAccTable().getValueAt(row,col));
            accountService.updateAccount(id,col,nv);
            writeAccountTable();
        }
    }

    private class AddAccountButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int row=employeeView.getRowAccClicked();
            int col=employeeView.getColAccClicked();
            String type=employeeView.getTypeCombo().getSelectedItem().toString();
            long ownerid=Long.parseLong(employeeView.getOwnerCombo().getSelectedItem().toString().substring(0,1));
            float amount=employeeView.getAmount();
            Account a=new AccountBuilder()
                    .setType(type)
                    .setAmount(amount)
                    .setDate(new Date())
                    .setOwner(ownerid)
                    .build();
            accountService.save(a);
            writeAccountTable();
        }
    }

    private class ViewAccountButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            writeAccountTable();
        }
    }

    private class DeleteAccountButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            int row=employeeView.getRowAccClicked();
            String idd=String.valueOf(employeeView.getAccTable().getModel().getValueAt(row,0));
            Long id=Long.parseLong(idd);
            accountService.deleteAccount(id);
            writeAccountTable();
        }
    }

    private class AccTableMouseListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {

            employeeView.setRowAccClicked(employeeView.getAccTable().getSelectedRow());
            employeeView.setColAccClicked(employeeView.getAccTable().getSelectedColumn());
        }
    }

    public void showUI(){
        employeeView.setVisible(true);
    }

}
