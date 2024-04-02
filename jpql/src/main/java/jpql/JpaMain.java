package jpql;

import javax.persistence.*;
import javax.persistence.criteria.From;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        /// 1. jpa 작업은 무조건 트랜잭션 안에서 이루어줘야함.
        //2. 엔티티 매니저 팩토리는 하나만 생성해서 애플리케이션 전체에 공유해야됨
        //3. 엔티티 매니저는 쓰레드간에 공유X (사용하고 버려야 한다). 마치 데이터베이스 커넥션을 쓰고 돌려주느 것처럼
        tx.begin();

        try {
            Team teamA = new Team();
            teamA.setName("teamA");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("teamB");
            em.persist(teamB);


            Member member = new Member();
            member.setName("aa");
            member.setAge(10);
            member.setTeam(teamA);
            member.setType(MemberType.User);

            em.persist(member);

            Member member2 = new Member();
            member2.setName("bb");
            member2.setAge(10);
            member2.setType(MemberType.Admin);
            member2.setTeam(teamA);

            em.persist(member2);

            Member member3 = new Member();
            member3.setName("bb");
            member3.setAge(10);
            member3.setType(MemberType.Admin);
            member3.setTeam(teamB);

            em.persist(member3);

            em.flush();
            em.clear();

            List<Member> resultList1 = em.createNamedQuery("Member.findByUsername", Member.class)
                    .setParameter("username", "aa")
                    .getResultList();

            for (Member member1 : resultList1) {
                System.out.println("member1 = " + member1);
            }


//            String query = " select m from Member m where m.team = :team ";
//
//
//            List<Member> resultList = em.createQuery(query, Member.class)
//                    .setParameter("team", teamA)
//                    .getResultList();
//
//            for (Member member1 : resultList) {
//                System.out.println("member1 = " + member1);
//            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        emf.close();
        em.close();

    }

    private static void printMember(Member member) {
        System.out.println("member.getUsername() = " + member.getName());
    }

    private static void printMemberAndTeam(Member member) {
        System.out.println(member.getName() + member.getTeam().getName());
    }
}
