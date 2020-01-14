package com.google.android.gms.vision.barcode.internal.client;

import org.microg.safeparcel.AutoSafeParcelable;
import org.microg.safeparcel.SafeParceled;

public class BarcodeDetectorOptions extends AutoSafeParcelable {
    @SafeParceled(1)
    private int versionCode = 1;
    @SafeParceled(2)
    private int options;

    public static Creator<BarcodeDetectorOptions> CREATOR = new AutoCreator<BarcodeDetectorOptions>(BarcodeDetectorOptions.class);

}
