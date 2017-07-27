package digitalhouse.android.View.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import digitalhouse.android.Util.GlideApp;


import java.util.ArrayList;
import java.util.List;

import digitalhouse.android.Model.POJO.Album;
import digitalhouse.android.Util.GlideApp;
import com.bumptech.glide.Glide;
import digitalhouse.android.a0317moacns1c_01.R;

/**
 * Created by gaston on 23/06/17.
 */

public class AdapterAlbumList extends RecyclerView.Adapter implements View.OnClickListener {

    private List<Album> albumList = new ArrayList<>();
    private Context context;
    private View.OnClickListener onClickListener;

    public AdapterAlbumList(Context context) {
        this.context = context;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public Album getItem(Integer position) {
        return albumList.get(position);
    }



    public void setAlbumList(List<Album> albumList) {
        this.albumList = albumList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(R.layout.layout_cell_album, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        view.setOnClickListener(this);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Album album = albumList.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;

        viewHolder.loadData(album);
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    @Override
    public void onClick(View v) {
        if (onClickListener != null) {
            onClickListener.onClick(v);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewAlbumImage;
        private TextView textViewAlbumName;

        public ViewHolder(View itemView) {
            super(itemView);
            imageViewAlbumImage = (ImageView) itemView.findViewById(R.id.imageViewAlbumImage);
            textViewAlbumName = (TextView) itemView.findViewById(R.id.textViewAlbumTitle);
        }

        public void loadData(Album album){
            GlideApp.with(context).load(album.getCover_medium()).placeholder(R.drawable.placeholdermusic).into(imageViewAlbumImage);
            textViewAlbumName.setText(album.getTitle().toString());
            textViewAlbumName.setTypeface(robotoLight(context));
        }
    }

    //retornamos las fuentes necesarias
    public static Typeface robotoLight(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Light.ttf");
    }

    public static Typeface robotoBold(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Bold.ttf");
    }

}
