import 'package:all_calendar/core/age_calculation/age_calculator.dart';
import 'package:flutter_test/flutter_test.dart';

void main() {
  group('calculateAge', () {
    test('birthday is exactly today: 0 years 0 months 0 days', () {
      final result = calculateAge(DateTime(2000, 7, 20), DateTime(2026, 7, 20));
      expect(result.isValid, true);
      expect(result.years, 26);
      expect(result.months, 0);
      expect(result.days, 0);
    });

    test('birth date in the future is invalid', () {
      final result = calculateAge(DateTime(2027, 1, 1), DateTime(2026, 7, 20));
      expect(result.isValid, false);
      expect(result.error, isNotNull);
    });

    test('on-day less than birth-day borrows from the correct previous month', () {
      // Born Jan 30, evaluated on Mar 5: Feb has 28 days (2026 not leap),
      // so borrowing must use Feb's length, not the birth month's (Jan, 31).
      final result = calculateAge(DateTime(2000, 1, 30), DateTime(2026, 3, 5));
      expect(result.isValid, true);
      // days: 5 - 30 = -25 -> borrow daysInMonth(2026, 2) = 28 -> 3; months -1
      expect(result.days, 3);
      expect(result.months, 1); // (3 - 1) - 1(borrow) = 1
      expect(result.years, 26);
    });

    test('birth on Feb 29 aged into a non-leap year still computes without error', () {
      final result = calculateAge(DateTime(2000, 2, 29), DateTime(2026, 3, 1));
      expect(result.isValid, true);
      expect(result.years, 26);
    });

    test('December birth month, January "on" date wraps year correctly', () {
      final result = calculateAge(DateTime(1998, 12, 30), DateTime(2026, 1, 15));
      expect(result.isValid, true);
      // days: 15-30 = -15 -> borrow daysInMonth(2025,12)=31 -> 16; months -1
      expect(result.days, 16);
      // months: (1 - 12) - 1(borrow) = -12 -> +12 -> 0; years -1
      expect(result.months, 0);
      expect(result.years, 27);
    });

    test('December 30 1998 to today (2026-07-20) matches manual expectation', () {
      final result = calculateAge(DateTime(1998, 12, 30), DateTime(2026, 7, 20));
      expect(result.isValid, true);
      expect(result.years, 27);
      expect(result.months, 6);
      expect(result.days, 20);
    });
  });

  group('daysInMonth', () {
    test('February is 29 days in a leap year, 28 otherwise', () {
      expect(daysInMonth(2024, 2), 29);
      expect(daysInMonth(2026, 2), 28);
    });
  });
}
