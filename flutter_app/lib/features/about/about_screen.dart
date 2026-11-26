import 'package:flutter/material.dart';

import 'about_content.dart';

class AboutScreen extends StatelessWidget {
  const AboutScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('About App and Developer')),
      body: SafeArea(
        child: SingleChildScrollView(
          child: ExpansionPanelList.radio(
            children: [
              for (final section in aboutSections)
                ExpansionPanelRadio(
                  value: section.title,
                  headerBuilder: (context, isExpanded) => Padding(
                    padding: const EdgeInsets.symmetric(horizontal: 16),
                    child: Align(
                      alignment: Alignment.centerLeft,
                      child: Text(section.title),
                    ),
                  ),
                  body: Padding(
                    padding: const EdgeInsets.fromLTRB(16, 0, 16, 16),
                    child: Align(
                      alignment: Alignment.centerLeft,
                      child: Text(section.body),
                    ),
                  ),
                ),
            ],
          ),
        ),
      ),
    );
  }
}
