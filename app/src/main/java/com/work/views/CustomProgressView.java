package com.work.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

import com.work.R;
import com.work.utils.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * SurfaceView 기반의 Progress View Class
 * Created by hmju on 2019-01-27.
 */
public class CustomProgressView extends SurfaceView implements Callback {

    private final Context mContext;
    private final SurfaceHolder mHolder;

    private final int DEFAULT_MIN = 0;
    private final int DEFAULT_MAX = 100;

    final int DEFAULT_COLOR = Color.BLACK;
    final int ID_TYPE = R.styleable.CustomProgressView_type;
    final int ID_MAX = R.styleable.CustomProgressView_max;
    final int ID_MIN = R.styleable.CustomProgressView_min;
    final int ID_BG_COLOR = R.styleable.CustomProgressView_bgColor;
    final int ID_GRADIENT_RADIUS = R.styleable.CustomProgressView_gradientRadius;
    final int ID_GRADIENT_START_COLOR = R.styleable.CustomProgressView_gradientStartColor;
    final int ID_GRADIENT_CENTER_COLOR = R.styleable.CustomProgressView_gradientCenterColor;
    final int ID_GRADIENT_END_COLOR = R.styleable.CustomProgressView_gradientEndColor;
    final int ID_GRADIENT_LOCATION = R.styleable.CustomProgressView_gradientLocation;

    // attribute value define [START]
    private enum TYPE {
        HORIZONTAL, VERTICAL
    }

    // 프로그래스 색상 및 라운딩 처리에 대한 정보 구조체.
    protected class GradientInfo {
        int radius;
        int startColor;
        int centerColor;
        int endColor;
        float location;
    }

    private GradientInfo mGradientInfo = new GradientInfo();
    private TYPE mType = TYPE.HORIZONTAL;
    private int mMin = DEFAULT_MIN;
    private int mMax = DEFAULT_MAX;
    private int mBgColor;
    // attribute value define [END]

    private ProgressThread mThread;
    private int mCurrentProgress = 0;

    // surface life cycle
    // Type 은 2가지로 표현 한다. 더 필요한 경우 생명주기 타입 추가.
    private enum LIFE {
        CAN_DRAW, NOT_DRAW
    }

    private LIFE mCurrentLife = LIFE.NOT_DRAW;

    public CustomProgressView(Context context) {
        this(context, null);
    }

    public CustomProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // default Setting
        mContext = context;
        mHolder = getHolder();
        mHolder.addCallback(this);
        initView(attrs);
    }

    /**
     * init View
     *
     * @author hmju
     */
    private void initView(AttributeSet attrs) {
        // 기본 SurfaceView 투명화
        setZOrderOnTop(true);
        mHolder.setFormat(PixelFormat.TRANSPARENT);

        // 속성값 셋팅.
        if (attrs != null) {
            TypedArray attrsArray = mContext.obtainStyledAttributes(attrs, R.styleable.CustomProgressView);
            mBgColor = attrsArray.getColor(ID_BG_COLOR, DEFAULT_COLOR);
            mType = TYPE.values()[attrsArray.getInt(ID_TYPE, 0)];
            mMax = attrsArray.getInt(ID_MAX, DEFAULT_MAX);
            mMin = attrsArray.getInt(ID_MIN, DEFAULT_MIN);
            mGradientInfo.radius = attrsArray.getDimensionPixelOffset(ID_GRADIENT_RADIUS, 0);
            mGradientInfo.startColor = attrsArray.getColor(ID_GRADIENT_START_COLOR, DEFAULT_COLOR);
            mGradientInfo.centerColor = attrsArray.getColor(ID_GRADIENT_CENTER_COLOR, DEFAULT_COLOR);
            mGradientInfo.endColor = attrsArray.getColor(ID_GRADIENT_END_COLOR, DEFAULT_COLOR);
            mGradientInfo.location = attrsArray.getFloat(ID_GRADIENT_LOCATION, 0f);

            // setProgress
            mCurrentProgress = mMin;
            attrsArray.recycle();
        }
    }

    public CustomProgressView setType(TYPE type) {
        mType = type;
        return this;
    }

    public CustomProgressView setMin(int min) {
        mMin = min;
        mCurrentProgress = mMin;
        return this;
    }

    public CustomProgressView setMax(int max) {
        mMax = max;
        return this;
    }

    public CustomProgressView setBgColor(int id) {
        mBgColor = id;
        return this;
    }

    public CustomProgressView setRadius(int radius) {
        mGradientInfo.radius = radius;
        return this;
    }

    public CustomProgressView setStartColor(int color) {
        mGradientInfo.startColor = color;
        return this;
    }

    public CustomProgressView setCenterColor(int color) {
        mGradientInfo.centerColor = color;
        return this;
    }

    public CustomProgressView setEndColor(int color) {
        mGradientInfo.endColor = color;
        return this;
    }

    public CustomProgressView setGradientLocation(float location) {
        mGradientInfo.location = location;
        return this;
    }

    /**
     * 현재 프로그래스 진행 률 표시 하는 함수.
     *
     * @param progress
     */
    public void setProgress(int progress) {
        mCurrentProgress = progress;
        if (mCurrentLife == LIFE.CAN_DRAW) {
            mThread.draw();
        }
    }

    /**
     * 현재 프로그래스 진행률 get
     *
     * @return mCurrentProgress
     * @author hmju
     */
    public int getProgress() {
        return mCurrentProgress;
    }

    /**
     * 현재 프로그래스가 증가율을 표시하는 함수.
     *
     * @param diff
     */
    public void incrementProgressBy(int diff) {
        mCurrentProgress += diff;
        if (mCurrentLife == LIFE.CAN_DRAW) {
            mThread.draw();
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {

        if (mThread != null) {
            mThread.closeThread();
            mThread = null;
        }

        mCurrentLife = LIFE.CAN_DRAW;
        mThread = new ProgressThread(width, height);

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mCurrentLife = LIFE.NOT_DRAW;
        mThread.closeThread();
    }

    /**
     * ProgressThread Class
     *
     * @author hmju
     */
    class ProgressThread {

        ExecutorService thread = Executors.newFixedThreadPool(1);
        Paint fgPaint = new Paint(Paint.ANTI_ALIAS_FLAG); // 부드럽게 처리 하는 flag
        Paint bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG); // 부드럽게 처리 하는 flag
//        Paint txtPaint = new Paint();

        Rect fgRect;
        RectF bgRectF;
        LinearGradient gradient;
        int corner;
        int width;
        int height;

        /**
         * initialize Thread
         *
         * @author hmju
         */
        ProgressThread(int tmpWidth, int tmpHeight) {
            width = tmpWidth;
            height = tmpHeight;
//            txtPaint.setColor(Color.BLACK);
//            txtPaint.setTextSize(30);
            //initBackground & init Gradient & init Thread
            initBackground();
            initGradient();
            initThread();
        }

        /**
         * init BackGround
         *
         * @author hmju
         */
        void initBackground() {
            corner = mGradientInfo.radius;
            bgRectF = new RectF(0, 0, width, height);
            bgPaint.setColor(mBgColor);
        }

        /**
         * init Gradient
         * └ type 별로 분기 처리
         *
         * @author hmju
         */
        void initGradient() {
            int[] colors = new int[]{mGradientInfo.startColor, mGradientInfo.centerColor, mGradientInfo.endColor};
            float[] locations = new float[]{0, mGradientInfo.location, 1};

            // 라운딩 처리된 배경에 그라데이션되어 있는 foreground 를 입힌다.
            fgPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
            // horizontal
            if (mType == TYPE.HORIZONTAL) {
                fgRect = new Rect(0, 0, 0, height);
                gradient = new LinearGradient(0, 0, 0, height, colors, locations, Shader.TileMode.CLAMP);
            }
            // vertical
            else {
                fgRect = new Rect(0, height, width, height);
                gradient = new LinearGradient(0, 0, 0, height, colors, locations, Shader.TileMode.CLAMP);
            }

            // setGradient
            fgPaint.setShader(gradient);
        }

        /**
         * init Thread
         *
         * @author hmju
         */
        void initThread() {
            //set Thread interface
            thread.execute(runDraw);
            // setBgColor
            draw();
        }

        /**
         * Canvas Draw
         *
         * @author hmju
         */
        Runnable runDraw = new Runnable() {
            @Override
            public void run() {
                Canvas canvas = null;
                canvas = mHolder.lockCanvas();
                try {
                    synchronized (mHolder) {
                        // draw background
                        canvas.drawRoundRect(bgRectF, corner, corner, bgPaint);

                        // 초기값인 경우 아래 로직 패스
                        if (mCurrentProgress == 0) {
                            return;
                        }
                        // type horizontal
                        if (mType == TYPE.HORIZONTAL) {
                            float updateValue = (float) width / mMax * mCurrentProgress;
                            fgRect.right = (int) updateValue;
                        }
                        // type vertical
                        else {
                            float updateValue = (float) ((mMax - mCurrentProgress) * height) / mMax;
                            fgRect.top = (int) updateValue;
                        }

                        // draw foreground
                        canvas.drawRect(fgRect, fgPaint);
//                        canvas.drawText( mCurrentProgress +"%",width-100,100,txtPaint);
                    }
                } catch (Exception ex) {
                    Logger.d("Thread Error " + ex.getMessage());
                } finally {
                    if (canvas != null) {
                        mHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        };

        /**
         * runDraw
         *
         * @author hmju
         */
        void draw() {
            try {
                runDraw.run();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        /**
         * call ShutDown Thread
         *
         * @author hmju
         */
        void closeThread() {
            try {
                thread.shutdownNow();
            } catch (Exception ex) {
                Logger.d("TEST closeThread Error " + ex.getMessage());
            }
        }
    }
}
