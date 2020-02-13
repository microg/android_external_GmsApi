package com.google.android.gms.vision.barcode.internal.client;

import com.google.android.gms.vision.barcode.internal.client.BarcodeDetectorOptions;
import com.google.android.gms.vision.barcode.internal.client.FrameMetadata;
import com.google.android.gms.vision.barcode.internal.client.Barcode;
import com.google.android.gms.dynamic.IObjectWrapper;

interface INativeBarcodeDetector {
    void unk0(IObjectWrapper byteBuffer, in FrameMetadata metadata) = 0;
    Barcode[] detect(IObjectWrapper bitmap, in FrameMetadata metadata) = 1;
    // Probably wraps a ByteBuffer? but is otherwise the same functionality?
    void unk2(IObjectWrapper unk1, in FrameMetadata metadata) = 2;
}