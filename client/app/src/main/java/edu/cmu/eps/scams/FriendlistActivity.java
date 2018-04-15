package edu.cmu.eps.scams;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TwoLineListItem;
import android.view.LayoutInflater;

import java.util.List;

import edu.cmu.eps.scams.logic.model.Friend;
import edu.cmu.eps.scams.logic.HistoryRecords;
import edu.cmu.eps.scams.logic.IApplicationLogic;
import edu.cmu.eps.scams.notifications.NotificationFacade;


public class FriendlistActivity extends AppCompatActivity {

    private static final String TAG = "FriendlistActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendlist);


        IApplicationLogic InterfaceAction = new HistoryRecords();

        ListView listView = (ListView) findViewById(R.id.friend_list_view);

        // **************************************************
        // Get history information from local storage?
        List<Friend> friendList = InterfaceAction.getFriendList();

        //System.out.println(friendList);

        //History example = new History();
        // System.out.println(example.PhoneNumber);
        // List phone number in the first line, the time on the second line


        MyArrayAdapter adapter = new MyArrayAdapter(this,
                android.R.layout.simple_list_item_2, friendList);

         listView.setAdapter(adapter);


    }



    private class MyArrayAdapter extends ArrayAdapter<Friend> {

        private List<Friend> objects;
        private Context context;

        public MyArrayAdapter(Context context, int textViewResourceId, List<Friend> objects) {
            super(context, textViewResourceId, objects);
            this.objects = objects;
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TwoLineListItem twoLineListItem;

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                twoLineListItem = (TwoLineListItem) inflater.inflate(
                        android.R.layout.simple_list_item_2, null);
            } else {
                twoLineListItem = (TwoLineListItem) convertView;
            }

            TextView text1 = twoLineListItem.getText1();
            TextView text2 = twoLineListItem.getText2();

            text1.setText(objects.get(position).getPhoneNumber());
            text2.setText(objects.get(position).getFriendQr());

            return twoLineListItem;
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_history, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

