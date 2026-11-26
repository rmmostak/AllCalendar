const List<String> _banglaDigits = [
  '০',
  '১',
  '২',
  '৩',
  '৪',
  '৫',
  '৬',
  '৭',
  '৮',
  '৯',
];

/// Converts every ASCII digit in [input] to its Bengali numeral equivalent.
/// Non-digit characters are passed through unchanged.
String toBanglaNumerals(Object input) {
  final str = input.toString();
  final buffer = StringBuffer();
  for (final rune in str.runes) {
    if (rune >= 0x30 && rune <= 0x39) {
      buffer.write(_banglaDigits[rune - 0x30]);
    } else {
      buffer.writeCharCode(rune);
    }
  }
  return buffer.toString();
}
