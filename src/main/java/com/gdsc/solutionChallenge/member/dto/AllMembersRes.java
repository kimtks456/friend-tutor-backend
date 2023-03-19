package com.gdsc.solutionChallenge.member.dto;

import com.gdsc.solutionChallenge.member.entity.Member;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AllMembersRes {
    private String time;
    private String message;
    private List<MemberInfo> details;
}
