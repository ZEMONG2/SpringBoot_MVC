package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository{ // implements : 인터페이스 상속 - Override(재정의) 해줘야한다. 다중 상속 가능.

    private final EntityManager em;
    /* Entity 관리를 수행하는 클래스
    엔티티 매니저 내부에 **영속성 컨텍스트(Persistence Context)**라는 걸 두어서 엔티티들을 관리한다.
    영속성 컨텍스트는 엔티티를 영구히 저장하는 환경이다.
    */
    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    /*
    Optional<>
    NPE(NullPointerException)발생으르 막기 위해 null 여부를 검사해야하는데,
    Optional<T>는 null이 올 수 있는 값을 감싸는 Wrapper 클래스로, 참조하더라도 NPE가 발생하지 않도록 도와준다.
     */

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class).setParameter("name",name).getResultList();
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        List<Member> result = em.createQuery("select m from Member m", Member.class).getResultList();
        return result;
    }
}
