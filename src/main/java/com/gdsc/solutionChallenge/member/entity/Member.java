package com.gdsc.solutionChallenge.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Table(name = "member")
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Oracle, h2 이면 SEQUENCE
    @Column(name = "id")
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    @NotBlank(message = "아이디를 입력해주세요.") // null, "", " " 불가
    private String username;

    @Column(nullable = false)
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    @Column
    private String name;

    @Column(nullable = false, length = 50)
    @NotBlank(message = "닉네임을 입력해주세요.")
    private String nickName;

    @Column(nullable = false)
    @NotNull(message = "학년은 null이 되면 안됩니다.") // Integer이기에 NotBlank 불가
    private Integer grade;

    @Column(nullable = false)
    @Email(message = "이메일 형식이 아닙니다.")
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;
}

