package model.builder;

import model.Bill;
import org.apache.commons.lang3.RandomStringUtils;


public class BillBuilder {

    private Bill bill;

    public BillBuilder(){
        bill=new Bill();
        bill.setCode(RandomStringUtils.randomAlphanumeric(10));
    }

    public BillBuilder setOwner(float price){
        bill.setPrice(price);
        return  this;
    }

    public BillBuilder setTitle(String title){
        bill.setTitle(title);
        return  this;
    }

    public BillBuilder setClientId(long id){
        bill.setClientId(id);
        return  this;
    }

    public Bill build() {
        return bill;
    }
}
