package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    /*
    컨트롤러에서 리턴 값으로 문자를 반환하면 viewResolver가 화면을 찾아서 처리한다.
    스프링 부트 템플릿 엔진 기본 viewName 매핑
    resource:templates/ + {ViewName} + .html
    */

    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data", "hello!!");
        return "hello";
    }

    @GetMapping("hello-mvc")  // 쿼리스트링 가져오기
    public String helloMvc(@RequestParam("name") String name, Model model) {
        model.addAttribute("name", name);
        return "hello-template";
    }

    @GetMapping("hello-string")
    @ResponseBody
    public String helloString(@RequestParam("name") String name) {
        return "hello "+ name;
    }


    /*
    @ResponseBody 를 사용
    Http의 Body에 문자 내용을 직접 반환
    viewResolver 대신에 HttpMessageConverter가 동작
    기본 문자처리 : StringHttpMessageConverter
    기본 객체처리 : MappingJackson2HttpMessageConverter
    byte 처리 등등 기타 여러 HttpMessageConverter가 기본으로 등록 되어있음

    * 참고 : 클라이언트의 HTTP Accept 해더와 서버의 컨트롤러 반환 타입 정보 둘을 조합해서
    HttpMessageConverter가 선택된다.
    * */

    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name){
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }
    static class Hello {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    /*
    일반적인 웹 애플리케이션 계층 구조
    컨트롤러 : 웹 MVC의 컨트롤러 역할
    서비스 : 핵심 비지니스 로직 구현
    리포지토리 : 데이터베이스에 접근, 도메인 객체를 DB에 저장하고 관리
    도메인 : 비지니스 도메인 객체, 예) 회원, 주문, 쿠폰 등등 주로 데이터베이스에 저장하고 관리
     */
}
