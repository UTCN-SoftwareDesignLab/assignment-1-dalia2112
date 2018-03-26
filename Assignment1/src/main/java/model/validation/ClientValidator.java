package model.validation;

import model.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ClientValidator {

    private static final String PNC_VALIDATION_REGEX = "^[0-9]+";
    private final int PERSNUMCODE_LENGTH=13;
    private final int IDCARDNR_LENGTH=6;

    private final Client client;
    public List<String> getErrors() {
        return errors;
    }
    private final List<String> errors;

    public ClientValidator(Client client) {
        this.client = client;
        errors=new ArrayList<>();
    }

    public boolean validate() {
        validatePersNumCode(client.getPers_num_code());
        validateIdCardNr(client.getId_card_nr());
        return errors.isEmpty();
    }

    private void validatePersNumCode(Long pnc) {
        String spnc=pnc.toString();
        if(spnc.length()!=PERSNUMCODE_LENGTH || (spnc.charAt(0)!=1 || spnc.charAt(1)!=2))
            errors.add("Invalid personal numerical code!");
    }

    private void validateIdCardNr(Long icn) {
        if (icn.toString().length()!=6) {
            errors.add("Invalid Id card number!");
        }

    }
}
