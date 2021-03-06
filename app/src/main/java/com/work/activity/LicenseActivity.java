package com.work.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.work.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class LicenseActivity extends BaseActivity {

    TextView mLicenseTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license);

        mLicenseTextView = findViewById(R.id.license_text_view);
        setLicense();
    }

    private void setLicense() {
        String data = null;
        InputStream inputStream = getResources().openRawResource(R.raw.open_license);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int i;
        try {
            i = inputStream.read();
            while (i != -1) {
                byteArrayOutputStream.write(i);
                i = inputStream.read();
            }

            data = new String(byteArrayOutputStream.toByteArray(), "MS949");
            inputStream.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        mLicenseTextView.setText(data);
    }
}
