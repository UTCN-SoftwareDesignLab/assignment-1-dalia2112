package view;

import com.sun.org.apache.xpath.internal.SourceTree;
import database.Constants;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.Vector;

public class AdminView extends JDialog {
    private JPanel contentPane;
    private JTable emplTable;
    private JButton deleteEmployeeButton;
    private JButton addEmployeeButton;
    private JButton updateEmployeeButton;
    private JButton viewEmployeeButton;
    private JTextField idTf;
    private JTextField usernameTf;
    private JTextField passTf;
    private JComboBox rolesCombo;
    private JButton getActivityButton;
    private JTextField fromDate;
    private JTextField toDate;
//    private int rowClicked;
//    private int colClicked;

    public AdminView() {
        setContentPane(contentPane);
        setModal(true);
        setSize(900, 500);
        setResizable(false);
        setRolesCombo();
    }

    public void setRolesCombo() {
        rolesCombo.addItem(Constants.Columns.ADMINISTRATOR);
        rolesCombo.addItem(Constants.Columns.EMPLOYEE);
    }

    public JTable getEmplTable() {
        return emplTable;
    }

    public void setEmplTable(Vector<Vector<String>> data1) {
        DefaultTableModel dtm = new DefaultTableModel();
        dtm.setColumnIdentifiers(Constants.Columns.USER_COLS);
        emplTable.setModel(dtm);
        for (Vector<String> c : data1) {
            dtm.addRow(c);
        }
    }

    public String getIdTf() {
        return idTf.getText();
    }

    public String getUsernameTf() {
        return usernameTf.getText();
    }

    public String getPassTf() {
        return passTf.getText();
    }

    public String getRolesCombo() {
        return rolesCombo.getSelectedItem().toString();
    }

    public void setAddButtonListener(ActionListener addButtonListener) {
        addEmployeeButton.addActionListener(addButtonListener);
    }

    public void setDeleteButtonListener(ActionListener deleteButtonListener) {
        deleteEmployeeButton.addActionListener(deleteButtonListener);
    }

    public void setUpdateButtonListener(ActionListener deleteButtonListener) {
        updateEmployeeButton.addActionListener(deleteButtonListener);
    }

    public void setViewButtonListener(ActionListener viewButtonListener) {
        viewEmployeeButton.addActionListener(viewButtonListener);
    }

    public void setActivityButtonListener(ActionListener activityButtonListener) {
        getActivityButton.addActionListener(activityButtonListener);
    }
    public void setTableMouseListener(MouseAdapter m) {
        emplTable.addMouseListener(m);
    }

    public int getRowClicked() {
        return emplTable.getSelectedRow();
    }

//    public void setRowClicked(int rowClicked) {
//        this.rowClicked = rowClicked;
//    }

    public int getColClicked() {
        return emplTable.getSelectedColumn();
    }

//    public void setColClicked(int colClicked) {
//        this.colClicked = colClicked;
//    }


    public String getFromDate() {
        return fromDate.getText();
    }

    public String getToDate() {
        return toDate.getText();
    }

    public void showActivities(String activities){
        JOptionPane.showMessageDialog(this,activities);
    }

    public void addWindowListener(WindowAdapter windowAdapter) {
        super.addWindowListener(windowAdapter);
    }
}
