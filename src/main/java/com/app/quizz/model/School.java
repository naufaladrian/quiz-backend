package com.app.quizz.model;

import com.app.quizz.audit.Audit;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "school")
public class School extends Audit {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "head_master")
    private String headMaster;

    @Column(name = "total_user")
    private int totalUser;

    @Column(name = "phone_number")
    private int phoneNumber;

    @Lob
    @Column(name = "address")
    private String address;

    @Column(name = "update_by")
    private String updateBy;

}
