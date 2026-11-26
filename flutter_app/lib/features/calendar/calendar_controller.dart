import 'package:flutter/foundation.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

import '../../core/date_conversion/bangla_date_converter.dart';
import '../../core/date_conversion/hijri_converter.dart';
import '../../core/date_conversion/models/bangla_date.dart';
import '../../core/date_conversion/models/hijri_date.dart';
import '../../core/settings/app_settings.dart';
import '../../core/settings/settings_provider.dart';

@immutable
class CalendarDay {
  final DateTime gregorian;
  final BanglaDate bangla;
  final HijriDate hijri;

  const CalendarDay({
    required this.gregorian,
    required this.bangla,
    required this.hijri,
  });
}

@immutable
class MonthCalendar {
  /// First day of the displayed Gregorian month.
  final DateTime month;

  /// Full weeks (multiples of 7), Friday-first. Null entries pad the leading
  /// and trailing days of the grid that fall outside [month].
  final List<CalendarDay?> days;

  const MonthCalendar({required this.month, required this.days});

  /// e.g. "আষাঢ়-শ্রাবণ" when the Gregorian month spans two Bangla months.
  String get banglaMonthRange =>
      _monthRange(days.map((d) => d?.bangla.monthName));

  /// e.g. "মহররম-সফর" when the Gregorian month spans two Hijri months.
  String get hijriMonthRange =>
      _monthRange(days.map((d) => d?.hijri.monthName));

  static String _monthRange(Iterable<String?> monthNames) {
    final ordered = <String>[];
    for (final name in monthNames) {
      if (name != null && (ordered.isEmpty || ordered.last != name)) {
        ordered.add(name);
      }
    }
    if (ordered.isEmpty) return '';
    if (ordered.length == 1) return ordered.first;
    return '${ordered.first}-${ordered.last}';
  }
}

/// The Gregorian month currently displayed by the calendar screen.
class CalendarMonthController extends Notifier<DateTime> {
  @override
  DateTime build() {
    final now = DateTime.now();
    return DateTime(now.year, now.month);
  }

  void changeBy(int months) {
    state = DateTime(state.year, state.month + months);
  }
}

final calendarMonthProvider =
    NotifierProvider<CalendarMonthController, DateTime>(
  CalendarMonthController.new,
);

/// Friday-first weekday index for [date]: 0=Friday .. 6=Thursday. Matches the
/// Bangladeshi week (Friday-Saturday weekend) used by the calendar grid.
int fridayFirstWeekdayIndex(DateTime date) => (date.weekday + 2) % 7;

final monthCalendarProvider = Provider<MonthCalendar>((ref) {
  final month = ref.watch(calendarMonthProvider);
  final hijriMethod = ref.watch(settingsProvider).hijriMethod;

  final firstDay = DateTime(month.year, month.month);
  final daysInMonth = DateTime(month.year, month.month + 1, 0).day;
  final leadingBlanks = fridayFirstWeekdayIndex(firstDay);

  final days = <CalendarDay?>[
    for (var i = 0; i < leadingBlanks; i++) null,
    for (var d = 1; d <= daysInMonth; d++)
      _buildDay(DateTime(month.year, month.month, d), hijriMethod),
  ];
  while (days.length % 7 != 0) {
    days.add(null);
  }

  return MonthCalendar(month: firstDay, days: days);
});

CalendarDay _buildDay(DateTime date, HijriMethod hijriMethod) {
  return CalendarDay(
    gregorian: date,
    bangla: gregorianToBangla(date),
    hijri: gregorianToHijri(date, hijriMethod),
  );
}
