package mx.ita.securityhome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import net.glxn.qrgen.android.QRCode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class screenshot_reunion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screenshot_reunion);
        Bundle bundle = getIntent().getExtras();
        String dato = bundle.getString("id_reunion");
        Log.i("Dato recibido", dato);
        ImageView QR = findViewById(R.id.printQR2);
        Bitmap bitmap = QRCode.from(dato).withSize(400, 400).bitmap();
        QR.setImageBitmap(bitmap);

        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {

            ConstraintLayout content = findViewById(R.id.screenShotReunion);
            content.setDrawingCacheEnabled(true);
            Bitmap bitmap2 = content.getDrawingCache();
            content.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            content.layout(0, 0, content.getMeasuredWidth(), content.getMeasuredHeight());

            content.buildDrawingCache(true);
            Bitmap b = Bitmap.createBitmap(content.getDrawingCache());
            content.setDrawingCacheEnabled(false); // clear drawing cache
            if(b == null)
                Log.i("Bitmap","no hay nada XD");
            shareImageUri(saveImage(b));
        }
    }
    private Uri saveImage(Bitmap image) {

        File imagesFolder = new File(getCacheDir(), "images");
        Uri uri = null;
        try {
            imagesFolder.mkdirs();
            File file = new File(imagesFolder, "shared_image.png");

            FileOutputStream stream = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.JPEG, 10, stream);
            stream.flush();
            stream.close();
            uri = FileProvider.getUriForFile(this, "mx.ita.securityhome", file);

        } catch (IOException e) {
            Log.d("Compartir", "IOException while trying to write file for sharing: " + e.getMessage());
        }
        return uri;
    }
    private void shareImageUri(Uri uri){
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setType("image/png");
        startActivity(intent);
    }
}