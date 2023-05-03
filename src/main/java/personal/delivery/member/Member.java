package personal.delivery.member;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import personal.delivery.constant.Role;

import java.time.LocalDateTime;

@Entity
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

    @Column(length = 50)
    private String address;

    @Enumerated(EnumType.STRING)
    private Role role;

    LocalDateTime registrationTime;
    LocalDateTime updateTime;

    @Builder
    public Member(String name, String email, String password, String address, Role role,
                  LocalDateTime registrationTime, LocalDateTime updateTime) {

        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.role = role;
        this.registrationTime = registrationTime;
        this.updateTime = updateTime;

    }

}
