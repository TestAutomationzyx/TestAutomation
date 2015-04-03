package com.testautomationclient;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.floatingreceiver.WifiFloatingReceiver;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class WifiMonitor extends Activity {

	WifiManager wifiManager;
	/*
	 * WIFI_STATE_DISABLED��WIFI����������
	 * 
	 * WIFI_STATE_DISABLING��WIFI���ڹر�
	 * 
	 * WIFI_STATE_ENABLED��WIFI��������
	 * 
	 * WIFI_STATE_ENABLING��WIFI�������ڴ�
	 * 
	 * WIFI_STATE_UNKNOWN��δ֪����״̬
	 */

	// ɨ�����б�
	private List<ScanResult> list;
	private List<CharSequence> SSIDlist;
	private Spinner SSIDSpinner;
	private Context context;
	private String SSID, password;
	private EditText passwordet;
	private Button wifisure;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wifi);
		this.context = this.getApplicationContext();
		getAllNetWorkList();

		SSIDSpinner = (Spinner) findViewById(R.id.SSIDspinner);
		passwordet = (EditText) findViewById(R.id.SSIDpassword);
		wifisure = (Button) findViewById(R.id.wifisure);

		SSIDSpinner.setPrompt("ѡ�����wifi:");
		SpinnerAdapter mAdapter = new SpinnerAdapter(context,
				android.R.layout.simple_spinner_item, SSIDlist);
		SSIDSpinner.setAdapter(mAdapter);

		wifisure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				SSID = (String) SSIDlist.get(SSIDSpinner
						.getSelectedItemPosition());
				password = passwordet.getText().toString();
				Intent intent = new Intent(WifiMonitor.this, WifiFloatingReceiver.class);
				intent.putExtra("SSID", SSID);
				intent.putExtra("password", password);
				stopService(intent);
				startService(intent);
				finish();
			}
		});
	}

	private class SpinnerAdapter extends ArrayAdapter<CharSequence> {

		Context context;
		List<CharSequence> mList = new ArrayList<CharSequence>();

		public SpinnerAdapter(Context context, int resource,
				List<CharSequence> objects) {
			super(context, resource, objects);
			this.context = context;
			this.mList = objects;
		}

		@Override
		public View getDropDownView(int position, View convertView,
				ViewGroup parent) {

			if (convertView == null) {

				LayoutInflater inflater = LayoutInflater.from(context);
				convertView = inflater.inflate(
						android.R.layout.simple_spinner_item, parent, false);
			}

			TextView tv = (TextView) convertView
					.findViewById(android.R.id.text1);
			tv.setText(mList.get(position));
			tv.setGravity(Gravity.LEFT);
			tv.setTextColor(Color.rgb(255, 106, 106));
			tv.setTextSize(25);
			return convertView;
		}
	}

	private void getAllNetWorkList() {
		// ��ʼɨ������
		wifiManager = (WifiManager) context.getApplicationContext()
				.getSystemService(Context.WIFI_SERVICE);
		if (!wifiManager.isWifiEnabled())
			wifiManager.setWifiEnabled(true);
		list = wifiManager.getScanResults();
		SSIDlist = new ArrayList<CharSequence>();
		HashSet<CharSequence> SSIDset = new HashSet<CharSequence>();
		for (ScanResult scanresult : list) {
			SSIDset.add(scanresult.SSID);
		}
		for (CharSequence c : SSIDset) {
			if (c != "")
				SSIDlist.add(c);
		}
	}

	// ���弸�ּ��ܷ�ʽ��һ����WEP��һ����WPA������û����������
	public enum WifiCipherType {
		WIFICIPHER_WEP, WIFICIPHER_WPA, WIFICIPHER_NOPASS, WIFICIPHER_INVALID
	}
}
