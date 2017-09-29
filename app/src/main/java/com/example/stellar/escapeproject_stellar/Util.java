package com.example.stellar.escapeproject_stellar;

/**
 * Created by stellar on 2017/4/14.
 */

public class Util {
    protected static String itIntArray(int[] s)
    {
        String res = "";
        for (int i : s)
        {
            res += i + " ";
        }
        return res;
    }
}
