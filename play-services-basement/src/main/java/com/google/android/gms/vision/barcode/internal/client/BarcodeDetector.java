package com.google.android.gms.vision.barcode.internal.client;

import android.os.RemoteException;
import android.util.Log;

import com.google.android.gms.dynamic.IObjectWrapper;

public class BarcodeDetector extends INativeBarcodeDetector.Stub {
    @Override
    public void unk1(IObjectWrapper unk1, FrameMetadata metadata) throws RemoteException {
        Log.d("barcoder", "Unknown 1 called");
    }

    @Override
    public void unk2(IObjectWrapper unk1, FrameMetadata metadata) throws RemoteException {
        Log.d("barcoder", "Unknown 2 called");
    }
}
