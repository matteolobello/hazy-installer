package net.hazyrom.hazyinstaller;

// Android imports
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;

// SuperToast import
import com.github.johnpersano.supertoasts.SuperToast;

// Apache imports
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

// Java imports
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Vector;

public class ViewPagerManager extends FragmentActivity {

    // Supported devices
    private static final String S3 = "m0xx";
    private static final String SECONDS3 = "GT-I9300";
    private static final String NEXUS4 = "Nexus 4";
    private static final String NEXUS5 = "Nexus 5";
    private static final String NEXUS6 = "Nexus 6";
    private static final String NEXUS10 = "Nexus 10";
    private static final String TAB = "p3100";
    private static final String ONEPLUSONE = "A0001";
    private static final String G3 = "LG-D855";
    private static final String G2 = "LG-D802";
    private static final String S2 = "i9100";
    private static final String YU = "tomato";
    private static final String M8 = "m8";
    private static final String MOTOG2014 = "falcon";
    private static final String NOTE2 = "GT-N7100";
    private static final String FIND7 = "find7";

    // Server links
    private final String MAKO_REMOTE_PATH = "http://hazyrom.net/download/rom/extra/ota/mako";
    private final String HAMMERHEAD_REMOTE_PATH = "http://hazyrom.net/download/rom/extra/ota/hammerhead";
    private final String SHAMU_REMOTE_PATH = "http://hazyrom.net/download/rom/extra/ota/shamu";
    private final String ONE_REMOTE_PATH = "http://hazyrom.net/download/rom/extra/ota/bacon";
    private final String TAB_REMOTE_PATH = "http://hazyrom.net/download/rom/extra/ota/p3100";
    private final String S3_REMOTE_PATH = "http://hazyrom.net/download/rom/extra/ota/i9300";
    private final String MANTA_REMOTE_PATH = "http://hazyrom.net/download/rom/extra/ota/manta";
    private final String G2_REMOTE_PATH = "http://hazyrom.net/download/rom/extra/ota/d802";
    private final String G3_REMOTE_PATH = "http://hazyrom.net/download/rom/extra/ota/d855";
    private final String S2_REMOTE_PATH = "http://hazyrom.net/download/rom/extra/ota/i9100";
    private final String YU_REMOTE_PATH = "http://hazyrom.net/download/rom/extra/ota/tomato";
    private final String FIND7_REMOTE_PATH = "http://hazyrom.net/download/rom/extra/ota/find7";
    private final String M8_REMOTE_PATH = "http://hazyrom.net/download/rom/extra/ota/m8";
    private final String MOTG2014_REMOTE_PATH = "http://hazyrom.net/download/rom/extra/ota/motog2014";
    private final String NOTE2_REMOTE_PATH = "http://hazyrom.net/download/rom/extra/ota/n7100";
    private final String GAPPS_REMOTE_PATH = "http://hazyrom.net/download/rom/extra/ota/gapps";

    // Download links
    private String s3DownloadROM;
    private String nexus4DownloadROM;
    private String nexus5DownloadROM;
    private String nexus6DownloadROM;
    private String tabDownloadROM;
    private String onePlusOneDownloadROM;
    private String nexus10DownloadROM;
    private String g2DownloadROM;
    private String g3DownloadROM;
    private String s2DownloadROM;
    private String yuDownloadROM;
    private String find7DownloadROM;
    private String m8DownloadROM;
    private String motoG2014DownloadROM;
    private String n7100DownloadROM;
    private String gappsDownloadLink;

    // File links
    private String nexus4LinkFile = "/sdcard/HazyInstaller/nexus4Link";
    private String nexus5LinkFile = "/sdcard/HazyInstaller/nexus5Link";
    private String nexus6LinkFile =  "/sdcard/HazyInstaller/nexus6Link";
    private String nexus10LinkFile = "/sdcard/HazyInstaller/nexus10Link";
    private String oneLinkFile = "/sdcard/HazyInstaller/oneLink";
    private String s3LinkFile = "/sdcard/HazyInstaller/s3Link";
    private String tabLinkFile = "/sdcard/HazyInstaller/tabLink";
    private String g2LinkFile = "/sdcard/HazyInstaller/g2Link";
    private String g3LinkFile = "/sdcard/HazyInstaller/g3Link";
    private String s2LinkFile = "/sdcard/HazyInstaller/s2Link";
    private String yuLinkFile = "/sdcard/HazyInstaller/yuLink";
    private String find7LinkFile = "/sdcard/HazyInstaller/find7Link";
    private String m8LinkFile = "/sdcard/HazyInstaller/m8Link";
    private String motoG2014LinkFile = "/sdcard/HazyInstaller/motog2014Link";
    private String n7100LinkFile = "/sdcard/HazyInstaller/n7100Link";
    private String gappsLinkFile = "/sdcard/HazyInstaller/gappsLink";

    // Animations
    private Animation animationIn;
    private Animation animationOut;
    private Animation animationFade;

    // Layout
    private PagerAdapter mPagerAdapter;
    private ViewPager pager;

    // SuperToast library
    private SuperToast superToast;

    // Progress Views
    private ProgressDialog pDialog;
    private TextView gappsProgress;
    private ProgressBar progressBar2;
    private final int PROGRESSBAR_TYPE = 0;

    // Needed to disable the exit button
    // after user tapped the start button
    private int userTapped = 0;

    // Some views
    private TextView gapps;
    private TextView progressTv;
    private TextView percentual;
    private ProgressBar progressBar;
    private Button start;
    private Button exit;
    private CheckBox checkBox;

    // Current device
    private final static String CURRENT_DEVICE = android.os.Build.MODEL;

    // Check Internet connection
    private boolean isConnectedToInternet(Context con) {
        ConnectivityManager connectivityManager = (ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (mobileInfo.isConnected() || wifiInfo.isConnected())
            return true;
        else
            return false;
    }

    // Device that user is using
    private static String device() {
        if (CURRENT_DEVICE.equals(S3)) {
            return S3;
        } else if (CURRENT_DEVICE.equals(SECONDS3)) {
            return S3;
        } else if (CURRENT_DEVICE.equals(NEXUS4)) {
            return NEXUS4;
        } else if (CURRENT_DEVICE.equals(NEXUS5)) {
            return NEXUS5;
        } else if (CURRENT_DEVICE.equals(NEXUS6)) {
            return NEXUS6;
        } else if (CURRENT_DEVICE.equals(TAB)) {
            return TAB;
        } else if (CURRENT_DEVICE.equals(ONEPLUSONE)) {
            return ONEPLUSONE;
        } else if (CURRENT_DEVICE.equals(NEXUS10)) {
            return NEXUS10;
        } else if (CURRENT_DEVICE.equals(G2)) {
            return G2;
        } else if (CURRENT_DEVICE.equals(G3)) {
            return G3;
        } else if (CURRENT_DEVICE.equals(S2)) {
            return S2;
        } else if (CURRENT_DEVICE.equals(YU)) {
            return YU;
        } else if (CURRENT_DEVICE.equals(S2)) {
            return S2;
        } else if (CURRENT_DEVICE.equals(FIND7)) {
            return FIND7;
        } else if (CURRENT_DEVICE.equals(M8)) {
            return M8;
        } else if (CURRENT_DEVICE.equals(MOTOG2014)) {
            return MOTOG2014;
        } else if (CURRENT_DEVICE.equals(NOTE2)) {
            return NOTE2;
        } else {
            return "noDevice";
        }
    }

    // onCreate method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.viewpager_layout);
        this.initialisePaging();
        init();
    }

    // Do initial stuff
    private void init() {
        hideActionBar();
        setStatusBarColor();
        checkForSu();
        checkForConnection();
        downloadLinkFiles();
        checkForFreeSpace();
        makeLinksDir();
        checkForLinks();
    }

    // Hide action bar
    private void hideActionBar() {
        getActionBar().hide();
    }

    // Set the StatusBar color (it'll be darker if user is on Lollipop)
    private void setStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    // Check for root
    private void checkForSu() {
        try {
            Runtime.getRuntime().exec("su");
        } catch (IOException e) {
            deviceHasntRoot();
        }
    }

    // Check for Internet connection
    private void checkForConnection() {
        if (!isConnectedToInternet(getApplicationContext()))
            deviceHasntInternetConnection();
    }

    // Check for free storage
    private void checkForFreeSpace() {
        long freeSpace = sdCardFree();
        if (freeSpace < 300)
            lowSpaceOnDisk();
    }

    // Download files from website
    private void downloadLinkFiles() {
        new DownloadSomeFiles().execute("");
    }

    // Create backup directory
    private void makeLinksDir() {
        File hazydir = new File("/sdcard/HazyInstaller");
        if (!hazydir.exists())
            hazydir.mkdir();
    }

    // Check links
    private void checkForLinks() {
        try {
            onePlusOneDownloadROM = new GetLinks(oneLinkFile)
                    .onePlusOneDownloadROM;
            s3DownloadROM = new GetLinks(s3LinkFile)
                    .s3DownloadROM;
            nexus5DownloadROM = new GetLinks(nexus5LinkFile)
                    .nexus5DownloadROM;
            nexus6DownloadROM = new GetLinks(nexus6LinkFile)
                    .nexus6DownloadROM;
            tabDownloadROM = new GetLinks(tabLinkFile)
                    .tabDownloadROM;
            nexus4DownloadROM = new GetLinks(nexus4LinkFile)
                    .nexus4DownloadROM;
            nexus10DownloadROM = new GetLinks(nexus10LinkFile)
                    .nexus10DownloadROM;
            g2DownloadROM = new GetLinks(g2LinkFile)
                    .g2DownloadROM;
            g3DownloadROM = new GetLinks(g3LinkFile)
                    .g3DownloadROM;
            s2DownloadROM = new GetLinks(s2LinkFile)
                    .s2DownloadROM;
            yuDownloadROM = new GetLinks(yuLinkFile)
                    .yuDownloadROM;
            find7DownloadROM = new GetLinks(find7LinkFile)
                    .find7DownloadROM;
            m8DownloadROM = new GetLinks(m8LinkFile)
                    .m8DownloadROM;
            motoG2014DownloadROM = new GetLinks(motoG2014LinkFile)
                    .motoG2014DownloadROM;
            n7100DownloadROM = new GetLinks(n7100LinkFile)
                    .n7100DownloadROM;
            gappsDownloadLink = new GetLinks(gappsLinkFile)
                    .gappsDownloadLink;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Check device and run AsyncTask to download HazyROM
    private void deviceCheckForDownload() {
        if (device().equals(S3)) {
            new DownloadFileFromURL().execute(s3DownloadROM);
        } else if (device().equals(NEXUS4)) {
            new DownloadFileFromURL().execute(nexus4DownloadROM);
        } else if (device().equals(NEXUS5)) {
            new DownloadFileFromURL().execute(nexus5DownloadROM);
        } else if (device().equals(NEXUS6)) {
            new DownloadFileFromURL().execute(nexus6DownloadROM);
        } else if (device().equals(TAB)) {
            new DownloadFileFromURL().execute(tabDownloadROM);
        } else if (device().equals(ONEPLUSONE)) {
            new DownloadFileFromURL().execute(onePlusOneDownloadROM);
        } else if (device().equals(NEXUS10)) {
            new DownloadFileFromURL().execute(nexus10DownloadROM);
        } else if (device().equals(G2)) {
            new DownloadFileFromURL().execute(g2DownloadROM);
        } else if (device().equals(G3)) {
            new DownloadFileFromURL().execute(g3DownloadROM);
        } else if (device().equals(S2)) {
            new DownloadFileFromURL().execute(s2DownloadROM);
        } else if (device().equals(YU)) {
            new DownloadFileFromURL().execute(yuDownloadROM);
        } else if (device().equals(FIND7)) {
            new DownloadFileFromURL().execute(find7DownloadROM);
        } else if (device().equals(MOTOG2014)) {
            new DownloadFileFromURL().execute(motoG2014DownloadROM);
        } else if (device().equals(M8)) {
            new DownloadFileFromURL().execute(m8DownloadROM);
        } else if (device().equals(NOTE2)) {
            new DownloadFileFromURL().execute(n7100DownloadROM);
        }
    }

    // Lock third fragment
    private void mustNotTap() {
        pager.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View arg0, MotionEvent arg1) {
                return true;
            }
        });
    }

    // AlertDialog to display user hasn't Internet connection
    private void deviceHasntInternetConnection() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Error")
                .setMessage("No Internet Connection available.")
                .setCancelable(false)
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        dialog.dismiss();
                    }
                }).show();
    }

    // AlertDialog to display user he has low space on disk
    private void lowSpaceOnDisk() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Error")
                .setMessage("Low space on Disk.")
                .setCancelable(false)
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        dialog.dismiss();
                    }
                }).show();
    }

    // AlertDialog to display user his device isn't supported
    private void deviceNotSupported() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ViewPagerManager.this);
        alertDialog.setTitle("Error")
                .setMessage("Your device isn\'t supported.")
                .setCancelable(false)
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        dialog.dismiss();
                    }
                }).show();
    }

    // AlertDialog to display user hasn't root permissions
    private void deviceHasntRoot() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Error")
                .setMessage("Your device isn\'t rooted.")
                .setCancelable(false)
                .setPositiveButton("WHAT?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent whatIsRoot = new Intent(Intent.ACTION_VIEW, Uri.parse("http://bit.ly/SspwvM"));
                        startActivity(whatIsRoot);
                        finish();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        dialog.dismiss();
                    }

                }).show();
    }

    // Display an enhanced toast
    private void showSuperToast() {
        superToast = new SuperToast(getApplicationContext());
        superToast.setDuration(SuperToast.Duration.LONG);
        superToast.setAnimations(SuperToast.Animations.FLYIN);
        superToast.setText("Download Started!");
        superToast.setIcon(SuperToast.Icon.Dark.SAVE, SuperToast.IconPosition.LEFT);
        superToast.show();
    }

    // Free space on disk
    private long sdCardFree() {
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        int availBlocks = stat.getAvailableBlocks();
        int blockSize = stat.getBlockSize();
        long freeMemory = (long) availBlocks * (long) blockSize;
        return freeMemory * 1048576;
    }

    // Dialog for backing up apps
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case PROGRESSBAR_TYPE:
                pDialog = new ProgressDialog(this);
                pDialog.setMessage("Backing up your apps...\n\nWait please...");
                pDialog.setCancelable(false);
                pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                pDialog.show();
                return pDialog;
            default:
                return null;
        }
    }

    // Layout
    private void initialisePaging() {
        List<Fragment> fragments = new Vector<Fragment>();
        fragments.add(Fragment.instantiate(this, Tab1Fragment.class.getName()));
        fragments.add(Fragment.instantiate(this, Tab2Fragment.class.getName()));
        fragments.add(Fragment.instantiate(this, Tab3Fragment.class.getName()));
        this.mPagerAdapter = new net.hazyrom.hazyinstaller.PagerAdapter(super
                .getSupportFragmentManager(), fragments);
        pager = (ViewPager) super.findViewById(R.id.viewpager);
        pager.setAdapter(this.mPagerAdapter);

        // FLOW, DEPTH, ZOOM, FADE, FAST_ENTER_FROM_CENTER, ROTATION, SLIDE_AND_FADE, SLIDE_OVER
        pager.setPageTransformer(false, new ViewPagerAnimation(ViewPagerAnimation.TransformType.FADE));
    }

    // Exit button
    public void exit(View v) {
        if (userTapped == 0)
            finish();
    }

    // Start button
    public void startDownload(View v) {
        if (device().equals("noDevice"))
            deviceNotSupported();
        else {
            showSuperToast();

            gapps = (TextView) findViewById(R.id.gapps_progress);
            gappsProgress = (TextView) findViewById(R.id.textView9);
            progressBar2 = (ProgressBar) findViewById(R.id.gapps_progressbar);

            progressTv = (TextView) findViewById(R.id.progresstv);
            percentual = (TextView) findViewById(R.id.downloadTextView);
            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            start = (Button) findViewById(R.id.start);
            exit = (Button) findViewById(R.id.exit);
            checkBox = (CheckBox) findViewById(R.id.checkBox);

            animationIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.right_in);
            animationOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.left_in);
            animationFade = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out);

            progressTv.startAnimation(animationIn);
            progressTv.setVisibility(View.VISIBLE);

            percentual.startAnimation(animationIn);
            percentual.setVisibility(View.VISIBLE);

            progressBar.startAnimation(animationOut);
            progressBar.setVisibility(View.VISIBLE);

            start.startAnimation(animationFade);
            start.setVisibility(View.GONE);

            exit.startAnimation(animationFade);
            exit.setVisibility(View.INVISIBLE);

            mustNotTap();

            if (!checkBox.isChecked())
                deviceCheckForDownload();
            else
                new BackupTask().execute("");
        }
    }

    // Don't tap "start" on the first and the second fragment
    public void dontTapStart(View v) {
    }

    // AsyncTask to download link files
    private class DownloadSomeFiles extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                new DefaultHttpClient().execute(new HttpGet(MAKO_REMOTE_PATH))
                        .getEntity().writeTo(
                        new FileOutputStream(new File(nexus4LinkFile)));

                new DefaultHttpClient().execute(new HttpGet(HAMMERHEAD_REMOTE_PATH))
                        .getEntity().writeTo(
                        new FileOutputStream(new File(nexus5LinkFile)));

                new DefaultHttpClient().execute(new HttpGet(SHAMU_REMOTE_PATH))
                        .getEntity().writeTo(
                        new FileOutputStream(new File(nexus6LinkFile)));

                new DefaultHttpClient().execute(new HttpGet(S3_REMOTE_PATH))
                        .getEntity().writeTo(
                        new FileOutputStream(new File(s3LinkFile)));

                new DefaultHttpClient().execute(new HttpGet(TAB_REMOTE_PATH))
                        .getEntity().writeTo(
                        new FileOutputStream(new File(tabLinkFile)));

                new DefaultHttpClient().execute(new HttpGet(ONE_REMOTE_PATH))
                        .getEntity().writeTo(
                        new FileOutputStream(new File(oneLinkFile)));

                new DefaultHttpClient().execute(new HttpGet(MANTA_REMOTE_PATH))
                        .getEntity().writeTo(
                        new FileOutputStream(new File(nexus10LinkFile)));

                new DefaultHttpClient().execute(new HttpGet(G2_REMOTE_PATH))
                        .getEntity().writeTo(
                        new FileOutputStream(new File(g2LinkFile)));

                new DefaultHttpClient().execute(new HttpGet(G3_REMOTE_PATH))
                        .getEntity().writeTo(
                        new FileOutputStream(new File(g3LinkFile)));

                new DefaultHttpClient().execute(new HttpGet(S2_REMOTE_PATH))
                        .getEntity().writeTo(
                        new FileOutputStream(new File(s2LinkFile)));

                new DefaultHttpClient().execute(new HttpGet(GAPPS_REMOTE_PATH))
                        .getEntity().writeTo(
                        new FileOutputStream(new File(gappsLinkFile)));
                        
                new DefaultHttpClient().execute(new HttpGet(S2_REMOTE_PATH))
                        .getEntity().writeTo(
                        new FileOutputStream(new File(s2LinkFile)));

                new DefaultHttpClient().execute(new HttpGet(YU_REMOTE_PATH))
                        .getEntity().writeTo(
                        new FileOutputStream(new File(yuLinkFile)));

                new DefaultHttpClient().execute(new HttpGet(FIND7_REMOTE_PATH))
                        .getEntity().writeTo(
                        new FileOutputStream(new File(find7LinkFile)));

                new DefaultHttpClient().execute(new HttpGet(MOTG2014_REMOTE_PATH))
                        .getEntity().writeTo(
                        new FileOutputStream(new File(motoG2014LinkFile)));

                new DefaultHttpClient().execute(new HttpGet(M8_REMOTE_PATH))
                        .getEntity().writeTo(
                        new FileOutputStream(new File(m8LinkFile)));

                new DefaultHttpClient().execute(new HttpGet(NOTE2_REMOTE_PATH))
                        .getEntity().writeTo(
                        new FileOutputStream(new File(n7100LinkFile)));

                new DefaultHttpClient().execute(new HttpGet(GAPPS_REMOTE_PATH))
                        .getEntity().writeTo(
                        new FileOutputStream(new File(gappsLinkFile)));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

     // AsyncTask to backup all apks
     private class BackupTask extends AsyncTask<String, String, String> {

         @Override
         protected void onPreExecute() {
             super.onPreExecute();
             showDialog(PROGRESSBAR_TYPE);
         }

         @Override
         protected String doInBackground(String... strings) {
             String mountone[] = {"su", "-c", "mount -ro remount,rw /"};
             String backup[] = {"su", "-c", "cp -av /data/app /sdcard/Hazy"};

             File f = new File("/sdcard/Hazy");
             if (!f.exists())
                 f.mkdir();

             try {
                 Runtime.getRuntime().exec(mountone);
                 Runtime.getRuntime().exec(backup);
             } catch (IOException e) {
                 e.printStackTrace();
             }
             return null;
         }

         @Override
         protected void onPostExecute(String arg) {
             super.onPostExecute("");
             dismissDialog(PROGRESSBAR_TYPE);
             showSuperToast();
             deviceCheckForDownload();
         }
     }

     // Download HazyROM
     private class DownloadFileFromURL extends AsyncTask<String, String, String> {

         TextView downloadTextView;

         @Override
         protected void onPreExecute() {
             super.onPreExecute();
             userTapped++;
             progressBar = (ProgressBar) findViewById(R.id.progressBar);
             downloadTextView = (TextView) findViewById(R.id.downloadTextView);
         }

         @Override
         protected String doInBackground(String... f_url) {
             int count;
             try {
                 File rom = new File("/sdcard/HazyROM.zip");
                 if (rom.exists()) {
                     rom.delete();
                 }
                 URL url = new URL(f_url[0]);
                 URLConnection conection = url.openConnection();
                 conection.connect();
                 final int lenghtOfFile = conection.getContentLength();
                 InputStream input = new BufferedInputStream(url.openStream(), 8192);
                 OutputStream output = new FileOutputStream("/sdcard/HazyROM.zip");
                 byte data[] = new byte[1024];
                 long total = 0;
                 while ((count = input.read(data)) != -1) {
                     total += count;
                     String prgs = ("" + (int) ((total * 100) / lenghtOfFile));
                     publishProgress(prgs);
                     output.write(data, 0, count);
                 }
                 output.flush();
                 output.close();
                 input.close();
             } catch (MalformedURLException e) {
                 e.printStackTrace();
             } catch (IOException e) {
                 e.printStackTrace();
             }

             return null;
         }

         @Override
         protected void onProgressUpdate(String... progress) {
             progressBar.setProgress(Integer.parseInt(progress[0]));
             downloadTextView.setText(progress[0] + "%");
         }

         @Override
         protected void onPostExecute(String file_url) {
             super.onPostExecute("");
             gapps.startAnimation(animationOut);
             gapps.setVisibility(View.VISIBLE);

             gappsProgress.startAnimation(animationIn);
             gappsProgress.setVisibility(View.VISIBLE);

             progressBar2.startAnimation(animationOut);
             progressBar2.setVisibility(View.VISIBLE);

             new DownloadGappsFromURL().execute(gappsDownloadLink);
         }
     }

     // Download HazyGApps
     private class DownloadGappsFromURL extends AsyncTask<String, String, String> {

         @Override
         protected void onPreExecute() {
             super.onPreExecute();
         }

         @Override
         protected String doInBackground(String... f_url) {
             int count;
             try {
                 File rom = new File("/sdcard/HazyGApps.zip");
                 if (rom.exists()) {
                     rom.delete();
                 }
                 URL url = new URL(f_url[0]);
                 URLConnection conection = url.openConnection();
                 conection.connect();
                 int lenghtOfFile = conection.getContentLength();
                 InputStream input = new BufferedInputStream(url.openStream(), 8192);
                 OutputStream output = new FileOutputStream("/sdcard/HazyGApps.zip");
                 byte data[] = new byte[1024];
                 long total = 0;

                 while ((count = input.read(data)) != -1) {
                     total += count;
                     String prgs = ("" + (int) ((total * 100) / lenghtOfFile));
                     publishProgress(prgs);
                     output.write(data, 0, count);
                 }
                 output.flush();
                 output.close();
                 input.close();
             } catch (MalformedURLException e) {
                 e.printStackTrace();
             } catch (IOException e) {
                 e.printStackTrace();
             }
             return null;
         }

         @Override
         protected void onProgressUpdate(String... progress) {
             progressBar2.setProgress(Integer.parseInt(progress[0]));
             gappsProgress.setText(progress[0] + "%");
         }

         @Override
         protected void onPostExecute(String file_url) {
             super.onPostExecute("");
             new CopyScript().execute();
         }
     }

     // Install BusyBox, copy Script and reboot to Recovery mode
     private class CopyScript extends AsyncTask<Void, Void, Void> {

         @Override
         protected Void doInBackground(Void... voids) {
             try {
                 try {
                     File traceFile = new File("/sdcard/openrecoveryscript");
                     if (traceFile.exists()) {
                         traceFile.delete();
                         traceFile.createNewFile();
                     }
                     BufferedWriter writer = new BufferedWriter(new FileWriter(traceFile, true));
                     writer.write("print \"Starting wipe...\"\n" +
                             "wipe data\n" +
                             "wipe cache\n" +
                             "print \"#############################\"\n" +
                             "print \"### HAZY ROM by HAZY TEAM ###\"\n" +
                             "print \"#############################\"\n" +
                             "install /sdcard/HazyROM.zip\n" +
                             "install /sdcard/HazyGApps.zip\n" +
                             "\n");
                     writer.close();
                     MediaScannerConnection.scanFile(getApplicationContext(),
                             new String[]{traceFile.toString()},
                             null,
                             null);

                 } catch (IOException e) {
                     e.printStackTrace();
                 }
                 Runtime.getRuntime().exec("su");
                 String[] InstallBusyBoxCmd = new String[]{
                         "su", "-c",
                         "cat /sdcard/busybox &gt; /system/xbin/busybox;" +
                                 "chmod 755 /system/xbin/busybox;" +
                                 "busybox --install /system/xbin"
                 };
                 Runtime.getRuntime().exec(InstallBusyBoxCmd);
                 Runtime.getRuntime().exec("mount -ro remount,rw /cache/recovery/");
                 Runtime.getRuntime().exec("mount -ro remount,rw /cache/");
                 Runtime.getRuntime().exec("mount -ro remount,rw /");

                 File openrecovery = new File("/cache/recovery/openrecoveryscript");
                 if (openrecovery.exists()) {
                     openrecovery.delete();
                 }

                 String sSUCommand = "cp /sdcard/openrecoveryscript /cache/recovery/";

                 String[] sCommand = {"su", "-c", sSUCommand};
                 Runtime.getRuntime().exec(sCommand);

                 String permissions[] = {"su", "-c", "chmod 777 /cache/recovery/openrecoveryscript"};
                 Runtime.getRuntime().exec(permissions);

                 String[] reboot = {"su", "-c", "reboot recovery"};
                 Runtime.getRuntime().exec(reboot);

                 return null;
             } catch (IOException e) {
                 e.printStackTrace();
             }
             return null;
         }
     }
}
