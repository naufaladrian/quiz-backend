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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "score")
public class Score extends Audit {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id")
    private String id;

    @Column(name = "point")
    private float point;

    @Column(name = "score")
    private float score;

    @OneToOne
    @JoinColumn(name = "school_id")
    private School schoolId;

    @OneToOne
    @JoinColumn(name = "materi_id")
    private Materi materiId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User userId;
}
