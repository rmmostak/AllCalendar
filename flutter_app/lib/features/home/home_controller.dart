import 'package:flutter/foundation.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

import '../../core/clock_provider.dart';
import '../../core/date_conversion/bangla_date_converter.dart';
import '../../core/date_conversion/hijri_converter.dart';
import '../../core/date_conversion/models/bangla_date.dart';
import '../../core/date_conversion/models/hijri_date.dart';
import '../../core/settings/settings_provider.dart';

@immutable
class TodayDates {
  final DateTime gregorian;
  final BanglaDate bangla;
  final HijriDate hijri;
  final String banglaDayName;

  const TodayDates({
    required this.gregorian,
    required this.bangla,
    required this.hijri,
    required this.banglaDayName,
  });
}

final todayDatesProvider = Provider<TodayDates>((ref) {
  final now = ref.watch(nowProvider);
  final hijriMethod = ref.watch(settingsProvider).hijriMethod;

  return TodayDates(
    gregorian: now,
    bangla: gregorianToBangla(now),
    hijri: gregorianToHijri(now, hijriMethod),
    banglaDayName: banglaDayName(now),
  );
});
