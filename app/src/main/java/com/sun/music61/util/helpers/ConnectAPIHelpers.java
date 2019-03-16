package com.sun.music61.util.helpers;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * This provides methods to help connect api working with AsyncTask.
 */
public class ConnectAPIHelpers extends AsyncTask<String, Void, HashMap<String, String>> {

    private static final String TAG = ConnectAPIHelpers.class.getName();

    private static final int READ_TIMEOUT = 5000;
    private static final int CONNECTION_TIMEOUT = 5000;

    /**
     * Key for HashMap response.
     */
    public interface Constants {
        String DATA = "DATA";
        String STATUS_CODE = "STATUS_CODE";
    }

    /**
     * Used with the type method in request.
     */
    public interface Method {
        String GET = "GET";
        String POST = "POST";
    }

    private String mUrl;

    private String mMethod;

    private StringBuffer mData;

    private List<HashMap<String, String>> mAttributes;

    public ConnectAPIHelpers(@NonNull String url) {
        mUrl = checkNotNull(url);
        mMethod = Method.GET;
    }

    public ConnectAPIHelpers(@NonNull String url, String method) {
        mUrl = checkNotNull(url);
        mMethod = method;
    }

    public ConnectAPIHelpers(@NonNull String url, String method, List<HashMap<String, String>> attrs) {
        mUrl = checkNotNull(url);
        mMethod = method;
        mAttributes = attrs;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected HashMap<String, String> doInBackground(String... strings) {
        int statusCode = 0;
        HashMap<String, String> response = new HashMap<>();
        try {
            URL urlParse = new URL(mUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlParse.openConnection();
            httpURLConnection.setConnectTimeout(CONNECTION_TIMEOUT);
            httpURLConnection.setReadTimeout(READ_TIMEOUT);
            switch (mMethod) {
                case Method.GET:
                    httpURLConnection.setRequestMethod(Method.GET);
                    mData = fetchData(httpURLConnection);
                    break;
                case Method.POST:
                    httpURLConnection.setRequestMethod(Method.POST);
                    mData = configPostMethod(httpURLConnection);
                    break;
            }

            statusCode = httpURLConnection.getResponseCode();
            response.put(Constants.DATA, String.valueOf(mData));
            response.put(Constants.STATUS_CODE, String.valueOf(statusCode));

        } catch (MalformedURLException e) {
            Log.e(TAG, "doInBackground: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "doInBackground: " + e.getMessage());
        }

        return response;
    }

    private StringBuffer fetchData(HttpURLConnection httpURLConnection) {
        InputStream inputStream;
        try {
            httpURLConnection.connect();
            inputStream = httpURLConnection.getInputStream();
            InputStreamReader reader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(reader);

            StringBuffer data = new StringBuffer();

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                data.append(line);
            }

            // Free memory
            bufferedReader.close();
            reader.close();
            inputStream.close();
            httpURLConnection.disconnect();

            return data;

        } catch (MalformedURLException e) {
            Log.e(TAG, "fetchData: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "fetchData: " + e.getMessage());
        }
        return null;
    }

    private StringBuffer configPostMethod(HttpURLConnection httpURLConnection) {
        try {
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            Uri.Builder builder = new Uri.Builder();

            for (int i = 0; i < mAttributes.size(); i++) {
                String key = null;
                String value = null;
                for (Map.Entry<String, String> values : mAttributes.get(i).entrySet()) {
                    key = values.getKey();
                    value = values.getValue();
                }
                builder.appendQueryParameter(key, value);
            }

            String query = builder.build().getEncodedQuery();
            OutputStream outputStream = httpURLConnection.getOutputStream();
            OutputStreamWriter streamWriter = new OutputStreamWriter(outputStream);
            BufferedWriter writer = new BufferedWriter(streamWriter);

            writer.write(query);
            writer.flush();

            // Free memory
            writer.close();
            streamWriter.close();
            outputStream.close();

            return fetchData(httpURLConnection);

        } catch (ProtocolException e) {
            Log.e(TAG, "configPostMethod: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "configPostMethod: " + e.getMessage());
        }
        return null;
    }
}
