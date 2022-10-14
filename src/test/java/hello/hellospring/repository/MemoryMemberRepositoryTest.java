package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

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

        Member result = repository.findByName("zemong1").get();
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
