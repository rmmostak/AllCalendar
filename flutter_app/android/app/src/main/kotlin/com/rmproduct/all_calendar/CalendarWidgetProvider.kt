package com.rmproduct.all_calendar

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.SharedPreferences
import android.icu.util.IslamicCalendar
import android.os.Bundle
import android.util.TypedValue
import android.widget.RemoteViews
import es.antonborri.home_widget.HomeWidgetLaunchIntent
import es.antonborri.home_widget.HomeWidgetPlugin
import es.antonborri.home_widget.HomeWidgetProvider
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import android.icu.util.Calendar as IcuCalendar

/**
 * Renders today's date entirely natively (Gregorian, Bangla, Hijri), porting
 * the same algorithms as core/date_conversion in Dart. Unlike the previous
 * version, this widget does NOT depend on the Flutter engine or app process
 * being alive to refresh: it recomputes "today" on every periodic tick
 * (calendar_widget_info.xml's updatePeriodMillis) and every resize, so it
 * can't go stale for days the way a cached-string snapshot would. The only
 * thing still read from Dart-saved widget data is the user's Hijri-method
 * preference (see lib/services/home_widget_service.dart).
 */
class CalendarWidgetProvider : HomeWidgetProvider() {

    /** The [calendar_widget.xml] text sizes (sp) at the smallest supported widget size. */
    private companion object {
        const val BASE_WIDTH_DP = 180f
        const val BASE_HEIGHT_DP = 60f
        const val BASE_DAY_SP = 13f
        const val BASE_DATE_SP = 11f
        const val MAX_SCALE = 3f

        val BANGLA_DIGITS = charArrayOf('০', '১', '২', '৩', '৪', '৫', '৬', '৭', '৮', '৯')

        // Index 0 = Boishakh .. 11 = Chaitra, matches Dart's banglaMonthNames.
        val BANGLA_MONTH_NAMES = arrayOf(
            "বৈশাখ", "জ্যৈষ্ঠ", "আষাঢ়", "শ্রাবণ", "ভাদ্র", "আশ্বিন",
            "কার্তিক", "অগ্রহায়ণ", "পৌষ", "মাঘ", "ফাল্গুন", "চৈত্র",
        )

        // Index 0 = Muharram .. 11 = Dhul Hijjah, matches Dart's hijriMonthNames.
        val HIJRI_MONTH_NAMES = arrayOf(
            "মুহররম", "সফর", "রবিউল আউয়াল", "রবিউস সানি", "জমাদিউল আউয়াল",
            "জমাদিউস সানি", "রজব", "শাবান", "রমজান", "শাওয়াল", "জিলকদ", "জিলহজ্জ",
        )

        // Monday..Sunday, matches Dart's banglaDayNames.
        val BANGLA_DAY_NAMES = arrayOf(
            "সোমবার", "মঙ্গলবার", "বুধবার", "বৃহস্পতিবার", "শুক্রবার", "শনিবার", "রবিবার",
        )
    }

    private data class BanglaDate(val year: Int, val monthIndex: Int, val day: Int)

    private data class HijriDate(val year: Int, val month: Int, val day: Int)

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray,
        widgetData: SharedPreferences,
    ) {
        appWidgetIds.forEach { widgetId ->
            updateWidget(context, appWidgetManager, widgetId, widgetData)
        }
    }

    override fun onAppWidgetOptionsChanged(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int,
        newOptions: Bundle,
    ) {
        updateWidget(context, appWidgetManager, appWidgetId, HomeWidgetPlugin.getData(context))
    }

    private fun updateWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        widgetId: Int,
        widgetData: SharedPreferences,
    ) {
        // Scale text up as the user resizes the widget larger than its minimum
        // size, so a big widget doesn't end up with tiny, wasted-space text.
        val options = appWidgetManager.getAppWidgetOptions(widgetId)
        val width = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH, BASE_WIDTH_DP.toInt())
        val height = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT, BASE_HEIGHT_DP.toInt())
        val scale = minOf(width / BASE_WIDTH_DP, height / BASE_HEIGHT_DP).coerceIn(1f, MAX_SCALE)

        val today = Calendar.getInstance()
        val year = today.get(Calendar.YEAR)
        val month = today.get(Calendar.MONTH) + 1
        val day = today.get(Calendar.DAY_OF_MONTH)

        val bangla = gregorianToBangla(year, month, day)
        val useUmmAlQura = widgetData.getString("hijriMethod", "ummAlQura") != "civilTabular"
        val hijri = gregorianToHijri(today.timeInMillis, useUmmAlQura)
        val dayName = BANGLA_DAY_NAMES[(today.get(Calendar.DAY_OF_WEEK) + 5) % 7]
        val englishDate = SimpleDateFormat("MMM d, yyyy", Locale.US).format(today.time)

        val views = RemoteViews(context.packageName, R.layout.calendar_widget).apply {
            setTextViewText(R.id.widget_day, dayName)
            setTextViewText(
                R.id.widget_bangla_date,
                "${toBanglaNumerals(bangla.day)} ${BANGLA_MONTH_NAMES[bangla.monthIndex]} " +
                    toBanglaNumerals(bangla.year),
            )
            setTextViewText(
                R.id.widget_arabic_date,
                "${toBanglaNumerals(hijri.day)} ${HIJRI_MONTH_NAMES[hijri.month - 1]} " +
                    toBanglaNumerals(hijri.year),
            )
            setTextViewText(R.id.widget_english_date, englishDate)

            setTextViewTextSize(R.id.widget_day, TypedValue.COMPLEX_UNIT_SP, BASE_DAY_SP * scale)
            setTextViewTextSize(R.id.widget_bangla_date, TypedValue.COMPLEX_UNIT_SP, BASE_DATE_SP * scale)
            setTextViewTextSize(R.id.widget_arabic_date, TypedValue.COMPLEX_UNIT_SP, BASE_DATE_SP * scale)
            setTextViewTextSize(R.id.widget_english_date, TypedValue.COMPLEX_UNIT_SP, BASE_DATE_SP * scale)

            val pendingIntent = HomeWidgetLaunchIntent.getActivity(context, MainActivity::class.java)
            setOnClickPendingIntent(R.id.widget_root, pendingIntent)
        }

        appWidgetManager.updateAppWidget(widgetId, views)
    }

    private fun isGregorianLeapYear(year: Int): Boolean =
        (year % 4 == 0 && year % 100 != 0) || year % 400 == 0

    /** Julian Day Number for a proleptic-Gregorian calendar date (timezone-safe, integer-only). */
    private fun gregorianToJDN(year: Int, month: Int, day: Int): Long {
        val a = (14 - month) / 12
        val y2 = year + 4800 - a
        val m2 = month + 12 * a - 3
        return day + (153L * m2 + 2) / 5 + 365L * y2 + y2 / 4 - y2 / 100 + y2 / 400 - 32045
    }

    /**
     * Ports core/date_conversion/bangla_date_converter.dart's Bangla Academy
     * 2019-reform algorithm: 1 Boishakh fixed at April 14 every Gregorian
     * year, with fixed month lengths except a leap-dependent Falgun.
     */
    private fun gregorianToBangla(year: Int, month: Int, day: Int): BanglaDate {
        val newYearThisGregYearJdn = gregorianToJDN(year, 4, 14)
        val gJdn = gregorianToJDN(year, month, day)

        val banglaYear: Int
        val epochYear: Int
        val epochJdn: Long
        if (gJdn >= newYearThisGregYearJdn) {
            banglaYear = year - 593
            epochYear = year
            epochJdn = newYearThisGregYearJdn
        } else {
            banglaYear = year - 594
            epochYear = year - 1
            epochJdn = gregorianToJDN(year - 1, 4, 14)
        }

        val dayOffset = (gJdn - epochJdn).toInt()

        val falgunGregorianYear = epochYear + 1
        val falgunLength = if (isGregorianLeapYear(falgunGregorianYear)) 30 else 29
        val monthLengths = intArrayOf(31, 31, 31, 31, 31, 31, 30, 30, 30, 30, falgunLength, 30)

        var remaining = dayOffset
        var monthIndex = 0
        while (remaining >= monthLengths[monthIndex]) {
            remaining -= monthLengths[monthIndex]
            monthIndex++
        }

        return BanglaDate(year = banglaYear, monthIndex = monthIndex, day = remaining + 1)
    }

    /**
     * Delegates to Android's built-in ICU Islamic calendar, matching what
     * core/date_conversion's Dart converters already document as their
     * reference implementation for both Hijri methods.
     */
    private fun gregorianToHijri(timeInMillis: Long, useUmmAlQura: Boolean): HijriDate {
        val islamic = IslamicCalendar()
        islamic.setCalculationType(
            if (useUmmAlQura) {
                IslamicCalendar.CalculationType.ISLAMIC_UMALQURA
            } else {
                IslamicCalendar.CalculationType.ISLAMIC_CIVIL
            },
        )
        islamic.timeInMillis = timeInMillis
        return HijriDate(
            year = islamic.get(IcuCalendar.YEAR),
            month = islamic.get(IcuCalendar.MONTH) + 1,
            day = islamic.get(IcuCalendar.DAY_OF_MONTH),
        )
    }

    private fun toBanglaNumerals(value: Int): String =
        value.toString().map { ch -> if (ch.isDigit()) BANGLA_DIGITS[ch - '0'] else ch }.joinToString("")
}
