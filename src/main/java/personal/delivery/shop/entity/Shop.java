package personal.delivery.shop.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import personal.delivery.base.BaseEntity;
import personal.delivery.member.entity.Member;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "shop")
public class Shop extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shop_id")
    private Long id;

    @Column(length = 20)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime registrationTime;
    private LocalDateTime updateTime;

    @Builder
    private Shop(String name, Member member, LocalDateTime registrationTime, LocalDateTime updateTime) {

        this.name = name;
        this.member = member;
        this.registrationTime = registrationTime;
        this.updateTime = updateTime;

    }

}
