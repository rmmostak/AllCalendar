import 'package:all_calendar/core/settings/app_settings.dart';
import 'package:all_calendar/core/settings/settings_repository.dart';
import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:shared_preferences/shared_preferences.dart';

void main() {
  setUp(() {
    SharedPreferences.setMockInitialValues({});
  });

  group('SettingsRepository', () {
    test('load returns defaults when nothing persisted yet', () async {
      final repo = SettingsRepository();
      final settings = await repo.load();
      expect(settings.themeMode, ThemeMode.system);
      expect(settings.locale, const Locale('en'));
      expect(settings.hijriMethod, HijriMethod.ummAlQura);
    });

    test('theme mode round-trips through persistence', () async {
      final repo = SettingsRepository();
      await repo.saveThemeMode(ThemeMode.dark);
      final settings = await repo.load();
      expect(settings.themeMode, ThemeMode.dark);
    });

    test('locale round-trips through persistence', () async {
      final repo = SettingsRepository();
      await repo.saveLocale(const Locale('bn'));
      final settings = await repo.load();
      expect(settings.locale, const Locale('bn'));
    });

    test('hijri method round-trips through persistence', () async {
      final repo = SettingsRepository();
      await repo.saveHijriMethod(HijriMethod.civilTabular);
      final settings = await repo.load();
      expect(settings.hijriMethod, HijriMethod.civilTabular);
    });
  });
}
