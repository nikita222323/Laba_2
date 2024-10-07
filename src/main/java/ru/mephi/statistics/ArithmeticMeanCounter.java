package ru.mephi.statistics;

import org.apache.commons.math3.stat.StatUtils;

import java.util.ArrayList;
import java.util.List;
import java.io.File;

//среднее арифметическое(2)
public class ArithmeticMeanCounter implements StatisticsCounter {
    private List<Double> result = new ArrayList<>();

    @Override
    public void calculate(List<List<Double>> columns) {
        result = new ArrayList<>();
        for (List<Double> column : columns) {
            double mean = StatUtils.mean(column.stream().mapToDouble(Double::doubleValue).toArray());
            result.add(mean);
        }
    }

    @Override
    public List<Double> getResult() {
        return result;
    }
}
