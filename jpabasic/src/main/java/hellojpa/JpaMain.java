package hellojpa;

import jdk.swing.interop.SwingInterOpUtils;
import org.hibernate.Hibernate;

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
//            // 1. 데이터 저장
//            Member member2 = new Member();
//            member2.setId(12L);
//            member2.setName("영숙아니고 영속");
//            em.persist(member2);
//
//            // 2. 데이터 조회
//            Member findMember = em.find(Member.class, 1L);
//            System.out.println("findMember = " + findMember.getName());
//            //  트랜잭션이 커밋하는 시점에서 체크를 해서 update 쿼리가 날라간다.
//            findMember.setName("수정이다!");
//
//            // 3. 특정 조건에 대해 검색시 JPQL 사용
//            List<Member> result = em.createQuery("select m from Member m", Member.class)
//                    .setFirstResult(1) // 페이지 네이션 1번째부터 10개들고와!  limit
//                    .setMaxResults(10) // offset
//                    .getResultList();
//
//            for (Member member : result) {
//                System.out.println(member.getName());
//            }


            // 쿼리생성시점

//            System.out.println("=====BEFORE======");
//            Member member3 = new Member();
//            member3.setId(101L);
//            member3.setName("검사용");
//            em.persist(member3);
//            System.out.println("=====AFTER======");


            // 영속, commit 이 된순간 query가 날라간다
//            // 모아쓰기!
//            Member member1 = new Member(150L, "1");
//            Member member = em.find(Member.class, 150L);
//
//            // 값만 바꿧지만, 변경을 감지해서 update 쿼리를 생성!
//            member.setName("바꾸기!!");
//            System.out.println("===========");

//            System.out.println("=======");
//
//            Member member = new Member();
//            member.setUsername("c");
//            em.persist(member);
//            System.out.println("member = " + member.getId());
//            System.out.println("======="); // generated.identify 인경우 persist에 commit함.
//
//            Member member2 = new Member();
//            member2.setUsername("c");
//            em.persist(member2);
//            Member member3 = new Member();
//            member3.setUsername("c");
//            em.persist(member3);




//            Member member = new Member();
//            member.setUsername("c");
//            em.persist(member);
//
//
//            Team team = new Team();
//            team.setName("TeamA");
//            team.getMembers().add(member);
//            em.persist(team);

            // 객체지향적으로 생각했을때 양쪽으로 작성하는 것이 맞음.
//            member.changeTeam(team); // 연관관계 편의 메서드로 주입
//            team.getMembers().add(member); // 연관관계 편의 메서드로 주입으로 한쪽은 지워야된다.


//            Movie movie = new Movie();
//            movie.setPrice(110000);
//            movie.setActor("d");
//            movie.setDirector("a");
//            movie.setName("엄준식");
//
//            em.persist(movie);
//
//            em.flush();
//            em.clear();
//
//            Item findMovie = em.find(Item.class, movie.getId());
//
//            /// 각 테이블 전략은 테이블 3개를 다 뒤지는 비효율작업
//            System.out.println("findMovie = " + findMovie.getName());


//            Member member = new Member();
//            member.setCreateBy("엄");
//            member.setUsername("아이씨");
//            em.persist(member);


            Member member = new Member();
            member.setUsername("hello");
            em.persist(member);

            Member member2 = new Member();
            member.setUsername("hello2");
            em.persist(member2);

            em.flush();
            em.clear();

            //
//            Member findMember = em.find(Member.class, member.getId());
            Member findMember = em.find(Member.class, member.getId()); // 실제로 사용할때
            System.out.println("findMember = " + findMember.getClass());
            System.out.println("findMember.getId() = " + findMember.getId());
            System.out.println("findMember.getUsername() = " + findMember.getUsername()); // 사용할때 내부적으로 db에 요청해서 실제 타겟에 요청.
            System.out.println("after findMember = " + findMember.getClass());

            Member findMember2= em.getReference(Member.class, member2.getId()); // 참조
            System.out.println("findMember2.getClass() = " + findMember2.getClass());

            System.out.println(" 타입비교, ==  "+(findMember.getClass() == findMember2.getClass())); // 프록시와 실제타입은 동등비교 x
            System.out.println(" 타입비교,  instanceof"+(findMember instanceof Member));
            System.out.println(" 타입비교,  instanceof"+(findMember2 instanceof Member));


            Member refernce = em.getReference(Member.class, member.getId());
            System.out.println("refernce.getClass() = " + refernce.getClass()); /// 영속성컨텍스트에 이미 잇기 때문에 프록시가 아닌 실제객체반환.

            // jpa에서 영속성컨텍스트에 잇으면 a == a 는 항상 true;
            System.out.println("a == a : " + (findMember == refernce));


            ////  프록시를 조회했다면 이후 find를 하더라도 프록시로 조회된다.
            Member refMember = em.getReference(Member.class, member2.getId()); // 프록시
            System.out.println("refMember.getClass() = " + refMember.getClass());



            Member findMember3 = em.find(Member.class, member2.getId()); // find 햇지만 프록시
            System.out.println("findMember3.getClass() = " + findMember3.getClass());


            em.flush();
            em.clear();

            // 만약에 준영속이 된다면 프록시 초기화시 오류발생
            Member refMember4 = em.getReference(Member.class, member2.getId()); // 프록시
            System.out.println("refMember4.getClass() = " + refMember4.getClass());
//            em.detach(refMember4); // 영속화 해제 -> 영속성 도움을 받지 못함 !
//            em.close();//
//            em.clear();
            //  could not initialize proxy [hellojpa.Member#2] - no Session
            // 에러 발생 초기화 안됨
            System.out.println("refMember4 = " + refMember4.getUsername());

            // 초기화 됬는지 ture, 안됫느지  false
            System.out.println(emf.getPersistenceUnitUtil().isLoaded(refMember4));

            Hibernate.initialize(refMember4);// 강제 초기화

            tx.commit();
        }catch (Exception e){
            tx.rollback();
            e.printStackTrace();
        }finally {
            em.close();
        }

        emf.close();
        em.close();

    }

    private static void printMember(Member member){
        System.out.println("member.getUsername() = " + member.getUsername());
    }

    private static void printMemberAndTeam(Member member){
        System.out.println(member.getUsername() + member.getTeam().getName());
    }
}
