package pl.sda.finalapp.users;

import pl.sda.finalapp.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User extends BaseEntity {


    private String firstName;
    private String lastName;
    private String eMail;
    private String passwordHash;
    private String city;
    private String country;
    private String zipCode;
    private String street;
    private String birthDate;
    private String pesel;
    private String phone;
    private boolean preferEmails;
    @ManyToMany
    @JoinTable(name = "user_role")
    private List<Role> roles = new ArrayList<>();

    public User() {
    }

    public User(String firstName, String lastName, String eMail, String passwordHash, String city, String country, String zipCode, String street, String birthDate, String pesel, String phone, boolean preferEmails) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.eMail = eMail;
        this.passwordHash = passwordHash;
        this.city = city;
        this.country = country;
        this.zipCode = zipCode;
        this.street = street;
        this.birthDate = birthDate;
        this.pesel = pesel;
        this.phone = phone;
        this.preferEmails = preferEmails;
    }

    public static User fromDto(RegistrationUserDto dto, String passwordHash){
        return new User(dto.getFirstName(),
                dto.getLastName(),
                dto.getEMail(),
                passwordHash,
                dto.getCity(),
                dto.getCountry(),
                dto.getZipCode(),
                dto.getStreet(),
                dto.getBirthDate(),
                dto.getPesel(),
                dto.getPhone(),
                dto.isPreferEmails());
    }

    public String geteMail() {
        return eMail;
    }

    public void addRole(Role role){
        this.roles.add(role);
    }
}
