package com.example.zero.snake;

import java.util.Random;

/**
 * Created by Zero on 2017/5/4.
 */
public class SnakeRunnable implements Runnable
{
	private Model m;
	private int sid, w, h;
	private long delay;
	Random r = new Random();

	public SnakeRunnable(Model m, int sid, int w, int h)
	{
		this.delay = 100 + r.nextInt(1901);
		this.m = m;
		m.addSnake(this.sid, r.nextInt(255) + 1, r.nextInt(255) + 1, r.nextInt(255) + 1);
		for(int i=0; i<5; i++)
		{
			m.addPoint(this.sid, r.nextInt(w + 1), r.nextInt(h + 1));
		}
	}

	public void run()
	{
		while(true)
		{
			m.addPoint(this.sid, r.nextInt(w + 1), r.nextInt(h + 1));
			m.deleteLastPoint(this.sid);
			try
			{
				Thread.sleep(this.delay);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}

}
