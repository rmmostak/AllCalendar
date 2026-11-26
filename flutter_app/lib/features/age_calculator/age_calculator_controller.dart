import 'package:flutter_riverpod/flutter_riverpod.dart';

import '../../core/age_calculation/age_calculator.dart';
import '../../core/clock_provider.dart';

class AgeCalculatorState {
  final DateTime? birthDate;
  final AgeResult? result;

  const AgeCalculatorState({this.birthDate, this.result});

  AgeCalculatorState copyWith({DateTime? birthDate, AgeResult? result}) {
    return AgeCalculatorState(
      birthDate: birthDate ?? this.birthDate,
      result: result,
    );
  }
}

class AgeCalculatorController extends Notifier<AgeCalculatorState> {
  @override
  AgeCalculatorState build() => const AgeCalculatorState();

  void setBirthDate(DateTime date) {
    state = state.copyWith(birthDate: date);
  }

  void calculate() {
    final birth = state.birthDate;
    if (birth == null) return;
    state = state.copyWith(result: calculateAge(birth, ref.read(nowProvider)));
  }
}

final ageCalculatorControllerProvider =
    NotifierProvider.autoDispose<AgeCalculatorController, AgeCalculatorState>(
  AgeCalculatorController.new,
);
