// INativeBarcodeDetector.aidl
package com.google.android.gms.vision.barcode.internal.client;

import com.google.android.gms.vision.barcode.internal.client.BarcodeDetectorOptions;
import com.google.android.gms.vision.barcode.internal.client.FrameMetadata;
import com.google.android.gms.dynamic.IObjectWrapper;

interface INativeBarcodeDetector {
    void unk1(IObjectWrapper unk1, in FrameMetadata metadata) = 1;
    void unk2(IObjectWrapper unk1, in FrameMetadata metadata) = 2;
}