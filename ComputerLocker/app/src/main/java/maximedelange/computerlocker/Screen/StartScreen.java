package maximedelange.computerlocker.Screen;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import maximedelange.computerlocker.Domain.ClientLocker;
import maximedelange.computerlocker.R;

public class StartScreen extends AppCompatActivity {

    // Fields
    private Context context = this;
    private ImageButton lock = null;
    private ImageButton unlock = null;
    private ClientLocker client = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Additions
        client = new ClientLocker();
        lockComputer();
        unlockComputer();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start_screen, menu);
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

    public void lockComputer(){
        lock = (ImageButton) findViewById(R.id.btnLock);
        lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                client.getPermission();
                client.makeConnection(1);
            }
        });
    }

    public void unlockComputer(){
        unlock = (ImageButton) findViewById(R.id.btnUnlock);
        unlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                client.getPermission();
                client.makeConnection(2);
            }
        });
    }

}
