package personal.delivery.cart.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import personal.delivery.member.Member;

@Entity
@Getter
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Cart(Member member) {

        this.member = member;

    }

}
