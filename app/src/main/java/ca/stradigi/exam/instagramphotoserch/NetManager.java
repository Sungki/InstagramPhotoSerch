package ca.stradigi.exam.instagramphotoserch;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class NetManager {
    private static final String LOG_TAG = "NetManager";

    public static JSONObject requestHttp(String serviceUrl) {
        HttpURLConnection httpConnection = null;

        try {
            URL url = new URL(serviceUrl);
            httpConnection = (HttpURLConnection)url.openConnection();
            httpConnection.setConnectTimeout(0);
            httpConnection.setReadTimeout(0);

            int status = httpConnection.getResponseCode();
            if (status == HttpURLConnection.HTTP_UNAUTHORIZED) {
                Log.w(LOG_TAG, "HttpURLConnection Error " + status);
                return null;
            } else if (status != HttpURLConnection.HTTP_OK) {
                Log.w(LOG_TAG, "HttpURLConnection Error " + status);
                return null;
            }

            InputStream in = new BufferedInputStream(httpConnection.getInputStream());

            return new JSONObject(getResponseText(in));

        } catch (MalformedURLException e) {
            Log.w(LOG_TAG, "MalformedURLException Error " + serviceUrl, e);
            e.printStackTrace();
        } catch (SocketTimeoutException e) {
            Log.w(LOG_TAG, " SocketTimeoutException Error " + serviceUrl, e);
            e.printStackTrace();
        } catch (IOException e) {
            Log.w(LOG_TAG, "SocketTimeoutException Error "+ serviceUrl, e);
            e.printStackTrace();
        } catch (JSONException e) {
            Log.w(LOG_TAG, "SocketTimeoutException Error " + serviceUrl, e);
            e.printStackTrace();
        } finally {
            if (httpConnection != null) {
                httpConnection.disconnect();
            }
        }

        return null;
    }

    private static String getResponseText(InputStream inStream) {
        return new Scanner(inStream).useDelimiter("\\A").next();
    }

    public static Bitmap downloadBitmap(String url) {
        Bitmap imgBitmap = null;

        try {
            final URL imgUrl = new URL(url);
            URLConnection conn = imgUrl.openConnection();
            conn.connect();

            int nSize = conn.getContentLength();
            BufferedInputStream bis = new BufferedInputStream(conn.getInputStream(), nSize);
            imgBitmap = BitmapFactory.decodeStream(bis);
            bis.close();

        } catch (IOException e) {
            Log.w(LOG_TAG, "URLConnection Error " + url, e);
            e.printStackTrace();
        }

        return imgBitmap;

    }
}