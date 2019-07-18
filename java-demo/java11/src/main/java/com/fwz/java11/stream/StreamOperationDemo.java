package com.fwz.java11.stream;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamOperationDemo {

    private List<Person> persons =
            Arrays.asList(
                    new Person("Max", 18),
                    new Person("Peter", 23),
                    new Person("Pamela", 23),
                    new Person("David", 12));

    private void simpleStreamSamples() {
        List<String> list = Arrays.asList("a3", "a2", "a1", "b1", "b2");
        list.stream().filter((s) -> s.startsWith("a"))
                .map((String::toUpperCase))
                .sorted()
                .forEach(System.out::println);

        System.out.println("------------------");
        Arrays.asList("a1", "a2", "a3")
                .stream()
                .findFirst()
                .ifPresent(System.out::println);
        System.out.println("------------------");
        Stream.of("a1", "a2", "a3")
                .findFirst()
                .ifPresent(System.out::println);
        System.out.println("------------------");
        IntStream.range(7, 9)
                .forEach(System.out::println);
        System.out.println("------------------");
        IntStream.range(3, 7)
                .map(i -> i * 2)
                .average()
                .ifPresent(System.out::println);
        System.out.println("------------------");
        Stream.of("a1", "a2", "a3")
                .map(s -> s.substring(1))
                .map(Integer::parseInt)
                .forEach(System.out::println);
        System.out.println("------------------");

    }

    private void streamOrderSamples() {
        System.out.println("------------------");
        Stream.of("a1", "a5", "a3", "a4", "a2")
                .filter(s -> {
                    System.out.println("fileter: " + s);
                    return true;
                })
                .forEach(System.out::println);
        System.out.println("------------------");
        Stream.of("d2", "a2", "b1", "b3", "c")
                .map(s -> {
                    System.out.println("map: " + s);
                    return s.toUpperCase();
                })
                .anyMatch(s -> {
                    System.out.println("anyMatch: " + s);
                    return s.startsWith("B");
                });
        System.out.println("------------------");
        Stream.of("d2", "a2", "b1", "b3", "c")
                .map(s -> {
                    System.out.println("map: " + s);
                    return s.toUpperCase();
                })
                .filter(s -> {
                    System.out.println("filter: " + s);
                    return s.startsWith("A");
                })
                .forEach(s -> System.out.println("forEach: " + s));
        System.out.println("------------------");
        Stream.of("d2", "a2", "b1", "b3", "c")
                .filter(s -> {
                    System.out.println("filter: " + s);
                    return s.startsWith("a");
                })
                .map(s -> {
                    System.out.println("map: " + s);
                    return s.toUpperCase();
                })
                .forEach(s -> System.out.println("forEach: " + s));
        System.out.println("------------------");
        Stream.of("d2", "a2", "b1", "b3", "c")
                .sorted((s1, s2) -> {
                    System.out.printf("sort: %s; %s\n", s1, s2);
                    return s1.compareTo(s2);
                })
                .filter(s -> {
                    System.out.println("filter: " + s);
                    return s.startsWith("a");
                })
                .map(s -> {
                    System.out.println("map: " + s);
                    return s.toUpperCase();
                })
                .forEach(s -> System.out.println("forEach: " + s));
        System.out.println("------------------");
        Stream.of("d2", "a2", "b1", "b3", "c")
                .filter(s -> {
                    System.out.println("filter: " + s);
                    return s.startsWith("a");
                })
                .sorted((s1, s2) -> {
                    System.out.printf("sort: %s; %s\n", s1, s2);
                    return s1.compareTo(s2);
                })
                .map(s -> {
                    System.out.println("map: " + s);
                    return s.toUpperCase();
                })
                .forEach(s -> System.out.println("forEach: " + s));
        System.out.println("------------------");

    }

    private void streamReuseSamples() {
        System.out.println("------------------");
        Supplier<Stream<String>> streamSupplier =
                () -> Stream.of("d2", "a2", "b1", "b3", "c")
                        .filter(s -> s.startsWith("a"));

        streamSupplier.get().anyMatch(s -> true);   // ok
        streamSupplier.get().noneMatch(s -> true);  // ok
        System.out.println("------------------");

    }

    private void streamAdvanceSamples() {
        System.out.println("------------------");
        Set<Person> filtered1 = persons.stream()
                .filter(s -> s.name.startsWith("P"))
                .collect(Collectors.toSet());
        System.out.println(filtered1);
        System.out.println("------------------");
        Map<Integer, List<Person>> personsByAge = persons
                .stream()
                .collect(Collectors.groupingBy(p -> p.age));

        personsByAge
                .forEach((age, p) -> System.out.format("age %s: %s\n", age, p));
        System.out.println("------------------");
        Double averageAge = persons
                .stream()
                .collect(Collectors.averagingInt(p -> p.age));

        System.out.println(averageAge);     // 19.0
        System.out.println("------------------");
        IntSummaryStatistics ageSummary =
                persons
                        .stream()
                        .collect(Collectors.summarizingInt(p -> p.age));

        System.out.println(ageSummary);
        System.out.println("------------------");
        String phrase = persons
                .stream()
                .filter(p -> p.age >= 18)
                .map(p -> p.name)
                .collect(Collectors.joining(" and ", "In Germany ", " are of legal age."));

        System.out.println(phrase);
        Collector<Person, StringJoiner, String> personNameCollector =
                Collector.of(
                        () -> new StringJoiner(" | "),          // supplier
                        (j, p) -> j.add(p.name.toUpperCase()),  // accumulator
                        (j1, j2) -> j1.merge(j2),               // combiner
                        StringJoiner::toString);                // finisher

        String names = persons
                .stream()
                .collect(personNameCollector);

        System.out.println(names);  // MAX | PETER | PAMELA | DAVID
        System.out.println("------------------");
    }

    public static void main(String[] args) {
        StreamOperationDemo demo = new StreamOperationDemo();
        demo.simpleStreamSamples();
        demo.streamOrderSamples();
        demo.streamReuseSamples();
        demo.streamAdvanceSamples();
    }

    class Person {
        String name;
        int age;

        Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return name;
        }
    }

}
