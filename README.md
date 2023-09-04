# Seoulmate - 서울시 공공서비스 커뮤니케이션 사이트

![seoul mate logo](https://github.com/lazygyu97/seoulmate/assets/105356296/436eafb1-ae8c-40bc-a822-12e681503cec)


- 서울시에서 제공하는 진료,  문화 행사,  교육,  시설 대관,  체육 시설,  공공 서비스,  예약 등의 정보를 사용자들에게 제공한다.
- 참고 : 아래의 공공 API
    
          https://www.data.go.kr/data/15051731/openapi.do
- 서울시 내의 근거리 거주자들 간 행사 동행, 교육 동반 등 커뮤니케이션 공간으로 활용한다.


# 기술 스택
<img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white"> <img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white">
## Back-End
<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
<img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white"> <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"> <img src="https://img.shields.io/badge/amazonaws-232F3E?style=for-the-badge&logo=amazonaws&logoColor=white"> 
<img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white">

## Front-End
<img src="https://img.shields.io/badge/vue.js-4FC08D?style=for-the-badge&logo=vue.js&logoColor=white">

# 프로젝트 요구 사항
<details>
<summary> 학습 목표 </summary>
  
#### 1. 공공 API에서 제공받은 데이터 관리하기
    
공공 API 제공하는 다양한 데이터들을 적절히 가져와서 활용하는 경험과 스케줄을 통한 자동 업데이트를 활용한다.
     
    
#### 2. 대규모 데이터 처리 경험하기

공공 API에서 제공하는 데이터베이스를 자체 데이터베이스로 옮겨 대규모 데이터베이스를 구성하고 Jmeter, nGrinder 와 같은 부하 테스트 도구를 활용하여 처리 시간의 개선을 경험해본다. 

#### 3. CI/CD 구축하기

CI/CD를 위한 파이프라인을 구성하고, 안정감 있는 서비스를 배포하여 운영하는 환경을 구성해본다.

#### 4. SSE와 웹소켓을 적용해보기

 실시간으로 사용자에게 데이터를 전달하기 위해 SSE와 웹소켓에 대해 연구해보고, 이를 채팅 기능 및 알림 기능에 구현해본다.

#### 5. Test Code 구현, 적용

</details>



<details>
<summary>필수 기능</summary>

#### 1. 로그인 / 회원가입
- jwt 토큰 발급을 통한 로그인
- 소셜 로그인 (카카오, 구글, 네이버)
- 일반 로그인 
- `맞춤 공공 서비스 제공 용도` 관심사 입력 받기
- `맞춤 공공 서비스 제공 용도` 거주지 / 소득 분위 등 사용자 정보 입력 받기
- 이메일 인증 후 회원가입

#### 2. 메인 페이지 
- 지역별 맞춤 게시물
- 전체 게시물 
- s3를 통한 이미지 업로드 기능

#### 3. 공공 서비스 페이지
- 공공 API를 활용한 서울시 공공 서비스 조회 (카테고리 별 조회, 검색 조회)
- 조회한 공공 서비스 정보들을 데이터베이스에 저장 및 업데이트
  - 스케줄링을 사용하여 주기적으로 업데이트
- 관심있는 공공 서비스 스크랩

#### 4. 게시판 페이지
- 지역별 맞춤 게시물
- 말머리를 통한 게시물 작성 및 조회
- 페이징 처리를 통한 게시물 조회 (전체, 인기 ,지역 별 맞춤 게시물)

</details>

<details>
<summary>추가 기능</summary>

#### 1. 로그인 / 회원 가입
- 휴대폰 인증을 통한 회원 가입
- API를 통한 주소 검색
- Redis를 활용한 리프레시 토큰 및 로그아웃
- 자동 로그인
- 최근 3회 이내 사용한 비밀번호는 재사용 불가
- 회원 탈퇴
    
    
#### 2. 메인 페이지
- 공공 API를 활용한 서울시 공공 서비스 추천 (Redis 사용)
- 인기 게시물 보여주기 (Redis 사용)

</details>

# 프로젝트 명세

## [API 명세서](https://www.notion.so/S-A-5-SEOUL-mate-29412467908b4fffa0dc3b1ac01d5d51?pvs=4#824aa7ce20904c05b7bf17f7e4a09152)

## ERD 다이어그램

<hr>

## 팀원

| 리더 | 부리더 | 팀원 | 팀원 |
|------|------|------|------|
| 박현규| 조아영 | 김진희 | 임남명 |
| [GitHub](https://github.com/lazygyu97) | [GitHub](https://github.com/ayboori) | [GitHub](https://github.com/hee0hee) | [GitHub](https://github.com/nm0628) |
| [Velog](https://velog.io/@code_park) | [Velog](https://velog.io/@ayoung3052) | [Velog](https://velog.io/@heeh) | [Velog](https://velog.io/@nmuud) |
