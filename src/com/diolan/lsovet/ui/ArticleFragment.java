package com.diolan.lsovet.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.diolan.lsovet.R;

public class ArticleFragment extends Fragment {

    private WebView mWebView;

    public ArticleFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_article_view, container, false);
        mWebView = (WebView) rootView.findViewById(R.id.webView);

        String url = getArguments().getString(HeadersAdapter.ARTICLE_URL);
        mWebView.loadUrl(url);

        return rootView;
    }
}
