package model.builder;

import model.Account;

import java.util.Date;

public class AccountBuilder {

    private Account account;

    public AccountBuilder() {
        account = new Account();
        account.setDate_of_creation(new Date());
    }

    public AccountBuilder setId(Long id) {
        account.setId(id);
        return this;
    }

    public AccountBuilder setOwner(Long id) {
        account.setOwnerId(id);
        return this;
    }

    public AccountBuilder setType(String type) {
        account.setType(type);
        return this;
    }

    public AccountBuilder setAmount(Float amount) {
        account.setAmount(amount);
        return this;
    }

    public AccountBuilder setDate(Date date) {
        account.setDate_of_creation(date);
        return this;
    }

    public Account build() {
        return account;
    }
}
