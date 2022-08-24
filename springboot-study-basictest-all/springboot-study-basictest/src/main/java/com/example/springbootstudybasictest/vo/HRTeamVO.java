package com.example.springbootstudybasictest.vo;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class HRTeamVO {
    private String jobId;
    private String jobTitle;
    private String sectionId;
    private String sectionName;
    private String teamId;
    private String teamName;
}
