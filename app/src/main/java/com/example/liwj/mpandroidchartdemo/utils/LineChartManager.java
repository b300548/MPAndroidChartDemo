package com.example.liwj.mpandroidchartdemo.utils;

import android.graphics.Color;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class LineChartManager {
    /**
     * 折线图表
     */
    private LineChart mLineChart;

    /**
     * X轴，左侧Y轴，右侧Y轴
     */
    private XAxis mXAxis;
    private YAxis mLeftAxis;
    private YAxis mRightAxis;

    /**
     * 构造方法，初始化属性
     * @param lineChart  需要显示的折线图
     */
    public LineChartManager(LineChart lineChart){
        mLineChart = lineChart;
        mXAxis = mLineChart.getXAxis();
        mLeftAxis = mLineChart.getAxisLeft();
        mRightAxis = mLineChart.getAxisRight();

    }

    /**
     * 初始化图表
     */
    private void initChart(){
        // 设置背景颜色
        mLineChart.setBackgroundColor(Color.WHITE);

        // 是否显示背景网格
        mLineChart.setDrawGridBackground(false);

        // 显示边界
        mLineChart.setDrawBorders(true);

        // 是否可以拉伸（缩放）
        mLineChart.setScaleEnabled(false);

        // 是否可以拖拽
        mLineChart.setDragEnabled(false);


        /**
         * 描述
         */
        Description description = mLineChart.getDescription();
        // 设置描述信息
        description.setText("我是Description");

        /**
         * 图例
         */
        Legend legend = mLineChart.getLegend();
        legend.setWordWrapEnabled(true);

        // 设置图例位置（左下角）
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);

        // 设置图例方向
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);

        // 图例是否在图表内部
        legend.setDrawInside(false);

        // 是否显示图例
        legend.setEnabled(false);


        /**
         * 坐标轴
         */
        // 不显示网格线
        mXAxis.setDrawGridLines(false);
        mRightAxis.setDrawGridLines(false);
        mLeftAxis.setDrawGridLines(true);

        // 是否显示右侧Y轴
        mRightAxis.setDrawGridLines(false);

        // 设置左侧Y轴最小值
        mLeftAxis.setAxisMinimum(0f);

//        mLineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
//            @Override
//            public void onValueSelected(Entry e, Highlight h) {
//                Toast.makeText(mLineChart.this,e.getY()+"",Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onNothingSelected() {
//
//            }
//        });

    }

    /**
     * 设置X轴坐标值
     * @param xAxisValues  x轴坐标集合
     */
    private void setXAxis(final List<String> xAxisValues){


        mLeftAxis.enableAxisLineDashedLine(10f,10f,0); // 虚线
        mXAxis.setLabelCount(4,true);


        // X轴设置在底部
        mXAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        // 自定义X轴坐标名称
        mXAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return xAxisValues.get((int)value);
            }
        });

        mLineChart.invalidate();
    }


    /**
     * 得到折线图 （一条折线）
     *
     * @param lineChartY  折线Y轴值
     * @param lineName  折线图名字
     * @param lineColor  折线颜色
     * @return
     */
    private LineData getLineData(List<Float> lineChartY, String lineName, int lineColor){
        LineData lineData = new LineData();

        // Entry数组列表
        ArrayList<Entry> yValue = new ArrayList<>();
        for (int i=0;i<lineChartY.size();i++){
            yValue.add(new Entry(i,lineChartY.get(i)));
        }
        LineDataSet dataSet = new LineDataSet(yValue,lineName);

        // 设置折线颜色
        dataSet.setColor(lineColor);


        dataSet.setCircleColor(lineColor);
        dataSet.setLineWidth(3f);
        dataSet.setCircleRadius(6f);

        // 设置曲线值的圆点是实心还是空心
        dataSet.setDrawCircleHole(false);

        // 显示值
        dataSet.setDrawValues(true);
        dataSet.setValueTextSize(10f);
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
        lineData.addDataSet(dataSet);

        return lineData;
    }

    private LineData getLineData(List<List<Float>> lineChartY, List<String> lineName, int lineColor){
        LineData lineData = new LineData();
        for (int i=0;i<lineChartY.size();i++){
            // Entry数组列表
            ArrayList<Entry> yValue = new ArrayList<>();
            for (int j=0;j<lineChartY.size();j++){
                yValue.add(new Entry(i,lineChartY.get(i).get(j)));
            }
            LineDataSet dataSet = new LineDataSet(yValue,lineName.get(i));

            // 设置折线颜色
            dataSet.setColor(lineColor);


            dataSet.setCircleColor(lineColor);
            dataSet.setLineWidth(3f);
            dataSet.setCircleRadius(6f);

            // 设置曲线值的圆点是实心还是空心
            dataSet.setDrawCircleHole(false);

            // 显示值
            dataSet.setDrawValues(true);
            dataSet.setValueTextSize(10f);
            dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            dataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);

            lineData.addDataSet(dataSet);
        }



        return lineData;
    }


    /**
     *  显示混合土（柱状图+折线图）
     *
     * @param xAxisValues  X轴名称
     * @param lineChartY  折线图Y值
     * @param lineName  折线图名字
     * @param lineColor  折线图颜色
     */
    public void showCombinedChart(List<String> xAxisValues,List<Float> lineChartY,String lineName, int lineColor){
        initChart();
        setXAxis(xAxisValues);

        LineData lineData = getLineData(lineChartY,lineName,lineColor);

        mLineChart.setData(lineData);
        mLineChart.invalidate();
    }
}
