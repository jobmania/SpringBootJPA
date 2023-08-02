package jpabook.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class MemberForm {
    @NotEmpty(message = "회원이름 필수야 토카토카")
    private String name;
    private String city;
    private String street;
    private String zipcode;
}
