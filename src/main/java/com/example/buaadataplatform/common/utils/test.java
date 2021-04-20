package com.example.buaadataplatform.common.utils;

import java.util.Date;

class A{
    public A(){
        System.out.println("A");
    }

//    abstract class C{
//
//    }
    abstract class C{

    }
}

class B extends A{
    public B(){
        System.out.println("B");
        A a = new A();
    }
}

public class test {
    public test(){
        System.out.println("Test");
    }

    public static void main(String[] args){
        int _value;
        B b =new B();
//        Date date= new Date();
//        boolean res = date.after(new Date());
    }

}
