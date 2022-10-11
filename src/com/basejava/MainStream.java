package com.basejava;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainStream {

    public static void main(String[] args) {
        int[] intValues = {1, 1, 3, 4, 7, 9, 4, 3, 5, 4};
        System.out.println("Минимально возможное число:\n" + minValue(intValues));

        List<Integer> list = new ArrayList<>();
        list.add(8);
        list.add(11);
        list.add(18);
        list.add(3);
        System.out.println("Четное или нечетное:\n" + oddOrEven(list));
    }

    private static int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce(0, (acc, x) -> acc * 10 + x);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        int oddCounter = (int) integers.stream()
                .filter(x -> x % 2 != 0)
                .count();
        return integers.stream()
                .filter(x -> isApproveInt(oddCounter, x))
                .collect(Collectors.toList());
    }

    private static boolean isApproveInt(int count, Integer num) {
        if (count % 2 == 0 && num % 2 != 0) {
            return true;
        } else return count % 2 != 0 && num % 2 == 0;
    }
}
