package com.basejava;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainStream {

    public static void main(String[] args) {
        int[] intValues = {1, 1, 3, 4, 7, 9, 4, 3, 5, 4};
        System.out.println("Минимально возможное число:\n" + minValue(intValues));

        List<Integer> list = new ArrayList<>();
        list.add(12);
        list.add(11);
        list.add(13);
        list.add(3);
        System.out.println("Четное или нечетное:\n" + oddOrEven(list));
    }

    private static int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce((acc, x) -> acc * 10 + x)
                .getAsInt();
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        int oddCounter = (int) integers.stream()
                .filter(x -> x % 2 != 0)
                .count();
        return integers.stream()
                .flatMap(x -> getApproveInt(oddCounter, x))
                .collect(Collectors.toList());
    }

    private static Stream<Integer> getApproveInt(int count, Integer num) {
        if (count % 2 == 0 && num % 2 != 0) {
            return Stream.of(num);
        } else if (count % 2 != 0 && num % 2 == 0) {
            return Stream.of(num);
        }
        return Stream.empty();
    }
}
