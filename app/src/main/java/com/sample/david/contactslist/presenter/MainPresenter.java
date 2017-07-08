package com.sample.david.contactslist.presenter;

import android.app.Activity;

import com.sample.david.contactslist.model.Contact;
import com.sample.david.contactslist.model.DatabaseHandler;

import java.util.List;

/**
 * Created by david on 08/07/2017.
 */

public class MainPresenter {

    private Activity mViewActivity;
    DatabaseHandler db;

    public MainPresenter(Activity activity) {
        this.mViewActivity = activity;
    }

    public List<Contact> getAllContactsFromLocal(){
        return DatabaseHandler.getInstance(mViewActivity).getAllContacts();
    }

    public void deleteContact(String id) {
        DatabaseHandler.getInstance(mViewActivity).deleteContact(id);
    }
}
