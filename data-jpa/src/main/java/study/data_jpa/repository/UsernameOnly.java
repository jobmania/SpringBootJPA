package study.data_jpa.repository;

import org.springframework.beans.factory.annotation.Value;

public interface UsernameOnly {
    /** Open Projection : SpringSPL 지원 ! 전체 컬럼들고온다음에 어플리케이션에서 계싼  */
//    @Value("#{target.userName + ' ' + target.age}")
//    String getUserName();

    /** Close Projection 특정 컬럼만들고옴 */
    String getUserName();
}
