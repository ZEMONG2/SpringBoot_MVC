package hello.hellospring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/*
AOP동작 방식

AOP를 적용하기 전 의존관계는
 - helloController -> memberService
AOP를 적용한 후 의존관계
 - helloController -> (프록시)memberService -> joinPoint.proceed() -> (실제)memberService

AOP를 적용하기 전 전체 그림
 - memberController -> (실제)memberService -> (실제)memberRepository
AOP를 적용한 후 전체 그림
 - (프록시)memberController -> memberController -> (프록시)memberService -> (실제)memberService -> (프록시)memberRepository -> (실제)memberRepository
 */

@Aspect
@Component // 직접 bean을 생성하지않고 component 스캔방식으로
public class TimeTraceAop {

    @Around("execution(* hello.hellospring..*(..))") //패키지 하위에 다 적용해라
    public Object excute(ProceedingJoinPoint joinpoint) throws Throwable {
        long start = System.currentTimeMillis();
        System.out.println("START: " + joinpoint.toString());

        try {
            Object result = joinpoint.proceed();
            return result;
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("END: " + joinpoint.toString() + " " + timeMs + "ms");
        }

    }

    /*
    해결
    - 회원가입, 회원 조회등 핵심 관심사항과 시간을 측정하는 공통 관심 사항을 분리한다.
    - 시간을 측정하는 로직을 별도의 공통 로직으로 만들었다.
    - 핵심 관심 사항을 깔끔하게 유지할 수 있다.
    - 변경이 필요하면 이 로직만 변경하면 된다.
    - 원하는 적용 대상을 선택할 수 있다.
     */

}
