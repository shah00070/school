package com.schooltriangle.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.schooltriangle.mylibrary.R;

public class GooglePlayAlert extends Dialog {
	Context context;
	private TextView message_text;

	public GooglePlayAlert(Context context) {
		super(context);
		this.context = context;
	}

	public void setMessage(String message) {
		message_text.setText(message);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.alert_dialog);
		message_text = (TextView) findViewById(R.id.message);
		TextView ok = (TextView) findViewById(R.id.ok);

		ok.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri
						.parse("market://details?id=bizcloud.jeevom.com"));
				context.startActivity(intent);
				dismiss();
			}
		});

	}
}
