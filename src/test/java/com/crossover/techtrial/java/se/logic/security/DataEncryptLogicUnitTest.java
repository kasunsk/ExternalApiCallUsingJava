package com.crossover.techtrial.java.se.logic.security;


import com.crossover.techtrial.java.se.common.execption.ServiceRuntimeException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class DataEncryptLogicUnitTest {

    @InjectMocks
    DataEncryptLogic logic = new DataEncryptLogic();

    @Mock
    Environment environment;

    @BeforeMethod(alwaysRun = true)
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expectedExceptions = ServiceRuntimeException.class)
    public void encryptInputInvalidTest() {
        logic.invoke(null);
    }

    @Test(expectedExceptions = ServiceRuntimeException.class)
    public void encryptInputEmptyFailTest() {
        logic.invoke("");
    }

    @Test
    public void encryptTest(){

        when(environment.getRequiredProperty("encrypt.secret.key")).thenReturn("testKey");
        String encryptedString = logic.invoke("test");
        assertEquals("nWwUfP1PfCSujaCqqVUjBg==", encryptedString);
    }


}
