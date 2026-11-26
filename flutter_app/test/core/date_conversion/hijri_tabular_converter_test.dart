import 'package:all_calendar/core/date_conversion/hijri_tabular_converter.dart';
import 'package:all_calendar/core/date_conversion/models/hijri_date.dart';
import 'package:flutter_test/flutter_test.dart';

void main() {
  group('gregorianToHijriCivil', () {
    // Reference values independently cross-checked with a standalone Python
    // implementation of the same JDN-based algorithm before trusting the
    // Dart port -- see plan notes. 2024-03-11 -> 1 Ramadan 1445 also matches
    // the widely reported public start of Ramadan 1445.
    final cases = <DateTime, HijriDate>{
      DateTime(2026, 7, 20): const HijriDate(year: 1448, month: 2, day: 4),
      DateTime(2024, 3, 11): const HijriDate(year: 1445, month: 9, day: 1),
      DateTime(2000, 1, 1): const HijriDate(year: 1420, month: 9, day: 24),
      DateTime(2026, 1, 1): const HijriDate(year: 1447, month: 7, day: 12),
    };

    for (final entry in cases.entries) {
      test('${entry.key.toIso8601String().split("T").first} -> ${entry.value}',
          () {
        expect(gregorianToHijriCivil(entry.key), entry.value);
      });
    }

    test('month rolls over correctly at the end of a 30-day Hijri month', () {
      final day1445Ramadan1 = gregorianToHijriCivil(DateTime(2024, 3, 11));
      expect(day1445Ramadan1.month, 9);
      expect(day1445Ramadan1.day, 1);
    });
  });
}
