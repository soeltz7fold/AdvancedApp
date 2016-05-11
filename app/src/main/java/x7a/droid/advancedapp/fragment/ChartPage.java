package x7a.droid.advancedapp.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Locale;

import x7a.droid.advancedapp.R;

/**
 * Created by DroiD on 12/05/2016.
 */
public class ChartPage extends Fragment {
    //Pie
    private PieChart pie_chart;
    private float[] y_pie = {8000, 80000, 72000};
    private String[] x_pie = {"expenses", "incomes", "balance"};

    //Fab Progress
    private int mScrollOffset = 10;
    private int mMaxProgress = 100;
    private LinkedList<ProgressType> mProgressTypes;
    private Handler mUiHandler = new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chart_page, container, false);
        pie_chart = (PieChart) view.findViewById(R.id.pieChart);
        layout_pie_chart();
        return view;
    }

    private void layout_pie_chart() {
        pie_chart.setDescription("Card Statistic");
        pie_chart.setUsePercentValues(true);
        //enable hole and configure
        pie_chart.setDrawHoleEnabled(true);
//        pie_chart.setHoleColorTransparent(true);
        pie_chart.setHoleRadius(9);
        pie_chart.setTransparentCircleRadius(10);
        //enable rotation
        pie_chart.setRotationEnabled(true);
        pie_chart.setRotationAngle(0);
        //add listener when rotate
        pie_chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                if (e.equals(null)) return;
                Toast.makeText(getActivity(), x_pie[e.getXIndex()] + " = " + e.getVal() + "%", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });
        add_entry_pie_chart();
        //custom legend
        Legend legend = pie_chart.getLegend();
        legend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        legend.setXEntrySpace(9);
        legend.setYEntrySpace(7);
    }

    private void add_entry_pie_chart() {
        ArrayList<Entry> y_value = new ArrayList<Entry>();
        for (int i = 0; i < y_pie.length; i++) {
            y_value.add(new Entry(y_pie[i], i));
        }
        ArrayList<String> x_value = new ArrayList<String>();
        for (int i = 0; i < x_pie.length; i++) {
            x_value.add(x_pie[i]);
        }
        //create pie data
        PieDataSet data_set = new PieDataSet(y_value, "Market Share");
        data_set.setSliceSpace(3);
        data_set.setSelectionShift(5);
        //add many colors
        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int c : ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(c);
        }
        for (int c : ColorTemplate.JOYFUL_COLORS) {
            colors.add(c);
        }
        for (int c : ColorTemplate.COLORFUL_COLORS) {
            colors.add(c);
        }
        for (int c : ColorTemplate.LIBERTY_COLORS) {
            colors.add(c);
        }
        for (int c : ColorTemplate.PASTEL_COLORS) {
            colors.add(c);
        }
        colors.add(ColorTemplate.getHoloBlue());
        data_set.setColors(colors);

        //instantiate pie data object
        PieData data = new PieData(x_value, data_set);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.GRAY);
        pie_chart.setData(data);
        pie_chart.highlightValue(null);
        pie_chart.invalidate();
    }


//Method custom fab
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Locale[] availableLocales = Locale.getAvailableLocales();
        mProgressTypes = new LinkedList<>();
        for (ProgressType type : ProgressType.values()) {
            mProgressTypes.offer(type);
        }

        final FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab_anim);
        fab.setMax(mMaxProgress);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressType type = mProgressTypes.poll();
                switch (type) {
                    case INDETERMINATE:
                        fab.setShowProgressBackground(true);
                        fab.setIndeterminate(true);
                        mProgressTypes.offer(ProgressType.INDETERMINATE);
                        break;
                    case PROGRESS_POSITIVE:
                        fab.setIndeterminate(false);
                        fab.setProgress(70, true);
                        mProgressTypes.offer(ProgressType.PROGRESS_POSITIVE);
                        break;
                    case PROGRESS_NEGATIVE:
                        fab.setProgress(30, true);
                        mProgressTypes.offer(ProgressType.PROGRESS_NEGATIVE);
                        break;
                    case HIDDEN:
                        fab.hideProgress();
                        mProgressTypes.offer(ProgressType.HIDDEN);
                        break;
                    case PROGRESS_NO_ANIMATION:
                        increaseProgress(fab, 0);
                        break;
                    case PROGRESS_NO_BACKGROUND:
                        fab.setShowProgressBackground(false);
                        fab.setIndeterminate(true);
                        mProgressTypes.offer(ProgressType.PROGRESS_NO_BACKGROUND);
                        break;
                }
            }
        });
    }

    private enum ProgressType {
        INDETERMINATE, PROGRESS_POSITIVE, PROGRESS_NEGATIVE, HIDDEN, PROGRESS_NO_ANIMATION, PROGRESS_NO_BACKGROUND
    }
    private void increaseProgress(final FloatingActionButton fab, int i) {
        if (i <= mMaxProgress) {
            fab.setProgress(i, false);
            final int progress = ++i;
            mUiHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    increaseProgress(fab, progress);
                }
            }, 30);
        } else {
            mUiHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    fab.hideProgress();
                }
            }, 200);
            mProgressTypes.offer(ProgressType.PROGRESS_NO_ANIMATION);
        }
    }
}
