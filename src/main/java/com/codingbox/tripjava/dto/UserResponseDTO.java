package com.codingbox.tripjava.dto;

import com.codingbox.tripjava.entity.User;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserResponseDTO {
	private User user;
    private String accessToken;
	
	
	public UserResponseDTO(User user, String accessToken) {
		this.user = user;
        this.accessToken = accessToken;
	}

}
