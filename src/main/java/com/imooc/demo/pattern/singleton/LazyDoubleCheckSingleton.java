package com.imooc.demo.pattern.singleton;

public class LazyDoubleCheckSingleton {
    // volatile
    private volatile static LazyDoubleCheckSingleton instance;
    private LazyDoubleCheckSingleton(){}
    public static LazyDoubleCheckSingleton getInstance(){
        if(instance==null){
            // lazyDoubleCheckSignleton这个class对象只会有一个
            synchronized (LazyDoubleCheckSingleton.class){
                // 两次检查instance==null 是为了确保线程安全
                if(instance==null){
                    // 分三步完成，分配对象内存空间（memory=allocate();) 初始化对象（instance(memory);） 设置instance指向刚分配的内存地址（instance=memory）
                    // 如果不用volatile修饰，那么就有可能第二步和第三步会颠倒过来执行，也就是说第三步先执行了。这样instance也就不为空了。此时如果有另一个线程来访问这个getInstance方法，那么直接就会跳过第一个if语句，然后直接返回instance
                    // 这样就会造成另一个线程直接去使用了一个还没有初始化完成的对象的情况，所以我们引入volatile来保证第二步一定在第三步之前，保证了顺序。
                    instance = new LazyDoubleCheckSingleton();
                }
            }
        }
        return instance;
    }

}
