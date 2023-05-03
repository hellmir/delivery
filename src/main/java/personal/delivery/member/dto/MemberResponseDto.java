package personal.delivery.member.dto;

import lombok.Data;
import personal.delivery.constant.Role;

import java.time.LocalDateTime;

@Data
public class MemberResponseDto {

    private String name;
    private String email;
    private String address;
    private Role role;

    private LocalDateTime registrationTime;
    private LocalDateTime updateTime;

}
