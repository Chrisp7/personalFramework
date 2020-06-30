package com.imooc.demo.reflect;

public class ReflectTarget {
    public static void main(String[] args) throws ClassNotFoundException {
        // The first way: getClass
        ReflectTarget reflectTarget = new ReflectTarget();
        Class reflectTargetClass1 = reflectTarget.getClass();
        System.out.println("1st-->" + reflectTargetClass1.getName());

        // the second way: xxx.class
        Class reflectTargetClass2 = ReflectTarget.class;
        System.out.println("2nd-->" + reflectTargetClass2.getName());

        // the third way: forName
        Class reflectTargetClass3 = Class.forName("com.imooc.demo.reflect.ReflectTarget");
        System.out.println("3rd-->" + reflectTargetClass3.getName());
    }
}
