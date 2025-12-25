
# 프로젝트명: 🥗 MealLog
> **식단 관리, 이제는 쉽게! 누구나 쉽고 직관적으로, 밀로그와 함께**

## 1. 프로그램 개요
**MealLog**는 "건강한 식습관 관리에 대한 관심은 높지만, 매번 기록하는 것은 번거롭다"는 문제의식에서 출발한 **AI 기반 맞춤형 식단 관리 서비스**입니다.

단순한 칼로리 기록을 넘어, **AI가 식단을 분석**하고 피드백을 주며, 유저에게 개인화된 **제품을 추천**합니다. 직관적인 캘린더 UI와 리워드 시스템을 통해 사용자가 식단 관리를 '숙제'가 아닌 '즐거운 습관'으로 느낄 수 있도록 돕습니다.

## 2. 프로젝트 구조
<code>
backend
└── src
    └── main
        ├── java
        │   └── com
        │       └── yunyun
        │           └── meallog
        │               ├── ai                       // AI 분석 기능
        │               │   ├── AiAnalysisResponse
        │               │   ├── AiController
        │               │   └── AiService
        │               │
        │               ├── food                     // 음식 데이터 관련 도메인
        │               │   ├── controller
        │               │   ├── dao
        │               │   ├── domain
        │               │   ├── dto
        │               │   └── service
        │               │
        │               ├── global                   // 전역 설정 및 공통 모듈
        │               │   ├── common
        │               │   ├── config               // 설정 (Security, Swagger 등)
        │               │   ├── exception            // 예외 처리 핸들러
        │               │   ├── interceptor          // 요청 인터셉터
        │               │   └── jwt                  // 인증 토큰 관련
        │               │
        │               ├── meal                     // 식단 기록/관리 도메인
        │               │   ├── controller
        │               │   ├── dao
        │               │   ├── domain
        │               │   ├── dto
        │               │   └── service
        │               │
        │               ├── pointshop                // 포인트 상점 도메인
        │               │   ├── controller
        │               │   ├── dao
        │               │   ├── domain
        │               │   ├── dto
        │               │   └── service
        │               │
        │               ├── user                     // 회원/사용자 도메인
        │               │   ├── controller
        │               │   ├── dao
        │               │   ├── domain
        │               │   ├── dto
        │               │   └── service
        │               │
        │               └── MealLogApplication       // Spring Boot 실행 메인 클래스
        │
        └── resources
            ├── mappers                          // MyBatis XML 매퍼
            │   ├── FoodMapper.xml
            │   ├── MealMapper.xml
            │   ├── OrderMapper.xml
            │   ├── ProductMapper.xml
            │   └── UserMapper.xml
            │
            ├── static                           // SQL 스크립트 (초기 데이터)
            │   ├── food_data.sql
            │   ├── product_data.sql
            │   └── schema.sql
            │
            └── application.properties           // 환경 설정 파일
</code>

<code>
  frontend
├── .idea
├── node_modules
├── public
│   └── favicon.png
└── src
    ├── api                          // 서버 통신 (API 요청)
    │   ├── aiApi.js
    │   ├── authApi.js
    │   ├── axios.js
    │   ├── foodApi.js
    │   ├── mealApi.js
    │   └── pointshop.js
    │
    ├── assets                       // 정적 자원 (스타일, 이미지)
    │   └── style
    │       ├── common.css
    │       ├── main.css
    │       └── variable.css
    │
    ├── components                   // 재사용 UI 컴포넌트 모음
    │   ├── common                   // 공통 컴포넌트
    │   │   ├── Header.vue
    │   │   ├── Main.vue
    │   │   └── Modal.vue
    │   ├── daily                    // 일일 관리 관련 컴포넌트
    │   │   ├── DoughnutChart.vue
    │   │   ├── MealAddCard.vue
    │   │   ├── MealItemCard.vue
    │   │   ├── MealTimeSection.vue
    │   │   └── ProductRecommendation.vue
    │   ├── meal                     // 식단 상세 관련 컴포넌트
    │   │   ├── MealCard.vue
    │   │   ├── MealForm.vue
    │   │   └── MealTimeLabel.vue
    │   └── pointshop                // 포인트 상점 관련 컴포넌트
    │       ├── ProductCard.vue
    │       └── PurchaseModal.vue
    │
    ├── router                       // 페이지 라우팅 설정
    │   └── index.js
    │
    ├── stores                       // 전역 상태 관리 (Pinia)
    │   ├── authStore.js
    │   └── counter.js
    │
    ├── views                        // 실제 페이지 화면
    │   ├── CalendarView.vue
    │   ├── LoginView.vue
    │   ├── MealCreateView.vue
    │   ├── MealDailyAnalysisView.vue
    │   ├── MealDailyView.vue
    │   ├── MyPageView.vue
    │   ├── PointHistoryView.vue
    │   ├── PointShopView.vue
    │   └── SignUpView.vue
    │
    └── App.vue                      // 최상위 루트 컴포넌트
</code>

## 3. 기술스택
Backend: Java, Spring Boot, MyBatis, MySQL
Frontend: Vue.js
AI: GPT-4.1
협업 도구: Git, Swagger, Figma, Notion, 미리캔버스

## 4. 팀원 소개
### 차윤선 (팀장)


### 윤상현 (팀원)
* 풀스택

* Backend
  * user, pointshop
  * AI API 활용 recommendtaion 구현

* Frontend
  * auth / sighup / login / pointshop / orderhistory / mypage

* DB
  * users / pruducts / orders

* 기타
  * 일정 관리
  * 발표 및 ppt
  * README

## 5. 목표
MealLog는 사용자가 겪는 식단 관리의 어려움을 해결하기 위해 두 가지 핵심 목표를 가집니다.

### 1) 식단 기록 지속성 (Sustainability)
* **직관적인 UI:** 복잡한 입력 없이 누구나 쉽게 사용할 수 있는 캘린더 기반 인터페이스 제공
* **리워드 시스템:** 꾸준한 기록에 대한 포인트 보상을 통해 동기 부여 및 습관 형성 유도
* **스마트 검색:** 방대한 음식 데이터베이스와 자동완성 기능으로 기록 시간 단축

### 2) 개인화된 제품 추천 경험 (Personalized Experience)
* **AI 맞춤 분석:** 사용자의 신체 정보와 목표에 따른 영양소 섭취 현황을 실시간 분석
* **초개인화 추천:** 부족하거나 과한 영양소를 파악하여, 이를 보완할 수 있는 구체적인 식단 및 제품 추천

## 6. 페르소나 (Persona)
서비스 기획 단계에서 구체적인 타겟 유저를 설정하여 기능을 구체화했습니다.

### 👤 Persona 1: 김지원 (30세, 직장인) - "다이어트"
* **특징:** 체중 감량이 목표지만, 바쁜 업무로 인해 식단 관리에 시간을 쏟기 어려움.
* **Needs:** 간편하고 빠른 식단 기록, 칼로리 및 영양소의 직관적 확인, 지속적인 동기 부여.

### 👤 Persona 2: 박민수 (24세, 헬스 초보) - "근육 증가"
* **특징:** 운동을 시작했으나 식단 구성에 어려움을 겪음. (일명 '헬린이')
* **Needs:** 단백질/탄수화물 등 정확한 영양소 비율(Macro) 관리, 목표에 맞는 구체적인 식단 가이드.

## 7. 고객 여정 지도 (Customer Journey Map)
사용자가 MealLog를 통해 경험하는 흐름은 다음과 같습니다.

1.  **목표 설정:** 회원가입 시 다이어트, 근육 증가 등 개인 목표 및 신체 정보 입력
2.  **식단 기록:** '스마트 검색'을 통해 섭취한 음식을 빠르고 간편하게 캘린더에 등록
3.  **상태 분석:** AI가 분석한 '데일리 리포트'와 직관적인 그래프로 오늘의 영양 섭취 확인
4.  **피드백 & 추천:** 부족한 영양소를 채워줄 수 있는 맞춤형 음식 및 제품 추천 받기
5.  **리워드 및 포인트샵:** 기록 완료 후 포인트를 적립하고 제품 구매에 사용 -> 꾸준한 습관 형성

## 8. 핵심 기능

### 📅 1. 식단 기록 캘린더
* **직관적인 조회:** 월별/일별 캘린더 UI를 통해 나의 식단 기록을 한눈에 파악
* **체계적 관리:** 아침, 점심, 저녁으로 분류하여 기록 및 수정/삭제 가능

### 🔍 2. 스마트 음식 검색
* **자동완성 검색:** 방대한 음식 DB를 기반으로 검색어 입력 시 실시간 리스트 표시
* **커스텀 등록:** DB에 없는 음식은 직접 추가하여 관리 가능

### 📊 3. 맞춤형 영양 분석
* **시각적 리포트:** 탄단지(탄수화물, 단백질, 지방) 비율을 그래프로 시각화
* **목표 달성 확인:** 권장 칼로리 대비 실제 섭취 칼로리를 자동 계산하여 목표 달성 여부 피드백

### 🤖 4. AI 식단 분석 & 추천
* **AI 영양 코치:** 24시간 식단 데이터를 분석하여 "단백질이 부족해요", "지방 섭취가 높아요" 등의 상세 피드백 제공
* **제품 추천:** 분석 결과를 토대로 실제 구매 가능한 식품(예: 삶은 계란, 닭갈비 볶음밥 등) 추천 및 연동

### 🎁 5. 리워드 시스템
* **포인트 적립:** 식단 기록 시 포인트 지급을 통해 지속적인 사용 유도
* **포인트샵:** 적립된 포인트로 제품을 앱 내에서 구매 가능

## 9. 기능 상세

### 로그인 / 회원가입

* **로그인**
  * email + password 입력
  * jwt 토큰 기반 로그인 
 
* **회원가입**
  * email, password, nickname 입력
  * email 형식 검증 및 중복확인
  * password 8 ~ 16자리 제한 및 재확인
  * nickname 중복 확인
  * 사용자 정보(성별, 운동 목표, 운동 빈도, 키, 몸무게, 나이) 입력
 
### 마이페이지

* **포인트샵**
  * 카테고리 별 제품 조회

* **교환내역 조회**
  * 교환 제품, 소진 포인트, 교환 날짜 확인

* **문의하기**
 * 문의 이메일 주소 안내

### AI 제품 추천

* **AI API 활용**
  * AI API를 호출하여 유저의 최근 7일 식단 기록을 조회하고 메뉴 이름을 분석
  * 카테고리를 분류하고 카운트하여 빈도를 파악
  * 분석 기반으로 products DB에서 일치하는 카테고리의 제품 추천

## 소감

### 차윤선


### 윤상현
> 프로젝트를 통해 설계부터 구현, 점검까지 서비스를 완성하는 전체 개발과정을 경험할 수 있었다.
> 개발하는 과정에서 배웠던 내용을 복기하고 필요한 부분을 추가 학습을 통해 지식을 확장할 수 있었다. 개발을 하며 지속적으로 학습할 수 있어 역량 향상이 많은 도움이 되었다.
> 또한 기획 단계의 중요성을 깊이 체감할 수 있었다. 기획이 미흡했던 부분은 개발 및 점검 과정에서 팀원으로 하여금 의문을 가지게 하였고 이를 수정하면서 개발 진행이 더디는 문제가 생겼다. 따라서 아주 기본적인 기능이더라도 사용자의 입장에서 충분히 고민하고 기획한 후에 개발을 진행해야 한다는 것을 느꼈다. 물론 기획과 개발 과정에 있어 적절한 시간 분배도 매우 중요하다. 다음 프로젝트에서는 어느 한족에 치우치지 않도록 하는 것을 염두에 두어야겠다.
> 이번 프로젝트에서는 기능 구현에 많은 시간을 할애하여 구현 후, 성능 개선에 신경쓰지 못했다. AI 기능에서 식단 메뉴를 분석하고 카테고리를 카운트하는 기능은 구현했지만, 정확도와 일관성을 확보하지 못했다고 느꼈다. 추후에 프롬프트를 더 구체적으로 작성하거나, 조건들을 추가해서 성능을 개선하고자 한다.
