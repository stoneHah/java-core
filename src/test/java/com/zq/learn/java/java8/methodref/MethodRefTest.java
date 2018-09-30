package com.zq.learn.java.java8.methodref;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class MethodRefTest {

    @Test
    public void testMethodRef(){
        //构造方法引用
        Car car = Car.create(Car::new);

        List<Car> cars = Arrays.asList(car);
        cars.forEach(Car::collide);

        cars.forEach(Car::repair);
    }
}
