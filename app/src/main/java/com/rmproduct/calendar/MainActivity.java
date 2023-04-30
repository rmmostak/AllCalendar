package com.rmproduct.calendar;

import static com.rmproduct.calendar.CalendarWidget.banglaMonth;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.res.Resources;
import android.icu.util.IslamicCalendar;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextView banglaDate, englishDate, arabicDate, day;
    //in app update
    private int MY_REQUEST_CODE = 111;
    private AppUpdateManager mAppUpdateManager;
    private final int RC_APP_UPDATE = 999;
    private int inAppUpdateType;
    private Task<AppUpdateInfo> appUpdateInfoTask;
    private InstallStateUpdatedListener installStateUpdatedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*start of in app update functions*/

        AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(MainActivity.this);
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
        appUpdateInfoTask.addOnSuccessListener(result -> {
            if (result.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && result.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                try {
                    appUpdateManager.startUpdateFlowForResult(
                            result,
                            AppUpdateType.IMMEDIATE,
                            MainActivity.this,
                            MY_REQUEST_CODE
                    );
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
            }
        });

        /*end of in app update function*/

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


        day.setText(getString(R.string.day) + " " + pickBanglaDay());

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

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("MMMM");
        String strMonth = formatter.format(date);

        return convertToBanglaNumber(String.valueOf(day)) + " " + banglaMonth(strMonth) + " " + convertToBanglaNumber(String.valueOf(year));
    }

    private String pickBanglaDay() {

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("E", Locale.getDefault());
        String strDay = formatter.format(date);
        String strBanglaDay = "";

        if (strDay.equals("Sat")) {
            strBanglaDay = getString(R.string.sat);
        } else if (strDay.equals("Sun")) {
            strBanglaDay = getString(R.string.sun);
        } else if (strDay.equals("Mon")) {
            strBanglaDay = getString(R.string.mon);
        } else if (strDay.equals("Tue")) {
            strBanglaDay = getString(R.string.tue);
        } else if (strDay.equals("Wed")) {
            strBanglaDay = getString(R.string.wed);
        } else if (strDay.equals("Thu")) {
            strBanglaDay = getString(R.string.thu);
        } else if (strDay.equals("Fri")) {
            strBanglaDay = getString(R.string.fri);
        }

        return strBanglaDay;
    }

    private String convertToBanglaNumber(String input) {
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
            Month = getString(R.string.april); //চৈত্র
            banglaYear = banglaYear - 1;
            dayNumber = 1;
            banglaDay = 14;
            for (i = dayNumber; i > day; i++) {
                banglaDay = banglaDay + 1;
            }
        } else if (strMonth.equals("April") && day > 13) {
            Month = getString(R.string.boishakh); //বৈশাখ
            dayNumber = 14;
            banglaDay = 1;
            for (i = dayNumber; i < day; i++) {
                banglaDay = banglaDay + 1;
            }
        } else if (strMonth.equals("May") && day <= 14) {
            Month = getString(R.string.boishakh2); //বৈশাখ
            dayNumber = 1;
            banglaDay = 15;
            for (i = dayNumber; i > day; i++) {
                banglaDay = banglaDay + 1;

            }
        } else if (strMonth.equals("May") && day > 14) {
            Month = getString(R.string.jaistho); //জৈষ্ঠ্য
            dayNumber = 15;
            banglaDay = 1;
            for (i = dayNumber; i < day; i++) {
                banglaDay = banglaDay + 1;
            }
        } else if (strMonth.equals("June") && day <= 14) {
            Month = getString(R.string.jaistho2); //জৈষ্ঠ্য
            dayNumber = 1;
            banglaDay = 15;
            for (i = dayNumber; i > day; i++) {
                banglaDay = banglaDay + 1;
            }
        } else if (strMonth.equals("June") && day > 14) {
            Month = getString(R.string.ashar); //আষাঢ়
            dayNumber = 15;
            banglaDay = 1;
            for (i = dayNumber; i < day; i++) {
                banglaDay = banglaDay + 1;
            }
        } else if (strMonth.equals("July") && day <= 15) {

            Month = getString(R.string.ashar2); //আষাঢ়
            dayNumber = 1;
            banglaDay = 16;
            for (i = dayNumber; i < day; i++) {
                banglaDay = banglaDay + day;
            }

        } else if (strMonth.equals("July") && day > 15) {
            Month = getString(R.string.srabon); //শ্রাবণ
            dayNumber = 16;
            banglaDay = 1;
            for (i = dayNumber; i < day; i++) {
                banglaDay = banglaDay + 1;
            }
        } else if (strMonth.equals("August") && day <= 15) {
            dayNumber = 1;
            banglaDay = 16;
            Month = getString(R.string.srabon2); //শ্রাবণ

            for (i = dayNumber; i <= day; i++) {
                banglaDay = banglaDay + 1;
            }
        } else if (strMonth.equals("August") && day > 15) {
            dayNumber = 16;
            banglaDay = 1;
            Month = getString(R.string.vadro); //ভাদ্র

            for (i = dayNumber; i < day; i++) {
                banglaDay = banglaDay + 1;
            }
        } else if (strMonth.equals("September") && day <= 15) {
            Month = getString(R.string.vadro2); //ভাদ্র
            dayNumber = 1;
            banglaDay = 16;

            for (i = dayNumber; i < day; i++) {
                banglaDay = banglaDay + 1;

            }
        } else if (strMonth.equals("September") && day > 15) {
            Month = getString(R.string.ashwin); //আশ্বিন
            dayNumber = 16;
            banglaDay = 1;

            for (i = dayNumber; i < day; i++) {
                banglaDay = banglaDay + 1;

            }
        } else if (strMonth.equals("October") && day <= 16) {
            Month = getString(R.string.ashwin2); //আশ্বিন

            dayNumber = 1;
            banglaDay = 17;
            for (i = dayNumber; i < day; i++) {
                banglaDay = banglaDay + 1;

            }
        } else if (strMonth.equals("October") && day > 16) {
            Month = getString(R.string.kartik); //কার্ত্তিক
            dayNumber = 17;
            banglaDay = 1;
            for (i = dayNumber; i < day; i++) {
                banglaDay = banglaDay + 1;
            }
        } else if (strMonth.equals("November") && day <= 15) {
            Month = getString(R.string.kartik2); //কার্ত্তিক
            dayNumber = 1;
            banglaDay = 16;
            for (i = dayNumber; i < day; i++) {
                banglaDay = banglaDay + 1;

            }

        } else if (strMonth.equals("November") && day > 15) {
            Month = getString(R.string.agun); //অগ্রহায়ণ
            dayNumber = 16;
            banglaDay = 1;
            for (i = dayNumber; i < day; i++) {
                banglaDay = banglaDay + 1;
            }
        } else if (strMonth.equals("December") && day <= 15) {

            Month = getString(R.string.agun2); //অগ্রহায়ণ
            dayNumber = 1;
            banglaDay = 16;
            for (i = dayNumber; i < day; i++) {
                banglaDay = banglaDay + 1;
            }
        } else if (strMonth.equals("December") && day > 15) {
            Month = getString(R.string.poush); //পৌষ
            dayNumber = 16;
            banglaDay = 1;
            for (i = dayNumber; i < day; i++) {
                banglaDay = banglaDay + 1;
            }
        } else if (strMonth.equals("January") && day <= 14) {
            Month = getString(R.string.poush2); //পৌষ
            banglaYear = banglaYear - 1;
            dayNumber = 1;
            banglaDay = 17; //for 15
            for (i = dayNumber; i < day; i++) {
                banglaDay = banglaDay + 1;

            }
        } else if (strMonth.equals("January") && day > 14) {
            Month = getString(R.string.magh); //মাঘ
            banglaYear = banglaYear - 1;
            dayNumber = 15;
            banglaDay = 1;
            for (i = dayNumber; i < day; i++) {
                banglaDay = banglaDay + 1;
            }
        } else if (strMonth.equals("February") && day <= 13) {
            Month = getString(R.string.magh2); //মাঘ
            banglaYear = banglaYear - 1;
            dayNumber = 1;
            banglaDay = 14;
            for (i = dayNumber; i < day; i++) {
                banglaDay = banglaDay + 1;

            }
        } else if (strMonth.equals("February") && day > 13) {
            Month = getString(R.string.falgun); //ফাল্গুন
            banglaYear = banglaYear - 1;
            dayNumber = 14;
            banglaDay = 1;
            for (i = dayNumber; i < day; i++) {
                banglaDay = banglaDay + 1;
            }
        } else if (strMonth.equals("March") && day <= 14) {
            Month = getString(R.string.falgun2); //ফাল্গুন
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
            Month = getString(R.string.choitro); //চৈত্র
            banglaYear = banglaYear - 1;
            dayNumber = 15;
            banglaDay = 1;
            for (i = dayNumber; i < day; i++) {
                banglaDay = banglaDay + 1;
            }
        }

        return ((convertToBanglaNumber(String.valueOf(banglaDay))) + " " + Month + " " + (convertToBanglaNumber(String.valueOf(banglaYear))));
    }

    private String pickArabicDate() {
        IslamicCalendar c = new IslamicCalendar();
        int year = c.get(IslamicCalendar.YEAR);
        int month = c.get(IslamicCalendar.MONTH) + 1;
        int day = c.get(IslamicCalendar.DAY_OF_MONTH);

        String strMonth = "";

        if (month == 1) {
            strMonth = getString(R.string.muharram); //মুহররম
        } else if (month == 2) {
            strMonth = getString(R.string.safar); //সফর
        } else if (month == 3) {
            strMonth = getString(R.string.rabi_aual); //রবিউল আউয়াল
        } else if (month == 4) {
            strMonth = getString(R.string.rabi_sany); //রবিউস সানি
        } else if (month == 5) {
            strMonth = getString(R.string.jama_aual); //জমাদিউল আউয়াল
        } else if (month == 6) {
            strMonth = getString(R.string.jama_sany); //জমাদিউস সানি
        } else if (month == 7) {
            strMonth = getString(R.string.rajab); //রজব
        } else if (month == 8) {
            strMonth = getString(R.string.shaban); //শা'বান
        } else if (month == 9) {
            strMonth = getString(R.string.ramadan); //রমজান
        } else if (month == 10) {
            strMonth = getString(R.string.shaoal); //শাওয়াল
        } else if (month == 11) {
            strMonth = getString(R.string.zilqad); //জ্বিলকদ
        } else if (month == 12) {
            strMonth = getString(R.string.zilhaz); //জ্বিলহজ্জ
        }

        return (convertToBanglaNumber(String.valueOf(day)) + " " + strMonth + " " + convertToBanglaNumber(String.valueOf(year)));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_REQUEST_CODE) {
            Log.d("status", "Download started");
            popupSnackBarForCompleteUpdate();
            if (resultCode != RESULT_OK) {
                Log.d("result status", "download failed");
            }
        }
    }

    private void popupSnackBarForCompleteUpdate() {
        try {
            Snackbar snackbar =
                    Snackbar.make(
                            findViewById(R.id.coordinator),
                            "An update has just been downloaded.\nRestart to update",
                            Snackbar.LENGTH_INDEFINITE);

            snackbar.setAction("INSTALL", v -> {
                if (mAppUpdateManager != null) {
                    mAppUpdateManager.completeUpdate();
                }
            });
            snackbar.setActionTextColor(getResources().getColor(R.color.colorPrimaryDark));
            snackbar.show();

        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
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
            String shareBody = "https://play.google.com/store/apps/details?id=com.rmproduct.calendar";
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

}
