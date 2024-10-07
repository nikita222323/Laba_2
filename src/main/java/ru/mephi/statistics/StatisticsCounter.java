package ru.mephi.statistics;
import java.util.List;

public interface StatisticsCounter {

    void calculate(List<List<Double>> columns);
    List<?> getResult();
    }


