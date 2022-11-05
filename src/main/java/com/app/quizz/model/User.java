package com.app.quizz.model;

import com.app.quizz.audit.Audit;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user")
public class User extends Audit {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id")
    private String id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Lob
    @Column(name = "url_profile")
    private String urlProfile;

    @OneToOne
    @JoinColumn(name = "role_id")
    private Role roleId;

    @Column(name = "ip_address")
    private String ipAddress;


    @OneToOne
    @JoinColumn(name = "school_id")
    private School schoolId;
}
