package entity;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class Address {
    private Integer id;
    private String street;
    private String houseNumber;
    private String city;
    private String country;

    @Override
    public String toString() {
        return  "Address: " +
                "\n Street:" + street +
                "\n HouseNumber: " + houseNumber +
                "\n City: " + city +
                "\n Country=" + country + "\n";
    }
}
