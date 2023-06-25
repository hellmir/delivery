package personal.delivery.member.dto;

import lombok.Data;
import personal.delivery.member.domain.Address;

@Data
public class MemberDto {

    private String name;
    private String email;
    private String password;
    private Address address;

}
