package personal.delivery.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import personal.delivery.service.MenuService;

@Controller
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;
}