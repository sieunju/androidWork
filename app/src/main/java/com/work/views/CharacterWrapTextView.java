package com.work.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.work.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Class: CharacterWrapTextView
 * Created by jsieu on 2020-02-09.
 * <p>
 * Description: 글자 단위 개행 및 말줄임 표시 이후 추가할 텍스트에 대한 TextView Class
 */
public class CharacterWrapTextView extends AppCompatTextView {

    private final Context mContext;

    private Paint mPaint;
    private List<String> mCutTextList = new ArrayList<>();
    private final int ELLIPSIS_LENGTH = 3;        // ... 문자열 길이

    private float mAfterTextStartX;         // 말줄임 표시 이후 추가할 문자열 좌표값
    private boolean mIsCharOver = false;    // 문자열이 오버된 경우.

    // [s] Attributes Variable
    private boolean mIsEllipsis;            // 문자열 맨 마지막 말줄임 표시 유무
    private String mAfterText = null;       // 말줄임 표시 이후 추가할 문자열
    @ColorInt
    private int mAfterTextColor;            // 말줄임 표시 이후 추가할 문자열 색상
    private float mAfterTextSize;           // 말줄임 표시 이후 추가할 문자열 크기
    private boolean mIsAfterTextUnderLine;  // 말줄임 표시 이후 추가할 문자열 밑줄 유무
    private boolean mIsAfterTextBold;       // 말줄임 표시 이후 추가할 문자열 굵기 처리 유무
    // [e] Attributes Variable

    public CharacterWrapTextView(Context context) {
        this(context, null);
    }

    public CharacterWrapTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CharacterWrapTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initAttrs(attrs);
    }

    /**
     * init AttributeSet
     *
     * @param attrs AttributeSet
     */
    private void initAttrs(@Nullable AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.CharacterWrapTextView);
            try {
                mIsEllipsis = typedArray.getBoolean(R.styleable.CharacterWrapTextView_isEllipsis, false);
                mAfterText = typedArray.getString(R.styleable.CharacterWrapTextView_afterText);
                mAfterTextColor = typedArray.getColor(R.styleable.CharacterWrapTextView_afterTextColor, 0);
                mAfterTextSize = typedArray.getDimension(R.styleable.CharacterWrapTextView_afterTextSize, 0F);
                mIsAfterTextUnderLine = typedArray.getBoolean(R.styleable.CharacterWrapTextView_afterTextIsUnderLine, false);
                mIsAfterTextBold = typedArray.getBoolean(R.styleable.CharacterWrapTextView_afterTextIsBold, false);
            } finally {
                typedArray.recycle();
            }
        }
    }

    /**
     * 맨 마지막에 말줄임 표시 할건지에 대한 Flag 값.
     *
     * @param flag true -> 말줄임 합니다., false -> 말줄임 X
     */
    public void setEllipsis(boolean flag) {
        mIsEllipsis = flag;
    }

    /**
     * 말줄임 표시 이후 추가할 문자열
     *
     * @param text Text..
     */
    public void setAfterText(String text) {
        mAfterText = text;
    }

    /**
     * 말줄임 표시 이후 추가할 문자열 색상 값 세팅 하는 함수.
     *
     * @param color Color
     */
    public void setAfterTextColor(@ColorInt int color) {
        mAfterTextColor = color;
    }

    /**
     * 말줄임 표시 이후 추가할 문자열 크기.
     *
     * @param size Dimension Value
     */
    public void setAfterTextSize(float size) {
        mAfterTextSize = size;
    }

    /**
     * 텍스트 정보 세팅.
     *
     * @param text       문자열
     * @param textWidth  문자열 너비
     * @param textHeight 문자열 높이
     * @return 총 높이값.
     */
    private int setTextInfo(String text, int textWidth, int textHeight) {

        // 페인티 초기화.
        initPaint();

        int maxTextHeight = textHeight;

        if (textWidth > 0) {

            // 텍스트 표시 가능한 너비.
            int availableWidth = textWidth - this.getPaddingLeft() - this.getPaddingRight();

            // 데이터 초기화
            mCutTextList.clear();
            mIsCharOver = false;

            int end;
            do {
                // 글자가 width 보다 넘어가는지 체크
                end = mPaint.breakText(text, true, availableWidth, null);
                if (end > 0) {
                    // 자른 문자열을 문자열 배열에 담아 놓는다.
                    mCutTextList.add(text.substring(0, end));
                    // 넘어간 글자 모두 잘라 다음에 사용하도록 세팅
                    text = text.substring(end);
                    // 다음라인 높이 지정
                    if (textHeight == 0) maxTextHeight += getLineHeight();
                }

                // 최대 줄이상 넘어가는 경우
                if (mCutTextList.size() >= getMaxLines()) {
                    maxTextHeight += getPaddingBottom();
                    String lastText = mCutTextList.get(mCutTextList.size() - 1);
                    if (mIsEllipsis) {
                        // 기본 말줄임 표시.
                        if (mAfterText == null) {
                            if (lastText.length() > ELLIPSIS_LENGTH) {
                                lastText = lastText.substring(0, lastText.length() - ELLIPSIS_LENGTH);
                            }
                        }
                        // 말줄임 이후 추가할 문자열이 있는 경우.
                        else {
                            // 말줄임 이후 추가할 문자열까지 충분한 경우.
                            if (lastText.length() > (ELLIPSIS_LENGTH + mAfterText.length())) {
                                lastText = lastText.substring(0, lastText.length() - (ELLIPSIS_LENGTH + mAfterText.length()));
                            }
                        }

                        // 말줄임 '...' 표시
                        lastText += "...";
                        // 글자수 Over 값 세팅
                        mIsCharOver = true;
                        // 맨 마지막 글자 너비 값 Get
                        mAfterTextStartX = mPaint.measureText(lastText);
                    }
                    mCutTextList.set(mCutTextList.size() - 1, lastText);
                    end = 0;
                }

            } while (end > 0);
        }
        maxTextHeight += getPaddingTop() + getPaddingBottom();
        return maxTextHeight;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 첫번째 글자 높이 값 지정 Padding + 줄간격 - 글자 크기 / 2 Y 좌표값
        float height = getPaddingTop() + getLineHeight() - (getTextSize() / 2F);

        for (int i = 0; i < mCutTextList.size(); i++) {
            String text = mCutTextList.get(i);

            // 캔버스에 라인 높이 만큼 글자 그리기
            canvas.drawText(text, getPaddingLeft(), height, mPaint);

            // 맨 말줄임 이후 추가할 문자열이 있다면..
            if (i == mCutTextList.size() - 1 && mIsCharOver) {
                synchronized (this) {
                    if (mAfterText != null) {

                        // 밑줄 표시
                        if (mIsAfterTextUnderLine) {
                            mPaint.setFlags(mPaint.getFlags() | Paint.UNDERLINE_TEXT_FLAG);
                        }

                        // 굵기 처리
                        if (mIsAfterTextBold) {
                            mPaint.setFlags(mPaint.getFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
                        }

                        mPaint.setTextSize(mAfterTextSize);
                        mPaint.setColor(mAfterTextColor);
                        canvas.drawText(mAfterText, mAfterTextStartX, height, mPaint);
                    }
                    initPaint();
                }
            }

            height += getLineHeight();
        }
    }

    /**
     * init Paint
     */
    private void initPaint() {
        mPaint = getPaint();
        mPaint.setFlags(0);
        mPaint.setColor(getTextColors().getDefaultColor());
        mPaint.setTextSize(getTextSize());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
        int height = setTextInfo(this.getText().toString(), parentWidth, parentHeight);
        // 부모 높이가 0인경우 실제 그려줄 높이만큼 사이즈를 놀려줌...
        if (parentHeight == 0) {
            parentHeight = height;
        }
        this.setMeasuredDimension(parentWidth, parentHeight);
    }

    @Override
    protected void onTextChanged(final CharSequence text, final int start, final int before, final int after) {
        // 글자가 변경되었을때 다시 세팅
        setTextInfo(text.toString(), this.getWidth(), this.getHeight());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        // 사이즈가 변경되었을때 다시 세팅(가로 사이즈만...)
        if (w != oldw) {
            setTextInfo(this.getText().toString(), w, h);
        }
    }
}
