import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

import '../../core/clock_provider.dart';
import '../../core/date_conversion/bangla_numerals.dart';
import 'date_converter_controller.dart';

class DateConverterScreen extends ConsumerWidget {
  const DateConverterScreen({super.key});

  Future<void> _pickDate(BuildContext context, WidgetRef ref) async {
    final now = ref.read(nowProvider);
    final picked = await showDatePicker(
      context: context,
      initialDate: now,
      firstDate: DateTime(1700),
      lastDate: DateTime(2200),
    );
    if (picked != null) {
      ref.read(dateConverterControllerProvider.notifier).convert(picked);
    }
  }

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final state = ref.watch(dateConverterControllerProvider);

    return Scaffold(
      appBar: AppBar(title: const Text('Date Converter')),
      body: SafeArea(
        child: Center(
          child: SingleChildScrollView(
            padding: const EdgeInsets.all(24),
            child: ConstrainedBox(
              constraints: const BoxConstraints(maxWidth: 480),
              child: Column(
                mainAxisSize: MainAxisSize.min,
                crossAxisAlignment: CrossAxisAlignment.stretch,
                children: [
                  OutlinedButton.icon(
                    icon: const Icon(Icons.calendar_month_outlined),
                    label: Text(
                      state.pickedDate == null
                          ? 'Select a date'
                          : '${state.pickedDate!.year}-${state.pickedDate!.month.toString().padLeft(2, '0')}-${state.pickedDate!.day.toString().padLeft(2, '0')}',
                    ),
                    onPressed: () => _pickDate(context, ref),
                  ),
                  const SizedBox(height: 24),
                  if (state.bangla != null)
                    Card(
                      child: ListTile(
                        leading: const Icon(Icons.brightness_5_outlined),
                        title: const Text('বাংলা তারিখ'),
                        subtitle: Text(
                          '${toBanglaNumerals(state.bangla!.day)} ${state.bangla!.monthName} ${toBanglaNumerals(state.bangla!.year)}',
                          style: Theme.of(context).textTheme.titleLarge,
                        ),
                      ),
                    ),
                  if (state.hijri != null) ...[
                    const SizedBox(height: 12),
                    Card(
                      child: ListTile(
                        leading: const Icon(Icons.nights_stay_outlined),
                        title: const Text('আরবি তারিখ (হিজরি)'),
                        subtitle: Text(
                          '${toBanglaNumerals(state.hijri!.day)} ${state.hijri!.monthName} ${toBanglaNumerals(state.hijri!.year)}',
                          style: Theme.of(context).textTheme.titleLarge,
                        ),
                      ),
                    ),
                  ],
                ],
              ),
            ),
          ),
        ),
      ),
    );
  }
}
