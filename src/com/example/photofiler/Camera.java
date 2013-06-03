package com.example.photofiler;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

public class Camera extends Activity {
	private static final String JPEG_FILE_SUFFIX = ".jpg";
	private static final String MP4_FILE_SUFFIX = ".mp4";
	private static final String JPEG_FILE_PREFIX = "Photofiler_";

	static String timeStamp = new SimpleDateFormat("HHmmss", Locale.US).format(new Date());
	static String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
	File albumF = getAlbumDir();

	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		if (resultCode == RESULT_OK) {
			// handlePhoto(intent);
		}

	}

	public static File getAlbumDir() {
		File storageDir = null;

		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {

			storageDir = getAlbumStorageDir(getAlbumName());

			if (storageDir != null) {
				if (!storageDir.mkdirs()) {
					if (!storageDir.exists()) {
						Log.d("Photofiler", "failed to create directory");
						return null;
					}
				}
			}
		}

		return storageDir;
	}

	private static String getAlbumName() {
		// Name of the directory to be used during photo saving
		return "Photofiler";
	}

	public static File getAlbumStorageDir(String albumName) {
		return new File(Environment.getExternalStoragePublicDirectory(
		// Sets the name for directory where to store the folders with pictures
				"PHOTOFILER"), albumName);
	}

	public static String getImageFileName() {
		return imageFileName;
	}

	public static String getTimeStamp() {
		return timeStamp;
	}
	
	public static String getImageSuffix() {
		return JPEG_FILE_SUFFIX;
	}
	
	public static String getVideoSuffix() {
		return MP4_FILE_SUFFIX;
	}

}
