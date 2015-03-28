package com.tabhost;

import com.testautomationclient.R;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ContactFragment extends Fragment {

	Context context;
	EditText ctname,ctphone,ctnumber;
	Button ctsure,ctlist,ctdelete;
	Toast toast;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.context = getActivity();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.contact, null);
		
		ctname = (EditText) view.findViewById(R.id.ctnameet);
		ctphone = (EditText) view.findViewById(R.id.ctphoneet);
		ctnumber = (EditText) view.findViewById(R.id.ctnumberet);
		
		ctsure = (Button) view.findViewById(R.id.ctsure);
		ctlist = (Button) view.findViewById(R.id.ctlist);
		ctdelete = (Button) view.findViewById(R.id.ctdelete);
		
		ctsure.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String given_name = ctname.getText().toString();
				int nums = Integer.parseInt(ctnumber.getText().toString());
				String number = ctphone.getText().toString();
				long startTimes = System.currentTimeMillis();
				insertContacts(context, given_name, number, true,nums);
				toast = Toast.makeText(context, "ºÄÊ±"+String.valueOf((System.currentTimeMillis()-startTimes)/1000)+"Ãë", Toast.LENGTH_SHORT);
				toast.show();
			}
		});
		
		ctlist.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String given_name = ctname.getText().toString();
				int nums = Integer.parseInt(ctnumber.getText().toString());
				String number = ctphone.getText().toString();
				long startTimes = System.currentTimeMillis();
				insertContacts(context, given_name, number, false,nums);
				toast = Toast.makeText(context, "ºÄÊ±"+String.valueOf((System.currentTimeMillis()-startTimes)/1000)+"Ãë", Toast.LENGTH_SHORT);
				toast.show();				
			}
		});
		
		ctdelete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				long startTimes = System.currentTimeMillis();
				deleteContacts(context);
				toast = Toast.makeText(context, "ºÄÊ±"+String.valueOf((System.currentTimeMillis()-startTimes)/1000)+"Ãë", Toast.LENGTH_SHORT);
				toast.show();				
			}
		});
		
		return view;
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	private void insertContacts(Context context,String given_name, String number,boolean isOne,int nums) 
	{		
	    if(isOne){ 
	    	ContentValues values = new ContentValues();        
		    Uri rawContactUri = context.getContentResolver().insert(RawContacts.CONTENT_URI, values); 
	    	long rawContactId = ContentUris.parseId(rawContactUri);
	    	values.clear(); 
		    values.put(Data.RAW_CONTACT_ID, rawContactId); 
		    values.put(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE); 
		    values.put(StructuredName.GIVEN_NAME, given_name); 
		    context.getContentResolver().insert(Data.CONTENT_URI, values);
		    
		    for(int i=0; i<nums;i++){
		    	values.clear(); 
			    values.put(Data.RAW_CONTACT_ID, rawContactId); 
			    values.put(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE); 
			    values.put(Phone.NUMBER, number+i); 
			    values.put(Phone.TYPE,Phone.TYPE_MOBILE); 
			    context.getContentResolver().insert(Data.CONTENT_URI, values);
		    }
	    }else{
	    	for(int i=0; i<nums; i++){
	    		ContentValues values = new ContentValues();        
	    	    Uri rawContactUri = context.getContentResolver().insert(RawContacts.CONTENT_URI, values); 
	    		long rawContactId = ContentUris.parseId(rawContactUri);
	    		values.clear(); 
	    	    values.put(Data.RAW_CONTACT_ID, rawContactId); 
	    	    values.put(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE); 
	    	    values.put(StructuredName.GIVEN_NAME, given_name+i); 
	    	    context.getContentResolver().insert(Data.CONTENT_URI, values);
	    	        
	    	    values.clear(); 
	    	    values.put(Data.RAW_CONTACT_ID, rawContactId); 
	    	    values.put(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE); 
	    	    values.put(Phone.NUMBER, number); 
	    	    values.put(Phone.TYPE,Phone.TYPE_MOBILE); 
	    	    context.getContentResolver().insert(Data.CONTENT_URI, values);
	    	}
	    }
	}
	
	private void deleteContacts(Context context){
		context.getContentResolver().delete(RawContacts.CONTENT_URI, null, null);
	}
}
