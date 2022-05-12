package com.nasa.suite;

import com.nasa.test.CuriosityTest;
import org.testng.annotations.Factory;

public class NasaFactory {

    @Factory
    public Object[] factoryMethod() {
        return new Object[] {
                new CuriosityTest()
        };
    }

}
