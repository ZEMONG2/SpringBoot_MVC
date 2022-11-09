package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

// 순수 Jdbc와 동일한 환경설정을 하면된다.
// 스프링 JdbcTemplate과 Mybatis 같은 라이브러리는 JDBC API에서 본 반복 코드 대부분을 제거해준다.
public class JdbcTemplateMemberRepository implements MemberRepository{

    private final JdbcTemplate jdbcTemplate;
    // JdbcTemplate : https://bibi6666667.tistory.com/300 관련정보

    // @Autowired // 생성자가 하나만 있다면 @Autowired 생략 가능
    public JdbcTemplateMemberRepository(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Member save(Member member) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", member.getName());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        member.setId(key.longValue());

        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        List<Member> result = jdbcTemplate.query("select * from member where id = ?", memberRowMapper(), id);
        return result.stream().findAny();
        /*
        stream() :  데이터를 담고 있는 저장소 (컬렉션)이 아니다.
                    스트림은 원본 데이터 소스를 변경하지 않는다.(Read Only)
                    스트림은 lterator처럼 일회용이다. (필요하면 다시 스트림을 생성해야 함)
                    최종 연산 전까지 중간연산을 수행되지 않는다.(lazy)
                    무제한일 수도 있다. (Short Circuit 메소드를 사용해서 제한할 수 있다.)
                    손쉽게 병렬 처리할 수 있다. (멀티 쓰레드 사용) (.parallel)
                    기본형 스트림으로 IntStream, LongStream, DoubleStream등 제공
                    오토박싱 등의 불필요한 과정이 생략됨.
                    Stream<Integer> 대신에 IntStream을 사용하는게 더 효율적이다.
                    뿐만 아니라 숫자의 경우 더 유용한 메서드를 Stream<T>보다 더 많이 제공한다.(.sum(), .averge() 등)
        findAny() : stream에서 가장 먼저 탐색되는 요소를 리턴
        findFirst() : stream에서 조건에 일치하는 요소들 중 stream에서 순서가 가장 앞에 있는 요소 리턴
        -> stream을 직렬 처리할 땐 차이점이 없지만 병렬 처리할 땐 차이가 있다.
        -> 리턴값은 Optional 이므로 ifPresent()를 이용해 출력하기도 한다.
        -> 보통 filter()와 함께 사용한다.
        - 이 외에도 여러가지가 많이 존재한다. : https://dinfree.com/blog/2019/04/01/javafp-3.html 참고
         */
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = jdbcTemplate.query("select * from member where name = ?", memberRowMapper(), name);
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query("select * from member", memberRowMapper());
    }

    private RowMapper<Member> memberRowMapper(){
//        return new RowMapper<Member>() {
        return (rs, rowNum) -> { // lambda 형식

            Member member = new Member();
            member.setId(rs.getLong("id"));
            member.setName(rs.getString("name"));

            return member;

        };
    }

}
