package com.example.liwj.mpandroidchartdemo.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.liwj.mpandroidchartdemo.R;

public class MainActivity extends AppCompatActivity {
    private Button mBtnLineChart;
    private Button mBtnCombinedChart;
    private Button mBtnPieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnLineChart = (Button)findViewById(R.id.btn_lineChart);
        mBtnLineChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,LineChartActivity.class);
                startActivity(intent);
            }
        });

        mBtnCombinedChart = (Button)findViewById(R.id.btn_combinedChart);
        mBtnCombinedChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,CombinedChartActivity.class);
                startActivity(intent);
            }
        });

        mBtnPieChart = (Button)findViewById(R.id.btn_pieChaer);
        mBtnPieChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PieChartActivity.class);
                startActivity(intent);
            }
        });
    }
}
