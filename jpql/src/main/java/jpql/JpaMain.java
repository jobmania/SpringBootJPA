package jpql;

import javax.persistence.*;
import javax.persistence.criteria.From;
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

            Member member = new Member();
            member.setName("관리자");
            member.setAge(10);
            member.setType(MemberType.Admin);
            Team team = new Team();
            team.setName("team1");
            member.changeTeam(team);
            em.persist(team);
            em.persist(member);

            em.flush();
            em.clear();

            // 영속성에 관리가 되나??
//              em.createQuery("select m.name, m.age from Member m ")
//                    .getResultList();

            // 관리됨 ㅇㅇ !

            // 여러가지 타입을 조회하고 싶어!

//            List resultList = em.createQuery("select m.name, m.age from Member m ")
//                    .getResultList();
//
//            // 1, object타입으로
//            Object o = resultList.get(0);
//            Object[] result = (Object[]) o;
//
//            System.out.println(result[0]);
//            System.out.println(result[1]);
//
//            // 2.  List<Object[]>선언
//            List<Object[]> resultList2 = em.createQuery("select m.name, m.age from Member m ")
//                    .getResultList();

            //3 . new 객체로 생성자를 통해서 출력 ! 
//            List<MemberDTO> resultList = em.createQuery("select new jpql.MemberDTO(m.name, m.age) from Member m ", MemberDTO.class)
//                    .getResultList();
//
//            MemberDTO memberDTO = resultList.get(0);
//            System.out.println("memberDTO.getName() = " + memberDTO.getName());


//            for (int i = 0; i < 100; i++) {
//                Member member1 = new Member();
//                member1.setName("ㅁㅁ"+i);
//                member1.setAge(i);
//                em.persist(member1);
//            }
//
//            List<Member> resultList1 = em.createQuery("select m from Member m Order by m.age desc", Member.class)
//                    .setFirstResult(1)
//                    .setMaxResults(10)
//                    .getResultList();
//
//
//            System.out.println(resultList1.size());
//            for (Member member1 : resultList1) {
//                System.out.println("member1 = " + member1);
//            }

            /// 조인

//            String query = "select m from Member m left join Team t on m.name=t.name";
//            List<Member> resultList = em.createQuery(query, Member.class)
//                    .getResultList();
//
//            System.out.println(resultList.size());

            /*String query = "select m.name,'Hello', true from Member m " +
                    "WHERE m.type = :userType and m.name is not null";
            List<Object[]> resultList = em.createQuery(query)
                    .setParameter("userType",MemberType.Admin)
                    .getResultList();

            for (Object[] objects : resultList) {
                System.out.println("objects[0] = " + objects[0]);
                System.out.println("objects[0] = " + objects[1]);
                System.out.println("objects[0] = " + objects[2]);
            }*/


            // case 식
//            String query = "select " +
//                                " case when m.age<=10 then '학생요금' " +
//                                "      when m.age>= 60 then '경로요금' " +
//                                "      else '일반요금'  " +
//                                 "     end       "+
//                            "from Member m ";
//            List<String> resultList = em.createQuery(query)
//                    .getResultList();


            // coalesce AND null if
//            String query = "select nullif(m.name, '관리자') as username" +
//                    " from Member m " ;
//
//            List<String> resultList = em.createQuery(query, String.class)
//                    .getResultList();
//
//            for (String s : resultList) {
//                System.out.println("s = " + s);
//            }


            String query = "select 'a' || 'b' From Member m";
            String query2 = "select concat('a','b')  From Member m";
            String query3 = "select locate('de','abcdefrg')  From Member m"; // Integer type
            String query4 = "select size(t.members) From Team t";  // Integer type
            String query5 = "select index(t.members) From Team t";  // Integer type
            String query6 = "select group_concat(m.name) FROM Member m";
            List<String> resultList = em.createQuery(query6, String.class)
                    .getResultList();

            for (String integer : resultList) {
                System.out.println("integer = " + integer);
            }

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
