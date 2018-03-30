package model.validation;

import model.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ClientValidator {

    private static final String PNC_VALIDATION_REGEX = "^[0-9]+";
    private final int PERSNUMCODE_LENGTH = 5;
    private final int IDCARDNR_LENGTH = 6;


    public List<String> getErrors() {
        return errors;
    }

    private final List<String> errors;


    public ClientValidator() {
        errors = new ArrayList<>();
    }

    public boolean validatePersNumCode(String persNumCode) {
        boolean ok = true;
        if (!persNumCode.chars().allMatch((Character::isDigit))) {
            errors.add("Pers num code must contain only digits!");
            ok = false;
        }
        if (persNumCode.length() != PERSNUMCODE_LENGTH) {
            errors.add("Personal numerical code too short (13 digits)!");
            ok = false;
        }
        if (!persNumCode.substring(0, 1).equalsIgnoreCase("1") && !persNumCode.substring(0, 1).equalsIgnoreCase("2")) {
            errors.add("Personal numerical must start with 1 or 2!");
            ok = false;
        }
        return ok;
    }

    public boolean validateIdCardNr(String idCardNr) {
        boolean ok = true;
        if (idCardNr.length() != IDCARDNR_LENGTH) {
            errors.add("Invalid Id card number! Length must be at least 6!");
            ok = false;
        }
        if (idCardNr.chars().allMatch(Character::isDigit)) {
            errors.add("Id card number must contain only numeric characters!");
            ok = false;
        }
        return ok;
    }

    public boolean validateUpdate(int column, String newValue) {
        switch (column) {
            case 0:
                errors.add("Cannot change id!");
                break;
            case 1:
                return true;
            case 2:
                errors.add("Cannot change id card number!");
            case 3:
                errors.add("Cannot change personal numeric code!");
                break;
            case 4:
                return true;
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
