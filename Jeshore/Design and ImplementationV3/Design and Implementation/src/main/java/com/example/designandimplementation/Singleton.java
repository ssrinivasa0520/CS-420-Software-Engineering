package com.example.designandimplementation;

public class Singleton {
    private static Singleton instance;

    public String s;
    private Singleton(){
        s = "test";
        System.out.println("test");
    }
    public static Singleton getInstance(){
        if (instance == null){
            instance = new Singleton();
            System.out.println("out2");
        }
        System.out.println("out");
        return instance;
    }
}
