package s2017s40.kr.hs.mirim.mirimitshow.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import s2017s40.kr.hs.mirim.mirimitshow.Activities.ViewPaperActivity;
import s2017s40.kr.hs.mirim.mirimitshow.R;
import s2017s40.kr.hs.mirim.mirimitshow.Scan;

public class PaperListAdapter extends  RecyclerView.Adapter<PaperListAdapter.ViewHolder>{
    private ArrayList<Scan> items;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView paper_title;
        View view;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            paper_title =view.findViewById(R.id.item_sub_paper_title);
        }
    }

    public PaperListAdapter(ArrayList<Scan> items, Context con){
        this.items = items;
        context = con;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_view_sub_paper, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String title_str = items.get(i).getCartegory();
        viewHolder.paper_title.setText(title_str);
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewPaperActivity.class);
                intent.putExtra("paperName", items.get(i).getName());
                intent.putExtra("paperUri", items.get(i).getImage().toString());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}