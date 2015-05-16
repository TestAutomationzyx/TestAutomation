package com.testautomationclient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.brocastreceiver.DumpFileService;
import com.testautomationclient.MyGridLayout.GridAdatper;
import com.testautomationclient.MyGridLayout.OnItemClickListener;
import com.testcase.CaseItems;
import com.testcase.TestCase;


public class MainActivity extends Activity {

	String TAG="MainActivity";
	private String sdcardPath = Environment.getExternalStorageDirectory().getPath();
	private String imagePath = sdcardPath+"/TestAutomation/image";
	private String filePath = sdcardPath+"/TestAutomation/dump";
	private String fileName = "UiAutomator.jar";
	Context context;
	
	// 定义浮动窗口布局
	LinearLayout mFloatLayout;
	// 创建浮动窗口设置布局参数的对象
	WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
	WindowManager mWindowManager;

	ImageView start,upload;
	MyGridLayout grid;
	int[] srcs = { R.drawable.actions_basic, R.drawable.actions_pressure,
			R.drawable.actions_debug, R.drawable.actions_info,
			R.drawable.actions_data, R.drawable.actions_log,
			R.drawable.actions_wifi, R.drawable.actions_open,
			R.drawable.actions_more, };
	//"系统","界面", "适配", "语言", "性能","此处有空",, "此处有空"
	String titles[] = { "基本", "压力", "调试", "关于手机", "数据操作", "开启log工具", "wifi监听", "开启权限","添加更多" };	
	
	CharSequence button[] = { "确定", "取消" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.context = getApplicationContext();
		copy();	
		createImageFile();
		
		grid = (MyGridLayout) findViewById(R.id.list);
		grid.setGridAdapter(new GridAdatper() {

			@Override
			public View getView(int index) {
				View view = getLayoutInflater().inflate(R.layout.actions_item,
						null);
				ImageView iv = (ImageView) view.findViewById(R.id.iv);
				TextView tv = (TextView) view.findViewById(R.id.tv);
				iv.setImageResource(srcs[index]);
				tv.setText(titles[index]);
				return view;
			}

			@Override
			public int getCount() {
				return titles.length;
			}
		});
		grid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(View v, int index) {
				if(titles[index].equals("开启权限")){
					openPermission();
				}else if(titles[index].equals("关于手机")){
					information();
				}else if(titles[index].equals("数据操作")){
					insertData();
				}else if(titles[index].equals("开启log工具")){
					openLog();
				}else if(titles[index].equals("wifi监听")){
					wifiMonitor();
				}else if(titles[index].equals("调试")){
					debugCase();
				}else if(titles[index].equals("添加更多")){
					more();
				}else
					moduleDialog(index);
			}

		});
		
		upload = (ImageView) findViewById(R.id.upload);
		
	}


	public void start(View view) {
		Intent intent = new Intent(MainActivity.this,DumpFileService.class);	
		startService(intent);
	}
	
	public void upload(View view){

		Toast toast = Toast.makeText(context, "上传成功!", Toast.LENGTH_LONG);
		toast.show();
	}

	private void copy() {
		InputStream inputStream;
		try {
			inputStream = getResources().getAssets().open(fileName);// assets文件夹下的文件
			File file = new File(filePath);
			if (!file.exists()) {
				file.mkdirs();
			}
			FileOutputStream fileOutputStream = new FileOutputStream(filePath + "/" + fileName);// 保存到本地的文件夹下的文件
			byte[] buffer = new byte[1024];
			int count = 0;
			while ((count = inputStream.read(buffer)) > 0) {
				fileOutputStream.write(buffer, 0, count);
			}
			fileOutputStream.flush();
			fileOutputStream.close();
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void createImageFile(){
		File file = new File(imagePath);
		if (!file.exists()) {
			file.mkdirs();
		}
	}
	
	protected void moduleDialog(final int index){
		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
		builder.setIcon(srcs[index]);
		builder.setTitle(titles[index]);
		final CharSequence[] items = new CaseItems().getModule(titles[index]);
		final List<String> modules = new ArrayList<String>();
		final boolean flags[] = new boolean[items.length];
		builder.setMultiChoiceItems(items, null, new OnMultiChoiceClickListener() {
			@Override
			public void onClick(DialogInterface dialog,
					int which, boolean ischecked) {
				
				flags[which]=ischecked;
				modules.clear();
				for(int i=0; i<flags.length;i++)
					if(flags[i])
						modules.add(items[i].toString());
				}
			});
		builder.setPositiveButton(button[0], new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				for(String m:modules)
					Log.e(TAG, m);
				new TestCase(context).startCase(titles[index],modules);
				
			}
		});
		builder.setNegativeButton(button[1], new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
		});
		AlertDialog dialog = builder.create();
		Window window = dialog.getWindow();
		WindowManager.LayoutParams lp = window.getAttributes();
		// 设置透明度
		lp.alpha = 0.9f;
		window.setAttributes(lp);
		dialog.show();
	}
	
	protected void openPermission() {
		Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
		Uri uri = Uri.fromParts("package", "com.testautomationclient", null);
		intent.setData(uri);
		startActivity(intent);		
	}
	
	protected void information() {
		Intent intent = new Intent(MainActivity.this, LoadingActivity.class);//Information
		intent.putExtra("type", 0);
		startActivity(intent);		
	}
	
	protected void insertData() {
		Intent intent = new Intent(MainActivity.this, DataOperator.class);
		startActivity(intent);		
	}
	
	protected void openLog() {
		new OpenLog(context);
	}
	
	protected void wifiMonitor() {
		Intent intent = new Intent(MainActivity.this, WifiMonitor.class);
		startActivity(intent);
	}
	
	protected void debugCase() {
		Intent intent = new Intent(MainActivity.this, DebugCase.class);
		startActivity(intent);
	}
	
	protected void more() {
		Intent intent = new Intent(MainActivity.this, LoadingActivity.class);//More
		intent.putExtra("type", 1);
		startActivity(intent);
	}
}
