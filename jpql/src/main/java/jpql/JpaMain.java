package jpql;

import javax.persistence.*;
import java.util.List;

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

            Member member = new Member();
            member.setName("mer1");
            member.setAge(10);

            em.persist(member);

            em.createQuery("select m.team from Member m where m.name = :username")
                    .setParameter("username", "mer1")
                    .getSingleResult();

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
