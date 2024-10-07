package ru.mephi.statistics;

import org.apache.commons.math3.stat.StatUtils;
import org.apache.poi.ss.usermodel.*;
import java.util.ArrayList;
import java.util.List;
//оценка дисперсии(9)
public class VarianceEstimationCounter implements StatisticsCounter {
    private List<Double> result = new ArrayList<>();

    @Override
    public void calculate( List<List<Double>> columns) {
        result = new ArrayList<>();
        for (List<Double> column : columns) {
            double variance = StatUtils.variance(column.stream().mapToDouble(Double::doubleValue).toArray());
            result.add(variance);
        }
    }

    @Override
    public List<Double> getResult() {
        return result;
    }
}