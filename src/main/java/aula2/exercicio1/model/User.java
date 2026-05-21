package aula2.exercicio.model;

import lombok.Data;

@Data
public class User {
    private int  id;
    private String name;
    private String email;
    private String phone;
    private String website;

    public User(int id, String name, String email, String phone, String website) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.website = website;
    }
}
