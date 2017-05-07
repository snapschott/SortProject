package com.example.andrew.testapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.LinearLayout;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;
    ImageView iv;
    LinearLayout llayout;
    String mCurrentPhotoPath;
    String urlString = "http://api.cloudsightapi.com";
    File thePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        setContentView(R.layout.change_screen);
        //Log.d("fuck","fucking");

        iv = (ImageView) findViewById(R.id.m_image_view);

        //LinearLayout llayout = (LinearLayout) findViewById(R.id.change_screen);
        llayout = (LinearLayout) findViewById(R.id.change_screen);
        llayout.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Log.d("FUCK OFF","FUCK OFF");
                setContentView(R.layout.activity_main);
                dispatchTakePictureIntent();

            }
        });
    }

    /*
    private void dispatchTakePictureIntent(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    */



    private void dispatchTakePictureIntent(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //Ensure that there's a camera activity to handle the intent
        if(takePictureIntent.resolveActivity(getPackageManager()) != null){
            //Create the File where the photo should go
            File photoFile = null;
            try{
                photoFile = createImageFile();
            } catch(IOException ex){
                //Error occurred while creating the File
            }
            //Continue only if the File was successfully created
            if(photoFile != null){
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            //Bundle extras = data.getExtras();
            //Bitmap imageBitmap = (Bitmap) extras.get("data");
            //iv.setImageBitmap(imageBitmap);
            Uri photoPath = Uri.parse(mCurrentPhotoPath);
            //iv.setImageURI(photoPath);
            //setContentView(llayout);


            try {
                //InputStream image_stream = getContentResolver().openInputStream(photoPath);
                //Bitmap imageBM = BitmapFactory.decodeStream(image_stream);
                Log.d("Worked", "Got bitmap!!!");
                Bitmap imageBM = BitmapFactory.decodeFile(mCurrentPhotoPath);
                iv.setImageBitmap(imageBM);
                setContentView(llayout);
                File img = new File(mCurrentPhotoPath);
                sendPhoto(imageBM, img);
            } catch(Exception e){
                Log.d("Messup", e.toString());
            }

        }
    }

    private void sendPhoto(Bitmap imageBM, File img)
    {
        String[] params = new String[1];
        params[0] = urlString;
        new HttpCallsClass(imageBM, img).execute(params);


        //Bitmap bitmap =

        //try {
            /*
            final String boundary = "==================";
            final String twoHyphens = "--";
            final String crlf = "\r\n";
            final String mimeType = "image/jpeg";
            final int IMAGE_QUALITY = 100;

            URL url = null;
            HttpURLConnection urlConnection = null;
            DataOutputStream dos;
            String response = null;

            try {
                url = new URL(urlString + "/image_requests/");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);     ///
                urlConnection.setRequestProperty("Authorization", "CloudSight 5l5nJ2_Uc4SHC37eeF1uow");
                urlConnection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
                //urlConnection.setRequestProperty("Content-Type", "image/jpeg");
                urlConnection.setRequestMethod("POST");

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                imageBM.compress(Bitmap.CompressFormat.JPEG, IMAGE_QUALITY, stream);
                byte[] byteArray = stream.toByteArray();

                dos = new DataOutputStream(urlConnection.getOutputStream());
                dos.writeBytes(twoHyphens + boundary + crlf);
                dos.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\"" + "theBitmap" + "\"" + crlf);
                dos.writeBytes("Content-Type: " + mimeType + crlf);
                dos.writeBytes(crlf);
                dos.write(byteArray);
                dos.writeBytes(crlf);
                dos.writeBytes(twoHyphens + boundary + twoHyphens);
                dos.flush();
                dos.close();

                String line = null;
                InputStreamReader isr = new InputStreamReader(urlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                response = sb.toString();

                isr.close();
                reader.close();

            } catch (MalformedURLException e) {
                e.printStackTrace();
                response = "Malformed URL";

            } catch (IOException e) {
                e.printStackTrace();
                response = "IO Exception";
            }

            //return response;
            Log.d("RESPONSE: ", response);
            */


            /*
            FileInputStream fileInputStream = new FileInputStream(img);
            URL url = new URL(urlString + "/image_requests/");
            Http
            */




            /*
            String attachmentName = "bitmap";
            String attachmentFileName = "bitmap.bmp";
            String crlf = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";

            //add a property called authorization with the key!
            URL url = new URL(urlString + "/image_requests/");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //conn.setReadTimeout(10000);
            //conn.setConnectTimeout(15000);
            conn.setRequestProperty("Authorization", "CloudSight 5l5nJ2_Uc4SHC37eeF1uow");
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Cache-Control", "no-cache");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

            DataOutputStream request = new DataOutputStream(conn.getOutputStream());
            request.writeBytes(twoHyphens + boundary + crlf);
            request.writeBytes("Content-Disposition: form-data; name=\"" + attachmentName + "\";filename=\"" + attachmentFileName + "\"" + crlf);
            request.writeBytes(crlf);
            */

        /*
        } catch(Exception d){
            Log.d("SendError", d.toString());
        }
        */
    }

    private File createImageFile() throws IOException {
        //Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        //Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

}
