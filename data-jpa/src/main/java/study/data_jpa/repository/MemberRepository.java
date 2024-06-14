package study.data_jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.data_jpa.entity.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    /** 파라미터가 많아질수록 메서드 명이 길어져용~ */
    List<Member> findByUserNameAndAgeGreaterThan(String username, int age);

    /** 실무에서 많이써용~ ㅋ  */
    /*** 오타 치면 컴파일상에서 바로 잡아줘용 ㅋ~ m.user123123Name */
    @Query("select m from Member m where m.userName = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);
}
