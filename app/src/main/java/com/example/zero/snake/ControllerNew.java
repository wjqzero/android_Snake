package com.example.zero.snake;

import java.util.ArrayList;

/**
 * Created by Zero on 2017/5/4.
 */
public class ControllerNew
{
	private Model m;
	private ArrayList<Thread> threads;

	public ControllerNew(Model m)
	{
		this.m = m;
	}

	public void newSnake(int w, int h)
	{
		this.threads = new ArrayList<Thread>();
		SnakeRunnable sr = new SnakeRunnable(m, threads.size(), w, h);
		Thread t = new Thread(sr);
		t.start();
	}
}
