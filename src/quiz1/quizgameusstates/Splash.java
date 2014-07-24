/**
 * @filename  src/quiz1/quizgameusstates/Splash.java
 * @author    Richard Szeto
 * @ccsfname  rszeto
 * @date      April 23, 2014
 * @objective Starting activity that asks user for his/her
 *            name. Also displays buttons to play game
 *            or view scores.
 */

package quiz1.quizgameusstates;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

public class Splash extends Activity
{
	private SQLiteDatabase db;
	
	private Cursor c;
	
	private String tag;
	
	private String name;
	
	private EditText nameEditText;
	
	private static Toast message;
	
	/*******************onCreate()*********************/
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        
        initialize();
        
        createOrOpenDatabase();
        
        populateDatabase();
        
        closeDatabase();
        
        setNameEditText();
    }
    
    /*********************initialize()**************/
    private void initialize()
    {
    	tag = getResources().getString(R.string.tagName);
    }
    
    /***************createOrOpenDatabase()************/
    private void createOrOpenDatabase()
    {
    	if(db == null || !db.isOpen())
    	{
	    	db = openOrCreateDatabase("UnitedStates.db",
	        		SQLiteDatabase.CREATE_IF_NECESSARY, null);
	        
	        String sqlcmd = "create table if not exists states_capitals(" +
	        		"_id integer primary key autoincrement, state text " +
	        		"not null, capital text not null);";
	        
	        db.execSQL(sqlcmd);
	        
	        sqlcmd = "create table if not exists highScores(" +
	        		"_id integer primary key autoincrement, name text " +
	        		"not null, score integer not null);";
	        
	        db.execSQL(sqlcmd);
	        
	        db.setVersion(1);
    	}
    }
    
    /**************populateDatabase()************/
    private void populateDatabase()
    {
    	c = db.rawQuery("select * from states_capitals;", null);
        
        if(c.getCount() == 0)
        {
        	Log.i(tag, "isAfterLast");
	    	String states[] = new String[50];
	    	String capitals[] = new String[50];
	    	
	    	Context con = getApplicationContext();
	    	
	    	US.parseUSStates(states, capitals, con, tag);
	    	
	    	ContentValues cv = new ContentValues();
	    	
	    	try
	    	{
		    	for(int i = 0; i < states.length; i++)
		    	{
		    		cv.put("state",states[i]);
		    		cv.put("capital",capitals[i]);
		    		db.insertOrThrow("states_capitals", null, cv);
		    	}
	    	}
	    	catch(SQLException e)
	    	{
	    		Log.e(tag, e.toString());
	    	}
        }
    }
    
    /*************closeDatabase()***************/
    private void closeDatabase()
    {
    	if(db != null && db.isOpen())
    		db.close();
    }
    
    /***************setNameEditText()************/
    private void setNameEditText()
    {
    	nameEditText = (EditText)findViewById(R.id.nameEditText);
        
        nameEditText.setOnKeyListener(new View.OnKeyListener()
        {
        	@Override
			public boolean onKey(View v, int keyCode,
        			                        KeyEvent ke)
        	{
        		if((ke.getAction() == KeyEvent.ACTION_DOWN) &&
        				(keyCode == KeyEvent.KEYCODE_ENTER))
        		{
        			name = nameEditText.getText().toString();
        			
        			if(name != null)
        			{
        				if(name.equals(""))
        				{
        					mkToast(getResources().getString(
            	    				R.string.nameFailure));
        					return false;
        				}
        				mkToast(getResources().getString(
        	    				R.string.nameSuccess));
        				return true;
        			}
        			else
        			{
        				mkToast(getResources().getString(
        	    				R.string.nameFailure));
        				return false;
        			}
        		}
        		
        		return false;
        	}
        });
    }
    
    /***************onButtonClick()***********/
    public void onButtonClick(View v)
    {
    	switch(v.getId())
    	{
    	    case R.id.gameButton:
    	    	if(name != null)
    	    	{
    	    		if(name.equals(""))
    	    		{
    	    			mkToast(getResources().getString(
        	    				R.string.nameError));
    	    		}
    	    		else
    	    		{
    	    			Intent i = new Intent(getApplicationContext(),
    	    					QuizGame.class);
    	    			startActivityForResult(i,1);
    	    		}
    	    	}
    	    	else
    	    	{
    	    		mkToast(getResources().getString(
    	    				R.string.nameError));
    	    	}
    	    	break;
    	    
    	    case R.id.scoreButton:
    	    	Intent i = new Intent(getApplicationContext(),
    	    			Scores.class);
    	    	startActivity(i);
    	    	break;
    	}
    }
    
    /*******************onActivityResult()****************/
    @Override
    protected void onActivityResult(int requestCode, 
    		int resultCode, Intent x)
    {
    	if(requestCode == 1)
    	{
    		if(resultCode == RESULT_OK)
    		{
    			int scoreResult = x.getIntExtra("score", -1);
    			
    			if(scoreResult == -1)
    			{
    				mkToast(getResources().getString(
    						R.string.scoreFailure));
    			}
    			else
    			{
	    			openDatabase();
	    			
	    			insertScore(scoreResult);
	    			
	    			closeDatabase();
    			}
    		}
    		else if(resultCode == RESULT_CANCELED)
    		{
    			mkToast(getResources().getString(
    					R.string.scoreFailure));
    		}
    		else
    		{
    			super.onActivityResult(requestCode, resultCode, x);
    		}
    	}
    	else
    	{
    		super.onActivityResult(requestCode, resultCode, x);
    	}
    }
    
    /***************openDatabase()****************/
    private void openDatabase()
    {
    	if(db == null || !db.isOpen())
    	{
    		db = openOrCreateDatabase("UnitedStates.db",
    				SQLiteDatabase.OPEN_READWRITE, null);
    	}
    }
    
    /*****************insertScore()****************/
    private void insertScore(int score)
    {
    	ContentValues cv = new ContentValues();
    	cv.put("name", name);
    	cv.put("score", score);
    	try
    	{
    		db.insertOrThrow("highScores", null, cv);
    	}
    	catch(SQLException e)
    	{
    		Log.e(tag, e.toString());
    	}
    	
    	c = db.rawQuery("select * from highScores order " +
    				"by score DESC;", null);
    	
    	while(c.getCount() > 10)
    	{
    		c.moveToLast();
    		
    		String[] id = new String[1];
    		
    		id[0] = c.getString(c.getColumnIndex("_id"));
    		
    		db.delete("highScores", "_id = ?", id);
    		
    		c = db.rawQuery("select * from highScores order " +
    				"by score DESC;", null);
    	}
    }

    /**************onCreateOptionsMenu()**********/
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        
        // Inflate the menu; this adds items to the action bar
    	// if it is present.
        getMenuInflater().inflate(R.menu.splash, menu);
        return true;
    }
    
    /**************onOptionsItemSelected()**************/
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
    
    /******************mkToast()*******************/
    public void mkToast(String text)
    {
    	if(message != null)
    		message.cancel();
    	
    	message = Toast.makeText(getBaseContext(), text, 
    			Toast.LENGTH_SHORT);
    	
    	message.setGravity(Gravity.CENTER, 0, 0);
    	
    	message.show();
    }
}
