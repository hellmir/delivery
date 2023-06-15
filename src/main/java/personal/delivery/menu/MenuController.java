package personal.delivery.menu;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import personal.delivery.menu.dto.MenuDto;
import personal.delivery.menu.dto.MenuResponseDto;
import personal.delivery.menu.service.MenuService;

import java.util.List;


@RestController
@RequestMapping("menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @PostMapping()
    public ResponseEntity<MenuResponseDto> createMenu(@RequestBody MenuDto menuDto) {
        MenuResponseDto menuResponseDto = menuService.saveMenu(menuDto);

        return ResponseEntity.status(HttpStatus.OK).body(menuResponseDto);
    }

    @GetMapping("{id}")
    public ResponseEntity<MenuResponseDto> getMenu(@PathVariable Long id) {
        MenuResponseDto menuResponseDto = menuService.getMenu(id);

        return ResponseEntity.status(HttpStatus.OK).body(menuResponseDto);
    }

    @GetMapping()
    public ResponseEntity<List<MenuResponseDto>> getAllMenu() {
        List<MenuResponseDto> menuResponseDtoList = menuService.getAllMenu();

        return ResponseEntity.status(HttpStatus.OK).body(menuResponseDtoList);
    }

    @PatchMapping("{id}")
    public ResponseEntity<MenuResponseDto> changeMenu(@PathVariable Long id, @RequestBody MenuDto menuDto) throws Exception {
        MenuResponseDto menuResponseDto = menuService.changeMenu(id, menuDto);

        return ResponseEntity.status(HttpStatus.OK).body(menuResponseDto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<MenuResponseDto> deleteMenu(@PathVariable Long id) throws Exception {
        MenuResponseDto menuResponseDto = menuService.deleteMenu(id);

        return ResponseEntity.status(HttpStatus.OK).body(menuResponseDto);
    }

}