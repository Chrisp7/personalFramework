package com.imooc.demo.pattern.factory.entity;

import com.imooc.demo.annotation.TestAnnotation;

public class DellMouse implements Mouse {
    @Override
    @TestAnnotation
    public void sayHi() {
        System.out.println("I'm dell mouse");
    }
}
