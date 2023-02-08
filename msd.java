import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Vector;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.table.*;
import java.util.List;


public class RMSD {

	 public static void main(String[] args) {


	        String input_file="input.txt";//read from input file 

	        BufferedReader br = null;

	        String line = "";
	        String line1 = "";
	       

	        try {

	        @SuppressWarnings("resource")// in order to fix this problem (Resource leak: 'in2' is never closed) I add this line
			BufferedReader in = new BufferedReader(new FileReader(input_file));
	        
	        @SuppressWarnings("resource")// in order to fix this problem (Resource leak: 'in2' is never closed) I add this line
	    	BufferedReader in1 = new BufferedReader(new FileReader(input_file));
	     
	 
	      
	            
	      
	        	 
	        	 String atom_data[] = new String[3];
	        	 int time_counter = 0;
	        	 int frame=0; 
	        	 
	        	
	    while((line1 = in1.readLine()) != null){ 
	        	 frame++;
	     }
	        	
	        
	        
	        double XcmReal[]=new double[frame+1];
            double YcmReal[]=new double[frame+1];
            double ZcmReal[]=new double[frame+1];
            
          

    	    double MSD[]=new double[frame];
        	  
        
	        	 
	        	 
	       
	        	 
	   while((line = in.readLine()) != null){ 
	        			
	        // System.out.println(time_counter);
	        		
	        	   
	        			   
	         time_counter++; 
	        		
	        		    
	        			    atom_data=line.split("\\s+",3);//splits the string based on whitespace
	        			   
	        			    XcmReal[time_counter-1]= Double.valueOf(atom_data[0].trim());
	        			    YcmReal[time_counter-1] = Double.valueOf(atom_data[1].trim());
	        			    ZcmReal[time_counter-1] = Double.valueOf(atom_data[2].trim());
	        			  
	        			 
	        }//while
	        		   
	    	
			for(int i=0; i<frame-1; i++){ 		 
			  for(int t=i+1; t<frame; t++){		
	     
				  
        		   
					 // MSD[t-i]=Xcm[t]-Xcm[i];
			 MSD[t-i]+=Math.pow((XcmReal[t]-XcmReal[i]),2) + Math.pow((YcmReal[t]-YcmReal[i]),2)+ Math.pow((ZcmReal[t]-ZcmReal[i]),2);  
		   //here we find MSD of all possible combination of i and t (for example for frame=4 we have 6 combination of t-i: 4-1, 4-2, 4-3, 3-1, 3-2, 2-1). in fact these are the bins of our msd and show us each array ( ex/ MSD[1] or MSD[2])   				  
	       //is filling with how many elements. (ex/ MSD[1] is filling with 4-1, 3-1, 2-1 so with 3 elemens) then in next for loop we just check MSD[i] which is devided by number of elements for each i. Therefore it is correxct that for
			//MSD[t-i] we have 6 element but for MSD[i] we have 3 element.				 				 
			                             }//t	      
	     
    	                               }//i
			    
	        	
			 for(int i=1; i<(frame); i++){ //if i=0 it so we have msd[0] which means msd[t-i=0] so is just Xcm0 -Xcm0 and will be zero. but we need msd[t-i=1]which means msd[1], so here the first i is 1 and is not same as i of for loop for ref frame
 	        		  MSD[i]=MSD[i]/((frame)-i);//since i start from 1 so we devide by /((frame)-i) which means :
 	        		 
 	        		// for(int i=1; i<(2); i++){ //if i=0 it so we have msd[0] which means msd[t-i=0] so is just Xcm0 -Xcm0 and will be zero. but we need msd[t-i=1]which means msd[1], so here the first i is 1 and is not same as i of for loop for ref frame
 	 	        		 // MSD[i]=MSD[i]/((2)-i);//since i start from 1 so we devide by /((frame)-i) which means :
 	 	        		   
 	        		  //the max number of element we fill in first element of array is max t (<frame) minus number of that element so is frame-i
 	        		
 	        		  //System.out.println(i+"    "+MSD[i]);   
 		        	log	 (Double.toString(MSD[i]),"output.txt"); //here we print the output in a separate file and not in the Console
 		        }
			
			
			        
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

	// a method for printing the output in a text file with a desired name .Each word or phrase in one line only  



     public static void log(String message, String name) {



           try {



               PrintWriter out = new PrintWriter(new FileWriter(name, true), true);



               out.write(message);



               out.write("\n");



               out.close();



           } catch (IOException e) {



             e.printStackTrace();



         }



         }  
	 
	 
	 
	 
	 
	 
	 public static void printRow2(double[] row2, String name) {
    	 try {
         PrintWriter out = new PrintWriter(new FileWriter(name, true), true);
 
    	 for (double i : row2) {
             out.write(Double.toString(i));
             out.write(",");
    	 }
    	 out.write("\n");
          out.close(); 
	 
    	 } catch (IOException e) {

             e.printStackTrace();

         }     
	 }
}