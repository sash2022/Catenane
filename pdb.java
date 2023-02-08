package proj1;

import java.io.BufferedReader;


import java.io.BufferedWriter;

import java.io.DataInputStream;

import java.io.File;

import java.io.FileInputStream;

import java.io.FileNotFoundException;

import java.io.FileReader;

import java.io.FileWriter;

import java.io.IOException;

import java.io.InputStreamReader;

import java.io.PrintWriter;

import java.net.URL;

import java.net.URLConnection;

import java.util.ArrayList;

import java.util.Scanner;

import java.util.StringTokenizer;

import java.util.regex.Matcher;

import java.util.regex.Pattern;
import java.util.Vector;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.table.*;
import java.util.List;
import java.util.HashMap;

public class pdb {

	
///////////////////////////////////////////////////////////////////// reading from input/////////////////////////////////////////////////////////
	private  HashMap<Integer, Character> map;//add it  for hashmap

	 public static void main(String[] args) {

	
		    pdb pdbObj= new pdb();//add it  for hashmap
		
	        String input_file="input.txt";

	        BufferedReader br = null;
	      
	        String line = "";

	         
	        try {

	        BufferedReader in = new BufferedReader(new FileReader(input_file));

	        ArrayList<String> lines = new ArrayList<String>();
	        Vector<Vector<String>> dataVector = new Vector<Vector<String>>();
	       
	        while((line = in.readLine()) != null){//read input file and put its information in to lines
                line=line.trim();
	            lines.add(line);
	        }
	        
            
	        // making  a jtable for atom list part with header//////////////
	        Vector<String> header = new Vector<String>(2);
	        header.add("Name");
	        header.add("AtmID");
	        header.add("AtmName");
	        header.add("2");
	        header.add("3");
	        header.add("X");
	        header.add("Y");
	        header.add("Z");

	        TableModel model = new DefaultTableModel(dataVector, header);//for atom list
	        JTable ATM_jtbl = new JTable(model);
	        
	        
	        
	      //make a jtable to put information of columns in connect part of pdb file to it////////////////////
	        String [][] ATM2_jtbl=new String[280][6];//////change for each sample
           
	        int l=0;
	        for (String row : lines) {
	        	int k=0;
	            row = row.trim();  //UPDATE	                 
	            List<String> A = Arrays.asList(row.split("\\s+"));//A is columns of both atom list part and connect part.
	            String B=(A.get(0)).trim();
	           
	            Vector<String> data = new Vector<String>();	
	            if (B.equals("HETATM")) {//read the atom list part
	            data.addAll(A);
	            dataVector.add(data);
	            }
	            else if(B.equals("CONECT")) {//read the connect part of pdb file
	                while(k<A.size()){
	                	ATM2_jtbl[l][k]=(A.get(k)).trim();//put it into jtable	    	            
	    	            k++;
	                    }
	    	      l++;	    	            
	            }
	          }
	        
	       
            
            pdbObj.PrintFile(ATM_jtbl,"Output.txt");//we invoke the jtable for atom list part
           
            pdbObj.GetBounds(ATM2_jtbl,"Output.txt"); //we invoke the jtable for connect part 
            
            
              
	        } catch (FileNotFoundException e) {

	            e.printStackTrace();

	        } catch (IOException e) {

	            e.printStackTrace();

	        } finally {

	            if (br != null) {

	                try {

	                    br.close();

	                } catch (IOException e) {

	                    e.printStackTrace();

	                }

	            }

	        }
	

	    }

	
	 ////////////////////////////////////////////////////////  writing into output /////////////////////////////////////////////////////
	 ////////////////////////////////////// atom list part
	 
	 // a method for printing the jtable in a text file with a desired name    

     public void PrintFile(JTable TBname, String FileName) { 
    	 
    	
    	 
           try {

        	         	   
               PrintWriter out = new PrintWriter(new FileWriter(FileName, true), true);//it will write in to output file
               String message="";
               String message1="";
               String message2="";
               int CNT=0;
               int MolID=0;
               int Tcount=1;
               map = new HashMap<Integer, Character>(); 
               
               for(int i=0; i<280; i++){
            	   
            	       if(Tcount==8){
         	    	      Tcount=1;
         	           }
            	       
            	       if(CNT%7==0){
            	    	  MolID++;
            	       }
            		  
            	                   	       
            	       Object S1=TBname.getModel().getValueAt(i, 1);
                       out.write(S1.toString());
                       out.write("  ");
                       
                       message= String.valueOf(MolID);
                       out.write(message);
                       out.write("  ");
                       
                       
                       switch (Tcount) {
                       case 1:  message1 = "1";
                                message2= "-0.4";
                                break;
                       case 2:  message1 = "2";
                                message2= "0.14";
                                break;
                       case 3:  message1 = "2";
                                message2= "0.14";
                                break;
                       case 4:  message1 = "3";
                                message2= "0.03";
                                break;
                       case 5:  message1 = "3";
                                message2= "0.03";
                                break;
                       case 6:  message1 = "3";
                                message2= "0.03";
                                break;
                       case 7:  message1 = "3";
                                message2= "0.03";
                                break;
                       default: message1 = "Invalid Type";
                                break;
                   }
                       
                       out.write(message1);
                       out.write("  ");
                       
                       out.write(message2);
                       out.write("  ");
                       
                       //////////////////////////////////////////////
                       
                       
                       Object S2=TBname.getModel().getValueAt(i, 2);
                       //////////////////////////////////////////
                                       
                       
                       Object S3=TBname.getModel().getValueAt(i, 5);
                       out.write(S3.toString());
                       out.write("  ");
                       
                       Object S4=TBname.getModel().getValueAt(i, 6);
                       out.write(S4.toString());
                       out.write("  ");
                       
                       Object S5=TBname.getModel().getValueAt(i, 7);
                       out.write(S5.toString());
                       out.write("\n");
                       CNT++;
            	       Tcount++;
            	      
            	       int k=Integer.valueOf(S1.toString());//atom number
            	       char m=(S2.toString()).charAt(0);// atom type (o c c h h h h)
            	               
             	       map.put(k,m);
            	  }
         
              // System.out.println(map.values()); 
            // System.out.println(map.keySet()); 
               out.close();
  
           } catch (IOException e) {

             e.printStackTrace();

         }

         }   
	
     
     
   //////////////////////////////////////////////bond list part
	
     public void GetBounds( String [][] Bname, String FileName) { 
    	 
    	// Create 2-dimensional array.
    	 int CNT=1;
         int[][] Bonds = new int[281][281];//--------------------------------------------change
         char[][] Bonds2 = new char[281][281];
         int index1;
         int index2;
        
         int row = Bname.length;
         
      for(int i=0; i<280; i++){
        	 int j=2;
        	 int n=Bname[i].length; 
        	
        	  if(Bname[i][1]!=null){
         		 index1= Integer.valueOf(Bname[i][1]);}//is second column of connect part but since forexample for atom 1 it has one connection with 2 and another connection with 10 so we consider 1 two times in index 1
                  else break;
                  
                  
           while(j<n){
               
                 
                 if(Bname[i][j]!=null){
                 index2= Integer.valueOf(Bname[i][j]);}//is the other columns in front of second column of connect part
                 else break;
                
                 
                 if(Bonds[index2][index1]==1){
                 Bonds[index1][index2]=2;
                 }
                 else {Bonds[index1][index2]=1;}
                 
                 
               //  System.out.println(map.get(index2)); 
               //  if(Bonds[map.get(index2)][map.get(index1)]==1){
               //      Bonds[index1][index2]=2;
               //      }
                // if(Bname[i][j]!=null){
               //  index3=map.get(i);
                // index4=map.get(j);
                // System.out.println(index4); 
               //  }
               //  else break;
                 
                 
                 j++;
                 
           }
         }
	      //  System.out.println(Arrays.deepToString(Bonds));
      
    
      
      
      /////////////////////////////////////////////////////////////////////////
      
      
	        append("\n"+ "Bonds"+ "\n",FileName);// invoke append
	     String Type="";
	     
         for(Integer k=1; k<=280; k++){
             for(int l=1; l<=280; l++){
            	 
            	 if (Bonds[l][k]==1){//it means if we have bond btw l and k? the answer can be 1 mean yes or 0 means no
            		 
            		
            	//	 map.keySet()
            		 //System.out.println(Bonds[map.get(k)][map.get(l)]); 
            		  // if(Bonds[map.get(k)][map.get(l)]==1){
                //           Bonds[index1][index2]=2;
                //           }
                //           else {Bonds[index1][index2]=1;} 
            		 
            		 if ((map.get(k)=='O'&& map.get(l)=='C')||(map.get(l)=='O'&& map.get(k)=='C'))            			 	
            				 Type= "1";
            		 
            		 
            		 else if (map.get(k)=='C' && map.get(l)=='C')
                			 	 Type= "2";
            	 
                			 else {Type= "3";}
                			 
            	
            		
            		 printRow(CNT,Type,k,l,FileName);//invoke printRow 
            		 log("\n",FileName);
            		 CNT++;
            	 }
             }

         }

         }

     
     public static void printRow(int CNT, String AtmTyp, int atom1, int atom2, String FileName) { 
    	 
    	 String Str=String.valueOf(CNT)+"   "+AtmTyp+"   "+String.valueOf(atom1)+"   "+String.valueOf(atom2);
    	 append(Str,FileName);
     }

     
     // a method for printing the output in a text file with a desired name 

     public static void log(String message, String name) { 

           try {

               PrintWriter out = new PrintWriter(new FileWriter(name, true), true);

               out.write(message);

               out.close();

           } catch (IOException e) {

             e.printStackTrace();

         }

         }

     // a method to append new strings to the text file 

     public static void append( String content , String name)

     {    

       try{

         //Specify the file name and path here

         File file =new File(name);


         /* This logic is to create the file if the

          * file is not already present

          */

         if(!file.exists()){

            file.createNewFile();

         }


         //Here true is to append the content to file

         FileWriter fw = new FileWriter(file,true);

         //BufferedWriter writer give better performance

         BufferedWriter bw = new BufferedWriter(fw);

         bw.write(content);

         //Closing BufferedWriter Stream

         bw.close();


     //System.out.println("Data successfully appended at the end of file");


       }catch(IOException ioe){

          // System.out.println("Exception occurred:");

          ioe.printStackTrace();

         }

     }

    


     
     
     
}