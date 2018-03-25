package view;

import controller.EmployeeController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.Vector;

public class EmployeeView extends JDialog {
    private JPanel contentPane;
    private JFrame frame;
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
    private int rowClicked;
    private int colClicked;

    private final Object[] cols={"Id","Name","Id_card_nr","Pers_num_code","Address"};
    private final String[][] data={{"dal","bla","bla","bla"},{"dal2","bla","bla","bla"},{"dal3","bla","bla","bla"},};

    public EmployeeView() {

        setContentPane(contentPane);
        setModal(true);
        pack();
        setResizable(false);
        setVisible(false);
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

    public JTable getTable() {
        return table;
    }

    public void setTable(Vector<Vector<String>> data1) {
        DefaultTableModel dtm=new DefaultTableModel();
        dtm.setColumnIdentifiers(cols);
        table.setModel(dtm);
        for(Vector<String> c:data1){
            dtm.addRow(c);
        }
    }
    public static void main(String[] args){
       // EmployeeView ev=new EmployeeView();
        //ev.setVisible(true);
        System.out.println( Long.valueOf("3"));
    }
    public String getClientName(){
        return  name.getText();
    }
    public String getClientAddress(){
        return  address.getText();
    }
    public Long getClientId_card_nr(){
//        System.out.println();
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

    public void showUI(){
        setVisible(true);
    }
}
