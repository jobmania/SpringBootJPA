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
}