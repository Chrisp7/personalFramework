package com.imooc.demo.pattern.template;

public abstract class KTVRoom {
    public void procedure() {
        openDevice();
        orderSong();
        orderExtra();
        pay();
    }

    public void openDevice() {
        System.out.println("打开设备");
    }

    protected abstract void orderSong();

    protected void orderExtra() {
    }


    public void pay() {
        System.out.println("唱完歌付钱");
    }

}
