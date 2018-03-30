package mapper;

import model.Account;
import model.Client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ClientTableMapper {
    private List<Client> clients;

    public ClientTableMapper() {
        clients = new ArrayList<>();
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public Vector<Vector<String>> formatClientTable() {
        Vector<Vector<String>> clientsVect = new Vector<>();
        for (Client client : clients) {
            Vector<String> data = new Vector<>();
            data.add(client.getId().toString());
            data.add(client.getName());
            data.add(client.getId_card_nr().toString());
            data.add(client.getPers_num_code().toString());
            data.add(client.getAddress().toString());
            clientsVect.add(data);
        }
        return clientsVect;
    }

    public long getClientId(int row) {
        return clients.get(row).getId();
    }

    public String getClientName(int row) {
        return clients.get(row).getName();
    }

    public long getClientId_card_nr(int row) {
        return clients.get(row).getId_card_nr();
    }

    public long getClientPers_num_code(int row) {
        return clients.get(row).getPers_num_code();
    }

    public String getClientAddress(int row) {
        return clients.get(row).getAddress();
    }

    public void addClientToTable(Client client) {
        clients.add(client);
    }

    public void deleteClientFromTable(int row) {
        clients.remove(row);
    }


    public void updateClient(int row, int col, String newval) {
        switch (col) {
            case 0:
                break;
            case 1:
                clients.get(row).setName(newval);
                break;
            case 2:
                clients.get(row).setId_card_nr(Long.parseLong(newval));
                break;
            case 3:
                break;
            case 4:
                clients.get(row).setAddress(newval);
                break;
        }
    }

}
