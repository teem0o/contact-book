package com.dga.contactbook.repository;

import com.dga.contactbook.entity.ContactInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactInfoRepository extends JpaRepository<ContactInfo, Long> {

    @Query("SELECT c FROM ContactInfo c " +
            "WHERE (:fullName IS NULL OR lower( c.fullName) LIKE %:fullName%) " +
            "AND (:phoneNumber IS NULL OR lower( c.phoneNumber) LIKE %:phoneNumber%) " +
            "AND (:email IS NULL OR lower( c.email) LIKE %:email%) " +
            "AND (:address IS NULL OR lower( c.address) LIKE %:address%) " +
            "AND (c.user.id = :userId)")
    Page<ContactInfo> findContactInfoByCustomFilter(
            @Param("userId") Long userId,
            @Param("fullName") String fullName,
            @Param("phoneNumber") String phoneNumber,
            @Param("email") String email,
            @Param("address") String address,
            Pageable pageable);
}
