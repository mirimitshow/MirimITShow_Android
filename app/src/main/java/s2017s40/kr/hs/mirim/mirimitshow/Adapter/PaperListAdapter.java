package s2017s40.kr.hs.mirim.mirimitshow.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import s2017s40.kr.hs.mirim.mirimitshow.Activities.ViewPaperActivity;
import s2017s40.kr.hs.mirim.mirimitshow.Board;
import s2017s40.kr.hs.mirim.mirimitshow.R;
import s2017s40.kr.hs.mirim.mirimitshow.Scan;

public class PaperListAdapter extends  RecyclerView.Adapter<PaperListAdapter.ViewHolder>{
    private ArrayList<Scan> items;
    private String scanUri, scanName;
    private Context context;
    private PaperListAdapter.ClickCallback callback;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView paper_title;
        View view;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            paper_title =view.findViewById(R.id.item_sub_paper_title);
        }
    }

    public PaperListAdapter(ArrayList<Scan> items, PaperListAdapter.ClickCallback clickCallback){
        this.items = items;
        this.callback = clickCallback;
    }

    @Override
    public PaperListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_view_sub_paper, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PaperListAdapter.ViewHolder holder, final int position) {

        holder.paper_title.setText(items.get(position).getName());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanUri = items.get(position).getUrl();
                scanName = items.get(position).getName();
                callback.onItemClick(position, scanUri, scanName);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public interface ClickCallback {
        void onItemClick(int position, String scanUri, String scanName);
    }
}