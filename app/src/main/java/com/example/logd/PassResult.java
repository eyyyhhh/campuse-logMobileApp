package com.example.logd;

public class PassResult {
    private static int data;
    private static String temp,health,q1,q2,q3,q4,q5;

    public static void setData(int data) {
        PassResult.data = data;
    }

    public int getData() {
        return data;
    }
    public static void setHealth(String health) {
        PassResult.health = health;
    }
    public String getHealth() {
        return health;
    }
    public static void setTemp(String temp) {
        PassResult.temp = temp;
    }
    public String getTemp() {
        return temp;
    }
   //questionnaires

    public static void setQ1(String q1) {
        PassResult.q1 = q1;
    }

    public String getQ1() {
        return q1;
    }
    public static void setQ2(String q2) {
        PassResult.q2 = q2;
    }

    public String getQ2() {
        return q2;
    }
    public static void setQ3(String q3) {
        PassResult.q3 = q3;
    }

    public String getQ3() {
        return q3;
    }
    public static void setQ4(String q4) {
        PassResult.q4 = q4;
    }

    public String getQ4() {
        return q4;
    }
    public static void setQ5(String q5) {
        PassResult.q5 = q5;
    }

    public String getQ5() {
        return q5;
    }

}
