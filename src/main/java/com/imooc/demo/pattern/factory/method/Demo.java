package com.imooc.demo.pattern.factory.method;

import com.imooc.demo.pattern.factory.entity.Mouse;

public class Demo {
    public static void main(String[] args) {
        MouseFactory mf = new HpMouseFactory();
        Mouse mouse = mf.createMouse();
        mouse.sayHi();
    }
}
