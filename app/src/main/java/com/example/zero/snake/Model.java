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

	public synchronized void addSnake(int sid, int r, int g, int b)
	{
		SQLiteDatabase rdb = this.getReadableDatabase();
		rdb.execSQL("INSERT INTO " + table1name + "(" + t1col1 + "," + t1col2 + "," + t1col3 + "," + t1col4 + ")" +
				" VALUES(?,?,?,?)", new Object[]{sid, r, g, b});
		rdb.close();
		this.notifyAllModelListeners();
	}

	public synchronized void addPoint(int sid, int x, int y)
	{
		SQLiteDatabase rdb = this.getReadableDatabase();
		rdb.execSQL("INSERT INTO " + table2name + " (" + t2col1 + "," + t2col2 + "," + t2col3 + ")" +
				" VALUES(?,?,?)", new Object[]{sid, x, y});
		rdb.close();
		this.notifyAllModelListeners();
	}

	public synchronized void deleteLastPoint(int sid)
	{
		SQLiteDatabase rdb = this.getReadableDatabase();
		rdb.execSQL("DELETE FROM " + table2name + " WHERE sid = ?", new Object[]{sid});
		rdb.close();
		this.notifyAllModelListeners();
	}

	public void addListener(IModelListener listener)
	{
		listeners.add(listener);
	}

	private void notifyAllModelListeners()
	{
		for (IModelListener listener : listeners)
		{
			listener.notifyModelListener();
		}
	}

	public synchronized void drawAll(Canvas c)
	{
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.STROKE);

		int sid, r, g, b, x1, y1, x2, y2, count;

		SQLiteDatabase rdb = this.getReadableDatabase();
		Cursor s_cursor = rdb.rawQuery("SELECT * FROM " + table1name, null);
		Cursor e_cursor = null;

		while (s_cursor.moveToNext()) {
			sid = s_cursor.getInt(s_cursor.getColumnIndex("SID"));
			r = s_cursor.getInt(s_cursor.getColumnIndex("R"));
			g = s_cursor.getInt(s_cursor.getColumnIndex("G"));
			b = s_cursor.getInt(s_cursor.getColumnIndex("B"));
			// Set color.
			paint.setColor(Color.rgb(r, g, b));

			e_cursor = rdb.rawQuery("SELECT * FROM " + table2name + " WHERE SID=" + sid, null);
			count = e_cursor.getCount();
			if (e_cursor.moveToFirst()) {
				x1 = e_cursor.getInt(e_cursor.getColumnIndex("x"));
				y1 = e_cursor.getInt(e_cursor.getColumnIndex("y"));
				for (int i = 0; i < count - 1; i++) {
					e_cursor.moveToNext();
					x2 = e_cursor.getInt(e_cursor.getColumnIndex("x"));
					y2 = e_cursor.getInt(e_cursor.getColumnIndex("y"));
					c.drawLine((float) x1, (float) y1, (float) x2, (float) y2, paint);
					x1 = x2;
					y1 = y2;
				}
			}
		}
		// Close database connection.
		if (e_cursor != null)
			e_cursor.close();
		s_cursor.close();

		//c.drawRGB(23, 23, 23);
		//c.drawLines();
		rdb.close();
	}

}
