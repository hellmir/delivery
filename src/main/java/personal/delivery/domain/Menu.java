package personal.delivery.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Menu {

    private Long id; // identifier, primary key
    private String name; // 메뉴 이름
    private Integer price; // 가격
    private String flavor; // 맛
    private Integer portions; // 음식 양
    private Integer cookingTime; // 조리 시간
    private String menuType; // 메뉴 종류(메인메뉴/세트메뉴/사이드메뉴/음료/주류 등)
    private String foodType; // 음식 종류(치킨/피자/탕수육 등)
    private MenuOptions menuOptions; // 선택할 수 있는 옵션들
    private Integer salesRate; // 판매량
    private Integer stock; // 재료 재고(재고 이상 주문 불가)
    private Boolean popularMenu; // 인기 메뉴(일정 판매량 기준, 상단에 표시)
}
