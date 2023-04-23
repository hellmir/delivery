package personal.delivery.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import personal.delivery.dto.MenuChangeDto;
import personal.delivery.dto.MenuDto;
import personal.delivery.dto.MenuResponseDto;
import personal.delivery.service.MenuService;

import java.util.List;


@RestController
@RequestMapping("menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    // 1. 클라이언트로부터 신메뉴 요청값 받음
    // 2. MenuDto를 통해 MenuService에 전달
    // 3. MenuService로부터 MenuResponseDto 응답값 받아서 ResponseEntity로 구현해 return
    @PostMapping()
    public ResponseEntity<MenuResponseDto> createMenu(@RequestBody MenuDto menuDto) {
        MenuResponseDto menuResponseDto = menuService.saveMenu(menuDto);

        return ResponseEntity.status(HttpStatus.OK).body(menuResponseDto);
    }

    // 1. 클라이언트로부터 ID 요청값 받음
    // 2. MenuService에 전달 후, MenuResponseDto 응답값 받아서 ResponseEntity로 구현해 return
    @GetMapping()
    public ResponseEntity<MenuResponseDto> getMenu(Long id) {
        MenuResponseDto menuResponseDto = menuService.getMenu(id);

        return ResponseEntity.status(HttpStatus.OK).body(menuResponseDto);
    }

    // 1. 클라이언트로부터 요청 받음
    // 2. MenuService에 전달 후, MenuResponseDto 응답값 받아서 ResponseEntity로 구현해 return
    @GetMapping("list")
    public ResponseEntity< List<MenuResponseDto> > getAllMenu() throws Exception {
        List<MenuResponseDto> menuResponseDtoList = menuService.getAllMenu();

        return ResponseEntity.status(HttpStatus.OK).body(menuResponseDtoList);
    }

    // 1. 클라이언트로부터 메뉴 수정 요청값 받음
    // 2. MenuChangeDto를 통해 MenuService에 전달
    // 3. MenuService로부터 MenuResponseDto 응답값 받아서 ResponseEntity로 구현해 return
    @PatchMapping
    public ResponseEntity<MenuResponseDto> changeMenu(@RequestBody MenuChangeDto menuChangeDto) throws Exception {
        MenuResponseDto menuResponseDto = menuService.changeMenu(menuChangeDto);

        return ResponseEntity.status(HttpStatus.OK).body(menuResponseDto);
    }

    // 1. 클라이언트로부터 ID 요청값 받음
    // 2. MenuService에 전달 후, name 응답값 받아서 ResponseEntity로 구현해 return
    @DeleteMapping
    public ResponseEntity<String> deleteMenu(Long id) throws Exception {
        String name = menuService.deleteMenu(id);

        return ResponseEntity.status(HttpStatus.OK).body(id + "번 " + name + " 메뉴가 삭제되었습니다.");
    }

}