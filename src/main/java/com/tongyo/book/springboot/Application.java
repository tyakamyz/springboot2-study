package com.tongyo.book.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// 앞으로 만들 프로젝트의 메인 클래스
// @SpringBootApplication 를 선언하면 스프링 부트의 자동 설정, 스프링 Bean 읽기와 생성이 모두 자동으로 설정됨
// 특히나 @SpringBootApplication이 있는 위치부터 설정을 읽어가기 때문에 이 클래스는 항상 프로젝트의 최상단에 위치해야함!!!
@SpringBootApplication
public class Application {
    public static void main(String[] args){
        // SpringApplication.run으로 인해 내장 WAS를 실행
        // 내장 WAS란 별도로 외부에 WAS를 두지 않고 애플리케이션을 실행할 때 내부에서 WAS를 실행하는 것을 의미
        // 톰캣 설치가 필요 없으며, 스프링 부트로 만들어진 Jar 파일(실행가능한 Java 패키징 파일)로 실행하면 됨
        SpringApplication.run(Application.class, args);
    }
}