package pe.devpicon.android.agendatechlatam.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Armando on 28/3/2017.
 */

public class ImageLoader {

    private static final String TAG = ImageLoader.class.getSimpleName();

    private ImageView imageView;

    private ImageLoader instance;

    private ImageLoader() {
    }

    public  void with(ImageView imageView){
        if(instance == null){
            instance = new ImageLoader();
        }

        this.imageView = imageView;
    }

    public void load(String url){
        if(imageView != null){
            try {
                URL imageUrl = new URL(url);
                Bitmap bitmap = BitmapFactory.decodeStream(imageUrl.openConnection()
                        .getInputStream());
                imageView.setImageBitmap(bitmap);
            } catch (MalformedURLException e) {
                Log.e(TAG, "La URL provista no es correcta", e);
            } catch (IOException e) {
                Log.e(TAG, "Ocurrió un error durante la lectura de la imagen", e);
            }
        }
    }
}
