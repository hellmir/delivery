# 배달 애플리케이션 API Server
<br>

## URL

### 메뉴

- **메뉴 조회**
  - **전체 메뉴 조회 : /menu** (Get method)
  - **단일 메뉴 조회 : /menu/{id}** (Get method)
<br>

- **판매자**
  - **메뉴 등록 : /menu** (Post method)
  - **메뉴 수정 : /menu** (Patch method)
  - **메뉴 삭제 : /menu/{id}** (Delete method)
<br>

### 회원

- **회원 가입 : /members** (Post method)
<br>

### 주문

- **주문 조회 : /orders** (Get method)
<br>

- **판매자**
  - **주문 진행/거절 : /orders/{id}** (Patch method)
<br>

- **고객**
  - **주문 생성 : /orders** (Post method)
  - **주문 취소 : /orders/{id}** (Patch method)
<br>
 
### 장바구니

- **고객**
  - **장바구니 담기 : /carts** (Post method)
  - **장바구니 메뉴 조회**
    - **장바구니 전체 메뉴 조회 : /carts** (Get method)
    - **장바구니 단일 메뉴 조회 : /carts/{id}** (Get method)
  - **장바구니 메뉴 삭제 : /carts** (Delete method)
  - **장바구니 메뉴 주문 : /carts/orders** (Post method)
<br>

## Stack

- Java 17
- Spring Boot 3.0.4
- Gradle 7.6.1
- MariaDB 10.6.5
- Docker 4.18.0
<br>

## ERD

![ERD](https://github.com/hellmir/delivery/assets/128391669/c73f4217-025a-4933-b8c1-ea6568d9f2da)
