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
@NoArgsConstructor  // 생성자 자동 추가
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
    - JPA를 사용하면 DB데이터에 작업할 경우 실제 쿼리를 날리기보다는, Entity 클래스의 수정을 통해 작업

    |어노테이션|설명|
    |----|----|
    |@Entity|- 테이블과 링크될 클래스임을 나타냄<br>- 기본값으로 클래스의 카멜케이스 이름을 언더스코어 네이밍(_)으로 테이블 이름을 매칭<br>ex) SaleManager.java -> sales_manager table|
    |@id|해당 테이블의 PK 필드를 나타냄|
    |@GeneratedValue|- PK의 생성 규칙을 나타냄<br>- 스프링 부트 2.0에서는 GenerationType.IDENTITY 옵션을 추가해야만 auto_increment가 됨<br>- 스프링 부트 2.0 버전과 1.5 버전의 차이 https://jojoldu.tistory.com 참고|
    |@Column|- 테이블의 컬럼을 나타내며 굳이 선언하지 않더라도 해당 클래스의 필드는 모두 컬럼이 됨<br>- 사용하는 이유는, 기본값 외에 추가로 변경이 필요한 옵션이 있으면 사용<br>- 문자열의 경우 VARCHAR(255)가 기본값인데, 사이즈를 늘리고 싶거나, 타입을 TEXT로 변경하고 싶을 경우 등에 사용|
-------
> ## Posts 클래스의 특이점
- Setter 메소드를 사용하지 않음
- 해당 클래스의 인스턴스 값들이 언제 어디서 변해야 하는지 코드상으로 명확하게 구분할 수 있도록 Setter 메소드를 만들지 않음
- 생성자 또는 builder 를 통해 최종값을 채운 후 DB에 삽입함
---------
> PostsRepository.java
- 보통 iBatis, MyBatis 등에서 Dao라 불리는 DB Layer 접근자를 JPA에선 Repository라고 부르며, 인터페이스로 생성
(@Repository를 추가할 필요도 없음)
- JpaRepository<Entity 클래스, PK 타입>를 상속하면 기본적인 CRUD 메소드가 자동으로 생성됨
- 주의점! Entity 클래스와 기본 Entity Repository는 함께 위치해야함
```java
package com.tongyo.book.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsRepository extends JpaRepository<Posts, Long> {

}
```
> PostsRepositoryTest.java
- @After
    - Junit에서 단위 테스트가 끝날 때마다 수행되는 메소드를 지정
    - 보통은 배포 전 전체 테스트를ㄹ 수행할 때 테스트간 데이터 침범을 막기 위해 사용
    - 여러 테스트가 동시에 수행되면 테스트용 데이터베이스인 H2에 데이터가 그대로 남아 있어 다음 테스트 실행 시 테스트가 실패할 수 있음
- @SpringBootTest
    - 별다른 설정없이 사용할 경우 H2 데이터베이스를 자동으로 실행
```java
package com.tongyo.book.springboot.domain.posts;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {
    @Autowired
    PostsRepository postsRepository;

    @After
    public void cleanup(){
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기() {
        //given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        postsRepository.save(Posts.builder() 
                .title(title)
                .content(content)
                .author("tyakamyz@gmail.com")
                .build());  // id값이 있다면 update. 없으면 insert

        //when
        List<Posts> postsList = postsRepository.findAll();  // posts 테이블에 있는 모든 데이터를 조회

        //then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }
}
```