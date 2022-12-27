package com.app.quizz.model;

import com.app.quizz.audit.Audit;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "materi")
public class Materi extends Audit {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "teacher")
    private String teacher;

    @Column(name = "total_question")
    private int totalQuestion;

    @Column(name = "active")
    private boolean active;

    @Column(name = "already_answer")
    private boolean alreadyAnswer;

    @OneToOne
    @JoinColumn(name = "school_id")
    private School schoolId;

    @Transient
    private List<Question> questions =new ArrayList<>();
}
