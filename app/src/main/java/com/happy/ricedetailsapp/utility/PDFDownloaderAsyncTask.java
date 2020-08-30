package com.happy.ricedetailsapp.utility;

import java.io.File;
import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Loader;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.Toast;

import com.happy.ricedetailsapp.R;


public class PDFDownloaderAsyncTask extends AsyncTask<ArrayList<String>, Void, String> {

    private boolean isDownloadingPdf = false;
    private ProgressDialog progress;
    private File    file;
    private Context context;

    public PDFDownloaderAsyncTask (Context context, File file, ProgressDialog progress) {

        this.file = file;
        this.context = context;
        this.isDownloadingPdf = false;
        this.progress =progress;
    }

    public boolean isDownloadingPdf () {

        return this.isDownloadingPdf;
    }

    @Override
    protected void onPreExecute () {

        super.onPreExecute ();
        //show loader etc
    }

    @Override
    protected String doInBackground (ArrayList<String>... params) {

        isDownloadingPdf = true;
        File file = new File (params[0].get (0));
        String fileStatus = PdfDownloader.downloadFile (params[0].get (1),context);
        return fileStatus;
    }

    @Override
    protected void onPostExecute (String result) {

        super.onPostExecute (result);
//        Loader.hideLoader ();
        if ( !progress.isShowing() ) {
            return;
        }
        if (!TextUtils.isEmpty (result) && result.equalsIgnoreCase ("success")) {
            progress.dismiss();
            showPdf ();
        }
        else {
            isDownloadingPdf = false;
            progress.dismiss();
            Toast.makeText (context, context.getString (R.string.error_could_not_download_pdf), Toast.LENGTH_LONG).show ();
            file.delete ();
        }
    }

    @Override
    protected void onCancelled () {

        isDownloadingPdf = false;
        super.onCancelled ();
        //Loader.hideLoader ();
    }

    @Override
    protected void onCancelled (String s) {

        isDownloadingPdf = false;
        super.onCancelled (s);
        //Loader.hideLoader ();
    }

    private void showPdf () {

        new Handler ().postDelayed (new Runnable () {
            @Override
            public void run () {

                isDownloadingPdf = false;
                PdfDownloader.openPDFFile (context, file);
            }
        }, 1000);
    }
}
