class AboutSection {
  final String title;
  final String body;

  const AboutSection({required this.title, required this.body});
}

/// Ported from the previous app's res/values/strings.xml `group`/`child`
/// arrays (About screen content).
const List<AboutSection> aboutSections = [
  AboutSection(
    title: 'About Developer',
    body: 'Initiator:\nMd. Niyajul Islam\nB.Sc. Manda Govt. College\n\n'
        'Design & Development:\nMustak Ahmmed\nCSE-16, JUST.',
  ),
  AboutSection(
    title: 'Contact',
    body: 'For more, please contact us -\n'
        'Email: rnniyajul@gmail.com (Initiator)\n'
        'Email: rmmostak@gmail.com (Developer)',
  ),
  AboutSection(
    title: 'Bangla Calendar',
    body: 'New Bengali Calendar\n\n'
        'Under the 2019 Bangla Academy reform: the first six months, '
        'Boishakh to Ashwin, are 31 days each. Kartik, Agrahayan, Poush, '
        'Magh and Chaitra are 30 days each. Falgun is 29 days, and 30 days '
        'in a leap year -- so Pohela Boishakh (Bangla New Year) always '
        'falls on April 14.',
  ),
  AboutSection(
    title: 'Arabic Calendar',
    body: 'The Islamic (Hijri) calendar begins with the Prophet Muhammad '
        '(PBUH)\'s migration ("Hijra") from Makkah to Madina. It is a purely '
        'lunar calendar, about 11 days shorter than the Gregorian year, so '
        'its months shift earlier each Gregorian year. Muharram marks the '
        'start of the Islamic year.',
  ),
  AboutSection(
    title: 'English Calendar',
    body: 'The Gregorian calendar is today\'s internationally accepted '
        'civil calendar. It replaced the Julian calendar in 1582 under '
        'Pope Gregory XIII, correcting a drift of about 10 days that had '
        'accumulated over the centuries.',
  ),
  AboutSection(
    title: 'Age Calculator',
    body: 'Calculate age based on a date of birth and another date '
        '(default: today), shown in years, months, and days.',
  ),
  AboutSection(
    title: 'Date Converter',
    body: 'Convert any Gregorian date into its Bangla and Hijri calendar '
        'equivalents.',
  ),
];
