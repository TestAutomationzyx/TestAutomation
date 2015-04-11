package com.testautomationclient;

import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.testcase.CaseItem;
import com.testcase.CaseItems;

public class DebugCase extends Activity {

	String TAG = "DebugCase";
	Context context;
	TextView typeTextView, moduleTextView, stepTextView;
	Button sureButton;
	boolean[] flags;
	CaseItems caseItems = new CaseItems();
	List<CaseItem> CASE_ITEMS=caseItems.getCaseItems();
	CharSequence[] mType,mModule,mStep;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.testacse_list);
		this.context = getApplicationContext();

		typeTextView = (TextView) findViewById(R.id.txt_showtype);
		moduleTextView = (TextView) findViewById(R.id.txt_showmodule);
		stepTextView = (TextView) findViewById(R.id.txt_showstep);
		sureButton = (Button) findViewById(R.id.dbtc_sure);
		
		typeTextView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				newDialog("tDIALOG").show();
				
			}
		});
		moduleTextView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				newDialog("mDIALOG").show();
				
			}
		});
		stepTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				newDialog("sDIALOG").show();

			}
		});
		stepTextView.setMovementMethod(ScrollingMovementMethod.getInstance());
		sureButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String type = typeTextView.getText().toString();
				String module = moduleTextView.getText().toString();
				String[] steps = stepTextView.getText().toString().split("��");
				Log.e(TAG, type);
				Log.e(TAG, module);
				Log.e(TAG, steps[0]);
				for(int i=0;i<steps.length;i++){
					CaseItem caseItem = new CaseItem(type, module, steps[i]);
					caseItems.startCase(context, caseItem);
				}								
			}
		});
	}

	protected Dialog newDialog(String id) {
		Dialog dialog = null;
		android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(
				this);
		// ���öԻ����ͼ��
		builder.setIcon(R.drawable.actions_account);
		switch (id) {
		case "tDIALOG":
			// ���öԻ���ı���
			builder.setTitle("ѡ�������ͣ�");
			mType = caseItems.getTypes();
			builder.setSingleChoiceItems(mType, -1, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					typeTextView.setText(mType[which]);	
				}
			});
			break;
		case "mDIALOG":
			// ���öԻ���ı���
			builder.setTitle( "ѡ����ѡ���Ͷ�Ӧ��ģ�飺");
			mModule = caseItems.getModule(typeTextView.getText().toString());
			builder.setSingleChoiceItems(mModule, -1, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					moduleTextView.setText(mModule[which]);					
				}
			});
			break;
		case "sDIALOG":
			// ���öԻ���ı���
			builder.setTitle( "ѡ��ִ�в��裺");
			mStep = caseItems.getStep(typeTextView.getText().toString(), moduleTextView.getText().toString());
			flags = new boolean[mStep.length];
			builder.setMultiChoiceItems(mStep, flags,
					new DialogInterface.OnMultiChoiceClickListener() {
						public void onClick(DialogInterface dialog, int which,
								boolean isChecked) {
							flags[which] = isChecked;
							String result = "";
							for (int i = 0; i < flags.length; i++) {
								if (flags[i]) {
									result = result + mStep[i] + "��";
								}
							}
							if (result.equals(""))
								result = "����δѡ����Բ��裡";
							stepTextView.setText(result);
						}
					});
			break;
		}
		// ���һ��ȷ����ť
		builder.setPositiveButton("ȷ ��",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {

					}
				});
		// ����һ����ѡ��Ի���
		dialog = builder.create();
		return dialog;
	}

}
