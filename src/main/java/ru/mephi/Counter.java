package ru.mephi;

import ru.mephi.statistics.*;
import ru.mephi.excel.SheetReader;
import ru.mephi.excel.SheetWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Counter {
    private Map<String, StatisticsCounter> statObjects = new HashMap<>();
    private Map<String, List<?>> allResults = new HashMap<>();
    private List<List<Double>> columns;
    private  List<String>labels;

    public Counter() {
        statObjects.put("Стандартное отклонение", new StandardDeviationCounter());
        statObjects.put("Доверительный интервал для мат ожидания", new ConfidenceInterval());
        statObjects.put("Количество элементов", new SizeCounter());
        statObjects.put("Среднее значение", new ArithmeticMeanCounter());
        statObjects.put("Максимальное значение", new MaxCounter());
        statObjects.put("Минимальное значение", new MinCounter());
        statObjects.put("Среднее геометрическое", new AverageGeometricalValueCounter());
        statObjects.put("Коэффициент вариации", new CVCounter());
        statObjects.put("Дисперсия", new VarianceEstimationCounter());
        statObjects.put("Размах", new RangeCounter());
        statObjects.put("Ковариация", new CovariationCounter());
    }

    public void read(String file, String name) throws IOException {
        SheetReader excelReader = new SheetReader();
        Map<String, List<Double>> data  = excelReader.readFromExcel(file, name);
        labels = new ArrayList<>(data.keySet());
        columns = new ArrayList<>(data.values());

    }


    public void calculateAll() {
        for (Map.Entry<String, StatisticsCounter> entry : statObjects.entrySet()) {
            String name = entry.getKey();
            StatisticsCounter calc = entry.getValue();
            try {
                calc.calculate(columns);
                allResults.put(name, calc.getResult());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    public Map<String, List<?>> getAllResults() {
        return allResults;
    }

    public void write() throws IOException{
        SheetWriter.write(allResults, labels, "OutputStatistics");



    }}
