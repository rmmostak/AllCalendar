import 'package:home_widget/home_widget.dart';
import 'package:intl/intl.dart';

import '../core/date_conversion/bangla_date_converter.dart';
import '../core/date_conversion/bangla_numerals.dart';
import '../core/date_conversion/hijri_converter.dart';
import '../core/settings/app_settings.dart';

/// Pushes today's dates (computed once, in Dart, from the same
/// core/date_conversion functions the app itself uses) to the native
/// homescreen widgets. The native widget providers only render the last
/// values saved here -- they do not recompute dates themselves.
class HomeWidgetService {
  static const androidProviderName = 'CalendarWidgetProvider';
  static const iOSWidgetName = 'CalendarWidget';

  /// Must match the App Group configured on both the Runner and
  /// CalendarWidgetExtension iOS targets (Signing & Capabilities).
  static const iOSAppGroupId = 'group.com.rmproduct.allcalendar.widget';

  bool _appGroupConfigured = false;

  Future<void> _ensureAppGroupConfigured() async {
    if (_appGroupConfigured) return;
    await HomeWidget.setAppGroupId(iOSAppGroupId);
    _appGroupConfigured = true;
  }

  Future<void> updateWidget(HijriMethod hijriMethod) async {
    await _ensureAppGroupConfigured();
    final now = DateTime.now();
    final bangla = gregorianToBangla(now);
    final hijri = gregorianToHijri(now, hijriMethod);

    // Read by CalendarWidgetProvider.kt, which recomputes Hijri dates natively
    // (via android.icu) rather than relying on this cached-strings snapshot,
    // so the Android widget stays correct on its own periodic refresh even
    // when the app hasn't been opened in days.
    await HomeWidget.saveWidgetData<String>('hijriMethod', hijriMethod.name);

    await HomeWidget.saveWidgetData<String>('dayName', banglaDayName(now));
    await HomeWidget.saveWidgetData<String>(
      'banglaDate',
      '${toBanglaNumerals(bangla.day)} ${bangla.monthName} ${toBanglaNumerals(bangla.year)}',
    );
    await HomeWidget.saveWidgetData<String>(
      'hijriDate',
      '${toBanglaNumerals(hijri.day)} ${hijri.monthName} ${toBanglaNumerals(hijri.year)}',
    );
    await HomeWidget.saveWidgetData<String>(
      'englishDate',
      DateFormat.yMMMd().format(now),
    );

    await HomeWidget.updateWidget(
      androidName: androidProviderName,
      iOSName: iOSWidgetName,
    );
  }
}
