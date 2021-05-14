package com.example.myprojectx.ATask;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import com.example.myprojectx.Adapter.MyItemAdapter;
import com.example.myprojectx.Dto.MyItem;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static com.example.myprojectx.Common.CommonMethod.ipConfig;

public class ListSelect extends AsyncTask<Void, Void, Void> {

    ArrayList<MyItem> arrayList;
    MyItemAdapter adapter;
    ProgressDialog dialog;

    public ListSelect(ArrayList<MyItem> arrayList, MyItemAdapter adapter, ProgressDialog dialog) {
        this.arrayList = arrayList;
        this.adapter = adapter;
        this.dialog = dialog;
    }

    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;

    @Override
    protected Void doInBackground(Void... voids) {
        arrayList.clear();
        String postURL = ipConfig + "/app/anSelectMulti";

        try{
            // MultipartEntityBuild 생성
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

            // 전송
            InputStream inputStream = null;
            httpClient = AndroidHttpClient.newInstance("Android");
            httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            httpResponse = httpClient.execute(httpPost);
            httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();

            readJsonStream(inputStream);

        } catch (Exception e) {
            Log.d("Sub1", e.getMessage());
            e.printStackTrace();
        }finally {
            if(httpEntity != null){
                httpEntity = null;
            }
            if(httpResponse != null){
                httpResponse = null;
            }
            if(httpPost != null){
                httpPost = null;
            }
            if(httpClient != null){
                httpClient = null;
            }

        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        if(dialog != null){
            dialog.dismiss();
        }

        Log.d("Sub1", "List Select Complete!!!");

        adapter.notifyDataSetChanged();
    }

    public void readJsonStream(InputStream inputStream) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
        try {
            reader.beginArray();
            while (reader.hasNext()) {
                arrayList.add(readMessage(reader));
            }
            reader.endArray();
        } finally {
            reader.close();
        }
    }

    public MyItem readMessage(JsonReader reader) throws IOException {
        String id = "", name = "", hire_date = "", image_path = "";

        reader.beginObject();
        while (reader.hasNext()) {
            String readStr = reader.nextName();
            if (readStr.equals("id")) {
                id = reader.nextString();
            } else if (readStr.equals("name")) {
                name = reader.nextString();
            } else if (readStr.equals("hire_date")) {
                String[] temp = reader.nextString().replace("월", "-").replace(",", "-")
                        .replace(" ", "").split("-");
                hire_date = temp[2] + "-" + temp[0] + "-" + temp[1];
            } else if (readStr.equals("image_path")) {
                image_path = reader.nextString();
            }else {
                reader.skipValue();
            }
        }
        reader.endObject();
        Log.d("listselect:myitem", id + "," + name + "," + hire_date + "," + image_path);
        return new MyItem(id, name, hire_date, image_path);

    }


}
