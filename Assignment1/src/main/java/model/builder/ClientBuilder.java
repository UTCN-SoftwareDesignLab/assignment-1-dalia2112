package model.builder;

import model.Client;

public class ClientBuilder {

    private Client client;

    public ClientBuilder() {
        this.client = new Client();
    }


    public ClientBuilder setID(Long id){
        client.setId(id);
        return this;
    }

    public ClientBuilder setName(String name){
        client.setName(name);
        return this;
    }

    public ClientBuilder setIdCard(Long idCard){
        client.setId_card_nr(idCard);
        return this;
    }

    public ClientBuilder setPersNumCode(Long pnc){
        client.setPers_num_code(pnc);
        return this;
    }

    public ClientBuilder setAddress(String address){
        client.setAddress(address);
        return this;
    }
    public Client build(){
        return client;
    }
}
