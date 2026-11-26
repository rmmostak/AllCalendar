import 'dart:math' as math;

import 'models/hijri_date.dart';

/// Julian Day Number of 1 Muharram 1 AH under the civil (tabular, Friday
/// epoch) Islamic calendar -- the same algorithm Android ICU's civil
/// `IslamicCalendar` uses, so results match the previous native app.
const int islamicEpochCivil = 1948440;

int gregorianToJDN(int year, int month, int day) {
  final a = (14 - month) ~/ 12;
  final y2 = year + 4800 - a;
  final m2 = month + 12 * a - 3;
  return day +
      (153 * m2 + 2) ~/ 5 +
      365 * y2 +
      y2 ~/ 4 -
      y2 ~/ 100 +
      y2 ~/ 400 -
      32045;
}

int islamicToJDN(int islamicYear, int islamicMonth, int islamicDay) {
  return islamicDay +
      (29.5 * (islamicMonth - 1)).ceil() +
      (islamicYear - 1) * 354 +
      (3 + 11 * islamicYear) ~/ 30 +
      islamicEpochCivil -
      1;
}

HijriDate jdnToIslamicCivil(int jdn) {
  final islamicYear =
      ((30 * (jdn - islamicEpochCivil) + 10646) / 10631).floor();
  final islamicMonth = math.min(
    12,
    ((jdn - (29 + islamicToJDN(islamicYear, 1, 1))) / 29.5).ceil() + 1,
  );
  final islamicDay = jdn - islamicToJDN(islamicYear, islamicMonth, 1) + 1;
  return HijriDate(year: islamicYear, month: islamicMonth, day: islamicDay);
}

/// Converts a Gregorian [date] to the Hijri calendar using the deterministic
/// civil/tabular algorithm (no moon-sighting data, no lookup table -- this is
/// the "civilTabular" HijriMethod option).
HijriDate gregorianToHijriCivil(DateTime date) {
  return jdnToIslamicCivil(gregorianToJDN(date.year, date.month, date.day));
}
