import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

import '../../core/age_calculation/age_calculator.dart';
import '../../core/clock_provider.dart';
import 'age_calculator_controller.dart';

class AgeCalculatorScreen extends ConsumerWidget {
  const AgeCalculatorScreen({super.key});

  Future<void> _pickBirthDate(BuildContext context, WidgetRef ref) async {
    final now = ref.read(nowProvider);
    final picked = await showDatePicker(
      context: context,
      initialDate: DateTime(now.year - 20, now.month, now.day),
      firstDate: DateTime(1900),
      lastDate: now,
    );
    if (picked != null) {
      ref.read(ageCalculatorControllerProvider.notifier).setBirthDate(picked);
    }
  }

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final state = ref.watch(ageCalculatorControllerProvider);
    final controller = ref.read(ageCalculatorControllerProvider.notifier);

    return Scaffold(
      appBar: AppBar(title: const Text('Age Calculator')),
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
                      state.birthDate == null
                          ? 'Select date of birth'
                          : '${state.birthDate!.year}-${state.birthDate!.month.toString().padLeft(2, '0')}-${state.birthDate!.day.toString().padLeft(2, '0')}',
                    ),
                    onPressed: () => _pickBirthDate(context, ref),
                  ),
                  const SizedBox(height: 16),
                  FilledButton(
                    onPressed: state.birthDate == null ? null : controller.calculate,
                    child: const Text('Calculate Age'),
                  ),
                  const SizedBox(height: 24),
                  if (state.result != null) _ResultCard(result: state.result!),
                ],
              ),
            ),
          ),
        ),
      ),
    );
  }
}

class _ResultCard extends StatelessWidget {
  final AgeResult result;

  const _ResultCard({required this.result});

  @override
  Widget build(BuildContext context) {
    if (!result.isValid) {
      return Card(
        color: Theme.of(context).colorScheme.errorContainer,
        child: Padding(
          padding: const EdgeInsets.all(16),
          child: Text(
            result.error!,
            style: TextStyle(
              color: Theme.of(context).colorScheme.onErrorContainer,
            ),
          ),
        ),
      );
    }

    return Card(
      child: Padding(
        padding: const EdgeInsets.all(20),
        child: Text(
          '${result.years} Year(s) ${result.months} Month(s) ${result.days} Day(s)',
          textAlign: TextAlign.center,
          style: Theme.of(context).textTheme.titleLarge,
        ),
      ),
    );
  }
}
