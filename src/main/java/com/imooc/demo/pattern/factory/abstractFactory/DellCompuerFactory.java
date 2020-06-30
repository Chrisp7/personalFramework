package com.imooc.demo.pattern.factory.abstractFactory;

import com.imooc.demo.pattern.factory.entity.DellKeyboard;
import com.imooc.demo.pattern.factory.entity.DellMouse;
import com.imooc.demo.pattern.factory.entity.Keyboard;
import com.imooc.demo.pattern.factory.entity.Mouse;

public class DellCompuerFactory implements ComputerFactory {
    @Override
    public Mouse createMouse() {
        return new DellMouse();
    }

    @Override
    public Keyboard createKeyboard() {
        return new DellKeyboard();
    }
}
