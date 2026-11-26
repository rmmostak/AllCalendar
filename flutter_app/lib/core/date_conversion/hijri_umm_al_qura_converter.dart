import 'package:hijri_date_time/hijri_date_time.dart';

import 'models/hijri_date.dart';

/// Converts a Gregorian [date] to the Hijri calendar using Umm al-Qura data
/// (the Saudi civil calendar) -- the "ummAlQura" HijriMethod option, and the
/// closest match to what most Islamic-date apps/websites show today.
///
/// Valid for dates within 1356-1500 AH (1937-2077 CE), which covers all
/// realistic use of this app.
HijriDate gregorianToHijriUmmAlQura(DateTime date) {
  final h = HijriDateTime.fromGregorian(date);
  return HijriDate(year: h.year, month: h.month, day: h.day);
}
