package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.util.Vector;

public class AdminView extends JDialog {
    private JPanel contentPane;
    private JTable emplTable;
    private JButton deleteEmployeeButton;
    private JButton addEmployeeButton;
    private JButton updateEmployeeButton;
    private JButton viewEmployeeButton;
    private JTextField idTf;
    private JTextField passTf;
    private JTextField usernameTf;
    private JComboBox rolesCombo;
    private String[] cols={"Id","Username","Password","Role"};


    public AdminView() {
        setContentPane(contentPane);
        setModal(true);
        setSize(500,500);
//        pack();
    }


    public JTable getEmplTable() {
        return emplTable;
    }

    public void setEmplTable(Vector<Vector<String>> data1) {
        DefaultTableModel dtm = new DefaultTableModel();
        dtm.setColumnIdentifiers(cols);
        emplTable.setModel(dtm);
        for (Vector<String> c : data1) {
            dtm.addRow(c);
        }
    }

    public JTextField getIdTf() {
        return idTf;
    }

    public JTextField getPassTf() {
        return passTf;
    }

    public JTextField getUsernameTf() {
        return usernameTf;
    }

    public JComboBox getRolesCombo() {
        return rolesCombo;
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
}
