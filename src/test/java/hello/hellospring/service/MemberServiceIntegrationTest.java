package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


// 통합 테스트
@SpringBootTest // spring 컨테이너와 테스트를 함께 실행한다
@Transactional // 테스트 매서드를 트랜젝션으로 감싸고 매서드가 종료 될때 롤백 한다, db에 데이터가 남지 않으므로 다음 테스트에 영향을 주지 않는다.
    //https://tecoble.techcourse.co.kr/post/2021-05-25-transactional/ 관련글
class MemberServiceIntegrationTest {

    // 테스트는 마지막 단이기 때문에 Autowired로 해도 충분하다
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    //@Commit // @Commit 사용시 롤백하지않고 적용된다.
    void 회원가입() {
        //given
        Member member = new Member();
        member.setName("spring");

        //when
        Long saveId = memberService.join(member);

        //then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());

    }

    @Test
    public void 중복_회원_예외() {
        //given
        Member member1 = new Member();
        member1.setName("zemong");

        Member member2 = new Member();
        member2.setName("zemong");

        //when
        memberService.join(member1);

        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

    }
}