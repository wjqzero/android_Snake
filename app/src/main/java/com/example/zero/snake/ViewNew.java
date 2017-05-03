package com.example.zero.snake;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

/**
 * Created by Zero on 2017/5/4.
 */
public class ViewNew extends Button implements IModelListener
{
	private Model m;
	private ControllerNew c;

	public ViewNew(Context ct, AttributeSet at)
	{
		super(ct, at);
	}

	public void setMVC(Model m, ControllerNew cn)
	{
		this.m = m;
		this.c = cn;

		m.addListener(this);
		notifyModelListener();

		this.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				c.newSnake(v.getWidth(), v.getHeight());
			}
		});
	}

	public void notifyModelListener()
	{
	}
}
