package com.app.quizz.model;

import com.app.quizz.audit.Audit;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "question")
public class Question extends Audit {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id")
    private String id;

    @Column(name = "answer_true")
    private String answerTrue;

    @Column(name = "count_used")
    private int countUsed;

    @Lob
    @Column(name = "image")
    private String image;

    @Lob
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "list_answer")
    private String listAnswer;

    @Column(name = "question")
    private String question;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "materi_id")
    private Materi materiId;

    @Transient
    private List<String> answerList=new ArrayList<>();
}
