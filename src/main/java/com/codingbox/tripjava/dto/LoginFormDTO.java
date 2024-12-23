package com.codingbox.tripjava.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginFormDTO {
	
	private String email; // 로그인 요청 이메일 아이디
	private String password;// 패스워드
	
	public LoginFormDTO(String email, String password) {
		this.email = email;
		this.password = password;
	}
	public LoginFormDTO() {}
	
}
