package com.cs495.kittypaint;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.graphics.Point;
import android.os.Build;

public class PonyActivity extends Activity {
	
	private ProgressBar mProgress;
	private int mPonyProgress = 0;
	private Handler mHandler = new Handler();
	
	private TextView ponytext;
	private int width;
	private int height;
	private int radius;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pony);

		
		
	}
	
	public void getWidthHeight(){
		Display display = getWindowManager().getDefaultDisplay();
		width = display.getWidth(); // deprecated, but do you see any police around? didn't think so
		height = display.getHeight();
	}
	
	public void doPony(){
		mProgress = (ProgressBar) findViewById(R.id.progressBar1);
		
		ponytext = (TextView) findViewById(R.id.ponytext);
		getWidthHeight();
		radius = (int) (0.75 * Math.min(width, height));
		
		
		new Thread(new Runnable(){ // <------------------------------ IS THIS EVEN DOING ANYTHING AT ALL
			public void run(){
				for(;;){
					mPonyProgress = mPonyProgress++ % 100;
					
					double radian = (mPonyProgress/100.0)*2*Math.PI;
					int xLoc = (int) (width/2 + Math.cos(radian) * radius);
					int yLoc = (int) (height/2 + Math.sin(radian) * radius);
					//ponytext.setX(xLoc); //requires API 11, ours is too low and we should set it higher but i'll ask later
					
					mHandler.post(new Runnable(){ // <--------- wtf is this even
						public void run(){
							//mProgress.setProgress(mPonyProgress); // i have no idea what's going on right now
							//ponytext.setText("pony" + mPonyProgress); // this doesn't work either
						}
					});
					
					try {
						Thread.sleep(33);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pony, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
