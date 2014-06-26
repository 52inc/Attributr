package com.ftinc.attributr.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.ftinc.attributr.R;
import com.ftinc.attributr.model.Library;

import java.util.List;

/**
 * The list adapter for the license fragment
 */
public class LibraryListAdapter extends BetterListAdapter<Library> {

    /**
     * Variables
     */
    private ILibraryActionListener mActionListener;

    /**
     * Constructor
     *
     * @param context            application context
     * @param textViewResourceId the item view id
     * @param objects            the list of objects
     */
    public LibraryListAdapter(Context context, int textViewResourceId, List<Library> objects) {
        super(context, textViewResourceId, objects);
    }

    /**
     * Set the action listener for this adapter
     * @param listener      the action listener
     */
    public void setActionListener(ILibraryActionListener listener){
        mActionListener = listener;
    }


    @Override
    public ViewHolder createHolder(View view) {
        LibraryViewHolder holder = new LibraryViewHolder();
        holder.name = (TextView) view.findViewById(R.id.name);
        holder.author = (TextView) view.findViewById(R.id.author);
        holder.source = (ImageView) view.findViewById(R.id.source_link);
        holder.licenseText = (TextView) view.findViewById(R.id.license_text);
        return holder;
    }

    @Override
    public void bindHolder(ViewHolder holder, int position) {
        LibraryViewHolder lvh = (LibraryViewHolder) holder;
        final Library data = getItem(position);

        // Bind data to the view holder
        lvh.name.setText(data.name);
        lvh.author.setText(data.author);

        // Bind action listeners to image views
        lvh.source.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mActionListener != null) mActionListener.onSourceClicked(data.source);
                else{
                    Intent link = data.getSourceIntent();
                    getContext().startActivity(link);
                }
            }
        });

        // Set License Text
        lvh.licenseText.setText(data.licenseText);
    }

    /**
     * This adapters viewholder
     */
    static class LibraryViewHolder extends ViewHolder{
        TextView name, author, licenseText;
        ImageView source;
    }

    /**
     * This action listener for the source and licences buttons
     * in this adapter
     */
    public static interface ILibraryActionListener{
        public void onSourceClicked(String sourceLink);
        public void onLicenseClicked(String licenseLink);
    }

}