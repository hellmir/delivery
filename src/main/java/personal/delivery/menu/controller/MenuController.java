package personal.delivery.menu.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import personal.delivery.menu.dto.MenuResponseDto;
import personal.delivery.menu.service.MenuService;

import java.util.List;

@RestController
@RequestMapping("menus")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @GetMapping("menu")
    public ResponseEntity<List<MenuResponseDto>> getAllMenus() {

        List<MenuResponseDto> menuResponseDtoList = menuService.getAllMenus();

        return ResponseEntity.status(HttpStatus.OK).body(menuResponseDtoList);

    }

}
