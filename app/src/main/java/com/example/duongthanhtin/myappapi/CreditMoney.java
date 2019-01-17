package com.example.duongthanhtin.myappapi;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.method.KeyListener;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.duongthanhtin.myappapi.DataBase.DataBase;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CreditMoney extends AppCompatActivity  {
    EditText ResultEt;
    ImageView PreviewIv;
    int SavedCount;
    View ImageView;
    Button btnOption, btnCall, btnRescan, btnSave;
    Calendar calendar;
    private int mYear, mMonth, mHour, mMinute, mDay;
    private String mTime;
    private String mDate;
    public static final int CAMERA_REQUEST_CODE=200;
    public static final int STORAGE_REQUEST_CODE=400;
    public static final int IMAGE_PICK_GALLERY_CODE=1000;
    public static final int IMAGE_PICK_CAMERA_CODE=1001;
    String cameraPermission[];
    String storagePermission[];
    Uri image_uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_money);

        ResultEt =(findViewById(R.id.resultEt));
        PreviewIv = (findViewById(R.id.imageIv));
        ImageView=(findViewById(R.id.imagelvView));
        calendar = Calendar.getInstance();

        //Camera Permission
        cameraPermission = new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};

        //Storage Permission
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        //Button Option
        btnOption = (Button) findViewById(R.id.idBtnOption);
        btnCall =(Button)findViewById(R.id.idBtnCall);
        btnRescan=(Button)findViewById(R.id.idBtnRescan);
        btnSave=(Button)findViewById(R.id.idBtnSave);

        //Count saved or not
        SavedCount=0;

        //DataBase
        final DataBase db = new DataBase(getApplicationContext(),"Subject_History",null,1);
        db.QueryData("CREATE TABLE IF NOT EXISTS Table_works(Id INTEGER PRIMARY KEY AUTOINCREMENT,Name VARCHAR(150),Time VARCHAR(150),Day VARCHAR(150),Image BLOB)");

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CreditMoney.this,"Saving...",Toast.LENGTH_SHORT).show();
                mHour = calendar.get(Calendar.HOUR_OF_DAY);
                mMinute = calendar.get(Calendar.MINUTE);
                mYear = calendar.get(Calendar.YEAR);
                mMonth = calendar.get(Calendar.MONTH) + 1;
                mDay = calendar.get(Calendar.DATE);

                mDate = mDay + "/" + mMonth + "/" + mYear;
                mTime = mHour + ":" + mMinute;

                String Name = ResultEt.getText().toString();
                String Time = mTime.toString();
                String Date = mDate.toString();
                if(Name.equals(""))
                {
                    Toast.makeText(CreditMoney.this,"Please insert data",Toast.LENGTH_SHORT).show();
                }
                else {

                    db.INSERT_SUBJECT(
                            Name,
                            Time,
                            Date,
                            ImageView_To_Byte(image_uri)

                    );
                    Toast.makeText(CreditMoney.this,"Saved",Toast.LENGTH_SHORT).show();
                    SavedCount++;
                }
            }
        });


        btnOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageImportDialog();

            }
        });
        btnRescan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageImportDialog();
            }
        });



        //database.QueryData("INSERT INTO Table_works VALUES(null,'Mobile','11h','Monday')");
        final Dialog dialog=new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        final Date today=new Date(System.currentTimeMillis());
        final SimpleDateFormat timeFormat= new SimpleDateFormat("hh:mm:ss dd/MM/yyyy");

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SavedCount < 1) {


                    mHour = calendar.get(Calendar.HOUR_OF_DAY);
                    mMinute = calendar.get(Calendar.MINUTE);
                    mYear = calendar.get(Calendar.YEAR);
                    mMonth = calendar.get(Calendar.MONTH) + 1;
                    mDay = calendar.get(Calendar.DATE);

                    mDate = mDay + "/" + mMonth + "/" + mYear;
                    mTime = mHour + ":" + mMinute;

                    String Name = ResultEt.getText().toString();
                    String Time = mTime.toString();
                    String Date = mDate.toString();
                    if (Name.equals("")) {
                        Toast.makeText(CreditMoney.this, "Please insert data", Toast.LENGTH_SHORT).show();
                    } else {

                        db.INSERT_SUBJECT(
                                Name,
                                Time,
                                Date,
                                ImageView_To_Byte(image_uri)
                        );
                        btnSave.setVisibility(View.INVISIBLE);
                        SavedCount++;
                        //Call
                        String number1 = new String("*100*");
                        String number = ResultEt.getText().toString();
                        String txtCode = "#";
                        txtCode = number1 + number + txtCode;
                        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", txtCode, null)));

                    }

                } else {
                    //Call
                    String number1 = new String("*100*");
                    String number = ResultEt.getText().toString();
                    String txtCode = "#";
                    txtCode = number1 + number + txtCode;
                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", txtCode, null)));


                }
            }

        });

        ImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnOption.callOnClick();
            }
        });
    }

    private void showImageImportDialog()
    {
        //item to display in Dialog
        String[] item ={"Camera", "Gallery"};
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        //set title
        dialog.setTitle("Select Image");
        dialog.setItems(item, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which==0){
                    //camera option clicked
                    if(!checkCameraPermission()){
                        requestCameraPermission();
                    }
                    else{
                        //permission allowed
                        pickCamera();
                    }
                }
                if(which==1){
                    //gallery clicked
                    if(!checkStoragePermission()){
                        requestStoragePermission();
                    }
                    else{
                        //permission allowed
                        pickGallery();

                    }
                }
            }
        });
        dialog.create().show();
    }

    //Image Gallery
    private void pickGallery() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_PICK_GALLERY_CODE);
    }

    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(this,storagePermission,STORAGE_REQUEST_CODE);
    }

    private boolean checkStoragePermission() {
        boolean result= ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)==(PackageManager.PERMISSION_GRANTED);
        return result;
    }

    //Camera
    private void pickCamera() {
        ContentValues values =new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"NewPic"); //title of pic
        values.put(MediaStore.Images.Media.DESCRIPTION,"Image to Text");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent,IMAGE_PICK_CAMERA_CODE);
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this,cameraPermission,CAMERA_REQUEST_CODE);
    }

    private boolean checkCameraPermission() {
        boolean result= ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)==(PackageManager.PERMISSION_GRANTED);
        boolean result1= ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)==(PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }

    // handler permission result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean writeStorageAccepted = grantResults[0]==PackageManager.PERMISSION_GRANTED;
                    if(cameraAccepted&&writeStorageAccepted){
                        pickCamera();
                    }
                    else{
                        Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case STORAGE_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean writeStorageAccepted = grantResults[0]==PackageManager.PERMISSION_GRANTED;
                    if(writeStorageAccepted){
                        pickGallery();
                    }
                    else{
                        Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    //handle img result and get text
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        //got img from cmr
        if(resultCode==RESULT_OK){
            if(requestCode==IMAGE_PICK_GALLERY_CODE)
            {
                // crop image
                CropImage.activity(data.getData())
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(this);
            }
            if (requestCode==IMAGE_PICK_CAMERA_CODE){

                CropImage.activity(image_uri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(this);

            }
        }

        //lay dc crop img
        if(requestCode== CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode==RESULT_OK)
            {
                btnRescan.setVisibility(View.VISIBLE);
                btnSave.setVisibility(View.VISIBLE);
                btnOption.setVisibility(View.INVISIBLE);
                btnCall.setVisibility(View.VISIBLE);
                ResultEt.setFocusableInTouchMode(true);
                Uri resultUri = result.getUri(); //lay img uri
                //  dat img vao img view
                PreviewIv.setImageURI(resultUri);
                // lay drawable bitmap de recognition
                BitmapDrawable bitmapDrawable =(BitmapDrawable)PreviewIv.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();

                TextRecognizer recognizer = new TextRecognizer.Builder(getApplicationContext()).build();
                if(!recognizer.isOperational()){
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                }
                else {
                    Frame frame = new Frame.Builder().setBitmap(bitmap).build();
                    SparseArray<TextBlock> items = recognizer.detect(frame);
                    StringBuilder sb = new StringBuilder();
                    //lay text tu string builder cho den khi het text
                    for(int i =0;i<items.size();i++)
                    {
                        TextBlock myItem= items.valueAt(i);
                        sb.append(myItem.getValue());
                        sb.append("\n");
                    }
                    //gan text cho cai EditText
                    ResultEt.setText(sb.toString());
                }
            }
            else if(resultCode==CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE)
            {
                // hien thi loi neu co loi
                Exception error = result.getError();
                Toast.makeText(this, ""+error, Toast.LENGTH_SHORT).show();
            }
        }
    }

    //Image
    public byte[] ImageView_To_Byte(Uri image_uri){
        BitmapDrawable drawable= (BitmapDrawable) PreviewIv.getDrawable();
        Bitmap bmp = drawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100,stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
}
