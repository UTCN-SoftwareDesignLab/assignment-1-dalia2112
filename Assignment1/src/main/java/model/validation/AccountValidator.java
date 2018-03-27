package model.validation;

import model.Account;

import java.util.ArrayList;
import java.util.List;

public class AccountValidator {

    private final float MINSUM=10;
    private final Account account;
    public List<String> getErrors() {
        return errors;
    }
    private final List<String> errors;

    public AccountValidator(Account account) {
        this.account = account;
        errors=new ArrayList<>();
    }

    public boolean validate(long sumToTransfer) {
        validateTransfSum(sumToTransfer);
        validateAmount(account.getAmount());
        return errors.isEmpty();
    }

    private void validateTransfSum(float sumToTransfer) {
        if(account.getAmount()-sumToTransfer<MINSUM)
            errors.add("Cannot trasfer from this account! Not enough money!");
    }

    private void validateAmount(float amount) {
        if(amount<MINSUM)
            errors.add("Invalid amount! Must be at least 10 lei!");
    }
}
