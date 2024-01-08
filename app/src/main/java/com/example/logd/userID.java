package com.example.logd;

public class userID {

    private static int userId;
    private static int schoolId;


    public static  void setDataUserId(int id){
        userID.userId = id;
    }


    public static  void setDataSchoolId(int id){
        userID.schoolId = id;
    }
    public int getDataUserId(){
        return userId;
    }

    public int getDataSchoolId(){
        return schoolId;
    }
}
