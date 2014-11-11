package com.diolan.lsovet.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
public class HeadersAdapter extends RecyclerView.Adapter implements OnItemClickListener {

    public static final String ARTICLE_URL = "ARTICLE_URL";
    private Context mContext;
    private List<ArticleHeader> mArticleHeaders;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTitle;
        private TextView mText;
        private TextView mAuthor;
        private TextView mDate;
        private ImageView mImageView;
        private OnItemClickListener mClickListener;

        public ViewHolder(View v, OnItemClickListener clickListener) {
            super(v);
            mClickListener = clickListener;
            mTitle = (TextView)v.findViewById(R.id.title_text);
            mText = (TextView)v.findViewById(R.id.text);
            mAuthor = (TextView)v.findViewById(R.id.author);
            mDate = (TextView)v.findViewById(R.id.date);
            mImageView = (ImageView)v.findViewById(R.id.preview_image);

            mTitle.setOnClickListener(this);
            mImageView.setOnClickListener(this);
            mDate.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mClickListener.onItemClick(getPosition());
        }

    }

    @Override
    public void onItemClick(int itemPosition) {
        String url = mArticleHeaders.get(itemPosition).getLink();
        Intent intent = new Intent(mContext, ArticleViewActivity.class);
        intent.putExtra(ARTICLE_URL, url);
        mContext.startActivity(intent);
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
        ViewHolder vh = new ViewHolder(v, this);
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
