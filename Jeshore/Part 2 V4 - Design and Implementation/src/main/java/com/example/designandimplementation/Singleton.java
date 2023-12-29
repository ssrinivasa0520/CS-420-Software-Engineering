package com.example.designandimplementation;

public class Singleton { //Creates Singleton
    private static Singleton instance;
    private Singleton(){
    }
    public static Singleton getInstance(){
        if (instance == null){
            instance = new Singleton();
            System.out.println("Instance Created");
        }
        System.out.println("Instance Already Created");
        return instance;
    }
}
