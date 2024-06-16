package study.data_jpa.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.data_jpa.dto.MemberDto;
import study.data_jpa.entity.Member;
import study.data_jpa.entity.Team;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TeamRepository  teamRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    void testMethod() {
        System.out.println("memberRepository.getClass() = " + memberRepository.getClass());
        Member member = new Member("memberA", 2);
        Member savedMember = memberRepository.save(member);

        Optional<Member> findMember = memberRepository.findById(savedMember.getId());

        assertThat(findMember.get().getId()).isEqualTo(member.getId());
        assertThat(findMember.get().getUserName()).isEqualTo(member.getUserName());
        assertThat(findMember.get()).isEqualTo(member);
    }

    @Test
    public void basicCRUD(){
        Member member = new Member("member1", 12);
        Member member2 = new Member("member2", 32);

        memberRepository.save(member);
        memberRepository.save(member2);
        Member findMember1 = memberRepository.findById(member.getId()).get();
        Member findMember2 = memberRepository.findById(member2.getId()).get();

        assertThat(findMember1).isEqualTo(member);
        assertThat(findMember2).isEqualTo(member2);

        // 리스트 조회
        List<Member> all = memberRepository.findAll();
        assertThat(all.size()).isEqualTo(2);

        // 카운트
        long count = memberRepository.count();
        assertThat(count).isEqualTo(2);

        // 삭제
        memberRepository.delete(member);
        memberRepository.delete(member2);

        // 삭제후 검증

        long deleteCount = memberRepository.count();
        assertThat(deleteCount).isEqualTo(0);
    }


    @Test
    public void findByUsernameAndAgeGreaterThen(){
        Member member1 = new Member("AAA", 10);
        Member member2 = new Member("AAA", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> result = memberRepository.findByUserNameAndAgeGreaterThan("AAA", 15);


        assertThat(result.get(0).getUserName()).isEqualTo("AAA");
        assertThat(result.get(0).getAge()).isEqualTo(20);
        assertThat(result.size()).isEqualTo(1);
    }


    @Test
    public void testQuery(){
        Member member1 = new Member("AAA", 10);
        Member member2 = new Member("BBB", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> result = memberRepository.findUser("AAA", 10);


        assertThat(result.get(0).getUserName()).isEqualTo("AAA");
        assertThat(result.get(0).getAge()).isEqualTo(20);
        assertThat(result.size()).isEqualTo(1);
    }


    @Test
    public void finduserNameList(){
        Member member1 = new Member("AAA", 10);
        Member member2 = new Member("BBB", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<String> result = memberRepository.findUsernabeList();

        for (String s : result) {
            System.out.println("s = " + s);
        }

    }


    @Test
    public void finduserTeam(){



        Team teamA = new Team("teamA");
        teamRepository.save(teamA);

        Member member1 = new Member("AAA", 10);
        memberRepository.save(member1);

        member1.setTeam(teamA);

        List<MemberDto> memberDto = memberRepository.findMemberDto();

        for (MemberDto dto : memberDto) {
            System.out.println("dto.getId() = " + dto.getId());
            System.out.println("dto.getUsername() = " + dto.getUsername());
            System.out.println("dto.getUsername() = " + dto.getTeamName());
        }

    }


    @Test
    public void findByNames(){

        Member member1 = new Member("AAA", 10);
        Member member2 = new Member("BBB", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> result = memberRepository.findByNames(Arrays.asList("AAA","BBB"));

        for (Member member : result) {
            System.out.println("member = " + member);
        }

    }




    @Test
    public void returnTypes(){

        Member member1 = new Member("AAA", 10);
        Member member2 = new Member("AAA", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);

        Optional<Member> aaa = memberRepository.findOptionalByUserName("AAA");
        /***
         * 결과값이 2개 ..
         * IncorrectResultSizeDataAccessException: 이 발생.
         * 스프링이 .NonUniqueResultException 예외를 한번 변환해붐
         * */


    }

    @Test
    public void paging(){


        //given

        Member member1 = new Member("AAA1", 10);
        Member member2 = new Member("AAA2", 10);
        Member member3 = new Member("AAA3", 10);
        Member member4 = new Member("AAA4", 10);
        Member member5 = new Member("AAA5", 10);

        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
        memberRepository.save(member4);
        memberRepository.save(member5);

        int age = 10;
        int offset = 1;
        int limit = 3;

        PageRequest pageReq = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "userName"));

        //when

        Page<Member> page = memberRepository.findByAge(age, pageReq); // 페이지 시작은 0
//        Slice<Member> slicePage = memberRepository.findByAge(age, pageReq);


        /** DTO 변환 */
        Page<MemberDto> toMap = page.map(member -> new MemberDto(member.getId(), member.getUserName(), "엄"));

        //then
        /** 데이터 꺼내기. */
        List<Member> content = page.getContent();

        for (Member member : content) {
            System.out.println("member = " + member);
        }

        /** 갯수.. */

        /**
         * total Count 쿼리는 조인을 할 필요가 없다 ..
         * left outer join.. 그래서 countqueyr를 따로 만든다.
         *
         * */
        long totalElements = page.getTotalElements();


        /***
         *
         * 페이징. 기능 ..
         * */

        assertThat(content.size()).isEqualTo(3);
        assertThat(page.getTotalElements()).isEqualTo(5);
        assertThat(page.getNumber()).isEqualTo(0);
        assertThat(page.getTotalPages()).isEqualTo(2);
        assertThat(page.isFirst()).isTrue();
        assertThat(page.hasNext()).isTrue();
//
//        assertThat(content.size()).isEqualTo(3);
//        assertThat(slicePage.getNumber()).isEqualTo(0);
//        assertThat(slicePage.isFirst()).isTrue();
//        assertThat(slicePage.hasNext()).isTrue();




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

        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
        memberRepository.save(member4);
        memberRepository.save(member5);

        // 벌크 연산시 영속성 컨텍스트 변경감지  x;;
        // 벌크 연산후에는 영속성 컨텍스틑 비우자 !
        int resultCount = memberRepository.bulkAgePlus(10);
//        em.flush();
//        em.clear();

        List<Member> aa5 = memberRepository.findListByUserName("AA5");
        Member member = aa5.get(0);
        // DB에
        System.out.println("member.getAge() = " + member.getAge());;

        //then
        assertThat(resultCount).isEqualTo(5);
    }

    @Test
    public void findMemberLazy(){
        // given
        // member 1 -> teamA
        // member2 -> team B

        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");

        teamRepository.save(teamA);
        teamRepository.save(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 23, teamB);

        memberRepository.save(member1);
        memberRepository.save(member2);


        em.flush();
        em.clear();

        //when
        List<Member> members = memberRepository.findEntityGraphByUserName("member1");


        //then

        for (Member member : members) {
            System.out.println(member.getUserName());
            System.out.println("member.getTeam().getName() = " + member.getTeam().getName());
        }


    }

    @Test
    public void queryHint(){

        //given
        Member member = new Member("member1", 10);
        memberRepository.save(member);
        em.flush();
        em.clear();

        //when --- read_only 스냅샷을 안만들어버림
        // 변경감지 -- 기존 객체와 변경 객체 비교..
        Member findMember = memberRepository.findReadOnlyByUserName("member1");
        findMember.setUserName("member2");
        em.flush();

        //then


    }


    @Test
    public void lock(){


        //given
        Member member = new Member("member1", 10);
        memberRepository.save(member);
        em.flush();
        em.clear();

        //when --- read_only 스냅샷을 안만들어버림
        // 변경감지 -- 기존 객체와 변경 객체 비교..
        List<Member> member1 = memberRepository.findLockByUserName("member1");

        //then

    }

    @Test
    public void callCustom(){
        List<Member> result = memberRepository.findMemberCustom();

    }

}