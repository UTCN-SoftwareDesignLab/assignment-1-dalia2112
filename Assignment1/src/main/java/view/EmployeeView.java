package view;

import controller.EmployeeController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private JButton button1;

    private final String[] cols={"id","name","id_card_nr","pers_num_code","address"};
    private final String[][] data={{"dal","bla","bla","bla"},{"dal2","bla","bla","bla"},{"dal3","bla","bla","bla"},};

    public EmployeeView() {

//        frame = new JFrame("Client Info");
//        JPanel jp = new JPanel();
        setContentPane(contentPane);
        setModal(true);
        pack();
        setResizable(false);
        setVisible(false);

//        add(deleteClientButton);
//        add(addClientButton);

        //setVisible(true);
//        frame.add(updateClientButton);
//        frame.add(idcardnr);
//        frame.add(persnum);
//        frame.add(address);
//        frame.add(name);
//        frame.setSize(300, 300);
//        scroll.setVisible(true);
//        contentPane.setVisible(true);
//        frame.setVisible(true);
        //frame.setVisible(true);
//        setContentPane(contentPane);
        //setModal(true);
        //setSize(400, 400);
        //setResizable(false);
        //scroll.add(table);
//        DefaultTableModel d = new DefaultTableModel(data, cols);

//        table=new JTable(data, cols);
//        scroll=new JScrollPane(table);
//        table.setVisible(true);
//        scroll.setVisible(true);
//        table.setPreferredScrollableViewportSize(new Dimension(450,63));
//        table.setFillsViewportHeight(true);
//        scroll=new JScrollPane(table);
//        table.setVisible(true);
//        scroll.setVisible(true);
//        initialize();
//        add(table);
        //add(updateClientButton);
       // add(addClientButton);
        //add(viewClientButton);
        //add(clientName);
//        add(scroll);
        //getRootPane().setDefaultButton(buttonOK)
//        button1.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                JOptionPane.showInputDialog("HEEI");
//            }
//        });
//        updateClientButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                JOptionPane.showInputDialog("HEEEEEEEI");
//            }
//        });

//        viewClientButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                JOptionPane.showInputDialog("HEI");
//            }
//        });
    }

//    private void initialize(){
//        clientName = new JTextField();
//        table = new JTable();
//        scroll = new JScrollPane(table);
//        table.setFillsViewportHeight(true);
//    }

    public JTable getTable() {
        return table;
    }

    public JFrame getFrame(){
        return frame;
    }
    public JScrollPane getScroll() {
        return scroll;
    }

    public JButton getViewClientButton() {
        return viewClientButton;
    }

    public void setTable(Vector<Vector<String>> data1) {
//        System.out.println("AYE");
        String[][] newdata = new String[data1.size()][5];
        for (int i = 0; i < data1.size(); i++){
            newdata[i] = data1.elementAt(i).toArray(new String[0]);
        }
        table=new JTable(data, cols);
        scroll=new JScrollPane(table);
        table.setVisible(true);
        scroll.setVisible(true);
    }
    public static void main(String[] args){
        EmployeeView ev=new EmployeeView();
        ev.setVisible(true);
    }
    public String getClientName(){
        return  name.getText();
    }
    public String getClientAddress(){
        return  address.getText();
    }
    public Long getClientId_card_nr(){
        return  Long.getLong(idcardnr.getText());
    }
    public Long getClientpPersNr(){
        return  Long.getLong(persnum.getText());
    }

    public void setUpdateButtonListener(ActionListener updateButtonListener) {
        updateClientButton.addActionListener(updateButtonListener);
    }

    public void setAddButtonListener(ActionListener addButtonListener) {
        addClientButton.addActionListener(addButtonListener);
    }
    public void setButton1Listener(ActionListener button1Listener) {
        button1.addActionListener(button1Listener);
    }

    public void setViewButtonListener(ActionListener viewClientListener) {
        viewClientButton.addActionListener(viewClientListener);
    }

    public void setDeleteButtonListener(ActionListener deleteClientListener) {
        deleteClientButton.addActionListener(deleteClientListener);
    }

    public void showUI(){
        setVisible(true);
    }
}
