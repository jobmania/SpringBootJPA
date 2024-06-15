package study.data_jpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.data_jpa.entity.Member;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberJpaRepositoryTest {

    @Autowired
    MemberJpaRepository memberJpaRepository;


    @Test
    void testMethod() {
        Member member = new Member("memberA", 2);
        Member savedMember = memberJpaRepository.save(member);

        Member findMember = memberJpaRepository.find(savedMember.getId());

        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUserName()).isEqualTo(member.getUserName());
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    public void basicCRUD(){
        Member member = new Member("member1", 12);
        Member member2 = new Member("member2", 32);

        memberJpaRepository.save(member);
        memberJpaRepository.save(member2);
        Member findMember1 = memberJpaRepository.findById(member.getId()).get();
        Member findMember2 = memberJpaRepository.findById(member2.getId()).get();

        assertThat(findMember1).isEqualTo(member);
        assertThat(findMember2).isEqualTo(member2);

        // 리스트 조회
        List<Member> all = memberJpaRepository.findAll();
        assertThat(all.size()).isEqualTo(2);

        // 카운트
        long count = memberJpaRepository.count();
        assertThat(count).isEqualTo(2);

        // 삭제
        memberJpaRepository.delete(member);
        memberJpaRepository.delete(member2);

        // 삭제후 검증

        long deleteCount = memberJpaRepository.count();
        assertThat(deleteCount).isEqualTo(0);
    }


    @Test
    public void findByUsernameAndAgeGreaterThen(){
        Member member1 = new Member("AAA", 10);
        Member member2 = new Member("AAA", 20);
        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);

        List<Member> result = memberJpaRepository.findByUsernameAndAgeGreaterThen("AAA", 15);


        assertThat(result.get(0).getUserName()).isEqualTo("AAA");
        assertThat(result.get(0).getAge()).isEqualTo(20);
        assertThat(result.size()).isEqualTo(1);
    }


    @Test
    public void paging(){


        //given

        Member member1 = new Member("AAA", 10);
        Member member2 = new Member("AAA", 10);
        Member member3 = new Member("AAA", 10);
        Member member4 = new Member("AAA", 10);
        Member member5 = new Member("AAA", 10);

        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);
        memberJpaRepository.save(member3);
        memberJpaRepository.save(member4);
        memberJpaRepository.save(member5);

        int age = 10;
        int offset = 1;
        int limit = 3;

        //when

        List<Member> members = memberJpaRepository.findByPage(age, offset, limit);
        long totalCount = memberJpaRepository.totalCount(age);

        //then

        assertThat(members.size()).isEqualTo(3);
        assertThat(totalCount).isEqualTo(5);
    }

    @Test
    public void bulkTest(){
        //given


        Member member1 = new Member("AAA", 10);
        Member member2 = new Member("AA2", 12);
        Member member3 = new Member("AA3", 15);
        Member member4 = new Member("AA4", 21);
        Member member5 = new Member("AA5", 30);
        //when

        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);
        memberJpaRepository.save(member3);
        memberJpaRepository.save(member4);
        memberJpaRepository.save(member5);

        int resultCount = memberJpaRepository.bulkAgePlus(20);

        //then
        assertThat(resultCount).isEqualTo(2);
    }

}