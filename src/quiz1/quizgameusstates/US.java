/**
 * @filename  src/quiz1/quizgameusstates/US.java
 * @author    Richard Szeto
 * @ccsfname  rszeto
 * @date      April 23, 2014
 * @objective Non activity class that stores the 
 *            parsing method for the file
 *            "US_states". File is parsed from
 *            internal storage.
 */

/*
 
 Author: Abbas Moghtanei 
 Date  : 07/18/13
 Program Name: US.java
 Objective: This program reads the US_states file, 
            places states and capitals into two 
            separate arrays.
 
*/

package quiz1.quizgameusstates;
 
import java.util.*;
import java.io.*;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
 
public class US
{

//****************************parseUSStates()*****************************
   public static void parseUSStates(String states[], 
		   String capitals[], Context con, String tag)
   {
      try
      {
    	 InputStream fis = con.getResources().openRawResource(R.raw.us_states);
         Scanner sc = new Scanner(fis);
         String line;
         int i = 0;
         
         sc.nextLine(); sc.nextLine(); // skip over couple of headers

         while(sc.hasNext())
         {
            line = sc.nextLine();
            String temp[] = line.split("\\s\\s+");
            if(temp.length >= 2)
            {
               if(temp.length == 2) 
               {
                  states[i]     = temp[0];
                  capitals[i] = temp[1];
               }
               else
               {
                  states[i]     = temp[0] + " " + temp[1];
                  capitals[i] = temp[2];
               }
               
               i++;
            }
         }
         
         sc.close();
         
         fis.close();
                
      }
      catch(FileNotFoundException e)
      {
    	  Log.e(tag, e.toString());
      }
      catch(IOException e)
      {
    	  Log.e(tag, e.toString());
      }
   }
}
