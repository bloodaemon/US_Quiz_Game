/**
 * @filename  src/quiz1/quizgameusstates/QuizGame.java
 * @author    Richard Szeto
 * @ccsfname  rszeto
 * @date      April 23, 2014
 * @objective Activity started by intent from Splash
 *            activity. Asks users a series of 
 *            questions about states and capitals
 *            of the United States. Calculates and
 *            returns score back to Splash activity.
 */

package quiz1.quizgameusstates;

import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class QuizGame extends Activity
{
	private static Toast message;
	
	private String[] states;
	
	private String[] capitals;
	
	private String tag;
	
	private SQLiteDatabase db;
	
	private Cursor c;
	
	private TextView[] stateTextView;
	
	private TextView[] capitalTextView;
	
	private EditText[] stateEditText;
	
	private EditText[] capitalEditText;
	
	private String[] statesAnswers;
	
	private String[] capitalsAnswers;
	
	/********************onCreate()******************/
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_quizgame);
        
        openDatabase();
        
        initialize();
        
        pickRandomData("state", states);
        
        pickRandomData("capital", capitals);
        
        closeDatabase();
        
        fillStateTextView();
        
        fillCapitalTextView();
        
        setStateEditText();
        
        setCapitalEditText();
    }
	
	/*****************initialize()***************/
	private void initialize()
	{
		stateTextView = new TextView[5];

		capitalTextView = new TextView[5];
		
		stateEditText = new EditText[5];
		
		capitalEditText = new EditText[5];
		
		stateTextView[0] = (TextView)findViewById(
				R.id.state1TextView);
		stateTextView[1] = (TextView)findViewById(
				R.id.state2TextView);
		stateTextView[2] = (TextView)findViewById(
				R.id.state3TextView);
		stateTextView[3] = (TextView)findViewById(
				R.id.state4TextView);
		stateTextView[4] = (TextView)findViewById(
				R.id.state5TextView);
		
		capitalTextView[0] = (TextView)findViewById(
				R.id.capital1TextView);
		capitalTextView[1] = (TextView)findViewById(
				R.id.capital2TextView);
		capitalTextView[2] = (TextView)findViewById(
				R.id.capital3TextView);
		capitalTextView[3] = (TextView)findViewById(
				R.id.capital4TextView);
		capitalTextView[4] = (TextView)findViewById(
				R.id.capital5TextView);
		
		stateEditText[0] = (EditText)findViewById(
				R.id.state1EditText);
		stateEditText[1] = (EditText)findViewById(
				R.id.state2EditText);
		stateEditText[2] = (EditText)findViewById(
				R.id.state3EditText);
		stateEditText[3] = (EditText)findViewById(
				R.id.state4EditText);
		stateEditText[4] = (EditText)findViewById(
				R.id.state5EditText);
		
		capitalEditText[0] = (EditText)findViewById(
				R.id.capital1EditText);
		capitalEditText[1] = (EditText)findViewById(
				R.id.capital2EditText);
		capitalEditText[2] = (EditText)findViewById(
				R.id.capital3EditText);
		capitalEditText[3] = (EditText)findViewById(
				R.id.capital4EditText);
		capitalEditText[4] = (EditText)findViewById(
				R.id.capital5EditText);
		
		tag = getResources().getString(R.string.tagName);
        
        states = new String[5];
        capitals = new String[5];
        
        c = db.rawQuery("select * from states_capitals;", null);
        
        statesAnswers = new String[5];
        
        capitalsAnswers = new String[5];
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
	
	/****************closeDatabase()****************/
	private void closeDatabase()
	{
		if(db != null && db.isOpen())
			db.close();
	}
	
	/*****************pickRandomData()*******************/
	private void pickRandomData(String field, String[] array)
	{
		int i = 0;
		
		boolean found = false;
		
		Random rand = new Random();
		
		int number;
		
		String data;
		
		while(i < array.length)
		{
			number = rand.nextInt(50);
			
			c.moveToPosition(number);
			
			data = c.getString(c.getColumnIndex(field));
			
			for(int j = 0; j < i; j++)
			{
				if(data.equals(array[j]))
				{
					found = true;
				}
			}
			
			if(!found)
			{
				array[i] = data;
				i++;
			}
			
			found = false;
		}
	}
	
	/*********************fillStateTextView()***********/
	private void fillStateTextView()
	{
		for(int i = 0; i < states.length &&
				i < stateTextView.length; i++)
		{
			stateTextView[i].setText(states[i]);
		}
	}
	
	/******************fillCapitalTextView()*************/
	private void fillCapitalTextView()
	{
		for(int i = 0; i < capitals.length &&
				i < capitalTextView.length; i++)
		{
			capitalTextView[i].setText(capitals[i]);
		}
	}
	
	/*****************setStateEditText()***************/
	private void setStateEditText()
	{
		stateEditText[0].setOnKeyListener(new View.OnKeyListener()
        {
        	@Override
			public boolean onKey(View v, int keyCode,
        			                        KeyEvent ke)
        	{
        		if((ke.getAction() == KeyEvent.ACTION_DOWN) &&
        				(keyCode == KeyEvent.KEYCODE_ENTER))
        		{
        			statesAnswers[0] = stateEditText[0].
        					getText().toString();
        			
        			if(statesAnswers[0] != null &&
        					!statesAnswers[0].equals(""))
        			{
        				mkToast(getResources().getString(
        						R.string.answerSuccess));
        				return true;
        			}
        			else
        			{
        				mkToast(getResources().getString(
        						R.string.answerFailure));
        				return false;
        			}
        		}
        		
        		return false;
        	}
        });
		
		stateEditText[1].setOnKeyListener(new View.OnKeyListener()
        {
        	@Override
			public boolean onKey(View v, int keyCode,
        			                        KeyEvent ke)
        	{
        		if((ke.getAction() == KeyEvent.ACTION_DOWN) &&
        				(keyCode == KeyEvent.KEYCODE_ENTER))
        		{
        			statesAnswers[1] = stateEditText[1].
        					getText().toString();
        			
        			if(statesAnswers[1] != null && 
        					!statesAnswers[1].equals(""))
        			{
        				mkToast(getResources().getString(
        						R.string.answerSuccess));
        				return true;
        			}
        			else
        			{
        				mkToast(getResources().getString(
        						R.string.answerFailure));
        				return false;
        			}
        		}
        		
        		return false;
        	}
        });
		
		stateEditText[2].setOnKeyListener(new View.OnKeyListener()
        {
        	@Override
			public boolean onKey(View v, int keyCode,
        			                        KeyEvent ke)
        	{
        		if((ke.getAction() == KeyEvent.ACTION_DOWN) &&
        				(keyCode == KeyEvent.KEYCODE_ENTER))
        		{
        			statesAnswers[2] = stateEditText[2].
        					getText().toString();
        			
        			if(statesAnswers[2] != null && 
        					!statesAnswers[2].equals(""))
        			{
        				mkToast(getResources().getString(
        						R.string.answerSuccess));
        				return true;
        			}
        			else
        			{
        				mkToast(getResources().getString(
        						R.string.answerFailure));
        				return false;
        			}
        		}
        		
        		return false;
        	}
        });
		
		stateEditText[3].setOnKeyListener(new View.OnKeyListener()
        {
        	@Override
			public boolean onKey(View v, int keyCode,
        			                        KeyEvent ke)
        	{
        		if((ke.getAction() == KeyEvent.ACTION_DOWN) &&
        				(keyCode == KeyEvent.KEYCODE_ENTER))
        		{
        			statesAnswers[3] = stateEditText[3].
        					getText().toString();
        			
        			if(statesAnswers[3] != null && 
        					!statesAnswers[3].equals(""))
        			{
        				mkToast(getResources().getString(
        						R.string.answerSuccess));
        				return true;
        			}
        			else
        			{
        				mkToast(getResources().getString(
        						R.string.answerFailure));
        				return false;
        			}
        		}
        		
        		return false;
        	}
        });
		
		stateEditText[4].setOnKeyListener(new View.OnKeyListener()
        {
        	@Override
			public boolean onKey(View v, int keyCode,
        			                        KeyEvent ke)
        	{
        		if((ke.getAction() == KeyEvent.ACTION_DOWN) &&
        				(keyCode == KeyEvent.KEYCODE_ENTER))
        		{
        			statesAnswers[4] = stateEditText[4].
        					getText().toString();
        			
        			if(statesAnswers[4] != null && 
        					!statesAnswers[4].equals(""))
        			{
        				mkToast(getResources().getString(
        						R.string.answerSuccess));
        				return true;
        			}
        			else
        			{
        				mkToast(getResources().getString(
        						R.string.answerFailure));
        				return false;
        			}
        		}
        		
        		return false;
        	}
        });
	}
	
	/******************setCapitalEditText()*****************/
	private void setCapitalEditText()
	{
		capitalEditText[0].setOnKeyListener(new View.OnKeyListener()
        {
        	@Override
			public boolean onKey(View v, int keyCode,
        			                        KeyEvent ke)
        	{
        		if((ke.getAction() == KeyEvent.ACTION_DOWN) &&
        				(keyCode == KeyEvent.KEYCODE_ENTER))
        		{
        			capitalsAnswers[0] = capitalEditText[0].
        					getText().toString();
        			
        			if(capitalsAnswers[0] != null && 
        					!capitalsAnswers[0].equals(""))
        			{
        				mkToast(getResources().getString(
        						R.string.answerSuccess));
        				return true;
        			}
        			else
        			{
        				mkToast(getResources().getString(
        						R.string.answerFailure));
        				return false;
        			}
        		}
        		
        		return false;
        	}
        });
		
		capitalEditText[1].setOnKeyListener(new View.OnKeyListener()
        {
        	@Override
			public boolean onKey(View v, int keyCode,
        			                        KeyEvent ke)
        	{
        		if((ke.getAction() == KeyEvent.ACTION_DOWN) &&
        				(keyCode == KeyEvent.KEYCODE_ENTER))
        		{
        			capitalsAnswers[1] = capitalEditText[1].
        					getText().toString();
        			
        			if(capitalsAnswers[1] != null && 
        					!capitalsAnswers[1].equals(""))
        			{
        				mkToast(getResources().getString(
        						R.string.answerSuccess));
        				return true;
        			}
        			else
        			{
        				mkToast(getResources().getString(
        						R.string.answerFailure));
        				return false;
        			}
        		}
        		
        		return false;
        	}
        });
		
		capitalEditText[2].setOnKeyListener(new View.OnKeyListener()
        {
        	@Override
			public boolean onKey(View v, int keyCode,
        			                        KeyEvent ke)
        	{
        		if((ke.getAction() == KeyEvent.ACTION_DOWN) &&
        				(keyCode == KeyEvent.KEYCODE_ENTER))
        		{
        			capitalsAnswers[2] = capitalEditText[2].
        					getText().toString();
        			
        			if(capitalsAnswers[2] != null && 
        					!capitalsAnswers[2].equals(""))
        			{
        				mkToast(getResources().getString(
        						R.string.answerSuccess));
        				return true;
        			}
        			else
        			{
        				mkToast(getResources().getString(
        						R.string.answerFailure));
        				return false;
        			}
        		}
        		
        		return false;
        	}
        });
		
		capitalEditText[3].setOnKeyListener(new View.OnKeyListener()
        {
        	@Override
			public boolean onKey(View v, int keyCode,
        			                        KeyEvent ke)
        	{
        		if((ke.getAction() == KeyEvent.ACTION_DOWN) &&
        				(keyCode == KeyEvent.KEYCODE_ENTER))
        		{
        			capitalsAnswers[3] = capitalEditText[3].
        					getText().toString();
        			
        			if(capitalsAnswers[3] != null && 
        					!capitalsAnswers[3].equals(""))
        			{
        				mkToast(getResources().getString(
        						R.string.answerSuccess));
        				return true;
        			}
        			else
        			{
        				mkToast(getResources().getString(
        						R.string.answerFailure));
        				return false;
        			}
        		}
        		
        		return false;
        	}
        });
		
		capitalEditText[4].setOnKeyListener(new View.OnKeyListener()
        {
        	@Override
			public boolean onKey(View v, int keyCode,
        			                        KeyEvent ke)
        	{
        		if((ke.getAction() == KeyEvent.ACTION_DOWN) &&
        				(keyCode == KeyEvent.KEYCODE_ENTER))
        		{
        			capitalsAnswers[4] = capitalEditText[4].
        					getText().toString();
        			
        			if(capitalsAnswers[4] != null && 
        					!capitalsAnswers[4].equals(""))
        			{
        				mkToast(getResources().getString(
        						R.string.answerSuccess));
        				return true;
        			}
        			else
        			{
        				mkToast(getResources().getString(
        						R.string.answerFailure));
        				return false;
        			}
        		}
        		
        		return false;
        	}
        });
	}
	
	/**************onCreateOptionsMenu()**********/
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        
        // Inflate the menu; this adds items to the action bar
    	// if it is present.
        getMenuInflater().inflate(R.menu.quizgame, menu);
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
    
    /***************onButtonClick()*********/
    public void onButtonClick(View v)
    {
    	switch(v.getId())
    	{
    		case R.id.submitButton:
    			boolean isFieldEmpty = false;
    			
    			for(int i = 0; i < statesAnswers.length &&
    					!isFieldEmpty; i++)
    			{
    				if(statesAnswers[i] == null ||
    						capitalsAnswers[i] == null)
    				{
    					isFieldEmpty = true;
    				}
    			}
    			
    			if(isFieldEmpty)
    			{
    				mkToast(getResources().getString(
    						R.string.emptyField));
    			}
    			else
    			{
    				returnScore();
    			}
    			break;
    	}
    }
    
    /******************returnScore()**************/
    private void returnScore()
    {
    	int score = computeScore();
		mkToast(getResources().getString(
				R.string.scoreResult) + " " + score);
		
		Intent i = new Intent();
		i.putExtra("score", score);
		setResult(RESULT_OK, i);
		finish();
    }
    
    /******************computeScore()******************/
    private int computeScore()
    {
    	int result = 0;
    	
    	String stateString;
    	
    	String capitalString;
    	
    	openDatabase();
    	
    	for(int i = 0; i < stateTextView.length; i++)
    	{	
    		stateString = stateTextView[i].getText().toString();
    		
    		if(stateString != null && !stateString.equals(""))
    		{
		    	c = db.rawQuery("select * from states_capitals" +
		    			" where state = ?;", 
		    			new String[]{stateString});
		    	
		    	c.moveToFirst();
			    	
		    	if(c.getString(c.getColumnIndex("capital")).equals(
		    			capitalsAnswers[i]))
		    	{
		    		result += 10;
		    	}
    		}
	    	
    		capitalString = capitalTextView[i].
    				getText().toString();
    		
    		if(capitalString != null && !capitalString.equals(""))
    		{
		    	c = db.rawQuery("select * from states_capitals" +
		    			" where capital = ?;", 
		    			new String[]{capitalString});
		    	
		    	c.moveToFirst();
		    	
		    	if(c.getString(c.getColumnIndex("state")).equals(
		    			statesAnswers[i]))
		    	{
		    		result += 10;
		    	}
    		}
    	}
    	
    	closeDatabase();
    	return result;
    }
    
    /***************onBackPressed()***************/
    @Override
    public void onBackPressed()
    {
    	returnScore();
    	
    	super.onBackPressed();
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
