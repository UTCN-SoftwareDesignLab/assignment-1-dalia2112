package mapper;

import database.Constants;
import model.Account;
import model.Client;
import model.validation.ClientValidator;
import repository.client.ClientRepository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ClientTableMapper {

    public Vector<Vector<String>> formatClientTable(List<Client> clients) {
        Vector<Vector<String>> clientsVect = new Vector<>();
        for (Client client : clients) {
            Vector<String> data = new Vector<>();
            for (String column : Constants.Columns.CLIENT_COLS) {
                data.add(getValueAtColumn(column, client));
            }
            clientsVect.add(data);
        }
        return clientsVect;
    }

    public String getValueAtColumn(String column, Client client) {
        switch (column) {
            case "Id":
                return client.getId().toString();
            case "Name":
                return client.getName();
            case "Id card nr":
                return client.getId_card_nr().toString();
            case "Personal Num Code":
                return client.getPers_num_code().toString();
            case "Address":
                return client.getAddress();
        }
        return "";
    }

    public long getClientId(List<Client> clients, int row) {
        return clients.get(row).getId();
    }

    public void updateClient(List<Client> clients, int row, int col, String newval) {
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

    public String getColumnName(int col) {
        switch (col) {
            case 0:
                return "id";
            case 1:
                return "name";
            case 2:
                return "id_card_nr";
            case 3:
                return "pers_num_code";
            case 4:
                return "address";
        }
        return "";
    }
}
