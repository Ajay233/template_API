package template.API.User.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String forename;
    private String surname;
    private String email;
    private String password;
    private String permission;

    public User(){}

    public User(String forename, String surname, String email, String password){
        this.forename = forename;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.permission = "USER";
    }

}
