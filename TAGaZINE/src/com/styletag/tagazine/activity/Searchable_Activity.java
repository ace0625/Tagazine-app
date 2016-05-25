package com.styletag.tagazine.activity;

import android.app.Activity;
import android.content.Context;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class Searchable_Activity extends Activity implements TextWatcher {
	
	AutoCompleteTextView autotv;
	View.OnClickListener bHandler = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch(v.getId())
			{
			case R.id.btn_search_cancel:
				break;
//			case R.id.btn_search_okay:
//				break;
			}
			
		}
	};

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.searchpage);
	    
	    findViewById(R.id.btn_search_cancel).setOnClickListener(bHandler);
//	    findViewById(R.id.btn_search_okay).setOnClickListener(bHandler);
//	    InputMethodManager inputmanager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//	    inputmanager.showSoftInput(autotv, 0);
	    autotv = (AutoCompleteTextView)findViewById(R.id.searchedittext);
	    autotv.addTextChangedListener(this);
	    this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
//	    autotv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, item));
	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		//Change here
		
	}

}
