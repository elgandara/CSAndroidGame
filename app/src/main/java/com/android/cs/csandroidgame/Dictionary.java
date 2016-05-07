package com.android.cs.csandroidgame;

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
    public final static int MIN_LENGTH=1;
    private DAFSA taken;
    private TrieNode root;
    private ArrayList<String> wordsUsed;

    public Dictionary(InputStream wordStream) throws IOException{
        BufferedReader in = new BufferedReader(new InputStreamReader(wordStream));
        wordsUsed = new ArrayList<>();
        taken = new DAFSA();

        String line = null;
        while((line = in.readLine())!=null)
        {
            String word = line.trim();
            if(word.length()>=MIN_LENGTH)
            {
                root.add(line.trim());
            }

        }
    }


    public boolean isWord(String word) {
        return root.isWord(word);}


    public String getPossibleWord(String start)
    {
        return root.getAnyWordStartingWith(start);
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

    public boolean reset()
    {
        taken= new DAFSA();
        for(String temp: wordsUsed)
        {
            root.add(temp);
        }

        return true;

    }

}
