package personal.delivery.member.dto;

import lombok.Data;
import personal.delivery.member.constant.Role;
import personal.delivery.member.entity.Address;

import java.time.LocalDateTime;

@Data
public class MemberResponseDto {

    private Long id;
    private String name;
    private String email;
    private Address address;
    private Role role;

    private LocalDateTime registeredTime;
    private LocalDateTime updatedTime;

}
