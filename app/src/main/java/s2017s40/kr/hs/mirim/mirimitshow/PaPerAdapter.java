package s2017s40.kr.hs.mirim.mirimitshow;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class PaPerAdapter extends RecyclerView.Adapter<PaPerAdapter.ViewHolder> {
    private ArrayList<MyPaPerDTO> mDataset;
    private PaPerAdapter.ClickCallback callback;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public TextView mTextView_title;
        public TextView mTextView_num;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTextView_title = (TextView)view.findViewById(R.id.item_paper_title_text);
            mTextView_num = (TextView)view.findViewById(R.id.item_paper_num_text);
        }
    }
    public PaPerAdapter(ArrayList<MyPaPerDTO> myDataset, PaPerAdapter.ClickCallback clickCallback) {
        mDataset = myDataset;
        this.callback = clickCallback;
    }

    @Override
    public PaPerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view_paper, parent, false);
        PaPerAdapter.ViewHolder vh = new PaPerAdapter.ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(PaPerAdapter.ViewHolder holder, final int position) {
        holder.mTextView_title.setText(mDataset.get(position).getTitle());
        holder.mTextView_num.setText(mDataset.get(position).getContent());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onItemClick(position);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
    public interface ClickCallback {
        void onItemClick(int position);
    }
}

