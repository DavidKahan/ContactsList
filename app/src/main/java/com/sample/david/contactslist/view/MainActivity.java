package com.sample.david.contactslist.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.sample.david.contactslist.R;
import com.sample.david.contactslist.model.Contact;
import com.sample.david.contactslist.presenter.MainPresenter;

import java.util.List;

/**
 * Created by david on 08/07/2017.
 */

public class MainActivity extends Activity {

    private MainPresenter mainPresenter;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Contact> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainPresenter = new MainPresenter(this);
        contactList =  mainPresenter.getAllContactsFromLocal();
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new MyContactsAdapter(contactList, MainActivity.this);
        mRecyclerView.setAdapter(mAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                // Remove item from backing list here
                final int position = viewHolder.getAdapterPosition();
                Contact contactToDelete = contactList.get(position);
                mainPresenter.deleteContact(contactToDelete.getID());
                contactList.remove(position);
                mAdapter.notifyDataSetChanged();
            }
        });
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }
}
