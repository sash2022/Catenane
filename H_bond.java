
//Input file should be the dump file for water and input file2 is peo dump file.
//So natom2, counter2 and all variables with 2 at the end of its name is for peo 
//and natom, counter and ... is for water


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


public class endDistance {

	 public static void main(String[] args) {


	       // String input_file="input.txt";//read from input file 

	        BufferedReader br = null;

	        String line = "";
	        String line2 = "";
	        String line3 = "";
	        String line4 = "";
	        String partOne="";
	        String partTwo= "";
	        try {
	        	/*
	            // String input_file="input.txt";//read from input file
	       	    // for (int I=0; I<5; I++) {
	       		   for (int I=0; I<2; I++) {
	       	        String [] input_file=new String [5]; //read from input file
	       	        input_file[0]="input.txt";
	       	        input_file[1]="input2.txt";
	       	       // input_file[2]="input3.txt";
	       	     //  input_file[3]="input4.txt";
	       	     //  input_file[4]="input5.txt";
	       	      */  
	       	     String input_file="input.txt";//read from input file  
	       	     String input_file2="input2.txt";//read from input file     
	       	        
	       	        
	       	        @SuppressWarnings("resource")// in order to fix this problem (Resource leak: 'in2' is never closed) I add this line
	       			BufferedReader in = new BufferedReader(new FileReader(input_file)); //will be used for counting the entire number of frames which is the same for the two dump files
	       	        @SuppressWarnings("resource")
	       			BufferedReader in2 = new BufferedReader(new FileReader(input_file));//will be used for the all parts of dump file like header and coordinate in the peo (input file)
	       	         
	       	       @SuppressWarnings("resource")
	       			BufferedReader in3 = new BufferedReader(new FileReader(input_file2));//will be used for just count atom number in the water (input file2)
	       	    @SuppressWarnings("resource")
     			BufferedReader in4 = new BufferedReader(new FileReader(input_file2));//will be used for the all parts of dump file like header and coordinate in the water (input file2)
     	         
	       	     
                  
		       	     // System.out.println(partOne + "\t" + partTwo);//put lines in front of each other
		       	  // System.out.println(partOne + "\n" + partTwo);//put lines after each other
		       	 
	       	     
	       	        

	       	     
	       	        
	       	    // String english = "/home/path-to-file/english";//we can give the address instead of just being in the address and then run it
	       	//  String french = "/home/path-to-file/french";
	       	
	        
      	   
           
	        

             int type2=0;
             int type7=0;
       			           					           	
	           	double Xoverall=0;
	           	double Yoverall=0;
	           	double Zoverall=0;
	           	
	        
	        	 String xdim[] = new String[2];
	        	 String ydim[] = new String[2];
	        	 String zdim[] = new String[2];
	        	 String atom_data[] = new String[8];
	        	 String atom_data2[] = new String[8];
	        	 
	        	 String beg_time = "ITEM: TIMESTEP";
	        	// String beg_time2 = "ITEM: TIMESTEP";
	        	 int tstep = 0, natoms = 1; 
	        	 int tstep2 = 0, natoms2 = 1; 
	        	 int wholeFrameNumber=0; //is for both water and peo 
	        	int wholeFrameNumber2=0;
	        	 int id, type,mx,my,mz;
	        	 double x,y,z,dx,dy,dz,rmsd,charge;
	        	 double xlo,xhi,ylo,yhi,zlo,zhi;
	        	 double Lx = 1.0,Ly = 1.0,Lz = 1.0;
	        	 
	        	 int id2, typeII,mx2,my2,mz2;
	        	 double x2,y2,z2,dx2,dy2,d2;
	        	 
	        	 double Lx2 = 1.0,Ly2 = 1.0,Lz2 = 1.0;
	        	     
	        	 int counter = 0;
	        	 int counter2 = 0;
	        	 
	        	 int time_counter = 0;
	        	 int atom_counter = 0;
	        	 int time_counter2 = 0;
	        	 int time_count = 0;
	        	 int time_count2 = 0;
	        	 int atom_counter2 = 0;
	        	 int Hcounter=0;
	        	 int Ocounter=0;
	        	 boolean array_created = false;


	        	
	        	 
	        	   double Xoverallc=0.0; //Thank God by putting Xoverallc out of while loop
	        	 //I could add all Xoverall (while reading x and inside the while loop)
	        	//to the Xoverallc and have summation of all Xoverall in it so go to next step (Xcm)
	        	   double Yoverallc=0.0;
	        	   double Zoverallc=0.0;
	        	   double Total_Massc=0.0; 
	        	 
	        	   
		             
		    	     //double distanceSqr=0;
		    	    // double distance=0;
		    	     int HbondCounter=0;
		    	    
		    	     
		    	   
	       
		    	     
		  	    	 
		    	     
		    	     
		    	//////first while loop to just count number of frames in the whole file//////////////////////
		  		   while((line = in.readLine()) != null){ 	        	
		  	        	
		  	        			//if(line.equals(beg_time)) {counter = 0;
		  	        			//if(line.equals(beg_time))HbondCounter=0;// to start counting Hbond from beginning of each frame.
		  	        	if(line.equals(beg_time)) {
		  	        		counter = 0; //for peo
		  	        		//HbondCounter=0;// to start counting Hbond from beginning of each frame.
		  	        	
		  	        	}
		  	        	
		  	        			if(counter == 1) {
		  	        			   // tstep = Integer.valueOf(line);
		  	        			   // System.out.println("tstep : " + tstep);
		  	        			  
		  	        			    time_counter++; 
		  	        			  wholeFrameNumber++;
		  	        			   // System.out.println("tstep : " + time_counter);
		  	        			  //  atom_counter = 0;
		  	        			   
		  	        			} 
		  	        			
		  	        			
		  	        			if(counter == 3){
			        			    natoms = Integer.valueOf(line);
			        			    //System.out.println("natoms : " + natoms);
			        		/*	    if(!array_created){
			        				for (int i = 0; i < frame; i++) {
			        				    xarr[i] = new double[natoms];
			        				    yarr[i] = new double[natoms];
			        				    zarr[i] = new double[natoms];
			        				}
			        				array_created = true;  //array created, do not create again
			        			    }*/

			        			}
		  	        			counter++;  //count until next time step 
		  	        			
		  		   }
		  		   
		  		   
		  		   
		  		   double [] Xmin= new double [time_counter+1]; //time_counter+1 is correct bc in an array always the index starts with zero so we should consider the size of it one index more than the size we decided
		  		   double [] Ymin= new double [time_counter+1]; 
		  		   double [] Zmin= new double [time_counter+1]; 
		  		     Xmin[time_counter-1]=10000.0; //for reading from first frame
		  		     Xmin[time_counter]=10000.0;  // for reading from second frame
		  		     
		         	 Ymin[time_counter-1]=10000.0;
		         	Ymin[time_counter]=10000.0;
		         	
		         	 Zmin[time_counter-1]=10000.0;
		         	Zmin[time_counter]=10000.0;
		         	 
		  		   
		  		   double [] Xmax= new double [time_counter+1]; 
			  	   double [] Ymax= new double [time_counter+1]; 
			  	   double [] Zmax= new double [time_counter+1]; 
		  		     Xmax[time_counter]=0.0;
		         	 Ymax[time_counter]=0.0;
		         	 Zmax[time_counter]=0.0;
		  		   
		         	 
		  		   
		  		   
		  			//////second while loop to just count atom number in the second input file(PEO)//////////////////////
		  		   while((line4 = in4.readLine()) != null){ 	        	
		  	        	
		  	        			//if(line.equals(beg_time)) {counter = 0;
		  	        			//if(line.equals(beg_time))HbondCounter=0;// to start counting Hbond from beginning of each frame.
		  	        	if(line4.equals(beg_time)) {
		  	        		counter2 = 0; 
		  	        		//HbondCounter=0;// to start counting Hbond from beginning of each frame.
		  	        	
		  	        	}
		  	        	
		  	        		
		  	        	
		  	      	if(counter2 == 1) {
	        			   // tstep = Integer.valueOf(line);
	        			   // System.out.println("tstep : " + tstep);
	        			  
	        			    time_counter2++; 
	        			  wholeFrameNumber2++;
	        			   // System.out.println("tstep : " + time_counter);
	        			  //  atom_counter = 0;
	        			   
	        			} 
	        			
		  	        			
		  	        			if(counter2 == 3){
			        			    natoms2 = Integer.valueOf(line4);
			        			  //  System.out.println("natoms2 : " + natoms2);
			        		    
			        			}
		  	        			
		  	        			
		  	        			if(counter2 > 8){
		  	  		        	// System.out.println(line1);
		  	  		        	
		  	        				  atom_data2=line4.split("\\s+",8);
						        		id2 = Integer.valueOf(atom_data2[0]);
					       			   
					       			    typeII = Integer.valueOf(atom_data2[1]);
					       			    x2 = Double.valueOf(atom_data2[2]);
					       			    y2 = Double.valueOf(atom_data2[3]);
					       			    z2 = Double.valueOf(atom_data2[4]);
					       			    
		  	        				
					       			  if (x2 < Xmin[time_counter2] ) {// to have the maximum of X of peo to find water molecules in an area 5A larger than Xmax of peo
								        	 Xmin[time_counter2]=x2;
								         }
								        	
								         if (y2 < Ymin[time_counter2] ) {
								        	 Ymin[time_counter2]=y2;
								         }
								         
								         if (z2 < Zmin[time_counter2] ) {
								        	 Zmin[time_counter2]=z2;
								         }
								         
								        
					       			    
		  	        				
		  	        			   if (x2 > Xmax[time_counter2] ) {// to have the maximum of X of peo to find water molecules in an area 5A larger than Xmax of peo
							        	 Xmax[time_counter2]=x2;
							         }
							        	
							         if (y2 > Ymax[time_counter2] ) {
							        	 Ymax[time_counter2]=y2;
							         }
							         
							         if (z2 > Zmax[time_counter2] ) {
							        	 Zmax[time_counter2]=z2;
							         }
						       			
		// System.out.println(time_counter2+"    "+Xmin[time_counter2]+"   "+Xmax[time_counter2]+"    "+Ymin[time_counter2]+"   "+Ymax[time_counter2]+"    "+Zmin[time_counter2]+"   "+Zmax[time_counter2]); 
		  	  	        		
						       			
		  	         			
		  	  	        		 }
						        
		  	        			counter2++;  //count until next time step 
		  	        				
		  		   }
		  		   
		  		   
		  		    
		             
		           
		             double X1[]=new double [natoms+1];
		             double Y1[]=new double [natoms+1];
		             double Z1[]=new double [natoms+1];
		                
		             double X2[]=new double [natoms2+1];//wholeFrameNumber are the same for both input files but number of atoms for water input file is bigger. But since we want to have 
		             //distance calculating from both X1 and X2 so the array size of both should be the same therefore we consider the highesst amount of atom number for both of arrays which is atom number of water
		             double Y2[]=new double [natoms2+1];
		             double Z2[]=new double [natoms2+1];
		  	       	 
		             double X1ordered[]=new double [natoms+1];
		             double Y1ordered[]=new double [natoms+1];
		             double Z1ordered[]=new double [natoms+1];
		                
		             double X2ordered[]=new double [natoms2+1];//wholeFrameNumber are the same for both input files but number of atoms for water input file is bigger. But since we want to have 
		             //distance calculating from both X1 and X2 so the array size of both should be the same therefore we consider the highesst amount of atom number for both of arrays which is atom number of water
		             double Y2ordered[]=new double [natoms2+1];
		             double Z2ordered[]=new double [natoms2+1];
		             
		             double X2input[][]=new double [wholeFrameNumber+1][natoms2+1];//wholeFrameNumber are the same for both input files but number of atoms for water input file is bigger. But since we want to have 
		             //distance calculating from both X1 and X2 so the array size of both should be the same therefore we consider the highesst amount of atom number for both of arrays which is atom number of water
		             double Y2input[][]=new double [wholeFrameNumber+1][natoms2+1];
		             double Z2input[][]=new double [wholeFrameNumber+1][natoms2+1];
		             
		           	 double distance[][]=new double [natoms+1][natoms2+1]; //we consider based of number of atoms of first input file which is always water which has larger atom number (natoms)
		    	    double distanceSqr[][]=new double [natoms+1][natoms2+1];
		  	      
		  	 
		    
		    	    
		    	    
		    	    double [] dist= new double[wholeFrameNumber+1];
		    	    Integer Hbond[]=new Integer[wholeFrameNumber+1]; 
		    	    
		    	  while ((line2=in2.readLine())!=null) {
		    	     	
		///////////////////////////input2////////////////////////////////////////////////////////////////////////// 
		    		       line3=in3.readLine();	 
		    	   if ( line3 !=null) {//since input2 is smaller than input then reading line by line of the two will cause input2 reach to null so we use this to prevent null.
		    		   
		    		  
		    			   
		    	            if(line3.equals(beg_time)) {//this sentence will start the counter from beginning of the input file and at starting of each frame it also makes the counter zero 
				        		counter2 = 0; 
				        		HbondCounter=0;
				        		 atom_counter2 = 0; 
				        		 Ocounter=0;
				        		 //Hcounter=0;
				        		 time_count2++; 
				        	}
				    	  
		    	            
				    	 	if(counter2 == 1) {
			    			    tstep2 = Integer.valueOf(line3);
			    			   // System.out.println("tstep : " + tstep2);
			    			
			    			}
				        	

				    	 	
				    		if(counter2 == 5){
		        			    
		        			    xdim = line3.split("\\s",2);
		        			     for (int i=0; i < xdim.length; i++){
		        			     	//System.out.println(xdim[i]);
		        			    } 
		        			   
		        			    //f = Double.parseDouble("200");
		        			    //f = Double.parseDouble(xdim[0]);
		        			    xlo = Double.valueOf(xdim[0]);
		        			    xhi = Double.valueOf(xdim[1]);
		        			    Lx = xhi-xlo;
		        			    //System.out.println("xlo, xhi : " + xlo + ", " + xhi);
		        			    
		        			}

		        			if(counter2 == 6) {
		        			    
		        			    ydim = line3.split("\\s",2);
		        			     for (int i=0; i < ydim.length; i++){
		        			     	//System.out.println(ydim[i]);
		        			     } 
		        			   
		        			    ylo = Double.valueOf(ydim[0]);
		        			    yhi = Double.valueOf(ydim[1]);
		        			    Ly = yhi-ylo;
		        			    //System.out.println("ylo, yhi : " + ylo + ", " + yhi);
		        			  
		        			}

		        			if(counter2 == 7) {
		        			    
		        			    zdim = line3.split("\\s",2);
		        			     for (int i=0; i < zdim.length; i++){
		        			     	//System.out.println(zdim[i]);
		        			     } 
		        			   
		        			    zlo = Double.valueOf(zdim[0]);
		        			    zhi = Double.valueOf(zdim[1]);
		        			    Lz = zhi-zlo;
		        			    //System.out.println("zlo, zhi : " + zlo + ", " + zhi);
		        			    
		        			}

		        			
							         	 X2[atom_counter2]=0;
							         	  Y2[atom_counter2]=0;
							         	  Z2[atom_counter2]=0; 	 
					       				
					       				
							         	
				    	 	       
				    	 	      int ID2=0;
					        // if(counter2 > 8 && counter2 <= (9+natoms2)){	
					         if(counter2 > 8 ){		
					        	    atom_data2=line3.split("\\s+",8);
					        		id2 = Integer.valueOf(atom_data2[0]);
				       			    ID2=id2;
				       			    typeII = Integer.valueOf(atom_data2[1]);
				       			    x2 = Double.valueOf(atom_data2[2]);
				       			    y2 = Double.valueOf(atom_data2[3]);
				       			    z2 = Double.valueOf(atom_data2[4]);
				       			    mx2 = Integer.valueOf(atom_data2[5]);
				       			    my2 = Integer.valueOf(atom_data2[6]);
				       			    mz2 = Integer.valueOf(atom_data2[7].trim());
				       			// System.out.println(time_counter2+"  "+counter2+"   "+x2);
				       		
				       			//System.out.println(x2);
				       		
				       			 
				       			 atom_counter2++;
				       			 //type7=0;
					        		
				       			 
				       			// if (Idi<minStartAtm) {//find minimum of Idi 
					        	//      minStartAtm=Idi;			        			     
				       	        //      }//if
				       			
					        		
					        
					       
				       		
				       		  //double total_DimensionX2=((x2 + mx2)*Lx);
				                  //double total_DimensionY2=((y2 + my2)*Ly);
				                  //double total_DimensionZ2=((z2 + mz2)*Lz);
				                  
				                  double total_DimensionX2=x2*Lx;
				                  double total_DimensionY2=y2*Ly;
				                  double total_DimensionZ2=z2*Lz;
			                     
				                  
					        
					     // if(typeII==7){//for peo at interface of water/toluene atom type is different than atom type for peo at bulk.
					    	  if(typeII==3){ 		
					    	          Ocounter++;
					        		  // if (counter2<=natoms2+8) { //to read one frame 
					        		  X2[atom_counter2]=total_DimensionX2;
						         	  Y2[atom_counter2]=total_DimensionY2;
						         	  Z2[atom_counter2]=total_DimensionZ2;
						         	/*
						         	 X2[n]=x2;
						         	  Y2[n]=y2;
						         	  Z2[n]=z2;
						         	  */
						         	
					        		    X2ordered[Ocounter]=X2[atom_counter2];
	     				            	Y2ordered[Ocounter]=Y2[atom_counter2];
	     				              	Z2ordered[Ocounter]=Z2[atom_counter2];
						         	  //System.out.println(X2ordered[n]+"  "+n);
	     				              
	     				              //	System.out.println(time_counter2+"          "+Ocounter+"    "+X2ordered[Ocounter]);
					        	  }//if(typeII==7){
					          }//if(counter2 > 8){	 
					         //System.out.println(time_count2+"    "+Ocounter+"   "+X2ordered[Ocounter]);
	
					         
					         //Thank GOD!!!!!!
					         //GOD helped me using this part I can prepare the X2ordered[Ocounter] for the outside of while loop of input2 (I mean for use it in the while loop of input)
					            X2input[time_count2][Ocounter]=X2ordered[Ocounter];
					            Y2input[time_count2][Ocounter]=Y2ordered[Ocounter];
					            Z2input[time_count2][Ocounter]=Z2ordered[Ocounter];
					          //  System.out.println(time_counter2+"    "+Ocounter+"   "+  X2input[time_counter2][Ocounter]);
					      
					            
					         
					            
					         counter2++;
		       		}//if line3!=null 
		    
		    	   
		    	   
		    	   
		    	   
		    	   
		    		
		   ///////////////////////input///////////////////////////////////////////////////////////////////////
		    	
		    	    	      if(line2.equals(beg_time)) {
				      	        		counter = 0; 
				      	        		HbondCounter=0;// to start counting Hbond from beginning of each frame.
				      	        		 atom_counter = 0;
				      	        		 Hcounter=0;
				      	        		 time_count++;
				      	        	 }
		    	 
				      	        	
				      	       if(counter == 1) {
				      	        			    tstep = Integer.valueOf(line2);
				      	        			   // System.out.println("tstep : " + tstep);
				      	        			  
				      	        			   
				      	        			    
				      	        			    // System.out.println("tstep : " + time_counter);
				      	        			
				      	        			  //System.out.println(atom_counter);
				      	        			}
				      	        	
				      	        	

				      	      if(counter == 3){
				      	        			    natoms = Integer.valueOf(line2);
				      	        			   // System.out.println("natoms : " + natoms);
				      	        		
				      	        			}
				      	        			

				      	      if(counter == 5){
				      	        			    
				      	        			    xdim = line2.split("\\s",2);
				      	        			     for (int i=0; i < xdim.length; i++){
				      	        			     //System.out.println(xdim[i]);
				      	        			    } 
				      	        			   
				      	        			    //f = Double.parseDouble("200");
				      	        			    //f = Double.parseDouble(xdim[0]);
				      	        			    xlo = Double.valueOf(xdim[0]);
				      	        			    xhi = Double.valueOf(xdim[1]);
				      	        			    Lx = xhi-xlo;
				      	        			    //System.out.println("xlo, xhi : " + xlo + ", " + xhi);
				      	        			    
				      	        			}
				      	        			
				      	        			

				      	      if(counter == 6) {
				      	        			    
				      	        			    ydim = line2.split("\\s",2);
				      	        			     for (int i=0; i < ydim.length; i++){
				      	        			     	//System.out.println(ydim[i]);
				      	        			     } 
				      	        			   
				      	        			    ylo = Double.valueOf(ydim[0]);
				      	        			    yhi = Double.valueOf(ydim[1]);
				      	        			    Ly = yhi-ylo;
				      	        			    //System.out.println("ylo, yhi : " + ylo + ", " + yhi);
				      	        			  
				      	        			}
				      	        			
				      	        			

				      	       if(counter == 7) {
				      	        			    
				      	        			    zdim = line2.split("\\s",2);
				      	        			     for (int i=0; i < zdim.length; i++){
				      	        			     	//System.out.println(zdim[i]);
				      	        			     } 
				      	        			   
				      	        			    zlo = Double.valueOf(zdim[0]);
				      	        			    zhi = Double.valueOf(zdim[1]);
				      	        			    Lz = zhi-zlo;
				      	        			   // System.out.println("zlo, zhi : " + zlo + ", " + zhi);
				      	        			
				      	        			}

				      	        			
				      	        			
				      	        		   //to read one frame
								    	 	 // for (int i=1; i<= wholeFrameNumber;  i++){   
								    	 		  //   if (time_counter==i) {	
				      	        			//for evacuating the array after reading each frame
									       			   X1[atom_counter]=0;
						      				           Y1[atom_counter]=0;
				     				              	   Z1[atom_counter]=0;	
				     				              		
				     				              
				     				            	   
				      	        			
				      	        		           int ID=0;//define it to put id in it so we have id out of if(counter > 8){ 
				      	        		        
					      	     if(counter > 8){//here we can read just coordinates of each frame.
					      	        		
					      	        			    atom_data=line2.split("\\s+",8);//splits the string based on whitespace
					      	        			  
					      	        			    id = Integer.valueOf(atom_data[0]);
					      	        			    ID=id;
					      	        			    type = Integer.valueOf(atom_data[1]);
					      	        			    x = Double.valueOf(atom_data[2]);
					      	        			    y = Double.valueOf(atom_data[3]);
					      	        			    z = Double.valueOf(atom_data[4]);
					      	        			    mx = Integer.valueOf(atom_data[5]);
					      	        			    my = Integer.valueOf(atom_data[6]);
					      	        			    mz = Integer.valueOf(atom_data[7].trim());  //may be a new line character after this
					      	        			  // System.out.println(x);
					      	        			  atom_counter++;	        		
					      	   	  	                //type2=0;
					      	        			 	 
					      	        			
					      	        if (x<=Xmax[time_count]+5 && y<=Ymax[time_count]+5 && z<=Zmax[time_count]+5 && x>=Xmin[time_count]-5 && y>=Ymin[time_count]-5 && z>=Zmin[time_count]-5 ) { 
					      	        				   			  
					      	        			    //we should consider image flag(ix, iy, iz) bc the x1, y1, z1 are just a portion of box dimension (lmax- lmin)
					      	           // double total_DimensionX=((x + mx)*Lx);
			      			              //  double total_DimensionY=((y + my)*Ly);
			      			               // double total_DimensionZ=((z + mz)*Lz); 
			      			                
			      			                double total_DimensionX=x*Lx;
			      			                double total_DimensionY=y*Ly;
			      			                double total_DimensionZ=z*Lz;
			      			     //Thank God!!! *I do not need to consider image flag (periodic images) for Hbonding because in structural calculations we do not need to use it*        
			      			     // it means we do not need to see if peo has Hbond with water in image boxes rather we just check Hbond in main real box        // m=0;
			      				
					      			         
					      			          
					      			              
					      				 if(type==2) {
					      				    	 Hcounter++;
					      				        	 X1[atom_counter]=total_DimensionX;
					      				        	Y1[atom_counter]=total_DimensionY;
			     				              		 Z1[atom_counter]=total_DimensionZ;	
			     				              		
			     				              	    // System.out.println(x+"   "+y+ "   "+z); 
				     				              	    X1ordered[Hcounter]=X1[atom_counter];
					     				            	Y1ordered[Hcounter]=Y1[atom_counter];
					     				              	Z1ordered[Hcounter]=Z1[atom_counter];
					     				              	 
					     				           //	log((time_counter+"  "+Hcounter+"   "+Ocounter),"output.txt");
					      				       }//if 
					      					
					      	                 }//if Xmax
					      	        	  
					      	     }//if counter>8


					   
					      	
					      	   
					      	        	  if (atom_counter!=0) {//to just show lines where we have atom coords

									      	 if (atom_counter==natoms){	// by this one frame of input is complete bc one frame of input2 is already completed
									      		    
									   	 
									      		
					      	        	    	for (int i=1; i<=Hcounter; i++) {
					      					           for (int j=1; j<=(natoms2/7); j++) {
					      					            	
					      					            	    distanceSqr[i][j]=Math.pow((X2input[time_count][j]-X1ordered[i]),2) + Math.pow((Y2input[time_count][j]-Y1ordered[i]),2)+ Math.pow((Z2input[time_count][j]-Z1ordered[i]),2); 	
					      					  		           //it is a double array based on time_counter and atom_counter of first input file which is water bc we want to check interaction of all H atoms of water with o of peo. although in X2 and Y2 and Z2 we have atom counter2 but in distance we have atom counter bc we need to make the array based on the largest atom counter
					      					  				   distance[i][j]=Math.sqrt(distanceSqr[i][j]);
					      					  				
					      					                   Hbond[time_count]=0;
					      					                
					      					                 
					      					               
					      				      if (  distance[i][j]<= 2.675 ){  
					      				      					      				   
					      				    		   
					      			                HbondCounter++;//a variable that count the number of hbond in each frame
					      			      					      			               
					      			              }
					      				             Hbond[time_count]=HbondCounter;//put the number of hbonds of each frame in an array which saves the num of hbonds for each frame in the location of that frame in the array
				      					   
					      				                }									            
					      					         }
					      	        	   
					      	        	    	  
					      	        	    
					      	        	    	} //if (atom_counter==natoms){ 					      	        	    
					      	        	   } 
					      	        					      	        
					      	        	  
					      	    
					 counter++;
					   
		    	  }//while (true)		    	  
		    	
		    
		             for (int t=1; t<=wholeFrameNumber; t++) {
		            	
		    	          System.out.println(t+"      "+"Number of HbondHbonds"+"   "+Hbond[t]/2);
		    	
		             }
		    
		    
		    
		   

		
	       /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			        
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

	//log	 (Double.toString(X2),"output.txt");//for pinting the result of code in the output.file instead of showing on the console.
     

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