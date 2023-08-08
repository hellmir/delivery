package personal.delivery.menu.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import personal.delivery.menu.dto.MenuRequestDto;
import personal.delivery.menu.dto.MenuResponseDto;
import personal.delivery.menu.service.MenuService;
import personal.delivery.validation.group.OnCreate;
import personal.delivery.validation.group.OnUpdate;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("shops/{shopId}/menus")
@RequiredArgsConstructor
public class ShopMenuController {

    private final MenuService menuService;

    @PostMapping()
    public ResponseEntity<MenuResponseDto> createMenu
            (@PathVariable Long shopId, @Validated(OnCreate.class) @RequestBody MenuRequestDto menuRequestDto) {

        MenuResponseDto menuResponseDto = menuService.saveMenu(shopId, menuRequestDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(menuResponseDto.getId())
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);

        return ResponseEntity.created(location).headers(headers).body(menuResponseDto);

    }

    @GetMapping()
    public ResponseEntity<List<MenuResponseDto>> getAllShopMenus(@PathVariable Long shopId) {

        List<MenuResponseDto> menuResponseDtoList = menuService.getAllShopMenus(shopId);

        return ResponseEntity.status(HttpStatus.OK).body(menuResponseDtoList);

    }

    @GetMapping("{id}")
    public ResponseEntity<MenuResponseDto> getMenu(@PathVariable Long id) {

        MenuResponseDto menuResponseDto = menuService.getMenu(id);

        return ResponseEntity.status(HttpStatus.OK).body(menuResponseDto);

    }

    @PatchMapping("{id}")
    public ResponseEntity<MenuResponseDto> changeMenu
            (@PathVariable Long id, @Validated(OnUpdate.class) @RequestBody MenuRequestDto menuRequestDto) {

        MenuResponseDto menuResponseDto = menuService.changeMenu(id, menuRequestDto);

        return ResponseEntity.status(HttpStatus.OK).body(menuResponseDto);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteMenu(@PathVariable Long id) {

        menuService.deleteMenu(id);

        return ResponseEntity.noContent().build();

    }

}