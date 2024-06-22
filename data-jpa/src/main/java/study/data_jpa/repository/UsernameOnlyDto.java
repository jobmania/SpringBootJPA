package study.data_jpa.repository;

import lombok.Getter;

@Getter
public class UsernameOnlyDto {

    private final String userName;

    /** 생성자 파라미터 명과 repositoy 파라미터명을 일치해야됨 . 이름으로 매칭시킨다 ! */
    public UsernameOnlyDto(String userName) {
        this.userName = userName;
    }

}
