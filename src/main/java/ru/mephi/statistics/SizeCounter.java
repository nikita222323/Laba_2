package ru.mephi.statistics;

import java.util.ArrayList;
import java.util.List;
//кол-во элементов выборки(6)
public class SizeCounter implements StatisticsCounter {
    private List<Integer> result = new ArrayList<>();

    @Override
    public void calculate(List<List<Double>> columns) {
        result = new ArrayList<>();
        for (List<Double> column : columns) {
            Integer quantity = column.size();
            result.add(quantity);
        }
    }

    @Override
    public List<Integer> getResult() {
        return result;
    }
}
