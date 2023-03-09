package com.gdsc.solutionChallenge.posts.entity;

import com.gdsc.solutionChallenge.member.entity.BaseTimeEntity;
import com.gdsc.solutionChallenge.member.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "certificate")
public class Certificate extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "certificate_id", nullable = false)
    private Long id;

    @Column(name = "member_id", nullable = false)
    private Integer member_id;

    @Column(name = "name")
    private String name; // 실명

    @Column(name = "title", nullable = false)
    private String title; // 활동명

    @Column(name = "start", nullable = false)
    private String start;

    @Column(name = "end", nullable = false)
    private String end;

    @Column(name = "number_of_lectures", nullable = false)
    private Integer lectures;

    @Column(name = "number_of_referrals", nullable = false)
    private Integer likes;

    @Column(name = "level", nullable = false)
    private String rank;

    @Builder
    public Certificate(String name, String title, String start, String end, Integer lectures, Integer likes, String rank) {
        this.name = name;
        this.title = title;
        this.start = start;
        this.end = end;
        this.lectures = lectures;
        this.likes = likes;
        this.rank = rank;
    }
}
