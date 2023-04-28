package personal.delivery.cart;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "cart")
@Getter
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

}
