package com.floatingreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiConfiguration.AuthAlgorithm;
import android.net.wifi.WifiConfiguration.KeyMgmt;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;

import com.testautomationclient.R;

public class WifiFloatingReceiver extends FloatingService {

	WifiManager wifiManager;
	Context context;
	WifiStateReceiver wifiStateReceiver;
	String TAG="WifiFloatingReceiver",SSID, password;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		super.setTextViewUnvisual();
		super.setIconSrc(R.drawable.wifi);
		this.context = getApplicationContext();
		this.wifiManager = (WifiManager) context.getApplicationContext()
				.getSystemService(Context.WIFI_SERVICE);

		// ע���������
		wifiStateReceiver = new WifiStateReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
		filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
		registerReceiver(wifiStateReceiver, filter);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		SSID = intent.getStringExtra("SSID");
		password = intent.getStringExtra("password");
		return START_STICKY;
	}

	@Override
	public void clickIcon() {
		// TODO Auto-generated method stub
		super.clickIcon();
		unregisterReceiver(wifiStateReceiver);
		stopSelf();
	}

	// ���弸�ּ��ܷ�ʽ��һ����WEP��һ����WPA������û����������
	public enum WifiCipherType {
		WIFICIPHER_WEP, WIFICIPHER_WPA, WIFICIPHER_NOPASS, WIFICIPHER_INVALID
	}

	// �ṩһ���ⲿ�ӿڣ�����Ҫ���ӵ�������
	public void connect(String ssid, String password, WifiCipherType type) {
		if (wifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLING
				||wifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLED) {
			try {
				Thread thread = new Thread(new ConnectRunnable(ssid, password,type));
				thread.start();
				Thread.currentThread();
				Thread.sleep(100);
			} catch (InterruptedException ie) {
			}
		}
	}

	private WifiConfiguration createWifiInfo(String SSID, String Password,
			WifiCipherType Type) {
		WifiConfiguration config = new WifiConfiguration();
		config.allowedAuthAlgorithms.clear();
		config.allowedGroupCiphers.clear();
		config.allowedKeyManagement.clear();
		config.allowedPairwiseCiphers.clear();
		config.allowedProtocols.clear();
		config.SSID = "\"" + SSID + "\"";
		// nopass
		if (Type == WifiCipherType.WIFICIPHER_NOPASS) {
			config.wepKeys[0] = "";
			config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
			config.wepTxKeyIndex = 0;
		}
		// wep
		if (Type == WifiCipherType.WIFICIPHER_WEP) {
			if (!TextUtils.isEmpty(Password)) {
				if (isHexWepKey(Password)) {
					config.wepKeys[0] = Password;
				} else {
					config.wepKeys[0] = "\"" + Password + "\"";
				}
			}
			config.allowedAuthAlgorithms.set(AuthAlgorithm.OPEN);
			config.allowedAuthAlgorithms.set(AuthAlgorithm.SHARED);
			config.allowedKeyManagement.set(KeyMgmt.NONE);
			config.wepTxKeyIndex = 0;
		}
		// wpa
		if (Type == WifiCipherType.WIFICIPHER_WPA) {
			config.preSharedKey = "\"" + Password + "\"";
			config.hiddenSSID = true;
			config.allowedAuthAlgorithms
					.set(WifiConfiguration.AuthAlgorithm.OPEN);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
			config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
			config.allowedPairwiseCiphers
					.set(WifiConfiguration.PairwiseCipher.TKIP);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
			config.allowedPairwiseCiphers
					.set(WifiConfiguration.PairwiseCipher.CCMP);
			config.status = WifiConfiguration.Status.ENABLED;
		}
		return config;
	}

	// ��wifi����
	private boolean openWifi() {
		boolean bRet = true;
		if (!wifiManager.isWifiEnabled()) {
			bRet = wifiManager.setWifiEnabled(true);
		}
		return bRet;
	}

	class ConnectRunnable implements Runnable {
		private String ssid;

		private String password;

		private WifiCipherType type;

		public ConnectRunnable(String ssid, String password, WifiCipherType type) {
			this.ssid = ssid;
			this.password = password;
			this.type = type;
		}

		@Override
		public void run() {
			// ��wifi
			openWifi();
			// ״̬���WIFI_STATE_ENABLED��ʱ�����ִ����������
			while (wifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLING) {
				try {
					// Ϊ�˱������һֱwhileѭ��������˯��100�����⡭��
					Thread.sleep(100);
				} catch (InterruptedException ie) {
				}
			}

			WifiConfiguration wifiConfig = createWifiInfo(ssid, password, type);
			//
			if (wifiConfig == null) {
				return;
			}
			int netID = wifiManager.addNetwork(wifiConfig);
			wifiManager.enableNetwork(netID, true);
			wifiManager.reconnect();

		}
	}

	private static boolean isHexWepKey(String wepKey) {
		final int len = wepKey.length();

		// WEP-40, WEP-104, and some vendors using 256-bit WEP (WEP-232?)
		if (len != 10 && len != 26 && len != 58) {
			return false;
		}

		return isHex(wepKey);
	}

	private static boolean isHex(String key) {
		for (int i = key.length() - 1; i >= 0; i--) {
			final char c = key.charAt(i);
			if (!(c >= '0' && c <= '9' || c >= 'A' && c <= 'F' || c >= 'a'
					&& c <= 'f')) {
				return false;
			}
		}

		return true;
	}

	class WifiStateReceiver extends BroadcastReceiver {

		// 0 --> WIFI_STATE_DISABLING
		// 1 --> WIFI_STATE_DISABLED
		// 2 --> WIFI_STATE_ENABLING
		// 3 --> WIFI_STATE_ENABLED
		// 4 --> WIFI_STATE_UNKNOWN

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			Bundle bundle = intent.getExtras();
			int oldInt = bundle.getInt("previous_wifi_state");
			int newInt = bundle.getInt("wifi_state");
			Log.i(TAG, oldInt+"");
			Log.i(TAG, newInt+"");
			
			if (newInt == WifiManager.WIFI_STATE_ENABLING
					|| newInt == WifiManager.WIFI_STATE_ENABLED) {
				connect(SSID, password, WifiCipherType.WIFICIPHER_WPA);
			} else if (newInt == WifiManager.WIFI_STATE_DISABLING
					|| newInt == WifiManager.WIFI_STATE_DISABLED) {
				connect(SSID, password, WifiCipherType.WIFICIPHER_WPA);
			} else {
				
			}

			WifiInfo info = wifiManager.getConnectionInfo();
			Log.i(TAG, ">>>onReceive.wifiInfo=" + info.toString());
			Log.i(TAG, ">>>onReceive.SSID=" + info.getSSID());
	
			if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(intent.getAction())) {
				Parcelable parcelableExtra = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
				if (null != parcelableExtra) {
					NetworkInfo networkInfo = (NetworkInfo) parcelableExtra;
					switch (networkInfo.getState()) {
					case CONNECTED:
						Log.i(TAG,"APActivity-->CONNECTED");
						break;
					case CONNECTING:
						Log.i(TAG,"APActivity-->CONNECTING");
						break;
					case DISCONNECTED:
						Log.i(TAG,"APActivity-->DISCONNECTED");
						break;
					case DISCONNECTING:
						Log.i(TAG,"APActivity-->DISCONNECTING");
						break;
					case SUSPENDED:
						Log.i(TAG,"APActivity-->SUSPENDED");
						break;
					case UNKNOWN:
						Log.i(TAG,"APActivity-->UNKNOWN");
						break;
					default:
						break;
					}
				}
			}
		}
	}

}
