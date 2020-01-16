package com.google.android.gms.vision.barcode.internal.client;

import org.microg.safeparcel.AutoSafeParcelable;
import org.microg.safeparcel.SafeParceled;

public class BarcodeDetectorOptions extends AutoSafeParcelable {
    @SafeParceled(1)
    public int versionCode = 1;
    @SafeParceled(2)
    public int formats;

    public static Creator<BarcodeDetectorOptions> CREATOR = new AutoCreator<BarcodeDetectorOptions>(BarcodeDetectorOptions.class);

}
