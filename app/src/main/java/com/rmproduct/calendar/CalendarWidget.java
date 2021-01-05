package com.rmproduct.calendar;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.icu.util.IslamicCalendar;
import android.view.LayoutInflater;
import android.widget.RemoteViews;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CalendarWidget extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them

        for (int appWidgetId : appWidgetIds) {

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.calendar_widget);

            views.setTextViewText((R.id.day), "AvR " + pickBanglaDay());
            views.setTextViewText(R.id.banglaDate, pickBanglaDate());
            views.setTextViewText(R.id.arabicDate, pickArabicDate());
            views.setTextViewText(R.id.englishDate, pickDate());


            //create pending activity on click the view(RelativeLayout)
            Intent intentUpdate = new Intent(context, MainActivity.class);
            intentUpdate.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

            int[] idArray = new int[]{appWidgetId};

            intentUpdate.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, idArray);

            PendingIntent pendingUpdate = PendingIntent.getBroadcast(
                    context, appWidgetId, intentUpdate,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            views.setOnClickPendingIntent(R.id.root, pendingUpdate);

            appWidgetManager.updateAppWidget(appWidgetId, views);

        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }


    private String pickDate() {

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        String strDate = formatter.format(date);

        return strDate;
    }

    private String pickBanglaDay() {

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("E", Locale.getDefault());
        String strDay = formatter.format(date);
        String strBanglaDay = "";

        if (strDay.equals("Sat")) {
            strBanglaDay = "kwbevi";
        } else if (strDay.equals("Sun")) {
            strBanglaDay = "iweevi";
        } else if (strDay.equals("Mon")) {
            strBanglaDay = "‡mvgevi";
        } else if (strDay.equals("Tue")) {
            strBanglaDay = "g½jevi";
        } else if (strDay.equals("Wed")) {
            strBanglaDay = "eyaevi";
        } else if (strDay.equals("Thu")) {
            strBanglaDay = "e„n¯úwZevi";
        } else if (strDay.equals("Fri")) {
            strBanglaDay = "ïµevi";
        }

        return strBanglaDay;
    }

    private String pickBanglaDate() {

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("MMMM");
        String strMonth = formatter.format(date);

        String Month = "", banglaNumber = "";
        int banglaDay = 1, i, dayNumber = 1, banglaYear;
        banglaYear = year - 593;

        if (strMonth.equals("April") && day <= 13) {
            Month = "‰PÎ"; //চৈত্র
            banglaYear = banglaYear - 1;
            dayNumber = 1;
            banglaDay = 14;
            for (i = dayNumber; i > day; i++) {
                banglaDay = banglaDay + 1;
            }
        } else if (strMonth.equals("April") && day > 13) {
            Month = "‰ekvL"; //বৈশাখ
            dayNumber = 14;
            banglaDay = 1;
            for (i = dayNumber; i < day; i++) {
                banglaDay = banglaDay + 1;
            }
        } else if (strMonth.equals("May") && day <= 14) {
            Month = "‰ekvL"; //বৈশাখ
            dayNumber = 1;
            banglaDay = 15;
            for (i = dayNumber; i > day; i++) {
                banglaDay = banglaDay + 1;

            }
        } else if (strMonth.equals("May") && day > 14) {
            Month = "‰Rô¨"; //জৈষ্ঠ্য
            dayNumber = 15;
            banglaDay = 1;
            for (i = dayNumber; i < day; i++) {
                banglaDay = banglaDay + 1;
            }
        } else if (strMonth.equals("June") && day <= 14) {
            Month = "‰Rô¨"; //জৈষ্ঠ্য
            dayNumber = 1;
            banglaDay = 15;
            for (i = dayNumber; i > day; i++) {
                banglaDay = banglaDay + 1;

            }
        } else if (strMonth.equals("June") && day > 14) {
            Month = "Avlvp"; //আষাঢ়
            dayNumber = 15;
            banglaDay = 1;
            for (i = dayNumber; i < day; i++) {
                banglaDay = banglaDay + 1;
            }
        } else if (strMonth.equals("July") && day <= 15) {

            Month = "Avlvp"; //আষাঢ়
            dayNumber = 1;
            banglaDay = 16;
            for (i = dayNumber; i < day; i++) {

                banglaDay = banglaDay + day;

            }

        } else if (strMonth.equals("July") && day > 15) {
            Month = "kÖveY"; //শ্রাবণ
            dayNumber = 16;
            banglaDay = 1;
            for (i = dayNumber; i < day; i++) {
                banglaDay = banglaDay + 1;
            }
        } else if (strMonth.equals("August") && day <= 15) {
            dayNumber = 1;
            banglaDay = 16;
            Month = "kÖveY"; //শ্রাবণ

            for (i = dayNumber; i <= day; i++) {
                banglaDay = banglaDay + 1;
            }
        } else if (strMonth.equals("August") && day > 15) {
            dayNumber = 16;
            banglaDay = 1;
            Month = "fv`ª"; //ভাদ্র

            for (i = dayNumber; i < day; i++) {
                banglaDay = banglaDay + 1;
            }
        } else if (strMonth.equals("September") && day <= 15) {
            Month = "fv`ª"; //ভাদ্র
            dayNumber = 1;
            banglaDay = 16;

            for (i = dayNumber; i < day; i++) {
                banglaDay = banglaDay + 1;

            }
        } else if (strMonth.equals("September") && day > 15) {
            Month = "Avwk¦b"; //আশ্বিন
            dayNumber = 16;
            banglaDay = 1;

            for (i = dayNumber; i < day; i++) {
                banglaDay = banglaDay + 1;

            }
        } else if (strMonth.equals("October") && day <= 16) {
            Month = "Avwk¦b"; //আশ্বিন

            dayNumber = 1;
            banglaDay = 17;
            for (i = dayNumber; i < day; i++) {
                banglaDay = banglaDay + 1;

            }
        } else if (strMonth.equals("October") && day > 16) {
            Month = "KvwË©K"; //কার্ত্তিক
            dayNumber = 17;
            banglaDay = 1;
            for (i = dayNumber; i < day; i++) {
                banglaDay = banglaDay + 1;
            }
        } else if (strMonth.equals("November") && day <= 15) {
            Month = "KvwË©K"; //কার্ত্তিক
            dayNumber = 1;
            banglaDay = 16;
            for (i = dayNumber; i < day; i++) {
                banglaDay = banglaDay + 1;

            }

        } else if (strMonth.equals("November") && day > 15) {
            Month = "AMÖnvqY"; //অগ্রহায়ণ
            dayNumber = 16;
            banglaDay = 1;
            for (i = dayNumber; i < day; i++) {
                banglaDay = banglaDay + 1;
            }
        } else if (strMonth.equals("December") && day <= 15) {

            Month = "AMÖnvqY"; //অগ্রহায়ণ
            dayNumber = 1;
            banglaDay = 16;
            for (i = dayNumber; i < day; i++) {
                banglaDay = banglaDay + 1;
            }
        } else if (strMonth.equals("December") && day > 15) {
            Month = "‡cŠl"; //পৌষ
            dayNumber = 16;
            banglaDay = 1;
            for (i = dayNumber; i < day; i++) {
                banglaDay = banglaDay + 1;
            }
        } else if (strMonth.equals("January") && day <= 14) {
            Month = "‡cŠl"; //পৌষ
            banglaYear = banglaYear - 1;
            dayNumber = 1;
            banglaDay = 17; //for 15
            for (i = dayNumber; i < day; i++) {
                banglaDay = banglaDay + 1;

            }
        } else if (strMonth.equals("January") && day > 14) {
            Month = "gvN"; //মাঘ
            banglaYear = banglaYear - 1;
            dayNumber = 15;
            banglaDay = 1;
            for (i = dayNumber; i < day; i++) {
                banglaDay = banglaDay + 1;
            }
        } else if (strMonth.equals("February") && day <= 13) {
            Month = "gvN"; //মাঘ
            banglaYear = banglaYear - 1;
            dayNumber = 1;
            banglaDay = 14;
            for (i = dayNumber; i < day; i++) {
                banglaDay = banglaDay + 1;

            }
        } else if (strMonth.equals("February") && day > 13) {
            Month = "dvêyb"; //ফাল্গুন
            banglaYear = banglaYear - 1;
            dayNumber = 14;
            banglaDay = 1;
            for (i = dayNumber; i < day; i++) {
                banglaDay = banglaDay + 1;
            }
        } else if (strMonth.equals("March") && day <= 14) {
            Month = "dvêyb"; //ফাল্গুন
            banglaYear = banglaYear - 1;
            dayNumber = 1;
            banglaDay = 15;

            for (i = dayNumber; i < day; i++) {
                banglaDay = banglaDay + 1;
                if (((year % 400 == 0) || (year % 100 != 0) && (year % 4 == 00))) {
                    if (banglaDay > 30) {
                        banglaDay = 1;
                        break;
                    }
                } else {
                    if (banglaDay > 29) {
                        banglaDay = 1;
                        break;
                    }
                }

            }

        } else if (strMonth.equals("March") && day > 14) {
            Month = "‰PÎ"; //চৈত্র
            banglaYear = banglaYear - 1;
            dayNumber = 15;
            banglaDay = 1;
            for (i = dayNumber; i < day; i++) {
                banglaDay = banglaDay + 1;
            }
        }

        return ((banglaDay) + " " + Month + " " + (banglaYear));
    }

    private String pickArabicDate() {
        IslamicCalendar c = new IslamicCalendar();
        int year = c.get(IslamicCalendar.YEAR);
        int month = c.get(IslamicCalendar.MONTH) + 1;
        int day = c.get(IslamicCalendar.DAY_OF_MONTH);

        String strMonth = "";

        if (month == 1) {
            strMonth = "gyniig"; //মুহররম
        } else if (month == 2) {
            strMonth = "mdi"; //সফর
        } else if (month == 3) {
            strMonth = "iweDj AvDqvj"; //রবিউল আউয়াল
        } else if (month == 4) {
            strMonth = "iweDm mvwb"; //রবিউস সানি
        } else if (month == 5) {
            strMonth = "Rgvw`Dj AvDqvj"; //জমাদিউল আউয়াল
        } else if (month == 6) {
            strMonth = "Rgvw`Dm mvwb"; //জমাদিউস সানি
        } else if (month == 7) {
            strMonth = "iRe"; //রজব
        } else if (month == 8) {
            strMonth = "kvÕevb"; //শা'বান
        } else if (month == 9) {
            strMonth = "igRvb"; //রমজান
        } else if (month == 10) {
            strMonth = "kvIqvj"; //শাওয়াল
        } else if (month == 11) {
            strMonth = "wR¡jK`"; //জ্বিলকদ
        } else if (month == 12) {
            strMonth = "wR¡jn¾"; //জ্বিলহজ্জ
        }

        return (day + " " + strMonth + " " + year);
    }

}

