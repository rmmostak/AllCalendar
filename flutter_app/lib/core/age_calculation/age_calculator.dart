import 'package:flutter/foundation.dart';

@immutable
class AgeResult {
  final int years;
  final int months;
  final int days;
  final String? error;

  const AgeResult({
    required this.years,
    required this.months,
    required this.days,
  }) : error = null;

  const AgeResult.invalid(this.error)
      : years = 0,
        months = 0,
        days = 0;

  bool get isValid => error == null;
}

bool _isLeapYear(int year) {
  return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
}

int daysInMonth(int year, int month) {
  const lengths = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
  if (month == 2 && _isLeapYear(year)) return 29;
  return lengths[month - 1];
}

DateTime _dateOnly(DateTime d) => DateTime(d.year, d.month, d.day);

/// Computes the age between [birth] and [on] as whole years, months, and
/// days, using calendar-correct borrowing (borrows days-in-month from the
/// calendar month immediately before [on], not from a hardcoded table keyed
/// on the birth month's name).
AgeResult calculateAge(DateTime birth, DateTime on) {
  final b = _dateOnly(birth);
  final o = _dateOnly(on);

  if (b.isAfter(o)) {
    return const AgeResult.invalid('Birth date is in the future');
  }

  var years = o.year - b.year;
  var months = o.month - b.month;
  var days = o.day - b.day;

  if (days < 0) {
    var prevMonth = o.month - 1;
    var prevMonthYear = o.year;
    if (prevMonth == 0) {
      prevMonth = 12;
      prevMonthYear -= 1;
    }
    days += daysInMonth(prevMonthYear, prevMonth);
    months -= 1;
  }

  if (months < 0) {
    months += 12;
    years -= 1;
  }

  return AgeResult(years: years, months: months, days: days);
}
