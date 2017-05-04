package com.example.zero.snake;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Zero on 2017/5/4.
 */
public class ViewSnakes extends View implements IModelListener
{
	private Model m;
	private ControllerSnakes c;

	public ViewSnakes(Context ct, AttributeSet at)
	{
		super(ct, at);
	}

	public void setMVC(Model m, ControllerSnakes c)
	{
		this.m = m;
		this.c = c;

		m.addListener(this);
		notifyModelListener();
	}

	public void notifyModelListener()
	{
		this.post(new Runnable() {
			@Override
			public void run() {
				ViewSnakes viewSnakes = (ViewSnakes)findViewById(R.id.drawing_area);
				viewSnakes.invalidate();
			}
		});
	}

	protected void onDraw(final Canvas c)
	{
		m.drawAll(c);
	}
}
