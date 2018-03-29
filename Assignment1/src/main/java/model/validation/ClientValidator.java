package model.validation;

import model.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ClientValidator {

    private static final String PNC_VALIDATION_REGEX = "^[0-9]+";
    private final int PERSNUMCODE_LENGTH = 5;
    private final int IDCARDNR_LENGTH = 6;

    private Client client;

    public List<String> getErrors() {
        return errors;
    }

    private final List<String> errors;

    public ClientValidator(Client client) {
        this.client = client;
        errors = new ArrayList<>();
    }

    public ClientValidator() {
        errors = new ArrayList<>();
    }

    public boolean validate() {
        validatePersNumCode(client.getPers_num_code());
        validateIdCardNrr(client.getId_card_nr());
        return errors.isEmpty();
    }

    public boolean validatePersNumCode(Long pnc) {
        String spnc = String.valueOf(pnc);
        System.out.printf(spnc + " " + spnc.substring(0, 1) + " ");
        boolean ok = true;
        if (spnc.length() != PERSNUMCODE_LENGTH) {
            errors.add("Personal numerical code too short (13 digits)!");
            ok = false;
        }
        if (!spnc.substring(0, 1).equalsIgnoreCase("1") && !spnc.substring(0, 1).equalsIgnoreCase("2")) {
            errors.add("Personal numerical must start with 1 or 2!");
            ok = false;
        }
        return ok;
    }

//    private void validatePersNumCode() {
//        String spnc=client.getPers_num_code().toString();
//        if(spnc.length()!=PERSNUMCODE_LENGTH || (spnc.charAt(0)!=1 || spnc.charAt(1)!=2))
//            errors.add("Invalid personal numerical code!");
//    }

    public boolean validateIdCardNr(Long icn) {
        boolean ok = true;
        if (icn.toString().length() != IDCARDNR_LENGTH) {
            errors.add("Invalid Id card number! Length must be at least 6!");
            ok = false;
        }
        if (icn.toString().chars().allMatch(Character::isDigit)) {
            errors.add("Id card number must contain only numeric characters!");
            ok = false;
        }
        return ok;
    }

    private void validateIdCardNrr(Long icn) {
        if (icn.toString().length() != IDCARDNR_LENGTH) {
            errors.add("Invalid Id card number! Length must be at least 6!");
        }

    }
}
