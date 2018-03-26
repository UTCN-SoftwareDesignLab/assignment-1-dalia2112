package controller;

import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import database.Constants;
import model.Role;
import model.User;
import model.builder.UserBuilder;
import service.user.UserService;
import view.AdminView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class AdminController {
    private AdminView adminView;
    private UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
        adminView=new AdminView();
        adminView.setViewButtonListener(new ViewButtonListener());
        adminView.setAddButtonListener(new AddButtonListener());
//        adminView.setUpdateButtonListener(new UpdateButtonListener());
//        adminView.setDeleteButtonListener(new DeleteButtonListener());
    }

    private  void writeClientTable(){
        Vector<Vector<String>> data = userService.getAllUserTable();
        adminView.setEmplTable(data);
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
            String username = adminView.getUsernameTf().getText();
            String pass=adminView.getPassTf().getText();
            String r=adminView.getRolesCombo().getSelectedItem().toString();
            Vector<Role> role=null;
            if(r== Constants.Roles.EMPLOYEE) role.elementAt(0).setRole(Constants.Roles.EMPLOYEE);
            else role.elementAt(0).setRole(Constants.Roles.ADMINISTRATOR);
            User u= new UserBuilder()
                    .setUsername(username)
                    .setPassword(pass)
                    .setRoles(role)
                    .build();
            userService.save(u);
            writeClientTable();
        }
    }

   /* private class UpdateButtonListener implements ActionListener {

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

            int row=employeeView.getRowAccClicked();
            String idd=String.valueOf(employeeView.getAccTable().getModel().getValueAt(row,0));
            Long id=Long.parseLong(idd);
            accountService.deleteAccount(id);
            writeAccountTable();
        }
    }*/
    public void showUI(){
        adminView.setVisible(true);
    }

}
