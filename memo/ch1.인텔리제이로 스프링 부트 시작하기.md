# ch1. 인텔리제이로 스프링 부트 시작하기
>## 그레이들 프로젝트를 스프링 부트 프로젝트로 변경

> build.gradle
- 추후에는 스프링 이니셜라이져(https://start.spring.io)를 통해 진행하면됨
- 이니셜라이져 외에 추가로 의존성 추가가 필요할때를 대비하여 직접 코드 입력
```gradle
// 플러그인 의존성 관리를 위한 설정
buildscript{
    // ext라는 키워드는 build.gradle에서 사용하는 전역변수를 설정하겠다는 의미
    ext{
        // springBootVersion 전역변수를 생성하고 그 값을 2.1.7.RELEASE로 지정
        springBootVersion = '2.1.7.RELEASE'
    }
    repositories {
        mavenCentral()
        jcenter()
    }
    dependencies {
         // 즉, spring-boot-gradle-plugin 라는 스프링 부트 그레이들 플러그인의 2.1.7.RELEASE를 의존성으로 받겠다는 의미
        classpath("org.springframework.boot:spring-boot-gradle-plugin:${springBootVersion}")
    }
}

// 위에서 선언한 플로그인 의존성들을 적용할 것인지를 결정하는 코드
// 자바와 스프링 부트를 사용하기 위한 필수 플러그인 4가지
apply plugin: 'java'
apply plugin: 'eclipse'
apply plugin: 'org.springframework.boot'
// io.spring.dependency-management 플러그인은 스프링 부트의 의존성들을 관리해 주는 플러그인이라 꼭 추가해야함
apply plugin: 'io.spring.dependency-management'

group 'com.tongyo.book'
version '1.0-SNAPSHOT'
sourceCompatibility = 1.8

// 각종 의존성(라이브러리)들을 어떤 원격 저장소에 받을지 지정
// 기본적으로 mavenCentral을 많이 사용하지만, 최근에는 라이브러리 업로드 난이도 때문에 jcenter도 많이 사용
repositories {
    mavenCentral()
    jcenter()
}

// 프로젝트 개발에 필요한 의존성들을 선언하는 곳
// 특정 버젼을 명시하면 안됨. 버젼을 명시하지않아야만 맨위에 작성한 classpath("org.springframework.boot:spring-boot-gradle-plugin:${springBootVersion}") 버젼을 따라감
dependencies {
    compile('org.springframework.boot:spring-boot-starter-web')
    testCompile('org.springframework.boot:spring-boot-starter-test')
}

// 작성 완료 후 Enable Auto-import를 클릭해두면 자동 반영됨
```
----
> ## 인텔리제이에서 깃허브 연동
1. 액션 검색창 열기
    - 윈도우 단축키 : Ctrl + Shift + A
    - 맥 단축키 : Command + Shift + A
2. share project on github 검색
3. 해당 액션 선택 후 엔터버튼
4. 깃허브 로그인
5. 깃허브 프로젝트를 생성하고 Share 버튼 클릭 (동기화 과정에서 커밋 항목으로 추가할 것인지 묻는 안내문이 나온다면 No 선택)
6. 커밋을 위한 팝업창이 등장
7. .idea 디렉토리는 커밋하지않음. 이는 인텔리제이에서 프로젝트 실행시 자동으로 생성되는 파일들이기 때문에 불필요
----
> ## .gitignore 파일을 통해 커밋 예외 등록하기
- .ignore 플러그인이 지원하는 기능
    - 파일위치 자동완성
    - 이그노어 처리 여부 확인
    - 다양한 이그노어 파일 지원(.gitignore, .npmignore, .dockerignore 등등)
1. 액션 검색창 열기
    - 윈도우 단축키 : Ctrl + Shift + A
    - 맥 단축키 : Command + Shift + A
2. plugins 검색
3. 해당 액션 선택 후 엔터버튼
4. Marketplace 탭에서 .ignore 검색
5. 설치 후 반드시 인텔리제이 재시작
6. .gitignore 파일 생성
    - 템플릿 선택화면에서 미리 설정해둔 템플릿이 있다면 선택하여 바로 생성
    - 템플릿 선택화면에서 미리 설정해둔 템플릿이 없다면 아래 와 같이 입력
> .gitignore
``` .gitignore
# Project exclude paths
.gradle
.idea
```
-------
> ## git 단축키
- commit
    - 윈도우 : Ctrl + K
    - 맥 : Command + K
- push
    - 윈도우 : Ctrl + Shift + K
    - 맥 : Command + Shift + K