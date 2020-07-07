package com.imooc.demo.pattern.template;

public class ChineseKTV extends KTVRoom {
    @Override
    protected void orderSong() {
        System.out.println("凤凰传奇");
    }

    @Override
    protected void orderExtra() {
        System.out.println("一扎啤酒");
    }
}
