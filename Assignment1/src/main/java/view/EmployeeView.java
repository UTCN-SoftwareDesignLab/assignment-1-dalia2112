package view;

import controller.EmployeeController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
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
    private JLabel owner;
    private JComboBox typeCombo;
    private int rowClicked;
    private int colClicked;
    private int rowAccClicked;
    private int colAccClicked;

    private final Object[] cols={"Id","Name","Id_card_nr","Pers_num_code","Address"};
    private final Object[] accCols={"Id","Type","Amount","Date of creation","Owner ID"};

    public EmployeeView() {

        tabbedPane.addTab("Client",clientTab);
        tabbedPane.addTab("Account",accountTab);
        contentPane.setSize(500,315);
        setSize(735,600);
        setContentPane(contentPane);
        scroll.setSize(500,500);
        clientTab.setSize(200,200);
        setModal(true);
//        pack();
        setResizable(false);
        setVisible(false);
        setTypeCombo();
    }

    public int getRowClicked() {
        return rowClicked;
    }

    public int getColClicked() {
        return colClicked;
    }

    public void setRowClicked(int rowClicked) {
        this.rowClicked = rowClicked;
    }

    public void setColClicked(int colClicked) {
        this.colClicked = colClicked;
    }

    public int getRowAccClicked() {
        return rowAccClicked;
    }

    public void setRowAccClicked(int rowAccClicked) {
        this.rowAccClicked = rowAccClicked;
    }

    public int getColAccClicked() {
        return colAccClicked;
    }

    public void setColAccClicked(int colAccClicked) {
        this.colAccClicked = colAccClicked;
    }

    public void setTypeCombo(){
        typeCombo.addItem("Personal");
        typeCombo.addItem("Real");
        typeCombo.addItem("Nominal");

    }

    public void addOwnerCombo(DefaultComboBoxModel own){
        ownerCombo.setModel(own);
    }

    public JComboBox getOwnerCombo() {
        return ownerCombo;
    }

    public JComboBox getTypeCombo() {
        return typeCombo;
    }

    public JTable getTable() {
        return table;
    }

    public JTable getAccTable(){
        return accTable;
    }

    public void setTable(Vector<Vector<String>> data1) {
        DefaultTableModel dtm=new DefaultTableModel();
        dtm.setColumnIdentifiers(cols);
        table.setModel(dtm);
        for(Vector<String> c:data1){
            dtm.addRow(c);
        }
    }

    public void setAccTable(Vector<Vector<String>> data1) {
        DefaultTableModel dtm=new DefaultTableModel();
        dtm.setColumnIdentifiers(accCols);
        accTable.setModel(dtm);
        for(Vector<String> c:data1){
            dtm.addRow(c);
        }
    }
    public static void main(String[] args){
        EmployeeView ev=new EmployeeView();
        ev.setVisible(true);
        System.out.println( Long.valueOf("3"));
    }
    public String getClientName(){
        return  name.getText();
    }
    public String getClientAddress(){
        return  address.getText();
    }
    public float getAmount(){
        return Long.parseLong(amount.getText());
    }
    public Long getClientId_card_nr(){
        return  Long.valueOf(idcardnr.getText());
    }
    public Long getClientPersNr(){
        return  Long.valueOf(persnum.getText());
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

//    public void showUI(){
//        setVisible(true);
//    }


}
