package com.codingbox.tripjava.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberFormDTO {
// 회원가입용 데이터
	private Integer id; // pk값
	private String email;		// 이메일아이디
    private String password;	// 패스워드
    private String username;	// 이름
    private String phone;	// 휴대폰 번호
}
