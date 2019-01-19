package com.chia.myrecycleview.Login;

import android.graphics.Bitmap;
import android.util.Log;

import java.io.ByteArrayOutputStream;

public class Common {
    // Android官方模擬器連結本機web server可以直接使用 http://10.0.2.2
//	public final static String URL = "http://192.168.196.157:8080/ImageToJson_Login_Web/DataUploadServlet";
    public final static String URL = "http://10.0.2.2:8080/ImageToJson_Login_Web";
    public final static String PREF_FILE = "preference";
    private static final String TAG = "Common";

    public static byte[] bitmapToPNG(Bitmap srcBitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();//建一個空間等等放新轉的圖
        // 轉成PNG不會失真，所以quality參數值會被忽略
        //若轉會失真的就看要失真多少％的壓縮
        srcBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();//為了要"回傳"所以用"byteByteArray"好指定內存位置傳送
    }

    // 設定長寬不超過scaleSize
    public static Bitmap downSize(Bitmap srcBitmap, int newSize) {
        // 如果欲縮小的尺寸過小，就直接定為128
        if (newSize <= 50) {
            newSize = 128;
        }
        int srcWidth = srcBitmap.getWidth();
        int srcHeight = srcBitmap.getHeight();
        String text = "source image size = " + srcWidth + "x" + srcHeight;
        Log.d(TAG, text);
        int longer = Math.max(srcWidth, srcHeight);

        if (longer > newSize) {
            double scale = longer / (double) newSize;
            int dstWidth = (int) (srcWidth / scale);
            int dstHeight = (int) (srcHeight / scale);
            srcBitmap = Bitmap.createScaledBitmap(srcBitmap, dstWidth, dstHeight, false);
            System.gc();
            text = "\nscale = " + scale + "\nscaled image size = " +
                    srcBitmap.getWidth() + "x" + srcBitmap.getHeight();
            Log.d(TAG, text);
        }
        return srcBitmap;
    }
}
