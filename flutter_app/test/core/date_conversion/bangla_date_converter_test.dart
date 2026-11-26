import 'package:all_calendar/core/date_conversion/bangla_date_converter.dart';
import 'package:all_calendar/core/date_conversion/models/bangla_date.dart';
import 'package:flutter_test/flutter_test.dart';

void main() {
  group('gregorianToBangla', () {
    test('Ekushey February 2024 (leap year) is 8 Falgun 1430', () {
      final result = gregorianToBangla(DateTime(2024, 2, 21));
      expect(result, const BanglaDate(year: 1430, monthIndex: 10, day: 8));
    });

    test('April 14 is always 1 Boishakh, new Bangla year', () {
      final result = gregorianToBangla(DateTime(2026, 4, 14));
      expect(result, const BanglaDate(year: 1433, monthIndex: 0, day: 1));
    });

    test('April 13 is the last day of Chaitra, previous Bangla year', () {
      final result = gregorianToBangla(DateTime(2026, 4, 13));
      expect(result, const BanglaDate(year: 1432, monthIndex: 11, day: 30));
    });

    test('April 13 in a year following a Falgun-leap year is still 30 Chaitra', () {
      // 2024 was a Falgun-leap year (Falgun length 30), but Chaitra is always
      // 30 days regardless -- confirms month lengths past Falgun are unaffected.
      final result = gregorianToBangla(DateTime(2025, 4, 13));
      expect(result.monthIndex, 11);
      expect(result.day, 30);
    });

    test('Falgun always starts Feb 14 and Chaitra always starts Mar 15, but Falgun is 29 or 30 days depending on leap year', () {
      // Falgun of Bangla year N falls in Jan-Mar of Gregorian (N+594), and
      // always starts Feb 14 regardless of leap-ness. The reform's Falgun
      // elasticity (29 vs 30 days) exactly absorbs the Gregorian leap day,
      // so Chaitra 1 -- and every date afterwards, including next year's
      // Boishakh 1 -- lands on the same Gregorian date every year. Only the
      // day *number* within Falgun differs between a leap and non-leap year.
      expect(gregorianToBangla(DateTime(2024, 2, 14)), const BanglaDate(year: 1430, monthIndex: 10, day: 1));
      expect(gregorianToBangla(DateTime(2025, 2, 14)), const BanglaDate(year: 1431, monthIndex: 10, day: 1));

      // Bangla 1430 -> Falgun falls in 2024 (leap) -> 30 days -> Mar 14 is
      // Falgun's 30th (last) day.
      expect(gregorianToBangla(DateTime(2024, 3, 14)), const BanglaDate(year: 1430, monthIndex: 10, day: 30));
      // Bangla 1431 -> Falgun falls in 2025 (not leap) -> 29 days -> Mar 14
      // is Falgun's 29th (last) day instead.
      expect(gregorianToBangla(DateTime(2025, 3, 14)), const BanglaDate(year: 1431, monthIndex: 10, day: 29));

      // Both years: Chaitra 1 is Mar 15, unaffected by the leap-year difference.
      expect(gregorianToBangla(DateTime(2024, 3, 15)), const BanglaDate(year: 1430, monthIndex: 11, day: 1));
      expect(gregorianToBangla(DateTime(2025, 3, 15)), const BanglaDate(year: 1431, monthIndex: 11, day: 1));
    });

    test('first day of Boishakh has correct month name', () {
      final result = gregorianToBangla(DateTime(2025, 4, 14));
      expect(result.monthName, 'বৈশাখ');
    });

    test('last day of the Bangla year rolls into the next year the following day', () {
      final lastDay = gregorianToBangla(DateTime(2025, 4, 13));
      final firstDayNext = gregorianToBangla(DateTime(2025, 4, 14));
      expect(firstDayNext.year, lastDay.year + 1);
      expect(firstDayNext.monthIndex, 0);
      expect(firstDayNext.day, 1);
    });
  });

  group('banglaDayName', () {
    test('matches DateTime.weekday mapping', () {
      // 2026-07-20 is a Monday.
      expect(banglaDayName(DateTime(2026, 7, 20)), 'সোমবার');
      // 2026-07-26 is a Sunday.
      expect(banglaDayName(DateTime(2026, 7, 26)), 'রবিবার');
    });
  });
}
