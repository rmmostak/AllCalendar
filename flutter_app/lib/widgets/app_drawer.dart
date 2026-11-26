import 'package:flutter/material.dart';
import 'package:share_plus/share_plus.dart';

import '../routes.dart';

class AppDrawer extends StatelessWidget {
  const AppDrawer({super.key});

  @override
  Widget build(BuildContext context) {
    final currentRoute = ModalRoute.of(context)?.settings.name;

    void go(String route) {
      Navigator.of(context).pop();
      if (currentRoute != route) {
        Navigator.of(context).pushNamed(route);
      }
    }

    return Drawer(
      child: SafeArea(
        child: ListView(
          padding: EdgeInsets.zero,
          children: [
            DrawerHeader(
              child: Row(
                crossAxisAlignment: CrossAxisAlignment.end,
                children: [
                  ClipRRect(
                    borderRadius: BorderRadius.circular(12),
                    child: Image.asset(
                      'assets/images/logo.png',
                      width: 48,
                      height: 48,
                    ),
                  ),
                  const SizedBox(width: 12),
                  const Expanded(
                    child: Text(
                      'All Calendar',
                      style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold),
                    ),
                  ),
                ],
              ),
            ),
            ListTile(
              leading: const Icon(Icons.home_outlined),
              title: const Text('Home'),
              onTap: () => go(Routes.home),
            ),
            ListTile(
              leading: const Icon(Icons.cake_outlined),
              title: const Text('Age Calculator'),
              onTap: () => go(Routes.ageCalculator),
            ),
            ListTile(
              leading: const Icon(Icons.sync_alt),
              title: const Text('Date Converter'),
              onTap: () => go(Routes.dateConverter),
            ),
            ListTile(
              leading: const Icon(Icons.settings_outlined),
              title: const Text('Settings'),
              onTap: () => go(Routes.settings),
            ),
            const Divider(),
            ListTile(
              leading: const Icon(Icons.info_outline),
              title: const Text('About App'),
              onTap: () => go(Routes.about),
            ),
            ListTile(
              leading: const Icon(Icons.share_outlined),
              title: const Text('Share App'),
              onTap: () {
                Navigator.of(context).pop();
                SharePlus.instance.share(
                  ShareParams(
                    text:
                        'https://play.google.com/store/apps/details?id=com.rmproduct.calendar',
                    subject: 'All Calendar App',
                  ),
                );
              },
            ),
            ListTile(
              leading: const Icon(Icons.feedback_outlined),
              title: const Text('Feedback'),
              onTap: () => go(Routes.feedback),
            ),
          ],
        ),
      ),
    );
  }
}
