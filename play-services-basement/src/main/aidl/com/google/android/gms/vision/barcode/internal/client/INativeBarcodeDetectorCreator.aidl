// INativeBarcodeDetectorCreator.aidl
package com.google.android.gms.vision.barcode.internal.client;

import com.google.android.gms.vision.barcode.internal.client.BarcodeDetectorOptions;
import com.google.android.gms.vision.barcode.internal.client.INativeBarcodeDetector;
import com.google.android.gms.dynamic.IObjectWrapper;

interface INativeBarcodeDetectorCreator {
    // unk is probably Context
    INativeBarcodeDetector create(IObjectWrapper unk, in BarcodeDetectorOptions options) = 0;
}
