package com.sample.david.contactslist.view;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sample.david.contactslist.R;
import com.sample.david.contactslist.model.Contact;

import java.util.List;

/**
 * Created by david on 08/07/2017.
 */
public class MyContactsAdapter extends RecyclerView.Adapter<MyContactsAdapter.ViewHolder>{

    private List<Contact> myContactList;
    private Activity mActivity;

    public MyContactsAdapter(List<Contact> contactList, Activity mainActivity) {
        myContactList = contactList;
        mActivity = mainActivity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Contact currentContact = myContactList.get(position);
        holder.contactName.setText(currentContact.getName());
        holder.contactMobile.setText(currentContact.getPhoneNumber());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+holder.contactMobile.getText().toString()));
                mActivity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myContactList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView contactName, contactMobile;
        View contactView;
        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setClickable(true);
            contactView = itemView;
            contactName = (TextView) itemView.findViewById(R.id.contactName);
            contactMobile = (TextView) itemView.findViewById(R.id.contactMobile);
        }
    }
}
