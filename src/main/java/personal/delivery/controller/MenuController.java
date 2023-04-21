package personal.delivery.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import personal.delivery.dto.MenuDto;
import personal.delivery.dto.MenuResponseDto;
import personal.delivery.service.MenuService;


@RestController
@RequestMapping("menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    // 1. 클라이언트로부터 ID 요청값 받음
    // 2. MenuService에 전달 후, MenuResponseDto 응답값 받아서 return
    @GetMapping()
    public ResponseEntity<MenuResponseDto> getMenu(Long id) {
        MenuResponseDto menuResponseDto = menuService.getMenu(id);

        return ResponseEntity.status(HttpStatus.OK).body(menuResponseDto);
    }

    // 1. 클라이언트로부터 신메뉴 요청값 받음
    // 2. MenuDto를 통해 MenuService에 전달
    // 3. MenuService로부터 MenuResponseDto 응답값 받아서 return
    @PostMapping()
    public ResponseEntity<MenuResponseDto> createMenu(@RequestBody MenuDto menuDto) {
        MenuResponseDto menuResponseDto = menuService.saveMenu(menuDto);

        return ResponseEntity.status(HttpStatus.OK).body(menuResponseDto);
    }
}