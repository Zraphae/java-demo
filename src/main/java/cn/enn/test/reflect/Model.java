package cn.enn.test.reflect;

import java.util.Arrays;

public class Model {

    private static final String[] locations = {"aa", "bb"};

    public static void print(){
        System.out.print(Arrays.toString(locations));
    }
}
