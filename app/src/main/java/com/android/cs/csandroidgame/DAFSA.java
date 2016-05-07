package com.android.cs.csandroidgame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by uriel on 5/6/2016.
 *
 *adapted from https://github.com/davidtweinberger/dafsa/blob/master/DAFSA.java
 *
 */
public class DAFSA {

    private String previousWord;
    private DNode root;

    private ArrayList<Triple> uncheckedNodes;
    private HashSet<DNode> minimizedNodes;

    public DAFSA()
    {
        previousWord="";
        root = new DNode();
        uncheckedNodes= new ArrayList<Triple>();
        minimizedNodes = new HashSet<DNode>();

    }

    private class Triple{
        public final DNode node;
        public final Character letter;
        public final DNode next;
        public Triple(DNode no, Character le, DNode ne){
            node = no;
            letter = le;
            next = ne;
        }

    }

    private static class DNode{
        private static int currentID = 0;

        private int id;
        private boolean last;
        private HashMap<Character, DNode> edges;

        public DNode(){
            id =new Integer(DNode.currentID);
            DNode.currentID++;
            last = false;
            edges = new HashMap<Character, DNode>();
        }

        public int getId(){return id;}
        public boolean getFinal(){return last;}

        public void setId(int i){id=i;}
        public void setLast(boolean b){ last = b;}

        public void addEdge(Character letter, DNode destination){edges.put(letter,destination);}
        public boolean containsEdge(Character letter)
        {
            return edges.containsKey(letter);
        }
        public DNode traverseEdge(Character letter){return edges.get(letter);}
        public int numEdges(){return edges.size();}

        public HashMap<Character, DNode>getEdges(){return edges;}

    }

    public void insert(String word)
    {
        if(previousWord.compareTo(word)>0)
        {
            return;
        }
        int prefix =0;
        int len = Math.min(word.length(),previousWord.length());
        for(int i=0; i<len; i++)
        {
            if(word.charAt(i)!=previousWord.charAt(i))
            {
                break;
            }
            prefix+=1;
        }
        minimize(prefix);

        DNode node;
        if(uncheckedNodes.size()==0)
        {
            node = root;
        }
        else
        {
            node = uncheckedNodes.get(uncheckedNodes.size()-1).node;
        }
        String restLetters = word.substring(prefix);

        for(int i=0;i<restLetters.length();i++)
        {
            DNode nextNode = new DNode();
            Character letter = restLetters.charAt(i);
            node.addEdge(letter,nextNode);
            uncheckedNodes.add(new Triple(node,letter,nextNode));
            node= nextNode;
        }
        node.setLast(true);
        previousWord = word;
    }
    public void minimize(int prefix)
    {


    }



}
