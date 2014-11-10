package com.diolan.lsovet.ui;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.diolan.lsovet.data.ArticleHeader;
import com.diolan.lsovet.network.GetHeadersListRequest;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;
import com.diolan.lsovet.R;

public class ListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private HeadersAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout mSwipeRefresh;

    public ListFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        refreshList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mSwipeRefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_view);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshList();
            }
        });

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new HeadersAdapter(this.getActivity());
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    private void refreshList(){
        new AsyncTask<Void,Void, List<ArticleHeader>>(){

            @Override
            protected List<ArticleHeader> doInBackground(Void... params) {
                try {
                    return new GetHeadersListRequest().execute();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(List<ArticleHeader> articleHeaders) {
                if(articleHeaders != null) {
                    if(isAdded()) {
                        mAdapter.setList(articleHeaders);
                    }
                } else {
                    if(isAdded()) {
                        Toast.makeText(getActivity(),getString(R.string.error),Toast.LENGTH_SHORT);
                    }
                }
                mSwipeRefresh.setRefreshing(false);
            }
        }.execute();
    }
}
