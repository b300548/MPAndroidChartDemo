package com.example.liwj.mpandroidchartdemo.Activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.liwj.mpandroidchartdemo.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;

public class PieChartActivity extends AppCompatActivity {

    private PieChart mPieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);


        List<Float> values = new ArrayList<>();
        for (int i=0;i<5;i++){
            values.add((float)(Math.random()*100));
        }
        initChart();
        showCombinedChart(values);
    }

    private void initChart(){
        mPieChart = (PieChart) findViewById(R.id.piechart);

        // 不显示描述内容
        mPieChart.getDescription().setEnabled(false);


        mPieChart.setBackgroundColor(Color.WHITE);


        // 图例说明
        Legend legend = mPieChart.getLegend();
        legend.setWordWrapEnabled(true);

        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        legend.setEnabled(false);

        mPieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Toast.makeText(PieChartActivity.this,e.getY()+"",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });


    }

    /**
     * 设置X轴坐标值
     * @param xAxisValues  x轴坐标集合
     */
    private void setXAxis(final List<String> xAxisValues){

        // 设置X轴在底部
        XAxis xAxis = mPieChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);

        xAxis.setLabelCount(xAxisValues.size()-1,false);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return xAxisValues.get((int)value%xAxisValues.size());
            }
        });
        mPieChart.invalidate();
    }


    /**
     * 得到折线图
     *
     * @return
     */
    private PieData getPieData(List<Float> pieChartData){
        PieData pieData = new PieData();

        ArrayList<PieEntry> yValue = new ArrayList<>();
        for (int i=0;i<pieChartData.size();i++){
            yValue.add(new PieEntry(i+1,pieChartData.get(i)+"号"));
        }
        PieDataSet dataSet = new PieDataSet(yValue,"Lable");

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.RED);
        colors.add(Color.BLUE);
        colors.add(Color.GREEN);

        dataSet.setColors(colors);


        // 显示值
        dataSet.setDrawValues(true);
        dataSet.setValueTextSize(10f);

        pieData.addDataSet(dataSet);
        pieData.setDrawValues(true);
        return pieData;
    }




    /**
     *  显示混合土（柱状图+折线图）
     *
     */
    public void showCombinedChart(List<Float> pieChartValues){
        initChart();

        PieData pieData = getPieData(pieChartValues);



        mPieChart.setData(pieData);
        mPieChart.invalidate();
    }
}
