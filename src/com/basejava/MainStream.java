package com.basejava;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static java.lang.Math.pow;

public class MainStream {

    public static void main(String[] args) {
        int[] intValues = {1, 1, 3, 4, 7, 9, 4, 3, 5, 4};
        System.out.println("Минимально возможное число:\n" + minValue(intValues));

        List<Integer> list = new ArrayList<>();
        list.add(12);
        list.add(11);
        list.add(3);
        System.out.println("Четное или нечетное:\n" + oddOrEven(list));
    }

    private static int minValue(int[] values) {
        AtomicInteger power = new AtomicInteger();
        Optional<Integer> result = Arrays.stream(values)
                .distinct()
                .boxed()
                .sorted(Comparator.reverseOrder())
                .flatMap(x -> {
                    Stream<Integer> stream = Stream.of(x * (int) (pow(10, power.get())));
                    power.getAndIncrement();
                    return stream;
                })
                .reduce((acc, x) -> acc + x);
        return result.get();
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        integers.stream()
                //.collect()
                .forEach(System.out::println);
        return null;
    }
}
