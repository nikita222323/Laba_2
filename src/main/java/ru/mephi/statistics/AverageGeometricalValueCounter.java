package ru.mephi.statistics;

import org.apache.commons.math3.stat.StatUtils;

import java.util.ArrayList;
import java.util.List;
//среднее геометрическое(1)
public class AverageGeometricalValueCounter implements StatisticsCounter {
    private List<Double> result = new ArrayList<>();

    @Override
    public void calculate(List<List<Double>> columns) {
        result = new ArrayList<>();
        for (List<Double> column : columns) {
            double geometricMean = StatUtils.geometricMean(column.stream().mapToDouble(Double::doubleValue).toArray());
            result.add(geometricMean);
        }
    }

    @Override
    public List<Double> getResult() {
        return result;
    }
}
