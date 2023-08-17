package personal.delivery.shop.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import personal.delivery.base.BaseEntity;
import personal.delivery.member.entity.Member;
import personal.delivery.menu.entity.Menu;

import java.time.LocalDateTime;
import java.util.List;

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

    @OneToMany(mappedBy = "shop", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Menu> menus;

    @Builder
    private Shop(String name, Member member, LocalDateTime registeredTime, LocalDateTime updatedTime) {

        this.name = name;
        this.member = member;

        setRegisteredTime(registeredTime);

        setUpdatedTime(updatedTime);

    }

}
