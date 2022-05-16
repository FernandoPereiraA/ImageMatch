package com.example.ejercicio2pmdmfernandopereira;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    ImageView imgv1,imgv2,imgv3,imgv4,imgv5,imgv6,imgBravo;
    String msg;
    int selectedImage;

    //private android.widget.RelativeLayout.LayoutParams layoutParams;
    private Handler handler=null;

    final int[] images = { R.drawable.emotilove, R.drawable.emotipensativo, R.drawable.emotirisa };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imgv1 = (ImageView) findViewById(R.id.imageView6);
        imgv2 = (ImageView) findViewById(R.id.imageView7);
        imgv3 = (ImageView) findViewById(R.id.imageView8);
        imgv4 = (ImageView) findViewById(R.id.imageView9);
        imgv5 = (ImageView) findViewById(R.id.imageView10);
        imgv6 = (ImageView) findViewById(R.id.imageView11);
        imgBravo = (ImageView) findViewById(R.id.imgBravo);

        //Ponemos las imagenes aleatoriamente en la parte de arriba y de abajo
        setImageAleatoria(R.id.imageView6, R.id.imageView7, R.id.imageView8);
        setImageAleatoria(R.id.imageView9, R.id.imageView10, R.id.imageView11);

        //Obtener coordenadas de los elementos de abajo
        int img4x=imgv4.getLeft();
        int img4y=imgv4.getTop();
        int img5x=imgv5.getLeft();
        int img5y=imgv5.getLeft();

        imgv1.setOnTouchListener(new MyTouchListener());
        imgv2.setOnTouchListener(new MyTouchListener());
        imgv3.setOnTouchListener(new MyTouchListener());
        imgv4.setOnDragListener(new MyDragListener());
        imgv5.setOnDragListener(new MyDragListener());
        imgv6.setOnDragListener(new MyDragListener());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //Clicks en el menu para empezar partida nueva o salir de la app
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.salir) {
            System.exit(1);
            return true;
        }
        if (id == R.id.empezar) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

public void setImageAleatoria(int img1, int img2, int img3){
        final int[] imageViews = { img1, img2, img3 };
                //R.id.imageView6, R.id.imageView7, R.id.imageView8

        Random rng = new Random();
        List<Integer> generated = new ArrayList<Integer>();
        for (int i = 0; i < 3; i++)
        {
            while(true)
            {
                Integer next = rng.nextInt(3) ;
                if (!generated.contains(next))
                {
                    generated.add(next);
                    ImageView iv = (ImageView)findViewById(imageViews[i]);
                    iv.setImageResource(images[next]);
                    iv.setTag(images[next]);
                    break;
                }
            }
        }

    }

    private final class MyTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                        view);
                view.startDrag(data, shadowBuilder, view, 0);
                selectedImage = (int) view.getTag();
                //view.setVisibility(View.INVISIBLE);
                return true;
            } else {
                return false;
            }
        }
    }


    class MyDragListener implements View.OnDragListener {
        Drawable enterShape = getResources().getDrawable(
                R.drawable.emotilove);
        Drawable normalShape = getResources().getDrawable(R.drawable.emotilove);

        public void mostrarImagenBravo(){
            imgBravo.setImageResource(R.drawable.bravo);
        }

        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            int id= v.getId();
            int tag = (int) v.getTag();

            switch(event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    //layoutParams = (RelativeLayout.LayoutParams)v.getLayoutParams();
                    Log.d(msg, "Action is DragEvent.ACTION_DRAG_STARTED");

                    // Do nothing
                    break;

                case DragEvent.ACTION_DRAG_ENTERED:
                    Log.d(msg, "Action is DragEvent.ACTION_DRAG_ENTERED");
                    int x_cord = (int) event.getX();
                    int y_cord = (int) event.getY();
                    // Si coincide el tag de una imagen de arriba con la de abajo
                    if (tag==selectedImage) {
                        MediaPlayer mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.victoria);
                        mPlayer.start();
                        //Hacemos que desaparezca la imagen
                        v.setVisibility(View.INVISIBLE);
                    }
                    break;

                case DragEvent.ACTION_DRAG_EXITED :
                    Log.d(msg, "Action is DragEvent.ACTION_DRAG_EXITED");
                    x_cord = (int) event.getX();
                    y_cord = (int) event.getY();
                    break;

                case DragEvent.ACTION_DRAG_LOCATION  :
                    Log.d(msg, "Action is DragEvent.ACTION_DRAG_LOCATION");
                    x_cord = (int) event.getX();
                    y_cord = (int) event.getY();
                    break;

                case DragEvent.ACTION_DRAG_ENDED   :
                    Log.d(msg, "Action is DragEvent.ACTION_DRAG_ENDED");
                    //y Damos la enhorabuena al usuario
                    if (imgv4.getVisibility() == View.INVISIBLE &&  imgv5.getVisibility() == View.INVISIBLE && imgv6.getVisibility() == View.INVISIBLE)
                        mostrarImagenBravo();

                    // Do nothing
                    break;

                case DragEvent.ACTION_DROP:
                    Log.d(msg, "ACTION_DROP event");

                    // Do nothing
                    break;
                default: break;
            }
            return true;
        }


    }

}