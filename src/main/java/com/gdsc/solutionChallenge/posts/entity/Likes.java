package com.gdsc.solutionChallenge.posts.entity;

import com.gdsc.solutionChallenge.member.entity.BaseTimeEntity;
import com.gdsc.solutionChallenge.member.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "likes",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "likes_unique",
                        columnNames = {"post_id", "member_id"}
                )
        })
public class Likes extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;



    //== 연관관계 메서드 ==//
    public void confirmMemberPost(Member member, Post post) {
        this.member = member;
        this.post = post;
        member.addLikes(this);
        post.addLikes(this);
    }
}
