package edu.cmu.eps.scams;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Date;

import edu.cmu.eps.scams.logic.ApplicationLogicFactory;
import edu.cmu.eps.scams.logic.ApplicationLogicResult;
import edu.cmu.eps.scams.logic.ApplicationLogicTask;
import edu.cmu.eps.scams.logic.IApplicationLogic;
import edu.cmu.eps.scams.logic.IApplicationLogicCommand;
import edu.cmu.eps.scams.logic.model.AppSettings;
import edu.cmu.eps.scams.logic.model.Telemetry;
import edu.cmu.eps.scams.utilities.TimestampUtility;


public class FirstTimeLogin extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "FirstTimeLogin";
    private IApplicationLogic logic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_time_login);
        this.logic = ApplicationLogicFactory.build(this);

        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button2);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String userType = "";
        switch (view.getId()) {
            case R.id.button1:
                userType = "Reviewer";
                break;
            case R.id.button2:
                userType = "Primary User";
                break;
            case R.id.button3:
                userType = "DELETE";
                break;
        }
        final String userAction = userType;
        ApplicationLogicTask task = new ApplicationLogicTask(
                this.logic,
                progress -> {
                },
                result -> {
                    Intent intent = new Intent(FirstTimeLogin.this, MainActivity.class);
                    startActivity(intent);
                }
        );
        task.execute((IApplicationLogicCommand) logic -> {
            AppSettings settings = logic.getAppSettings();
            Log.d(TAG, String.format("Retrieved settings: %s", settings.toString()));
            if (userAction.equals("DELETE")) {
                Log.d(TAG, "Updating settings with removing user");
                logic.updateAppSettings(new AppSettings(
                        settings.getIdentifier(),
                        false,
                        settings.getSecret(),
                        String.format("{ \"userType\": \"%s\" }", userAction),
                        settings.getRecovery()
                ));
            } else {
                Log.d(TAG, "Updating settings with new user");
                Telemetry installTelemetry = new Telemetry("system.install", TimestampUtility.now());
                installTelemetry.getProperties().put("userType", userAction);
                logic.sendTelemetry(installTelemetry);
                logic.updateAppSettings(new AppSettings(
                        settings.getIdentifier(),
                        true,
                        settings.getSecret(),
                        String.format("{ \"userType\": \"%s\" }", userAction),
                        settings.getRecovery()
                ));
            }
            return new ApplicationLogicResult(logic.getAppSettings());
        });

    }

}
