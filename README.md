# readme.md

# TTC:TimeToCode

---

팀원 : 이예원 이세현 안채영 윤홍현 김선미

TeamManager : 이예원

ProductManager : 안채영

Design : 안채영 이예원

Idea : 김선미

### 주 개발 파트

Front : 이예원 이세현 안채영

Back : 윤홍현 김선미

## Introduction

---

- 저의 앱은 코딩을 하는 모든 사람에게 동기부여해주는 앱
- 원하는 챌린지에 참여를 해 인증을 할 때 마다 달력에 색을 채워나감
- 다른 사람과 함께 하는 챌린지를 통해 꾸준한 학습을 습관화 도모
- 1일 1코딩, 코딩 스터디 앱

## Development Environment

---

- Android Studio

**Kotlin**

1.7.0                                                                                            

**Java - Android**

8                                                                                                  

## Application Version

---

- SDK version : Android 12
- compileSDK : 31
- minSDK : 28
- targetSDK :31

## Description

---

# Part1. Front

### navActivity

- 사용자는 언제든 원하는 화면으로 이동할 수 있음
    
    **상단바**
    
    - 하트버튼 → 찜 챌린지 페이지로 이동
    - 로고 → 메인 페이지로 이동
    - 돋보기버튼 → 검색페이지로 이동
    
    **하단바**
    
    - certification버튼 → 인증센터로 이동
    - mainPage버튼 → 메인 페이지로 이동
    - challenge버튼 → 챌린지 페이지로 이동

### Screen Saver - 화면 보호기

Nav activity에서 데이터를 불러오는 동안 보이는 Fragment화면 클릭 시 Main Fragment로 이동

### Main Fragment - 메인 화면

Navgation bar의 MainPage 메뉴 또는 상단 로고 클릭 시 보이는 Fragment

- 회원 정보 (예: 떡볶이)
    
    사용자 프로필: 기본 이미지로 설정사용자 이름: UserProfile 객체의 name
    
- 챌린지 현황 (예: 진행 2, 완료 3, 개설 2)
    
    각 text view 클릭 시 진행 중인 챌린지, 완료한 챌린지, 개설한 챌린지 목록을 보여주는 fragment로 이동
    
- 캘린더뷰
    
    챌린지 인증한 날짜에 점 표시 MainFragment.kt 
    
- 메인 하단 공지사항, 고객센터, 버전정보
    
    메인 하단 각 버튼 클릭 시 해당 fragment로 이동****
    

---

### proceedingFragment, usercreatedFragment, doneFragment - 진행중, 개설, 완료 챌린지

- 유저의 현재 진행중, 완료, 개설 챌린지를 모두 볼 수 있음
- 챌린지 추가할때 설정한 endtime을 넘기지 않으면 진행중인 챌린지에 표시
- endtime을 넘기면 완료된 챌린지에 표시
- 자신이 만든 챌린지는 완료 챌린지에 표시

---

### favoriteFragment - 내가 찜한 챌린지

- nav바에서 하트버튼을 누르면 이동하는 화면
- 유저가 찜한 챌린지를 모두 볼 수 있음
- 찜 추가는 챌린지의 상세보기에서 ‘찜하기’버튼 클릭
- 찜 삭제는 favoriteFragment에서 ‘찜 취소하기’버튼 클릭

---

### Splash - 첫화면

- Splash → Login
- 애니메이션으로 화면이 점점 투명해짐
- 앱을 실행했을 때 로딩 중에 사용자에게 잠시 보였다가 사라짐

---

### Login - 로그인

- Login → Main 또는 Register
- 비밀번호가 틀렸을 때 떨리는 애니메이션으로 사용자가 빨리 알아챌 수 있도록 함
- 자동 로그인 구현

---

### Register - 회원가입

- Register → Login
- EditText의 내용이 적절하다면 우측에 체크 표시가 생김
- 계정 생성 버튼을 눌렀을 때 적절하지 않은 내용의 EditText에 떨리는 애니메이션을 줌

---

### Search - 검색창

- Main 상단 돋보기 아이콘 → Search
- 검색 결과 리스트에서 챌린지 선택 → 챌린지 상세 화면
- 검색창 클릭시 검색 결과 리스트 → 최근 검색 리스트
- 검색 버튼은 검색창 우측 돋보기 아이콘
- 최근 검색어 클릭 또는 검색 버튼 클릭시
    1. 최근 검색 리스트 → 검색 결과 리스트
    2. 키보드가 내려감
    3. 검색 결과 리스트 Refresh

---

### Certification Fragment - 인증 센터

- 각 챌린지마다 챌린지 대표 사진, 챌린지 이름, 챌린지 만든이, 인증하기 버튼, 갤러리 버튼으로 구성되어 있음
- 인증하기 → 인증하기로 이동
- 갤러리 → 기록센터로 이동
- 인증하기 까지는 들어가지나, 파이어베이스 연동 문제로 인해 사진 불러오기 불가능

---

### Record Fragment - 기록 센터

- 챌린지를 인증한 기록을 불러옴
- 상단의 화살표를 누르면 인증센터로 이동
- 인증 센터 비활성화로 인해 사진 불러오기 불가능

---

### Add Challenge Fragment - 챌린지 등록

1. 첫번째 화면
    - 챌린지명, 챌린지 대표 태그 생성, 챌린지 소개를 입력받음
    - 태그는 2개 이상 필수 생성이며 순서대로 대표 태그 2개가 챌린지 소개 화면에 뜸
    - 모든 내용을 입력해야 다음 버튼이 활성화됨
2. 두번째 화면
    - 챌린지의 대표 이미지 설정
    - 기본 이미지나 본인 앨범에서 선택가능
    - 사진 선택시 다음 버튼이 활성화
3. 세번째 화면
    - 챌린지 인증 빈도, 하루 인증 횟수, 인증 가능 시간, 챌린지 기간 등을 설정
    - 챌린지 기간만 설정하면 다음 버튼이 활성화
4. 네번째 화면
    - 인증 방법 설명과 인증 방법 사진 이미지 첨부
    - 사진 첨부하기 버튼을 누르면 핸드폰 앨범에서 가져올 것인지 사진을 직접 찍을 것인지 결정할 수 있음

---

### Challenge List Fragment - 챌린지 리스트

- 모든 챌린지 확인 가능
- New 챌린지 ( 새로 생긴 챌린지 ), 모든 챌린지를 볼 수 있음
- 챌린지 선택시 챌린지 상세화면 페이지로 넘어감

---

### Challege Detail Activity

- 챌린지 생성시 등록된 정보를 확인할 수 있음
- 찜하기 버튼 클릭 시 내가 찜한 챌린지에서 챌린지 확인 가능
- 하단 챌린지 함께하기 클릭시 챌린지 참여 현황에 해당 챌린지 확인 가능, 챌린지 인증 가능

---

### Support Fragment - 고객센터

- TTC 사용자들이 궁금한 점, 불편한 점, 개인정보, 이용약관에 대한 정보를 주는 화면
- 각각 제목 옆 버튼을 누르면 각 페이지로 이동

---

### Question Fragment - 자주 묻는 질문

- TTC 앱에서 가장 기본적인 사용 방법을 알려줌
- 각각 질문을 누르면 질문에 대한 답변이 밑으로 내려옴

---

### Notice Page Fragment - 공지사항

- 공지사항을 볼 수 있는 화면
- 공지사항 화면에서 제목 옆 화살표를 누르면 공지 사항 내용을 볼 수 있음

---

### Inquiry Fragment - 1:1 문의

- 앱 사용시 불편한 것을 제보하는 화면
- 제목과 내용을 채워야 문의하기 버튼을 누를 수 있음
- 문의하기 버튼을 누르면 문의 완료, 이전 화면으로 돌아감

---

# Part2. Back

## Development Environment

---

- Firebase
- goormIDE
- MySql
- Spring Boot

**Java - Spring Boot**

9                                                                                                 

**Maven**

3.3.9                                                                                           

**Gradle**

4.6                                                                                              

**Spring Boot**

2.0.1                                                                                           

MySql

8                                                                                                 

## 자바 코드 작성시 주의 사항

---

구름은 기본 위치가 정해져 있기 때문에 /src/main/java 이후를 기준으로 패키지 위치를 작성하였다.

## 프로젝트 실행시 주의 사항

---

Spring Boot 새 프로젝트를 생성 후, application.properties와 pom.xml파일 내용을 변경해야 한다.
새 프로젝트 안에 패키지 경로를 수정한 자바 파일을 추가해야 한다.

## 구름에서 프로젝트 시작하기

---

구름에서 spring boot와 mysql 프로젝트를 생성한 후 파일을 추가한다.

- MySQL 실행 방법 : service mysql start
- 백그라운드 실행 방법 : nopub mvn:spring-boot

## MySQL Table 구조

---
<img width="941" alt="db" src="https://user-images.githubusercontent.com/81704418/206715197-2b89f40d-2b9b-4659-95a0-351cb10bf210.png">

## Domain 주소

---

 `https://android-pkfbl.run.goorm.io`
