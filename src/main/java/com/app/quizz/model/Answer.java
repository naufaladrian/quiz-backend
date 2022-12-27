package com.app.quizz.model;

import com.app.quizz.audit.Audit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "answer")
public class Answer extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "is_correct")
    private boolean isCorrect;

    @Column(name = "answer")
    private String answer;

    @OneToOne
    @JoinColumn(name = "question_id")
    private Question questionId;

    @OneToOne
    @JoinColumn(name = "materi_id")
    private Materi materiId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User userId;
}
