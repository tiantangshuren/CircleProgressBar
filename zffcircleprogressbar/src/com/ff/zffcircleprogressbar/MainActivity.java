package com.ff.zffcircleprogressbar;

import com.ff.zffcircleprogressbar.ui.CircleProgressBar;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;

public class MainActivity extends Activity implements Runnable {
	
	private CircleProgressBar circleProgressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		circleProgressBar = (CircleProgressBar) findViewById(R.id.cirlcle_progress_bar);
		circleProgressBar.setMax(100);
	}
	
	public void start(View v){
		progress = 0;
		new Thread(this).start();
	}

	int progress = 0;
	@Override
	public void run() {
		while(true){
			if(progress == 100){
				break;
			}
			SystemClock.sleep(50);
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					progress ++;
					circleProgressBar.setProgress(progress);
				}
			});
		}
	}
}
