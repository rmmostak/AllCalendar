import 'package:flutter_riverpod/flutter_riverpod.dart';

import '../../core/date_conversion/bangla_date_converter.dart';
import '../../core/date_conversion/hijri_converter.dart';
import '../../core/date_conversion/models/bangla_date.dart';
import '../../core/date_conversion/models/hijri_date.dart';
import '../../core/settings/settings_provider.dart';

class DateConverterState {
  final DateTime? pickedDate;
  final BanglaDate? bangla;
  final HijriDate? hijri;

  const DateConverterState({this.pickedDate, this.bangla, this.hijri});
}

class DateConverterController extends Notifier<DateConverterState> {
  @override
  DateConverterState build() => const DateConverterState();

  void convert(DateTime date) {
    final hijriMethod = ref.read(settingsProvider).hijriMethod;
    state = DateConverterState(
      pickedDate: date,
      bangla: gregorianToBangla(date),
      hijri: gregorianToHijri(date, hijriMethod),
    );
  }
}

final dateConverterControllerProvider =
    NotifierProvider.autoDispose<DateConverterController, DateConverterState>(
  DateConverterController.new,
);
