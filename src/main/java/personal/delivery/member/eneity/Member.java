package personal.delivery.member.eneity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import personal.delivery.constant.Role;

import java.time.LocalDateTime;

@Entity(name = "member")
@Getter
@NoArgsConstructor
@Table(name = "member")
public class Member {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10)
    private String name;

    @Column(nullable = false, unique = true, length = 30)
    private String email;

    @Column(nullable = false, length = 20)
    private String password;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private Role role;

    LocalDateTime registrationTime;
    LocalDateTime updateTime;

    @Builder
    public Member(String name, String email, String password, Address address, Role role,
                  LocalDateTime registrationTime, LocalDateTime updateTime) {

        this.name = name;
        this.email = email;
        this.password = password;

        Address inputAddress = new Address(address.getCity(), address.getStreet(), address.getZipcode(), address.getDetailedAddress());
        this.address = inputAddress;

        this.role = role;
        this.registrationTime = registrationTime;
        this.updateTime = updateTime;

    }

}
