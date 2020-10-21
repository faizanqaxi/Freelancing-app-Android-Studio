/*
package com.example.helpyar;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


*/
/**
 * A simple {@link Fragment} subclass.
 *//*

public class NotificationFragment extends Fragment {


    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification, container, false);
    }

}
*/

/*package com.example.helpyar;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MessagesFragment extends Fragment {


    public MessagesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_messages, container, false);
    }

}*/

package com.example.helpyar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class NotificationFragment extends Fragment implements notificationAdapter.onLayoutItemClickListener {


    String user_id;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    ArrayList<notificationItem> notificationItemList = new ArrayList<>();

    public NotificationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_notification, container, false);

        HomeActivity activity = (HomeActivity) getActivity();

        user_id = activity.getUser_id();


        //notificationItemList.add(new notificationItem("Id","Notification Text","Notification Date", "Notification By Id", "Notification By Name"));


        //FETCHING DATA FROM DATABASE AND ADDING IT TO THE LIST TO BE DISPLAYED IN RECYCLER VIEW

        database db = new database(getContext());
        final SQLiteDatabase sql = db.getWritableDatabase();

        String notificationUserId;
        String notificationText;

        String notificationUserFName;
        String notificationUserLName;

        String notificationUserText;
        String notificationTime;
        String notificationId;


        /*select
        notification.notification_id, notification_text, notification_time
        from notification, hasNotification
        where notification.notification_id = hasNotification.notification_id
        and hasNotification.user_id = '1'*/

        Cursor c = sql.rawQuery("\n" +
                "select \n" +
                "notification.notification_id, notification_text, notification_time \n" +
                "from notification, hasNotification \n" +
                "where notification.notification_id = hasNotification.notification_id \n" +
                "and hasNotification.user_id = '" + user_id + "' order by notification_time desc \n", null);




        if(c.moveToFirst()) {
            do {

                notificationId = c.getString(c.getColumnIndex("notification_id"));
                notificationText = c.getString(c.getColumnIndex("notification_text"));
                notificationTime = c.getString(c.getColumnIndex("notification_time"));

                notificationItemList.add(new notificationItem(notificationId, notificationText, notificationTime, " ", " "));


            } while (c.moveToNext());
        }


        //DATE TRANSFER FROM DATABASE TO LIST COMPLETED



        mRecyclerView = v.findViewById(R.id.recyclerViewNotification);

        mLayoutManager = new LinearLayoutManager(getContext());

        mAdapter = new notificationAdapter(notificationItemList,this);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);


        return  v;
    }

    @Override
    public void onNoteClick(int position) {

        String p = Integer.toString(position);

        Toast.makeText(getContext(), p, Toast.LENGTH_SHORT).show();

        /*final Intent intent = new Intent(getContext(), ChatActivity.class);
        intent.putExtra("user_id", user_id);
        intent.putExtra("chatUserId", messageItemList.get(position).getChatUserId().toString());
        startActivity(intent);*/

//        final Intent intent = new Intent(getContext(), chatMessages.class);
//
//        startActivity(intent);
    }
}

