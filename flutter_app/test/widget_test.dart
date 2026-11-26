import 'package:all_calendar/app.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:flutter_test/flutter_test.dart';

void main() {
  testWidgets("App shows home screen with today's three dates", (tester) async {
    await tester.pumpWidget(const ProviderScope(child: App()));
    await tester.pumpAndSettle();

    expect(find.text('All Calendar'), findsWidgets);
    expect(find.text('বাংলা তারিখ'), findsOneWidget);
    expect(find.text('আরবি তারিখ (হিজরি)'), findsOneWidget);
    expect(find.text('English Date'), findsOneWidget);
  });
}
