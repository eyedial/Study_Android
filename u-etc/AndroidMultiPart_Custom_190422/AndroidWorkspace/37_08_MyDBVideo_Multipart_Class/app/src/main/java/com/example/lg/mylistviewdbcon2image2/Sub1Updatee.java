package com.example.lg.mylistviewdbcon2image2;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.core.content.FileProvider;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.lg.mylistviewdbcon2image2.ATask.ListUpdate;
import com.example.lg.mylistviewdbcon2image2.Common.CommonMethod;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import static com.example.lg.mylistviewdbcon2image2.Common.CommonMethod.ipConfig;
import static com.example.lg.mylistviewdbcon2image2.Common.CommonMethod.isNetworkConnected;

public class Sub1Updatee extends AppCompatActivity {

    EditText etId, etName;
    String id, name, date;

    DatePicker DPdate;
    Button photoBtn, photoLoad;

    Button btnVideo, btnVideoLoad;

    int year, month, day;

    ImageView imageView;
    VideoView videoView;
    MediaController m;

    public String imagePath;
    public String uploadType, pUploadType;
    public String selVideoImage;
    public String pUploadPathU;
    public String imageFilePathU = "", imageUploadPathU;
    public String videoFilePathU = "", videoUploadPathU;

    final int CAMERA_REQUEST = 1010;
    final int LOAD_IMAGE = 1011;
    final int VIDEO_REQUEST = 1013;
    final int LOAD_VIDEO = 1014;

    File file = null;
    long fileSize = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub1_updatee);

        etId = findViewById(R.id.etUId);
        etName = findViewById(R.id.etUName);
        DPdate = findViewById(R.id.DPUpdate);
        photoBtn = findViewById(R.id.btnPhoto);
        btnVideo = findViewById(R.id.btnVideo);
        photoLoad = findViewById(R.id.btnLoad);

        btnVideoLoad = findViewById(R.id.btnVideoLoad);

        imageView = findViewById(R.id.imageView);
        videoView = findViewById(R.id.videoView);
        videoViewSetting();

        imageView.setVisibility(View.VISIBLE);
        videoView.setVisibility(View.GONE);

        // ??????????????? ????????? ????????????
        etId.setEnabled(false);

        // ????????? ??? ??????
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        name = intent.getStringExtra("name");

        // ?????? ????????????
        date = intent.getStringExtra("date");
        String[] temp = date.split("-");
        year = Integer.parseInt(temp[0]);
        month = Integer.parseInt(temp[1]);
        day = Integer.parseInt(temp[2]);

        // ????????? ??? ??? ??????
        etId.setText(id);
        etName.setText(name);
        DPdate.updateDate(year, month - 1, day);

        // datePicker is changed and into date value
        DPdate.init(DPdate.getYear(), DPdate.getMonth(), DPdate.getDayOfMonth(),
                new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker datePicker, int year, int month, int day) {

                        date = String.valueOf(year) + "/" + String.valueOf(month + 1) + "/"
                                + String.valueOf(day);
                    }
                });

        imagePath = intent.getStringExtra("img");
        uploadType = intent.getStringExtra("uploadType");
        selVideoImage = intent.getStringExtra("selVideoImage");

        //Log.d("Sub1Update11", imagePath);
        //uploadPathU = imagePath;
        pUploadType = uploadType;
        pUploadPathU = imagePath;
        //imageFilePathU = imagePath;
        imageUploadPathU = imagePath;
        //videoFilePathU = imagePath;
        videoUploadPathU = imagePath;


        if(uploadType.equals("image")) {
            videoView.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
            //ImageSize targetSize1 = new ImageSize(150, 150);
            ImageLoader.getInstance().displayImage(imagePath, imageView);

        }else if(uploadType.equals("video")){
            videoView.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageResource(R.drawable.video);

            ImageLoader.getInstance().displayImage(selVideoImage, imageView, new ImageLoadingListener() {
                        @Override
                        public void onLoadingStarted(String s, View view) {
                            Log.d("Sub1Update:String s", s);
                        }

                        @Override
                        public void onLoadingFailed(String s, View view, FailReason failReason) {
                            Log.d("Sub1Update:ImageFail", failReason.getCause().toString());
                        }

                        @Override
                        public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                        }

                        @Override
                        public void onLoadingCancelled(String s, View view) {
                        }
                    });

        }

        photoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    try{
                        file = createFile();
                        Log.d("Sub1Update:FilePath ", file.getAbsolutePath());

                    }catch(Exception e){
                        Log.d("Sub1Update:error1", "Something Wrong", e);
                    }

                    imageView.setVisibility(View.VISIBLE);
                    videoView.setVisibility(View.GONE);

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    /*intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));*/
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,
                            FileProvider.getUriForFile(getApplicationContext(), "My37_CaptureIntent.fileprovider", file));
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(intent, CAMERA_REQUEST);
                    }

                }catch(Exception e){
                    Log.d("Sub1Update:error2", "Something Wrong", e);
                }

            }
        });


        photoLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setVisibility(View.VISIBLE);
                videoView.setVisibility(View.GONE);

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), LOAD_IMAGE);
            }
        });

        btnVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.setVisibility(View.GONE);
                videoView.setVisibility(View.VISIBLE);

                Intent intent=new Intent("android.media.action.VIDEO_CAPTURE");
//                intent.putExtra("android.intent.extra.durationLimit", VIDEO_DURATION);
//                intent.putExtra("android.intent.extra.videoQuality",1);
                intent.putExtra(android.provider.MediaStore.EXTRA_SIZE_LIMIT,30485760);
                intent.putExtra(android.provider.MediaStore.EXTRA_VIDEO_QUALITY,-1);

                startActivityForResult(intent, VIDEO_REQUEST);
            }
        });

        btnVideoLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.setVisibility(View.GONE);
                videoView.setVisibility(View.VISIBLE);

                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, LOAD_VIDEO);

            }
        });

    }

    private void videoViewSetting() {
        m = new MediaController(this);
        m.setVisibility(View.GONE);
        videoView.setMediaController(m);
    }


    private File createFile() throws IOException {
        java.text.SimpleDateFormat tmpDateFormat = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss");

        String imageFileName = "My" + tmpDateFormat.format(new Date()) + ".jpg";
        File storageDir = Environment.getExternalStorageDirectory();
        File curFile = new File(storageDir, imageFileName);

        return curFile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            uploadType = "image";

            try {
                // ????????? ????????? ??? ????????????
                Bitmap newBitmap = CommonMethod.imageRotateAndResize(file.getAbsolutePath());
                if(newBitmap != null){
                    imageView.setImageBitmap(newBitmap);
                }else{
                    Toast.makeText(this, "???????????? null ?????????...", Toast.LENGTH_SHORT).show();
                }

                imageFilePathU = file.getAbsolutePath();
                String uploadFileName = imageFilePathU.split("/")[imageFilePathU.split("/").length - 1];
                imageUploadPathU = ipConfig + "/app/resources/" + uploadFileName;

                ImageView imageView = (ImageView) findViewById(R.id.imageView);
                imageView.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));

                Log.d("Sub1Update:picPath", file.getAbsolutePath());

            } catch (Exception e){
                e.printStackTrace();
            }
        }else if (requestCode == LOAD_IMAGE && resultCode == RESULT_OK) {
            uploadType = "image";

            try {
                String path = "";
                // Get the url from data
                Uri selectedImageUri = data.getData();
                if (selectedImageUri != null) {
                    // Get the path from the Uri
                    path = getPathFromURI(selectedImageUri);
                    //Log.d("Main", "Image Path : " + path);
                }

                // ????????? ????????? ??? ????????????
                Bitmap newBitmap = CommonMethod.imageRotateAndResize(path);
                if(newBitmap != null){
                    imageView.setImageBitmap(newBitmap);
                }else{
                    Toast.makeText(this, "???????????? null ?????????...", Toast.LENGTH_SHORT).show();
                }

                imageFilePathU = path;
                String uploadFileName = imageFilePathU.split("/")[imageFilePathU.split("/").length - 1];
                imageUploadPathU = ipConfig + "/app/resources/" + uploadFileName;

            } catch (Exception e){
                e.printStackTrace();
            }
        }else if ((requestCode == VIDEO_REQUEST || requestCode == LOAD_VIDEO) && resultCode == RESULT_OK) {
            uploadType = "video";

            try {
                String path = "";
                // Get the url from data
                Uri selectedVideoUri = data.getData();

                if (null != selectedVideoUri) {
                    // Get the path from the Uri
                    path = getPathFromURI(selectedVideoUri);
                    Log.d("Sub1Update", path);
                }

                File file = new File(path);
                fileSize = file.length();
                Log.d("Sub1Update:fileSize", "" + fileSize);

                videoFilePathU = path;
                String uploadFileName = videoFilePathU.split("/")[videoFilePathU.split("/").length - 1];
                videoUploadPathU = ipConfig + "/app/resources/" + uploadFileName;

                Log.d("Sub1Update9", path + " : " + videoFilePathU);
                Log.d("Sub1Update10", path + " : " + videoUploadPathU);

                videoView.setVideoURI(selectedVideoUri);
                videoView.start();

                videoView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        m.show(0);
                        videoView.pause();
                    }
                }, 1000);

            } catch (Exception e){
                e.printStackTrace();
            }
        }        else {
            Log.d("Main => ", "imagepath is null, whatever something is wrong!!");
        }
    }

    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    public void btnUpdateClicked(View view){
        if(isNetworkConnected(this) == true){
            if(fileSize <= 30000000) {  // ??????????????? 30?????? ?????? ????????? ????????? ?????? ??????
                id = etId.getText().toString();
                name = etName.getText().toString();

                ListUpdate listUpdate = new ListUpdate(id, name, date, uploadType, pUploadType, pUploadPathU,
                                                imageUploadPathU, imageFilePathU, videoUploadPathU, videoFilePathU);
                listUpdate.execute();

                //Toast.makeText(getApplicationContext(), "????????????", Toast.LENGTH_LONG).show();

                Intent showIntent = new Intent(getApplicationContext(), Sub1.class);
                showIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |   // ??? ???????????? ???????????? ???????????? ??????????????? ???????????? ?????? ????????? ???????????? ???????????? ??? ??????????????? ??????????????? ???????????? ?????????. ???, ????????? ???????????? ?????????????????? ??????????????? ??????????????? ????????? affinity(??????, ??????)??? ????????? ?????? ???????????? ????????? ???????????? ??? ??????????????? ?????????????????????.
                        Intent.FLAG_ACTIVITY_SINGLE_TOP | // ??????????????? ????????? ?????? ????????? ??????????????? ?????? ???????????? ???????????? ???????????? ???????????? ????????? ??????????????? ???????????? ????????????. ?????? ?????? ABC??? ???????????? ????????? ???????????? ???????????? C??? ?????????????????? ????????? ABC??? ???????????? ?????????.
                        Intent.FLAG_ACTIVITY_CLEAR_TOP); // ????????? ????????????????????? ??????????????? ??????????????? ??????????????? ?????? ???????????? ?????? ????????? ????????? ??????????????? ???????????? ??? ????????? ???????????? ?????? ??????????????? ?????????????????? ???????????????. ????????? ????????????????????? ????????? ?????????????????? ?????????????????? ????????? ????????????????????? ?????? ??????????????? ???????????????.
                startActivity(showIntent);

                finish();
            }else{
                // ????????? ??????
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("??????");
                builder.setMessage("?????? ????????? 30MB???????????? ????????? ???????????? ???????????? ????????????.\n30MB?????? ????????? ????????? ????????????!!!");
                builder.setPositiveButton("???", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
            }

        }else {
            Toast.makeText(this, "???????????? ???????????? ?????? ????????????.",
                    Toast.LENGTH_SHORT).show();
        }

    }

    public void btnCancelClicked(View view){
        finish();
    }

}
