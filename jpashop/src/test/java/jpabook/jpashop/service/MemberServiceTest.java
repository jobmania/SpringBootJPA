package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {


    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;
    @Test
    public void 회원가입() throws Exception{
        //given
        Member member = new Member();
        member.setName("엄");

        // when
        Long savedId = memberService.join(member);


        // then
        em.flush();
        assertEquals(member,memberRepository.findOne(savedId));
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception{
        //given

        Member member1 = new Member();
        member1.setName("엄");

        Member member2 = new Member();
        member2.setName("엄");

        // when
        memberService.join(member1);
        memberService.join(member2); // 예외 터져야됨




        // then
        fail("예외가 발생해야한다.");


    }

}