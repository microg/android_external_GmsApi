package com.google.android.gms.vision.barcode.internal.client;

import android.os.RemoteException;
import android.util.Log;

import com.google.android.gms.dynamic.IObjectWrapper;

public class BarcodeDetector extends INativeBarcodeDetector.Stub {
    // TODO: Can we force these to be a bitfield somehow?
    public static final int CODE_128 = 1;
    public static final int CODE_39 = 2;
    public static final int CODE_93 = 4;
    public static final int CODABAR = 8;
    public static final int DATA_MATRIX = 16;
    public static final int EAN_13 = 32;
    public static final int EAN_8 = 64;
    public static final int ITF = 128;
    public static final int QR_CODE = 256;
    public static final int UPC_A = 512;
    public static final int UPC_E = 1024;
    public static final int PDF417 = 2048;

    private int formats;

    public BarcodeDetector(BarcodeDetectorOptions options) {
        this.formats = options.formats;
    }

    @Override
    public void unk1(IObjectWrapper unk1, FrameMetadata metadata) throws RemoteException {
        Log.d("barcoder", "Unknown 1 called");
    }

    @Override
    public void unk2(IObjectWrapper unk1, FrameMetadata metadata) throws RemoteException {
        Log.d("barcoder", "Unknown 2 called");
    }
}
