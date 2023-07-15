package personal.delivery.member.eneity;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Address {

    @NotBlank(message = "지역 정보는 필수값입니다.")
    private String city;

    @NotBlank(message = "도로명은 필수값입니다.")
    private String street;

    @NotBlank(message = "우편번호는 필수값입니다.")
    private String zipcode;

    @NotBlank(message = "상세 주소는 필수값입니다.")
    private String detailedAddress;

    public Address(String city, String street, String zipcode, String detailedAddress) {

        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
        this.detailedAddress = detailedAddress;

    }

}