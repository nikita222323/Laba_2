package ru.mephi.statistics;

import org.apache.commons.math3.stat.correlation.Covariance;

import java.util.ArrayList;
import java.util.List;

public class CovariationCounter implements StatisticsCounter{
    private List<List<Double>> covarianceMatrix;

    @Override
    public void calculate(List<List<Double>> columns) {
        covarianceMatrix = new ArrayList<>();
        Covariance covariance = new Covariance();
        for (List<Double> innerList1 : columns) {
            List<Double> row = new ArrayList<>();
            for (List<Double> innerList2 : columns) {
                row.add(covariance.covariance(innerList1.stream().mapToDouble(Double::doubleValue).toArray(), innerList2.stream().mapToDouble(Double::doubleValue).toArray()));
            }
            covarianceMatrix.add(row);
        }
    }

    @Override
    public List<List<Double>> getResult() {
        return covarianceMatrix;
    }
}
