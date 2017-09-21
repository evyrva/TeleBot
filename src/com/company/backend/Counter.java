package com.company.backend;

import java.util.*;

public class Counter {

    private static List<String[]> permutate(Map<String, Double> map, int limit) {
        List<String[]> list = new LinkedList<>();
        String[] strings = new String[map.size()];
        int count = 0;
        for (String s : map.keySet()) strings[count++] = s;
        permuteIteration(strings, 0, limit, list);
        return list;
    }

    private static void permuteIteration(String[] arr, int index, int limit, List<String[]> list) {
        //последняя итерация
        if (index >= limit) {
            String[] strings = new String[limit];
            for (int i = 0; i < limit; i++) strings[i] = arr[i];
            if (!containPermute(list, strings))
                list.add(strings);
            return;
        }

        for (int i = index; i < arr.length; i++) {
            String temp = arr[index];
            arr[index] = arr[i];
            arr[i] = temp;

            permuteIteration(arr, index + 1, limit, list);

            temp = arr[index];
            arr[index] = arr[i];
            arr[i] = temp;

        }
    }

    private static boolean containPermute (List<String[]> list, String[] strings){
        int count;
        for (String[] s : list){
            count = 0;
            for (String string : s){
                for (String string1 : strings){
                    if (string.equals(string1)) count++;
                }
            }
            if (count == strings.length) return true;
        }
        return false;
    }

    private static List<String> nullSumCombinations(Map<String, Double> inputMap){
        Map<String, Double> workMap = new HashMap<>();
        workMap.putAll(inputMap);
//        workMap = inputMap;
        List<String> outList = new LinkedList<>();
        while (workMap.size()>0){
            final double maxVal = Collections.max(workMap.values());
            final double minVal = Collections.min(workMap.values());

            Optional<String> nameMax = workMap.entrySet()
                    .stream()
                    .filter(entry -> maxVal == entry.getValue())
                    .map(Map.Entry::getKey)
                    .findFirst();

            Optional<String> nameMin = workMap.entrySet()
                    .stream()
                    .filter(entry -> minVal == entry.getValue())
                    .map(Map.Entry::getKey)
                    .findFirst();
            if (maxVal + minVal > 0){
                workMap.replace(nameMax.get(), maxVal + minVal);
                workMap.remove(nameMin.get());
            } else if (maxVal + minVal < 0){
                workMap.replace(nameMin.get(), maxVal + minVal);
                workMap.remove(nameMax.get());
            } else{
                workMap.remove(nameMin.get());
                workMap.remove(nameMax.get());
            }
            outList.add(nameMin.get() + " должен отдать " + nameMax.get() + " " + (maxVal + minVal >= 0 ? Math.abs(minVal) : Math.abs(maxVal)));
        }
        return outList;
    }

    protected static Map<String, Double> roundMap(Map<String, Double> inputMap, int roundConst){
        Map<String, Double> workMap = new HashMap<>();
        workMap.putAll(inputMap);
        double controlSum = 0;
        String randName = "";
        for (Map.Entry<String, Double> entry : workMap.entrySet()){
            entry.setValue(Math.round(entry.getValue() / roundConst) * roundConst * 1.0);
            controlSum += entry.getValue();
            randName = entry.getKey();
        }
        workMap.replace(randName,workMap.get(randName) - controlSum);
        return workMap;
    }

    private static List<Map<String, Double>> combination(Map<String, Double> inputMap,int limit){
        List<Map<String, Double>> outlist = new LinkedList<>();
        Map<String, Double> map;
        for (String[] strings : permutate(inputMap, limit)){
            double sum = 0;
            for (String s : strings){
                sum += inputMap.get(s);
            }
            if (sum == 0) {
                map = new HashMap<>();
                for (String s : strings){
                    map.put(s,inputMap.get(s));
                    inputMap.remove(s);
                }
                outlist.add(map);
                outlist.addAll(combination(inputMap, limit));
                break;
            }
        }
        return outlist;
    }

    public static List<String> calculateMap(Map<String, Double> inputMap, int roundConst){
        Map<String, Double> workMap = new HashMap<>();
        workMap.putAll(inputMap);
        List<String> outputList = new LinkedList<>();
        List<Map<String, Double>> maps;
        Set<String> nullNames = new HashSet<>();
        for (Map.Entry<String, Double> entry : workMap.entrySet()){
            if (entry.getValue() < 1 && entry.getValue() > -1){
                nullNames.add(entry.getKey());
            }
        }
        for (String name : nullNames){
            workMap.remove(name);
        }
        workMap = roundMap(workMap, roundConst);
        int count = 1;
        while (workMap.size()>0){
            maps = combination(workMap, count+1);
            for (Map<String, Double> map : maps){
                outputList.addAll(nullSumCombinations(map));
            }
            count++;
        }
        return outputList;
    }
}
