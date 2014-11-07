package com.example.bot;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

@SuppressLint("NewApi")
public class DrawingPanel extends SurfaceView implements SurfaceHolder.Callback  {
	 PanelThread _thread;	 
	 
	 public Paint paint = new Paint();
	 int centreLine = 825;
	 int centreX1 = 500, centreY1 = 750, centerX2 = 1250, centreY2 = 500;
	 int id1, in1, id2, in2;
	 float x1 = centreX1, y1 = centreY1, x2 = centerX2, y2 = centreY2;
	 Joystick left, right;
	 
	 //
	 //public Bitmap backgroundImg = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
	 
	 //Constructors
	 public DrawingPanel(Context context) { 
	        super(context); 
	    	this.setBackgroundColor(Color.WHITE);
	        paint.setAntiAlias(true);
	        paint.setStrokeWidth(6f);
	        paint.setStyle(Paint.Style.STROKE);
	        paint.setStrokeJoin(Paint.Join.ROUND);

	        left = new Joystick(440, 750, 10, 775, 10, 750);  
	        right = new Joystick(1260, 375, 875, 1640, 10, 750);  
	        
	        getHolder().addCallback(this);
	    }
	 
	 //Essentially the main method, runs multiple times and is where updating and drawing is done.
	 	@Override 
	 public void onDraw(Canvas canvas) {//do drawing stuff here.    	
	 		drawJoysticks(canvas);
	    }
	 	
	 	private void drawJoysticks(Canvas canvas){
	 		/*canvas.drawCircle(x1, y1, 35, paint);
	 	   	canvas.drawCircle(x2, y2, 35, paint);*/
	 		left.draw(canvas, paint);
	 		right.draw(canvas, paint);
	 		
	 	   	paint.setStrokeWidth(3);
	 	   	paint.setTextSize(35);
	 	   	canvas.drawText("(" + left.x + ", " + left.y + ")", 100, 100, paint);
	 	   	canvas.drawText("(" + right.x + ", " + right.y + ")", 400, 100, paint);
	 	}
	 	
	 	 public boolean onTouchEvent(MotionEvent event) {
	 	

		      switch (event.getActionMasked()) {
		      
			      case MotionEvent.ACTION_DOWN:	
			    	  updatePositions(event);
			        screenTouched();
			        return true;
			        
			      case MotionEvent.ACTION_MOVE:
			    	  	screenMoved(event);
			        	break;
			        	
			      case MotionEvent.ACTION_POINTER_UP:
				   	  	clearPositions(event);
					   	screenReleased(event);
					   	break;
					   	
			      case MotionEvent.ACTION_UP:
				   	  	clearPositions(event);
					   	screenReleased(event);
					   	break;
					   	
			      default:
			        return false;
		      }
		      return true;
		    }
	 	 	
	 	 	private void updatePositions(MotionEvent event){
	 	 		 
		    	  for(int i = 0; i < event.getPointerCount(); i++){
		    		  int in = event.findPointerIndex(event.getPointerId(i));
		    		  if(event.getX(in) < centreLine){
		    			  left.updateValues(event.getX(in), event.getY(in));  
			    	  }
		    		  else if(event.getX(in)>centreLine){
		    			  right.updateValues(event.getX(in), event.getY(in));
		    		  }
		    	  }
		    	  
	 	 	  }
	 	 	  private void clearPositions(MotionEvent event){
	 	 		if(event.getX(event.getActionIndex()) < centreLine){
		 	 		left.updateValues(left.defaultX, left.y);
	 	 		}else{
	 	 			right.updateValues(right.defaultX, right.defaultY);
	 	 		}
	 	 	  }
	 	 	  private void screenTouched(){
			   
		      }
		      private void screenMoved(MotionEvent event){
		    	 /* int in = event.findPointerIndex(0);
		    	  if(event.getX(in) > 870 && event.getX(in) < 880){
		    		  clearPositions(event);
		    	  }
		    	  else{
		    		  updatePositions(event);
		    	  }*/
		    	  updatePositions(event);
		      }
		      private void screenReleased(MotionEvent event){
		    	  
		      }
		  
			
	 	
	 	
	 	@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	    }
	    @Override
		public void surfaceCreated(SurfaceHolder holder) {


	     setWillNotDraw(false); //Allows us to use invalidate() to call onDraw()


	     _thread = new PanelThread(getHolder(), this); //Start the thread that
	        _thread.setRunning(true);                     //will make calls to 
	        _thread.start();    
	    }
	    @Override
		public void surfaceDestroyed(SurfaceHolder holder) {
	     try {
	            _thread.setRunning(false);                //Tells thread to stop
	     _thread.join();                           //Removes thread from mem.
	 } catch (InterruptedException e) {}
	    }
}