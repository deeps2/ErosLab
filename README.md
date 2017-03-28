# ErosLab (ErosLabs Assignment)

I was going through DownloadManger class(in android) last night, hoping that through DownloadManager the problem can be solved. 
But it seems like DownloadManager just downloads the file but there is no such method to pause/resume the download (as mentioned in challenge requirement)

So, I started implementing download functionality(using URL, HttpURLConnection etc..) of my own(inside DownloadItemsAdapter.java) on a separate background thread.
I was planning to move the same code to onHandleIntent() inside an IntentService so that the downloading will still work even when app is closed.
But, I got stuck in between:

- //getting exception here: java.io.FileNotFoundException: /data/user/0/com.shikhar.eroslab/cache/http: open failed: EISDIR (Is a directory)
   <b> RandomAccessFile output = new RandomAccessFile(outputFileCache, "rw"); </b>

There are other things also to be taken care of like publishing progress from service to UI thread(can be done using UI handler)
and saving the state of the app when closed(like progress bar, button state - can be done using shared preference) but I was unable to implement
in available time.

# ScreenShot
<img src="https://firebasestorage.googleapis.com/v0/b/delhi06-31a81.appspot.com/o/eroslabs.JPG?alt=media&token=71257c3d-5111-449d-b518-36a72c7b6927">



# Libraries Used
- Butterknife :  compile 'com.jakewharton:butterknife:8.4.0'

- RecyclerView & CardView</br>
&nbsp;&nbsp;&nbsp;&nbsp;compile 'com.android.support:cardview-v7:25.1.0' </br>
&nbsp;&nbsp;&nbsp;&nbsp;compile 'com.android.support:recyclerview-v7:25.1.0'</br>






