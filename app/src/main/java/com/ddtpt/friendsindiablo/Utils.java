package com.ddtpt.friendsindiablo;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by e228596 on 2/24/2015.
 */
public class Utils {
    Context mContext;
    String mFileName;

    public Utils(Context c, String file) {
        mContext = c;
        mFileName = file;
    }

    public ArrayList<String> getCareerList() {
        ArrayList<String> careers = new ArrayList<>();
        BufferedReader reader;
        Gson gson = new Gson();

        try {
            File file = new File(mContext.getFilesDir(), mFileName);
            if (file.exists()) {
                InputStream in = mContext.openFileInput(mFileName);
                reader = new BufferedReader(new InputStreamReader(in));
                String json = reader.readLine();
                if (json != null) {
                    careers = gson.fromJson(json, new TypeToken<ArrayList<String>>() {
                    }.getType());
                }
            } else {
                file.createNewFile();
            }

        }catch(IOException e) {
            Log.e("UTILS", e.toString());
        }

        return careers;
    }

    public void storeCareerList(ArrayList<String> careers) {
        Writer writer;

        try {
            OutputStream out = mContext.openFileOutput(mFileName, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);

            String json = new Gson().toJson(careers);

            writer.write(json);
        } catch (FileNotFoundException e) {
            Log.e("UTILS", "File was not found to write data");
        } catch (IOException e) {
            Log.e("UTILS", "IO Exception Occurred");
        }

    }
}
