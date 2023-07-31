package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)  // flush 안하고, 더티체킹안해서 성능이득.
@RequiredArgsConstructor // final 붙은 필드에 대한 주입.
public class MemberService {


    private final MemberRepository memberRepository; // 생성자 미주입에 대한 실수를 방지

//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    // setter 인젝션은 변경위험성,,

    /*
     회원가입
    */
    @Transactional
    public Long join(Member member){

        // 실제로는 진짜 동시에 요청을 할 수 있기때문에, member name에 대한 유니크 제약조건을 추가해야된다.(다단계 방어)
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId(); // 영속성 context 에서 꺼내옴.
    }

    private void validateDuplicateMember(Member member) {
        // 중복일시 예외 Exception
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원");
        }

    }

    // 회원 전체 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }


}
