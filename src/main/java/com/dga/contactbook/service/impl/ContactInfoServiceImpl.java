package com.dga.contactbook.service.impl;

import com.dga.contactbook.dto.ContactInfoRequest;
import com.dga.contactbook.dto.ContactInfoResponse;
import com.dga.contactbook.entity.ContactInfo;
import com.dga.contactbook.entity.User;
import com.dga.contactbook.exception.ContactInfoNotFoundException;
import com.dga.contactbook.exception.ContactInfoPermissionException;
import com.dga.contactbook.repository.ContactInfoRepository;
import com.dga.contactbook.repository.UserRepository;
import com.dga.contactbook.security.JWTService;
import com.dga.contactbook.service.ContactInfoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@AllArgsConstructor
public class ContactInfoServiceImpl implements ContactInfoService {
    private final ContactInfoRepository contactInfoRepository;
    private final UserRepository userRepository;
    private ModelMapper modelMapper;
    private final JWTService jwtService;

    @Override
    public Page<ContactInfoResponse> getAllContactInfo(HttpServletRequest request, String fullName, String phoneNumber,
                                                       String email, String address, Pageable pageable) {
        var username = getUserName(request);
        var user = modelMapper.map(userRepository.findByUsername(username), User.class);
        return contactInfoRepository.findContactInfoByCustomFilter(user.getId(),
                        fullName != null ? fullName.toLowerCase(Locale.ROOT) : null,
                        phoneNumber != null ? phoneNumber.toLowerCase(Locale.ROOT) : null,
                        email != null ? email.toLowerCase(Locale.ROOT) : null,
                        address != null ? address.toLowerCase(Locale.ROOT) : null, pageable)
                .map(contactInfo -> modelMapper.map(contactInfo, ContactInfoResponse.class));
    }

    @Override
    public ContactInfoResponse getContactInfoById(HttpServletRequest request, Long id) {
        checkAuthority(id, request, "get");
        return contactInfoRepository.findById(id).map(ci -> modelMapper.map(ci, ContactInfoResponse.class))
                .orElseThrow(() -> new ContactInfoNotFoundException(id));
    }

    @Override
    public ContactInfoResponse createContactInfo(HttpServletRequest request, ContactInfoRequest contactInfoRequest) {
        var username = getUserName(request);
        var user = modelMapper.map(userRepository.findByUsername(username), User.class);
        var contactInfo = modelMapper.map(contactInfoRequest, ContactInfo.class);
        contactInfo.setUser(user);
        var savedContactInfo = contactInfoRepository.save(contactInfo);
        return modelMapper.map(savedContactInfo, ContactInfoResponse.class);
    }

    @Override
    public ContactInfoResponse updateContactInfo(HttpServletRequest request, ContactInfoRequest contactInfoRequest) {
        var contactInfo = checkAuthority(contactInfoRequest.getId(),
                request, "update");
        modelMapper.map(contactInfoRequest, contactInfo);
        contactInfoRepository.save(contactInfo);
        return modelMapper.map(contactInfo, ContactInfoResponse.class);

    }

    @Override
    public void deleteContactInfo(HttpServletRequest request, Long id) {
        checkAuthority(id, request, "delete");
        contactInfoRepository.deleteById(id);
    }

    private ContactInfo checkAuthority(Long id, HttpServletRequest request, String actionType) {
        var contactInfo = contactInfoRepository.findById(id)
                .orElseThrow(() -> new ContactInfoNotFoundException(id));
        var username = getUserName(request);
        var user = modelMapper.map(userRepository.findByUsername(username), User.class);
        if (!user.getId().equals(contactInfo.getUser().getId())) {
            throw new ContactInfoPermissionException(actionType, id);
        }
        return contactInfo;
    }

    private String getUserName(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        var jwt = authHeader.substring(7);
        return jwtService.extractUsername(jwt);
    }
}
