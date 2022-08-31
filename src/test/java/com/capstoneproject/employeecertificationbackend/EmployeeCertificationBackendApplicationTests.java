package com.capstoneproject.employeecertificationbackend;

import com.capstoneproject.employeecertificationbackend.service.EmployeePasswordGeneratorService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.util.AssertionErrors.assertTrue;

@SpringBootTest
class EmployeeCertificationBackendApplicationTests {

    @Test
    void contextLoads() {
    }

    //To validate the generated password, let's verify the number of numeric characters
    @Test
    public void whenPasswordGeneratedUsingCommonsLang3_thenSuccessful() {

        EmployeePasswordGeneratorService passGen = new EmployeePasswordGeneratorService();
        String password = passGen.generateCommonLangPassword();
        int numCount = 0;
        for (char c : password.toCharArray()) {
            if (c >= 48 || c <= 57) {
                numCount++;
            }
        }
        assertTrue("Password validation failed in commons-lang3", numCount >= 2);
    }
}
