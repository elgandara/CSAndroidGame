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
 * connect the taken with the DAFSA
 *
 *
 */



public class Dictionary {
    public final static int MIN_LENGTH=1;
    private DAFSA taken;
    private ArrayList<String> words;

    public Dictionary(InputStream wordStream) throws IOException{
        BufferedReader in = new BufferedReader(new InputStreamReader(wordStream));
        words = new ArrayList<>();
        taken = new DAFSA();

        String line = null;
        while((line = in.readLine())!=null)
        {
            String word = line.trim();
            if(word.length()>=MIN_LENGTH)
                words.add(line.trim());
        }
    }


    public boolean isWord(String word){return words.contains(word);}


    public String getPossibleWord(String start)
    {

        return null;
    }

    public boolean isWordTaken(String word)
    {
        return taken.contains(word);
    }

    public boolean removeWord(String word)
    {
        taken.insert(word);
        return words.remove(word);

    }
    public boolean addWord(String word)
    {
        return words.add(word);
    }
    public boolean reset()
    {
        taken= new DAFSA();
        return true;

    }

}
