package com.dga.contactbook.service;

import com.dga.contactbook.dto.ContactInfoRequest;
import com.dga.contactbook.dto.ContactInfoResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContactInfoService {
    Page<ContactInfoResponse> getAllContactInfo(HttpServletRequest request, String fullName, String phoneNumber, String email, String address, Pageable pageable);

    ContactInfoResponse getContactInfoById(HttpServletRequest request, Long id);

    ContactInfoResponse createContactInfo(HttpServletRequest request, ContactInfoRequest contactInfoRequest);

    ContactInfoResponse updateContactInfo(HttpServletRequest request, ContactInfoRequest contactInfoRequest);

    void deleteContactInfo(HttpServletRequest request, Long id);

}
