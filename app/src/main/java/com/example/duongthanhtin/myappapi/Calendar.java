package com.example.duongthanhtin.myappapi;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Calendar extends  AppCompatActivity {
    String resultText;
    ImageView previewIv;
    View previewIMG;

    Button  btnCreat, btnCancel, btnScan;
    ImageButton btnEditSpinnerName, btnEditLocation1, btnEditLocation2;
    Spinner spinnerLocation, spinnerLocation2, spinnerName, spinnerTime, spinnerDate;
    java.util.Calendar calendar;
    public static final int CAMERA_REQUEST_CODE = 200;
    public static final int STORAGE_REQUEST_CODE = 400;
    public static final int IMAGE_PICK_GALLERY_CODE = 1000;
    public static final int IMAGE_PICK_CAMERA_CODE = 1001;
    String cameraPermission[];
    String storagePermission[];
    Uri image_uri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);


        // get some useful variables
        previewIv = (findViewById(R.id.imagevCalendar));
        previewIMG = (findViewById(R.id.previewimage));
        spinnerLocation = findViewById(R.id.spnLocation);
        spinnerLocation2 = findViewById(R.id.spnLocation2);
        spinnerName=findViewById(R.id.spnName);
        spinnerDate=findViewById(R.id.spnDate);
        spinnerTime=findViewById(R.id.spnTime);
        btnScan=findViewById(R.id.idBtnRescanCalendar);
        btnCreat=findViewById(R.id.idBtnCreateEvent);
        btnEditSpinnerName=findViewById(R.id.editName);
        btnEditLocation1=findViewById(R.id.editLocation);
        btnEditLocation2=findViewById(R.id.editLocation2);


        resultText = "";


        //Camera Permission
        cameraPermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};


        //Storage Permission
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};


        previewIMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageImportDialog();
            }
        });
        btnEditSpinnerName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                final EditText editTextName = new EditText(Calendar.this);
                editTextName.setText(spinnerName.getSelectedItem().toString());
                final ArrayList<String> arrEditText = new ArrayList<>();

                new AlertDialog.Builder(Calendar.this)
                        .setTitle("Change Name")
                        .setView(editTextName)

                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setPositiveButton("Change", new DialogInterface.OnClickListener() {
                            CardInformation cardInformation = new CardInformation();
                            @Override

                            public void onClick(DialogInterface dialog, int which) {
                            arrEditText.add(editTextName.getText().toString());
                            cardInformation.setEventNames(arrEditText);
                            PutDataInSpinner(cardInformation,spinnerName,3);
                            }
                        })
                        .show();
            }
        });
//        btnEditLocation1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v){
//
//                final EditText editTextLoc1 = new EditText(Calendar.this);
//                editTextLoc1.setText(spinnerLocation.getSelectedItem().toString());
//                final ArrayList<String> arrEditText = new ArrayList<>();
//
//                new AlertDialog.Builder(Calendar.this)
//                        .setTitle("Change Location")
//                        .setView(editTextLoc1)
//
//                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                            }
//                        })
//                        .setPositiveButton("Change", new DialogInterface.OnClickListener() {
//                            CardInformation cardInformation = new CardInformation();
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                                arrEditText.add(editTextLoc1.getText().toString());
//                                cardInformation.setAddresses(arrEditText);
//                                PutDataInSpinner(cardInformation,spinnerLocation,3);
//                            }
//                        })
//                        .show();
//            }
//        });
//        btnEditLocation2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v){
//
//                final EditText editTextLoc2 = new EditText(Calendar.this);
//                editTextLoc2.setText(spinnerLocation2.getSelectedItem().toString());
//                final ArrayList<String> arrEditText = new ArrayList<>();
//
//                new AlertDialog.Builder(Calendar.this)
//                        .setTitle("Change Location")
//                        .setView(editTextLoc2)
//
//                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                            }
//                        })
//                        .setPositiveButton("Change", new DialogInterface.OnClickListener() {
//                            CardInformation cardInformation = new CardInformation();
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                arrEditText.add(editTextLoc2.getText().toString());
//                                cardInformation.setAddresses(arrEditText);
//                                PutDataInSpinner(cardInformation,spinnerLocation2,3);
//                            }
//                        })
//                        .show();
//            }
//        });



        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                previewIMG.callOnClick();
            }
        });
        btnCreat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){
                final String strName = spinnerName.getSelectedItem().toString();
                final String strContent = spinnerLocation.getSelectedItem().toString();

                if(strName.equals("") || strContent.equals(""))
                {
                    Toast.makeText(Calendar.this,"Please select a valid field",Toast.LENGTH_SHORT).show();
                } else {
                    Intent calIntent = new Intent(Intent.ACTION_INSERT);
                    calIntent.setType("vnd.android.cursor.item/event");
                    calIntent.putExtra(CalendarContract.Events.TITLE, spinnerName.getSelectedItem().toString());
                    calIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, spinnerLocation.getSelectedItem().toString()+" " +spinnerLocation2.getSelectedItem().toString());
                    startActivity(calIntent);
                }
            }
        });
    }

    private void showImageImportDialog() {
        //item to display in Dialog
        String[] item = {"Camera", "Gallery"};
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        //set title
        dialog.setTitle("Select Image");
        dialog.setItems(item, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    //camera option clicked
                    if (!checkCameraPermission()) {
                        requestCameraPermission();
                    } else {
                        //permission allowed
                        pickCamera();
                    }
                }
                if (which == 1) {
                    //gallery clicked
                    if (!checkStoragePermission()) {
                        requestStoragePermission();
                    } else {
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
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CAMERA_CODE);
    }

    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(this, storagePermission, STORAGE_REQUEST_CODE);
    }

    private boolean checkStoragePermission() {
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    //Camera
    private void pickCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "NewPic"); //title of pic
        values.put(MediaStore.Images.Media.DESCRIPTION, "Image to Text");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA_CODE);
    }

    private boolean checkCameraPermission() {
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, cameraPermission, CAMERA_REQUEST_CODE);
    }

    //handle img result and Have text
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //got img from cmr
        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_PICK_GALLERY_CODE) {
                // crop image
                CropImage.activity(data.getData())
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(this);
            }
            if (requestCode == IMAGE_PICK_CAMERA_CODE) {

                CropImage.activity(image_uri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(this);

            }
        }

        //lay dc crop img
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                //ResultEt.setFocusableInTouchMode(true);
                Uri resultUri = result.getUri(); //lay img uri
                //  dat img vao img view
                previewIv.setImageURI(resultUri);
                // lay drawable bitmap de recognition
                BitmapDrawable bitmapDrawable = (BitmapDrawable) previewIv.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();

                TextRecognizer recognizer = new TextRecognizer.Builder(getApplicationContext()).build();
                if (!recognizer.isOperational()) {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                } else {
                    Frame frame = new Frame.Builder().setBitmap(bitmap).build();
                    SparseArray<TextBlock> items = recognizer.detect(frame);
                    StringBuilder sb = new StringBuilder();
                    //lay text tu string builder cho den khi het text
                    for (int i = 0; i < items.size(); i++) {
                        TextBlock myItem = items.valueAt(i);
                        sb.append(myItem.getValue());
                        sb.append("\n");
                    }
                    //gan text cho cai EditText
                    //ResultEt.setText(sb.toString());
                    resultText = sb.toString();

                    //TODO: Analyze the text by TextAnalyzer
                    //TODO: Set all the element of CardInformation into combox ( should use
                    //TODO: a method to bundle the process).
                    CardInformation cardInformation = new CardInformation();
                    TextAnalyzer textAnalyzer = new TextAnalyzer(resultText);
                    textAnalyzer.analyze();
                    cardInformation = textAnalyzer.getCardInformation();


                    //Log.d("My app","Dates: "+cardInformation.getDates());


                    //case 1: arrlist=cardInformation.getAddresses(); //Location
                    //case 2: arrlist=cardInformation.getEmails(); //Email
                    //case 3: arrlist=cardInformation.getEventNames(); //Event Name
                    //case 4: arrlist=cardInformation.getPhoneNumbers(); //Phone Numbers
                    //case 5: arrlist=cardInformation.getDates().toArray(); //Dates and Times

                    PutDataInSpinner(cardInformation, spinnerLocation, 1);
                    PutDataInSpinner(cardInformation, spinnerLocation2, 1);
                    PutDataInSpinner(cardInformation, spinnerName, 3);
                    PutDataInSpinner(cardInformation, spinnerTime,5);
                    PutDataInSpinner(cardInformation, spinnerDate,5);

                    //etName.setText(String.valueOf(cardInformation.getEventNames().get(1)));
                    //etContact.setText(String.valueOf(cardInformation.getEmails().get(1)));
                    //etContact.setText(String.valueOf(cardInformation.getPhoneNumbers().get(1)));
                    //etDate.setText(String.valueOf(cardInformation.getDates().get(1)));

                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                // hien thi loi neu co loi
                Exception error = result.getError();
                Toast.makeText(this, "" + error, Toast.LENGTH_SHORT).show();
            }
        }
    }

    // handler permission result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean writeStorageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted && writeStorageAccepted) {
                        pickCamera();
                    } else {
                        Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case STORAGE_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean writeStorageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (writeStorageAccepted) {
                        pickGallery();
                    } else {
                        Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }
    //Image
    public byte[] ImageView_To_Byte(Uri image_uri) {
        BitmapDrawable drawable = (BitmapDrawable) previewIv.getDrawable();
        Bitmap bmp = drawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    //ArrayAdapter
    private void PutDataInSpinner(CardInformation cardInformation, Spinner spinner, int typeofdata) {

        List<MyDateTime> arrlisttime = new ArrayList<>();
        List<String> arrlist = new ArrayList<>();
        ArrayAdapter<String> adapter;

        switch (typeofdata) {
            case 1: {

                arrlist = cardInformation.getAddresses(); //Location
                break;
            }
            case 2: {
                arrlist = cardInformation.getEmails(); //Email
                break;
            }
            case 3: {
                arrlist = cardInformation.getEventNames(); //Event Name
                break;
            }
            case 4: {
                arrlist = cardInformation.getPhoneNumbers(); //Phone Numbers
                break;

            }
            case 5: {
                arrlisttime=cardInformation.getDates(); //Dates
            }

        }

        if (typeofdata==5) {
            adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrlisttime);
        } else {
            adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrlist);
        }

        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    //Get spinner index
    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }

        return 0;
    }
}

