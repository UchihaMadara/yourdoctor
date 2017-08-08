package com.ahmed.yourdoc;

import java.util.ArrayList;

/**
 * Created by ksunil on 12/23/16.
 */

public class Constant {

    public static String [] name = {"تصنيفات","تصنيفات كتابيه"};
    public static String [] subName = {"google", "Motorola", "Samsung", "Lenevo"};
    public static String [] subName1 = {"google1", "Motorola1", "Samsung1"};
    public static ArrayList<String[]>sub=new ArrayList<>();
    public static ArrayList<String []> getSub(){
        sub.add(subName);
        sub.add(subName1);
        return sub;
    }
}
