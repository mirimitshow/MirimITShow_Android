package s2017s40.kr.hs.mirim.mirimitshow;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class GroupSub1Adapter extends RecyclerView.Adapter<GroupSub1Adapter.ViewHolder> {
    private ArrayList<SubGroup1DTO> mDataset;
    private GroupSub1Adapter.ClickCallback callback;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        public TextView mTextView_title;
        public TextView mTextView_content;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTextView_title = (TextView)view.findViewById(R.id.item_sub_group_title_text);
            mTextView_content = (TextView)view.findViewById(R.id.item_sub_group_content_text);
        }
    }
    public GroupSub1Adapter(ArrayList<SubGroup1DTO> myDataset,  GroupSub1Adapter.ClickCallback clickCallback) {
        mDataset = myDataset;
        this.callback = clickCallback;
    }

    @Override
    public GroupSub1Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view_sub_group1, parent, false);
        GroupSub1Adapter.ViewHolder vh = new GroupSub1Adapter.ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(GroupSub1Adapter.ViewHolder holder, final int position) {
        holder.mTextView_title.setText(mDataset.get(position).getTitle());
        holder.mTextView_content.setText(mDataset.get(position).getSub());
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
