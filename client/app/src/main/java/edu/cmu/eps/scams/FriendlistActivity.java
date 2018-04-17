package edu.cmu.eps.scams;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import edu.cmu.eps.scams.logic.ApplicationLogicFactory;
import edu.cmu.eps.scams.logic.ApplicationLogicResult;
import edu.cmu.eps.scams.logic.ApplicationLogicTask;
import edu.cmu.eps.scams.logic.IApplicationLogicCommand;
import edu.cmu.eps.scams.logic.model.Association;
import edu.cmu.eps.scams.logic.IApplicationLogic;


public class FriendlistActivity extends AppCompatActivity {

    private static final String TAG = "FriendlistActivity";
    private IApplicationLogic logic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendlist);
        ListView listView = (ListView) findViewById(R.id.friend_list_view);

        this.logic = ApplicationLogicFactory.build(this);
        // Task to create a list whenever the Connection page is shown
        // Retrieve updated contact(friendslist) information from the local database
        ApplicationLogicTask task = new ApplicationLogicTask(
                this.logic,
                progress -> {
                },
                result -> {
                    List<Association> associations = result.getAssociations();
                    MyArrayAdapter adapter = new MyArrayAdapter(this,
                            android.R.layout.simple_list_item_2, associations);

                    listView.setAdapter(adapter);
                }
        );
       // runs the task's code on a background thread
        task.execute((IApplicationLogicCommand) logic -> new ApplicationLogicResult(logic.getHistory()));
    }


    // Customize the list display
    private class MyArrayAdapter extends ArrayAdapter<Association> {

        private List<Association> objects;
        private Context context;

        public MyArrayAdapter(Context context, int textViewResourceId, List<Association> objects) {
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

            text1.setText(objects.get(position).getName());
            text2.setText(objects.get(position).getIdentifier());

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

