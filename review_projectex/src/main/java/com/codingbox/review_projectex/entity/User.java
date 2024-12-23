package com.codingbox.review_projectex.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * USERS 엔티티 클래스
 * USERS 테이블과 매핑되는 클래스입니다.
 */
@Entity
@Getter
@Setter
@Table(name = "USERS")
public class User {

    /**
     * 사용자 ID (기본키)
     * 시퀀스를 사용하여 자동으로 증가합니다.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
    @SequenceGenerator(name = "user_id_seq", sequenceName = "user_id_seq", allocationSize = 1)
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 사용자 이름
     * 데이터베이스에서 NOT NULL로 설정됩니다.
     */
    @Column(name = "username", nullable = false)
    private String username;

    /**
     * 비밀번호
     * NULL 가능. 데이터베이스에서 관리됩니다.
     */
    @Column(name = "password")
    private String password;

    /**
     * 이메일
     * 데이터베이스에서 UNIQUE로 설정됩니다.
     */
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    /**
     * 전화번호
     */
    @Column(name = "phone")
    private String phone;

    /**
     * 소셜 ID
     */
    @Column(name = "social_id")
    private String socialId;

    /**
     * 소셜 로그인 제공자
     */
    @Column(name = "social_provider")
    private String socialProvider;

    // 기본 생성자
    public User() {
    }

    /**
     * 사용자 정보를 초기화하는 생성자
     */
    public User(String username, String password, String email, String phone, String socialId, String socialProvider) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.socialId = socialId;
        this.socialProvider = socialProvider;
    }

    // 회원가입 할 생성자 추가
	public User(String email, String username, String socialId) {
		this.email = email;
		this.username = username;
		this.socialId = socialId;
	}
}