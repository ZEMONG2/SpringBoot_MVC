package hello.hellospring;

import hello.hellospring.aop.TimeTraceAop;
import hello.hellospring.repository.JpaMemberRepository;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/*
 * DI에는 필드주입, setter주입, 생성자주입 3가지 방법이 있다
 * 의존관계가 실행중에 동적으로 변하는 경우는 거의 없으므로 생성자 주입을 권장한다.
 *
 * 실무에서는 주로 정형화된 컨트롤러, 서비스, 리포지토리 같은 코드는 컴포넌트 스캔을 사용한다.
 * 그리고 정형화 되지 않거나, 상황에 따라 구현 클래스를 변경해야 하면 설정을 통해 스프링 빈으로 등록한다.
 *
 * @Autowired를 통한 DI는 helloController, MemberService등과 같이 스프링이 관리하는 객체에서만 동작한다
 * 스프링 빈으로 등록하지 않고 내가 직접 생성한 객체에서는 동작하지 않는다.
 *
 * 필드주입
 * 프레임워크 의존적 : 스프링 DI 컨테이너에서만 동작, 외부에서 수정 불가, 테스트의 어려움
 * final 선언 불가 : 객체 변경 가능
 *
 * setter주입
 * 스프링 3.x대 버전에서 추천되었던 방식으로, 현재는 주입받는 객체가 변경될 가능성이 있을 경우에만 사용되는 방식.
 *
 * 생성자주입
 * 현재 스프링에서 가장 권장하는 방식
 * 테스트 용이 : 프로엠워크 의존적이지 않아 순수 자바 등 외부 테스트 코드 작성 가능
 * 객체 불변성 확보: final 선언 가능, 유지보수 용이성
 * 순환 참조 에러가 발생할 경우 컴파일 시 판단 가능
 *
 * 생성자 주입 방식도 Lombok의 @RequiredArgsConstructor를 이용하면 코드를 단순화 할 수 있다.
 * */

@Configuration
public class SpringConfig {

    // 방법1
    // @Autowired
    // DataSource dataSource;

//    // 방법2
//    private DataSource dataSource;
//
//
//    @Autowired
//    public SpringConfig(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }
//
//    //

//    private EntityManager em;
//
//    @Autowired
//    public SpringConfig(EntityManager em) {
//        this.em = em;
//    }

//    @Bean
//    public MemberService memberService() {
//        return new MemberService(memberRepository());
//    }

    private final MemberRepository memberRepository;

    @Autowired // 생성자가 하나인 경우는 생략 가능
    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    // 스프링 컨테이너에서 MemberRepository를 찾는데 SpringDataJpaMemberRepository 인터페이스를 만들어 놓고
    // JpaRepository를 extends 하면 spring-data-jpa가 인터페이스에 대한 구현체를 만들고 스프링 빈에 등록 해놓는다

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository);
    }

//    @Bean // 특별한 것은 직접 스프링 빈에 등록해서 사용하는걸 선호한다.
//    public TimeTraceAop timeTraceAop(){
//        return new TimeTraceAop();
//    }

//    @Bean
//    public MemberRepository memberRepository() {
////        return new MemoryMemberRepository();
////        return new JdbcMemberRepository(dataSource);
////        return new JdbcTemplateMemberRepository(dataSource);
////        return new JpaMemberRepository(em);
//    }
}
