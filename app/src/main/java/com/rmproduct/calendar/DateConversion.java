package com.rmproduct.calendar;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

public class DateConversion extends AppCompatActivity {

    private DatePicker eDatePicker;
    private Button convert;
    private TextView banglaDate;
    private Typeface banglaFont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_conversion);

        eDatePicker = findViewById(R.id.eDatePicker);
        convert = findViewById(R.id.convert);
        banglaDate = findViewById(R.id.banglaDate);

        banglaFont = Typeface.createFromAsset(getAssets(), "font/bangla_regular.TTF");

        banglaDate.setTypeface(banglaFont);

        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int day = 0, month = 0, year = 0, iday, imonth, iyear;
                iday = eDatePicker.getDayOfMonth();
                imonth = eDatePicker.getMonth() + 1;
                iyear = eDatePicker.getYear();

                if (iday <= 13) {
                    if (imonth == 6 || imonth == 7 || imonth == 8 || imonth == 9 || imonth == 10) {

                        iday = iday + 31;
                        day = iday - 13;
                        month = imonth - 4;
                        year = iyear - 593;

                    } else if (imonth == 11 || imonth == 12) {

                        iday = iday + 30;
                        day = iday - 13;
                        month = imonth - 4;
                        year = iyear - 593;

                    } else if (imonth == 1 || imonth == 3 || imonth == 4 || imonth == 5) {

                        iday = iday + 30;
                        day = iday - 13;
                        month = imonth - 4;
                        year = iyear - 594;

                    } else if (imonth == 2) {

                        if (((year % 400 == 0) || (year % 100 != 0) && (year % 4 == 00))) {

                            iday = iday + 29;
                            day = iday - 13;
                            month = imonth - 4;
                            year = iyear - 594;

                        } else {

                            iday = iday + 28;
                            day = iday - 13;
                            month = imonth - 4;
                            year = iyear - 594;

                        }

                    }
                } else {

                    day = (iday+1) - 13;

                    if (imonth <= 3) {
                        month = (imonth + 12) - 3;
                        year = iyear - 594;
                    } else {
                        month = imonth - 3;
                        year = iyear - 593;
                    }
                }

                banglaDate.setText(day + " " + pickBanglaMonth(month) + " " + year);
            }
        });
    }

    private String pickBanglaMonth(int month) {
        String bMonth = "";

        if (month == 1) {
            bMonth = "‰ekvL"; //বৈশাখ
        } else if (month == 2) {
            bMonth = "‰Rô¨"; //জৈষ্ঠ্য
        } else if (month == 3) {
            bMonth = "Avlvp"; //আষাঢ়
        } else if (month == 4) {
            bMonth = "kÖveY"; //শ্রাবণ
        } else if (month == 5) {
            bMonth = "fv`"; //ভাদ্র
        } else if (month == 6) {
            bMonth = "Avwk¦b"; //আশ্বিন
        } else if (month == 7) {
            bMonth = "KvwË©K"; //কার্ত্তিক
        } else if (month == 8) {
            bMonth = "AMÖnvqY"; //অগ্রহায়ণ
        } else if (month == 9) {
            bMonth = "‡cŠl"; //পৌষ
        } else if (month == 10) {
            bMonth = "gvN"; //মাঘ
        } else if (month == 11) {
            bMonth = "dvêyb"; //ফাল্গুন
        } else if (month == 12) {
            bMonth = "‰PÎ"; //চৈত্র
        }

        return bMonth;
    }
}
