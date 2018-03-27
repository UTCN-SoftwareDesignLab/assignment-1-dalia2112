package controller;

import model.Account;
import model.Client;

import java.awt.*;
import java.util.*;
import model.builder.ClientBuilder;
import model.builder.AccountBuilder;
import model.validation.AccountValidator;
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
        setOwnerCombo();
    }

    private  void writeClientTable(){
        if(employeeView.getNameorId().getText().equals("")) {
            Vector<Vector<String>> data = clientService.getAllClientsTable();
            employeeView.setTable(data);
        }
        else{
            Vector<Vector<String>> data= new Vector<>();
            Vector<String> d=new Vector<>();
            Long id=Long.parseLong(employeeView.getNameorId().getText());
            Client  u=clientService.findById(id);
            d.add(u.getId().toString());
            d.add(u.getName());
            d.add(u.getId_card_nr().toString());
            d.add(u.getPers_num_code().toString());
            d.add(u.getAddress());
            data.add(d);
            employeeView.setTable(data);
        }
    }

    private  void writeAccountTable(List<Account> data){
//        Vector<Vector<String>> data = accountService.getAllAccountsTable();
        Vector<Vector<String>> tabl=accountService.getAllAccountsTable(data);
        employeeView.setAccTable(tabl);
        //setOwnerCombo();
        setAccToTransfCombo();
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
            writeAccountTable(accountService.findAll());
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
            AccountValidator accountValidator=new AccountValidator(a);
            if(accountValidator.validate(0)) {
                accountService.save(a);
                writeAccountTable(accountService.findAll());
            }
            else JOptionPane.showMessageDialog(employeeView,accountValidator.getErrors().toString());
        }
    }

    private class ViewAccountButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            writeAccountTable(accountService.findAll());
        }
    }

    private class DeleteAccountButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            int row=employeeView.getRowAccClicked();
            String idd=String.valueOf(employeeView.getAccTable().getModel().getValueAt(row,0));
            Long id=Long.parseLong(idd);
            accountService.deleteAccount(id);
            writeAccountTable(accountService.findAll());
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
            //FIND client from whose account will be transfered money
            int a=0;
            long ownerid1=Long.parseLong(employeeView.getOwnerCombo().getSelectedItem().toString().substring(0,1));
            writeAccountTable(accountService.findByOwner(ownerid1));   //list all the accounts
//            System.out.println("Owner id= "+ownerid1);

            //find the clicked account's id
            int row=employeeView.getRowAccClicked();
            String idd=String.valueOf(employeeView.getAccTable().getModel().getValueAt(row,0));
            long accId1=Long.parseLong(idd);

            //choose the 2nd account from combo box
//            setAccToTransfCombo();
            JOptionPane.showMessageDialog(null,employeeView.getAccToTransfComb().getSelectedItem().toString());
            long accId2=Long.parseLong(employeeView.getAccToTransfComb().getSelectedItem().toString().substring(0,1));
            float amount=employeeView.getAmount();

            accountService.transferMoney(accId1,accId2,amount);
            writeAccountTable(accountService.findAll());
        }
    }

    private class OwnerComboActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            long id=Long.parseLong(employeeView.getOwnerCombo().getSelectedItem().toString().substring(0,1));
            writeAccountTable(accountService.findByOwner(id));
        }
    }
    public void showUI(){
        employeeView.setVisible(true);
    }

}
