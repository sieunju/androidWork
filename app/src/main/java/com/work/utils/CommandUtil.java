package com.work.utils;

import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 공통적으로 쓰이는 유틸 싱글톤 클래스
 * Created by hmju on 2019-04-06.
 */
public class CommandUtil {
    private static final CommandUtil ourInstance = new CommandUtil();

    public static CommandUtil getInstance() {
        return ourInstance;
    }

    private CommandUtil() {
    }

    /**
     * 텍스트 파일에 있는 데이터를 Json 으로 파싱 하는 함수.
     *
     * @param context
     * @param txtFile   Asset 에있는 File 이름
     * @param dataModel 변환 하고 싶은 데이터 모델
     * @param <T>       JSON Parser
     * @return
     * @author hmju
     */
    public synchronized <T extends Object> T getFileToJson(Context context, String txtFile, Class<T> dataModel) {
        try {
            // file Read.
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    context.getAssets().open(txtFile)));
            StringBuilder stringBuilder = new StringBuilder();
            String mLine = reader.readLine();
            while (mLine != null) {
                stringBuilder.append(mLine); // process line
                mLine = reader.readLine();
            }
            // BufferedReader close.
            reader.close();

            // Make JSONObject
            JSONObject jsonObject = new JSONObject(stringBuilder.toString());
            // Parser gogo..
            return new Gson().fromJson(jsonObject.toString(), dataModel);
        } catch (Exception ex) {
            return null;
        }
    }
}
