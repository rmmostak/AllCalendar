package com.rmproduct.calendar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AgeCalculation extends AppCompatActivity {

    private Spinner day, month, year;
    private Button calculate;
    private TextView age, cDay, cMonth, cYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_age_calculation);

        day = findViewById(R.id.spinnerDay);
        month = findViewById(R.id.spinnerMonth);
        year = findViewById(R.id.spinnerYear);

        calculate = findViewById(R.id.calculate);

        cDay=findViewById(R.id.cDay);
        cMonth=findViewById(R.id.cMonth);
        cYear=findViewById(R.id.cYear);
        age = findViewById(R.id.age);

        Calendar c = Calendar.getInstance();
        int currentDay = c.get(Calendar.DAY_OF_MONTH);
        int currentMonth = c.get(Calendar.MONTH) + 1;
        int currentYear = c.get(Calendar.YEAR);

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("MMMM");
        String strMonth = formatter.format(date);

        cDay.setText(String.valueOf(currentDay));
        cMonth.setText(strMonth);
        cYear.setText(String.valueOf(currentYear));

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar c = Calendar.getInstance();
                int currentDay = c.get(Calendar.DAY_OF_MONTH);
                int currentMonth = c.get(Calendar.MONTH) + 1;
                int currentYear = c.get(Calendar.YEAR);

                String strDay, strMonth, strYear;
                strDay = day.getSelectedItem().toString().trim();
                strMonth = month.getSelectedItem().toString().trim();
                strYear = year.getSelectedItem().toString().trim();

                int intDay = 0, intMonth = 0, intYear = 0;
                int s2iday, s2imonth, s2iyear;

                if (!strDay.equals("Day")) {

                    if (!strMonth.equals("Month")) {

                        if (!strYear.equals("Year")) {

                            s2iday = Integer.parseInt(strDay);
                            s2imonth = pickMonthNumber(strMonth);
                            s2iyear = Integer.parseInt(strYear);

                            if ((currentDay < s2iday) && (strMonth.equals("January") || strMonth.equals("March") || strMonth.equals("May") || strMonth.equals("July") || strMonth.equals("August") || strMonth.equals("October") || strMonth.equals("December"))) {

                                intDay = (currentDay + 31) - s2iday;
                                s2imonth = s2imonth + 1;

                                if (currentMonth < s2imonth) {

                                    intMonth = (currentMonth + 12) - s2imonth;
                                    s2iyear = s2iyear + 1;
                                    intYear = currentYear - s2iyear;

                                } else {

                                    intMonth = currentMonth - s2imonth;
                                    intYear = currentYear - s2iyear;

                                }
                            } else if ((currentDay < s2iday) && (strMonth.equals("February") || strMonth.equals("April") || strMonth.equals("June") || strMonth.equals("September") || strMonth.equals("November") || strMonth.equals("October"))) {

                                intDay = (currentDay + 30) - s2iday;
                                s2imonth = s2imonth + 1;

                                if (currentMonth < s2imonth) {

                                    intMonth = (currentMonth + 12) - s2imonth;
                                    s2iyear = s2iyear + 1;
                                    intYear = currentYear - s2iyear;

                                } else {

                                    intMonth = currentMonth - s2imonth;
                                    intYear = currentYear - s2iyear;

                                }

                            } else if (currentDay >= s2iday) {

                                intDay = currentDay - s2iday;

                                if (currentMonth < s2imonth) {

                                    intMonth = (currentMonth + 12) - s2imonth;
                                    s2iyear = s2iyear + 1;
                                    intYear = currentYear - s2iyear;

                                } else {

                                    intMonth = currentMonth - s2imonth;
                                    intYear = currentYear - s2iyear;

                                }
                            }

                            if (intDay<0||intMonth<0||intYear<0) {
                                age.setText("Please select valid birth date!");
                            } else {

                                age.setText(intYear + " Year(s) " + intMonth + " Month(s) " + intDay + " Day(s)");

                            }

                        } else {

                            Toast.makeText(getApplicationContext(), "Please select Year!", Toast.LENGTH_LONG).show();

                        }
                    } else {

                        Toast.makeText(getApplicationContext(), "Please select Month!", Toast.LENGTH_LONG).show();

                    }

                } else {

                    Toast.makeText(getApplicationContext(), "Please select Day!", Toast.LENGTH_LONG).show();

                }

            }
        });

    }

    private int pickMonthNumber(String strMonth) {
        int monthNumber = -1;
        if (strMonth.equals("January")) {
            monthNumber = 1;
        } else if (strMonth.equals("February")) {
            monthNumber = 2;
        } else if (strMonth.equals("March")) {
            monthNumber = 3;
        } else if (strMonth.equals("April")) {
            monthNumber = 4;
        } else if (strMonth.equals("May")) {
            monthNumber = 5;
        } else if (strMonth.equals("June")) {
            monthNumber = 6;
        } else if (strMonth.equals("July")) {
            monthNumber = 7;
        } else if (strMonth.equals("August")) {
            monthNumber = 8;
        } else if (strMonth.equals("September")) {
            monthNumber = 9;
        } else if (strMonth.equals("October")) {
            monthNumber = 10;
        } else if (strMonth.equals("November")) {
            monthNumber = 11;
        } else if (strMonth.equals("December")) {
            monthNumber = 12;
        }

        return monthNumber;
    }
}
