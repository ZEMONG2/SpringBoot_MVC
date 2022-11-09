package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


/*
개발한 기능을 실행해서 테스트 할 때 자바의 main매서드를 통해서 실행하거나,
웹 애플리케이션의 컨트롤러를 통해서 해당 기능을 실행한다.
이러한 방법은 준비하고 실행하는데 오래 걸리고, 반복 실행하기 어렵고 여러 테스트를 한번에 실행하기 어렵다는 단점이 있다.
자바는 JUnit이라는 프레임워크로 테스트를 실행해서 이러한 문제를 해결한다.
 */


public class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach
    public void afterEach() {
        repository.clearStore();
    } // 하나의 테스트가 끝나면 데이터 초기화

    @Test
    public void save() {
        Member member = new Member();
        member.setName("zemong");

        repository.save(member);

        Member result = repository.findById(member.getId()).get();
        //Assertions.assertEquals(member, result);
        assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("zemong1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("zemong2");
        repository.save(member2);

        Member result = repository.findByName("zemong1").get(); //Optional 로 감싸진걸 한번더 벗기기위해 .get()
        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll() {

        Member member1 = new Member();
        member1.setName("zemong1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("zemong2");
        repository.save(member2);

        List<Member> result = repository.findAll();
        assertThat(result.size()).isEqualTo(2);
    }
}
