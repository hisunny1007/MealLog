package com.yunyun.meallog.user.domain;


import lombok.*;


import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

//@Entity
//@Table(name = "users")
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
//public class User implements UserDetails {
public class User {
    //    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 사용자 PK

    // 회원가입 페이지 1 (로그인 정보)
//    @Column(unique = true, nullable = false, length = 100)
    private String email;

    //    @Column(nullable = false, length = 255) // Bcrypt 해시를 위해 길이 255
    private String password;

    //    @Column(nullable = false, length = 50)
    private String nickname;




    // 회원가입 페이지 2 및 ERD 정보
//    @Column(nullable = false)
    private Integer age; // ERD에 NOT NULL로 명시되어 있음

    //    @Column(length = 1)
    private String gender; // CHAR(1)

    //    @Column(nullable = false)
    private Float height;

    //    @Column(nullable = false)
    private Float weight;

    //    @Column(length = 50)
    private String exerciseFrequency;

    //    @Column(length = 20)
//    @Enumerated(EnumType.STRING)
    private String exerciseGoal; // ERD의 ENUM 타입 반영

    // 리워드 포인트 및 메타데이터
//    @Column(nullable = false)
    private Integer rewardPoint = 0;

    //    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // 포인트 업데이트 메소드 (Service Layer에서 사용)
    public void updatePointBalance(int points) {
        this.rewardPoint += points;
    }
}
// --- Spring Security UserDetails 구현 ---
//    @Override public Collection<? extends GrantedAuthority> getAuthorities() { return Collections.emptyList(); }
//    @Override public String getUsername() { return email; }
//    @Override public boolean isAccountNonExpired() { return true; }
//    @Override public boolean isAccountNonLocked() { return true; }
//    @Override public boolean isCredentialsNonExpired() { return true; }
//    @Override public boolean isEnabled() { return true; }
//}