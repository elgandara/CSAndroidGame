package com.android.cs.csandroidgame;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


public class TrieNode {
    private HashMap<String, TrieNode> children;
    private boolean isWord;

    public TrieNode() {
        children = new HashMap<>();
        isWord = false;
    }

    public void add(String s) {

        multitask(s,true);

        //   add(s, this);
    }

    public void add(String s, TrieNode level)
    {

        if(level.children.containsKey(s.charAt(0)+""))
        {
            if(s.length()==1)
            {
                TrieNode other = level.children.get(s.charAt(0)+"");
                other.isWord=true;
                level.children.put(s,other);
                return;
            }
            add(s.substring(1),level.children.get(s.charAt(0)+""));
        }
        else
        {
            TrieNode other = new TrieNode();
            if(s.length()==1)
            {
                other.isWord=true;
                level.children.put(s.charAt(0)+"",other);
                return;
            }
            level.children.put(s.charAt(0)+"",other);
            add(s.substring(0)+"",level.children.get(s.charAt(0)+""));
        }
        return;
    }
    public boolean isWord(String s) {

        return isWordHelper(this,s);
    }

    public boolean isWordHelper(TrieNode temp,String s)
    {
        //search top level for node that matches first character in key
        //if none return null
        //else
        //if the matched character is \0 return true
        //else
        //move to subtrie that matched char
        //move to next char in key
        if(s.length()<1 || s.equals(""))
        {
          //  Log.d("ISWORD:FALSE", s);
            return false;
        }
        if(!temp.children.containsKey(s.charAt(0)+""))
        {
            //Log.d("ISWORD:FALSE", s);
            return false;
        }
        else if(s.length()==1 && temp.children.get(s.charAt(0)+"").isWord)
        {
            //Log.d("ISWORD:TRUE", s);
            return true;
        }
        else
        {
            //Log.d("ISWORD:LOOKING", s);
            TrieNode other = temp.children.get(s.charAt(0)+"");
            return isWordHelper(other,s.substring(1));
        }
    }

    public String getAnyWordStartingWith(String s) {

        if("".equals(s))
        {
            s= getRandomWord(this);
            //Log.d("GETWORD", "("+s+")");
            return s;
        }
        // Log.d("GETWORD", "Before");
        String temp = possibleWord(s);

        //Log.d("GETWORD:Possible", "("+temp+")");
        return temp;
    }

    public String possibleWord(String s)
    {

        TrieNode current = new TrieNode();
        current = this;
        String nextWord=s;
        //Log.d("GETWORD: Nexword", "("+nextWord+")");

        while(!s.isEmpty() || !"".equals(s))
        {
           // Log.d("GETWORD: Currently", "("+s+")");
            if(current.children.containsKey(s.charAt(0)+""))
            {
              //  Log.d("GETWORD: FOUNDTRI", "("+s.charAt(0)+")");
                current = current.children.get(s.charAt(0)+"");
                s=s.substring(1);
            }
            else
            {
                //Log.d("GETWORD: RElse", "("+s+")");
                return null;
            }
        }


        while(!isWord(nextWord))
        {
            // Log.d("GETWORD: Random", "("+nextWord+")" +(nextWord.length()));
            //Log.d("GETWORD","POSSIBLE");
            printKeySet(current);

            nextWord+=getRandomWord(current);
            //Log.d("GETWORD: Random", "(" + nextWord + ")" + isWord(nextWord) + nextWord.length() + "CHAR: " + nextWord.charAt(nextWord.length()-1));
            current = current.children.get(nextWord.charAt(nextWord.length()-1)+"");
            //Log.d("GETWORD","AFTER");
            printKeySet(current);

        }

        //Log.d("GETWORD: RETURN", "("+nextWord+")"+isWord(nextWord));
        return nextWord;

    }
    public boolean remove(String other)
    {


        if(isWord(other))
        {
            multitask(other,false);
            return true;
        }
        else
        {
            return false;
        }

    }
    public boolean multitask(String other, boolean t)
    {
        TrieNode current = new TrieNode();
        current = this;
        for(;;)
        {
            if(current.children.containsKey(other.charAt(0)+""))
            {
                if(other.length()==1)
                {
                    // Log.d("ADD Word", s);
                    TrieNode temp = current.children.get(other);
                    temp.isWord=t;
                    current.children.put(other,temp);
                    break;
                }
                else
                {
                    //Log.d("ADD: found Child", s);
                    current=current.children.get(other.charAt(0)+"");
                    other=other.substring(1);
                }

            }
            else{
                TrieNode temp= new TrieNode();
                if(other.length()==1)
                {
                    //  Log.d("ADD: new TrieWord", s);
                    temp.isWord=t;
                    current.children.put(other.charAt(0)+"",temp);
                    break;
                }
                ///Log.d("ADD: new TrieNode", s);
                current.children.put(other.charAt(0)+"",temp);
                current=current.children.get(other.charAt(0)+"");
                other=other.substring(1);
            }
        }

        return true;
    }

    public String getRandomWord(TrieNode last)
    {

        //Log.d("GETWORD: INRAND","POSSIBLE");
        printKeySet(last);
        Random rand = new Random();
        List<String> keys = new ArrayList<String>(last.children.keySet());
        String rValue = keys.get(rand.nextInt(keys.size()));

        //Log.d("GETWORD: INRAND","AFTER");
        printKeySet(last.children.get(rValue+""));

        //Log.d("GETWORD: INRAND","RETURN: "+rValue);
        return rValue;
    }
    public void printKeySet(TrieNode other)
    {
        Log.d("GETWORD", "printKeySet: " + other.children.keySet().toString());
    }

    public String getGoodWordStartingWith(String s) {
        return "HELLO";

    }

}
