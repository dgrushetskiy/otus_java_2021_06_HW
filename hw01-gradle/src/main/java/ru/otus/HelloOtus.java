package ru.otus;

import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;
import java.util.List;

public class HelloOtus {
    public static void main(String[] args) {
        List<Integer> sourceList = Lists.newArrayList(0, 1, 25, 3, 41, 5, 6, 12, 43, 7, 9, 22, 15, 113,11, 17, 33);
        int[] targetArray = Ints.toArray(sourceList);
        System.out.println("max value: " + getMaxValue(targetArray));
    }

    public static int getMaxValue(int[] values) {
        int maxValue = Integer.MIN_VALUE;
        for (int value : values) {
            if (value > maxValue) {
                maxValue = value;
            }
        }
        return maxValue;
    }
}
