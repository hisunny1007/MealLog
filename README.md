# 🥗 MealLog
> ### 하루 식단 기록과 분석, AI 기반 추천까지 한 번에! <br> 누구나 쉽고 간편하게 하는 식단 기록
</br>

**MealLog**는 '건강한 식습관 관리에 대한 관심은 높지만, 매번 기록하는 것은 번거롭다'는 문제의식에서 출발한 **AI 기반 맞춤형 식단 관리 서비스**입니다.

단순한 칼로리 기록을 넘어, **AI가 식단을 분석**하고 피드백을 주며, 유저에게 개인화된 **제품을 추천**합니다. 직관적인 캘린더 UI와 리워드 시스템을 통해 사용자가 식단 관리를 '숙제'가 아닌 '즐거운 습관'으로 느낄 수 있도록 돕습니다.
<br>

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
* **포인트샵:** 적립된 포인트로 제품 교환 가능 (추후 구매 기능 확장 예정)

<hr>

## 📖 프로젝트 개요
- 프로젝트 기간: 2025.11.29. ~ 2025.12.25.
- 프로젝트 참여인원: 2명
- 팀장: 차윤선
- 팀원: 윤상현
</br>

## 📝 목표 및 페르소나
**✔️ 식단 기록 지속성**
* **직관적인 UI:** 복잡한 입력 없이 누구나 쉽게 사용할 수 있는 캘린더 기반 인터페이스 제공
* **리워드 시스템:** 꾸준한 기록에 대한 포인트 보상을 통해 동기 부여 및 습관 형성 유도
* **스마트 검색:** 방대한 음식 데이터베이스와 자동완성 기능으로 기록 시간 단축

**✔️ 개인화된 제품 추천 경험**
* **AI 맞춤 분석:** 사용자의 신체 정보와 목표에 따른 영양소 섭취 현황을 실시간 분석
* **초개인화 추천:** 부족하거나 과한 영양소를 파악하여, 이를 보완할 수 있는 구체적인 식단 및 제품 추천

**👤김지원 (30세, 직장인) - "다이어트"**
* **특징:** 체중 감량이 목표지만, 바쁜 업무로 인해 식단 관리에 시간을 쏟기 어려움.
* **Needs:** 간편하고 빠른 식단 기록, 칼로리 및 영양소의 직관적 확인, 지속적인 동기 부여.

**👤박민수 (24세, 헬스 초보) - "근육 증가"**
* **특징:** 운동을 시작했으나 식단 구성에 어려움을 겪음. (일명 '헬린이')
* **Needs:** 단백질/탄수화물 등 정확한 영양소 비율(Macro) 관리, 목표에 맞는 구체적인 식단 가이드.
</br>

## 🧩 고객 여정 지도
1.  **목표 설정:** 회원가입 시 다이어트, 근육 증가 등 개인 목표 및 신체 정보 입력
2.  **식단 기록:** '스마트 검색'을 통해 섭취한 음식을 빠르고 간편하게 캘린더에 등록
3.  **상태 분석:** AI가 분석한 '데일리 리포트'와 직관적인 그래프로 오늘의 영양 섭취 확인
4.  **피드백 & 추천:** 부족한 영양소를 채워줄 수 있는 맞춤형 음식 및 제품 추천 받기
5.  **리워드 및 포인트샵:** 기록 완료 후 포인트를 적립하고 제품 교환에 사용 -> 꾸준한 습관 형성
</br>

## 🛠️ 기술 스택
* Backend: Java, Spring Boot, MyBatis
* Frontend: Vue 3, Vite, Axios, Vue-Router, Bootstrap, FullCalendar
* Database: MySQL
* AI: GPT-4.1
* Collaboration / Design Tools: Git, GitHub, Swagger, Figma, Notion, Canva
</br>

## 🏗️ 프로젝트 구조
### Backend
```
meal-log-backend/
├── src
│   └── main
│       ├── java
│       │   └── com
│       │       └── yunyun
│       │           └── meallog
│       │               ├── ai                      
│       │               │   ├── AiAnalysisResponse
│       │               │   ├── AiController
│       │               │   └── AiService
│       │               │
│       │               ├── food                    
│       │               │   ├── controller
│       │               │   ├── dao
│       │               │   ├── domain
│       │               │   ├── dto
│       │               │   └── service
│       │               │
│       │               ├── global                  
│       │               │   ├── common
│       │               │   ├── config               
│       │               │   ├── exception            
│       │               │   ├── interceptor          
│       │               │   └── jwt                  
│       │               │
│       │               ├── meal                   
│       │               │   ├── controller
│       │               │   ├── dao
│       │               │   ├── domain
│       │               │   ├── dto
│       │               │   └── service
│       │               │
│       │               ├── pointshop               
│       │               │   ├── controller
│       │               │   ├── dao
│       │               │   ├── domain
│       │               │   ├── dto
│       │               │   └── service
│       │               │
│       │               ├── user                     
│       │               │   ├── controller
│       │               │   ├── dao
│       │               │   ├── domain
│       │               │   ├── dto
│       │               │   └── service
│       │               │
│       │               └── MealLogApplication      
│       │
│       └── resources
│           ├── mappers                         
│           │   ├── FoodMapper.xml
│           │   ├── MealMapper.xml
│           │   ├── OrderMapper.xml
│           │   ├── ProductMapper.xml
│           │   └── UserMapper.xml
│           │
│           ├── static                           
│           │   ├── food_data.sql
│           │   ├── product_data.sql
│           │   └── schema.sql
│           │
│           └── application.properties          
└── uploads          
        └── default          
```

### Frontend
```
meal-log-backend/
├── public
│   └── favicon.png
└── src
    ├── api                          
    │   ├── aiApi.js
    │   ├── authApi.js
    │   ├── axios.js
    │   ├── foodApi.js
    │   ├── mealApi.js
    │   └── pointshop.js
    │
    ├── assets                       
    │   └── style
    │       ├── common.css
    │       ├── main.css
    │       └── variable.css
    │
    ├── components                 
    │   ├── analysis                   
    │   │   ├── AnalysisContent.vue
    │   │   ├── AnalysisEmpty.vue
    │   │   └── AnalysisPreview.vue
    │   ├── common                   
    │   │   ├── Header.vue
    │   │   ├── Main.vue
    │   │   └── Modal.vue
    │   ├── daily                    
    │   │   ├── DoughnutChart.vue
    │   │   ├── MealAddCard.vue
    │   │   ├── MealItemCard.vue
    │   │   ├── MealTimeSection.vue
    │   │   └── ProductRecommendation.vue
    │   ├── meal                    
    │   │   ├── MealCard.vue
    │   │   ├── MealForm.vue
    │   │   └── MealTimeLabel.vue
    │   └── pointshop               
    │       ├── ProductCard.vue
    │       └── PurchaseModal.vue
    │
    ├── router                     
    │   └── index.js
    │
    ├── stores                       
    │   ├── authStore.js
    │   └── counter.js
    │
    ├── views                        
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
    └── App.vue                     
```

## ⚙️ 로컬 설치 및 실행 방법
#### FrontEnd
```
cd frontend
npm install
npm run dev
```

#### BackEnd
```
cd backend
./gradlew bootrun
```
</br>

## 시연 영상
- 신규 유저 온보딩 UI 및 튤팁 가이드 적용

https://github.com/user-attachments/assets/55d717ff-b2a1-49bb-aeea-5ecaceb7b119

- 식단 등록 및 AI 분석 리포트


https://github.com/user-attachments/assets/18265894-cb6b-4cbf-8afc-a24c7e3e4f6e

- 포인트샵 교환


https://github.com/user-attachments/assets/e916ea67-b2a0-4651-9829-cb49e92164cb



- 회원가입


https://github.com/user-attachments/assets/a4ddaaa4-8e51-4ce5-9c1c-cb40d36b7680


## 👥 팀원 소개
### 차윤선 (팀장)
* Backend
  * 식단 CRUD API 설계 및 구현
  * 통계청 음식 데이터 DB 초기 구축 및 자동완성 검색 API 구현
  * AI 기반 식단 분석 및 맞춤 추천 기능 구현
  * 캘린더 기반 식단 조회/연동 API 구현
* Frontend
  * 전체 화면 레이아웃 및 CSS 구조 설계
  * 캘린더, 식단, 음식 데이터 연동 및 화면 표시 기능 구현
  * 모달, 토스트, 오버레이, 튤팁, 버튼 UI 컴포넌트 전반 설계 및 구현
  * 로고 및 파비콘 제작 적용

### 윤상현 (팀원)
* Backend
  * user CRUD 기능 및 JWT 인증 기반 로그인 기능 구현
  * pointshop 카테고리 별 제품조회 및 포인트 교환 기능 구현
  * AI API 활용 recommendtaion 기능 구현

* Frontend
  * auth / api연동 / signup / login / pointshop / orderhistory / mypage / productcard / modal 구현


## 소감

### 차윤선
이번 프로젝트에서는 풀스택으로 서비스 핵심 기능과 사용자 경험을 설계 및 구현했으며 팀장으로서 프로젝트 구조 설계, 기술 스택 선정, 일정 관리 등 개발 전반의 방향성을 잡았다. </br>
</br>

백엔드에서는 식단 CRUD API, 통계청 데이터 기반 자동완성 검색, AI 기반 식단 분석 및 맞춤 추천 기능을 구현하고, 프론트와의 데이터 연동까지 책임졌다. </br>
특히 자동완성 검색 기능은 현재 MySQL 기반으로 구현했지만, 향후 Elasticsearch 등 전문 검색 엔진을 활용하여 성능과 사용자 경험을 더욱 확장하고 개선해보고 싶다.</br>
</br>

프론트엔드에서는 실제 사용자가 서비스를 보다 직관적이고 편리하게 이용할 수 있도록 고민하며, 사용자 관점에서 발생할 수 있는 예외 상황을 고려했다.</br>
특히 캘린더 식단 기록 시 직관적인 시각적 표시를 구현하고, 신규 유저 온보딩 오버레이와 튤팁을 통해 처음 사용하는 유저가 서비스 흐름을 쉽게 이해하도록 안내하는 기능을 설계·구현하며 많은 공부를 했다.</br>
</br>

개발 과정에서는 팀원과의 협업과 일정 조율을 경험하며, 서로의 속도를 맞추고 의견을 조율하는 과정에서 프로젝트 관리와 소통 능력을 많이 배웠다.</br>
이번 경험을 통해 사용자 중심 사고, 예외 처리 및 UX 개선, 백엔드-프론트 통합 설계, 협업 능력까지 성장할 수 있었던 좋은 경험이라고 생각한다.</br>
</br>

### 윤상현
> 프로젝트를 통해 설계부터 구현, 점검까지 서비스를 완성하는 전체 개발과정을 경험할 수 있었다.

> 개발하는 과정에서 배웠던 내용을 복기하고 필요한 부분을 추가 학습을 통해 지식을 확장할 수 있었다. 개발을 하며 지속적으로 학습할 수 있어 역량 향상이 많은 도움이 되었다.

> 또한 기획 단계의 중요성을 깊이 체감할 수 있었다. 기획이 미흡했던 부분은 개발 및 점검 과정에서 오류가 발생했고 이를 수정하면서 개발 진행이 더뎌지는 문제가 생겼다. 따라서 아주 기본적인 기능이더라도 사용자의 입장에서 충분히 고민하고 기획한 후에 개발을 진행해야 한다는 것을 느꼈다. 물론 기획과 개발 과정에 있어 적절한 시간 분배도 매우 중요하다. 다음 프로젝트에서는 어느 한족에 치우치지 않도록 하는 것을 염두에 두어야겠다.

> 이번 프로젝트에서는 기능 구현에 많은 시간을 할애하여 구현 후, 성능 개선에 신경쓰지 못했다. AI 기능에서 식단 메뉴를 분석하고 카테고리를 카운트하는 기능은 구현했지만, 정확도와 일관성을 확보하지 못했다고 느꼈다. 추후에 프롬프트를 더 구체적으로 작성하거나, 조건들을 추가하는 등 기능의 성능을 개선하고자 한다.
