package personal.delivery.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import personal.delivery.repository.MenuRepository;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
}