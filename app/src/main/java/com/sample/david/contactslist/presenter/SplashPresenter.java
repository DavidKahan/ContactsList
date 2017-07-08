package com.sample.david.contactslist.presenter;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.sample.david.contactslist.model.Contact;
import com.sample.david.contactslist.model.DatabaseHandler;
import com.sample.david.contactslist.model.HttpRequestHandler;
import com.sample.david.contactslist.view.SplashActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.ContentValues.TAG;

/**
 * Created by david on 08/07/2017.
 */

public class SplashPresenter {

    Activity mViewActivity;
    private static String url = "http://api.androidhive.info/contacts/";
    DatabaseHandler db;

    public SplashPresenter(Activity activity) {
        mViewActivity = activity;
        db = DatabaseHandler.getInstance(mViewActivity);
    }

    public void getContactsToLocal(){
        new GetContacts().execute();
    }

    public boolean doesDatabaseExist(Activity activity) {
        return db.doesDatabaseExist(activity);
    }


    /**
     * Async task class to get json by making HTTP call
     */
    public class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpRequestHandler sh = new HttpRequestHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    // Getting contacts Array
                    JSONArray contacts = jsonObj.getJSONArray("contacts");
                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        Contact contact = new Contact();
                        JSONObject c = contacts.getJSONObject(i);

                        contact.setID(c.getString("id"));
                        contact.setName(c.getString("name"));
//                        String email = c.getString("email");
//                        String address = c.getString("address");
//                        String gender = c.getString("gender");

                        // Phone node is JSON Object
                        JSONObject phone = c.getJSONObject("phone");
                        contact.setPhoneNumber(phone.getString("mobile"));
//                        String home = phone.getString("home");
//                        String office = phone.getString("office");
                        db.addContact(contact);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }

    }

}
