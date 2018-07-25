package com.mvopo.myresume.Helper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mvopo.myresume.Model.Message;
import com.mvopo.myresume.R;

import java.util.List;

/**
 * Created by mvopo on 5/8/2018.
 */

public class ListAdapter extends ArrayAdapter {
    private Context mContext;
    private int layoutID;
    private List<Message> messages;

    public ListAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);

        mContext = context;
        layoutID = resource;
        messages = objects;
    }

    @Override
    public int getCount() {
        int size = 0;

        if (messages != null) size = messages.size();

        return size;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (layoutID == R.layout.message_in_layout) {
            if (messages.get(position).getType().equals("in"))
                convertView = LayoutInflater.from(mContext).inflate(R.layout.message_in_layout, parent, false);
            else
                convertView = LayoutInflater.from(mContext).inflate(R.layout.message_out_layout, parent, false);

            TextView messageBody = convertView.findViewById(R.id.message_body);
            messageBody.setText(messages.get(position).getBody());
        }

        return convertView;
    }
}
