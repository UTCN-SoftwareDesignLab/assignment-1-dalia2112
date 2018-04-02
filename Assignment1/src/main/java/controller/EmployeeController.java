package controller;

import database.Constants;
import mapper.AccountTableMapper;
import mapper.BillTableMapper;
import mapper.ClientTableMapper;
import model.*;

import model.builder.ActivityBuilder;
import model.builder.ClientBuilder;
import model.builder.AccountBuilder;
import model.validation.AccountValidator;
import model.validation.ClientValidator;
import service.account.AccountService;
import service.activity.ActivityService;
import service.bill.BillService;
import service.client.ClientService;
import view.EmployeeView;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class EmployeeController {

    private User loggedUser;
    private ActivityService activityService;

    private EmployeeView employeeView;
    private ClientService clientService;
    private AccountService accountService;
    private BillService billService;
    private AccountTableMapper accountTableMapper;
    private BillTableMapper billTableMapper;
    private ClientTableMapper clientTableMapper;

    public EmployeeController(EmployeeView employeeView, ClientService clientService, AccountService accountService, ActivityService activityService, BillService billService) {
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
        employeeView.setAccTableMouseListener(new AccTableMouseListener());
        employeeView.setVisible(false);
        accountTableMapper = new AccountTableMapper();
        setAccToTransfCombo(accountService.findAll());
        employeeView.addOwnerCombo(clientService.setOwnerCombo());
        billTableMapper = new BillTableMapper();
        clientTableMapper = new ClientTableMapper();
        this.activityService = activityService;
        this.billService = billService;
    }


    private void setAccToTransfCombo(List<Account> data) {
        List<String> accountsAndOwners = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            String nameOwner = clientService.findById(accountTableMapper.getAccountOwnerId(data, i)).getName();
            String accountAndOwner = accountTableMapper.getAccountId(data, i) + " " + accountTableMapper.getAccountAmount(data, i) + " " + nameOwner;
            accountsAndOwners.add(accountAndOwner);
        }
        employeeView.setAccToTransfComb(accountsAndOwners);
    }

    private class UpdateButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int row = employeeView.getRowClicked();
            int col = employeeView.getColClicked();
            Long id = clientTableMapper.getClientId(clientService.findAll(), row);
            String newValue = String.valueOf(employeeView.getTable().getValueAt(row, col));
            ClientValidator clientValidator = new ClientValidator();
            if (!clientValidator.validateUpdate(col, newValue)) {
                JOptionPane.showMessageDialog(null, clientValidator.getFormattedErrors());
                return;
            }
            clientService.updateClient(id, clientTableMapper.getColumnName(col), newValue);
            clientTableMapper.updateClient(clientService.findAll(), row, col, newValue);
            employeeView.setTable(clientTableMapper.formatClientTable(clientService.findAll()));
            employeeView.addOwnerCombo(clientService.setOwnerCombo());
            addActivity(Constants.Activities.UPDATE_CLIENT + id);
        }
    }

    private class DeleteButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int row = employeeView.getRowClicked();
            Long id = clientTableMapper.getClientId(clientService.findAll(), row);
            clientService.deleteClient(id);
            employeeView.setTable(clientTableMapper.formatClientTable(clientService.findAll()));
            clientService.setOwnerCombo();
            employeeView.addOwnerCombo(clientService.setOwnerCombo());
            addActivity(Constants.Activities.DELETE_CLIENT + id);
        }
    }

    private class ViewButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (employeeView.getNameorId().chars().allMatch(Character::isDigit) && !employeeView.getNameorId().equalsIgnoreCase("")) {
                List<Client> clientsList = new ArrayList<>();
                Client client = clientService.findById(Long.parseLong(employeeView.getNameorId()));
                clientsList.add(client);
//                clientTableMapper.setClients(clientsList);
                employeeView.setTable(clientTableMapper.formatClientTable(clientService.findAll()));
                addActivity(Constants.Activities.VIEW_CLIENT + employeeView.getNameorId());
            } else {
//                clientTableMapper.setClients(clientService.findAll());
                employeeView.setTable(clientTableMapper.formatClientTable(clientService.findAll()));
                employeeView.addOwnerCombo(clientService.setOwnerCombo());
                addActivity(Constants.Activities.VIEW_CLIENT + " : all clients");
            }
        }
    }

    private class AddButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String clientname = employeeView.getClientName();
            String clientAddress = employeeView.getClientAddress();
            String clientIDcardString = employeeView.getClientId_card_nr();
            String clientPersNumCodeString = employeeView.getClientPersNr();
            long clientPersNum = 0, clientIdCardNr = 0;

            ClientValidator clientValidator = new ClientValidator();

            if (!clientValidator.validateIdCardNr(clientIDcardString) || !clientValidator.validatePersNumCode(clientPersNumCodeString)) {
                JOptionPane.showMessageDialog(null, clientValidator.getFormattedErrors());
                return;
            }
            clientPersNum = Long.parseLong(clientPersNumCodeString);
            clientIdCardNr = Long.parseLong(clientIDcardString);
            Client c = new ClientBuilder()
                    .setName(clientname)
                    .setIdCard(clientIdCardNr)
                    .setPersNumCode(clientPersNum)
                    .setAddress(clientAddress)
                    .build();
            clientService.save(c);
//            clientTableMapper.setClients(clientService.findAll());
            employeeView.setTable(clientTableMapper.formatClientTable(clientService.findAll()));
            employeeView.addOwnerCombo(clientService.setOwnerCombo());
            addActivity(" created  customer " + c.getName());
        }
    }

    private class UpdateAccountListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int row = employeeView.getRowAccClicked();
            int col = employeeView.getColAccClicked();
            String idd = String.valueOf(accountTableMapper.getAccountId(accountService.findAll(), row));
            Long id = Long.parseLong(idd);
            String newValue = String.valueOf(employeeView.getAccTable().getValueAt(row, col));
            AccountValidator accountValidator = new AccountValidator();
            if (!accountValidator.validateUpdate(col, newValue)) {
                JOptionPane.showMessageDialog(null, accountValidator.getFormattedErrors());
                employeeView.setAccTable(Constants.Columns.ACCOUNT_COLS, accountTableMapper.formatAccountTable(accountService.findAll()));
                return;
            }
            accountService.updateAccount(id, accountTableMapper.getColumnName(col), newValue);
            accountTableMapper.updateAccountFromTable(accountService.findAll(), row, col, newValue);
            employeeView.setAccTable(Constants.Columns.ACCOUNT_COLS, accountTableMapper.formatAccountTable(accountService.findAll()));
            setAccToTransfCombo(accountService.findAll());
            addActivity(Constants.Activities.UPDATE_ACCOUNT + id);
        }
    }

    private class AddAccountButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String type = employeeView.getTypeCombo();
            long ownerid = getIDFromCombos(employeeView.getOwnerCombo());
            String amount = employeeView.getAmount();
            AccountValidator accountValidator = new AccountValidator();
            if (!accountValidator.validateAmount(amount)) {
                JOptionPane.showMessageDialog(null, accountValidator.getFormattedErrors());
                return;
            }
            Account account = new AccountBuilder()
                    .setType(type)
                    .setAmount(Float.parseFloat(amount))
                    .setOwner(ownerid)
                    .build();

            accountService.save(account);
            employeeView.setAccTable(Constants.Columns.ACCOUNT_COLS, accountTableMapper.formatAccountTable(accountService.findAll()));
            setAccToTransfCombo(accountService.findAll());
            addActivity(Constants.Activities.CREATE_ACCOUNT + ownerid);
        }
    }

    private class ViewAccountButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            employeeView.setAccTable(Constants.Columns.ACCOUNT_COLS, accountTableMapper.formatAccountTable(accountService.findAll()));
            addActivity(Constants.Activities.VIEW_ACCOUNT);
        }
    }

    private class DeleteAccountButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            int row = employeeView.getRowAccClicked();
            Long id = accountTableMapper.getAccountId(accountService.findAll(),row);
            accountService.deleteAccount(id);
            employeeView.setAccTable(Constants.Columns.ACCOUNT_COLS, accountTableMapper.formatAccountTable(accountService.findAll()));
            addActivity(Constants.Activities.DELETE_ACCOUNT + id);
        }
    }

    private class TransferButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //FIND client from whose account will be transferred money

            long ownerId = getIDFromCombos(employeeView.getOwnerCombo());
            employeeView.setAccTable(Constants.Columns.ACCOUNT_COLS, accountTableMapper.formatAccountTable(accountService.findByOwner(ownerId)));

            //find the clicked account's id
            int row = employeeView.getRowAccClicked();
            long accId1 = accountTableMapper.getAccountId(accountService.findByOwner(ownerId),row);
            Account account1 = accountService.findById(accId1);
            //choose the 2nd account from combo box
            long accId2 = getIDFromCombos(employeeView.getAccToTransfComb());
            String amountToTransfer = employeeView.getAmount();
            if (accId1 == accId2) {
                JOptionPane.showMessageDialog(null, "Cannot transfer to the same account");
                return;
            }
            AccountValidator accountValidator = new AccountValidator();
            if (!accountValidator.validateAmount(amountToTransfer)) {
                JOptionPane.showMessageDialog(null, accountValidator.getFormattedErrors());
                return;
            }
            float amountToTransferr = Float.parseFloat(amountToTransfer);
            if (!accountValidator.validateTransfSum(account1.getAmount(), amountToTransferr)) {
                JOptionPane.showMessageDialog(null, accountValidator.getFormattedErrors());
                return;
            }
            accountService.transferMoney(accId1, accId2, amountToTransferr);
            employeeView.setAccTable(Constants.Columns.ACCOUNT_COLS, accountTableMapper.formatAccountTable(accountService.findAll()));
            setAccToTransfCombo(accountService.findAll());
            addActivity(Constants.Activities.TRANSFER_MONEY1 + accId1 + Constants.Activities.TRANSFER_MONEY2 + accId2 + " " + amountToTransfer + " lei.");
        }
    }

    private class OwnerComboActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            long id = getIDFromCombos(employeeView.getOwnerCombo());
            employeeView.setAccTable(Constants.Columns.ACCOUNT_COLS, accountTableMapper.formatAccountTable(accountService.findByOwner(id)));
        }
    }

    private class ShowBillsButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            long ownerId = getIDFromCombos(employeeView.getOwnerCombo());
            employeeView.setBillTable(billTableMapper.formatBillTable(billService.findBillByOwner(ownerId)));
            accountTableMapper.formatAccountTable(accountService.findByOwner(ownerId));
            setAccToTransfCombo(accountService.findByOwner(ownerId));
        }
    }

    private class PayBillsButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            long ownerId = getIDFromCombos(employeeView.getOwnerCombo());
            long accId = getIDFromCombos(employeeView.getAccToTransfComb());
            Account account = accountService.findById(accId);
            String billToPayCode = billTableMapper.getBillCode(billService.findBillByOwner(ownerId), employeeView.getRowAccClicked());
            Bill bill = billService.findBillByCode(billToPayCode);

            AccountValidator accountValidator = new AccountValidator();
            if (!accountValidator.validateTransfSum(account.getAmount(), bill.getPrice())) {
                JOptionPane.showMessageDialog(null, accountValidator.getFormattedErrors());
                return;
            }
            billService.payBill(account, billToPayCode);

            employeeView.setBillTable(billTableMapper.formatBillTable(billService.findBillByOwner(ownerId)));
            setAccToTransfCombo(accountService.findAll());  //update accounts in combo box after payment
            addActivity(Constants.Activities.PAY_BILL1 + billToPayCode + Constants.Activities.PAY_BILL2 + ownerId + Constants.Activities.PAY_BILL3 + accId);
        }
    }


    private class AccTableMouseListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            employeeView.setRowAccClicked(employeeView.getAccTable().getSelectedRow());
            super.mouseClicked(e);
        }
    }

    public void showUI() {
        addActivity(Constants.Activities.LOGIN);
        employeeView.setVisible(true);
    }

    public void hideUI() {
        addActivity(Constants.Activities.LOGOUT);
        employeeView.setVisible(false);
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    public void setWindowListener(WindowAdapter windowAdapter) {
        employeeView.addWindowListener(windowAdapter);
    }

    public long getIDFromCombos(String content) {
        String[] tokens = content.split(" ");
        return Long.parseLong(tokens[0]);
    }

    private void addActivity(String description) {
        Activity activity = new ActivityBuilder()
                .setDescription("User " + loggedUser.getUsername() + " : " + description)
                .setDate(new Date())
                .setUserId(loggedUser.getId())
                .build();
        activityService.addActivity(activity);
    }

}
