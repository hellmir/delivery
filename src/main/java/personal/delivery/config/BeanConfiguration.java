package personal.delivery.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;

public class BeanConfiguration {

    @Bean
    public static ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
