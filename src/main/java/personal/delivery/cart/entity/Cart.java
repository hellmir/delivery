package personal.delivery.cart.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import personal.delivery.base.BaseEntity;
import personal.delivery.member.entity.Member;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "cart")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class Cart extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime registrationTime;
    private LocalDateTime updateTime;

    @Builder
    private Cart(Member member, LocalDateTime registrationTime, LocalDateTime updateTime) {

        this.member = member;
        this.registrationTime = registrationTime;
        this.updateTime = updateTime;

    }

}
