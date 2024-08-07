package com.apps.kunalfarmah.omrscanner;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Scan extends AppCompatActivity {

    private EditText answerKeyEditText;
    private SurfaceView surfaceView;
    private TextView tv;
    private CameraSource cameraSource;
    private static final int Permission = 100;

    private String previousDetectedText = "";
    private boolean matchDisplayed = false;

    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable clearTextRunnable = new Runnable() {
        @Override
        public void run() {
            tv.setText("");
            previousDetectedText = "";
            matchDisplayed = false;
        }
    };
    private static final long CLEAR_TEXT_DELAY = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        surfaceView = findViewById(R.id.surfaceView);
        tv = findViewById(R.id.script);
        answerKeyEditText = findViewById(R.id.answerKeyEditText);

        tv.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);

        startCameraSource();
    }

    private void startCameraSource() {
        final TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
        if (!textRecognizer.isOperational()) {
            Log.w("Tag", "Dependencies not loaded yet");
        } else {
            cameraSource = new CameraSource.Builder(getApplicationContext(), textRecognizer)
                    .setFacing(CameraSource.CAMERA_FACING_BACK).setRequestedPreviewSize(1280, 1024)
                    .setAutoFocusEnabled(true).setRequestedFps(2.0f).build();

            surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
                    try {
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(Scan.this, new String[]{android.Manifest.permission.CAMERA}, Permission);
                            return;
                        }
                        cameraSource.start(surfaceView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int format, int width, int height) {
                }

                @Override
                public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
                    cameraSource.stop();
                }
            });

            textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
                @Override
                public void release() {}

                @Override
                public void receiveDetections(Detector.Detections<TextBlock> detections) {
                    final SparseArray<TextBlock> items = detections.getDetectedItems();
                    if (items.size() != 0) {
                        StringBuilder stringBuilder = new StringBuilder();
                        for (int i = 0; i < items.size(); i++) {
                            TextBlock item = items.valueAt(i);
                            stringBuilder.append(item.getValue());
                            stringBuilder.append("\n");
                        }
                        final String detectedText = stringBuilder.toString().trim();

                        if (!detectedText.equals(previousDetectedText)) {
                            previousDetectedText = detectedText;
                            matchDisplayed = false;

                            final String userInput = answerKeyEditText.getText().toString().trim();
                            if (!userInput.isEmpty()) {
                                final Set<String> userKeywords = new HashSet<>(Arrays.asList(userInput.split("\\s*,\\s*")));

                                String highlightedText = detectedText;
                                for (String keyword : userKeywords) {
                                    highlightedText = highlightedText.replaceAll("(?i)" + keyword, "<font color='green'>" + keyword + "</font>");
                                }

                                final String finalHighlightedText = highlightedText;
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        tv.setText(Html.fromHtml(finalHighlightedText));
                                    }
                                });

                                boolean matchFound = false;
                                for (String keyword : userKeywords) {
                                    if (detectedText.toLowerCase().contains(keyword.toLowerCase())) {
                                        matchFound = true;
                                        break;
                                    }
                                }

                                if (matchFound) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            tv.append(Html.fromHtml("\n<font color='green'> Match found for keywords.</font>"));
                                        }
                                    });
                                } else if (!matchDisplayed) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            tv.append(Html.fromHtml("\n<font color='red'> No match found for keywords.</font>"));
                                            matchDisplayed = true;
                                        }
                                    });
                                }
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        tv.setText(detectedText);
                                    }
                                });
                            }
                        }

                        handler.removeCallbacks(clearTextRunnable);
                        handler.postDelayed(clearTextRunnable, CLEAR_TEXT_DELAY);
                    } else {
                        handler.postDelayed(clearTextRunnable, CLEAR_TEXT_DELAY);
                    }
                }
            });
        }
    }
}

