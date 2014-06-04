package com.example.tipcalculator;

import java.text.NumberFormat;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	EditText etAmount;
	
	TextView tv10Amount, tv15Amount, tv20Amount;
	TextView tv10Total, tv15Total, tv20Total;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tv10Amount = (TextView) findViewById(R.id.lbl_10_amount);
		tv15Amount = (TextView) findViewById(R.id.lbl_15_amount);
		tv20Amount = (TextView) findViewById(R.id.lbl_20_amount);
		
		tv10Total = (TextView) findViewById(R.id.tv10Total);
		tv15Total = (TextView) findViewById(R.id.tv15Total);
		tv20Total = (TextView) findViewById(R.id.tv20Total);
		
		etAmount = (EditText) findViewById(R.id.etAmount);
		etAmount.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start,
					int count, int after) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (s.length() > 0) {
					Float billAmount = Float.parseFloat(s.toString());
					if(billAmount != null && billAmount > 0.0f) {
						//Have only 2 decimal places
						NumberFormat nf = NumberFormat.getCurrencyInstance();

						tv10Amount.setText(nf.format(getTipAmount(billAmount, 0.10f)));
						tv10Total.setText(nf.format(getTotalAmount(billAmount, 0.10f)));
						
						tv15Amount.setText(nf.format(getTipAmount(billAmount, 0.15f)));
						tv15Total.setText(nf.format(getTotalAmount(billAmount, 0.15f)));
						
						tv20Amount.setText(nf.format(getTipAmount(billAmount, 0.20f)));
						tv20Total.setText(nf.format(getTotalAmount(billAmount, 0.20f)));
					}
				}
			}
		});
	}
	
	private Float getTipAmount(float billAmount, float tipPercent) {
		if(billAmount > 0.0f && tipPercent > 0.0f) 
			return billAmount * tipPercent;
		return 0.0f;
	}
	
	private Float getTotalAmount(float billAmount, float tipPercent) {
		if(billAmount > 0.0f && tipPercent > 0.0f)
			return (billAmount + (billAmount * tipPercent));
		return 0.0f;
	}
}
