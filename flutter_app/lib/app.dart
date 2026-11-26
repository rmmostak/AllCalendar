import 'package:flutter/material.dart';
import 'package:flutter_localizations/flutter_localizations.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

import 'core/settings/settings_provider.dart';
import 'core/theme/app_theme.dart';
import 'features/about/about_screen.dart';
import 'features/age_calculator/age_calculator_screen.dart';
import 'features/calendar/calendar_screen.dart';
import 'features/date_converter/date_converter_screen.dart';
import 'features/feedback/feedback_screen.dart';
import 'features/home/home_screen.dart';
import 'features/settings/settings_screen.dart';
import 'routes.dart';
import 'services/home_widget_service.dart';

class App extends ConsumerStatefulWidget {
  const App({super.key});

  @override
  ConsumerState<App> createState() => _AppState();
}

class _AppState extends ConsumerState<App> with WidgetsBindingObserver {
  final _homeWidgetService = HomeWidgetService();

  @override
  void initState() {
    super.initState();
    WidgetsBinding.instance.addObserver(this);
    WidgetsBinding.instance.addPostFrameCallback((_) => _syncWidget());
  }

  @override
  void dispose() {
    WidgetsBinding.instance.removeObserver(this);
    super.dispose();
  }

  @override
  void didChangeAppLifecycleState(AppLifecycleState state) {
    if (state == AppLifecycleState.resumed) {
      _syncWidget();
    }
  }

  void _syncWidget() {
    _homeWidgetService.updateWidget(ref.read(settingsProvider).hijriMethod);
  }

  @override
  Widget build(BuildContext context) {
    final settings = ref.watch(settingsProvider);

    ref.listen(settingsProvider.select((s) => s.hijriMethod), (previous, next) {
      if (previous != null && previous != next) _syncWidget();
    });

    return MaterialApp(
      title: 'All Calendar',
      debugShowCheckedModeBanner: false,
      themeMode: settings.themeMode,
      theme: AppTheme.light,
      darkTheme: AppTheme.dark,
      locale: settings.locale,
      supportedLocales: const [Locale('en'), Locale('bn')],
      localizationsDelegates: const [
        GlobalMaterialLocalizations.delegate,
        GlobalWidgetsLocalizations.delegate,
        GlobalCupertinoLocalizations.delegate,
      ],
      initialRoute: Routes.home,
      routes: {
        Routes.home: (_) => const HomeScreen(),
        Routes.calendar: (_) => const CalendarScreen(),
        Routes.ageCalculator: (_) => const AgeCalculatorScreen(),
        Routes.dateConverter: (_) => const DateConverterScreen(),
        Routes.settings: (_) => const SettingsScreen(),
        Routes.about: (_) => const AboutScreen(),
        Routes.feedback: (_) => const FeedbackScreen(),
      },
    );
  }
}
