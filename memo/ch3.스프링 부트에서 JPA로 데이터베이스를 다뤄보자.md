# ch3.스프링 부트에서 JPA로 데이터베이스를 다뤄보자
> ## 프로젝트에 Spring Data Jpa 적용하기

> build.gradle
- 의존성 등록
    - spring-boot-starter-data-jpa
    - 스프링 부트용 Spring Data Jpa 추상화 라이브러리
    - 스프링 부트 버전에 맞춰 자동으로 JPA관련 라이브러리들의 버전을 관리해줌
- h2
    - 인메모리 관계형 데이터베이스
    - 별도의 설치가 필요 없이 프로젝트 의존성만으로 관리할 수 있음
    - 메모리에서 실행되기 때문에 애플리케이션을 재시작할 때마다 초기화된다는 점을 이용하여 테스트 용도로 많이 사용됨
```gradle
dependencies {
    compile('org.springframework.boot:spring-boot-starter-web')
    compile('org.projectlombok:lombok')
    compile('org.springframework.boot:spring-boot-starter-data-jpa')
    compile('com.h2database:h2')
    testCompile('org.springframework.boot:spring-boot-starter-test')
}
```

-------
> Posts.java
- domain을 담을 패키지
- 도메인이란 게시글, 댓글, 회원, 정산, 결제 등 소프트웨어에 대한 요구사항 혹은 문제영역이라고 생각하면 됨
- DTO 패키지와 조금 결이 다름
```java
package com.tongyo.book.springboot.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder
    public Posts(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }
}
```
- Posts 클래스
    - JPA를 사용하면 DB데이터에 작업할 경우 실제 쿼리를 날리기보다는, Entity