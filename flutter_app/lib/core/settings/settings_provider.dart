import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

import 'app_settings.dart';
import 'settings_repository.dart';

final settingsRepositoryProvider = Provider<SettingsRepository>((ref) {
  return SettingsRepository();
});

class SettingsController extends Notifier<AppSettings> {
  @override
  AppSettings build() {
    _loadInitial();
    return const AppSettings();
  }

  Future<void> _loadInitial() async {
    state = await ref.read(settingsRepositoryProvider).load();
  }

  Future<void> setThemeMode(ThemeMode mode) async {
    state = state.copyWith(themeMode: mode);
    await ref.read(settingsRepositoryProvider).saveThemeMode(mode);
  }

  Future<void> setLocale(Locale locale) async {
    state = state.copyWith(locale: locale);
    await ref.read(settingsRepositoryProvider).saveLocale(locale);
  }

  Future<void> setHijriMethod(HijriMethod method) async {
    state = state.copyWith(hijriMethod: method);
    await ref.read(settingsRepositoryProvider).saveHijriMethod(method);
  }
}

final settingsProvider = NotifierProvider<SettingsController, AppSettings>(
  SettingsController.new,
);
