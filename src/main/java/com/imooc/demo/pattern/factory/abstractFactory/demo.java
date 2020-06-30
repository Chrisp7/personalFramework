package com.imooc.demo.pattern.factory.abstractFactory;

import com.imooc.demo.pattern.factory.entity.Keyboard;
import com.imooc.demo.pattern.factory.entity.Mouse;

public class demo {
    public static void main(String[] args) {
        ComputerFactory cf = new DellCompuerFactory();
        Mouse mouse = cf.createMouse();
        Keyboard keyboard = cf.createKeyboard();
        mouse.sayHi();
        keyboard.sayHello();

    }
}
