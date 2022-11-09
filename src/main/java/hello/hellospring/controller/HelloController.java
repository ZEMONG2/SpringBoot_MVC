package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    /*
    스프링 빈이란
    스프링 빈은 스프링 컨테이너에 의해 관리되는 자바 객체(POJO)를 의미한다.
     */

    /*
    스프링 컨테이너란
    스프링 컨테이너는 스프링 빈의 생명 주기를 관리하며, 생성된 스프링 빈들에게 추가적인 기능을 제공하는 역할을 한다.
    IOC와 DI의 원리가 스프링 컨테이너에 적용된다
    일반적으로 new연산자, 인터페이스 호출, 팩토리 호출 방식으로 객체를 생성하고 소멸하지만,
    스프링 컨테이너를 사용하면 해당 역할을 대신해 준다. 즉, 제어 흐름을 외부에서 관리하게 된다.
    또한, 객체들 간의 의존 관계를 스프링 컨테이너가 런타임 과정에서 알아서 만들어 준다.
     */

    /*
    @Controller : Presentation Layer에서 Controller를 명시하기 위해 사용 / 해당 클래스를 스프링 MVC 모듈의 컨트롤러로 인식하게 해준다.
    @Service : Business Layer에서 Service를 명시하기 위해 사용 / 특별한 처리를 해주는 것은 아니지만, 명시적으로 비지니스 로직을 처리하는 클래스라는 것을 보여준다.
    @Repository : Persistence Layer에서 DAO를 명시하기 위해 사용 / 해당 클래스를 스프링 데이터 접근 계층으로 인식하고, 데이터 계층의 예외를 한 단계 추상화하여
    스프링 예외로 변환시켜 던져준다. (스프링의 DataAccessException) 이로 인해 DB마다 다른 예외가 일어나도 서비스단에 영향을 끼치지 않게 된다.
    @Configuration : 해당 클래스를 스프링 설정 정보라고 인식한다.
    또한 코드생성 라이브러리[CGLIB(Code Generator Library)]를 통해 조작된 클래스가 빈으로 등록되게 하며, 이로인해 빈들을 싱글톤으로 관리할 수 있다.
    @Component : 그 외에 자동으로 스캔해서 등록하고 싶은 것들을 위해 사용
    (+ 추가 @Component의 구체화된 형태로 @Repository, @Service, @Controller, @Configuration)

    각 레이어의 부가 설명
    Presentation Layer : 클라이언트와 최초로 만나는 곳으로 데이터 입출력이 발생하는 곳이다.
    Business Layer : 컨트롤러와 뷰를 연결해 주는 곳이다.
    Persistence Layer : 데이터 베이스에 접근하는 계층이다.
     */

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

    @GetMapping("hello-mvc")  // 쿼리스트링 가져오기 - required = true 가 디폴트라서 선언한 name이 없을경우 페이지를 불러올 수 없다.
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
    @ResponseBody //api
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
