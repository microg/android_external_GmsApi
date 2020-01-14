// INativeBarcodeDetectorCreator.aidl
package com.google.android.gms.vision.barcode.internal.client;

import com.google.android.gms.vision.barcode.internal.client.BarcodeDetectorOptions;
import com.google.android.gms.vision.barcode.internal.client.INativeBarcodeDetector;

interface INativeBarcodeDetectorCreator {
    INativeBarcodeDetector create(in BarcodeDetectorOptions options) = 0;
}
