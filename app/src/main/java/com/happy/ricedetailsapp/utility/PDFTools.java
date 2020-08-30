package com.happy.ricedetailsapp.utility;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import com.happy.ricedetailsapp.R;

import java.io.File;
import java.util.ArrayList;

public class PDFTools {
    private static final String GOOGLE_DRIVE_PDF_READER_PREFIX = "http://drive.google.com/viewer?url=";
    private static final String PDF_MIME_TYPE = "application/pdf";
    private static final String HTML_MIME_TYPE = "text/html";


    public static void showPDFUrl(final Context context, final String pdfUrl ) {
        if ( isPDFSupported( context ) ) {
            downloadAndOpenPDF(context, pdfUrl);
        } else {
            askToOpenPDFThroughGoogleDrive( context, pdfUrl );
        }
    }
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static void downloadAndOpenPDF(final Context context, final String pdfUrl) {
        String fileName = pdfUrl.substring(pdfUrl.lastIndexOf("/")+1,pdfUrl.indexOf("?"));
        File tempFile =new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS
        ), "specs.pdf");
        if (tempFile.exists () && tempFile.length () > 0&&(!DashboardRepository.getString(context,"pdf_key","").isEmpty()
                &&DashboardRepository.getString(context,"pdf_key","").equals(fileName))) {
            PdfDownloader.openPDFFile (context, tempFile);
        }
        else {
            ArrayList<String> fileNameAndURL = new ArrayList<>();
            fileNameAndURL.add(tempFile.toString());
            fileNameAndURL.add(pdfUrl);
            final ProgressDialog progress = ProgressDialog.show( context, context.getString( R.string.pdf_show_local_progress_title ), context.getString( R.string.pdf_show_local_progress_content ), true );
//            if (pdfDownloaderAsyncTask == null) {
            PDFDownloaderAsyncTask pdfDownloaderAsyncTask = new PDFDownloaderAsyncTask(context, tempFile, progress);
//            }
            if (DashboardRepository.isNetworkAvailable(context)) {
                if (!pdfDownloaderAsyncTask.isDownloadingPdf()) {
                    pdfDownloaderAsyncTask = new PDFDownloaderAsyncTask(context, tempFile, progress);
                    pdfDownloaderAsyncTask.execute(fileNameAndURL);
                }
            } else {
                //show error
            }
        }
    }
    public static void askToOpenPDFThroughGoogleDrive( final Context context, final String pdfUrl ) {
        new AlertDialog.Builder( context )
                .setTitle( R.string.pdf_show_online_dialog_title )
                .setMessage( R.string.pdf_show_online_dialog_question )
                .setNegativeButton( R.string.pdf_show_online_dialog_button_no, null )
                .setPositiveButton( R.string.pdf_show_online_dialog_button_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        openPDFThroughGoogleDrive(context, pdfUrl);
                    }
                })
                .show();
    }

    public static void openPDFThroughGoogleDrive(final Context context, final String pdfUrl) {
        Intent i = new Intent( Intent.ACTION_VIEW );
        i.setDataAndType(Uri.parse(GOOGLE_DRIVE_PDF_READER_PREFIX + pdfUrl ), HTML_MIME_TYPE );
        context.startActivity( i );
    }
    public static boolean isPDFSupported( Context context ) {
        Intent i = new Intent( Intent.ACTION_VIEW );
        final File tempFile = new File( context.getExternalFilesDir( Environment.DIRECTORY_DOWNLOADS ), "test.pdf" );
        i.setDataAndType( Uri.fromFile( tempFile ), PDF_MIME_TYPE );
        return context.getPackageManager().queryIntentActivities( i, PackageManager.MATCH_DEFAULT_ONLY ).size() > 0;
    }

}