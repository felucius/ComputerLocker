package maximedelange.computerlocker.Screen;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import maximedelange.computerlocker.Domain.ClientLocker;
import maximedelange.computerlocker.R;

public class StartScreen extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // Fields
    private ImageButton lock = null;
    private ImageButton unlock = null;
    private ImageButton searchIPAddress = null;
    private ClientLocker client = null;
    private ActionBar actionBar = null;
    private Button addIP = null;
    private EditText inputIPAddress = null;
    private Spinner spinnerIPAddresses = null;
    private ArrayList<String> ipAddressesList = null;
    private Integer connection = 0;
    private String selectedIPAddress = null;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Additions
        hideActionBar();
        client = new ClientLocker();
        lockComputer();
        unlockComputer();
        addIPAddress();
        createSpinnerAdapter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start_screen, menu);
        return true;
    }

    /*
    Locking the computer.
     */
    public void lockComputer(){
        lock = (ImageButton) findViewById(R.id.btnLock);
        lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                client.getPermission();
                connection = client.makeConnection(1, selectedIPAddress);
                if(connection == 0){
                    Toast.makeText(context, "No connection available, Choose an IP address or add one.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /*
    Unlocking the computer.
     */
    public void unlockComputer(){
        unlock = (ImageButton) findViewById(R.id.btnUnlock);
        unlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                client.getPermission();
                connection = client.makeConnection(2, selectedIPAddress);
                if(connection == 0){
                    Toast.makeText(context, "No connection available, Choose an IP address or add one.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /*
    Hide the actionbar on top of the screen.
     */
    public void hideActionBar(){
        actionBar = getSupportActionBar();
        actionBar.hide();
    }

    /*
    Adding an IP Address to the ArrayList.
     */
    public void addIPAddress(){
        addIP = (Button) findViewById(R.id.btnAddIP);
        addIP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputIPAddress = (EditText) findViewById(R.id.txtIPAddress);
                if(!inputIPAddress.getText().equals("")){
                    ipAddressesList.add(inputIPAddress.getText().toString());
                }else{
                    System.out.println("Fill in the field.");
                }
            }
        });
    }

    /*
    Creates a spinner with IP adresses into it.
     */
    public void createSpinnerAdapter(){
        ipAddressesList = new ArrayList<>();
        ipAddressesList.add("Select an IP address to connect with.");
        ipAddressesList.add("192.168.2.11");
        spinnerIPAddresses = (Spinner) findViewById(R.id.spinnerIPStorage);
        spinnerIPAddresses.setOnItemSelectedListener(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, ipAddressesList);
        spinnerIPAddresses.setAdapter(adapter);
    }

    /*
    Get the selected item of the spinner list.
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
        parent.getItemAtPosition(position);
        selectedIPAddress = (String)parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Do nothing
    }
}
