package com.aakiproject.journalApp.services;

import com.aakiproject.journalApp.entity.User;
import com.aakiproject.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServicesTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServices userServices;

    @Test
    public void testFindByUserName() {
        assertEquals(4,2+2);
        assertNotNull(userRepository.findByUserName("shyam"));
    }

    @ParameterizedTest
    @ArgumentsSource(UserArgumentsProvider.class)
    public void testSaveNewUser(User user){
        assertTrue(userServices.saveNewUser(user));
    }

    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "2,10,12",
            "3,3,6"
    })
    public void test(int a, int b, int expected){
        assertEquals(expected,a+b);
    }
}
