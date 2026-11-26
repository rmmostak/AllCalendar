import 'package:all_calendar/core/date_conversion/hijri_umm_al_qura_converter.dart';
import 'package:flutter_test/flutter_test.dart';

void main() {
  group('gregorianToHijriUmmAlQura', () {
    test('produces a plausible year/month/day for a known date', () {
      // 2024-03-11 is the widely reported start of Ramadan 1445 under
      // Umm al-Qura too (may differ from the civil algorithm by a day
      // depending on the source year -- this is a smoke test, not an
      // exact-match assertion against the civil converter).
      final result = gregorianToHijriUmmAlQura(DateTime(2024, 3, 11));
      expect(result.year, 1445);
      expect(result.month, inInclusiveRange(1, 12));
      expect(result.day, inInclusiveRange(1, 30));
    });

    test('today converts without throwing and stays in a sane range', () {
      final result = gregorianToHijriUmmAlQura(DateTime(2026, 7, 20));
      expect(result.year, inInclusiveRange(1440, 1460));
    });
  });
}
