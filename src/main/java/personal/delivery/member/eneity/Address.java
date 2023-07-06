package personal.delivery.member.eneity;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;
    private String detailedAddress;

    protected Address() {
    }

    public Address(String city, String street, String zipcode, String detailedAddress) {

        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
        this.detailedAddress = detailedAddress;

    }

}