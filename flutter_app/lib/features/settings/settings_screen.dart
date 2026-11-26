import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

import '../../core/settings/app_settings.dart';
import '../../core/settings/settings_provider.dart';

class SettingsScreen extends ConsumerWidget {
  const SettingsScreen({super.key});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final settings = ref.watch(settingsProvider);
    final controller = ref.read(settingsProvider.notifier);

    return Scaffold(
      appBar: AppBar(title: const Text('Settings')),
      body: SafeArea(
        child: ListView(
          children: [
            const _SectionHeader('Theme'),
            RadioGroup<ThemeMode>(
              groupValue: settings.themeMode,
              onChanged: (v) => controller.setThemeMode(v!),
              child: const Column(
                children: [
                  RadioListTile<ThemeMode>(
                    title: Text('System default'),
                    value: ThemeMode.system,
                  ),
                  RadioListTile<ThemeMode>(
                    title: Text('Light'),
                    value: ThemeMode.light,
                  ),
                  RadioListTile<ThemeMode>(
                    title: Text('Dark'),
                    value: ThemeMode.dark,
                  ),
                ],
              ),
            ),
            const Divider(),
            const _SectionHeader('Language'),
            RadioGroup<Locale>(
              groupValue: settings.locale,
              onChanged: (v) => controller.setLocale(v!),
              child: const Column(
                children: [
                  RadioListTile<Locale>(
                    title: Text('English'),
                    value: Locale('en'),
                  ),
                  RadioListTile<Locale>(
                    title: Text('বাংলা'),
                    value: Locale('bn'),
                  ),
                ],
              ),
            ),
            const Divider(),
            const _SectionHeader('Hijri calculation method'),
            RadioGroup<HijriMethod>(
              groupValue: settings.hijriMethod,
              onChanged: (v) => controller.setHijriMethod(v!),
              child: const Column(
                children: [
                  RadioListTile<HijriMethod>(
                    title: Text('Umm al-Qura'),
                    subtitle: Text('Saudi civil calendar; matches most Islamic-date apps today'),
                    value: HijriMethod.ummAlQura,
                  ),
                  RadioListTile<HijriMethod>(
                    title: Text('Tabular (civil)'),
                    subtitle: Text('Deterministic arithmetic algorithm, matches the previous app version'),
                    value: HijriMethod.civilTabular,
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}

class _SectionHeader extends StatelessWidget {
  final String title;

  const _SectionHeader(this.title);

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.fromLTRB(16, 16, 16, 4),
      child: Text(
        title,
        style: Theme.of(context)
            .textTheme
            .labelLarge
            ?.copyWith(color: Theme.of(context).colorScheme.primary),
      ),
    );
  }
}
