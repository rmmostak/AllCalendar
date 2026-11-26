import '../settings/app_settings.dart';
import 'hijri_tabular_converter.dart';
import 'hijri_umm_al_qura_converter.dart';
import 'models/hijri_date.dart';

/// Converts a Gregorian [date] to Hijri using whichever [method] the user
/// has selected in Settings.
HijriDate gregorianToHijri(DateTime date, HijriMethod method) {
  return switch (method) {
    HijriMethod.ummAlQura => gregorianToHijriUmmAlQura(date),
    HijriMethod.civilTabular => gregorianToHijriCivil(date),
  };
}
