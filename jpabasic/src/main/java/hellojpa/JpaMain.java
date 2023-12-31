package hellojpa;

import jdk.swing.interop.SwingInterOpUtils;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collection;
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


            // 쿼리생성시점 확인 ( 영속성 컨텍스트)

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


            // 연관관계 메서드

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

//
//            Member member = new Member();
//            member.setUsername("hello");
//            em.persist(member);
//
//            Member member2 = new Member();
//            member.setUsername("hello2");
//            em.persist(member2);
//
//            em.flush();
//            em.clear();
//
//            // 프록시 조회 및 초기화
////            Member findMember = em.find(Member.class, member.getId());
//            Member findMember = em.find(Member.class, member.getId()); // 실제로 사용할때
//            System.out.println("findMember = " + findMember.getClass());
//            System.out.println("findMember.getId() = " + findMember.getId());
//            System.out.println("findMember.getUsername() = " + findMember.getUsername()); // 사용할때 내부적으로 db에 요청해서 실제 타겟에 요청.
//            System.out.println("after findMember = " + findMember.getClass());
//
//            Member findMember2= em.getReference(Member.class, member2.getId()); // 참조
//            System.out.println("findMember2.getClass() = " + findMember2.getClass());
//
//            System.out.println(" 타입비교, ==  "+(findMember.getClass() == findMember2.getClass())); // 프록시와 실제타입은 동등비교 x
//            System.out.println(" 타입비교,  instanceof"+(findMember instanceof Member));
//            System.out.println(" 타입비교,  instanceof"+(findMember2 instanceof Member));
//
//
//            Member refernce = em.getReference(Member.class, member.getId());
//            System.out.println("refernce.getClass() = " + refernce.getClass()); /// 영속성컨텍스트에 이미 잇기 때문에 프록시가 아닌 실제객체반환.
//
//            // jpa에서 영속성컨텍스트에 잇으면 a == a 는 항상 true;
//            System.out.println("a == a : " + (findMember == refernce));
//
//
//            ////  프록시를 조회했다면 이후 find를 하더라도 프록시로 조회된다.
//            Member refMember = em.getReference(Member.class, member2.getId()); // 프록시
//            System.out.println("refMember.getClass() = " + refMember.getClass());
//
//
//
//            Member findMember3 = em.find(Member.class, member2.getId()); // find 햇지만 프록시
//            System.out.println("findMember3.getClass() = " + findMember3.getClass());
//
//
//            em.flush();
//            em.clear();
//
//            // 만약에 준영속이 된다면 프록시 초기화시 오류발생
//            Member refMember4 = em.getReference(Member.class, member2.getId()); // 프록시
//            System.out.println("refMember4.getClass() = " + refMember4.getClass());
////            em.detach(refMember4); // 영속화 해제 -> 영속성 도움을 받지 못함 !
////            em.close();//
////            em.clear();
//            //  could not initialize proxy [hellojpa.Member#2] - no Session
//            // 에러 발생 초기화 안됨
//            System.out.println("refMember4 = " + refMember4.getUsername());
//
//            // 초기화 됬는지 ture, 안됫느지  false
//            System.out.println(emf.getPersistenceUnitUtil().isLoaded(refMember4));
//
//            Hibernate.initialize(refMember4);// 강제 초기화


            /// 지연로딩, 즉시로딩

//
//            Member member = new Member();
//            member.setUsername("c");
//
//            Member member2 = new Member();
//            member2.setUsername("cd");
//
//
//            Team team = new Team();
//            team.setName("TeamA");
//            Team team2 = new Team();
//            team.setName("TeamB");
//
//            member.setTeam(team);
//            member2.setTeam(team2);
//
//            em.persist(member);
//            em.persist(member2);
//            em.persist(team);
//            em.persist(team2);
//
//            em.flush();
//            em.clear();
//
////            Member m = em.find(Member.class, member.getId());
////            System.out.println(m.getTeam().getClass());
//
//
//            System.out.println("=======팀을 조회한다면???====");
////            m.getTeam().getName(); // 팀을 실제 사용할때 초기화가 일어난다.
//
//
//            // JPQL N+1 문제
//            // sql 번역 -> member를 들고옴 -> team이 즉시로딩되어있으면 -> 멤버가 갯수가 10면 10개만큼 쿼리가 별도로 나감
//            List<Member> members = em.createQuery("select m from Member  m join fetch m.team", Member.class)
//                    .getResultList();
//
//            /// SQL: select * from Member,
//            // SQL : select * from Team where TEAM_ID = M.id~~
//            // 만약 다른팀이면 따로따로 들고옴
//            // N+1 : 결과가 N개로 나오면 그만큼 최초 1개 쿼리 + 결과 N개만큼 쿼리가 나와서
//
//            // fetch 조인 사용 하면 lazy 로딩하여

            // Cascade

//            Parent parent = new Parent();
//            Child child1 = new Child();
//            Child child2= new Child();
//
//            parent.addChild(child1);
//            parent.addChild(child2);
//
//
//            // 일반적으로는 3번의 persist를 호출.
//            // 그러나 cascade를 사용하면, 아래의 것들도 persist 해준다.
//            em.persist(parent);
//
//            em.flush();
//            em.clear();
//
//            // 고아객체 삭제 ! -> 연관관계가 끊어지면 삭제 .
//            Parent findParent = em.find(Parent.class, parent.getId());
//            findParent.getChildList().remove(0);
////            em.remove(findParent);
            // 자식은 부모가 생명주기를 관리함.

            // 값타입
/*
            Member member = new Member();
            member.setUsername("hee");
            member.setWorkAddress(new Address("city","street","10000"));
            member.setPeriod(new Period());

            em.persist(member);
            */


         /*   // 값타입 공유시 위험
            Address address = new Address("city", "strre", "222");
            Member member1 = new Member();
            member1.setUsername("hee");
            member1.setHomeAddress(address);
            Member member2 = new Member();
            member2.setUsername("hee");

            Address copyAddress = new Address(address.getCity(), address.getStreet(), address.getZipcode());
            member2.setHomeAddress(copyAddress);

            em.persist(member1);
            em.persist(member2);
            // 공유참조 문제 발생 !!!  이를  막기위해서 임베디드를 바로 사용하는 것이 아닌 값을 복사함 .
//            member1.getHomeAddress().setCity("바꾸고 싶은 주소야 ! ");
            /// 생성자로만 생성하고, 변경을 막기 !  setter  삭제

            // 변경할려면 새로운 값을
            Address newAddress = new Address("NewCity", address.getStreet(), address.getZipcode());
            member1.setHomeAddress(newAddress);*/

/*
            Member member = new Member();
            member.setUsername("member1");
            member.setHomeAddress(new Address("city","street", "lozcl"));

            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("ㅇ머2");
            member.getFavoriteFoods().add("ㅇ머3");
            member.getFavoriteFoods().add("ㅇ머4");

//            member.getAddressHistory().a(new Address("old","stret","33"));
//            member.getAddressHistory().add(new Address("old4","stret","33"));
//            member.getAddressHistory().add(new Address("old1","stret","33"));

            em.persist(member);

            em.flush();
            em.clear();

            System.out.println("====================");
            Member findMember = em.find(Member.class, member.getId());

            List<AddressEntity> addressHistory = findMember.getAddressHistory();
            for (AddressEntity address : addressHistory) {
                System.out.println("address.getCity() = " + address.getAddress().getCity());
            }

            for (String favoriteFood : findMember.getFavoriteFoods()) {
                System.out.println("favoriteFood = " + favoriteFood);
            }

            //////////// 변경은 무조건 생성자로 생성해서 넣기!
            Address newAddress = new Address("뉴", "기", "니");
            findMember.setHomeAddress(newAddress);


            // 컬렉션 값으로
            findMember.getFavoriteFoods().remove("치킨");
            findMember.getFavoriteFoods().add("립스테이크!");

            System.out.println("========================");
            // 수정:  equals 및 해시코드 적용되어야 있어야함.
//            findMember.getAddressHistory().remove(new AddressEntity("old", "stret", "33"));
            findMember.getAddressHistory().add(new AddressEntity("new","stret","33"));

            // 수정*/


           /* //Criteria 사용 준비 --> 가독성 어렵다~!
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Member> query = cb.createQuery(Member.class);

            Root<Member> m = query.from(Member.class);

            CriteriaQuery<Member> cq = query.select(m).where(cb.equal(m.get("username"), "kim"));

            List<Member> resultList = em.createQuery(cq).getResultList();*/


            // 네티이브 쿼리
            Team team = new Team();
            em.persist(team);


            Member member = new Member();
            member.setUsername("MEMBER1");
            member.setTeam(team);
            em.persist(member);

            Member member2 = new Member();
            member2.setUsername("MEMBER2");
            member2.setTeam(team);
            em.persist(member2);



            // flush -> commit 또는 query 날라갈때
            em.flush();
            em.clear();

            String query = "select t.members.size from Team t"; // 묵시적 조인보다 명시적 조인으로 해야 리스트를 접근가능
            String query2 = "select m.username from Team t join t.members m"; // 묵시적 조인보다 명시적 조인으로 해야 리스트를 접근가능

            List<Collection> result = em.createQuery(query, Collection.class)
                    .getResultList();

            // 묵시적 조인은 성능튜닝에 막대한 영향을 끼침 (조인이 발생하는지 직관적으로 알기 어려움)

            System.out.println("result = " + result);


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
        System.out.println("member.getUsername() = " + member.getUsername());
    }

    private static void printMemberAndTeam(Member member) {
        System.out.println(member.getUsername() + member.getTeam().getName());
    }
}
