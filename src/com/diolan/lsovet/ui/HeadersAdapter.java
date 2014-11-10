package com.diolan.lsovet.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.diolan.lsovet.data.ArticleHeader;

import java.util.ArrayList;
import java.util.List;
import com.diolan.lsovet.R;

/**
 * Created by d.barkalov on 06.11.2014.
 */
public class HeadersAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<ArticleHeader> mArticleHeaders;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTitle;
        public TextView mText;
        public TextView mAuthor;
        public TextView mDate;
        public ImageView mImageView;
        public ViewHolder(View v) {
            super(v);
            mTitle = (TextView)v.findViewById(R.id.title_text);
            mText = (TextView)v.findViewById(R.id.text);
            mAuthor = (TextView)v.findViewById(R.id.author);
            mDate = (TextView)v.findViewById(R.id.date);
            mImageView = (ImageView)v.findViewById(R.id.preview_image);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public HeadersAdapter(Context context) {
        mContext = context;
        mArticleHeaders = new ArrayList<ArticleHeader>();
    }


    public void setList(List<ArticleHeader> headers){
        mArticleHeaders = headers;
        notifyDataSetChanged();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public HeadersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.header_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final ArticleHeader header = mArticleHeaders.get(position);
        ((ViewHolder)holder).mTitle.setText(header.getTitle());
        ((ViewHolder)holder).mText.setText(header.getText());
        ((ViewHolder)holder).mAuthor.setText(header.getAuthor());
        ((ViewHolder)holder).mDate.setText(header.getDate());

        Glide.with(mContext)
                .load(header.getThumbnail())
                .centerCrop()
                .placeholder(R.drawable.preview_stub)
                .crossFade()
                .into(((ViewHolder)holder).mImageView);
    }

      // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mArticleHeaders.size();
    }

}
