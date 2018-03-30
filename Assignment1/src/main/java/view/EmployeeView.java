package view;

import database.Constants;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.List;
import java.util.Vector;

public class EmployeeView extends JDialog {
    private JPanel contentPane;
    private JTable table;
    private JButton updateClientButton;
    private JButton addClientButton;
    private JButton viewClientButton;
    private JScrollPane scroll;
    private JButton deleteClientButton;
    private JTextField name;
    private JTextField idcardnr;
    private JTextField persnum;
    private JTextField address;
    private JTabbedPane tabbedPane;
    private JPanel clientTab;
    private JPanel accountTab;
    private JButton viewAccountBtn;
    private JButton addAccountBtn;
    private JButton updateAccountBtn;
    private JButton deleteAccountBtn;
    private JTable accTable;
    private JTextField amount;
    private JComboBox ownerCombo;
    private JComboBox typeCombo;
    private JButton transferMoneyButton;
    private JComboBox accToTransfComb;
    private JButton payBillsButton;
    private JButton showBillsButton;
    private int rowClicked;
    private int colClicked;
    private int rowAccClicked;
    private int colAccClicked;


    public EmployeeView() {

        tabbedPane.addTab("Client", clientTab);
        tabbedPane.addTab("Account", accountTab);
        contentPane.setSize(800, 315);
        setSize(900, 700);
        setContentPane(contentPane);
        scroll.setSize(500, 500);
        clientTab.setSize(200, 200);
        setModal(true);
        setResizable(false);
        setVisible(false);
        setTypeCombo();
//        scroll.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                super.mouseClicked(e);
//            }
//        });
    }

    public void setRowAccClicked(int row){
        rowAccClicked=row;
    }

    public String getNameorId() {
        return name.getText();
    }

    public void setOwnerComboActionListener(ActionListener a) {
        ownerCombo.addActionListener(a);
    }

    public int getRowClicked() {

        return table.getSelectedRow();
    }

    public int getColClicked() {
        return table.getSelectedColumn();
    }


    public int getRowAccClicked() {
        return rowAccClicked;
    }


    public int getColAccClicked() {
        return accTable.getSelectedColumn();
    }

    public void setTypeCombo() {
        typeCombo.addItem(Constants.Columns.PERSONAL);
        typeCombo.addItem(Constants.Columns.REAL);
        typeCombo.addItem(Constants.Columns.NOMINAL);
        typeCombo.setSize(50, typeCombo.getPreferredSize().height);
    }

    public String getAccToTransfComb() {
        return accToTransfComb.getSelectedItem().toString();
    }

    public void setAccToTransfComb(List<String> accounts) {
        DefaultComboBoxModel defaultComboBoxModel = new DefaultComboBoxModel();
        for (String accountAndOwner : accounts) {
            defaultComboBoxModel.addElement(accountAndOwner);
        }
        accToTransfComb.setModel(defaultComboBoxModel);
        accToTransfComb.setSize(50, accToTransfComb.getPreferredSize().height);
    }

    public void addOwnerCombo(DefaultComboBoxModel own) {
        ownerCombo.setModel(own);
    }

    public String getOwnerCombo() {
        return ownerCombo.getSelectedItem().toString();
    }

    public String getTypeCombo() {
        return typeCombo.getSelectedItem().toString();
    }

    public JTable getTable() {
        return table;
    }

    public JTable getAccTable() {
        return accTable;
    }

    public void setTable(Vector<Vector<String>> data1) {
        DefaultTableModel dtm = new DefaultTableModel();
        dtm.setColumnIdentifiers(Constants.Columns.CLIENT_COLS);
        table.setModel(dtm);
        for (Vector<String> c : data1) {
            dtm.addRow(c);
        }
    }

    public void setBillTable(Vector<Vector<String>> data1) {
        DefaultTableModel dtm = new DefaultTableModel();
        dtm.setColumnIdentifiers(Constants.Columns.BILL_COLS);
        accTable.setModel(dtm);
        for (Vector<String> c : data1) {
            dtm.addRow(c);
        }
    }

    public void setAccTable(String[] columns, Vector<Vector<String>> data) {
        DefaultTableModel dtm = new DefaultTableModel();
        dtm.setColumnIdentifiers(columns);

        for (Vector<String> c : data) {
            dtm.addRow(c);
        }
        accTable.setModel(dtm);
    }

    public static void main(String[] args) {
        EmployeeView ev = new EmployeeView();
        ev.setVisible(true);
        System.out.println(Long.valueOf("3"));
    }

    public String getClientName() {
        return name.getText();
    }

    public String getClientAddress() {
        return address.getText();
    }

    public float getAmount() {
        return Long.parseLong(amount.getText());
    }

    public Long getClientId_card_nr() {
        return Long.parseLong(idcardnr.getText());
    }

    public String getClientPersNr() {
        return persnum.getText();
    }

    public void setUpdateButtonListener(ActionListener updateButtonListener) {
        updateClientButton.addActionListener(updateButtonListener);
    }

    public void setAddButtonListener(ActionListener addButtonListener) {
        addClientButton.addActionListener(addButtonListener);
    }

    public void setViewButtonListener(ActionListener viewClientListener) {
        viewClientButton.addActionListener(viewClientListener);
    }

    public void setDeleteButtonListener(ActionListener deleteClientListener) {
        deleteClientButton.addActionListener(deleteClientListener);
    }

    public void setTransferButtonListener(ActionListener transferClientListener) {
        transferMoneyButton.addActionListener(transferClientListener);
    }

    public void setTableMouseListener(MouseAdapter m) {
        table.addMouseListener(m);
    }

    public void setViewAccountButtonListener(ActionListener viewAccounListener) {
        viewAccountBtn.addActionListener(viewAccounListener);
    }

    public void setAddAccountButtonListener(ActionListener addAccounListener) {
        addAccountBtn.addActionListener(addAccounListener);
    }

    public void setUpdateAccountButtonListener(ActionListener updateAccounListener) {
        updateAccountBtn.addActionListener(updateAccounListener);
    }

    public void setDeleteAccountButtonListener(ActionListener deleteAccounListener) {
        deleteAccountBtn.addActionListener(deleteAccounListener);
    }

    public void setAccTableMouseListener(MouseAdapter m) {
        accTable.addMouseListener(m);
    }

    public void setShowBillsButtonListener(ActionListener showBillsButtonListener) {
        showBillsButton.addActionListener(showBillsButtonListener);
    }

    public void setPayBillsButtonListener(ActionListener payBillsButtonListener) {
        payBillsButton.addActionListener(payBillsButtonListener);
    }

}
