import 'package:flutter/material.dart';

/// Which algorithm to use when converting a Gregorian date to Hijri.
enum HijriMethod {
  /// Umm al-Qura (Saudi civil calendar), matches most Islamic-date apps today.
  ummAlQura,

  /// Deterministic civil/tabular arithmetic algorithm, matches the previous
  /// native app's ICU-based output.
  civilTabular,
}

extension HijriMethodLabel on HijriMethod {
  String get label => switch (this) {
        HijriMethod.ummAlQura => 'Umm al-Qura',
        HijriMethod.civilTabular => 'Tabular (civil)',
      };
}

@immutable
class AppSettings {
  final ThemeMode themeMode;
  final Locale locale;
  final HijriMethod hijriMethod;

  const AppSettings({
    this.themeMode = ThemeMode.system,
    this.locale = const Locale('en'),
    this.hijriMethod = HijriMethod.ummAlQura,
  });

  AppSettings copyWith({
    ThemeMode? themeMode,
    Locale? locale,
    HijriMethod? hijriMethod,
  }) {
    return AppSettings(
      themeMode: themeMode ?? this.themeMode,
      locale: locale ?? this.locale,
      hijriMethod: hijriMethod ?? this.hijriMethod,
    );
  }
}
