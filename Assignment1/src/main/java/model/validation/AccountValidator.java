package model.validation;

import model.Account;

import java.util.ArrayList;
import java.util.List;

public class AccountValidator {

    private final float MINSUM = 10;

    //    private final Account account;
    public List<String> getErrors() {
        return errors;
    }

    private final List<String> errors;

    public AccountValidator() {
//        this.account = account;
        errors = new ArrayList<>();
    }

//    public boolean validate(long sumToTransfer) {
//        validateTransfSum(sumToTransfer,transfer);
//        validateAmount(account.getAmount());
//        return errors.isEmpty();
//    }

    public boolean validateTransfSum(float amount, float sumToTransfer, boolean transfer) {
        if (amount - sumToTransfer < MINSUM)
            if (transfer)
                errors.add("Cannot trasfer from this account! Not enough money!");
            else
                errors.add("Not enough money! Provide at least 10 lei!");

        return errors.isEmpty();
    }

}
