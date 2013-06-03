package com.example.photofiler;

import java.io.File;
import java.io.IOException;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.os.Environment;
import android.view.*;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.ViewFlipper;


public class MainActivity extends Activity  {

    @SuppressLint("SdCardPath")
	private static String storageName = "/sdcard/PHOTOFILER";
    private File currentDir = new File(storageName);  
    private ViewGroup mContainerView;
    private String folderName;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		mContainerView = (ViewGroup) findViewById(R.id.container);
		fill(currentDir);
	}

    public void startPhoto(View v) throws IOException{
    	Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    	File f = File.createTempFile(Camera.getImageFileName(), Camera.getImageSuffix(), Camera.getAlbumDir());
    	takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
    	startActivityForResult(takePictureIntent, 0);
    }
	
    public void startVideo(View v) throws IOException{
    	Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
    	File f = File.createTempFile(Camera.getImageFileName(), Camera.getVideoSuffix(), Camera.getAlbumDir());
    	takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
    	startActivityForResult(takeVideoIntent, 0);
    }
    
    
    public void fileBrowser(View view){
    	Intent intent1 = new Intent(this, FileBrowserDeprecated.class);
    	startActivity(intent1);   
    }
     
    
	public void fill(File f) {
		f.mkdirs();
		
		File[] dirs = f.listFiles();
		
		for (File ff : dirs) {
			if (ff.isDirectory()) {
				listDirectory(ff.getName());
				/*Set if{...} for isHidden() file to find out and whether show or hide the directory*/
			}
		}
	}

    
    void listDirectory(String name) {
        final String folderName = name;
        // Instantiate a new "row" view.
        final ViewGroup newView = (ViewGroup) LayoutInflater.from(this).inflate(
                R.layout.list_item_example, mContainerView, false);

        // Set the text in the new row to a directory.
        ((TextView) newView.findViewById(android.R.id.text1)).setText(name);

        // Set a click listener for the "X" button in the row that will remove the row.
        newView.findViewById(R.id.delete_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Remove the row from its parent (the container view).
                // Because mContainerView has android:animateLayoutChanges set to true,
                // this removal is automatically animated.
                removeFolder(view,folderName);
                mContainerView.removeView(newView);
            }
        });
        
        
        newView.findViewById(R.id.item_layout).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                flip(view,folderName);
            }
        }); mContainerView.addView(newView, 0);
    }


    public void addFolder(View view) {
		InputDialog inputDialog = new InputDialog();
		inputDialog.dialog(this, "Create new folder", "");
		folderName = inputDialog.getUserInput();
		File direct = new File(Environment.getExternalStorageDirectory()
				+ "/PHOTOFILER/" + folderName);
		if (!direct.exists())
			if (direct.mkdir())
				listDirectory(folderName);

	}
    
	
	public void lol(String folderName){
		Log.d("lol","lol");

		File direct = new File(Environment.getExternalStorageDirectory()
				+ "/PHOTOFILER/" + folderName);
		if (!direct.exists())
			if (direct.mkdir())
				fill(currentDir);
	}

	public void removeFolder(View view, String folderName) {
        //Doesn't seem like a needed if{...} however, people are manipulative,
        // it's better to make sure that they can't crash the application
        File childDir = new File(storageName + "/" + folderName);
        if (currentDir.isDirectory()) {
            String[] children = childDir.list();
            for (int i = 0; i < children.length; i++) {
                new File(childDir, children[i]).delete();
            }
            new File(currentDir, folderName).delete();
        }

    }


    
    
    public void flip(View view, String folderName){
    	 
    	
        File childDir = new File(storageName + "/" + folderName);
        if (currentDir.isDirectory()) {
            String[] children = childDir.list();
            for (int i = 0; i < children.length; i++) {
    		    //create bitmap array
//    		    imageBitmaps  = new Bitmap[i];
//    		    
//                new File(childDir, children[i]).delete();
//                //decode the placeholder image
//    		    placeholder = BitmapFactory.decodeFile(childDir + "/"+ children[i].toString());
    		    //more processing
            }
        }
    	
    	
    	// Get the ViewFlipper from the layout
        ViewFlipper vf = (ViewFlipper) findViewById(R.id.side_layout_flip);

        // Set an animation from res/anim
        vf.setAnimation(AnimationUtils.loadAnimation(view.getContext(), R.anim.slide_in_right));
        vf.showNext();
        
        
    }
    
    public void flip2(View view){	
    	// Get the ViewFlipper from the layout
        ViewFlipper vf = (ViewFlipper) findViewById(R.id.side_layout_flip);

        // Set an animation from res/anim
        vf.setAnimation(AnimationUtils.loadAnimation(view.getContext(), R.anim.slide_in_right));
        vf.showNext();
        
        
    }
    
    public void flip3(View view){	
//        ViewFlipper vf = (ViewFlipper) findViewById(R.id.settings_layout_flip);
//        vf.setAnimation(AnimationUtils.loadAnimation(view.getContext(), R.anim.slide_in_right));
//        vf.showNext();
   
    }
 
    

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


}

/*





    
    
    
	//variable for selection intent
	private final int PICKER = 1;
	//variable to store the currently selected image
	private int currentPic = 0;
	//gallery object
	@SuppressWarnings("deprecation")
	private Gallery picGallery;
	//image view for larger display
	private ImageView picView;
	
	//adapter for gallery view
	private PicAdapter imgAdapt;
	
	//declare the bitmap
	Bitmap pic = null;
	//declare the path string
	String imgPath = "";
	
	//array to store bitmaps to display
	private Bitmap[] imageBitmaps;
	//placeholder bitmap for empty spaces in gallery
	Bitmap placeholder;	
    
    
    
    











//------------------gallery_in_onCreate-------------------//

//get the large image view
picView = (ImageView) findViewById(R.id.picture);
//get the gallery view
picGallery = (Gallery) findViewById(R.id.gallery);

//create a new adapter
imgAdapt = new PicAdapter(this);
//set the gallery adapter
picGallery.setAdapter(imgAdapt);


//set long click listener for each gallery thumbnail item
picGallery.setOnItemLongClickListener(new OnItemLongClickListener() {
    //handle long clicks
   
	 public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
	        //take user to choose an image
	    	
	    	//update the currently selected position so that we assign the imported bitmap to correct item
	    	currentPic = position;
	    	//take the user to their chosen image selection app (gallery or file manager)
	    	Intent pickIntent = new Intent();
	    	pickIntent.setType("image/*");
	    	pickIntent.setAction(Intent.ACTION_GET_CONTENT);
	    	//we will handle the returned data in onActivityResult
	    	startActivityForResult(Intent.createChooser(pickIntent, "Select Picture"), PICKER);
	    	return true;
	    }
	 
	 
});


//set the click listener for each item in the thumbnail gallery
picGallery.setOnItemClickListener(new OnItemClickListener() {
    //handle clicks
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        //set the larger image view to display the chosen bitmap calling method of adapter class
        picView.setImageBitmap(imgAdapt.getPic(position));
    }
});













    //-------------------------gallery---------------------------//
    
    
    
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if (resultCode == RESULT_OK) {
		    //check if we are returning from picture selection
		    if (requestCode == PICKER) {
		            //import the image
		    	//the returned picture URI
		    	Uri pickedUri = data.getData();
		    	
		    	//if we have a new URI attempt to decode the image bitmap
		    	if(pickedUri!=null) {
		    		//set the width and height we want to use as maximum display
		    		int targetWidth = 600;
		    		int targetHeight = 400;
		    		
		    		//create bitmap options to calculate and use sample size
		    		BitmapFactory.Options bmpOptions = new BitmapFactory.Options();
		    		
		    		//first decode image dimensions only - not the image bitmap itself
		    		bmpOptions.inJustDecodeBounds = true;
		    		BitmapFactory.decodeFile(imgPath, bmpOptions);
		    		//image width and height before sampling
		    		int currHeight = bmpOptions.outHeight;
		    		int currWidth = bmpOptions.outWidth;
		    		
		    		//variable to store new sample size
		    		int sampleSize = 1;
		    		
		    		//calculate the sample size if the existing size is larger than target size
		    		if (currHeight>targetHeight || currWidth>targetWidth)
		    		{
		    		    //use either width or height
		    		    if (currWidth>currHeight)
		    		        sampleSize = Math.round((float)currHeight/(float)targetHeight);
		    		    else
		    		        sampleSize = Math.round((float)currWidth/(float)targetWidth);
		    		}
		    		
		    		//use the new sample size
		    		bmpOptions.inSampleSize = sampleSize;
		    		
		    		//now decode the bitmap using sample options
		    		bmpOptions.inJustDecodeBounds = false;
		    		
		    		//get the file as a bitmap
		    		pic = BitmapFactory.decodeFile(imgPath, bmpOptions);
		    		
		    		//pass bitmap to ImageAdapter to add to array
		    		imgAdapt.addPic(pic);
		    		//redraw the gallery thumbnails to reflect the new addition
		    		picGallery.setAdapter(imgAdapt);
		    		
		    		//display the newly selected image at larger size
		    		picView.setImageBitmap(pic);
		    		//scale options
		    		picView.setScaleType(ImageView.ScaleType.FIT_CENTER);
		    	}
		    	
		    	
		    	//retrieve the string using media data
		    	String[] medData = { MediaStore.Images.Media.DATA };
		    	//query the data
		    	Cursor picCursor = managedQuery(pickedUri, medData, null, null, null);
		    	if(picCursor!=null)
		    	{
		    	    //get the path string
		    	    int index = picCursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		    	    picCursor.moveToFirst();
		    	    imgPath = picCursor.getString(index);
		    	}
		    	else
		    	    imgPath = pickedUri.getPath();
		    }
		}
		//superclass method
		super.onActivityResult(requestCode, resultCode, data);
		
	}

	
	public class PicAdapter extends BaseAdapter {
		
		//use the default gallery background image
		int defaultItemBackground;
		//gallery context
		private Context galleryContext;
		
		
		public PicAdapter(Context c) {
		    //instantiate context
		    galleryContext = c;
		    //create bitmap array
		    imageBitmaps  = new Bitmap[10];
		    //decode the placeholder image
		    placeholder = BitmapFactory.decodeResource(getResources(), R.drawable.pic);
		    //more processing
		    
		    
		  //set placeholder as all thumbnail images in the gallery initially
		    for(int i=0; i<imageBitmaps.length; i++)
		        imageBitmaps[i]=placeholder;
		  //get the styling attributes - use default Android system resources
		    TypedArray styleAttrs = galleryContext.obtainStyledAttributes(R.styleable.PicGallery);
		    //get the background resource
		    defaultItemBackground = styleAttrs.getResourceId(
		        R.styleable.PicGallery_android_galleryItemBackground, 0);
		    //recycle attributes
		    styleAttrs.recycle();
		}
		

		//return number of data items i.e. bitmap images
		public int getCount() {
		    return imageBitmaps.length;
		}

		//return item at specified position
		public Object getItem(int position) {
		    return position;
		}

		//return item ID at specified position
		public long getItemId(int position) {
		    return position;
		}

		//get view specifies layout and display options for each thumbnail in the gallery
		public View getView(int position, View convertView, ViewGroup parent) {
		    //create the view
		    ImageView imageView = new ImageView(galleryContext);
		    //specify the bitmap at this position in the array
		    imageView.setImageBitmap(imageBitmaps[position]);
		    //set layout options
		    imageView.setLayoutParams(new Gallery.LayoutParams(300, 200));
		    //scale type within view area
		    imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
		    //set default gallery item background
		    imageView.setBackgroundResource(defaultItemBackground);
		    //return the view
		    return imageView;
		}
		
		//helper method to add a bitmap to the gallery when the user chooses one
		public void addPic(Bitmap newPic)
		{
		    //set at currently selected index
		    imageBitmaps[currentPic] = newPic;
		}
		
		//return bitmap at specified position for larger display
		public Bitmap getPic(int posn)
		{
		    //return bitmap at posn index
		    return imageBitmaps[posn];
		}
		
	}















*/
