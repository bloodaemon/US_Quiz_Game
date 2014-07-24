/**
 * @filename  src/quiz1/quizgameusstates/Scores.java
 * @author    Richard Szeto
 * @ccsfname  rszeto
 * @date      April 23, 2014
 * @objective Activity started by intent from Splash
 *            activity. Displays the top 10 scores
 *            and the players' names associated.
 */

package quiz1.quizgameusstates;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;

public class Scores extends Activity
{
	
	private SQLiteDatabase db;
	
	private Cursor c;
	
	private TextView[] nameHS;
	
	private TextView[] scoreHS;
	
	private String tag;
	
	/*****************onCreate()*******************/
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_scores);
		
		initialize();
		
		openDatabase();
		
		showScores();
		
		closeDatabase();
	}
	
	/*****************initialize()*****************/
	private void initialize()
	{
		nameHS = new TextView[10];
		
		scoreHS = new TextView[10];
		
		nameHS[0] = (TextView)findViewById(R.id.nameHS1);
		nameHS[1] = (TextView)findViewById(R.id.nameHS2);
		nameHS[2] = (TextView)findViewById(R.id.nameHS3);
		nameHS[3] = (TextView)findViewById(R.id.nameHS4);
		nameHS[4] = (TextView)findViewById(R.id.nameHS5);
		nameHS[5] = (TextView)findViewById(R.id.nameHS6);
		nameHS[6] = (TextView)findViewById(R.id.nameHS7);
		nameHS[7] = (TextView)findViewById(R.id.nameHS8);
		nameHS[8] = (TextView)findViewById(R.id.nameHS9);
		nameHS[9] = (TextView)findViewById(R.id.nameHS10);
		
		scoreHS[0] = (TextView)findViewById(R.id.scoreHS1);
		scoreHS[1] = (TextView)findViewById(R.id.scoreHS2);
		scoreHS[2] = (TextView)findViewById(R.id.scoreHS3);
		scoreHS[3] = (TextView)findViewById(R.id.scoreHS4);
		scoreHS[4] = (TextView)findViewById(R.id.scoreHS5);
		scoreHS[5] = (TextView)findViewById(R.id.scoreHS6);
		scoreHS[6] = (TextView)findViewById(R.id.scoreHS7);
		scoreHS[7] = (TextView)findViewById(R.id.scoreHS8);
		scoreHS[8] = (TextView)findViewById(R.id.scoreHS9);
		scoreHS[9] = (TextView)findViewById(R.id.scoreHS10);
		
		tag = getResources().getString(R.string.tagName);
	}
	
	/*****************openDatabase()******************/
	private void openDatabase()
	{
		if(db == null || !db.isOpen())
		{
			db = openOrCreateDatabase("UnitedStates.db",
					SQLiteDatabase.OPEN_READONLY, null);
		}
	}
	
	/***************closeDatabase()**************/
	private void closeDatabase()
	{
		if(db != null && db.isOpen())
		{
			db.close();
		}
	}
	
	/*************showScores()***************/
	private void showScores()
	{
		c = db.rawQuery("select * from highScores order " +
				  "by score DESC;", null);
		
		if(c == null)
		{
			Log.i(tag, "is null");
		}
		else if(c.isBeforeFirst())
		{
			Log.i(tag, "is Before First");
		}
		else if(c.isAfterLast())
		{
			Log.i(tag, "is After Last");
		}
		
		c.moveToFirst();
		
		for(int i = 0; !c.isAfterLast() &&
				i < nameHS.length; i++, c.moveToNext())
		{
			nameHS[i].setText(c.getString(
					c.getColumnIndex("name")));
			
			scoreHS[i].setText(c.getString(
					c.getColumnIndex("score")));
		}
	}

	/**************onCreateOptionsMenu()*************/
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.scores, menu);
		return true;
	}

	/****************onOptionsItemSelected()***************/
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings)
		{
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
