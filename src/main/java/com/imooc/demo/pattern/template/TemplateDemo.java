package com.imooc.demo.pattern.template;

public class TemplateDemo {
    public static void main(String[] args) {
        KTVRoom room1 = new ChineseKTV();
        KTVRoom room2 = new AmericanKTV();

        room1.procedure();
        room2.procedure();
    }
}
