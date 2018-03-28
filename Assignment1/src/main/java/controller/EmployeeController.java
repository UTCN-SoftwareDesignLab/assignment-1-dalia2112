package controller;

import model.Account;
import model.Client;

import java.awt.*;
import java.util.*;
import model.builder.ClientBuilder;
import model.builder.AccountBuilder;
import model.validation.AccountValidator;
import model.validation.ClientValidator;
import service.account.AccountService;
import service.client.ClientService;
import view.EmployeeView;

import javax.swing.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.List;
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
        employeeView.setTransferButtonListener(new TransferButtonListener());
        employeeView.setOwnerComboActionListener(new OwnerComboActionListener());
        employeeView.setVisible(false);
        setAccToTransfCombo();
        employeeView.addOwnerCombo(clientService.setOwnerCombo());
    }


    private void setAccToTransfCombo(){
        Vector<Vector<String>> accounts=accountService.getAllAccountsTable(accountService.findAll());
        DefaultComboBoxModel dcm=new DefaultComboBoxModel();
        for(int i=0;i<accounts.size();i++){
            String nameOwner=clientService.findById(Long.parseLong(accounts.elementAt(i).elementAt(4))).getName();
            String nm=accounts.elementAt(i).elementAt(0)+" "+accounts.elementAt(i).elementAt(2)+" "+nameOwner;
            dcm.addElement(nm);
        }
        employeeView.setAccToTransfComb(dcm);
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
            employeeView.setTable(clientService.writeClientTable(employeeView.getNameorId()));
        }
    }

    private class DeleteButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int row=employeeView.getRowClicked();
            String idd=String.valueOf(employeeView.getTable().getModel().getValueAt(row,0));
            Long id=Long.parseLong(idd);
            clientService.deleteClient(id);
            employeeView.setTable(clientService.writeClientTable(employeeView.getNameorId()));
        }
    }

    private class ViewButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            employeeView.setTable(clientService.writeClientTable(employeeView.getNameorId()));
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
            ClientValidator clientValidator=new ClientValidator(c);
            clientValidator.validate();
            if(!clientValidator.getErrors().isEmpty()) {
                JOptionPane.showMessageDialog(null, clientValidator.getErrors());
                return;
            }
            clientService.save(c);
            employeeView.setTable(clientService.writeClientTable(employeeView.getNameorId()));
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
            employeeView.setAccTable(accountService.writeAccountTable(accountService.findAll()));
        }
    }

    private class AddAccountButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
//            int row=employeeView.getRowAccClicked();
//            int col=employeeView.getColAccClicked();
            String type=employeeView.getTypeCombo();
            long ownerid=Long.parseLong(employeeView.getOwnerCombo().substring(0,1));
            float amount=employeeView.getAmount();
            Account a=new AccountBuilder()
                    .setType(type)
                    .setAmount(amount)
                    .setOwner(ownerid)
                    .build();
            AccountValidator accountValidator=new AccountValidator();
            if(accountValidator.validateTransfSum(amount,0,false)) {
                accountService.save(a);
                employeeView.setAccTable(accountService.writeAccountTable(accountService.findAll()));
            }
            else JOptionPane.showMessageDialog(employeeView,accountValidator.getErrors().toString());
        }
    }

    private class ViewAccountButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            employeeView.setAccTable(accountService.writeAccountTable(accountService.findAll()));
        }
    }

    private class DeleteAccountButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            int row=employeeView.getRowAccClicked();
            String idd=String.valueOf(employeeView.getAccTable().getModel().getValueAt(row,0));
            Long id=Long.parseLong(idd);
            accountService.deleteAccount(id);
            employeeView.setAccTable(accountService.writeAccountTable(accountService.findAll()));
        }
    }

    private class AccTableMouseListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {

            employeeView.setRowAccClicked(employeeView.getAccTable().getSelectedRow());
            employeeView.setColAccClicked(employeeView.getAccTable().getSelectedColumn());
        }
    }

    private class TransferButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //FIND client from whose account will be transferred money
            long ownerid1=Long.parseLong(employeeView.getOwnerCombo().substring(0,1));
            employeeView.setAccTable(accountService.writeAccountTable(accountService.findByOwner(ownerid1)));

            //find the clicked account's id
            int row = employeeView.getRowAccClicked();
            String idd = String.valueOf(employeeView.getAccTable().getModel().getValueAt(row,0));
            long accId1=Long.parseLong(idd);

            //choose the 2nd account from combo box
            long accId2 = Long.parseLong(employeeView.getAccToTransfComb().substring(0,1));

            float amount=employeeView.getAmount();


            accountService.transferMoney(accId1,accId2,amount);
            employeeView.setAccTable(accountService.writeAccountTable(accountService.findAll()));
        }
    }

    private class OwnerComboActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            long id=Long.parseLong(employeeView.getOwnerCombo().substring(0,1));
            employeeView.setAccTable(accountService.writeAccountTable(accountService.findByOwner(id)));
        }
    }

    public void showUI(){
        employeeView.setVisible(true);
    }

}
