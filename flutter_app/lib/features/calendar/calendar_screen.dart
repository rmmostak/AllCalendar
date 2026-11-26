import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:intl/intl.dart';

import '../../core/date_conversion/bangla_numerals.dart';
import 'calendar_controller.dart';

/// Friday-first short weekday labels, matching the Bangladeshi week
/// (Friday-Saturday weekend).
const _weekdayLabels = ['শুক্র', 'শনি', 'রবি', 'সোম', 'মঙ্গল', 'বুধ', 'বৃহঃ'];

class CalendarScreen extends ConsumerWidget {
  const CalendarScreen({super.key});

  bool _isSameDate(DateTime a, DateTime b) =>
      a.year == b.year && a.month == b.month && a.day == b.day;

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final calendar = ref.watch(monthCalendarProvider);
    final locale = Localizations.localeOf(context).toString();
    final monthLabel = DateFormat.yMMMM(locale).format(calendar.month);
    final theme = Theme.of(context);
    final banglaColor = Colors.red.shade400;
    final hijriColor = Colors.teal.shade400;
    final today = DateTime.now();
    final useBanglaDigits = locale == 'bn';

    void changeMonth(int delta) {
      ref.read(calendarMonthProvider.notifier).changeBy(delta);
    }

    return Scaffold(
      appBar: AppBar(title: const Text('Calendar')),
      body: SafeArea(
        child: Column(
          children: [
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 4, vertical: 8),
              child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  IconButton(
                    icon: const Icon(Icons.chevron_left),
                    tooltip: 'Previous month',
                    onPressed: () => changeMonth(-1),
                  ),
                  Column(
                    children: [
                      Text(monthLabel, style: theme.textTheme.titleLarge),
                      const SizedBox(height: 2),
                      Row(
                        mainAxisSize: MainAxisSize.min,
                        children: [
                          if (calendar.banglaMonthRange.isNotEmpty)
                            Text(
                              calendar.banglaMonthRange,
                              style: theme.textTheme.bodySmall?.copyWith(
                                color: banglaColor,
                                fontWeight: FontWeight.w600,
                              ),
                            ),
                          const SizedBox(width: 12),
                          if (calendar.hijriMonthRange.isNotEmpty)
                            Text(
                              calendar.hijriMonthRange,
                              style: theme.textTheme.bodySmall?.copyWith(
                                color: hijriColor,
                                fontWeight: FontWeight.w600,
                              ),
                            ),
                        ],
                      ),
                    ],
                  ),
                  IconButton(
                    icon: const Icon(Icons.chevron_right),
                    tooltip: 'Next month',
                    onPressed: () => changeMonth(1),
                  ),
                ],
              ),
            ),
            Row(
              children: [
                for (var i = 0; i < _weekdayLabels.length; i++)
                  Expanded(
                    child: Center(
                      child: Text(
                        _weekdayLabels[i],
                        style: theme.textTheme.labelMedium?.copyWith(
                          color: i == 0 ? banglaColor : null,
                          fontWeight: FontWeight.w600,
                        ),
                      ),
                    ),
                  ),
              ],
            ),
            const Divider(height: 1),
            Expanded(
              child: GridView.builder(
                padding: const EdgeInsets.all(4),
                gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
                  crossAxisCount: 7,
                  childAspectRatio: 0.72,
                ),
                itemCount: calendar.days.length,
                itemBuilder: (context, index) {
                  final day = calendar.days[index];
                  if (day == null) return const SizedBox.shrink();
                  return _CalendarDayCell(
                    day: day,
                    isToday: _isSameDate(day.gregorian, today),
                    useBanglaDigits: useBanglaDigits,
                    banglaColor: banglaColor,
                    hijriColor: hijriColor,
                  );
                },
              ),
            ),
          ],
        ),
      ),
    );
  }
}

class _CalendarDayCell extends StatelessWidget {
  final CalendarDay day;
  final bool isToday;
  final bool useBanglaDigits;
  final Color banglaColor;
  final Color hijriColor;

  const _CalendarDayCell({
    required this.day,
    required this.isToday,
    required this.useBanglaDigits,
    required this.banglaColor,
    required this.hijriColor,
  });

  String _gregorianDigits(int n) => useBanglaDigits ? toBanglaNumerals(n) : '$n';

  @override
  Widget build(BuildContext context) {
    final theme = Theme.of(context);
    return Container(
      margin: const EdgeInsets.all(1),
      decoration: BoxDecoration(
        color: isToday ? theme.colorScheme.primaryContainer : null,
        borderRadius: BorderRadius.circular(8),
      ),
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        mainAxisSize: MainAxisSize.min,
        children: [
          Text(
            toBanglaNumerals(day.bangla.day),
            style: TextStyle(
              fontSize: 11,
              height: 1.1,
              color: banglaColor,
              fontWeight: FontWeight.w600,
            ),
          ),
          Text(
            _gregorianDigits(day.gregorian.day),
            style: TextStyle(
              fontSize: 16,
              height: 1.2,
              fontWeight: FontWeight.bold,
              color: isToday
                  ? theme.colorScheme.onPrimaryContainer
                  : theme.colorScheme.onSurface,
            ),
          ),
          Text(
            toBanglaNumerals(day.hijri.day),
            style: TextStyle(
              fontSize: 11,
              height: 1.1,
              color: hijriColor,
              fontWeight: FontWeight.w600,
            ),
          ),
        ],
      ),
    );
  }
}
