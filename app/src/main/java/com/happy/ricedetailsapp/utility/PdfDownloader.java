package com.happy.ricedetailsapp.utility;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.happy.ricedetailsapp.BuildConfig;

import java.net.URL;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

public class PdfDownloader {
    private static final int MEGABYTE = 1024 * 1024;
    private static File dest;
    public static String downloadFile (String fileUrl) {

        String downloadStatus;
        try {

            URL url = new URL (fileUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection ();
            urlConnection.connect ();

            InputStream inputStream = urlConnection.getInputStream ();
            dest =new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOCUMENTS
            ), "specs.pdf");
            try {
                dest.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            FileOutputStream fileOutputStream = new FileOutputStream (dest);
            int totalSize = urlConnection.getContentLength ();

            Log.d ("PDF", "Total size: " + totalSize);
            byte[] buffer = new byte[MEGABYTE];
            int bufferLength = 0;
            while ((bufferLength = inputStream.read (buffer)) > 0) {
                fileOutputStream.write (buffer, 0, bufferLength);
            }
            downloadStatus = "success";
            fileOutputStream.close ();
        }
        catch (FileNotFoundException e) {
            downloadStatus = "FileNotFoundException";
            e.printStackTrace ();
        }
        catch (MalformedURLException e) {
            downloadStatus = "MalformedURLException";
            e.printStackTrace ();
        }
        catch (IOException e) {
            downloadStatus = "IOException";
            e.printStackTrace ();
        }
        Log.d ("PDF", "Download Status: " + downloadStatus);
        return downloadStatus;
    }


    public static void openPDFFile(Context context, File file) {

        Intent intent = new Intent (Intent.ACTION_VIEW);
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
             uri = FileProvider.getUriForFile(context,
                    BuildConfig.APPLICATION_ID + ".provider",
                     file);
            context.grantUriPermission(BuildConfig.APPLICATION_ID, uri,Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(file);
        }
        Log.d ("PDF", "Download size: " + file.length());
        intent.setDataAndType (uri, "application/pdf");
        intent.setFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP);
        try {
            if(file.length()>0)
            context.startActivity (intent);
        }
        catch (ActivityNotFoundException e) {
            e.printStackTrace();
//            Toast.makeText (context, context.getString (R.string.txt_no_pdf_available), Toast.LENGTH_SHORT).show ();
        }
    }

}
