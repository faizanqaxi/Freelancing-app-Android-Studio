/*package com.example.helpyar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class ChatActivity extends AppCompatActivity {

    String user_id;
    String chatUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chat);

        user_id = getIntent().getExtras().getString("user_id") ;

        chatUserId = getIntent().getExtras().getString("chatUserId") ;

        Toast.makeText(ChatActivity.this, user_id + " " + chatUserId, Toast.LENGTH_SHORT).show();


    }

}*/

package com.example.helpyar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    Toolbar tb;

    String user_id;
    String chatUserId;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    EditText sendingMessage;
    Button sendB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chat);

        user_id = getIntent().getExtras().getString("user_id") ;

        chatUserId = getIntent().getExtras().getString("chatUserId") ;

        tb = findViewById(R.id.toolbarchat);
        setSupportActionBar(tb);


        ArrayList<chatItem> chatitemlist = new ArrayList<>();

        //chatitemlist.add(new chatItem("senderName","chatMessage","Date"));



        database db = new database(this);
        final SQLiteDatabase sql = db.getWritableDatabase();

        String senderId;
        String recieverId;
        String senderFName;
        String senderLName;
        String recieverFName;
        String recieverLName;
        String chatMessage;
        String textTime;


        /*select
                message, message_time, sender_id, reciever_id,
        user1.fname as senderFname, user1.lname  as senderLname,
        user2.fname as recieverFname, user2.lname  as recieverLname
        from
        message, chatmessage, user as user1 ,user as user2
        where message.message_id = chatmessage.message_id
        and message.message_id
        in (select chatmessage.message_id from chatmessage
                where (sender_id = '1' or sender_id = '2')
                and (reciever_id = '1' or reciever_id = '2'))
        and chatmessage.sender_id=user1.user_id and chatmessage.reciever_id=user2.user_id
        order by message_time desc;*/


        Cursor c = sql.rawQuery("\n" +
                "        select \n" +
                "        message, message_time, sender_id, reciever_id, \n" +
                "        user1.fname as senderFname, user1.lname  as senderLname, \n" +
                "        user2.fname as recieverFname, user2.lname  as recieverLname\n" +
                "        from \n" +
                "        message, chatmessage, user as user1 ,user as user2 \n" +
                "        where message.message_id = chatmessage.message_id \n" +
                "        and message.message_id\n" +
                "\t\tin (select chatmessage.message_id from chatmessage \n" +
                "        where (sender_id = '" + user_id +"' or sender_id = '" + chatUserId + "') \n" +
                "        and (reciever_id = '" + user_id + "' or reciever_id = '" + chatUserId + "'))\n" +
                "        and chatmessage.sender_id=user1.user_id and chatmessage.reciever_id=user2.user_id\n" +
                "        order by message_time desc;\n",null);


        if(c.moveToFirst()) {
            do {

                senderId = c.getString(c.getColumnIndex("sender_id"));
                recieverId = c.getString(c.getColumnIndex("reciever_id"));

                chatMessage = c.getString(c.getColumnIndex("message"));
                textTime = c.getString(c.getColumnIndex("message_time"));

                recieverFName = c.getString(c.getColumnIndex("recieverFname"));
                recieverLName = c.getString(c.getColumnIndex("recieverLname"));

                if(senderId.equals(user_id))
                {
                    senderFName = "YOU";
                    senderLName = " ";

                    tb.setTitle(recieverFName + " " + recieverLName);

                }
                else{

                    senderFName = c.getString(c.getColumnIndex("senderFname"));
                    senderLName = c.getString(c.getColumnIndex("senderLname"));

                    tb.setTitle(senderFName + " " + senderFName);

                }

                chatitemlist.add(new chatItem(senderFName + " " + senderLName, chatMessage, textTime));

            } while (c.moveToNext());
        }


        sendingMessage = findViewById(R.id.newMsgEtChatActivity);

        sendB = findViewById(R.id.sendNewMsgBtnChatActivity);

        sendB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    String time = String.valueOf(android.text.format.DateFormat.format("yyyy-MM-dd HH:mm:ss", new java.util.Date()));

                    String messageText = sendingMessage.getText().toString();

                    InsertMessage(messageText, time, user_id, chatUserId);


                final Intent intent = new Intent(getApplicationContext(), ChatActivity.class);

                    intent.putExtra("user_id", user_id);
                    intent.putExtra("chatUserId", chatUserId);

                    Toast.makeText(ChatActivity.this, "Message Sent Succesfully !!", Toast.LENGTH_SHORT).show();

                    startActivity(intent);

                }});


        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.putExtra("user_id",user_id);
                startActivity(intent);

                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MessagesFragment()).commit();

            }
        });

        mRecyclerView = findViewById(R.id.recyclerViewChatMessages);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new chatAdapter(chatitemlist);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);


        /*tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.putExtra("user_id",user_id);
                startActivity(intent);
            }
        });*/

    }

    void InsertMessage(String message, String message_time, String sender_id, String reciever_id){

        database db = new database(this);
        final SQLiteDatabase sql = db.getWritableDatabase();


        ContentValues dv = new ContentValues();

        dv.put("message", message);
        dv.put("message_time", message_time);

        sql.insert("Message",null,dv);


        ContentValues dv2 = new ContentValues();

        dv2.put("sender_id", sender_id);
        dv2.put("reciever_id", reciever_id);

        sql.insert("chatMessage",null,dv2);


    }

}

