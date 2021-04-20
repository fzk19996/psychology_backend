package com.example.buaadataplatform.common;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;

public class test {

    public int max(int a, int b){
        return a>b?a:b;
    }

    public void process(int a, int b, int c){
        try{
            int m1 = max(max(a+b,b),c);
            int m2 = max(max(a,b+c),c)+max(max(a,b),b+c);
            float m = (float)m1/m2;
            DecimalFormat fnum   =   new   DecimalFormat("##0.00");
            String dd=fnum.format(m);
            System.out.println(dd);
        }catch (Exception e){
            System.out.println("ERROR");
        }
    }

    public int process2(int n){
        HashMap<String, Integer> map = new HashMap<>();
        map.put("3",1);
        map.put("2",0);
        map.put("1",0);
        while(--n>0){
            int num3 = map.get("3");//num3为生的小牛数量
            int num2 = map.get("2");
            int num1 = map.get("1");
            map.put("1",num3);
            map.put("3", num2+num3);
            map.put("2",num1);
        }
        return map.get("1")+map.get("2")+map.get("3");
    }

    public static void main(String[] args){
        int a = 1;
        int b = 2;
        int c = 3;
        int res = new test().process2(6);
        System.out.println(res+"");
    }

}
