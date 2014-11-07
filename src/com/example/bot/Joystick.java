package com.example.bot;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Joystick {
	float defaultX, defaultY, maxX, maxY, minX, minY;
	float x, y;
	int id;
	public Joystick(float defaultX, float defaultY, float minX, float maxX,
			float minY, float maxY) {
		super();
		/*this.defaultX = defaultX;
		this.defaultY = defaultY;*/
		this.defaultX = (minX + maxX)/2;
		this.defaultY = (minY + maxY)/2;
		this.maxX = maxX;
		this.maxY = maxY;
		this.minX = minX;
		this.minY = minY;
		this.x = defaultX;
		this.y = defaultY;
	}
	
	
	public void updateValues(float x, float y){
		if(x>minX&&x<maxX){
			this.x  = x;
		}
		if(y>minY&&y<maxY){
			this.y = y;
		}
	}
	public void clearValues(){
		x = defaultX;
		y = defaultY;
	}
	
	public void draw(Canvas canvas, Paint paint){
		canvas.drawCircle(x, y, 30, paint);
	}
	
	public void setId(int id){
		this.id = id;
	}
	
}
