package tech.myevents.hq;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

class UploadBroadcastVideo extends AsyncTask<String, Integer, Integer>{

    private ProgressDialog dialog;
    private NotificationManager notificationManager;
    NotificationCompat.Builder builder;
    SharedPreferences prefs;
    private SharedPreferences prefsAd;
    private SharedPreferences.Editor editorAd;
    private SharedPreferences.Editor editor;
    Context context;
    private String mediaName;
    private String selectedVideoPath;
    private String notificationTitle;
    private String notificationTicker;
    private String notificationConTextStart;
    private String notificationConTextEnd;
    private String from;
    private int notificationId = 6236;

    UploadBroadcastVideo(Context context, String mediaName, String notificationConTextEnd,
                                String notificationConTextStart, String notificationTicker,
                                String notificationTitle, String selectedVideoPath, String from) {
        this.context = context;
        this.mediaName = mediaName;
        this.notificationConTextEnd = notificationConTextEnd;
        this.notificationConTextStart = notificationConTextStart;
        this.notificationTicker = notificationTicker;
        this.notificationTitle = notificationTitle;
        this.selectedVideoPath = selectedVideoPath;
        this.from = from;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        prefs = context.getSharedPreferences(App.AppInfo.PREF_BROADCAST_VALUES, Context.MODE_PRIVATE);
        prefsAd = context.getSharedPreferences(App.AppInfo.PREF_AD_VALUES, Context.MODE_PRIVATE);
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        builder = new NotificationCompat.Builder(context);
        builder.setContentTitle(notificationTitle)
                .setContentText(notificationConTextStart)
                .setTicker(notificationTicker)
                .setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_big));
        builder.setProgress(100,0,true);
        notificationManager.notify(notificationId,builder.build());
        dialog = ProgressDialog.show(context,"","Uploading Video To Broadcast Server ...",true);
    }

    @Override
    protected Integer doInBackground(String... params) {
        String SERVER_URL = App.AppInfo.VID_UPLOAD_URL;
        int serverResponseCode = 0;

        HttpURLConnection connection;
        DataOutputStream dataOutputStream;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";


        int bytesRead,bytesAvailable,bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File selectedVideoFile = new File(selectedVideoPath);

        try{
            FileInputStream fileInputStream = new FileInputStream(selectedVideoFile);
            URL url = new URL(SERVER_URL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);//Allow Inputs
            connection.setDoOutput(true);//Allow Outputs
            connection.setUseCaches(false);//Don't use a cached Copy
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("ENCTYPE", "multipart/form-data");
            connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            connection.setRequestProperty("uploaded_file",selectedVideoPath);
            connection.setChunkedStreamingMode(maxBufferSize);
            dataOutputStream = new DataOutputStream(connection.getOutputStream());

            dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
            dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\"" + mediaName + "\"" + lineEnd);

            dataOutputStream.writeBytes(lineEnd);
            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable,maxBufferSize);
            buffer = new byte[bufferSize];

            bytesRead = fileInputStream.read(buffer,0,bufferSize);
            int total = 0;
            long fileLength = fileInputStream.available();

            while (bytesRead > 0){
                total += bytesRead;
                int progress = (int) ((total / (float) fileLength) * 10);
                dataOutputStream.write(buffer,0,bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable,maxBufferSize);
                bytesRead = fileInputStream.read(buffer,0,bufferSize);

                publishProgress(progress);
            }
            dataOutputStream.writeBytes(lineEnd);
            dataOutputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            serverResponseCode = connection.getResponseCode();
            String serverResponseMessage = connection.getResponseMessage();

            fileInputStream.close();
            dataOutputStream.flush();
            dataOutputStream.close();
        } catch (FileNotFoundException e) {
            //
        } catch (MalformedURLException e) {
            //
        } catch (IOException e) {
            //
        }
        dialog.dismiss();

        return serverResponseCode;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        builder.setProgress(100,values[0],true);
        notificationManager.notify(notificationId,builder.build());
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Integer serverResponseCode) {
        super.onPostExecute(serverResponseCode);
        builder.setOngoing(false);
        //response code of 200 indicates the server status OK
        if(serverResponseCode == 200){
            Toast.makeText(context, "Broadcast Successful", Toast.LENGTH_LONG).show();
            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_big));
            builder.setTicker(notificationTicker);
            builder.setContentText(notificationConTextEnd);
            builder.setProgress(0,0,false);
            notificationManager.notify(notificationId,builder.build());

            if (from.equals("event")) {
                deleteBroadcastValues();
            } else if (from.equals("ad")) {
                deleteAdValues();
            }
        } else {
            builder.setContentText("Video Upload Failed");
            builder.setProgress(0,0,false);
            notificationManager.notify(notificationId,builder.build());
        }
    }

    private void deleteAdValues() {
        editorAd = prefsAd.edit();
        editorAd.clear().apply();
        File videoFile = new File("sdcard/MyEvents/PreBroadcast/pre_ad.mp4");
        videoFile.delete();
        File imageFile = new File("sdcard/MyEvents/PreBroadcast/pre_ad.JPEG");
        imageFile.delete();
        //context.startActivity(new Intent(context,MakeAd.class));
    }

    private void deleteBroadcastValues() {
        editor = prefs.edit();
        editor.clear().apply();
        File videoFile = new File("sdcard/MyEvents/PreBroadcast/pre_broadcast.mp4");
        videoFile.delete();
        File imageFile = new File("sdcard/MyEvents/PreBroadcast/pre_broadcast.JPEG");
        imageFile.delete();
        //context.startActivity(new Intent(context,MakeEvent.class));
    }

}