package com.example.zero.snake;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Model m = new Model(this);
		ControllerSnakes cs = new ControllerSnakes(m);
		ControllerNew cn = new ControllerNew(m);
		ViewSnakes vs = (ViewSnakes) findViewById(R.id.drawing_area);
		ViewNew vn = (ViewNew) findViewById(R.id.btn_new);

		vs.setMVC(m, cs);
		vn.setMVC(m, cn);


	}
}
