package com.gdsc.solutionChallenge.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

@Table(name = "member")
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Member extends BaseTimeEntity implements UserDetails {

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

    @ElementCollection(fetch = FetchType.EAGER) // 1:M 에서는 M을 필요시점에 가져올 수 있는데 바로가져오기.
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    //== 패스워드 암호화 ==//
    public void encodePassword(PasswordEncoder passwordEncoder){
        this.password = passwordEncoder.encode(password);
    }

    public boolean matchPassword(PasswordEncoder passwordEncoder, String checkPassword){
        return passwordEncoder.matches(checkPassword, getPassword());
    }

    public void addUserAuthority() {
        this.roles.add(Role.USER.getKey());
    }
}

