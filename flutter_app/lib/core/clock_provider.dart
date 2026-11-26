import 'package:flutter_riverpod/flutter_riverpod.dart';

/// Provides "now". Overridden in widget tests with a fixed [DateTime] so
/// date-dependent UI is deterministic.
final nowProvider = Provider<DateTime>((ref) => DateTime.now());
