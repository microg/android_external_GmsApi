package com.google.android.gms.vision.barcode.internal.client;

import android.graphics.Point;

import org.microg.safeparcel.AutoSafeParcelable;
import org.microg.safeparcel.SafeParceled;

public class Barcode extends AutoSafeParcelable {
    @SafeParceled(1)
    private final int versionCode = 1;
    @SafeParceled(2)
    public int format;
    @SafeParceled(3)
    public String rawValue;
    @SafeParceled(4)
    public String displayValue;
    @SafeParceled(5)
    public int valueFormat;
    @SafeParceled(6)
    public Point[] cornerPoints;
    @SafeParceled(7)
    public Barcode.Email email;
    @SafeParceled(8)
    public Barcode.Phone phone;
    @SafeParceled(9)
    public Barcode.Sms sms;
    @SafeParceled(10)
    public Barcode.WiFi wifi;
    @SafeParceled(11)
    public Barcode.UrlBookmark url;
    @SafeParceled(12)
    public Barcode.GeoPoint geoPoint;
    @SafeParceled(13)
    public Barcode.CalendarEvent calendarEvent;
    @SafeParceled(14)
    public Barcode.ContactInfo contactInfo;
    @SafeParceled(15)
    public Barcode.DriverLicense driverLicense;

    public static Creator<Barcode> CREATOR = new AutoCreator<Barcode>(Barcode.class);

    public static class Email extends AutoSafeParcelable {
        public static final int UNKNOWN = 0;
        public static final int WORK = 1;
        public static final int HOME = 2;

        @SafeParceled(1)
        final int versionCode = 1;
        @SafeParceled(2)
        public int type;
        @SafeParceled(3)
        public String address;
        @SafeParceled(4)
        public String subject;
        @SafeParceled(5)
        public String body;

        public static Creator<Email> CREATOR = new AutoCreator<Email>(Email.class);
    }

    public static class Phone extends AutoSafeParcelable {
        public static final int UNKNOWN = 0;
        public static final int WORK = 1;
        public static final int HOME = 2;
        public static final int FAX = 3;
        public static final int MOBILE = 4;

        @SafeParceled(1)
        final int versionCode = 1;
        @SafeParceled(2)
        public int type;
        @SafeParceled(3)
        public String number;

        public static Creator<Phone> CREATOR = new AutoCreator<Phone>(Phone.class);
    }

    public static class Sms extends AutoSafeParcelable {
        @SafeParceled(1)
        final int versionCode = 1;
        @SafeParceled(2)
        public String message;
        @SafeParceled(3)
        public String phoneNumber;

        public static Creator<Sms> CREATOR = new AutoCreator<Sms>(Sms.class);
    }

    public static class WiFi extends AutoSafeParcelable {
        public static final int OPEN = 1;
        public static final int WPA = 2;
        public static final int WEP = 3;

        @SafeParceled(1)
        final int versionCode = 1;
        @SafeParceled(2)
        public String ssid;
        @SafeParceled(3)
        public String password;
        @SafeParceled(4)
        public int encryptionType;

        public static Creator<WiFi> CREATOR = new AutoCreator<WiFi>(WiFi.class);
    }

    public static class UrlBookmark extends AutoSafeParcelable {
        @SafeParceled(1)
        final int versionCode = 1;
        @SafeParceled(2)
        public String title;
        @SafeParceled(3)
        public String url;

        public static Creator<UrlBookmark> CREATOR = new AutoCreator<UrlBookmark>(UrlBookmark.class);
    }

    public static class GeoPoint extends AutoSafeParcelable {
        @SafeParceled(1)
        final int versionCode = 1;
        @SafeParceled(2)
        public double lat;
        @SafeParceled(3)
        public double lng;

        public static Creator<GeoPoint> CREATOR = new AutoCreator<GeoPoint>(GeoPoint.class);
    }

    public static class CalendarDateTime extends AutoSafeParcelable {
        @SafeParceled(1)
        final int versionCode = 1;
        @SafeParceled(2)
        public int year;
        @SafeParceled(3)
        public int month;
        @SafeParceled(4)
        public int day;
        @SafeParceled(5)
        public int hours;
        @SafeParceled(6)
        public int minutes;
        @SafeParceled(7)
        public int seconds;
        @SafeParceled(8)
        public boolean isUtc;
        @SafeParceled(9)
        public String rawValue;

        public static Creator<CalendarDateTime> CREATOR = new AutoCreator<CalendarDateTime>(CalendarDateTime.class);
    }

    public static class CalendarEvent extends AutoSafeParcelable {
        @SafeParceled(1)
        final int versionCode = 1;
        @SafeParceled(2)
        public String summary;
        @SafeParceled(3)
        public String description;
        @SafeParceled(4)
        public String location;
        @SafeParceled(5)
        public String organizer;
        @SafeParceled(6)
        public String status;
        @SafeParceled(7)
        public Barcode.CalendarDateTime start;
        @SafeParceled(8)
        public Barcode.CalendarDateTime end;

        public static Creator<CalendarEvent> CREATOR = new AutoCreator<CalendarEvent>(CalendarEvent.class);
    }

    public static class PersonName extends AutoSafeParcelable {
        @SafeParceled(1)
        final int versionCode = 1;
        @SafeParceled(2)
        public String formattedName;
        @SafeParceled(3)
        public String pronunciation;
        @SafeParceled(4)
        public String prefix;
        @SafeParceled(5)
        public String first;
        @SafeParceled(6)
        public String middle;
        @SafeParceled(7)
        public String last;
        @SafeParceled(8)
        public String suffix;

        public static Creator<PersonName> CREATOR = new AutoCreator<PersonName>(PersonName.class);
    }

    public static class Address extends AutoSafeParcelable {
        public static final int UNKNOWN = 0;
        public static final int WORK = 1;
        public static final int HOME = 2;

        @SafeParceled(1)
        final int versionCode = 1;
        @SafeParceled(2)
        public int type;
        @SafeParceled(3)
        public String[] addressLines;

        public static Creator<Address> CREATOR = new AutoCreator<Address>(Address.class);
    }

    // TODO: DriverLicense and ContactInfo
}
