package com.example.zero.snake;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

/**
 * Created by Zero on 2017/5/4.
 */
public class Model extends SQLiteOpenHelper
{
	private ArrayList<IModelListener> listeners;

	private final String table1name = "SnakeData";
	private final String table2name = "PointData";
	private final String t1col1 = "SID";
	private final String t1col2 = "R";
	private final String t1col3 = "G";
	private final String t1col4 = "B";
	private final String t2col1 = "SID";
	private final String t2col2 = "x";
	private final String t2col3 = "y";

	public Model(Context context)
	{
		super(context, "mydb.db", null, 1);
		this.listeners = new ArrayList<IModelListener>();
	}

	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL("CREATE TABLE " + table1name + "(_id INTEGER PRIMARY KEY, " + t1col1 + " INTEGER," +
				t1col2 + " INTEGER," + t1col3 + " INTEGER," + t1col4 + " INTEGER)");
		db.execSQL("CREATE TABLE " + table2name + "(_id INTEGER PRIMARY KEY, " + t2col1 + " INTEGER," +
				t2col2 + " INTEGER," + t2col3 + " INTEGER)");
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
	}

	public void addSnake(int sid, int r, int g, int b)
	{
		SQLiteDatabase rdb = this.getReadableDatabase();
		rdb.execSQL("INSERT INTO " + table1name + "(" + t1col1 + "," + t1col2 + "," + t1col3 + "," + t1col4 + ")" +
				" VALUES(?,?,?,?)", new Object[]{sid, r, g, b});
		rdb.close();
	}

	public void addPoint(int sid, int x, int y)
	{
		SQLiteDatabase rdb = this.getReadableDatabase();
		rdb.execSQL("INSERT INTO " + table2name + " (" + t2col1 + "," + t2col2 + "," + t2col3 + ")" +
				" VALUES(?,?,?)", new Object[]{sid, x, y});
		rdb.close();
	}

	public void deleteLastPoint(int sid)
	{
		SQLiteDatabase rdb = this.getReadableDatabase();
		rdb.execSQL("DELETE FROM " + table2name + " WHERE sid = ?", new Object[]{sid});
		rdb.close();
	}

	public void addListener(IModelListener listener)
	{
		listeners.add(listener);
	}

	private void notifyAllModeListeners()
	{
		for (IModelListener listener : listeners)
		{
			listener.notifyModelListener();
		}
	}

	public void drawAll(Canvas c)
	{
		SQLiteDatabase rdb = this.getReadableDatabase();
		Cursor cursor = rdb.rawQuery("SELECT * FROM ?", new String[]{table2name});
		c.drawRGB(23, 23, 23);
		//c.drawLines();
		rdb.close();
	}

}
