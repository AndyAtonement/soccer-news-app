package com.example.soccernews.ui.adapters;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.soccernews.databinding.NewsItemBinding;
import com.example.soccernews.domain.News;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private List<News> news;
    private View.OnClickListener favoritesListener;

    public NewsAdapter(List<News> news, View.OnClickListener favoritesListener) {
        this.news = news;
        this.favoritesListener = favoritesListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflate = LayoutInflater.from(parent.getContext());
        NewsItemBinding binding = NewsItemBinding.inflate(layoutInflate, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        News news = this.news.get(position);
        holder.binding.tvTitle.setText(news.title);
        holder.binding.tvDescription.setText(news.description);
        Picasso.get().load(news.image).fit().into(holder.binding.ivThumbnail);
        //Implementação da funcionalidade de "Abrir Link"
        holder.binding.btnOpenlink.setOnClickListener(view -> {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(news.link));
            holder.itemView.getContext().startActivity(i);
        });
        //Implementação da funcionalidade de Compartilhar"
        holder.binding.ivShare.setOnClickListener(view -> {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, news.title);
            i.putExtra(Intent.EXTRA_TEXT, news.link);
            holder.itemView.getContext().startActivity(Intent.createChooser(i, "Share"));
        });
        //Implementação da funcionalidade de Favoritar" (O evento será instanciado pelo fragment)
        holder.binding.ivFavorites.setOnClickListener(this.favoritesListener);
    }

    @Override
    public int getItemCount() {
        return this.news.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final NewsItemBinding binding;

        public ViewHolder(NewsItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
