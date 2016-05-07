package com.android.cs.csandroidgame;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by uriel on 5/6/2016.
 *
 *
 */



public class Dictionary {
    public final static int MIN_LENGTH=2;
    private DAFSA taken;
    private TrieNode root;
    private ArrayList<String> wordsUsed;

    public Dictionary(InputStream wordStream) throws IOException{
        BufferedReader in = new BufferedReader(new InputStreamReader(wordStream));
        wordsUsed = new ArrayList<>();
        taken = new DAFSA();
        root = new TrieNode();
        String line = null;
        while((line = in.readLine())!=null)
        {
            String word = line.trim();
           // Log.d("ADD TEXT",word);
            if(word.length()>=MIN_LENGTH)
            {
                root.add(word);
            }

        }
    }


    public boolean isWord(String word) {
        return root.isWord(word);}


    public String getPossibleWord(String start)
    {
        String word = root.getAnyWordStartingWith(start);
        return word;
    }

    public boolean isWordTaken(String word)
    {
        return taken.contains(word);
    }


    public boolean removeWord(String word)
    {
        taken.insert(word);
        wordsUsed.add(word);
        return root.remove(word);

    }
    public boolean add(String word)
    {
        root.add(word);
        return true;
    }

    public boolean reset()
    {
        taken= new DAFSA();
        for(String temp: wordsUsed)
        {
            root.add(temp);
        }

        return true;

    }
    public String randomStart()
    {

        String start =root.getAnyWordStartingWith("");
       // Log.d("START", start.substring(0,1));

        return start.substring(0,1);
    }

}
