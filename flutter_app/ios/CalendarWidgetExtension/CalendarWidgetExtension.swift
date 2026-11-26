//
//  CalendarWidgetExtension.swift
//
//  Homescreen widget showing today's date in Bangla, Hijri, and Gregorian
//  calendars. Mirrors android/app/src/main/kotlin/.../CalendarWidgetProvider.kt:
//  this widget does NOT recompute dates itself (WidgetKit extensions can't run
//  the Flutter/Dart engine) -- it only renders whatever
//  lib/services/home_widget_service.dart last wrote into the shared App Group
//  UserDefaults via HomeWidget.saveWidgetData. The widget reloads once daily
//  (see Provider.getTimeline) plus whenever the app calls HomeWidget.updateWidget.
//
//  Must match lib/services/home_widget_service.dart:
//    - App Group id: "group.com.rmproduct.allcalendar.widget"
//    - Keys: "dayName", "banglaDate", "hijriDate", "englishDate"
//    - Widget kind below must match HomeWidgetService.iOSWidgetName ("CalendarWidget")
//

import SwiftUI
import WidgetKit

private let widgetGroupId = "group.com.rmproduct.allcalendar.widget"

struct CalendarEntry: TimelineEntry {
  let date: Date
  let dayName: String
  let banglaDate: String
  let hijriDate: String
  let englishDate: String
}

struct Provider: TimelineProvider {
  func placeholder(in context: Context) -> CalendarEntry {
    CalendarEntry(
      date: Date(),
      dayName: "সোমবার",
      banglaDate: "৫ শ্রাবণ ১৪৩৩",
      hijriDate: "৬ সফর ১৪৪৮",
      englishDate: "Jul 20, 2026"
    )
  }

  func getSnapshot(in context: Context, completion: @escaping (CalendarEntry) -> Void) {
    completion(readEntry())
  }

  func getTimeline(in context: Context, completion: @escaping (Timeline<CalendarEntry>) -> Void) {
    let entry = readEntry()

    // Content only changes at local midnight, so reload then rather than polling.
    let calendar = Calendar.current
    let nextMidnight =
      calendar.nextDate(
        after: Date(), matching: DateComponents(hour: 0, minute: 0, second: 0),
        matchingPolicy: .nextTime) ?? Date().addingTimeInterval(86400)

    let timeline = Timeline(entries: [entry], policy: .after(nextMidnight))
    completion(timeline)
  }

  private func readEntry() -> CalendarEntry {
    let data = UserDefaults(suiteName: widgetGroupId)
    return CalendarEntry(
      date: Date(),
      dayName: data?.string(forKey: "dayName") ?? "--",
      banglaDate: data?.string(forKey: "banglaDate") ?? "--",
      hijriDate: data?.string(forKey: "hijriDate") ?? "--",
      englishDate: data?.string(forKey: "englishDate") ?? "--"
    )
  }
}

struct CalendarWidgetView: View {
  var entry: Provider.Entry

  // Matches android/.../res/layout/calendar_widget.xml: transparent
  // background, white text with a dark shadow for legibility on any
  // wallpaper, rather than a solid color block.
  var body: some View {
    VStack(spacing: 2) {
      Text(entry.dayName)
        .font(.system(size: 14, weight: .bold))
      Text(entry.banglaDate)
        .font(.system(size: 12))
      Text(entry.hijriDate)
        .font(.system(size: 12))
      Text(entry.englishDate)
        .font(.system(size: 12))
    }
    .foregroundColor(.white)
    .shadow(color: .black.opacity(0.65), radius: 3, x: 0, y: 1)
    .frame(maxWidth: .infinity, maxHeight: .infinity)
    .widgetURL(URL(string: "allcalendar://open"))
    .containerBackground(for: .widget) {
      Color.clear
    }
  }
}

@main
struct CalendarWidgetExtension: Widget {
  let kind: String = "CalendarWidget"

  var body: some WidgetConfiguration {
    StaticConfiguration(kind: kind, provider: Provider()) { entry in
      CalendarWidgetView(entry: entry)
    }
    .configurationDisplayName("All Calendar")
    .description("Today's date in Bangla, Hijri, and English.")
    .supportedFamilies([.systemSmall, .systemMedium])
  }
}

struct CalendarWidgetExtension_Previews: PreviewProvider {
  static var previews: some View {
    CalendarWidgetView(
      entry: CalendarEntry(
        date: Date(), dayName: "সোমবার", banglaDate: "৫ শ্রাবণ ১৪৩৩",
        hijriDate: "৬ সফর ১৪৪৮", englishDate: "Jul 20, 2026")
    )
    .previewContext(WidgetPreviewContext(family: .systemSmall))
  }
}
