package org.gdg_lome.codelab_recyclerview;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by setico on 25/12/2015.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private Context context;
    private ArrayList<HashMap<String, String>> programmes;


    public RecyclerViewAdapter(Context context, ArrayList<HashMap<String, String>> programmes) {
        this.programmes = programmes;
        this.context = context;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final ImageView logo;
        public final TextView nom;
        public final TextView description;
        public final ProgressBar progress;
        public ItemClickListener clickListener;


        public ViewHolder(View itemView) {
            super(itemView);
                logo = (ImageView) itemView.findViewById(R.id.logo);
                nom = (TextView) itemView.findViewById(R.id.nom);
                description = (TextView) itemView.findViewById(R.id.description);
                progress = (ProgressBar) itemView.findViewById(R.id.progress);
            itemView.setOnClickListener(this);
        }

        public void setClickListener(ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }
        @Override
        public void onClick(View v) {
            clickListener.onClick(v, getPosition(), false);
        }

    }


    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {
        final HashMap<String, String> programme = programmes.get(position);
        final ViewHolder viewHolder = holder;
        viewHolder.nom.setText(programme.get(Config.NOM_KEY));
        viewHolder.description.setText(programme.get(Config.DESCRIPTION_KEY));
        Glide.with(context)
                .load(programme.get(Config.LOGO_KEY))
                .centerCrop()
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        viewHolder.progress.setVisibility(View.GONE);
                        return false;
                    }
                }) .into(holder.logo);
        viewHolder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Intent i = new Intent(context,DetailActivity.class);
                i.putExtra(Config.LOGO_KEY,programme.get(Config.LOGO_KEY));
                i.putExtra(Config.NOM_KEY,programme.get(Config.NOM_KEY));
                i.putExtra(Config.DESCRIPTION_KEY,programme.get(Config.DESCRIPTION_KEY));
                context.startActivity(i);
            }
        });


    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return programmes.size();
    }


}
