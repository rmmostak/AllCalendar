import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:intl/intl.dart';

import '../../core/date_conversion/bangla_numerals.dart';
import '../../routes.dart';
import '../../widgets/app_drawer.dart';
import 'home_controller.dart';

class HomeScreen extends ConsumerWidget {
  const HomeScreen({super.key});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final today = ref.watch(todayDatesProvider);
    final locale = Localizations.localeOf(context).toString();
    final gregorianText = DateFormat.yMMMMd(locale).format(today.gregorian);

    return Scaffold(
      appBar: AppBar(title: const Text('All Calendar')),
      drawer: const AppDrawer(),
      floatingActionButton: FloatingActionButton(
        onPressed: () => Navigator.of(context).pushNamed(Routes.calendar),
        tooltip: 'Calendar',
        child: const Icon(Icons.calendar_month),
      ),
      body: SafeArea(
        child: Center(
          child: SingleChildScrollView(
            padding: const EdgeInsets.symmetric(horizontal: 24, vertical: 16),
            child: ConstrainedBox(
              constraints: const BoxConstraints(maxWidth: 520),
              child: Column(
                mainAxisSize: MainAxisSize.min,
                children: [
                  Text(
                    today.banglaDayName,
                    style: Theme.of(context).textTheme.headlineMedium,
                  ),
                  const SizedBox(height: 24),
                  _DateCard(
                    icon: Icons.brightness_5_outlined,
                    label: 'বাংলা তারিখ',
                    value:
                        '${toBanglaNumerals(today.bangla.day)} ${today.bangla.monthName} ${toBanglaNumerals(today.bangla.year)}',
                  ),
                  const SizedBox(height: 12),
                  _DateCard(
                    icon: Icons.nights_stay_outlined,
                    label: 'আরবি তারিখ (হিজরি)',
                    value:
                        '${toBanglaNumerals(today.hijri.day)} ${today.hijri.monthName} ${toBanglaNumerals(today.hijri.year)}',
                  ),
                  const SizedBox(height: 12),
                  _DateCard(
                    icon: Icons.calendar_today_outlined,
                    label: 'English Date',
                    value: gregorianText,
                  ),
                ],
              ),
            ),
          ),
        ),
      ),
    );
  }
}

class _DateCard extends StatelessWidget {
  final IconData icon;
  final String label;
  final String value;

  const _DateCard({
    required this.icon,
    required this.label,
    required this.value,
  });

  @override
  Widget build(BuildContext context) {
    return Card(
      child: ListTile(
        leading: Icon(icon),
        title: Text(label, style: Theme.of(context).textTheme.labelLarge),
        subtitle: Text(
          value,
          style: Theme.of(context).textTheme.titleLarge,
        ),
      ),
    );
  }
}
