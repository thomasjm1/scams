package edu.cmu.eps.scams;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.zxing.Result;

import org.json.JSONObject;

import java.util.List;

import edu.cmu.eps.scams.logic.ApplicationLogicFactory;
import edu.cmu.eps.scams.logic.ApplicationLogicResult;
import edu.cmu.eps.scams.logic.ApplicationLogicTask;
import edu.cmu.eps.scams.logic.IApplicationLogic;
import edu.cmu.eps.scams.logic.IApplicationLogicCommand;
import edu.cmu.eps.scams.logic.model.History;
import edu.cmu.eps.scams.notifications.NotificationFacade;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;
    private IApplicationLogic logic;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        this.logic = ApplicationLogicFactory.build(this);
        // Programmatically initialize the scanner view
        mScannerView = new ZXingScannerView(this);
        // Set the scanner view as the content view
        setContentView(mScannerView);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Register ourselves as a handler for scan results.
        mScannerView.setResultHandler(this);
        // Start camera on resume
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        // Stop camera on pause
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here

        // Prints scan results & scan format (qrcode, pdf417 etc.)
        String TAG1 = "";
        String TAG2 = "";
        Log.v(TAG1, rawResult.getText());
        Log.v(TAG2, rawResult.getBarcodeFormat().toString());
        System.out.println(rawResult.getText());
        System.out.println(rawResult.getBarcodeFormat().toString());

        ApplicationLogicTask task = new ApplicationLogicTask(
                this.logic,
                progress -> {
                },
                result -> {
                    NotificationFacade facade = new NotificationFacade(this);
                    facade.create(this, "Added new friend", "");
                    finish();
                }
        );
        task.execute((IApplicationLogicCommand) logic -> {
            String rawText = rawResult.getText();
            JSONObject json = new JSONObject(rawText);
            String name = json.getString("name");
            String identifier = json.getString("identifier");
            return new ApplicationLogicResult(logic.createAssociation(
                    name,
                    identifier
            )
            );
        }

        );

        //If you would like to resume scanning, call this method below:
        //mScannerView.resumeCameraPreview(this);

        // Add friend based on the qrCode, scanning function here
        //Intent intent = new Intent(this, ConnectActivity.class);
        //intent.putExtra(AppConstants.KEY_QR_CODE, rawResult.getText());
        //setResult(RESULT_OK, intent);

    }
}
