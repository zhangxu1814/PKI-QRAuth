package com.Vshows.PKI.util;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.Vshows.PKI.R;
import com.Vshows.PKI.changeToken;
import com.google.gson.Gson;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.tomahawkd.pki.api.client.Connecter;

public class TokenListAdapter extends ArrayAdapter implements View.OnClickListener {
    private int layoutID;

    private InnerItemOnclickListener mylistener;

    public TokenListAdapter(Context context, int layout, List<TokenList> tList){
        super(context,layout,tList);
        layoutID = layout;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TokenList tokenList = (TokenList) getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(layoutID,null);
        RecyclerView.ViewHolder viewHolder;

        TextView date = (TextView)view.findViewById(R.id.token_date);
        TextView Ip = (TextView)view.findViewById(R.id.token_ip);
        TextView device = (TextView)view.findViewById(R.id.token_device);
        TextView id = (TextView)view.findViewById(R.id.token_ID);
        Button revoke = (Button) view.findViewById(R.id.revoke);


        date.setText(tokenList.getDate());
        Ip.setText(tokenList.getIp());
        device.setText(tokenList.getDevice());
        id.setText(tokenList.getID());


        revoke.setOnClickListener(this);

        revoke.setTag(id.getText().toString());

        return view;
    }

    public interface InnerItemOnclickListener {
        void itemClick(View v);
    }

    public void setOnInnerItemOnClickListener(InnerItemOnclickListener listener){
        this.mylistener=listener;
    }

    @Override
    public void onClick(View view) {
        mylistener.itemClick(view);
    }
}
