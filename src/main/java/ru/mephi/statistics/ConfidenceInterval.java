package ru.mephi.statistics;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.stat.StatUtils;

import java.util.ArrayList;
import java.util.List;
//доверительный интервал(8)
public class ConfidenceInterval implements StatisticsCounter {
    private List<String> result = new ArrayList<>();

    @Override
    public void calculate(List<List<Double>> columns) {
        result = new ArrayList<>();
        List<Double> mean = new ArrayList<>();
        for (List<Double> column : columns) {
            double mn = StatUtils.mean(column.stream().mapToDouble(Double::doubleValue).toArray());
            mean.add(mn);
        }
        List<Double> sd = new ArrayList<>();
        for (List<Double> column : columns) {
            double ssdd = StatUtils.variance((column.stream().mapToDouble(Double::doubleValue).toArray()));
            sd.add(Math.sqrt(ssdd));
        }
        List<Integer> size = new ArrayList<>();
        for (List<Double> column : columns) {
            Integer quantity = column.size();
            size.add(quantity);
        }
        double confidenceLevel = 0.95;
        for (int ind = 0; ind < columns.size(); ind++) {
            List<Double> column = columns.get(ind);
            NormalDistribution normalDistribution = new NormalDistribution();
            double quant = normalDistribution.inverseCumulativeProbability(1 - (1 - confidenceLevel) / 2);
            double marginOfError = quant * (sd.get(ind) / size.get(ind));
            double lowerBound = mean.get(ind) - marginOfError;
            double upperBound = mean.get(ind) + marginOfError;
            // Формируем строку интервала и добавляем ее в список
            String interval = "[" + lowerBound + "; " + upperBound + "]";
            result.add(interval);
        }
    }

    @Override
    public List<String> getResult() {
        return result;
    }
}
