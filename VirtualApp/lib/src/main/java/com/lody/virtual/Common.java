package com.lody.virtual;

/**
 * Created by Administrator on 2016/8/3.
 */
public class Common {

    public static void printCallStatck() {
        Throwable ex = new Throwable();
        StackTraceElement[] stackElements = ex.getStackTrace();
        if (stackElements != null) {
            for (int i = 0; i < stackElements.length; i++) {
                System.out.print(stackElements[i].getClassName() + "->");
                System.out.println(stackElements[i].getMethodName());

            }
            System.out.println("-----------------------------------");
        }
    }
}
