package com.crossover.techtrial.java.se.util;


import com.crossover.techtrial.java.se.common.execption.ServiceRuntimeException;

/**
 * This class provide validation facility for Object and String
 */
public class ValidationUtil {

    public static void validate(String str, String massage) {

        if (str == null || str.isEmpty()) {
            throw new ServiceRuntimeException(massage);
        }
    }

    public static void validate(Object str, String massage) {

        if (str == null) {
            throw new ServiceRuntimeException(massage);
        }
    }
}
