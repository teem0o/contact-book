package com.dga.contactbook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContactInfoRequest {
    private Long id;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String address;
}
