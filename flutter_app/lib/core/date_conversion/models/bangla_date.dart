import 'package:flutter/foundation.dart';

@immutable
class BanglaDate {
  final int year;

  /// 0-based index into [banglaMonthNames], 0 = Boishakh.
  final int monthIndex;

  /// 1-based day of month.
  final int day;

  const BanglaDate({
    required this.year,
    required this.monthIndex,
    required this.day,
  });

  String get monthName => banglaMonthNames[monthIndex];

  @override
  bool operator ==(Object other) =>
      other is BanglaDate &&
      other.year == year &&
      other.monthIndex == monthIndex &&
      other.day == day;

  @override
  int get hashCode => Object.hash(year, monthIndex, day);

  @override
  String toString() => '$day $monthName $year';
}

/// Bengali month names, index 0 = Boishakh .. 11 = Chaitra.
const List<String> banglaMonthNames = [
  'বৈশাখ',
  'জ্যৈষ্ঠ',
  'আষাঢ়',
  'শ্রাবণ',
  'ভাদ্র',
  'আশ্বিন',
  'কার্তিক',
  'অগ্রহায়ণ',
  'পৌষ',
  'মাঘ',
  'ফাল্গুন',
  'চৈত্র',
];

/// Bengali day names indexed by [DateTime.weekday] - 1 (1=Monday..7=Sunday).
const List<String> banglaDayNames = [
  'সোমবার',
  'মঙ্গলবার',
  'বুধবার',
  'বৃহস্পতিবার',
  'শুক্রবার',
  'শনিবার',
  'রবিবার',
];
