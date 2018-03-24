package model;

public class Client {

    private Long id;
    private String name;
    private Long id_card_nr;
    private Long pers_num_code;
    private String address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId_card_nr() {
        return id_card_nr;
    }

    public void setId_card_nr(Long id_card_nr) {
        this.id_card_nr = id_card_nr;
    }

    public Long getPers_num_code() {
        return pers_num_code;
    }

    public void setPers_num_code(Long pers_num_code) {
        this.pers_num_code = pers_num_code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", id_card_nr=" + id_card_nr +
                ", pers_num_code=" + pers_num_code +
                ", address='" + address + '\'' +
                '}';
    }
}
