package com.gdsc.solutionChallenge;

import com.gdsc.solutionChallenge.global.utils.EmailConverter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ValueAnnotationTest {

    @Autowired
    EmailConverter emailConverter;

    @Test
    void test() {
        System.out.println(emailConverter.toString());
    }
}
