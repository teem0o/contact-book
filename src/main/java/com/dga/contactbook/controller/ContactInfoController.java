package com.dga.contactbook.controller;

import com.dga.contactbook.dto.ContactInfoRequest;
import com.dga.contactbook.dto.ContactInfoResponse;
import com.dga.contactbook.service.ContactInfoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/contact-info")
@RequiredArgsConstructor
public class ContactInfoController {

    private final ContactInfoService contactInfoService;

    @GetMapping
    public ResponseEntity<Page<ContactInfoResponse>> getAllContactInfo(@NonNull HttpServletRequest request,
                                                                       @RequestParam(required = false) String fullName,
                                                                       @RequestParam(required = false) String phoneNumber,
                                                                       @RequestParam(required = false) String email,
                                                                       @RequestParam(required = false) String address,
                                                                       Pageable pageable) {
        return ResponseEntity.ok(contactInfoService.getAllContactInfo(request, fullName, phoneNumber, email, address, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactInfoResponse> getContactInfoById(@NonNull HttpServletRequest request, @PathVariable Long id) {
        return ResponseEntity.ok(contactInfoService.getContactInfoById(request, id));
    }

    @PostMapping
    public ResponseEntity<ContactInfoResponse> createContactInfo(@NonNull HttpServletRequest request, @RequestBody ContactInfoRequest contactInfoRequest) {
        return ResponseEntity.ok(contactInfoService.createContactInfo(request, contactInfoRequest));
    }

    @PutMapping
    public ResponseEntity<ContactInfoResponse> updateContactInfo(@NonNull HttpServletRequest request, @RequestBody ContactInfoRequest contactInfoRequest) {
        return ResponseEntity.ok(contactInfoService.updateContactInfo(request, contactInfoRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContactInfo(@NonNull HttpServletRequest request, @PathVariable Long id) {
        contactInfoService.deleteContactInfo(request, id);
        return ResponseEntity.ok().build();
    }

}
