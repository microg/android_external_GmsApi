package com.google.android.gms.vision.barcode.internal.client;

import org.microg.safeparcel.AutoSafeParcelable;
import org.microg.safeparcel.SafeParceled;

public class FrameMetadata extends AutoSafeParcelable {
    @SafeParceled(1)
    private final int versionCode = 1;
    @SafeParceled(2)
    public int width;
    @SafeParceled(3)
    public int height;
    @SafeParceled(4)
    public int id;
    @SafeParceled(5)
    public long timestampMillis;
    @SafeParceled(6)
    public int rotation;

    public static Creator<FrameMetadata> CREATOR = new AutoCreator<FrameMetadata>(FrameMetadata.class);
}
