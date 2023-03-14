package com.gdsc.solutionChallenge.member.entity;

import static jakarta.persistence.CascadeType.ALL;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gdsc.solutionChallenge.global.config.AES;
import com.gdsc.solutionChallenge.global.utils.EmailConverter;
import com.gdsc.solutionChallenge.posts.entity.Post;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
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
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;
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
@Slf4j
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
    @Convert(converter = EmailConverter.class)
    private String email;

    // 컬렉션은 기본적으로 부모 Entity와 한 쌍으로 움직이기 때문에 cascade 옵션이 없어도 부모 Entity와 함께 저장/삭제된다. (cascade 옵션을 ALL로 준 것 처럼 작동함)
    // https://prohannah.tistory.com/132
    @ElementCollection(fetch = FetchType.EAGER) // 1:M 에서는 M을 필요시점에 가져올 수 있는데 바로가져오기.
    @Builder.Default // 객체를 원하는 값으로 initialize해서 받을 수 있음.
    private List<String> roles = new ArrayList<>();

    @Column
    private Integer score;

    @Builder.Default
    @JsonIgnore
    // 연관관계의 owner가 아니므로, read만 가능하게 단방향 하나 더 추가해서 양방향으로 만듦.
    // child class에서 선언한 Parent class의 변수명
    @OneToMany(mappedBy = "writer", cascade = ALL, orphanRemoval = true)
    private List<Post> postList = new ArrayList<>();


//    @PrePersist
//    @PreUpdate
//    private void encryptEmail() {
//        if (!isValidEmail(this.email)) {
//            log.error(this.email.toString());
//            throw new RuntimeException("이메일 형식이 아닙니다.");
//        }
//        try {
//            this.email = AES.encrypt(this.email);
//        } catch (Exception e) {
//            // Handle the exception if the email address is invalid or encryption fails
//            // You can throw a custom exception or log the error message here
//            throw new RuntimeException("이메일 암호화 실패 : ", e.getCause());
//        }
//    }
//
//    @PostLoad
//    private void decryptEmail() {
//        try {
//            this.email = AES.decrypt(this.email);
//        } catch (Exception e) {
//            throw new RuntimeException("이메일 복호화 실패 : ", e);
//        }
//    }



    //== 연관관계 메서드 ==//
    public void addCourse(Post post){
        this.postList.add(post);
    }

    public void addScore(Integer score){
        this.score += score;
    }


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

    public boolean isValidEmail(String email) {
        EmailValidator emailValidator = new EmailValidator();
        return emailValidator.isValid(email, null);
    }
}
