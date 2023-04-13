package personal.delivery.domain;

public class Menu {

    private Long id; // identifier, primary key
    private String itemName; // 메뉴 이름
    private Integer price; // 가격
    private String flavor; // 맛
    private Integer portions; // 음식 양
    private Integer cookingTime; // 조리 시간
    private String menuType; // 메뉴 종류(메인메뉴/세트메뉴/사이드메뉴/음료/주류 등)
    private String foodType; // 음식 종류(치킨/피자/탕수육 등)
    private MenuOptions menuOptions; // 선택할 수 있는 옵션들
    private Integer salesRate; // 판매량
    private Integer stock; // 재료 재고(재고 이상 주문 불가)
    private Boolean popularMenu; // 인기 메뉴(일정 판매량 기준, 상단에 표시)]

    public Menu() {
    }

    public Long getId() {
        return id;
    }

    public String getItemName() {
        return itemName;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getFlavor() {
        return flavor;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }

    public Integer getPortions() {
        return portions;
    }

    public void setPortions(Integer portions) {
        this.portions = portions;
    }

    public Integer getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(Integer cookingTime) {
        this.cookingTime = cookingTime;
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public MenuOptions getMenuOptions() {
        return menuOptions;
    }

    public void setMenuOptions(MenuOptions menuOptions) {
        this.menuOptions = menuOptions;
    }

    public Integer getSalesRate() {
        return salesRate;
    }

    public void setSalesRate(Integer salesRate) {
        this.salesRate = salesRate;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Boolean isPopularMenu() {
        return popularMenu;
    }

    public void setPopularMenu(Boolean popularMenu) {
        this.popularMenu = popularMenu;
    }
}
