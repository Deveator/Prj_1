package com.example.prj_1;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.prj_1.CuView.DrawRect;
import com.example.prj_1.CuView.OneBall;
import com.example.prj_1.ImageActions.OriginalImage;
import com.example.prj_1.CuView.DrawRect.*;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.example.prj_1.CuView.DrawRect.*;
import static org.opencv.imgproc.Imgproc.THRESH_BINARY;
import static org.opencv.imgproc.Imgproc.THRESH_TOZERO;

public class MainActivity extends AppCompatActivity {

    ImageView iV, iV2, iV3, iV4;
    public TextView tView;
    Button bttn, bttn2, bttn3, bttn4, bttn5;
    public static String path;
    Mat oImage, oImage2, oImage3;
    static int mark = 0;
    DrawRect drawRect;
    public static int x1, y1;
    public static int widthDisp, heightDisp, screenWidth;
    Bitmap bitmap;
    private int thresholdValue = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iV = findViewById(R.id.imageV);
    //    iV2 = findViewById(R.id.imageV2);
   //     iV3 = findViewById(R.id.imageV3);
    //    iV4 = findViewById(R.id.imageV4);
        bttn = findViewById(R.id.button1);
        bttn2 = findViewById(R.id.button2);
        bttn3 = findViewById(R.id.button3);
        bttn4 = findViewById(R.id.button4);
        bttn5 = findViewById(R.id.button5);
        drawRect = findViewById(R.id.imageV5);
        tView = findViewById(R.id.tW);

        System.loadLibrary("opencv_java3");

        if (OpenCVLoader.initDebug()) {
            System.out.println(555);
        }
/*
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        heightDisp = displaymetrics.heightPixels;
        widthDisp = displaymetrics.widthPixels;
        System.out.println(heightDisp + " H");
        System.out.println(widthDisp + " W");
*/
    }

    public void openGallery(View v) {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101 && resultCode == RESULT_OK && data != null) {

            Uri imageUri = data.getData();

            path = getPath(imageUri);
            System.out.println(00 - 00 - 00);
            System.out.println(path);
            // if()

            //  oImage = OriginalImage.GetOriginalImage(path, 1, 1);
            oImage = OriginalImage.GetOriginalImage2(path);

                    //  Mat img = new Mat();
                    /*
                    Mat kern = new Mat(3, 3, CvType.CV_8S);
                    int row = 0, col = 0;
                    kern.put(row, col, 0, -1, 0, -1, 5, -1, 0, -1, 0);// '0, -1, 0, -1, 5, -1, 0, -1, 0' is dataSet to get 2D image
                    //   Imgproc.cvtColor(mImg, mImg, Imgproc.COLOR_BGR2GRAY);
                    Imgproc.filter2D(oImage3, oImage3, oImage3.depth(), kern);

                  ///  Imgproc.filter2D(oImage3, img, oImage3.depth(), kern);
*/
                    Imgproc.cvtColor(oImage, oImage, Imgproc.COLOR_BGR2GRAY);
                    // Imgproc.Canny(oImage3, oImage3, 50, 100 * 2);

                    /// Imgproc.cvtColor(oImage3, oImage3, Imgproc.COLOR_BGR2HSV);
                    displayImage(oImage);
                    System.out.println(oImage.rows() + " rows");
                    System.out.println(oImage.cols() + " clmns");
        }
    }

    private String getPath(Uri uri) {
        if (uri == null) {
            return null;
        } else {

            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(uri, projection, null, null, null);

            if (cursor != null) {
                int col_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();

                return cursor.getString(col_index);
            }
        }
        return uri.getPath();
    }

    Mat matImg1, matImg2, matImg3, matImg4;

    private void displayImage(Mat mat) {

        bitmap = Bitmap.createBitmap(mat.cols(), mat.rows(), Bitmap.Config.RGB_565);

        Utils.matToBitmap(mat, bitmap);

      ///  if (mark == 0) {
            iV.setImageBitmap(bitmap);
            matImg1 = mat;

            System.out.println(matImg1.cols() + " cols");
            System.out.println(matImg1.rows() + " rows");
            System.out.println(iV.getWidth() + " width");
            System.out.println(iV.getHeight() + " height");
            screenWidth = iV.getWidth();

   //     }

        if (mark == 1) {
            iV2.setImageBitmap(bitmap);
            matImg2 = mat;
        }
        if (mark == 2) {
            iV3.setImageBitmap(bitmap);
            matImg3 = mat;
        }
        if (mark == 3) {
            iV4.setImageBitmap(bitmap);
            matImg4 = mat;
        }
    }
/*
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        int eventAction = event.getAction();

        switch (eventAction) {
            case MotionEvent.ACTION_MOVE:

                int thickness = -1;
                int lineType = 8;
                int shift = 0;
                Point center = new Point(event.getX(), event.getY());
                Imgproc.circle(oImage, center, 400 / 32, new Scalar(0, 0, 255), thickness, lineType, shift);

                break;

        }


        return true;

    }
    */


    public void actionAny(View v) {
        DrawRect.getCoord(1);
    }

    public void save(View view) {
        // START creating directory for images
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/App_images");
        if (!myDir.exists()) {
            myDir.mkdir();
        }
        // STOP creating directory for images
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-" + n + ".jpg";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void transform(View view) {
        // get coordinate s for subMatco
        DrawRect.getCoord(1);

        // block to get coord os ROI
        ArrayList<Integer> full_x_ROIcoord = new ArrayList<>();
        ArrayList<Integer> full_y_ROIcoord = new ArrayList<>();

        if (xRed < 0 || xRed > 750 || yRed < 0 || yRed > 1000 ||
                xOrg < 0 || xOrg > 750 || yOrg < 0 || yOrg > 1000 ||
                xYell < 0 || xYell > 750 || yYell < 0 || yYell > 1000 ||
                xGreen < 0 || xGreen > 750 || yGreen < 0 || yGreen > 1000) {
            return;
        } else {
            // clear situation with x<= xYell
            for (int x = xRed; x < xYell; x++) {
                for (int y = yRed; y < yYell; y++) {
                    full_x_ROIcoord.add(x);
                    full_y_ROIcoord.add(y);
                }
            }
        }
        // end block

        // block get contour via CannyFilter
        Mat sMat = oImage.submat(yRed, yGreen, xRed, xOrg);
        Imgproc.Canny(sMat, sMat, 25, 100 * 2);
        ArrayList<Double> subMatValue = new ArrayList<>();

        for (int x = 0; x < sMat.cols(); x++) {
            for (int y = 0; y < sMat.rows(); y++) {
                double[] ft2 = sMat.get(y, x);
                subMatValue.add(ft2[0]);
            }
        }
        int count = 0;
        for (int x = xRed; x < xYell; x++) {
            for (int y = yRed; y < yYell; y++) {
                oImage.put(y, x, subMatValue.get(count));
                count++;
            }
        }
        // end block

        displayImage(oImage);
    }
}
