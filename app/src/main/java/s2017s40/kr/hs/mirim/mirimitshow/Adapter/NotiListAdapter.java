package s2017s40.kr.hs.mirim.mirimitshow.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import s2017s40.kr.hs.mirim.mirimitshow.Classes.NotiClass;
import s2017s40.kr.hs.mirim.mirimitshow.R;

public class NotiListAdapter extends BaseAdapter {

    ArrayList<NotiClass> list =  new ArrayList<>();
    LayoutInflater inflater = null;

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null)
        {
            final Context context = parent.getContext();
            if (inflater == null)
            {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            convertView = inflater.inflate(R.layout.noti_list_item, parent, false);
        }

        View notiFlag = convertView.findViewById(R.id.noticeFlag);
        TextView notiGroupName = (TextView) convertView.findViewById(R.id.notiGroupName);
        TextView notiContent = (TextView) convertView.findViewById(R.id.notiContent);

        notiGroupName.setText(list.get(position).getNotifyGroup());
        notiContent.setText(list.get(position).getNotifyContent());

        if(list.get(position).isNotice()){
            notiFlag.setBackgroundResource(R.color.colorMain);
        }
        return convertView;
    }



    public void setItem(NotiClass obj){
        list.add(obj);
    }
}
