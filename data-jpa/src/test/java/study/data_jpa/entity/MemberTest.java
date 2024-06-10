package study.data_jpa.entity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
class MemberTest {

    @PersistenceContext
    EntityManager em;


    @Test
    public void testEntity(){
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);

        em.persist(new Member("member1", 22,teamA));
        em.persist(new Member("member2", 32,teamA));
        em.persist(new Member("member3", 43,teamB));
        em.persist(new Member("member4", 55,teamB));


        em.flush();
        em.clear();


        /// 확인

        List<Member> memberList = em.createQuery
                        ("select m from Member m ", Member.class)
                        .getResultList();

        for (Member member : memberList) {
            System.out.println("member = " + member);
            System.out.println("member team = " + member.getTeam());
        }

    }
}