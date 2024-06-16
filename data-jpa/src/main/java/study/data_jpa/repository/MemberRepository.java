package study.data_jpa.repository;

import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import study.data_jpa.dto.MemberDto;
import study.data_jpa.entity.Member;
import study.data_jpa.entity.Team;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    /** 파라미터가 많아질수록 메서드 명이 길어져용~ */
    List<Member> findByUserNameAndAgeGreaterThan(String username, int age);

    /** 실무에서 많이써용~ ㅋ  */
    /*** 오타 치면 컴파일상에서 바로 잡아줘용 ㅋ~ m.user123123Name */
    @Query("select m from Member m where m.userName = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    @Query("select m.userName from Member m")
    List<String> findUsernabeList();

    @Query("select new study.data_jpa.dto.MemberDto(m.id, m.userName, t.name)  from Member m join m.team t")
    List<MemberDto> findMemberDto();

    /** List형 Parameter ! */
    @Query("select m from Member m where m.userName in :names")
    List<Member> findByNames(@Param("names") List<String> names);

    List<Member> findListByUserName(String username); // 컬랙션
    List<Member> findMemberByUserName(String username); // 단건
    Optional<Member> findOptionalByUserName(String username); // 단건 Optional


    @Query(value = "select m from Member m left join m.team t",
           countQuery = "select count(m) from Member m" ) // 카운트 쿼리 분리..
    Page<Member> findByAge(int age, Pageable pageable);
//    Slice<Member> findByAge(int age, Pageable pageable);

    @Modifying(clearAutomatically = true) // 벌크성!  업데이트 |  쿼리 변경한 행수 반환
    @Query("update Member m set m.age = m.age +1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);


    /** 페치 조인 */
    @Query("select m from Member m left join fetch m.team")
    List<Member> findMemberFetchJoin();

    /** 엔티티그래프 */
    @Override
    @EntityGraph(attributePaths = {"team"})
    List<Member> findAll();

    @EntityGraph(attributePaths = {"team"})
    @Query("select m from Member m ")
    List<Member> findMemberEntityGraphWithWJPQL();


    @EntityGraph(attributePaths = {"team"}) // find...ByUserName.
    List<Member> findEntityGraphByUserName(String username);

    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Member findReadOnlyByUserName(String username);

    // select for update
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Member> findLockByUserName(String username);
}
