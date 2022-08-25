package com.example.springbootstudybasictest.vo;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class HRTeamMemberVO {
    private String firstName;
    private String lastName;
    private String jobId;
    private String locationId;
    private String part;
    private String city;
    private String countryName;
}
