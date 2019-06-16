package com.work.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.work.R;
import com.work.utils.Logger;
import com.work.views.CustomProgressView;

public class ProgressBarActivity extends BaseActivity {

    @BindView(R.id.progress_bar)
    CustomProgressView mProgressBar;

    @BindView(R.id.start_button)
    TextView mButton;

    @BindView(R.id.stop_button)
    TextView mStopButton;

    private boolean isRun = true;

    TestThread t1 = new TestThread();
    TestThread t2 = new TestThread();
    TestThread t3 = new TestThread();
    TestThread t4 = new TestThread();
    TestThread t5 = new TestThread();
    TestThread t6 = new TestThread();
    TestThread t7 = new TestThread();
    TestThread t8 = new TestThread();
    TestThread t9 = new TestThread();
    TestThread t10 = new TestThread();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_bar);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.start_button, R.id.stop_button})
    public void onButtonClick(View id) {
        switch (id.getId()) {
            case R.id.start_button:
                isRun = true;
                testProgress();
                break;
            case R.id.stop_button:
                stopThread();
                break;
        }
    }

    private void testProgress() {
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
        t7.start();
        t8.start();
        t9.start();
        t10.start();
    }

    private void stopThread() {
        isRun = false;
        try {
            t1.notify();
            t2.notify();
            t3.notify();
            t4.notify();
            t5.notify();
        } catch (Exception ex) {
            Logger.d("Stop Error " + ex.getMessage());
        }


    }

    class TestThread extends Thread {

        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                if (isRun) {
                    try {
                        mProgressBar.incrementProgressBy(1);
                    } catch (Exception ex) {

                    }
                } else {
                    break;
                }
            }
        }
    }
}
