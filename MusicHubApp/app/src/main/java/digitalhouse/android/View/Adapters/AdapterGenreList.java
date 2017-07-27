package digitalhouse.android.View.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

import digitalhouse.android.Model.POJO.Genre;
import digitalhouse.android.Util.GlideApp;
import digitalhouse.android.a0317moacns1c_01.R;

/**
 * Created by gaston on 22/06/17.
 */

public class AdapterGenreList extends RecyclerView.Adapter implements View.OnClickListener{

    private List<Genre> genreList;
    private Context context;
    private View.OnClickListener onClickListener;

    public AdapterGenreList(List<Genre> genteList, Context context) {
        this.genreList = genteList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(R.layout.layout_cell_genre, parent, false);
        view.setOnClickListener(this);

        ViewHolder myViewHolder = new ViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Genre genre = genreList.get(position);
        ViewHolder myViewHolder = (ViewHolder) holder;

        myViewHolder.loadData(genre);
    }

    @Override
    public int getItemCount() {
        return genreList.size();
    }

    public void updateList(List<Genre> genreList){
        this.genreList = genreList;
        notifyDataSetChanged();
    }


    public void setGenreList(List<Genre> genreList) {
        this.genreList = genreList;
        notifyDataSetChanged();
    }

    public Genre getGenreItem(Integer position) {
        return genreList.get(position);
    }


    @Override
    public void onClick(View v) {
        if(onClickListener != null) {
            onClickListener.onClick(v);
        }
    }

    public void setClickListener (View.OnClickListener listener){
        onClickListener = listener;
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewGenreImage;
        private TextView textViewGenreName;

        public ViewHolder(View itemView) {
            super(itemView);
            imageViewGenreImage = (ImageView) itemView.findViewById(R.id.genreImage);
            textViewGenreName = (TextView) itemView.findViewById(R.id.genreText);
        }

        public void loadData(Genre genre){
            GlideApp.with(context).load(genre.getGenrePictureResource()).placeholder(R.drawable.placeholdermusic).into(imageViewGenreImage);
            textViewGenreName.setText(genre.getGenreName());
            textViewGenreName.setTypeface(robotoLight(context));
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
