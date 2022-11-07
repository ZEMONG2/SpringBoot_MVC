package hello.hellospring.domain;

import javax.persistence.*;

/*
- 테이블과의 매핑
- @Entity가 붙은 클래스는 JPA가 관리하는 것으로, 엔티티라고 불림
- 기본 생성자는 필수 (JPA가 엔티티 객체 생성 시 기본 생성자를 사용)
- final 클래스, enum, interface, inner class 에는 사용할 수 없음
- 저장할 필드에 final 사용 불가
*/
@Entity
public class Member {

    @Id // pk 매핑
    @GeneratedValue(strategy = GenerationType.IDENTITY) //기본 키 생성을 데이터베이스에 위임 즉, id 값을 null로 하면 DB가 알아서 AUTO_INCREMENT 해준다.
    private Long id;

//    @Column(name = "userName") 컬럼명이 이라면 @Column 으로 매핑
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
