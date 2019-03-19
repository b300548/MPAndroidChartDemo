package com.example.liwj.mpandroidchartdemo.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.liwj.mpandroidchartdemo.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;

public class LineChartActivity extends AppCompatActivity {

    private LineChart mLineChart;
    private XAxis mXAxis;  // X轴
    private YAxis mLeftAxis; // 左侧Y轴
    private YAxis mRightAxis; // 右侧Y轴
    private Legend mLegend; // 图例
    private LimitLine mLimitLine; // 限制线
    private MarkerView mMarkerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);

        mLineChart = (LineChart)findViewById(R.id.lineChart);

        initChart(mLineChart);
        showLineChart("房间一");


    }

    /**
     * 初始化图表
     * @param lineChart  需要初始化的图表
     */
    private void initChart(LineChart lineChart){

        /* 图表设置 */
        //是否展示网格线
//        lineChart.setDrawGridBackground(true);
//        lineChart.setGridBackgroundColor(Color.CYAN);
        //是否显示边界
        lineChart.setDrawBorders(false);
        // 是否可以拖动
        lineChart.setDragEnabled(true);
        //是否有触摸事件
        lineChart.setTouchEnabled(true);
        // 设置XY轴动画效果
        //lineChart.animateX(1500);
        //lineChart.animateY(2500);

        /* XY轴设置 */
        mXAxis = lineChart.getXAxis();
        mLeftAxis = lineChart.getAxisLeft();
        mRightAxis = lineChart.getAxisRight();

        // 设置右侧Y轴不可见
        mRightAxis.setEnabled(false);

        // 不显示网格线
        mXAxis.setDrawGridLines(false);
        mRightAxis.setDrawGridLines(false);
        mLeftAxis.setDrawGridLines(true);

        mLeftAxis.enableAxisLineDashedLine(10f,10f,0);


        // X轴设置在底部
        mXAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

//        mXAxis.setAxisMinimum(5f);
//        mXAxis.setAxisMaximum(10f);

        // 自定义X轴坐标名称
        mXAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return (int)value + "号";
            }
        });

        /* 折线图例 标签 设置 */
        mLegend = lineChart.getLegend();
        // 设置显示类型， Line Circle Square Empty 等等多种方式
        mLegend.setForm(Legend.LegendForm.LINE);
        mLegend.setTextSize(12f);

        // 显示位置 左下方
        mLegend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        mLegend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        mLegend.setOrientation(Legend.LegendOrientation.HORIZONTAL);

        // 是否绘制在图表里面
        mLegend.setDrawInside(false);

        // 不显示图例
        mLegend.setEnabled(false);

        // 设置图表数据点击回调
        lineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Toast.makeText(LineChartActivity.this,"用电量：" + e.getY(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });

        // 设置Description不可见
        Description description = new Description();
        description.setEnabled(false);
        lineChart.setDescription(description);
    }

    /**
     * 曲线初始化设置
     * 一个LineDataSet 代表一条曲线
     * @param lineDataSet 线条
     */
    private void initLineDataSet(LineDataSet lineDataSet){
        lineDataSet.setColor(getResources().getColor(R.color.lineColor));
        lineDataSet.setCircleColor(getResources().getColor(R.color.lineColor));
        lineDataSet.setLineWidth(3f);
        lineDataSet.setCircleRadius(6f);

        // 设置曲线值的圆点是实心还是空心
        lineDataSet.setDrawCircleHole(false);

        lineDataSet.setValueTextSize(12f);

        // 设置折线图填充
        lineDataSet.setDrawFilled(false);
        lineDataSet.setFormLineWidth(1f);
        lineDataSet.setFormSize(15.f);

        // 设置曲线展示位原话曲线（默认是折线）
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
    }

    private void showLineChart(String name){
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < 20 ;i++){
            Entry entry = new Entry(i,(float)(Math.random())*10000);
            entries.add(entry);
        }

        // 每一个LineDataSet 代表一条线
        LineDataSet lineDataSet = new LineDataSet(entries,name);
        initLineDataSet(lineDataSet);
        LineData lineData = new LineData(lineDataSet);
        mLineChart.setData(lineData);
        mLineChart.setVisibleXRange(5,12);
    }
}
