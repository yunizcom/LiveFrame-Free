package com.yuniz.liveframe;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	String showBtns = "0"; 
	String browserType = "0"; 
	Button imgBtn;
	Button musBtn;
	ImageView imageView;
	ImageView imageViewDummy;
	MediaPlayer mMediaPlayer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		imageView = (ImageView) findViewById(R.id.imageView);
		imageViewDummy = (ImageView) findViewById(R.id.imageDummy);
		imageView.setVisibility(View.INVISIBLE);
		
		try 
		{
		    // get input stream
		    InputStream ims = getAssets().open("images/welcomenote.png");
		    // load image as Drawable
		    Drawable d = Drawable.createFromStream(ims, null);
		    // set image to ImageView
		    imageViewDummy.setImageDrawable(d);
		}
		catch(IOException ex) 
		{
		    return;
		}
		
		mMediaPlayer  = new MediaPlayer();
	}
	
	public void loadImagesBtn(View v) {
		browserType = "1";
		Intent intent = new Intent();  
		intent.setType("image/*");  
		intent.setAction(Intent.ACTION_GET_CONTENT);  
		startActivityForResult(Intent.createChooser(intent, "Choose Picture"), 1);
		
	}
	
	public void loadMusicBtn(View v) {
		browserType = "2";
		Intent intent = new Intent();  
		intent.setType("audio/*");  
		intent.setAction(Intent.ACTION_GET_CONTENT);  
		startActivityForResult(Intent.createChooser(intent, "Choose Music"), 1);
	}
	
	public void openURL(View v) {
		Uri uri = Uri.parse("http://stanly.yuniz.com");
		 Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		 startActivity(intent);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
	    if(resultCode==RESULT_CANCELED)
	    {
	        // action cancelled
	    }
	    if(resultCode==RESULT_OK)
	    {
	    	if(browserType == "1"){
	    	
		    	imageView.setVisibility(View.VISIBLE);
		    	
		    	Uri selectedimg = data.getData();
		        try {
		        	imageViewDummy.setImageDrawable(null);
		        	imageViewDummy.setVisibility(View.GONE);
					imageView.setImageBitmap(MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedimg));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        
	    	}else{
	    		
	    		try {
	    			mMediaPlayer.reset();

	                mMediaPlayer.setDataSource(data.getData().getPath().toString());
	                mMediaPlayer.prepare();
	                mMediaPlayer.start();
	                mMediaPlayer.setLooping(true);
	            } catch (IOException e) {
	                Log.e("PlayAudioDemo", "Could not open file for playback.", e);
	            }

	    	}
	    }
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mMediaPlayer.stop();
        mMediaPlayer.release();
	}
	
	@Override
	protected void onStop() {
		super.onStop();

	}
	
	@Override
	protected void onPause() {
		super.onPause();

	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}	
}
