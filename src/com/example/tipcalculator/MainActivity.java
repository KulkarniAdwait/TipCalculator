package com.example.tipcalculator;

import java.text.NumberFormat;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class MainActivity extends Activity {
	//Have only 2 decimal places
	NumberFormat nf = NumberFormat.getCurrencyInstance();
	NumberFormat nfPercent = NumberFormat.getPercentInstance();
	
	RadioButton rbPercent, rbAmount;
	RadioGroup rgTipMethod;
	
	EditText etAmount, etUserNumber;
	
	TextView tv10Amount, tv15Amount, tv20Amount, tvUserAmount;
	TextView tv10Total, tv15Total, tv20Total, tvUserTotal;
	
	final int MAX_PERCENTAGE_LENGTH = 2;
	final int MAX_TIP_AMOUNT_LENGTH = 3;
	final int MAX_BILL_AMOUNT_LENGTH = 5;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		rgTipMethod = (RadioGroup) findViewById(R.id.rgTipMethod);
		rbPercent = (RadioButton) findViewById(R.id.rd_percent);
		rbAmount = (RadioButton) findViewById(R.id.rd_amount);
		
		tv10Amount = (TextView) findViewById(R.id.lbl_10_amount);
		tv15Amount = (TextView) findViewById(R.id.lbl_15_amount);
		tv20Amount = (TextView) findViewById(R.id.lbl_20_amount);
		tvUserAmount = (TextView) findViewById(R.id.lbl_User_Amount);
		
		tv10Total = (TextView) findViewById(R.id.tv10Total);
		tv15Total = (TextView) findViewById(R.id.tv15Total);
		tv20Total = (TextView) findViewById(R.id.tv20Total);
		tvUserTotal = (TextView) findViewById(R.id.tvUserTotal);
		
		etUserNumber = (EditText) findViewById(R.id.etUserNumber);
		etUserNumber.setFilters(new InputFilter[] {new InputFilter.LengthFilter(MAX_PERCENTAGE_LENGTH)});
		
		etAmount = (EditText) findViewById(R.id.etAmount);
		etAmount.setFilters(new InputFilter[] {new InputFilter.LengthFilter(MAX_BILL_AMOUNT_LENGTH)});
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

						tv10Amount.setText(nf.format(getTipAmount(billAmount, 0.10f)));
						tv10Total.setText(nf.format(getTotalAmount(billAmount, 0.10f)));
						
						tv15Amount.setText(nf.format(getTipAmount(billAmount, 0.15f)));
						tv15Total.setText(nf.format(getTotalAmount(billAmount, 0.15f)));
						
						tv20Amount.setText(nf.format(getTipAmount(billAmount, 0.20f)));
						tv20Total.setText(nf.format(getTotalAmount(billAmount, 0.20f)));
						
						userValueHandler(billAmount);
					}
				}
			}
		});
	
		etUserNumber.addTextChangedListener(new TextWatcher() {

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
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (s.length() > 0 && s.length() <=3) {
					Float userNumber = Float.parseFloat(s.toString());
					Float billAmount = Float.parseFloat(etAmount.getText().toString());
					if (userNumber != null && billAmount != null
							&& userNumber > 0.0f && billAmount > 0.0f) {
						userValueHandler(billAmount);
					}
				}
			}
		});
	
		rgTipMethod.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(rbPercent.isChecked()) {
					etUserNumber.setFilters(new InputFilter[] {new InputFilter.LengthFilter(MAX_PERCENTAGE_LENGTH)});
				}
				else {
					etUserNumber.setFilters(new InputFilter[] {new InputFilter.LengthFilter(MAX_TIP_AMOUNT_LENGTH)});
				}
				
				Float billAmount = Float.parseFloat(etAmount.getText().toString());
				if(billAmount != null && billAmount > 0.0f) {
					userValueHandler(billAmount);
				}
			}
			
		});
	}
	
	private void userValueHandler(Float billAmount) {
		Float userNumber;
		try {
			userNumber = Float.parseFloat(etUserNumber.getText().toString());
		}
		catch(Exception ex) {
			userNumber = Float.parseFloat(getString(R.string.default_User_Amount));
		}
		if(userNumber != null && userNumber > 0.0f) {
			if(rbPercent.isChecked()) {
				tvUserAmount.setText(nf.format(getTipAmount(billAmount, userNumber / 100.f)));
				tvUserTotal.setText(nf.format(getTotalAmount(billAmount, userNumber / 100.f)));
			}
			else {
				tvUserAmount.setText(nfPercent.format(getTipPercent(billAmount, userNumber)));
				tvUserTotal.setText(nf.format(billAmount + userNumber));
			}
		}
	}
	
	private Float getTipPercent(float billAmount, float tipAmount) {
		if(billAmount > 0.0f && tipAmount > 0.0f) {
			return tipAmount / billAmount;
		}
		return 0.0f;
	}
	
	/***
	 * 
	 * @param billAmount
	 * @param tipPercent
	 * @return
	 */
	private Float getTipAmount(float billAmount, float tipPercent) {
		if(billAmount > 0.0f && tipPercent > 0.0f) 
			return billAmount * tipPercent;
		return 0.0f;
	}
	
	/***
	 * 
	 * @param billAmount
	 * @param tipPercent
	 * @return
	 */
	private Float getTotalAmount(float billAmount, float tipPercent) {
		if(billAmount > 0.0f && tipPercent > 0.0f)
			return (billAmount + (billAmount * tipPercent));
		return 0.0f;
	}
}
