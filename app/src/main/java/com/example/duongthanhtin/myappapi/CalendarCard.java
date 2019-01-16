package com.example.duongthanhtin.myappapi;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarCard extends  AppCompatActivity {

    Calendar mCalendar = Calendar.getInstance();

    String resultText;
    ImageView previewIv;
    View previewIMG;

    Button  btnCreat, btnCancel, btnScan;
    ImageButton btnEditSpinnerName, btnEditLocation1, btnEditLocation2, btnEditDate, btnEditTime;
    Spinner spinnerLocation, spinnerLocation2, spinnerName;
    EditText editTextTime, editTextDate;
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
        btnScan=findViewById(R.id.idBtnRescanCalendar);
        btnCreat=findViewById(R.id.idBtnCreateEvent);
        btnEditSpinnerName=findViewById(R.id.editName);
        btnEditLocation1=findViewById(R.id.editLocation);
        btnEditLocation2=findViewById(R.id.editLocation2);
        btnEditDate=findViewById(R.id.editDate);
        btnEditTime=findViewById(R.id.editTime);
        editTextDate=findViewById(R.id.editTextDate);
        editTextTime=findViewById(R.id.editTextTime);

        editTextTime.setKeyListener(null);
        editTextDate.setKeyListener(null);


        resultText = "";


        //Camera Permission
        cameraPermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};



        //Storage Permission
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        btnScan.setEnabled(false);
        // Enable if permission granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED) {
            btnScan.setEnabled(true);
        }
        // Else ask for permission
        else {
            ActivityCompat.requestPermissions(this, new String[]
                    { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }


        previewIMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageImportDialog();
            }
        });
        btnEditSpinnerName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                final EditText editTextName = new EditText(CalendarCard.this);
                editTextName.setText(spinnerName.getSelectedItem().toString());
                final ArrayList<String> arrEditText = new ArrayList<>();

                new AlertDialog.Builder(CalendarCard.this)
                        .setTitle("Change Name")
                        .setView(editTextName)

                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setPositiveButton("Change", new DialogInterface.OnClickListener() {
                            @Override

                            public void onClick(DialogInterface dialog, int which) {
                            arrEditText.add(editTextName.getText().toString());

                                ArrayAdapter<String> adapterName = new ArrayAdapter(CalendarCard.this, android.R.layout.simple_spinner_item,arrEditText);
                                adapterName.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                                spinnerName.setAdapter(adapterName);
                                spinnerName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });


                            }
                        })
                        .show();
            }
        });
        btnEditLocation1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                final EditText editTextLoc = new EditText(CalendarCard.this);
                editTextLoc.setText(spinnerLocation.getSelectedItem().toString());
                final ArrayList<String> arrEditText = new ArrayList<>();

                new AlertDialog.Builder(CalendarCard.this)
                        .setTitle("Change Location")
                        .setView(editTextLoc)

                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setPositiveButton("Change", new DialogInterface.OnClickListener() {
                            @Override

                            public void onClick(DialogInterface dialog, int which) {
                                arrEditText.add(editTextLoc.getText().toString());

                                ArrayAdapter<String> adapterLoc1 = new ArrayAdapter(CalendarCard.this, android.R.layout.simple_spinner_item,arrEditText);
                                adapterLoc1.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                                spinnerLocation.setAdapter(adapterLoc1);
                                spinnerLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });


                            }
                        })
                        .show();
            }
        });
        btnEditLocation2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                final EditText editTextLoc2 = new EditText(CalendarCard.this);
                editTextLoc2.setText(spinnerLocation2.getSelectedItem().toString());
                final ArrayList<String> arrEditText = new ArrayList<>();

                new AlertDialog.Builder(CalendarCard.this)
                        .setTitle("Change Location")
                        .setView(editTextLoc2)

                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setPositiveButton("Change", new DialogInterface.OnClickListener() {
                            @Override

                            public void onClick(DialogInterface dialog, int which) {
                                arrEditText.add(editTextLoc2.getText().toString());

                                ArrayAdapter<String> adapterLoc2 = new ArrayAdapter(CalendarCard.this, android.R.layout.simple_spinner_item,arrEditText);
                                adapterLoc2.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                                spinnerLocation2.setAdapter(adapterLoc2);
                                spinnerLocation2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });


                            }
                        })
                        .show();
            }
        });
        btnEditDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //TODO: display date time picker here
//                DatePickerDialog datePickerDialog = new DatePickerDialog(CalendarCard.this, new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//
//                        String mOnth = Integer.toString(monthOfYear);
//                        String yEar=Integer.toString(year);
//                        String dAy=Integer.toString(dayOfMonth);
//                        editTextDate.setText(dAy+"/"+mOnth+"/"+yEar);
//                    }
//                }, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH));
//                datePickerDialog.show();
                int year = mCalendar.get(Calendar.YEAR);
                int month = mCalendar.get(Calendar.MONTH);
                int day = mCalendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(CalendarCard.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        String monthstring = Integer.toString(monthOfYear + 1);
                        //Don't know why ~~
                        String yearstring=Integer.toString(year);
                        String daystring=Integer.toString(dayOfMonth);

                        mCalendar.set(year, monthOfYear, dayOfMonth);
                        editTextDate.setText(daystring+"/"+monthstring+"/"+yearstring);

                    }
                }, year, month, day);
                datePickerDialog.show();



            }
        });
        btnEditTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //TODO: display time picker here

                mCalendar.set(2019,12,29,12,45);
                final int year = mCalendar.get(Calendar.YEAR);
                final int month = mCalendar.get(Calendar.MONTH);
                final int day = mCalendar.get(Calendar.DAY_OF_MONTH);
                final int hour = mCalendar.get(Calendar.HOUR_OF_DAY);
                int minute = mCalendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(CalendarCard.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        mCalendar.set(year,month,day,hourOfDay,minute);
                        String hourstring=Integer.toString(hourOfDay);
                        String minutestring=Integer.toString(minute);

                        editTextTime.setText(hourstring+":"+minutestring);
                    }
                }, hour, minute, true);

                timePickerDialog.show();



            }
        });



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
                    Toast.makeText(CalendarCard.this,"Please select a valid field",Toast.LENGTH_SHORT).show();
                } else {
                    Intent calIntent = new Intent(Intent.ACTION_INSERT);
                    calIntent.setType("vnd.android.cursor.item/event");
                    calIntent.putExtra(CalendarContract.Events.TITLE, spinnerName.getSelectedItem().toString());
                    calIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, spinnerLocation.getSelectedItem().toString()+" " +spinnerLocation2.getSelectedItem().toString());
                    calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,mCalendar.getTimeInMillis());
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
                    //Log.d("My app","Dates: "+cardInformation.getDates().toString());

                    mCalendar.set(cardInformation.getDates().getYear(),cardInformation.getDates().getMonth(),cardInformation.getDates().getDay());
                    //mCalendar.set(cardInformation.getDates().getYear(),cardInformation.getDates().getMonth(),cardInformation.getDates().getDay(),cardInformation.getDates().getHour(),cardInformation.getDates().getMinute());


                    editTextDate.setText(cardInformation.getDates().toString());




                    //editTextTime.setText(cardInformation.getTimes().toString());


                    //Log.d("My app","Dates: "+cardInformation.getDates());


                    //case 1: arrlist=cardInformation.getAddresses(); //Location
                    //case 2: arrlist=cardInformation.getEmails(); //Email
                    //case 3: arrlist=cardInformation.getEventNames(); //Event Name
                    //case 4: arrlist=cardInformation.getPhoneNumbers(); //Phone Numbers
                    //case 5: arrlist=cardInformation.getDates().toArray(); //Dates and Times

                    PutDataInSpinner(cardInformation, spinnerLocation, 1);
                    PutDataInSpinner(cardInformation, spinnerLocation2, 1);
                    PutDataInSpinner(cardInformation, spinnerName, 3);

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
                //arrlist = cardInformation.getEmails(); //Email
                break;
            }
            case 3: {
                arrlist = cardInformation.getEventNames(); //Event Name
                break;
            }
//            case 4: {
//                List<MyDateTime> allObjects =cardInformation.getTimes(); //Times
//                arrlist.clear();
//                for (MyDateTime object : allObjects)
//                    if (object.isValidTime())
//                        arrlisttime.add(object);
//
//                if (arrlisttime.size() == 0)
//                    arrlisttime.add(new MyDateTime());
//                break;
//
//            }
//            case 5: {
//                //arrlisttime=cardInformation.getDates(); //Dates
////                List<MyDateTime> allObjects =cardInformation.getDates(); //Dates
////                arrlist.clear();
////                for (MyDateTime object : allObjects)
////                    if (object.isValidDay())
////                        arrlisttime.add(object);
////
////                if (arrlisttime.size() == 0)
////                    arrlisttime.add(new MyDateTime());
////                break;
            //}

        }

        if (typeofdata==5 || typeofdata==4) {
            adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrlisttime);
        }
        else {
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

