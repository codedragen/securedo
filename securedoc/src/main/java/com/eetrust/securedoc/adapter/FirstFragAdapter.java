package com.eetrust.securedoc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.eetrust.securedoc.R;
import com.eetrust.securedoc.ui.FileDialog;
import com.eetrust.securedoc.ui.MdCheckbox;

import java.util.List;

/**
 * Created by long on 2016/6/10.
 */
public class FirstFragAdapter extends BaseAdapter {
    private final Context context;
    private final List<String> data;
    private  boolean checkMode;

    public  FirstFragAdapter(Context context, List<String> data){
       this.context=context;
       this.data=data;
   }

    public void setCheckMode(boolean checkMode) {
        this.checkMode = checkMode;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
          ViewHolder holder;
        if (convertView==null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.firstfragment_lv_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else
            holder= (ViewHolder) convertView.getTag();

          holder.tv_filename.setText(data.get(position));
        holder.iv_operate.setVisibility(View.VISIBLE);
        holder.cb_check.setVisibility(View.GONE);
        if (checkMode) {
            holder.cb_check.setVisibility(View.VISIBLE);
            holder.iv_operate.setVisibility(View.GONE);
        }
        holder.iv_operate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileDialog dialog=new FileDialog(context,data.get(position));

                dialog.show();
            }
        });


        return convertView;
    }

    class ViewHolder  {
        public TextView tv_filename;
        public ImageView iv_icon;
        public TextView tv_date;
        public TextView tv_time;
        public ImageView iv_operate;
        public MdCheckbox cb_check;
        ViewHolder(View itemView){
            tv_date= (TextView) itemView.findViewById(R.id.date);
            tv_filename= (TextView) itemView.findViewById(R.id.filename);
            iv_icon= (ImageView) itemView.findViewById(R.id.iv_icon);
            tv_time= (TextView) itemView.findViewById(R.id.time);
            iv_operate= (ImageView) itemView.findViewById(R.id.operate);

            cb_check= (MdCheckbox) itemView.findViewById(R.id.check);
        }



    }
}
