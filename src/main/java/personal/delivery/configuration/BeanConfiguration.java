package personal.delivery.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import personal.delivery.cart.entity.Cart;
import personal.delivery.member.entity.Member;
import personal.delivery.menu.Menu;
import personal.delivery.order.entity.Order;

@Configuration
public class BeanConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public Member member() {
        return new Member();
    }

    @Bean
    public Menu menu() {
        return new Menu();
    }

    @Bean
    public Order order() {
        return new Order();
    }

    @Bean
    public Cart cart() {
        return new Cart();
    }

}
