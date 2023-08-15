# 배달 애플리케이션 API Server
<br>

## URL

### 회원

- **회원 가입**
  - **판매자 : /members/sellers** (Post method)
  - **고객 : /members/customers** (Post method)

### 가게

- **가게 조회**
  - **전체 가게 조회 : /shops** (Get method)
  - **가게 검색 : /shops/searches?searchWord=value** (Get method)
  <br><br>
  
- **판매자**
  - **가게 등록 : /shops** (Post method)

### 메뉴

- **전체 메뉴 조회 : /menus** (Get method)
<br><br>
- **가게 메뉴 조회**
  - **가게 전체 메뉴 조회 : /{shopId}/menus** (Get method)
  - **메뉴 상세 조회 : /{shopId}/menus/{id}** (Get method)
    <br><br>

- **판매자**
  - **메뉴 등록 : /{shopId}/menus** (Post method)
  - **메뉴 수정 : /{shopId}/menus{id}** (Patch method)
  - **메뉴 삭제 : /{shopId}/menus/{id}** (Delete method)

### 주문

- **주문 조회**
  - **전체 주문 조회 : /orders** (Get method)
    <br><br>

- **판매자**
  - **주문 진행/거절 : /orders/{id}** (Patch method)
    <br><br>

- **고객**
  - **주문 생성 : /orders** (Post method)
  - **주문 취소 : /orders/{id}** (Patch method)

### 장바구니

- **고객**
  - **장바구니 담기 : /carts** (Post method)
  <br><br>
  - **장바구니 메뉴 조회**
    - **장바구니 전체 메뉴 조회 : /carts** (Get method)
    - **장바구니 단일 메뉴 조회 : /carts/{id}** (Get method)
    <br><br>
  - **장바구니 메뉴 삭제 : /carts{id}** (Delete method)
  - **장바구니 메뉴 주문 : /carts/orders** (Post method)

## Skills

- Java 17
- Spring Boot 3.0.4
- Gradle 7.6.1
- MariaDB 10.6.5
  <br>

## ERD

![ERD](https://github.com/hellmir/delivery/assets/128391669/8ee5b3a0-b144-44a3-a8b9-b955ebd761a7)

## Class Diagrams

### Member
![Member](https://github.com/hellmir/delivery/assets/128391669/228f6299-723b-4e79-aba3-f7b90768e287)

### Shop
![Shop](https://github.com/hellmir/delivery/assets/128391669/f8a1470e-60d0-4512-8c3c-4e7ec642f542)

### Menu
![Menu](https://github.com/hellmir/delivery/assets/128391669/91fb718f-182d-415e-8d66-76c50b0cef5a)

### Order
![Order](https://github.com/hellmir/delivery/assets/128391669/bc213e7e-bc8b-4b60-8021-ec2df3aece41)

### Cart
![Cart](https://github.com/hellmir/delivery/assets/128391669/5fb9e4d0-c9f2-4182-8fd6-4d1f8f0fd1a2)
