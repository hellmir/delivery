package personal.delivery.menu;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "menu")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer salesRate;

    @Column(nullable = false)
    private Integer stock;

    private String flavor;
    private Integer portions;
    private Integer cookingTime;
    private String menuType;
    private String foodType;
    private Boolean popularMenu;
}
