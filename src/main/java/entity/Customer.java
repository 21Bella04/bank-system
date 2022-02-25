package entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Customer {
    private Integer id;
    private String name;
    private String surname;
    private String phoneNumber;
    private String email;
    private Integer address;


    public Customer(Integer id, String name, String surname, Integer address, String phoneNumber, String email) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Name: " + name +
                ", \nSurname: " + surname +
                ", \nPhoneNumber: " + phoneNumber +
                ", \nEmail: " + email + "\n";
    }
}