package com.dga.contactbook.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "contact_info")
@SQLDelete(sql = "UPDATE contact_info SET deleted = 'true' WHERE id = ?")
@Where(clause = "deleted='false'")
public class ContactInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String fullName;
    private String phoneNumber;
    private String email;
    private String address;
    private boolean deleted = false;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
