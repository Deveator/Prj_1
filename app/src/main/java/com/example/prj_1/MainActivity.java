package com.example.prj_1;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import com.example.prj_1.CuView.DrawRect;
import com.example.prj_1.ImageActions.OriginalImage;
import com.example.prj_1.CuView.DrawRect.*;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import static com.example.prj_1.CuView.DrawRect.*;

public class MainActivity extends AppCompatActivity {

    ImageView iV, iV2, iV3, iV4;
    Button bttn, bttn2, bttn3;
    public static String path;
    Mat oImage, oImage2;
    static int mark = 0;
    DrawRect drawRect;
    public static int x1, y1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iV = findViewById(R.id.imageV);
        iV2 = findViewById(R.id.imageV2);
        iV3 = findViewById(R.id.imageV3);
        iV4 = findViewById(R.id.imageV4);
        bttn = findViewById(R.id.button1);
        bttn2 = findViewById(R.id.button2);
        bttn3 = findViewById(R.id.button3);
        drawRect = findViewById(R.id.imageV5);

        System.loadLibrary("opencv_java3");

        if (OpenCVLoader.initDebug()) {
            System.out.println(555);
        }
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

            oImage = OriginalImage.GetOriginalImage(path, 4, 4);
            //  oImage2 = OriginalImage.GetOriginalImage(path, 4, 4);

            switch (mark) {

                case 0:
                    displayImage(oImage);
                    break;
                case 1:
                    Mat sMat = oImage.submat(200, 300, 0, 100);
                    Imgproc.blur(sMat, sMat, new Size(3, 3));
                    //     displayImage(setBlack(GrayImage.GetGrayImage(sMat)));
                    break;
                case 2:
                    Mat sMat2 = oImage.submat(200, 300, 101, 290);
                    Imgproc.blur(sMat2, sMat2, new Size(3, 3));
                    //   displayImage(setBlack2(GrayImage.GetGrayImage(sMat2)));
                    break;
                case 3:
                    Mat sMat3 = oImage.submat(100, 300, 291, 389);
                    //   displayImage(FromWhiteToBlackBackgrnd.getFromWhiteToBlackBackgrnd(sMat3));
                    break;
            }
            mark++;
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

        Bitmap bitmap = Bitmap.createBitmap(mat.cols(), mat.rows(), Bitmap.Config.RGB_565);

        Utils.matToBitmap(mat, bitmap);

        if (mark == 0) {
            iV.setImageBitmap(bitmap);
            matImg1 = mat;
            System.out.println(matImg1.cols() + " cols");
            System.out.println(matImg1.rows() + " rows");
        }

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

    public void actionAny(View v){
        DrawRect.getCoord(1);
    }


}
