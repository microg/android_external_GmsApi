package com.google.android.gms.vision.barcode.internal.client;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.RemoteException;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.datamatrix.DataMatrixReader;
import com.google.zxing.oned.CodaBarReader;
import com.google.zxing.oned.Code128Reader;
import com.google.zxing.oned.Code39Reader;
import com.google.zxing.oned.Code93Reader;
import com.google.zxing.oned.EAN13Reader;
import com.google.zxing.oned.EAN8Reader;
import com.google.zxing.oned.ITFReader;
import com.google.zxing.oned.UPCAReader;
import com.google.zxing.oned.UPCEReader;
import com.google.zxing.pdf417.PDF417Reader;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.RGBLuminanceSource;

public class BarcodeDetector extends INativeBarcodeDetector.Stub {
    public static final String TAG = BarcodeDetector.class.getSimpleName();

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
    public void unk0(IObjectWrapper byteBuffer, FrameMetadata metadata) throws RemoteException {
        Log.i(TAG, "Unkown method 0 called");
    }

    @Override
    public Barcode[] detect(IObjectWrapper wrappedBitmap, FrameMetadata metadata) throws RemoteException {
        Bitmap bitmap = ObjectWrapper.unwrapTyped(wrappedBitmap, Bitmap.class);
        if (bitmap == null) {
            Log.e(TAG, "Could not unwrap Bitmap");
            return null;
        }

        IntBuffer frameBuf = IntBuffer.allocate(bitmap.getByteCount());
        bitmap.copyPixelsToBuffer(frameBuf);
        int[] frameBytes = frameBuf.array();

        RGBLuminanceSource source = new RGBLuminanceSource(bitmap.getHeight(), bitmap.getWidth(), frameBytes);
        HybridBinarizer binarizer = new HybridBinarizer(source);
        BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
        List<Barcode> barcodes = new ArrayList<>();

        if ((this.formats & CODE_128) != 0) {
            tryDetect(new Code128Reader(), binaryBitmap, barcodes);
        }
        if ((this.formats & CODE_39) != 0) {
            tryDetect(new Code39Reader(), binaryBitmap, barcodes);
        }
        if ((this.formats & CODE_93) != 0) {
            tryDetect(new Code93Reader(), binaryBitmap, barcodes);
        }
        if ((this.formats & CODABAR) != 0) {
            tryDetect(new CodaBarReader(), binaryBitmap, barcodes);
        }
        if ((this.formats & DATA_MATRIX) != 0) {
            tryDetect(new DataMatrixReader(), binaryBitmap, barcodes);
        }
        if ((this.formats & EAN_13) != 0) {
            tryDetect(new EAN13Reader(), binaryBitmap, barcodes);
        }
        if ((this.formats & EAN_8) != 0) {
            tryDetect(new EAN8Reader(), binaryBitmap, barcodes);
        }
        if ((this.formats & ITF) != 0) {
            tryDetect(new ITFReader(), binaryBitmap, barcodes);
        }
        if ((this.formats & QR_CODE) != 0) {
            tryDetect(new QRCodeReader(), binaryBitmap, barcodes);
        }
        if ((this.formats & UPC_A) != 0) {
            tryDetect(new UPCAReader(), binaryBitmap, barcodes);
        }
        if ((this.formats & UPC_E) != 0) {
            tryDetect(new UPCEReader(), binaryBitmap, barcodes);
        }
        if ((this.formats & PDF417) != 0) {
            tryDetect(new PDF417Reader(), binaryBitmap, barcodes);
        }

        return barcodes.toArray(new Barcode[0]);
    }

    @Override
    public void unk2(IObjectWrapper unk1, FrameMetadata metadata) throws RemoteException {
        Log.d(TAG, "Unknown 2 called");
    }

    private void tryDetect(Reader reader, BinaryBitmap bitmap, List<Barcode> results) {
        try {
            Result result = reader.decode(bitmap);
            if (result.getText() != null) {
                // Try to fill in the corner information, not sure that this is correct.
                List<Point> corners = new ArrayList<>();
                for (ResultPoint point : result.getResultPoints()) {
                    corners.add(new Point((int)point.getX(), (int)point.getY()));
                }
                results.add(new Barcode(result.getText(), corners.toArray(new Point[]{})));
            }
        } catch (FormatException e) {
            e.printStackTrace();
        } catch (ChecksumException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            Log.d(TAG, "No barcode found when detecting with " + reader.getClass().getName());
        }
    }
}
