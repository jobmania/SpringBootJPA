package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
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
            // 1. 데이터 저장
//            Member member = new Member();
//            member.setId(1L);
//            member.setName("엄준식");
//            em.persist(member);

            // 2. 데이터 조회
            Member findMember = em.find(Member.class, 1L);
            System.out.println("findMember = " + findMember.getName());
            //  트랜잭션이 커밋하는 시점에서 체크를 해서 update 쿼리가 날라간다.
            findMember.setName("수정이다!");

            // 3. 특정 조건에 대해 검색시 JPQL 사용
            List<Member> result = em.createQuery("select m from Member m", Member.class)
                    .setFirstResult(1) // 페이지 네이션 1번째부터 10개들고와!  limit
                    .setMaxResults(10) // offset
                    .getResultList();

            for (Member member : result) {
                System.out.println(member.getName());
            }


            tx.commit();

        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }

        emf.close();
        em.close();

    }
}
