package com.example.my43_mpchart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class PieChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);

        PieChart pieChart = findViewById(R.id.piechart);

        ArrayList NoOfEmp = new ArrayList();
        NoOfEmp.add(new PieEntry(945f, 0));
        NoOfEmp.add(new PieEntry(1040f, 1));
        NoOfEmp.add(new PieEntry(1133f, 2));
        NoOfEmp.add(new PieEntry(1240f, 3));
        NoOfEmp.add(new PieEntry(1369f, 4));
        NoOfEmp.add(new PieEntry(1487f, 5));
        NoOfEmp.add(new PieEntry(1501f, 6));
        NoOfEmp.add(new PieEntry(1645f, 7));
        NoOfEmp.add(new PieEntry(1578f, 8));
        NoOfEmp.add(new PieEntry(1695f, 9));

        PieDataSet dataSet = new PieDataSet(NoOfEmp, "Number Of Employees");

        ArrayList year = new ArrayList();
        year.add("2008");
        year.add("2009");
        year.add("2010");
        year.add("2011");
        year.add("2012");
        year.add("2013");
        year.add("2014");
        year.add("2015");
        year.add("2016");
        year.add("2017");

        PieData data = new PieData(dataSet); // MPAndroidChart v3.X 오류 발생   new PieData(year, dataSet);
        pieChart.setData(data);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieChart.animateXY(3000, 3000);

    }
}
