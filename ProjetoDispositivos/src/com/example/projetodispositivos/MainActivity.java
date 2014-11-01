package com.example.projetodispositivos;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements SensorEventListener{

	private TextView txtAviso;
	private ArrayList<Point> cliques; //posicoes de onde se está clicando (multitouch)
	
	private SensorManager mSensorManager;
	private Sensor mAcelerometro;
	private GameView gameView;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		
		cliques = new ArrayList<Point>();
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		gameView = new GameView(this);
		setContentView(gameView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
	@Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	mSensorManager.registerListener(this, mAcelerometro, 50000);    	
    }
	
    @Override
    protected void onPause() {
    	// TODO Auto-generated method stub
    	super.onPause();
    	mSensorManager.unregisterListener(this);
    }
	
    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	Float x = event.getX();
    	Float y = event.getY();
    	
    	Point pontoClicado = new Point(x.intValue(),y.intValue());
    	cliques.add(pontoClicado);
    	gameView.setCliques(cliques);
    	
    	//atualizarLigacoes(); agora faz-se no GameView os desenhos das linhas
    	
    	return super.onTouchEvent(event);
    }
    
    /**
     * faz as ligacos dos pontos que estão armazenados no ArrayList
     * liga um a um todos os pontos armazenados
     */
//    private void atualizarLigacoes() {
//    	if (cliques.size() > 1) //possui mais de um ponto para ser ligado
//    	for (int i=0;i<cliques.size();i++)
//    		for (int j=i+1;j<cliques.size();j++)
//    			//faz as ligações graficamente de cliques[i] e cliques[j]
//	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		Float x = event.values[0];
		Float y = event.values[1];
		Float z = event.values[2];
		
//		txtX.setText("pos X = "+ x.intValue() + " Float" + x.toString());
//		txtY.setText("pos Y = "+ y.intValue() + " Float" + y.toString());
//		txtZ.setText("pos Z = "+ z.intValue() + " Float" + z.toString());
		
		if ( y < 0) {
			if (x > 0)
				txtAviso.setText("Virando a esquerda e Inveritdo");
			if (x < 0)
				txtAviso.setText("Virando a Direita e Inveritdo");
		} else	{
			if (x > 0)
				txtAviso.setText("Virando a esquerda ");
			if (x < 0)
				txtAviso.setText("Virando a Direita ");
		}		
		
	}
	
}

//setado como ContentView no onCreat da activity
class GameView extends View {
    private Paint paint;
    private ArrayList<Point> cliques;
    
    public GameView(Context context) {
          super(context);
         //bmp = BitmapFactory.decodeResource(getResources(), R.drawable.icon); Desenha-se uma imagem
          paint = new Paint();
          paint.setColor(Color.BLACK);
          paint.setStrokeWidth(1);
          paint.setTextSize(20);
          
    }
    
    public void setCliques(ArrayList<Point> cliques){
    	this.cliques = cliques;
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
//    	  canvas.drawColor(Color.WHITE);
//        canvas.drawCircle(50, 80, 30, paint);
//        canvas.drawLine(80, 80, 80, 200, paint);
//        canvas.drawText(""+canvas.getWidth()+", "+canvas.getHeight(), 0, 200,paint);
          
          if (cliques != null && cliques.size() > 1) //possui mais de um ponto para ser ligado
          	for (int i=0;i<cliques.size();i++)
          		for (int j=i+1;j<cliques.size();j++)
          			canvas.drawLine(cliques.get(i).x,cliques.get(i).y,cliques.get(j).x,cliques.get(j).y,paint);
    }
}




