package controller;

import database.Constants;
import mapper.AccountTableMapper;
import mapper.BillTableMapper;
import mapper.ClientTableMapper;
import model.Account;
import model.Client;

import model.builder.ClientBuilder;
import model.builder.AccountBuilder;
import model.validation.AccountValidator;
import model.validation.ClientValidator;
import service.account.AccountService;
import service.client.ClientService;
import view.EmployeeView;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;


public class EmployeeController {

    private EmployeeView employeeView;
    private ClientService clientService;
    private AccountService accountService;
    private AccountTableMapper accountTableMapper;
    private BillTableMapper billTableMapper;
    private ClientTableMapper clientTableMapper;

    public EmployeeController(EmployeeView employeeView, ClientService clientService, AccountService accountService) {
        this.employeeView = employeeView;
        this.clientService = clientService;
        this.accountService = accountService;
        employeeView.setUpdateButtonListener(new UpdateButtonListener());
        employeeView.setAddButtonListener(new AddButtonListener());
        employeeView.setViewButtonListener(new ViewButtonListener());
        employeeView.setDeleteButtonListener(new DeleteButtonListener());
        employeeView.setUpdateAccountButtonListener(new UpdateAccountListener());
        employeeView.setAddAccountButtonListener(new AddAccountButtonListener());
        employeeView.setViewAccountButtonListener(new ViewAccountButtonListener());
        employeeView.setDeleteAccountButtonListener(new DeleteAccountButtonListener());
        employeeView.setTransferButtonListener(new TransferButtonListener());
        employeeView.setOwnerComboActionListener(new OwnerComboActionListener());
        employeeView.setShowBillsButtonListener(new ShowBillsButtonListener());
        employeeView.setPayBillsButtonListener(new PayBillsButtonListener());
        employeeView.setVisible(false);
        accountTableMapper = new AccountTableMapper();
        setAccToTransfCombo(accountService.findAll());
        employeeView.addOwnerCombo(clientService.setOwnerCombo());
        billTableMapper = new BillTableMapper();
        clientTableMapper = new ClientTableMapper();

    }


    private void setAccToTransfCombo(List<Account> data) {
        List<String> accountsAndOwners = new ArrayList<>();
        accountTableMapper.setAccounts(data);
        for (int i = 0; i < accountTableMapper.tableSize(); i++) {
            String nameOwner = clientService.findById(accountTableMapper.getAccountOwnerId(i)).getName();
            String accountAndOwner = accountTableMapper.getAccountId(i) + " " + accountTableMapper.getAccountAmount(i) + " " + nameOwner;
            accountsAndOwners.add(accountAndOwner);
        }
        employeeView.setAccToTransfComb(accountsAndOwners);
    }

    private class UpdateButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int row = employeeView.getRowClicked();
            int col = employeeView.getColClicked();
            clientTableMapper.setClients(clientService.findAll());
            Long id = clientTableMapper.getClientId(row);
            String newValue = String.valueOf(employeeView.getTable().getValueAt(row, col));
            clientService.updateClient(id, col, newValue);
            clientTableMapper.updateClient(row,col,newValue);
            if (employeeView.getNameorId().chars().allMatch(Character::isDigit) && !employeeView.getNameorId().equalsIgnoreCase("")){
                List<Client> clientsList=new ArrayList<>();
                Client client=clientService.findById(id);
                clientsList.add(client);
                clientTableMapper.setClients(clientsList);
                employeeView.setTable(clientTableMapper.formatClientTable());
            }
            else{
                clientTableMapper.setClients(clientService.findAll());
            }
            employeeView.addOwnerCombo(clientService.setOwnerCombo());
        }
    }

    private class DeleteButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int row = employeeView.getRowClicked();
            clientTableMapper.setClients(clientService.findAll());
            Long id = clientTableMapper.getClientId(row);
            clientService.deleteClient(id);
            clientTableMapper.deleteClientFromTable(row);
            employeeView.setTable(clientTableMapper.formatClientTable());
            clientService.setOwnerCombo();
            employeeView.addOwnerCombo(clientService.setOwnerCombo());
        }
    }

    private class ViewButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            clientTableMapper.setClients(clientService.findAll());
            employeeView.setTable(clientTableMapper.formatClientTable());
            employeeView.addOwnerCombo(clientService.setOwnerCombo());
        }
    }

    private class AddButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String clientname = employeeView.getClientName();
            String clientAddress = employeeView.getClientAddress();
            Long clientIDcard = employeeView.getClientId_card_nr();
            String clientPersNumCodeString = employeeView.getClientPersNr();
            long clientPersNum = 0;
            if (clientPersNumCodeString.chars().allMatch(Character::isDigit))
                clientPersNum = Long.parseLong(clientPersNumCodeString);
            else {
                JOptionPane.showMessageDialog(null, "Personal numerical code must contain only digits!");
            }
            Client c = new ClientBuilder()
                    .setName(clientname)
                    .setIdCard(clientIDcard)
                    .setPersNumCode(clientPersNum)
                    .setAddress(clientAddress)
                    .build();
            ClientValidator clientValidator = new ClientValidator(c);
            clientValidator.validate();
            if (!clientValidator.getErrors().isEmpty()) {
                JOptionPane.showMessageDialog(null, clientValidator.getErrors());
                return;
            }
            clientService.save(c);
            clientTableMapper.setClients(clientService.findAll());
            employeeView.setTable(clientTableMapper.formatClientTable());
            employeeView.addOwnerCombo(clientService.setOwnerCombo());
        }
    }

    private class UpdateAccountListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int row = employeeView.getRowAccClicked();
            int col = employeeView.getColAccClicked();
            String idd = String.valueOf(accountTableMapper.getAccountId(row));
            Long id = Long.parseLong(idd);
            String nv = String.valueOf(employeeView.getAccTable().getValueAt(row, col));
            accountService.updateAccount(id, accountTableMapper.getColumnName(col), nv);
            accountTableMapper.updateAccountFromTable(row, col, nv);
            employeeView.setAccTable(Constants.Columns.ACCOUNT_COLS, accountTableMapper.formatAccountTable());
        }
    }

    private class AddAccountButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String type = employeeView.getTypeCombo();
            long ownerid = getIDFromCombos(employeeView.getOwnerCombo());
            float amount = employeeView.getAmount();
            Account a = new AccountBuilder()
                    .setType(type)
                    .setAmount(amount)
                    .setOwner(ownerid)
                    .build();
            AccountValidator accountValidator = new AccountValidator();
            if (accountValidator.validateTransfSum(amount, 0, false)) {
                accountService.save(a);
                accountTableMapper.setAccounts(accountService.findAll());
                employeeView.setAccTable(Constants.Columns.ACCOUNT_COLS, accountTableMapper.formatAccountTable());
                setAccToTransfCombo(accountService.findAll());
            } else JOptionPane.showMessageDialog(employeeView, accountValidator.getErrors().toString());
        }
    }

    private class ViewAccountButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            employeeView.setAccTable(Constants.Columns.ACCOUNT_COLS, accountTableMapper.formatAccountTable());
        }
    }

    private class DeleteAccountButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            int row = employeeView.getRowAccClicked();
            accountTableMapper.setAccounts(accountService.findAll());
            Long id = accountTableMapper.getAccountId(row);
            accountService.deleteAccount(id);
            accountTableMapper.deleteAccountFromTable(row);
            employeeView.setAccTable(Constants.Columns.ACCOUNT_COLS, accountTableMapper.formatAccountTable());
        }
    }


    private class TransferButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //FIND client from whose account will be transferred money
            long ownerId = getIDFromCombos(employeeView.getOwnerCombo());
            accountTableMapper.setAccounts(accountService.findByOwner(ownerId));
            employeeView.setAccTable(Constants.Columns.ACCOUNT_COLS, accountTableMapper.formatAccountTable());

            //find the clicked account's id
            int row = employeeView.getRowAccClicked();
            long accId1 = accountTableMapper.getAccountId(row);

            //choose the 2nd account from combo box
            long accId2 = getIDFromCombos(employeeView.getAccToTransfComb());

            float amount = employeeView.getAmount();


            accountService.transferMoney(accId1, accId2, amount);
            accountTableMapper.setAccounts(accountService.findAll());
            employeeView.setAccTable(Constants.Columns.ACCOUNT_COLS, accountTableMapper.formatAccountTable());
            setAccToTransfCombo(accountService.findAll());
        }
    }

    private class OwnerComboActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            long id = getIDFromCombos(employeeView.getOwnerCombo());
            accountTableMapper.setAccounts(accountService.findByOwner(id));
            employeeView.setAccTable(Constants.Columns.ACCOUNT_COLS, accountTableMapper.formatAccountTable());
        }
    }

    private class ShowBillsButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            long ownerId = getIDFromCombos(employeeView.getOwnerCombo());
            billTableMapper.setBills(accountService.findBillByOwner(ownerId));
            employeeView.setBillTable(billTableMapper.formatBillTable());
            accountTableMapper.setAccounts(accountService.findByOwner(ownerId));
            setAccToTransfCombo(accountService.findByOwner(ownerId));
        }
    }

    private class PayBillsButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            long ownerId = getIDFromCombos(employeeView.getOwnerCombo());
            billTableMapper.setBills(accountService.findBillByOwner(ownerId));
            long accId = getIDFromCombos(employeeView.getAccToTransfComb());
            String billToPayCode = billTableMapper.getBillCode(employeeView.getRowAccClicked());
            accountService.payBill(accId, billToPayCode);

            employeeView.setBillTable(billTableMapper.formatBillTable());
            accountTableMapper.setAccounts(accountService.findByOwner(ownerId));
            setAccToTransfCombo(accountService.findByOwner(ownerId));
        }
    }

    public void showUI() {
        employeeView.setVisible(true);
    }

    public long getIDFromCombos(String content){
        String[] tokens=content.split(" ");
        return Long.parseLong(tokens[0]);
    }

}
