package s2017s40.kr.hs.mirim.mirimitshow.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import s2017s40.kr.hs.mirim.mirimitshow.Group;
import s2017s40.kr.hs.mirim.mirimitshow.R;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder> {
    private ArrayList<Group> mDataset;
    private ClickCallback callback;

    String token;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        public LinearLayout mImageView;
        public TextView mTextView_name;
        public TextView mTextView_participants;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mImageView = (LinearLayout)view.findViewById(R.id.item_group_image);
            mTextView_name = (TextView)view.findViewById(R.id.item_group_name_text);
            mTextView_participants = (TextView)view.findViewById(R.id.item_group_participants_text);
        }
    }
    public GroupAdapter(ArrayList<Group> myDataset, ClickCallback clickCallback) {
        mDataset = myDataset;
        this.callback = clickCallback;
    }

    @Override
    public GroupAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view_group, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.mImageView.setBackgroundColor(mDataset.get(position).getColor());
        holder.mTextView_name.setText(mDataset.get(position).getName());
        holder.mTextView_participants.setText(String.valueOf(mDataset.get(position).getMembers().size()));

        token = mDataset.get(position).getToken();

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onItemClick(position, token);

            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)a
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
    public interface ClickCallback {
        void onItemClick(int position, String token);
    }
}
