package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


// 인터페이스가 인터페이스를 상속할때는 extends로
// JpaRepository를 상속 받고있으면 구현체를 자동으로 생성해준다.
public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {
    // 스프링 데이터 JPA 제공기능
    // 인터페이스를 통한 기본적인 CRUD
    // findByName(), findByEmail() 처럼 매서드 이름만으로 조회기능 제공
    // JPQL select m from Member m where m.name = ?
    // 인터페이스 이름을 규칙에 맞게 작성하면 단순한 건 한번에 끝낼수 있다.
    @Override
    Optional<Member> findByName(String name);

    /*
    실무에서는 JPA와 스프링 데이터 JPA를 기본으로 사용하고, 복잡한 동적 쿼리는 Querydsl이라는 라이브러리를 사용하면 된다.
    Querydsl을 사용하면 쿼리도 자바 코드로 안전하게 작성할 수 있고, 동적 쿼리도 편리하게 작성할 수 있다.
    이 조합으로 해결하기 어려운 쿼리는 JPA가 제공하는 네이티브 쿼리를 사용하거나, 앞서 학습한 스프링 JdbcTemplate를 사용하면 된다.
     */


}
