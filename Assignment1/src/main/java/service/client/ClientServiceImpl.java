package service.client;

import model.Client;
import model.validation.Notification;
import repository.EntityNotFoundException;
import repository.client.ClientRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Vector;

public class ClientServiceImpl implements ClientService {

    private final ClientRepository repository;

    public ClientServiceImpl(ClientRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Client> findAll() {
        return repository.findAll();
    }

    @Override
    public Client findById(Long id) {//throws EntityNotFoundException {
        return repository.findById(id);
    }
    public Client findByName(String name) throws EntityNotFoundException {
        return repository.findByName(name);
    }

    @Override
    public boolean save(Client client) {
        return repository.save(client);
    }
    public Vector<Vector<String>> getAllClientsTable(){
        String[][] t=new String[][]{};
        Vector<Vector<String>> clients = new Vector<>();
        int i=0;
        for(Client c:findAll()){
            Vector<String> data = new Vector<>();
            data.add(c.getId().toString());
            data.add(c.getName());
            data.add(c.getId_card_nr().toString());
            data.add(c.getPers_num_code().toString());
            data.add(c.getAddress());
            clients.add(data);
        }
        return clients;
    }

    public void deleteClient(Long id){
        repository.deleteClient(id);
    }
    public void updateClient(Long id,int col,String newval){

        String column="";
        switch (col) {
            case 0:
                JOptionPane.showMessageDialog(null,"Cannot change id!");
                break;
            case 1:
                column="name";
                break;
            case 2:
                column="id_card_nr";
                break;
            case 3:
                column="pers_num_code";
                break;
            case 4:
                column="address";
                break;
            default: column="name";
        }
        repository.updateClient(id,column,newval);
    }



}
