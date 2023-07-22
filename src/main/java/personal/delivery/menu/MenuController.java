package personal.delivery.menu;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import personal.delivery.menu.dto.MenuDto;
import personal.delivery.menu.dto.MenuResponseDto;
import personal.delivery.menu.service.MenuService;
import personal.delivery.validation.group.OnCreate;
import personal.delivery.validation.group.OnUpdate;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping()
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @PostMapping("{shopId}/menu")
    public ResponseEntity<MenuResponseDto> createMenu(@PathVariable Long shopId, @Validated(OnCreate.class) @RequestBody MenuDto menuDto) {

        MenuResponseDto menuResponseDto = menuService.saveMenu(shopId, menuDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(menuResponseDto.getId())
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);

        return ResponseEntity.created(location).headers(headers).body(menuResponseDto);

    }

    @GetMapping("menu")
    public ResponseEntity<List<MenuResponseDto>> getAllMenu() {

        List<MenuResponseDto> menuResponseDtoList = menuService.getAllMenu();

        return ResponseEntity.status(HttpStatus.OK).body(menuResponseDtoList);

    }

    @GetMapping("{shopId}/menu")
    public ResponseEntity<List<MenuResponseDto>> getAllShopMenu(@PathVariable Long shopId) {

        List<MenuResponseDto> menuResponseDtoList = menuService.getAllShopMenu(shopId);

        return ResponseEntity.status(HttpStatus.OK).body(menuResponseDtoList);

    }

    @GetMapping("{shopId}/menu/{id}")
    public ResponseEntity<MenuResponseDto> getMenu(@PathVariable Long id) {

        MenuResponseDto menuResponseDto = menuService.getMenu(id);

        return ResponseEntity.status(HttpStatus.OK).body(menuResponseDto);

    }

    @PatchMapping("{shopId}/menu/{id}")
    public ResponseEntity<MenuResponseDto> changeMenu
            (@PathVariable Long id, @Validated(OnUpdate.class) @RequestBody MenuDto menuDto) throws Exception {

        MenuResponseDto menuResponseDto = menuService.changeMenu(id, menuDto);

        return ResponseEntity.status(HttpStatus.OK).body(menuResponseDto);

    }

    @DeleteMapping("{shopId}/menu/{id}")
    public ResponseEntity<Void> deleteMenu(@PathVariable Long id) throws Exception {

        menuService.deleteMenu(id);

        return ResponseEntity.noContent().build();

    }

}