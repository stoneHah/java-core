package com.zq.learn.java.java8.lambda;

import com.zq.learn.java.java8.Employee;
import com.zq.learn.java.java8.Filter;
import org.junit.Test;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class LambdaTest {

    @Test
    public void testComparator(){
        List<Employee> employeeList = getEmployees();
        Comparator<Employee> ageComparator = Comparator.comparing(Employee::getAge);

        //sort by age asc
        System.out.println("=========sort by age asc==========");
        employeeList.sort(ageComparator);
        employeeList.forEach((e) -> System.out.println(e.toString()));

        //sort by age desc
        System.out.println("=========sort by age desc==========");
        employeeList.sort(ageComparator.reversed());
//        employeeList.forEach((e) -> System.out.println(e.toString()));
        employeeList.forEach(System.out::println);
    }

    @Test
    public void testFilter(){
        List<Employee> employeeList = getEmployees();
         /* List<Employee> filterList = filter(employeeList, (e) -> e.getAge() > 30);

        filterList.forEach(System.out::println);*/
        Predicate<Employee> ageFilter = (e) -> e.getAge() > 30;
        Predicate<Employee> salaryFilter = (e) -> e.getSalary() > 6500;

         employeeList.stream().filter(ageFilter)
                 .filter(salaryFilter)
                 .forEach((e) -> System.out.println(e));

    }

    @Test
    public void testSort(){
        List<Employee> employeeList = getEmployees();

        Predicate<Employee> ageFilter = (e) -> {
            System.out.println("getAge call...");
            return e.getAge() > 30;
        };

        employeeList.stream()
                .filter(ageFilter)
                .sorted(Comparator.comparingInt(Employee::getAge))
                .collect(toList())
                .forEach((e) -> System.out.println(e.toString()));
    }

    @Test
    public void testMinMax(){
        List<Employee> employeeList = getEmployees();

        Predicate<Employee> ageFilter = (e) -> e.getAge() > 30;

        Employee employee = employeeList.stream()
                .min(Comparator.comparingDouble(Employee::getSalary))
                .get();
        System.out.println("min salary employee:" + employee.toString());
    }

    @Test
    public void testCollect(){
        List<Employee> employeeList = getEmployees();

        String employNames = employeeList.stream().map(Employee::getName)
                .collect(Collectors.joining(","));
        System.out.println(employNames);
    }

    @Test
    public void testSum(){
        List<Employee> employeeList = getEmployees();

        double sum = employeeList.parallelStream()
                .mapToDouble(Employee::getSalary)
                .sum();
        System.out.println(sum);
    }

    @Test
    public void testSummaryStatistic(){
        List<Employee> employeeList = getEmployees();

        DoubleSummaryStatistics summaryStatistics = employeeList.stream()
                .mapToDouble(Employee::getSalary)
                .summaryStatistics();

        System.out.println("max salary:" + summaryStatistics.getMax());
        System.out.println("min salary:" + summaryStatistics.getMin());
        System.out.println("avg salary:" + summaryStatistics.getAverage());
        System.out.println("sum salary:" + summaryStatistics.getSum());

        System.out.println("===========parallel=========");

        summaryStatistics = employeeList.parallelStream()
                .mapToDouble(Employee::getSalary)
                .summaryStatistics();

        System.out.println("max salary:" + summaryStatistics.getMax());
        System.out.println("min salary:" + summaryStatistics.getMin());
        System.out.println("avg salary:" + summaryStatistics.getAverage());
        System.out.println("sum salary:" + summaryStatistics.getSum());
    }

    @Test
    public void testFindAnyOrElse(){
        List<Employee> employeeList = getEmployees();

        Optional<Employee> employeeOptional = employeeList.stream()
                .filter((e) -> e.getAge() > 40)
                .findAny();

        System.out.println(employeeOptional.get());
    }

    @Test
    public void testMap(){
        List<Employee> employeeList = getEmployees();

        String collect = employeeList.stream()
                .map((e) -> {
                    System.out.println("getName call...");
                    return e.getName();
                })
                .map((name) -> {
                    System.out.println("toUpperCase call...");
                    return name.toUpperCase();
                })
                .collect(Collectors.joining(","));
        System.out.println(collect);
    }

    @Test
    public void testFlatMap(){
        List<Employee> employeeList = getEmployees();

        List<List<Integer>> outer = new ArrayList<>();
        List<Integer> inner1 = new ArrayList<>();
        inner1.add(1);
        List<Integer> inner2 = new ArrayList<>();
        inner1.add(2);
        List<Integer> inner3 = new ArrayList<>();
        inner1.add(3);
        List<Integer> inner4 = new ArrayList<>();
        inner1.add(4);
        List<Integer> inner5 = new ArrayList<>();
        inner1.add(5);
        outer.add(inner1);
        outer.add(inner2);
        outer.add(inner3);
        outer.add(inner4);
        outer.add(inner5);
        List<Integer> result = outer.stream().flatMap(inner -> inner.stream().map(i -> i + 1)).collect(toList());
        System.out.println(result);
    }

    @Test
    public void testGroupBy(){
        List<Employee> employeeList = getEmployees();

        Map<Integer, Double> collect = employeeList.stream().collect(Collectors.groupingBy(
            Employee::getAge,Collectors.summingDouble(Employee::getSalary)
        ));
        collect.forEach((k,v) -> System.out.println(k.toString() + "=" + v));
    }

    @Test
    public void testPrimitiveArrays(){
        int[] intArray = {1, 2, 3, 4, 5};

        // 1. Arrays.stream -> IntStream
        IntStream intStream1 = Arrays.stream(intArray);
        intStream1.forEach(x -> System.out.println(x));

        // 2. Stream.of -> Stream<int[]>
        Stream<int[]> temp = Stream.of(intArray);

        // Cant print Stream<int[]> directly, convert / flat it to IntStream
        IntStream intStream2 = temp.flatMapToInt(x -> Arrays.stream(x));
        intStream2.forEach(x -> System.out.println(x));
    }

    @Test
    public void testSort1(){
        List<String> items =
                Arrays.asList("apple", "apple", "banana",
                        "apple", "orange", "banana", "papaya");

        Map<String, Long> result =
                items.stream().collect(
                        Collectors.groupingBy(
                                Function.identity(), Collectors.counting()
                        )
                );

        Map<String, Long> finalMap = new LinkedHashMap<>();

        //Sort a map and add to finalMap
        result.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue()
                        .reversed()).forEach(e -> finalMap.put(e.getKey(), e.getValue()));

        System.out.println(finalMap);
    }

    @Test
    public void testSortMap(){
        Map<String,Integer> map = new HashMap<>();
        map.put("hello",123);
        map.put("world",123);
        map.put("abc",123);
        map.put("nimei",123);

        System.out.println("============before sort===========");
        System.out.println(map);

        map.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }

    List<Employee> getEmployees(){
        List<Employee> list = new ArrayList<>();

        list.add(new Employee(1, "zq", 30,5000.0));
        list.add(new Employee(2, "bwp", 30,6000.0));
        list.add(new Employee(3, "sxx", 34,6500.0));
        list.add(new Employee(4, "cy", 31,6800.0));

        return list;
    }

    <T> List<T> filter(List<T> list,Filter<T> filter){
        List<T> result = new ArrayList<>();
        for (T t : list) {
            if (filter.match(t)) {
                result.add(t);
            }
        }

        return result;
    }


}
