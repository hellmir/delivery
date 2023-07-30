package personal.delivery.member.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import personal.delivery.base.BaseEntity;
import personal.delivery.constant.Role;

import java.time.LocalDateTime;

@Entity(name = "member")
@Getter
@NoArgsConstructor
@Table(name = "member")
public class Member extends BaseEntity {

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

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    private LocalDateTime registrationTime;
    private LocalDateTime updateTime;

    @Builder
    private Member(String name, String email, String password, Address address, Role role,
                   LocalDateTime registrationTime, LocalDateTime updateTime) {

        this.name = name;
        this.email = email;
        this.password = password;

        Address inputAddress = new Address
                (address.getCity(), address.getStreet(), address.getZipcode(), address.getDetailedAddress());
        this.address = inputAddress;

        this.role = role;

        this.registrationTime = registrationTime;
        this.updateTime = updateTime;

    }

}
