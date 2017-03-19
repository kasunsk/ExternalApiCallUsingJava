package com.crossover.techtrial.java.se.common.service;

/**
 * Basic service logic function provider.
 */
public interface ServiceLogic<T, V> {

    T invoke(V var);

}
