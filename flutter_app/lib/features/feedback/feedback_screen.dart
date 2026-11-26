import 'package:flutter/material.dart';
import 'package:url_launcher/url_launcher.dart';

class FeedbackScreen extends StatefulWidget {
  const FeedbackScreen({super.key});

  @override
  State<FeedbackScreen> createState() => _FeedbackScreenState();
}

class _FeedbackScreenState extends State<FeedbackScreen> {
  final _nameController = TextEditingController();
  final _textController = TextEditingController();

  @override
  void dispose() {
    _nameController.dispose();
    _textController.dispose();
    super.dispose();
  }

  Future<void> _send() async {
    final name = _nameController.text.trim();
    final text = _textController.text.trim();

    if (name.isEmpty || text.isEmpty) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('Please fill the input and try again!')),
      );
      return;
    }

    final uri = Uri(
      scheme: 'mailto',
      path: 'rmmostak@yahoo.com',
      queryParameters: {
        'subject': 'Feedback from All Calendar Mobile App',
        'body': 'Name: $name\nMessage: $text',
      },
    );

    if (await canLaunchUrl(uri)) {
      await launchUrl(uri);
      _nameController.clear();
      _textController.clear();
    } else if (mounted) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('No email app available on this device.')),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('FeedBack')),
      body: SafeArea(
        child: Center(
          child: SingleChildScrollView(
            padding: const EdgeInsets.all(24),
            child: ConstrainedBox(
              constraints: const BoxConstraints(maxWidth: 480),
              child: Column(
                mainAxisSize: MainAxisSize.min,
                crossAxisAlignment: CrossAxisAlignment.stretch,
                children: [
                  TextField(
                    controller: _nameController,
                    decoration: const InputDecoration(
                      labelText: 'Your name',
                      border: OutlineInputBorder(),
                    ),
                  ),
                  const SizedBox(height: 16),
                  TextField(
                    controller: _textController,
                    maxLines: 5,
                    decoration: const InputDecoration(
                      labelText: 'Your message',
                      border: OutlineInputBorder(),
                    ),
                  ),
                  const SizedBox(height: 16),
                  FilledButton(onPressed: _send, child: const Text('Send')),
                ],
              ),
            ),
          ),
        ),
      ),
    );
  }
}
