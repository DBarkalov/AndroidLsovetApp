package com.diolan.lsovet.network;

import com.diolan.lsovet.data.ArticleHeader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by d.barkalov on 08.11.2014.
 */
public class GetHeadersListRequest {
    private static final String REQUEST_URL = "http://glassy-acolyte-752.appspot.com/headers";

    public List<ArticleHeader> execute() throws IOException, JSONException {
        NetworkRequest networkRequest = new NetworkRequest(REQUEST_URL);
        NetworkRequest.Responce responce = networkRequest.execute();
        if(responce.getStatus() == 200 && responce.getContent() != null) {
            return parseResponce(responce.getContent());
        }
        // ERROR
        return null;
    }

    private List<ArticleHeader> parseResponce(byte[] content) throws JSONException, UnsupportedEncodingException {
        final List<ArticleHeader> list = new ArrayList<ArticleHeader>();
        final JSONArray jsonArray = new JSONArray(new String(content, "UTF-8"));
        for(int i=0; i<jsonArray.length();i++ ){
           JSONObject jsonObject = (JSONObject) jsonArray.get(i);
           final ArticleHeader articleHeader = ArticleHeader.fromJson(jsonObject);
           list.add(articleHeader);
        }
        return list;
    }
}
