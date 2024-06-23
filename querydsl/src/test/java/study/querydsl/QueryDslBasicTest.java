package study.querydsl;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.entity.Member;
import study.querydsl.entity.QMember;
import study.querydsl.entity.Team;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static study.querydsl.entity.QMember.member;

@SpringBootTest
@Transactional
public class QueryDslBasicTest {

    @Autowired
    EntityManager em;

    JPAQueryFactory queryFactory ;

    @BeforeEach
    public void before(){
        queryFactory = new JPAQueryFactory(em);
        Team teamB = new Team("teamB");
        Team teamA = new Team("teamA");

        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);

        Member member3 = new Member("member3", 30, teamA);
        Member member4 = new Member("member4", 40, teamA);

        em.persist( member1);
        em.persist( member2);
        em.persist( member3);
        em.persist( member4);

        em.flush();
        em.clear();
    }

    @Test
    public void startJPQL(){
        // member 1 found
        String qlString = "select m from Member m where m.username = 'member1'";


        Member findMember = em.createQuery
                            (qlString, Member.class)
                            .getSingleResult();

        assertThat(findMember.getUsername()).isEqualTo("member1");
    }


    @Test
    public void startQuerydsl(){
        // member 1 found // 엔티티매니저를 넘겨줘야됨 !
        // 컴파일에서 오류 발견
        queryFactory  = new JPAQueryFactory(em);
//        QMember m = new QMember("m"); // 구분하는 m <== 별칭
//        QMember m2 =  member; // 구분하는 m <== 별칭


        Member findMember = queryFactory
                .select( member)
                .from( member)
                .where( member.username.eq("member1"))
                .fetchOne();

        assertThat(findMember.getUsername()).isEqualTo("member1");
    }



    @Test
    public void search(){
        Member findMember = queryFactory
                .selectFrom(member)
                .where(member.username.eq("member1")
                        .and(member.age.between(10,30)))
                .fetchOne();

        assertThat(findMember.getUsername()).isEqualTo("member1");
    }

    @Test
    public void searchAndParam(){
        Member findMember = queryFactory
                .selectFrom(member)
                .where(
                        // and 와 동일
                        member.username.eq("member1")
                        , (member.age.eq(10)))
                .fetchOne();

        assertThat(findMember.getUsername()).isEqualTo("member1");
    }

    @Test
    public void resultFetch(){
//        List<Member> members = queryFactory
//                .selectFrom(member)
//                .fetch();
//
//        Member member1 = queryFactory
//                .selectFrom(member)
//                .fetchOne();
//
//        Member member2 = queryFactory.selectFrom(member).fetchFirst(); // limit 1

        QueryResults<Member> results =
                queryFactory
                        .selectFrom(member)
                        .fetchResults();

        results.getTotal();
        List<Member> contents = results.getResults();

        long total = queryFactory
                .selectFrom(member)
                .fetchCount();

    }

    /*****
     * 1.회원 정렬 순서 내림차순(DESC)
     * 2. 회원 이름 올림차순(ASC)
     * */
    @Test
    public void sort(){
        em.persist(new Member(null,100));
        em.persist(new Member("member5",100));
        em.persist(new Member("member6",100));

        List<Member> result = queryFactory
                .selectFrom(member)
                .where(member.age.eq(100))
                .orderBy(member.age.desc(), member.username.asc().nullsLast())
                .fetch();

        Member member5 = result.get(0);
        Member member6 = result.get(1);
        Member memberNull = result.get(2);

        assertThat(member5.getUsername()).isEqualTo("member5");
        assertThat(member6.getUsername()).isEqualTo("member6");
        assertThat(memberNull.getUsername()).isNull();
    }

}
