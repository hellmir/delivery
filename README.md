# 배달 애플리케이션 API Server
<br>

## 프로젝트 설명
- 음식을 주문할 수 있는 배달 애플리케이션 API Server

## Server Endpoints

### 회원

- **관리자**
  - **전체 회원 조회 : /members/{role}** (GET method)
<br><br>

- **회원 가입 : /members** (POST method)
<br><br>

- **회원 정보 조회 : /members/{id}** (GET method)
- **회원 정보 수정 : /members/{id}** (PATCH method)
- **회원 탈퇴 : /members{id}** (DELETE method)

### 가게

- **가게 조회**
  - **가게 기본 페이지 : /shops** (GET method)
  - **가게 상세 페이지 : /shops/{shopId}/** (GET method)
  - **가게 검색 : /shops/searches?searchWord=value** (GET method)
  <br><br>
  
- **판매자**
  - **가게 등록 : /shops** (POST method)

### 메뉴

- **메뉴 기본 페이지 : /menus** (GET method)
<br><br>

- **가게 메뉴 조회**
  - **메뉴 상세 조회 : /shops/{shopId}/menus/{id}** (GET method)
    <br><br>

- **판매자**
  - **메뉴 등록 : /shops/{shopId}/menus** (POST method)
  - **메뉴 수정 : /shops/{shopId}/menus{id}** (PATCH method)
  - **메뉴 삭제 : /shops/{shopId}/menus/{id}** (DELETE method)

### 주문

- **주문 조회**
  - **주문 기본 페이지 : /orders** (GET method)
    <br><br>

- **판매자**
  - **주문 진행/거절 : /orders/{id}** (PATCH method)
    <br><br>

- **고객**
  - **주문 생성 : /orders** (POST method)
  - **주문 취소 : /orders/{id}** (PATCH method)

### 장바구니

- **고객**
  - **장바구니 담기 : /carts** (POST method)
  <br><br>
  - **장바구니 메뉴 조회**
    - **장바구니 전체 메뉴 조회 : /carts** (GET method)
    - **장바구니 단일 메뉴 조회 : /carts/{id}** (GET method)
    <br><br>
  - **장바구니 메뉴 삭제 : /carts{id}** (DELETE method)
  - **장바구니 메뉴 주문 : /carts/orders** (POST method)

## Skills and Environment

- Java 17
- Spring Boot 3.0.4
- Gradle 7.6.1
- IntelliJ IDEA
- MariaDB 10.6.5
  <br>

## ERD

![ERD](https://github.com/hellmir/delivery/assets/128391669/f479e532-e68a-435b-ad51-fd75f2ad066c)

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
