package com.testautomationclient;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.element.AdbShell;

public class Information extends Activity {

	TextView phonemodel, serialno, imei, release, sdk, resolution;
	AdbShell adbShell;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info);
		adbShell = new AdbShell();

		phonemodel = (TextView) Information.this.findViewById(R.id.phonemodel);
		serialno = (TextView) Information.this.findViewById(R.id.serialno);
		imei = (TextView) Information.this.findViewById(R.id.imei);
		release = (TextView) Information.this.findViewById(R.id.release);
		sdk = (TextView) Information.this.findViewById(R.id.sdk);
		resolution = (TextView) Information.this.findViewById(R.id.resolution);

		phonemodel.setText(phonemodel.getText() + android.os.Build.MODEL);
		serialno.setText(serialno.getText() + android.os.Build.SERIAL);
		imei.setText(imei.getText() + adbShell.getIMEI());
		release.setText(release.getText() + android.os.Build.VERSION.INCREMENTAL);
		sdk.setText(sdk.getText() + String.valueOf(android.os.Build.VERSION.SDK_INT));
		int[] sr = adbShell.getScreenResolution();
		resolution.setText(resolution.getText() + String.valueOf(sr[0]) + " x " + String.valueOf(sr[1]));
	}
}
