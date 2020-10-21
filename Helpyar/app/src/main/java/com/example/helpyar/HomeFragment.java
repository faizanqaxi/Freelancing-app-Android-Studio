package com.example.helpyar;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;


public class HomeFragment extends Fragment implements homeAdapter.onLayoutItemClickListener {
    String user_id;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    ArrayList<homeItem> homeItemList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
View v=inflater.inflate(R.layout.fragment_home, container, false);
        HomeActivity activity = (HomeActivity) getActivity();

        user_id = activity.getUser_id();

        /*****************************          NEWSFEED CODE BEGIN             *****************************/
        database db = new database(getContext());
        final SQLiteDatabase sql=db.getWritableDatabase();


        String titleS;
        String AmountS;
        String dateS;
        String statusS;
        String jobId;
        String jobPostedByUserId;
        String jobPostedByUserFName;
        String jobPostedByUserLName;
        String jobPostedByUserName;



        /*select
        job_id, seller_id, user.fname, user.lname,
                title, offered_price, job_assigned, job_status, posted_date
        from job, user
        where not
        seller_id = '8'
        and seller_id = user.user_id;*/


        /*select
        job_id, seller_id, user.fname, user.lname,
                title, offered_price, job_assigned, job_status, posted_date
        from job, user
        where (not
                seller_id = '8')
        and (job_assigned = 'NO')
        and (seller_id = user.user_id)
        order by posted_date desc;*/

        Cursor c2 = sql.rawQuery("\n" +
                "select \n" +
                "job_id, seller_id, user.fname, user.lname, \n" +
                "title, offered_price, job_assigned, job_status, posted_date \n" +
                "from job, user \n" +
                "where (not \n" +
                "seller_id = '" + user_id + "')\n" +
                "and (job_assigned = 'NO')  \n" +
                "and (seller_id = user.user_id)\n" +
                "order by posted_date desc;\n",null);

        if(c2.moveToFirst()) {
            do {

                titleS = c2.getString(c2.getColumnIndex("title"));
                AmountS = c2.getString(c2.getColumnIndex("offered_price"));
                dateS = c2.getString(c2.getColumnIndex("posted_date"));
                statusS = c2.getString(c2.getColumnIndex("job_status"));

                jobId = c2.getString(c2.getColumnIndex("job_id"));
                jobPostedByUserId = c2.getString(c2.getColumnIndex("seller_id"));
                jobPostedByUserFName = c2.getString(c2.getColumnIndex("fname"));
                jobPostedByUserLName = c2.getString(c2.getColumnIndex("lname"));

                jobPostedByUserName = jobPostedByUserFName + " " + jobPostedByUserLName;

                homeItemList.add(new homeItem("" + jobId, " " + titleS,"Rs : "+ AmountS, "Posted on: " + dateS, "Status : " + statusS, " " + jobPostedByUserId, " " + jobPostedByUserName));

            } while (c2.moveToNext());
        }


        mRecyclerView = v.findViewById(R.id.recyclerViewHomeFragment);
        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new homeAdapter(homeItemList, this);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);


        /**************************         NEWSFEED CODE END             **************************/





        return v;
    }

    @Override
    public void onNoteClick(int position) {

        //messageItem currentItem = messageItemList.get(position);

        //mAdapter.getItemId(position);

        //currentItem.getChatUserId();


        String p = Integer.toString(position);


        //Toast.makeText(getContext(),messageItemList.get(position).getmessageId().toString() + " " + messageItemList.get(position).getChatUserId().toString() + " " + user_id, Toast.LENGTH_SHORT).show();


        //Toast.makeText(getContext(), p, Toast.LENGTH_SHORT).show();

        /*final Intent intent = new Intent(getContext(), ChatActivity.class);
        intent.putExtra("user_id", user_id);
        intent.putExtra("chatUserId", messageItemList.get(position).getChatUserId().toString());
        startActivity(intent);*/

//        final Intent intent = new Intent(getContext(), chatMessages.class);
//
//        startActivity(intent);
    }

    @Override
    public void onAcceptClick(int position) {

        //String p = Integer.toString(position);

        //Toast.makeText(getContext(), homeItemList.get(position).getJobId(), Toast.LENGTH_SHORT).show();

        database db = new database(getContext());
        SQLiteDatabase sql = db.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("job_status", "In Progress");
        cv.put("job_assigned", "YES");

        sql.update("Job", cv, "job_id = " + homeItemList.get(position).getJobId(), null);


        ContentValues cv2 = new ContentValues();

        cv2.put("job_id", homeItemList.get(position).getJobId());
        cv2.put("buyer_id", user_id);

        sql.insert("worksOnJob", null, cv2);


        //************************  INSERTING IN THE NOTIFICATION TABLE START   ************************

        /*String time = String.valueOf(android.text.format.DateFormat.format("yyyy-MM-dd HH:mm:ss", new java.util.Date()));

        ContentValues cv3 = new ContentValues();

        String loggedInUserName = "Someone";
        String loggedInUserFName;
        String loggedInUserLName;

        Cursor c3 = sql.rawQuery("select user_id, fname, lname from User where user_id = '" + user_id + "'",null);


        if(c3.moveToFirst()) {
            do {

                loggedInUserFName = c3.getString(c3.getColumnIndex("fname"));
                loggedInUserLName = c3.getString(c3.getColumnIndex("lname"));

                loggedInUserName = loggedInUserFName + " " + loggedInUserLName;

            } while (c3.moveToNext());
        }

        cv3.put("notification_text", "Your job " + homeItemList.get(position).getTitle() + " got accepted by " + loggedInUserName + " ");
        cv3.put("notification_time", time);

        sql.insert("Notification", null, cv3);

        ContentValues cv4 = new ContentValues();

        cv4.put("user_id", homeItemList.get(position).getJobPostedByUserId());

        Cursor c2 = sql.rawQuery("select notification_id from Notification",null);

        String count = "0";

        if(c2.moveToFirst()) {
            do {

                count = c2.getString(c2.getColumnIndex("notification_id"));

            } while (c2.moveToNext());
        }

        cv4.put("notification_id", count);

        sql.insert("hasNotification", null, cv4);*/

        //************************  INSERTING IN THE NOTIFICATION TABLE END ************************

        //************************  INSERTING IN THE NOTIFICATION TABLE START   ************************

        String loggedInUserName = "Someone";
        String loggedInUserFName;
        String loggedInUserLName;

        Cursor c3 = sql.rawQuery("select user_id, fname, lname from User where user_id = '" + user_id + "'",null);


        if(c3.moveToFirst()) {
            do {

                loggedInUserFName = c3.getString(c3.getColumnIndex("fname"));
                loggedInUserLName = c3.getString(c3.getColumnIndex("lname"));

                loggedInUserName = loggedInUserFName + " " + loggedInUserLName;

            } while (c3.moveToNext());
        }
        String notification = "Your job " + homeItemList.get(position).getTitle() + " got accepted by " + loggedInUserName + " ";
        InsertNotification(homeItemList.get(position).getJobPostedByUserId(), notification);

        //************************  INSERTING IN THE NOTIFICATION TABLE END ************************

        //************************  INSERTING IN THE NOTIFICATION TABLE START   ************************

        String notification2 = "You accepted the job " + homeItemList.get(position).getTitle() + " posted by " + homeItemList.get(position).getJobPostedByUserName() + " ";

        InsertNotification(user_id, notification2);

        //************************  INSERTING IN THE NOTIFICATION TABLE END ************************

        Toast.makeText(getContext(), "Job Accepted Succesfully", Toast.LENGTH_SHORT).show();
        Toast.makeText(getContext(), "You will get your payment as soon as the seller confirms his job completion", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getContext(), HomeActivity.class);
        intent.putExtra("user_id",user_id);
        startActivity(intent);

    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HomeActivity activity=(HomeActivity) getActivity();
        user_id=activity.getUser_id();


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.toolbar_navigation,menu);


    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        int id=item.getItemId();
        switch(id){
            case R.id.nav_search:
                Intent intentig = new Intent(getActivity(), searchuser.class);
                startActivity(intentig);
                break;
            case R.id.nav_settings:
                Intent intenti = new Intent(getActivity(), settings.class);
               intenti.putExtra("user_id",user_id);
                startActivity(intenti);
                Toast.makeText(getActivity(),"Settings",Toast.LENGTH_SHORT).show();
                break;
                case R.id.nav_logout:
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    void InsertNotification(String userId, String notification){

        //************************  INSERTING IN THE NOTIFICATION TABLE START   ************************

        database db = new database(getContext());
        SQLiteDatabase sql = db.getWritableDatabase();

        String time = String.valueOf(android.text.format.DateFormat.format("yyyy-MM-dd HH:mm:ss", new java.util.Date()));

        ContentValues cv3 = new ContentValues();

        cv3.put("notification_text", notification);
        cv3.put("notification_time", time);

        sql.insert("Notification", null, cv3);

        ContentValues cv4 = new ContentValues();

        cv4.put("user_id", userId);

        Cursor c2 = sql.rawQuery("select notification_id from Notification",null);

        String count = "0";

        if(c2.moveToFirst()) {
            do {

                count = c2.getString(c2.getColumnIndex("notification_id"));

            } while (c2.moveToNext());
        }

        cv4.put("notification_id", count);

        sql.insert("hasNotification", null, cv4);

        //************************  INSERTING IN THE NOTIFICATION TABLE END ************************


    }
}
