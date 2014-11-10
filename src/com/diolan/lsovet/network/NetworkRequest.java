package com.diolan.lsovet.network;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by d.barkalov on 08.11.2014.
 */
public class NetworkRequest {
    private final String mUrl;

    public NetworkRequest(String url){
        mUrl = url;
    }

    public Responce execute() throws IOException {
        URL myURL = new URL(mUrl);
        HttpURLConnection con = (HttpURLConnection)myURL.openConnection();
        con.setRequestProperty("Accept-Charset", "UTF-8");
        con.connect();
        return new Responce(con.getResponseCode(),readByParts(con.getInputStream()));
    }

    private byte[] readByParts(InputStream is) throws IOException {
        byte[] data;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        int readBlockSize = 4048;
        byte[] readBlockBuffer = new byte[readBlockSize];
        int actual = 0;
        while ((actual != -1)) {
            actual = is.read(readBlockBuffer, 0, readBlockSize);
            if (actual > 0) {
                dos.write(readBlockBuffer, 0, actual);
            }
        }
        data = baos.toByteArray();
        return data;
    }

    public static class Responce {
        private final int status;
        private final byte[] content;

        public Responce(int status, byte[] content) {
            this.status = status;
            this.content = content;
        }

        public int getStatus() {
            return status;
        }

        public byte[] getContent() {
            return content;
        }
    }

}
