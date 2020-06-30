package com.imooc.demo.pattern.factory.simple;

import com.imooc.demo.pattern.factory.entity.DellMouse;
import com.imooc.demo.pattern.factory.entity.HpMouse;
import com.imooc.demo.pattern.factory.entity.Mouse;

public class MouseFactory {
    public static Mouse createMouse(int type) {
        switch (type) {
            case 0:
                return new DellMouse();
            case 1:
                return new HpMouse();
            default:
                throw new RuntimeException("error input");
        }
    }

    public static void main(String[] args) {
        Mouse mouse = MouseFactory.createMouse(4);
        mouse.sayHi();
    }
}
