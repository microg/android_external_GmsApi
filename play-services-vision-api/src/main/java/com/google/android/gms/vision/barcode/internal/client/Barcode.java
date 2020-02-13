package com.google.android.gms.vision.barcode.internal.client;

import android.graphics.Point;
import android.util.Log;
import android.util.Patterns;

import org.microg.safeparcel.AutoSafeParcelable;
import org.microg.safeparcel.SafeParceled;

import java.net.MalformedURLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.net.URL;

public class Barcode extends AutoSafeParcelable {
    public static final String TAG = Barcode.class.getSimpleName();

    public static final int CONTACT_INFO = 1;
    public static final int EMAIL = 2;
    public static final int ISBN = 3;
    public static final int PHONE = 4;
    public static final int PRODUCT = 5;
    public static final int SMS = 6;
    public static final int TEXT = 7;
    public static final int URL = 8;
    public static final int WIFI = 9;
    public static final int GEO = 10;
    public static final int CALENDAR_EVENT = 11;
    public static final int DRIVER_LICENSE = 12;

    @SafeParceled(1)
    private final int versionCode = 1;
    // TODO: Check bit field here
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

    public Barcode() {}

    public Barcode(String rawValue) {
        // TODO: Fill these out.
        this.cornerPoints = new Point[4];
        this.valueFormat = TEXT;
        this.rawValue = rawValue;
        // TODO: Maybe URLDecoder.decode() or smth?
        this.displayValue = rawValue;
        detectAndSetType();
    }

    // TODO: Not all types are handled yet, calendarEvent, contactInfo, and driverLicense are not supported yet.
    private void detectAndSetType() {
        try {
            // We do this for the side effect when the URI is invalid
            new URL(this.rawValue);
            this.valueFormat = Barcode.URL;
            this.url = new Barcode.UrlBookmark();
            this.url.url = this.rawValue;
            return;
        } catch(MalformedURLException _ex) {}

        int schemeSeparatorIndex = this.rawValue.indexOf(":");
        if (schemeSeparatorIndex == -1 || this.rawValue.length() == schemeSeparatorIndex + 1) {
            this.valueFormat = Barcode.TEXT;
            return;
        }
        String scheme = this.rawValue.substring(0, schemeSeparatorIndex);
        String rest = this.rawValue.substring(schemeSeparatorIndex + 1);
        switch(scheme.toLowerCase()) {
            case "mailto":
                this.valueFormat = Barcode.EMAIL;
                this.email = Barcode.Email.parse(rest);
                break;
            case "tel":
                this.valueFormat = Barcode.PHONE;
                this.phone = Barcode.Phone.parse(rest);
                break;
            case "sms":
            case "smsto":
                this.valueFormat = Barcode.SMS;
                this.sms = Barcode.Sms.parse(rest);
                break;
            case "mebkm":
                this.valueFormat = Barcode.URL;
                this.url = Barcode.UrlBookmark.parse(rest);
                break;
            case "wifi":
                this.valueFormat = Barcode.WIFI;
                this.wifi = Barcode.WiFi.parse(rest);
                break;
            case "geo":
                this.geoPoint = Barcode.GeoPoint.parse(rest);
                if (this.geoPoint != null) {
                    this.valueFormat = Barcode.GEO;
                }
                break;
            default:
                Log.d(TAG, "Scheme " + scheme + " is not supported yet");
                this.valueFormat = Barcode.TEXT;
                break;
        }
    }


    private static String extractMatch(String value, Pattern pattern, int group) {
        Matcher match = pattern.matcher(value);
        if (!match.matches() || match.group(group) == null) {
            return null;
        }
        return match.group(group);
    }

    private static String extractMatch(String value, Pattern pattern) {
        return extractMatch(value, pattern, 0);
    }

    public static Creator<Barcode> CREATOR = new AutoCreator<>(Barcode.class);

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

        public static Email parse(String value) {
            Email result = new Email();
            // TODO: Check the users contacts to see if we know the address?
            result.type = UNKNOWN;

            int index = value.indexOf('?');
            if (index != -1) {
                result.address = extractMatch(value.substring(0, index), Patterns.EMAIL_ADDRESS);
            } else {
                result.address = extractMatch(value, Patterns.EMAIL_ADDRESS);
            }
            result.subject = extractMatch(value, Pattern.compile(".*?subject=([^&]*).*"), 1);
            result.body = extractMatch(value, Pattern.compile(".*?body=([^&]*).*"), 1);
            return result;
        }

        public static Creator<Email> CREATOR = new AutoCreator<>(Email.class);
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

        public static Phone parse(String value) {
            Phone phone = new Phone();
            phone.type = UNKNOWN;
            phone.number = value;
            return phone;
        }

        public static Creator<Phone> CREATOR = new AutoCreator<>(Phone.class);
    }

    public static class Sms extends AutoSafeParcelable {
        @SafeParceled(1)
        final int versionCode = 1;
        @SafeParceled(2)
        public String message;
        @SafeParceled(3)
        public String phoneNumber;

        public static Sms parse(String value) {
            Sms result = new Sms();
            result.phoneNumber = extractMatch(value, Pattern.compile("([+]?[0-9]+).*"), 1);
            result.message = extractMatch(value, Pattern.compile(".*?(body=|:|,)(.*)$"), 2);
            return result;
        }

        public static Creator<Sms> CREATOR = new AutoCreator<>(Sms.class);
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

        public static WiFi parse(String value) {
            WiFi result = new WiFi();
            result.password = extractMatch(value, Pattern.compile(".*?(P|p):(.*?);.*"), 2);
            result.ssid = extractMatch(value, Pattern.compile(".*?(S|s):(.*?);.*"), 2);
            result.encryptionType = OPEN;
            String encryptionType = extractMatch(value, Pattern.compile(".*?(T|t):(.*?);.*"), 2);
            switch (encryptionType.toLowerCase()) {
                case "wpa":
                    result.encryptionType = WPA;
                    break;
                case "wep":
                    result.encryptionType = WEP;
                    break;
            }
            return result;
        }

        public static Creator<WiFi> CREATOR = new AutoCreator<>(WiFi.class);
    }

    public static class UrlBookmark extends AutoSafeParcelable {
        @SafeParceled(1)
        final int versionCode = 1;
        @SafeParceled(2)
        public String title;
        @SafeParceled(3)
        public String url;

        public static UrlBookmark parse(String value) {
            UrlBookmark result = new UrlBookmark();
            result.title = extractMatch(value, Pattern.compile(".*?(TITLE|title):(.*?);.*"), 2);
            result.url = extractMatch(value, Pattern.compile(".*?(URL|url):(.*?);.*"), 2);
            return result;
        }

        public static Creator<UrlBookmark> CREATOR = new AutoCreator<>(UrlBookmark.class);
    }

    public static class GeoPoint extends AutoSafeParcelable {
        @SafeParceled(1)
        final int versionCode = 1;
        @SafeParceled(2)
        public double lat;
        @SafeParceled(3)
        public double lng;

        public GeoPoint(double lat, double lng) {
            this.lat = lat;
            this.lng = lng;
        }

        public GeoPoint() {}

        public static GeoPoint parse(String value) {
            GeoPoint result = new GeoPoint();
            int commaIndex = value.indexOf(',');
            // We need at least one number after the comma for longitude
            if (commaIndex == -1 || value.length() <= commaIndex) {
                return null;
            }
            try {
                result.lat = Double.parseDouble(value.substring(0, commaIndex));
                result.lng = Double.parseDouble(value.substring(commaIndex + 1));
            } catch(NumberFormatException e) {
                return null;
            }
            return result;
        }

        public static Creator<GeoPoint> CREATOR = new AutoCreator<>(GeoPoint.class);
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

        public static Creator<CalendarDateTime> CREATOR = new AutoCreator<>(CalendarDateTime.class);
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

        public static Creator<CalendarEvent> CREATOR = new AutoCreator<>(CalendarEvent.class);
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

        public static Creator<PersonName> CREATOR = new AutoCreator<>(PersonName.class);
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

        public static Creator<Address> CREATOR = new AutoCreator<>(Address.class);
    }

    public static class DriverLicense extends AutoSafeParcelable {
        @SafeParceled(1)
        final int versionCode = 1;
        @SafeParceled(2)
        public String documentType;
        @SafeParceled(3)
        public String firstName;
        @SafeParceled(4)
        public String middleName;
        @SafeParceled(5)
        public String lastName;
        @SafeParceled(6)
        public String gender;
        @SafeParceled(7)
        public String addressStreet;
        @SafeParceled(8)
        public String addressCity;
        @SafeParceled(9)
        public String addressState;
        @SafeParceled(10)
        public String addressZip;
        @SafeParceled(11)
        public String licenseNumber;
        @SafeParceled(12)
        public String issueDate;
        @SafeParceled(13)
        public String expiryDate;
        @SafeParceled(14)
        public String birthDate;
        @SafeParceled(15)
        public String issuingCountry;

        public static Creator<DriverLicense> CREATOR = new AutoCreator<>(DriverLicense.class);
    }

    public static class ContactInfo extends AutoSafeParcelable {
        @SafeParceled(1)
        final int versionCode = 1;
        @SafeParceled(2)
        public Barcode.PersonName name;
        @SafeParceled(3)
        public String organization;
        @SafeParceled(4)
        public String title;
        @SafeParceled(5)
        public Barcode.Phone[] phones;
        @SafeParceled(6)
        public Barcode.Email[] emails;
        @SafeParceled(7)
        public String[] urls;
        @SafeParceled(8)
        public Barcode.Address[] addresses;

        public static Creator<ContactInfo> CREATOR = new AutoCreator<>(ContactInfo.class);
    }
}