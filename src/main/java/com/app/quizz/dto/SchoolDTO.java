package com.app.quizz.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Lob;

@Setter
@Getter
public class SchoolDTO {

    private String name;


    private String headMaster;


    private int phoneNumber;


    private String address;

}
