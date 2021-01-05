package com.rmproduct.calendar;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.icu.util.IslamicCalendar;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import me.grantland.widget.AutofitTextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ForceUpdateChecker.OnUpdateNeededListener {

    private TextView banglaDate, englishDate, arabicDate, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ForceUpdateChecker.with(this).onUpdateNeeded(this).check();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                calendarDialog();

            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        banglaDate = findViewById(R.id.idBanglaDate);
        englishDate = findViewById(R.id.idEnglisDate);
        arabicDate = findViewById(R.id.idArabicDate);
        day = findViewById(R.id.idDay);


        day.setText("AvR " + pickBanglaDay());

        englishDate.setText(pickDate());

        banglaDate.setText(pickBanglaDate());

        arabicDate.setText(pickArabicDate());

    }

    private void calendarDialog() {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.calendar_dialog, null);

        dialogBuilder.setView(dialogView);

        final AlertDialog alertDialog = dialogBuilder.create();

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);

        dialogBuilder.setTitle("Calendar " + year);

        dialogBuilder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        }).show();
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


    @Override
    public void onUpdateNeeded(final String updateUrl) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("New version available")
                .setMessage("Please, update this app and enjoy more functions.")
                .setPositiveButton("Update",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(updateUrl));
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);

                                redirectStore(updateUrl);
                                //Toast.makeText(getApplicationContext(), "This is the link for update", Toast.LENGTH_LONG).show();
                            }
                        }).setNegativeButton("No, thanks",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        }).create();
        dialog.show();
    }

    private void redirectStore(String updateUrl) {
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(updateUrl));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.aboutApp) {

            startActivity(new Intent(MainActivity.this, AboutApp.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.ageCalculation) {
            // Handle the camera action
            startActivity(new Intent(MainActivity.this, AgeCalculation.class));
        } else if (id == R.id.dateConversion) {

            Toast.makeText(getApplicationContext(), "This service is currently not available. Keep using and update app to get more services. Thank you!", Toast.LENGTH_LONG).show();

            //startActivity(new Intent(MainActivity.this, DateConversion.class));

        } else if (id == R.id.share) {

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            String shareBody = "https://rmproduct121.blogspot.com/2020/06/all-calendar-android-app.html";
            String shareSubject = "All Calendar Android App";

            shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubject);
            startActivity(Intent.createChooser(shareIntent, "Share App Using..."));

        } else if (id == R.id.feedback) {

            startActivity(new Intent(MainActivity.this, FeedBack.class));

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
