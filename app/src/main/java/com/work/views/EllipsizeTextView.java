package com.work.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.text.Html;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.work.utils.Logger;

public class EllipsizeTextView extends AppCompatTextView {

    private static final String ELLIPSIS = "... 더보기";
    private static final String ELLIPSIS_MORE = "... <font color=#999999>더보기</font>";

    private final Context mContext;
    private boolean mIsResetText;   // 문자열을 재조정후 그려야 하는지에 대한 변수
    private boolean mIsRun; // 텍스트 재조정하고 있는 도중 textChanged 가 들어왔을때 동기화 처리하기 위한 변수
    private String mFullText;
    private int mMaxLines;
    private float mSpacingMult = 1.0f;
    private float mSpacingAdd = 0.0f;

    public EllipsizeTextView(Context context) {
        this(context, null);
    }

    public EllipsizeTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EllipsizeTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        super.setEllipsize(null);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, new int[]{android.R.attr.maxLines});
        setMaxLines(typedArray.getInt(0, Integer.MAX_VALUE));
        typedArray.recycle();
    }

    /**
     * set Text 줄바꿈 및 여백 다 공백으로 처리후 super.setText() 호출
     *
     * @param text 문자엶
     */
    public void setText(String text) {
        super.setText(Html.fromHtml(text.replace(" ", "\u00A0")
                .replace("<br>", "\u00A0")
                .replace("</br>", "\u00A0")
                .replace("<BR>", "\u00A0")
                .replace("</BR>", "\u00A0")
                .replace("\r", "\u00A0")
                .replace("\n", "\u00A0")));
    }

    @Override
    public void setMaxLines(int maxLines) {
        super.setMaxLines(maxLines);
        mMaxLines = maxLines;
        mIsResetText = true;
    }

    public int getMaxLines() {
        return mMaxLines;
    }

    @Override
    public void setLineSpacing(float add, float mult) {
        mSpacingAdd = add;
        mSpacingMult = mult;
        super.setLineSpacing(add, mult);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int before, int after) {
        super.onTextChanged(text, start, before, after);
        if (!mIsRun) {
            mFullText = text.toString();
            mIsResetText = true;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mIsResetText) {
            resetText();
        }
        super.onDraw(canvas);
    }

    /**
     * 말줄임 가능한지 검사후 텍스트 다시 재 조정 하는 함수
     */
    private void resetText() {
        String tmpText = mFullText;
        boolean isEllipsize = false; // 말줄임 유무
        // 전체 문자열에 대한 Layout get
        Layout layout = getTextLayout(tmpText);
        Logger.d("Text Layout LineCnt\t" + layout.getLineCount() + "\tAttr MaxLines\t" + mMaxLines);
        // 말줄임 표시 해야함
        if (layout.getLineCount() > mMaxLines) {
            isEllipsize = true;
            Logger.d("문자열 자르기전\t" + tmpText.length());
            // MaxLine 마지막 줄까지만 문자열 자르기
            tmpText = mFullText.substring(0, layout.getLineEnd(mMaxLines - 1)).trim();

            Logger.d("문자열 자른후\t" + tmpText.length());

            tmpText = tmpText.substring(0, tmpText.length() - ELLIPSIS.length());
            tmpText += ELLIPSIS_MORE;
        }

        if (isEllipsize) {
            mIsRun = true;
            try {
                setText(Html.fromHtml(tmpText));
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                mIsRun = false;
            }
        }
        mIsResetText = false;
    }

    /**
     * 표시할 문자열에 대한 레이아웃
     *
     * @param text 표시할 문자열
     * @return Layout
     */
    private Layout getTextLayout(String text) {
        return new StaticLayout(text, getPaint(),
                getWidth() - getPaddingLeft() - getPaddingRight(),
                Layout.Alignment.ALIGN_NORMAL, mSpacingMult,
                mSpacingAdd, false);
    }

    @Override
    public void setEllipsize(TextUtils.TruncateAt where) {

    }

}
