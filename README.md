> 스프링 빈이란  
스프링 빈은 스프링 컨테이너에 의해 관리되는 자바 객체(POJO)를 의미한다.

> 스프링 컨테이너란  
스프링 컨테이너는 스프링 빈의 생명 주기를 관리하며, 생성된 스프링 빈들에게 추가적인 기능을 제공하는 역할을 한다.  
IOC와 DI의 원리가 스프링 컨테이너에 적용된다.  
일반적으로 new연산자, 인터페이스 호출, 팩토리 호출 방식으로 객체를 생성하고 소멸하지만,  
스프링 컨테이너를 사용하면 해당 역할을 대신해 준다. 즉, 제어 흐름을 외부에서 관리하게 된다.  
또한, 객체들 간의 의존 관계를 스프링 컨테이너가 런타임 과정에서 알아서 만들어 준다.  


> @Controller : Presentation Layer에서 Controller를 명시하기 위해 사용 /  
해당 클래스를 스프링 MVC 모듈의 컨트롤러로 인식하게 해준다.  
> 
> @Service : Business Layer에서 Service를 명시하기 위해 사용 /  
특별한 처리를 해주는 것은 아니지만, 명시적으로 비지니스 로직을 처리하는 클래스라는 것을 보여준다.  
>
> @Repository : Persistence Layer에서 DAO를 명시하기 위해 사용 /  
해당 클래스를 스프링 데이터 접근 계층으로 인식하고, 데이터 계층의 예외를 한 단계 추상화하여 스프링 예외로 변환시켜 던져준다.  
(스프링의 DataAccessException) 이로 인해 DB마다 다른 예외가 일어나도 서비스단에 영향을 끼치지 않게 된다.  
>
> @Configuration : 해당 클래스를 스프링 설정 정보라고 인식한다.  
또한 코드생성 라이브러리[CGLIB(Code Generator Library)]를 통해 조작된 클래스가 빈으로 등록되게 하며,  
이로인해 빈들을 싱글톤으로 관리할 수 있다.  
>
> @Component : 그 외에 자동으로 스캔해서 등록하고 싶은 것들을 위해 사용  
(+ 추가 @Component의 구체화된 형태로 @Repository, @Service, @Controller, @Configuration)

> 각 레이어의 부가 설명  
Presentation Layer : 클라이언트와 최초로 만나는 곳으로 데이터 입출력이 발생하는 곳이다.  
Business Layer : 컨트롤러와 뷰를 연결해 주는 곳이다.  
Persistence Layer : 데이터 베이스에 접근하는 계층이다.

> DI에는 필드주입, setter주입, 생성자주입 3가지 방법이 있다.  
의존관계가 실행중에 동적으로 변하는 경우는 거의 없으므로 생성자 주입을 권장한다.
> 
> 필드주입  
프레임워크 의존적 : 스프링 DI 컨테이너에서만 동작, 외부에서 수정 불가, 테스트의 어려움  
final 선언 불가 : 객체 변경 가능
>
> setter주입  
스프링 3.x대 버전에서 추천되었던 방식으로, 현재는 주입받는 객체가 변경될 가능성이 있을 경우에만 사용되는 방식.  
>
> 생성자주입  
현재 스프링에서 가장 권장하는 방식  
테스트 용이 : 프로엠워크 의존적이지 않아 순수 자바 등 외부 테스트 코드 작성 가능  
객체 불변성 확보: final 선언 가능, 유지보수 용이성  
순환 참조 에러가 발생할 경우 컴파일 시 판단 가능  
>
> 생성자 주입 방식도 Lombok의 @RequiredArgsConstructor를 이용하면 코드를 단순화 할 수 있다.

> 실무에서는 주로 정형화된 컨트롤러, 서비스, 리포지토리 같은 코드는 컴포넌트 스캔을 사용한다.  
그리고 정형화 되지 않거나, 상황에 따라 구현 클래스를 변경해야 하면 설정을 통해 스프링 빈으로 등록한다.

> 일반적인 웹 애플리케이션 계층 구조  
컨트롤러 : 웹 MVC의 컨트롤러 역할  
서비스 : 핵심 비지니스 로직 구현  
리포지토리 : 데이터베이스에 접근, 도메인 객체를 DB에 저장하고 관리  
도메인 : 비지니스 도메인 객체, 예) 회원, 주문, 쿠폰 등등 주로 데이터베이스에 저장하고 관리  

> 생성자에 @Autowired가 있으면 스프링이 연관된 객체를 스프링 컨테이너에서 찾아서 넣어준다.  
이렇게 객체 의존관계를 외부에서 넣어주는 것을 DI(Dependency Injection), 의존성 주입이라 한다.  
여기서는 @Autowired에 의해 스프링이 주입해준다.  
> 
> @Autowired를 통한 DI는 helloController, MemberService등과 같이 스프링이 관리하는 객체에서만 동작한다.  
스프링 빈으로 등록하지 않고 내가 직접 생성한 객체에서는 동작하지 않는다.

> @ResponseBody 를 사용  
Http의 Body에 문자 내용을 직접 반환  
viewResolver 대신에 HttpMessageConverter가 동작  
기본 문자처리 : StringHttpMessageConverter  
기본 객체처리 : MappingJackson2HttpMessageConverter  
byte 처리 등등 기타 여러 HttpMessageConverter가 기본으로 등록 되어있음 
>
> 참고 : 클라이언트의 HTTP Accept 해더와 서버의 컨트롤러 반환 타입 정보 둘을 조합해서  
HttpMessageConverter가 선택된다.
>
>  컨트롤러에서 리턴 값으로 문자를 반환하면 viewResolver가 화면을 찾아서 처리한다.  
스프링 부트 템플릿 엔진 기본 viewName 매핑  
resource:templates/ + {ViewName} + .html

> 컴포넌트 스캔 원리  
@Component 어노테이션이 있으면 스프링 빈으로 자동 등록된다.  
@Controller 컨트롤러가 스프링 빈으로 자동등록된 이유도 컴포넌트 스캔 때문이다.  
>
> @Component 를 포함하는 다음 어노테이션도 스프링 빈으로 자동 등록된다. @Controller, @Service, @Repository
>
> 참고 : 생성자에 @Autowired를 사용하면 객체 생성 시점에 스프링 컨테이너에서 해당 스프링 빈을 찾아서 주입한다.
>
> 참고 : 스프링은 스프링 컨테이너에 스프링 빈을 등록할 때, 기본으로 싱글톤으로 등록한다 (유일하게 하나만 등록해서 공유한다)  
        따라서 같은 스프링 빈이면 모두 같은 인스턴스다. 설정으로 싱글톤이 아니게 설정할 수 있지만,  
        특별한 경우를 제외하면 대부분 싱글톤을 사용한다.

> 테이블과의 매핑
> - @Entity가 붙은 클래스는 JPA가 관리하는 것으로, 엔티티라고 불림
> - 기본 생성자는 필수 (JPA가 엔티티 객체 생성 시 기본 생성자를 사용)
> - final 클래스, enum, interface, inner class 에는 사용할 수 없음
> - 저장할 필드에 final 사용 불가

> execute() : 수행결과로 boolean 타입 값 반환 / 모든 구문에 사용가능  
> executeUpdate() : 수행결과로 int 타입 값 반환 / select 구문을 제외한 구문에서 사용가능  
> executeQuery() : 수행결과로 ResultSet 객체 값 반환 / select 구문에 사용가능  

> JdbcTemplate설정은 순수 Jdbc와 동일한 환경설정을 하면된다.  
스프링 JdbcTemplate과 Mybatis 같은 라이브러리는 JDBC API에서 본 반복 코드 대부분을 제거해준다.
>
> JdbcTemplate : https://bibi6666667.tistory.com/300 관련정보  

> stream() :  데이터를 담고 있는 저장소 (컬렉션)이 아니다.  
스트림은 원본 데이터 소스를 변경하지 않는다.(Read Only)  
스트림은 lterator처럼 일회용이다. (필요하면 다시 스트림을 생성해야 함)  
최종 연산 전까지 중간연산을 수행되지 않는다.(lazy)  
무제한일 수도 있다. (Short Circuit 메소드를 사용해서 제한할 수 있다.)  
손쉽게 병렬 처리할 수 있다. (멀티 쓰레드 사용) (.parallel)  
기본형 스트림으로 IntStream, LongStream, DoubleStream등 제공  
오토박싱 등의 불필요한 과정이 생략됨.  
Stream<Integer> 대신에 IntStream을 사용하는게 더 효율적이다.  
뿐만 아니라 숫자의 경우 더 유용한 메서드를 Stream<T>보다 더 많이 제공한다.(.sum(), .averge() 등)  
>
> findAny() : stream에서 가장 먼저 탐색되는 요소를 리턴  
findFirst() : stream에서 조건에 일치하는 요소들 중 stream에서 순서가 가장 앞에 있는 요소 리턴  
-> stream을 직렬 처리할 땐 차이점이 없지만 병렬 처리할 땐 차이가 있다.  

> Optional<Member> findByName(String name);  
[JPQL select m from Member m where m.name = ?]  
인터페이스 이름을 규칙에 맞게 작성하면 단순한 건 한번에 끝낼수 있다.  
실무에서는 JPA와 스프링 데이터 JPA를 기본으로 사용하고, 복잡한 동적 쿼리는 Querydsl이라는 라이브러리를 사용하면 된다.  
>
> Querydsl을 사용하면 쿼리도 자바 코드로 안전하게 작성할 수 있고, 동적 쿼리도 편리하게 작성할 수 있다.  
이 조합으로 해결하기 어려운 쿼리는 JPA가 제공하는 네이티브 쿼리를 사용하거나, 앞서 학습한 스프링 JdbcTemplate를 사용하면 된다.  
>
> Entity 관리를 수행하는 클래스
엔티티 매니저 내부에 **영속성 컨텍스트(Persistence Context)**라는 걸 두어서 엔티티들을 관리한다.  
영속성 컨텍스트는 엔티티를 영구히 저장하는 환경이다.  

> @Transactional  
Jpa를 통한 모든 데이터 변경은 트랜잭션 안에서 실행해야 한다.  
스프링은 해당 클래스의 매서드를 실행할 때 트랜잭션을 시작하고, 매서드가 정상 종료되면 트랜잭션을 커밋한다.  
만약 런타임에 예외가 발생하면 롤백한다.

> AOP(Aspect Oriented Programming)가 필요한 상황  
> - 모든 메소드의 호출 시간을 측정하고 싶다면?
> - 공통 관심 사항(cross-cutting concern) vs 핵심 관심 사항(core concern)
> - 회원 가입 시간, 회원 조회 시간을 측정하고 싶다면?
>
> 문제
> - 회원가입, 회원조회에 시간을 측정하는 기능은 핵심 관심 사항이 아니다.
> - 시간을 측정하는 로직은 공통 관심 사항이다.
> - 시간을 측정하는 로직과 핵심 비지니스의 로직이 섞여서 유지보수가 어렵다.
> - 시간을 측정하는 로직을 별도로 공통 로직으로 만들기 매우 어렵다.
> - 시간을 측정하는 로직을 변경할 때 모든 로직을 찾아가면서 변경해야 한다.
>
> 해결
> - 회원가입, 회원 조회등 핵심 관심사항과 시간을 측정하는 공통 관심 사항을 분리한다.
> - 시간을 측정하는 로직을 별도의 공통 로직으로 만들었다.
> - 핵심 관심 사항을 깔끔하게 유지할 수 있다.
> - 변경이 필요하면 이 로직만 변경하면 된다.
> - 원하는 적용 대상을 선택할 수 있다.
>
> AOP동작 방식  
> 
> AOP를 적용하기 전 의존관계  
> helloController -> memberService  
> AOP를 적용한 후 의존관계  
> helloController -> (프록시)memberService -> joinPoint.proceed() -> (실제)memberService
>
> AOP를 적용하기 전 전체 그림
> memberController -> (실제)memberService -> (실제)memberRepository
> AOP를 적용한 후 전체 그림  
> (프록시)memberController -> memberController -> (프록시)memberService -> (실제)memberService -> (프록시)memberRepository -> (실제)
  memberRepository


> 테스트 코드에서  
>
> @BeforeEach : 각 테스트 실행 전에 호출된다. 테스트가 서로 영향이 없도록 항상 새로운 객체를 생성하고, 의존관계도 새로 맺어준다.  
>
> @AfterEach : 한번에 여러 테스트를 실행하면 메모리 DB에 직전 테스트의 결과가 남을 수 있다.  
이렇게 되면 다음 이전 테스트 때문에 다음 테스트가 실패할 가능성이 있다.  
@AfterEach를 사용하면 각 테스트가 종료될 때 마다 이 기능을 실행한다. 여기서는 메모리 DB에 저장된 데이터를 삭제한다.  
테스트는 각각 독립적으로 실행되어야 한다. 테스트 순서에 의존관계가 있는 것은 좋은 테스트가 아니다.  
>
> @SpringBootTest : spring 컨테이너와 테스트를 함께 실행한다.  
@Transactional : 테스트 매서드를 트랜젝션으로 감싸고 매서드가 종료 될때 롤백 한다.  
db에 데이터가 남지 않으므로 다음 테스트에 영향을 주지 않는다.  
관련글 : https://tecoble.techcourse.co.kr/post/2021-05-25-transactional/