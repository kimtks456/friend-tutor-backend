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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "course_info")
public class Post extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private Member writer;

    @Column(name = "grade", nullable = false)
    private Integer grade;

    @Column(name = "subject", nullable = false)
    private String subject;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "drive_link")
    private String drive_link;

    @Column(name = "video_link")
    private String video_link;

    @Column(name = "likes")
    private Integer likes;





    //== 연관관계 메서드 ==//
    public void confirmWriter(Member writer) {

        this.writer = writer;
        writer.addCourse(this);
    }
}
