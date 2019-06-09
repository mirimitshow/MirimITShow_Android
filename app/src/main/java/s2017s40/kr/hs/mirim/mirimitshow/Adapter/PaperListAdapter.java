package s2017s40.kr.hs.mirim.mirimitshow.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import s2017s40.kr.hs.mirim.mirimitshow.R;

public class PaperListAdapter extends BaseAdapter {

    ArrayList<String> lists = new ArrayList<>();

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.paper_list_item, parent, false);
        }

        TextView paperName = convertView.findViewById(R.id.paperList_Name);
        paperName.setText(lists.get(position));

        return convertView;
    }

    public void addItem(String text){
        lists.add(text);
    }
}