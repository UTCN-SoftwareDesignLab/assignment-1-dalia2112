package model.validation;


import java.util.ArrayList;
import java.util.List;

public class AccountValidator {

    private final float MINSUM = 10;

    public List<String> getErrors() {
        return errors;
    }

    private final List<String> errors;

    public AccountValidator() {
        errors = new ArrayList<>();
    }


    public boolean validateTransfSum(float amount, float sumToTransfer) {
        if (amount - sumToTransfer < MINSUM) {
            errors.add("Cannot transfer/pay from this account! Not enough money!");
            return false;
        }
        return true;
    }

    public boolean validateAmount(float amount) {
        if (amount < MINSUM) {
            errors.add("Not enough money! Provide at least 10 lei!");
            return false;
        }
        return true;
    }

    public boolean validateUpdate(int column, String newValue) {
        switch (column) {
            case 0:
                errors.add("Cannot change id!");
                break;
            case 1:
                errors.add("Cannot change type!");
                break;
            case 2: return validateAmount(Float.parseFloat(newValue));
            case 3:
                errors.add("Cannot change date!");
                break;
            case 4:
                errors.add("Cannot change owner ID!");
                break;
        }
        return false;
    }

    public String getFormattedErrors() {
        String result = "";
        for (String error : getErrors())
            result += error + "\n";
        return result;
    }
}
