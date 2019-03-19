package com.example.liwj.mpandroidchartdemo.Activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.liwj.mpandroidchartdemo.R;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;

public class CombinedChartActivity extends AppCompatActivity {

    private CombinedChart mCombinedChart;
    private XAxis mXAxis;
    private YAxis mLeftAxis;
    private YAxis mRightAxis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combined_chart);

        // X轴数据
        List<String> xData = new ArrayList<>();
        for (int i=0;i<20;i++){
            xData.add( i + "号");
        }

        // 柱状图Y轴数据
        List<Float> yBarDatas = new ArrayList<>();
        for (int i=0;i<20;i++){
            yBarDatas .add((float)(Math.random()*100));
        }

        // 折线图Y轴数据
        List<Float> yLineDatas = new ArrayList<>();
        for (int i=0;i<20;i++){
            yLineDatas.add((float)(Math.random()*1000));
        }

        showCombinedChart(xData,yBarDatas,yLineDatas,"用电量","数据环比",getColor(R.color.lineColor),getColor(R.color.lineColor));
    }

    private void initChart(){
        mCombinedChart = (CombinedChart)findViewById(R.id.combinedChart);
        mXAxis = mCombinedChart.getXAxis();
        mLeftAxis = mCombinedChart.getAxisLeft();
        mRightAxis = mCombinedChart.getAxisRight();

        // 不显示描述内容
        mCombinedChart.getDescription().setEnabled(false);

        mCombinedChart.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR,CombinedChart.DrawOrder.LINE
        });

        mCombinedChart.setBackgroundColor(Color.WHITE);
        mCombinedChart.setDrawGridBackground(false);
        mCombinedChart.setDrawBarShadow(false);
        mCombinedChart.setHighlightFullBarEnabled(false);

        // 显示边界
        mCombinedChart.setDrawBorders(false);

        // 图例说明
        Legend legend = mCombinedChart.getLegend();
        legend.setWordWrapEnabled(true);

        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);

        // Y轴设置
        mRightAxis.setDrawGridLines(false);
        mRightAxis.setAxisMinimum(0f);

        mLeftAxis.setDrawGridLines(false);
        mLeftAxis.setAxisMinimum(0f);

        mCombinedChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Toast.makeText(CombinedChartActivity.this,e.getY()+"",Toast.LENGTH_SHORT).show();
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
        XAxis xAxis = mCombinedChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);

        xAxis.setLabelCount(xAxisValues.size()-1,false);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return xAxisValues.get((int)value%xAxisValues.size());
            }
        });
        mCombinedChart.invalidate();
    }


    /**
     * 得到折线图
     *
     * @param lineChartY  折线Y轴值
     * @param lineName  折线图名字
     * @param lineColor  折线颜色
     * @return
     */
    private LineData getLineData(List<Float> lineChartY, String lineName, int lineColor){
        LineData lineData = new LineData();

        ArrayList<Entry> yValue = new ArrayList<>();
        for (int i=0;i<lineChartY.size();i++){
            yValue.add(new Entry(i,lineChartY.get(i)));
        }
        LineDataSet dataSet = new LineDataSet(yValue,lineName);

        dataSet.setColor(lineColor);
        dataSet.setCircleColor(lineColor);

        dataSet.setCircleRadius(1f);

        // 显示值
        dataSet.setDrawValues(true);
        dataSet.setValueTextSize(10f);
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
        lineData.addDataSet(dataSet);

        return lineData;
    }

    /**
     * 得到柱状图
     *
     * @param barChartY  柱状Y轴值
     * @param barName  柱状图名字
     * @param barColor  柱状图颜色
     * @return
     */
    private BarData getBarData(List<Float> barChartY,String barName, int barColor){
        BarData barData = new BarData();

        ArrayList<BarEntry> yValues = new ArrayList<>();
        for (int i=0;i< barChartY.size();i++) {
            yValues.add(new BarEntry(i,barChartY.get(i)));
        }

        BarDataSet barDataSet = new BarDataSet(yValues,barName);
        barDataSet.setColor(barColor);
        barDataSet.setValueTextSize(10f);
        barDataSet.setValueTextColor(barColor);
        barDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        barData.addDataSet(barDataSet);

        // 以下是为了解决柱状图左右两边只显示了一般的问题
        mXAxis.setAxisMaximum(-0.5f);
        mXAxis.setAxisMaximum((float)(barChartY.size() - 0.5));

        return barData;
    }


    /**
     *  显示混合土（柱状图+折线图）
     *
     * @param xAxisValues  X轴名称
     * @param barChartY  柱状图Y值
     * @param lineChartY  折线图Y值
     * @param barName  柱状图名字
     * @param lineName  折线图名字
     * @param barColor  柱状图颜色
     * @param lineColor  折线图颜色
     */
    public void showCombinedChart(List<String> xAxisValues, List<Float> barChartY, List<Float> lineChartY, String barName, String lineName, int barColor, int lineColor){
        initChart();
        setXAxis(xAxisValues);

        CombinedData combinedData = new CombinedData();

        combinedData.setData(getBarData(barChartY,barName,barColor));
        combinedData.setData(getLineData(lineChartY,lineName,lineColor));
        mCombinedChart.setData(combinedData);
        mCombinedChart.invalidate();
    }






}
