package com.google.android.gms.vision.barcode.internal.client;

import com.google.android.gms.vision.barcode.internal.client.BarcodeDetectorOptions;
import com.google.android.gms.vision.barcode.internal.client.FrameMetadata;
import com.google.android.gms.dynamic.IObjectWrapper;

interface INativeBarcodeDetector {
    // IObjectWrapper probably wraps a Bitmap
    void detect(IObjectWrapper bitmap, in FrameMetadata metadata) = 1;
    void unk2(IObjectWrapper unk1, in FrameMetadata metadata) = 2;
}