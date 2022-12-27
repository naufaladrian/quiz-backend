package com.app.quizz;


import com.app.quizz.dto.UserDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Testing {

    public static void main(String[] args) {
        String[] arr={"test1", "test2","test3"};
        List<String> arrList= List.of(arr);

        boolean findAnswerTrueInAnswerList
                = Arrays.asList(arr)
                .contains("");
        System.out.println(findAnswerTrueInAnswerList);

    }
}
