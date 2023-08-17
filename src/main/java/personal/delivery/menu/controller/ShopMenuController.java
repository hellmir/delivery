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

        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(menuResponseDto);

    }

    @GetMapping("{id}")
    public ResponseEntity<MenuResponseDto> getMenu(@PathVariable Long shopId, @PathVariable Long id) {

        MenuResponseDto menuResponseDto = menuService.getMenu(shopId, id);

        return ResponseEntity.status(HttpStatus.OK).body(menuResponseDto);

    }

    @PatchMapping("{id}")
    public ResponseEntity<MenuResponseDto> changeMenu
            (@PathVariable Long shopId, @PathVariable Long id,
             @Validated(OnUpdate.class) @RequestBody MenuRequestDto menuRequestDto) {

        MenuResponseDto menuResponseDto = menuService.updateMenu(shopId, id, menuRequestDto);

        return ResponseEntity.status(HttpStatus.OK).body(menuResponseDto);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteMenu(@PathVariable Long shopId, @PathVariable Long id) {

        menuService.deleteMenu(shopId, id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }


    @DeleteMapping()
    public ResponseEntity<Void> deleteSeveralMenus(@PathVariable Long shopId, @RequestBody List<Long> ids) {

        menuService.deleteSeveralMenus(shopId, ids);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

}