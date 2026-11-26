import 'package:flutter/foundation.dart';

@immutable
class HijriDate {
  final int year;

  /// 1-based month, 1 = Muharram .. 12 = Dhul Hijjah.
  final int month;

  /// 1-based day of month.
  final int day;

  const HijriDate({
    required this.year,
    required this.month,
    required this.day,
  });

  String get monthName => hijriMonthNames[month - 1];

  @override
  bool operator ==(Object other) =>
      other is HijriDate &&
      other.year == year &&
      other.month == month &&
      other.day == day;

  @override
  int get hashCode => Object.hash(year, month, day);

  @override
  String toString() => '$day $monthName $year';
}

/// Hijri month names (Bengali transliteration, matches the old app's strings.xml),
/// index 0 = Muharram .. 11 = Dhul Hijjah.
const List<String> hijriMonthNames = [
  'মুহররম',
  'সফর',
  'রবিউল আউয়াল',
  'রবিউস সানি',
  'জমাদিউল আউয়াল',
  'জমাদিউস সানি',
  'রজব',
  'শাবান',
  'রমজান',
  'শাওয়াল',
  'জিলকদ',
  'জিলহজ্জ',
];
