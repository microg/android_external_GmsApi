import com.google.android.gms.vision.barcode.internal.client.Barcode;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

/**
 * Verifies that we make a reasonable effort to decode the different barcode types that google
 * defines. Most of the barcode data comes from these sites:
 * * http://goqr.me/
 * * https://github.com/zxing/zxing/wiki/Barcode-Contents
 * * https://github.com/codebude/QRCoder/wiki/Advanced-usage---Payload-generators
 */
@RunWith(RobolectricTestRunner.class)
public class BarcodeTypesTest {
    @Test
    public void geoPoints() {
        Barcode barcode = new Barcode("geo:12.334,13.337");
        assertThat(barcode.valueFormat).isEqualTo(Barcode.GEO);
        assertThat(barcode.rawValue).isEqualTo("geo:12.334,13.337");
        assertThat(barcode.geoPoint.lat).isEqualTo(12.334);
        assertThat(barcode.geoPoint.lng).isEqualTo(13.337);

        barcode = new Barcode("geo:12.no,13.yes");
        assertThat(barcode.valueFormat).isEqualTo(Barcode.TEXT);
        assertThat(barcode.rawValue).isEqualTo("geo:12.no,13.yes");
        assertThat(barcode.geoPoint).isNull();
    }

    @Test
    public void phoneNumber() {
        Barcode barcode = new Barcode("tel:+12387784");
        assertThat(barcode.valueFormat).isEqualTo(Barcode.PHONE);
        assertThat(barcode.phone).isNotNull();
        assertThat(barcode.phone.number).isEqualTo("+12387784");
        assertThat(barcode.phone.type).isEqualTo(Barcode.Phone.UNKNOWN);

        barcode = new Barcode("tel:+2763test");
        assertThat(barcode.valueFormat).isEqualTo(Barcode.PHONE);
        assertThat(barcode.phone).isNotNull();
        assertThat(barcode.phone.number).isEqualTo("+2763test");
        assertThat(barcode.phone.type).isEqualTo(Barcode.Phone.UNKNOWN);
    }

    @Test
    public void mail() {
        Barcode barcode = new Barcode("mailto:test@lel.com");
        assertThat(barcode.valueFormat).isEqualTo(Barcode.EMAIL);
        assertThat(barcode.email).isNotNull();
        assertThat(barcode.email.address).isEqualTo("test@lel.com");
        assertThat(barcode.email.type).isEqualTo(Barcode.Email.UNKNOWN);
        assertThat(barcode.email.body).isNull();
        assertThat(barcode.email.subject).isNull();

        barcode = new Barcode("mailto:test@lel.com?subject=hello");
        assertThat(barcode.valueFormat).isEqualTo(Barcode.EMAIL);
        assertThat(barcode.email).isNotNull();
        assertThat(barcode.email.address).isEqualTo("test@lel.com");
        assertThat(barcode.email.type).isEqualTo(Barcode.Email.UNKNOWN);
        assertThat(barcode.email.body).isNull();
        assertThat(barcode.email.subject).isEqualTo("hello");

        barcode = new Barcode("mailto:test@lel.com?body=have a good day");
        assertThat(barcode.valueFormat).isEqualTo(Barcode.EMAIL);
        assertThat(barcode.email).isNotNull();
        assertThat(barcode.email.address).isEqualTo("test@lel.com");
        assertThat(barcode.email.type).isEqualTo(Barcode.Email.UNKNOWN);
        assertThat(barcode.email.body).isEqualTo("have a good day");
        assertThat(barcode.email.subject).isNull();

        barcode = new Barcode("mailto:test@lel.com?body=have a good day&subject=this is a header");
        assertThat(barcode.valueFormat).isEqualTo(Barcode.EMAIL);
        assertThat(barcode.email).isNotNull();
        assertThat(barcode.email.address).isEqualTo("test@lel.com");
        assertThat(barcode.email.type).isEqualTo(Barcode.Email.UNKNOWN);
        assertThat(barcode.email.body).isEqualTo("have a good day");
        assertThat(barcode.email.subject).isEqualTo("this is a header");
    }

    @Test
    public void sms() {
        Barcode barcode = new Barcode("sms:+91827454");
        assertThat(barcode.valueFormat).isEqualTo(Barcode.SMS);
        assertThat(barcode.sms).isNotNull();
        assertThat(barcode.sms.phoneNumber).isEqualTo("+91827454");
        assertThat(barcode.sms.message).isNull();

        barcode = new Barcode("sms:91827454?body=have a good day");
        assertThat(barcode.valueFormat).isEqualTo(Barcode.SMS);
        assertThat(barcode.sms).isNotNull();
        assertThat(barcode.sms.phoneNumber).isEqualTo("91827454");
        assertThat(barcode.sms.message).isEqualTo("have a good day");

        barcode = new Barcode("sms:91827454,have a good day");
        assertThat(barcode.valueFormat).isEqualTo(Barcode.SMS);
        assertThat(barcode.sms).isNotNull();
        assertThat(barcode.sms.phoneNumber).isEqualTo("91827454");
        assertThat(barcode.sms.message).isEqualTo("have a good day");

        barcode = new Barcode("smsto:91827454:have a good day");
        assertThat(barcode.valueFormat).isEqualTo(Barcode.SMS);
        assertThat(barcode.sms).isNotNull();
        assertThat(barcode.sms.phoneNumber).isEqualTo("91827454");
        assertThat(barcode.sms.message).isEqualTo("have a good day");
    }

    @Test
    public void wifi() {
        Barcode barcode = new Barcode("wifi:T:wpa;S:network;P:password;;");
        assertThat(barcode.valueFormat).isEqualTo(Barcode.WIFI);
        assertThat(barcode.wifi).isNotNull();
        assertThat(barcode.wifi.encryptionType).isEqualTo(Barcode.WiFi.WPA);
        assertThat(barcode.wifi.password).isEqualTo("password");
        assertThat(barcode.wifi.ssid).isEqualTo("network");

        // TOOD: More variations here
    }
}