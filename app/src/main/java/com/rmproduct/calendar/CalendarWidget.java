package com.rmproduct.calendar;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.icu.util.IslamicCalendar;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CalendarWidget extends AppWidgetProvider {

    private static final String MyOnClick = "myOnClickTag";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them

        for (int appWidgetId : appWidgetIds) {

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.calendar_widget);

            views.setTextViewText((R.id.day), context.getString(R.string.day) + " " + pickBanglaDay(context));
            views.setTextViewText(R.id.banglaDate, pickBanglaDate(context));
            views.setTextViewText(R.id.arabicDate, pickArabicDate(context));
            views.setTextViewText(R.id.englishDate, pickDate());

            updateAppWidget(context, appWidgetManager, appWidgetId);

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

    protected PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        PendingIntent pendingIntent = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getActivity
                    (context, 0, intent, PendingIntent.FLAG_MUTABLE);
        } else {
            pendingIntent = PendingIntent.getActivity
                    (context, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        }
        return pendingIntent;
    }

    void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                         int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.calendar_widget);
        //RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.calendar_widget);

        views.setTextViewText((R.id.day), context.getString(R.string.day) +" "+ pickBanglaDay(context));
        views.setTextViewText(R.id.banglaDate, pickBanglaDate(context));
        views.setTextViewText(R.id.arabicDate, pickArabicDate(context));
        views.setTextViewText(R.id.englishDate, pickDate());
        views.setOnClickPendingIntent(R.id.root, getPendingSelfIntent(context, MyOnClick));

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (MyOnClick.equals(intent.getAction())) {
            Intent intentUpdate = new Intent(context, MainActivity.class);
            intentUpdate.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intentUpdate);//intentUpdate.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        }
    }

    private String pickDate() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int day = c.get(Calendar.DAY_OF_MONTH);

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("MMMM");
        String strMonth = formatter.format(date);

        return toBangla(String.valueOf(day)) + " " + banglaMonth(strMonth) + " " + toBangla(String.valueOf(year));
    }

    private String pickBanglaDay(Context context) {

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("E", Locale.getDefault());
        String strDay = formatter.format(date);
        String strBanglaDay = "";

        if (strDay.equals("Sat")) {
            strBanglaDay = context.getString(R.string.sat);
        } else if (strDay.equals("Sun")) {
            strBanglaDay = context.getString(R.string.sun);
        } else if (strDay.equals("Mon")) {
            strBanglaDay = context.getString(R.string.mon);
        } else if (strDay.equals("Tue")) {
            strBanglaDay = context.getString(R.string.tue);
        } else if (strDay.equals("Wed")) {
            strBanglaDay = context.getString(R.string.wed);
        } else if (strDay.equals("Thu")) {
            strBanglaDay = context.getString(R.string.thu);
        } else if (strDay.equals("Fri")) {
            strBanglaDay = context.getString(R.string.fri);
        }
        return strBanglaDay;
    }

    private String pickBanglaDate(Context context) {

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
            Month = context.getString(R.string.april); //চৈত্র
            banglaYear = banglaYear - 1;
            dayNumber = 1;
            banglaDay = 14;
            for (i = dayNumber; i > day; i++) {
                banglaDay = banglaDay + 1;
            }
        } else if (strMonth.equals("April") && day > 13) {
            Month = context.getString(R.string.boishakh); //বৈশাখ
            dayNumber = 14;
            banglaDay = 1;
            for (i = dayNumber; i < day; i++) {
                banglaDay = banglaDay + 1;
            }
        } else if (strMonth.equals("May") && day <= 14) {
            Month = context.getString(R.string.boishakh2); //বৈশাখ
            dayNumber = 1;
            banglaDay = 15;
            for (i = dayNumber; i > day; i++) {
                banglaDay = banglaDay + 1;

            }
        } else if (strMonth.equals("May") && day > 14) {
            Month = context.getString(R.string.jaistho); //জৈষ্ঠ্য
            dayNumber = 15;
            banglaDay = 1;
            for (i = dayNumber; i < day; i++) {
                banglaDay = banglaDay + 1;
            }
        } else if (strMonth.equals("June") && day <= 14) {
            Month = context.getString(R.string.jaistho2); //জৈষ্ঠ্য
            dayNumber = 1;
            banglaDay = 15;
            for (i = dayNumber; i > day; i++) {
                banglaDay = banglaDay + 1;

            }
        } else if (strMonth.equals("June") && day > 14) {
            Month = context.getString(R.string.ashar); //আষাঢ়
            dayNumber = 15;
            banglaDay = 1;
            for (i = dayNumber; i < day; i++) {
                banglaDay = banglaDay + 1;
            }
        } else if (strMonth.equals("July") && day <= 15) {

            Month = context.getString(R.string.ashar2); //আষাঢ়
            dayNumber = 1;
            banglaDay = 16;
            for (i = dayNumber; i < day; i++) {

                banglaDay = banglaDay + day;

            }

        } else if (strMonth.equals("July") && day > 15) {
            Month = context.getString(R.string.srabon); //শ্রাবণ
            dayNumber = 16;
            banglaDay = 1;
            for (i = dayNumber; i < day; i++) {
                banglaDay = banglaDay + 1;
            }
        } else if (strMonth.equals("August") && day <= 15) {
            dayNumber = 1;
            banglaDay = 16;
            Month = context.getString(R.string.srabon2); //শ্রাবণ

            for (i = dayNumber; i <= day; i++) {
                banglaDay = banglaDay + 1;
            }
        } else if (strMonth.equals("August") && day > 15) {
            dayNumber = 16;
            banglaDay = 1;
            Month = context.getString(R.string.vadro); //ভাদ্র

            for (i = dayNumber; i < day; i++) {
                banglaDay = banglaDay + 1;
            }
        } else if (strMonth.equals("September") && day <= 15) {
            Month = context.getString(R.string.vadro2); //ভাদ্র
            dayNumber = 1;
            banglaDay = 16;

            for (i = dayNumber; i < day; i++) {
                banglaDay = banglaDay + 1;

            }
        } else if (strMonth.equals("September") && day > 15) {
            Month = context.getString(R.string.ashwin); //আশ্বিন
            dayNumber = 16;
            banglaDay = 1;

            for (i = dayNumber; i < day; i++) {
                banglaDay = banglaDay + 1;

            }
        } else if (strMonth.equals("October") && day <= 16) {
            Month = context.getString(R.string.ashwin2); //আশ্বিন

            dayNumber = 1;
            banglaDay = 17;
            for (i = dayNumber; i < day; i++) {
                banglaDay = banglaDay + 1;

            }
        } else if (strMonth.equals("October") && day > 16) {
            Month = context.getString(R.string.kartik); //কার্ত্তিক
            dayNumber = 17;
            banglaDay = 1;
            for (i = dayNumber; i < day; i++) {
                banglaDay = banglaDay + 1;
            }
        } else if (strMonth.equals("November") && day <= 15) {
            Month = context.getString(R.string.kartik2); //কার্ত্তিক
            dayNumber = 1;
            banglaDay = 16;
            for (i = dayNumber; i < day; i++) {
                banglaDay = banglaDay + 1;

            }

        } else if (strMonth.equals("November") && day > 15) {
            Month = context.getString(R.string.agun); //অগ্রহায়ণ
            dayNumber = 16;
            banglaDay = 1;
            for (i = dayNumber; i < day; i++) {
                banglaDay = banglaDay + 1;
            }
        } else if (strMonth.equals("December") && day <= 15) {

            Month = context.getString(R.string.agun2); //অগ্রহায়ণ
            dayNumber = 1;
            banglaDay = 16;
            for (i = dayNumber; i < day; i++) {
                banglaDay = banglaDay + 1;
            }
        } else if (strMonth.equals("December") && day > 15) {
            Month = context.getString(R.string.poush); //পৌষ
            dayNumber = 16;
            banglaDay = 1;
            for (i = dayNumber; i < day; i++) {
                banglaDay = banglaDay + 1;
            }
        } else if (strMonth.equals("January") && day <= 14) {
            Month = context.getString(R.string.poush2); //পৌষ
            banglaYear = banglaYear - 1;
            dayNumber = 1;
            banglaDay = 17; //for 15
            for (i = dayNumber; i < day; i++) {
                banglaDay = banglaDay + 1;

            }
        } else if (strMonth.equals("January") && day > 14) {
            Month = context.getString(R.string.magh); //মাঘ
            banglaYear = banglaYear - 1;
            dayNumber = 15;
            banglaDay = 1;
            for (i = dayNumber; i < day; i++) {
                banglaDay = banglaDay + 1;
            }
        } else if (strMonth.equals("February") && day <= 13) {
            Month = context.getString(R.string.magh2); //মাঘ
            banglaYear = banglaYear - 1;
            dayNumber = 1;
            banglaDay = 14;
            for (i = dayNumber; i < day; i++) {
                banglaDay = banglaDay + 1;

            }
        } else if (strMonth.equals("February") && day > 13) {
            Month = context.getString(R.string.falgun); //ফাল্গুন
            banglaYear = banglaYear - 1;
            dayNumber = 14;
            banglaDay = 1;
            for (i = dayNumber; i < day; i++) {
                banglaDay = banglaDay + 1;
            }
        } else if (strMonth.equals("March") && day <= 14) {
            Month = context.getString(R.string.falgun2); //ফাল্গুন
            banglaYear = banglaYear - 1;
            dayNumber = 1;
            banglaDay = 15;

            for (i = dayNumber; i < day; i++) {
                banglaDay = banglaDay + 1;
                if (((year % 400 == 0) || (year % 100 != 0) && (year % 4 == 0))) {
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
            Month = context.getString(R.string.choitro); //চৈত্র
            banglaYear = banglaYear - 1;
            dayNumber = 15;
            banglaDay = 1;
            for (i = dayNumber; i < day; i++) {
                banglaDay = banglaDay + 1;
            }
        }
        return (toBangla(String.valueOf(banglaDay)) + " " + Month + " " + toBangla(String.valueOf(banglaYear)));
    }

    private String toBangla(String input) {
        String[] numbers = {"০", "১", "২", "৩", "৪", "৫", "৬", "৭", "৮", "৯"};
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            if (ch >= '0' && ch <= '9') {
                result.append(numbers[ch - '0']);
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }

    public static String banglaMonth(String englishMonth) {
        String[] banglaMonths = {"জানুয়ারী", "ফেব্রুয়ারী", "মার্চ", "এপ্রিল", "মে", "জুন", "জুলাই", "আগস্ট", "সেপ্টেম্বর", "অক্টোবর", "নভেম্বর", "ডিসেম্বর"};
        String[] englishMonths = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        int index = Arrays.asList(englishMonths).indexOf(englishMonth);
        return banglaMonths[index];
    }

    private String pickArabicDate(Context context) {
        IslamicCalendar c = new IslamicCalendar();
        int year = c.get(IslamicCalendar.YEAR);
        int month = c.get(IslamicCalendar.MONTH) + 1;
        int day = c.get(IslamicCalendar.DAY_OF_MONTH);

        String strMonth = "";

        if (month == 1) {
            strMonth = context.getString(R.string.muharram); //মুহররম
        } else if (month == 2) {
            strMonth = context.getString(R.string.safar); //সফর
        } else if (month == 3) {
            strMonth = context.getString(R.string.rabi_aual); //রবিউল আউয়াল
        } else if (month == 4) {
            strMonth = context.getString(R.string.rabi_sany); //রবিউস সানি
        } else if (month == 5) {
            strMonth = context.getString(R.string.jama_aual); //জমাদিউল আউয়াল
        } else if (month == 6) {
            strMonth = context.getString(R.string.jama_sany); //জমাদিউস সানি
        } else if (month == 7) {
            strMonth = context.getString(R.string.rajab); //রজব
        } else if (month == 8) {
            strMonth = context.getString(R.string.shaban); //শা'বান
        } else if (month == 9) {
            strMonth = context.getString(R.string.ramadan); //রমজান
        } else if (month == 10) {
            strMonth = context.getString(R.string.shaoal); //শাওয়াল
        } else if (month == 11) {
            strMonth = context.getString(R.string.zilqad); //জ্বিলকদ
        } else if (month == 12) {
            strMonth = context.getString(R.string.zilhaz); //জ্বিলহজ্জ
        }

        return (toBangla(String.valueOf(day)) + " " + strMonth + " " + toBangla(String.valueOf(year)));
    }

}

