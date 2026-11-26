import 'models/bangla_date.dart';

bool isGregorianLeapYear(int year) {
  return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
}

DateTime _dateOnly(DateTime d) => DateTime(d.year, d.month, d.day);

/// Converts a Gregorian [date] to its Bangla calendar equivalent, using the
/// Bangla Academy's 2019 calendar reform: Pohela Boishakh (1 Boishakh) is
/// fixed at April 14 every Gregorian year, with fixed month lengths (31 days
/// for Boishakh-Ashwin, 30 days for Kartik/Agrahayan/Poush/Magh/Chaitra, and
/// Falgun at 29 or 30 days depending on whether the following Gregorian
/// February is a leap month).
BanglaDate gregorianToBangla(DateTime date) {
  final g = _dateOnly(date);
  final newYearThisGregYear = DateTime(g.year, 4, 14);

  late final int banglaYear;
  late final DateTime epoch;
  if (!g.isBefore(newYearThisGregYear)) {
    banglaYear = g.year - 593;
    epoch = newYearThisGregYear;
  } else {
    banglaYear = g.year - 594;
    epoch = DateTime(g.year - 1, 4, 14);
  }

  final dayOffset = g.difference(epoch).inDays; // 0-based; 0 == 1 Boishakh

  // Falgun (index 10, the 11th month) of this Bangla year always falls in
  // Jan-Mar of epoch.year + 1.
  final falgunGregorianYear = epoch.year + 1;
  final falgunLength = isGregorianLeapYear(falgunGregorianYear) ? 30 : 29;
  final monthLengths = [
    31, 31, 31, 31, 31, 31, // Boishakh..Ashwin
    30, 30, 30, 30, // Kartik, Agrahayan, Poush, Magh
    falgunLength, // Falgun
    30, // Chaitra
  ];

  var remaining = dayOffset;
  var monthIndex = 0;
  while (remaining >= monthLengths[monthIndex]) {
    remaining -= monthLengths[monthIndex];
    monthIndex++;
  }

  return BanglaDate(year: banglaYear, monthIndex: monthIndex, day: remaining + 1);
}

/// Bengali day-of-week name for [date], derived from [DateTime.weekday]
/// rather than a locale-formatted string (which would depend on device locale).
String banglaDayName(DateTime date) => banglaDayNames[date.weekday - 1];
