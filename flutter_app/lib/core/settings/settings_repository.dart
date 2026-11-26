import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';

import 'app_settings.dart';

class SettingsRepository {
  static const _themeModeKey = 'themeMode';
  static const _localeKey = 'locale';
  static const _hijriMethodKey = 'hijriMethod';

  Future<AppSettings> load() async {
    final prefs = await SharedPreferences.getInstance();

    final themeModeName = prefs.getString(_themeModeKey);
    final themeMode = ThemeMode.values.firstWhere(
      (m) => m.name == themeModeName,
      orElse: () => ThemeMode.system,
    );

    final localeCode = prefs.getString(_localeKey);
    final locale = localeCode != null ? Locale(localeCode) : const Locale('en');

    final hijriMethodName = prefs.getString(_hijriMethodKey);
    final hijriMethod = HijriMethod.values.firstWhere(
      (m) => m.name == hijriMethodName,
      orElse: () => HijriMethod.ummAlQura,
    );

    return AppSettings(
      themeMode: themeMode,
      locale: locale,
      hijriMethod: hijriMethod,
    );
  }

  Future<void> saveThemeMode(ThemeMode mode) async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.setString(_themeModeKey, mode.name);
  }

  Future<void> saveLocale(Locale locale) async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.setString(_localeKey, locale.languageCode);
  }

  Future<void> saveHijriMethod(HijriMethod method) async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.setString(_hijriMethodKey, method.name);
  }
}
