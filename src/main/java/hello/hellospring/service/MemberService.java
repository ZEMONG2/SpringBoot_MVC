package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

// cmd + shift + t : 테스트 코드 생성

@Transactional
/*
Jpa를 통한 모든 데이터 변경은 트랜잭션 안에서 실행해야 한다.
스프링은 해당 클래스의 매서드를 실행할 때 트랜잭션을 시작하고, 매서드가 정상 종료되면 트랜잭션을 커밋한다. 만약 런타임에 예외가 발생하면 롤백한다.
*/

public class MemberService {

    private final MemberRepository memberRepository;


    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원가입
     */
    public Long join(Member member) {
        /*
        AOP(Aspect Oriented Programming)가 필요한 상황
        - 모든 메소드의 호출 시간을 측정하고 싶다면?
        - 공통 관심 사항(cross-cutting concern) vs 핵심 관심 사항(core concern)
        - 회원 가입 시간, 회원 조회 시간을 측정하고 싶다면?
         */

// AOP를 사용하지않고 매서드 실행 시간 측정
//        long start = System.currentTimeMillis();
//
//        try{
//            validateDuplicateMember(member); // 중복회원 검증
//            memberRepository.save(member);
//            return member.getId();
//        } finally {
//          long finish = System.currentTimeMillis();
//          long timeMs = finish - start;
//          System.out.println("join = " + timeMs + "ms");
//        }

        /*
        문제
        - 회원가입, 회원조회에 시간을 측정하는 기능은 핵심 관심 사항이 아니다.
        - 시간을 측정하는 로직은 공통 관심 사항이다.
        - 시간을 측정하는 로직과 핵심 비지니스의 로직이 섞여서 유지보수가 어렵다.
        - 시간을 측정하는 로직을 별도로 공통 로직으로 만들기 매우 어렵다.
        - 시간을 측정하는 로직을 변경할 때 모든 로직을 찾아가면서 변경해야 한다.
         */

        validateDuplicateMember(member); // 중복회원 검증
        memberRepository.save(member);
        return member.getId();

    }

    private void validateDuplicateMember(Member member) {
        // 같은 이름의 중복 회원은 불가능
        // cmd + opt + v  :  리턴 알아서 잡아줌
        // ctrl + t : method 추출
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
        //ifPresent : 값이있다면 실행 없다면 패스 / 예외발생
        //isPresent : boolean타입 / true, false 체크
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * 특정 회원 조회
     */
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }

}
