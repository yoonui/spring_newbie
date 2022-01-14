package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository{

    // JPA는 EntityManager로 모든 것이 동작한다
    // 스프링부트는 현재 데이터베이스와 맵핑하여 자동으로 EntityManager를 생성해준다
    private final EntityManager em;

    public JpaMemberRepository(EntityManager em){
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member); // JPA가 insert 쿼리 만들어서 DB에 넣고, id까지 set해줌
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member =  em.find(Member.class, id); // find(조회할 타입, 식별자 PK값)
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        // JPQL : 테이블을 대상으로 sql문을 날리는 것이 아닌, 객체를 대상으로 sql문을 날림
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }
}
