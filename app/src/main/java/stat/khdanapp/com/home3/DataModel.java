package stat.khdanapp.com.home3;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;


import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class DataModel {

    public static String PATH_PHOTO = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() + "/Camera";

    public Observable<List<String>> listOfPicturesNames(){

        return (Observable.fromCallable(new Callable<List<String>>() {
            @Override
            public List<String> call() throws Exception {
                List<String> listNames = new ArrayList<>();
                File folder = new File(PATH_PHOTO);
                File[] files = folder.listFiles();

                if (files != null) {
                    for (File f : files) {
                        listNames.add(f.getName());
                    }
                }
              return listNames;
            }
        }));


    }

    public Observable converterJpgToPng(final String filepath){

//        boolean success=false;
//        try {
//            Bitmap bmp = BitmapFactory.decodeFile(PATH_PHOTO+"/"+filepath);// Create Bitmap object for the original image
//            // Crate new converted image file object
//            File convertedImage = new File(PATH_PHOTO + "/convertedimg.png");
//            // Create FileOutputStream object to write data to the converted image file
//            FileOutputStream outStream=new FileOutputStream(convertedImage);
//            // Keep 100 quality of the original image when converting
//            success=bmp.compress(Bitmap.CompressFormat.PNG, 100, outStream);
//            outStream.flush();
//            outStream.close();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        if (success) Log.d("CONVERT", "converting succes");
       // final String title = FilenameUtils.removeExtension(filepath);
        //final String[] title = filepath.split(".");

        String titleBuf = "";
        if (filepath.indexOf(".") > 0)
            titleBuf = filepath.substring(0, filepath.lastIndexOf("."));
        final String title = titleBuf;

        return Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter emitter) throws Exception {
                boolean success=false;
                try {
                    Bitmap bmp = BitmapFactory.decodeFile(PATH_PHOTO + "/" + filepath);// Create Bitmap object for the original image
                    // Crate new converted image file object
                    File convertedImage = new File(PATH_PHOTO + "/" + title + ".png");
                    // Create FileOutputStream object to write data to the converted image file
                    FileOutputStream outStream = new FileOutputStream(convertedImage);
                    // Keep 100 quality of the original image when converting
                    success = bmp.compress(Bitmap.CompressFormat.PNG, 100, outStream);
                    outStream.flush();
                    outStream.close();
                } catch (IOException e) {
                    emitter.onError(e);
                }
                if (success) emitter.onComplete();
                else emitter.onError(new Throwable("unsuccess"));

            }
        });


    }

}
