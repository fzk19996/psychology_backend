package com.example.buaadataplatform.common.utils;

import com.example.buaadataplatform.module.constant.ErrorCode;

import java.util.*;

public class CommonUitls {

//    public static int calc_score(String expression, List<Integer> scoreList) {
//        Stack<Integer> number_stack = new Stack<>();
//        Stack<String> keyword_stack = new Stack<>();
//        Stack<Character> character_stack = new Stack<>();
//        String number = "";
//        String keyword = "";
//        for (int i = 0; i < expression.length(); i++) {
//            if (expression.charAt(i) >= '0' && expression.charAt(i) <= '9') {
//                number += expression.charAt(i);
//                continue;
//            } else if (expression.charAt(i) >= 'a' && expression.charAt(i) <= 'z') {
//                keyword += expression.charAt(i);
//                continue;
//            } else {
//                if (number != "") {
//                    number_stack.push(scoreList.get(Integer.parseInt(number)-1));
//                    number = "";
//                }
//                if (keyword != "") {
//                    keyword_stack.push(keyword);
//                    keyword = "";
//                }
//                if (expression.charAt(i) == '(' || expression.charAt(i) == '+' || expression.charAt(i) == '-' || expression.charAt(i) == ',') {
//                    character_stack.push(expression.charAt(i));
//                } else if (expression.charAt(i) == ')') {
//                    while (!character_stack.empty()) {
//                        char operator = character_stack.pop();
//                        int num1 = number_stack.pop();
//                        int num2 = number_stack.pop();
//                        int res = 0;
//                        if (operator == '+') {
//                            res = num2 + num1;
//                        } else if (operator == '-') {
//                            res = num2 - num1;
//                        } else if (operator == ',') {
//                            String keyword_op = keyword_stack.peek();
//                            if (keyword_op.equals("max")) {
//                                res = num2 > num1 ? num2 : num1;
//                            } else if (keyword_op.equals("min")) {
//                                res = num2 < num1 ? num2 : num1;
//                            } else {
//                                return ErrorCode.CALC_EXCEPTION;
//                            }
//                        } else {
//                            return ErrorCode.CALC_EXCEPTION;
//                        }
//                        number_stack.push(res);
//                        if (!character_stack.empty() && character_stack.peek() == '(') {
//                            if (!keyword_stack.empty()) {
//                                keyword_stack.pop();
//                            }
//                            character_stack.pop();
//                            break;
//                        }
//                    }
//                }
//            }
//        }
//        if (number != "") {
//            number_stack.push(scoreList.get(Integer.parseInt(number)-1));
//        }
//        while (!character_stack.empty()) {
//            int num1 = number_stack.pop();
//            int num2 = number_stack.pop();
//            char operator = character_stack.pop();
//            int res = 0;
//            if (operator == '+') {
//                res = num1 + num2;
//            } else if (operator == '-') {
//                res =  num2 - num1;
//            } else if (operator == ',') {
//                String keyword_op = keyword_stack.peek();
//                if (keyword_op.equals("max")) {
//                    res = num2 > num1 ? num2 : num1;
//                } else if (keyword_op.equals("min")) {
//                    res = num2 < num1 ? num2 : num1;
//                } else {
//                    return ErrorCode.CALC_EXCEPTION;
//                }
//            } else {
//                return ErrorCode.CALC_EXCEPTION;
//            }
//            number_stack.push(res);
//        }
//        return number_stack.pop();
//    }

    public static boolean isInt(String input){
        try{
            int tmp = Integer.parseInt(input);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public static boolean isFloat(String input){
        try{
            float tmp = Float.parseFloat(input);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public static float calc_score(String expression, Map<String, Integer> scoreMap) {
        Stack<Float> number_stack = new Stack<>();
        Stack<String> keyword_stack = new Stack<>();
        Stack<Character> character_stack = new Stack<>();
        String number = "";
        String keyword = "";
        int state = 0;
        for (int i = 0; i < expression.length(); i++) {
            if(expression.charAt(i)==' '){
                continue;
            } else if (expression.charAt(i) >= '0' && expression.charAt(i) <= '9') {
                number += expression.charAt(i);
                keyword += expression.charAt(i);
                continue;
            } else if ((expression.charAt(i) >= 'a' && expression.charAt(i) <= 'z')
                    ||(expression.charAt(i) >= 'A' && expression.charAt(i) <= 'Z')
                    ||expression.charAt(i)=='_') {
                keyword += expression.charAt(i);
                continue;
            } else if ((expression.charAt(i)=='.')) {
                number += expression.charAt(i);
                keyword += expression.charAt(i);
            }else {
                if (number != "" && isFloat(keyword)) {
                    number_stack.push(Float.parseFloat(number));

                    number = "";
                    keyword = "";
                }
                if (keyword != "") {
                    if(keyword.equals("max")||keyword.equals("min")||keyword.equals("MAX")||keyword.equals("MIN")) {
                        keyword_stack.push(keyword);
                    }else{
                        if(scoreMap.containsKey(keyword)){
                            number_stack.push((float)scoreMap.get(keyword).intValue());
                        }else{
                            System.out.println("出现未识别的题号"+keyword);
                            number_stack.push(0f);
//                            return ErrorCode.CALC_EXCEPTION;
                        }
                    }
                    keyword = "";
                    number = "";
                }
                if (expression.charAt(i) == '(' || expression.charAt(i) == '+' || expression.charAt(i) == '-' || expression.charAt(i) == ','
                        ||expression.charAt(i)=='/' || expression.charAt(i)=='*') {
                    character_stack.push(expression.charAt(i));
                } else if (expression.charAt(i) == ')') {
                    Stack<Float> num_list = new Stack<>();
                    Stack<Character> operator_list = new Stack<>();
                    while (!character_stack.empty()) {
                        char operator = character_stack.pop();
                        if(operator=='(')
                            break;
                        if(num_list.size()==0){
                            num_list.push(number_stack.pop());
                            num_list.push(number_stack.pop());
                        }else{
                            num_list.push(number_stack.pop());
                        }
                        operator_list.push(operator);
                    }
                    while(!operator_list.empty()){
                        float num1 = num_list.pop();
                        float num2 = num_list.pop();
                        Character op = operator_list.pop();
                        if(op=='+'){
                            num_list.push(num1+num2);
                        }else if(op=='-'){
                            num_list.push(num1-num2);
                        }else if(op=='/'){
                            num_list.push(num1/num2);
                        }else if(op=='*'){
                            num_list.push(num1*num2);
                        }else if(op==','){
                            if(keyword_stack.empty())
                                return ErrorCode.CALC_EXCEPTION;
                            String func_name = keyword_stack.pop();
                            if(func_name.equals("max")||func_name.equals("MAX")){
                                num_list.push(num1>=num2?num1:num2);
                            }else if(func_name.equals("min")||func_name.equals("MIN")){
                                num_list.push(num1<num2?num1:num2);
                            }else{
                                return ErrorCode.CALC_EXCEPTION;
                            }
                        }
                    }
                    if(num_list.size()!=1) {
                        return ErrorCode.CALC_EXCEPTION;
                    }
                    number_stack.push(num_list.pop());
                }
            }
        }
        return number_stack.pop();
    }

    public static void main(String[] args){
//        List<Integer> scoreList = new ArrayList<>();
//        for(int i=0;i<20;i++)
//            scoreList.add(i+1);
//        String expression = "max(1,2)+1";
        Map<String, Integer> scoreMap = new HashMap<>();
        scoreMap.put("Q1A", 1);
        scoreMap.put("Q2",2);
        scoreMap.put("Q34",3);
        scoreMap.put("Q_4",4);
        scoreMap.put("Q5",5);
        String expression = "((Q1A+MAX(Q2,Q34)-MIN(Q_4,Q5)+6)*Q1A)";
        float res = CommonUitls.calc_score(expression, scoreMap);
        System.out.println(res);
    }


}
