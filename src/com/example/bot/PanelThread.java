/**
Thread created in PrawingPanel to be used in the SurfaceView
Physics World
ICS-3UP
@authors Viral Patel, Vanshil Shah, Adit Patel, Kunj Patel
@version May 1, 2014
 */

package com.example.bot;

import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

class PanelThread extends Thread {
	private SurfaceHolder _surfaceHolder;
	private DrawingPanel _panel;
	private boolean _run = false;
	float x = 100, y = 100;
	MotionEvent e;

	public PanelThread(SurfaceHolder surfaceHolder, DrawingPanel panel) {
		_surfaceHolder = surfaceHolder;
		_panel = panel;
	}

	public void setRunning(boolean run) { // Allow us to stop the thread
		_run = run;
	}

	@Override
	public void run() {
		Canvas c;
		while (_run) { // When setRunning(false) occurs, _run is
			c = null; // set to false and loop ends, stopping thread
			try {

				c = _surfaceHolder.lockCanvas(null);
				synchronized (_surfaceHolder) {
					// Insert methods to modify positions of items in onDraw()
					update();
				}
			}finally {
				if (c != null) {
					_surfaceHolder.unlockCanvasAndPost(c);
				}
			}
		}
	}
	
	public void update(){
		_panel.postInvalidate();
		
	}
	public void sendEvent(MotionEvent e){
		//this.e = e;
	}
	
	
}