package com.tongyo.book.springboot.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

/**
 *****@RunWith(SpringRunner.class)*****
 * 테스트를 진행할 때 Junit에 내장된 실행자 외에 다른 실행자를 실행
 * 여기서는 StringRunner라는 스프링 실행자를 사용
 * 즉, 스프링 부트 테스트와 Junit 사이에 연결자 역활을 함
 **/

/**
 *****@WebMvcTest*****
 * 여러 스프링 테스트 어노테이션 중, Web(Spring MVC)에 집중할 수 있는 어노테이션
 * 선언할 경우 @Controller, @ControllerAdvice 등을 사용할 수 있음
 * 단 @Service, @Component, @Repository 등은 사용할 수 없음
 **/
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = HelloController.class)
public class HelloControllerTest {
    // 스프링이 관리하는 빈(Bean)을 주입 받음
    @Autowired
    private MockMvc mvc;
    // 웹API를 테스트할 때 사용
    // 스프링 MVC 테스트의 시작점
    // 이 클래스를 통해 HTTP GET, POST 등에 대한 API 테스트를 할 수 있음

    @Test
    public void hello가_리턴된다() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(hello));
    }

    @Test
    public void helloDto가_리턴된다() throws Exception{
        String name = "hello";
        int amount = 1000;

        // param 사용시 String 값만 허용되기때문에 숫자나 날짜 등의 데이터를 등록할 때는 문자열로 변경 필요
        mvc.perform(get("/hello/dto").param("name", name).param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                // jsonPath는 Json 응답값을 필드별로 검증할 수 있는 메소드
                // $를 기준으로 필드명을 명시
                // 여기서는 name과 amount를 검증하니 $.name, $.amount로 검증
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.amount", is(amount)));
    }
}
