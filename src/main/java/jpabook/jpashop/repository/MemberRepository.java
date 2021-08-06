package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em; // EntityManager 주입

    /*
    @PersistenceUnit
    private EntityManagerFactory emf;
    */

    public long save(Member member) {
        em.persist(member);
        return member.getId();
    }

    public Member findOne(Long id){
        return em.find(Member.class, id);
    }

    public List<Member> findAll(){
        return  em.createQuery("select m from Member m", Member.class).getResultList();
    }

    public List<Member> findByName(String name){
        return  em.createQuery("select m from Member m where m.name = :name", Member.class).setParameter("name", name ).getResultList();
    }

    public Member findByEmail(String email){
        return em.createQuery("select m from Member m where m.email = :email", Member.class).setParameter("email", email ).getResultList().stream().findFirst().orElse(null);
    }

}