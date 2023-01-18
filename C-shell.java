
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


public class Hello {

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
	        	 //int counter4 = 0;
	        	 int time_counter = 0;
	        	 int atom_counter = 0;
	        	 int time_counter2 = 0;
	        	 int atom_counter2 = 0;
	        	 //int Hcounter=0;
	        	 int Ccounter=0;
	        	 int Ocounter=0;
	        	 boolean array_created = false;

	        	
				      		   
				      	
	        	    x=0;	
				   x2=0; 
				    y=0;
				   y2=0; 
				    z=0;
				   z2=0;   
				  type=0;
				  typeII=0;
				  id=0;
				  id2=0;
	        	 
	        	   double Xoverallc=0.0; //Thank God by putting Xoverallc out of while loop
	        	 //I could add all Xoverall (while reading x and inside the while loop)
	        	//to the Xoverallc and have summation of all Xoverall in it so go to next step (Xcm)
	        	   double Yoverallc=0.0;
	        	   double Zoverallc=0.0;
	        	   double Total_Massc=0.0; 
	        	 
	        	   
		             
		    	     //double distanceSqr=0;
		    	    // double distance=0;
		    	     int HbondCounter=0;
		    	    
		    	     double L=0.0;
		    	   
	       
		    	     
		  	    	 
		    	     
		    	     
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
		  	        			  
		  	        			   // time_counter++; 
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
		  		   
		  		   
		  		   
		  		   
		  		   
		  		   
		  		   
		  		   
		  			//////second while loop to just count atom number in the second input file(water)//////////////////////
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
	        			  
	        			   // time_counter++; 
	        			  wholeFrameNumber2++;
	        			   // System.out.println("tstep : " + time_counter);
	        			  //  atom_counter = 0;
	        			   
	        			} 
	        			
		  	        			
		  	        			if(counter2 == 3){
			        			    natoms2 = Integer.valueOf(line4);
			        			  //  System.out.println("natoms2 : " + natoms2);
			        		/*	    if(!array_created){
			        				for (int i = 0; i < frame; i++) {
			        				    xarr[i] = new double[natoms];
			        				    yarr[i] = new double[natoms];
			        				    zarr[i] = new double[natoms];
			        				}
			        				array_created = true;  //array created, do not create again
			        			    }*/

			        			}
		  	        			

					      	      if(counter2 == 5){
					      	        			    
					      	        			    xdim = line4.split("\\s",2);
					      	        			     for (int i=0; i < xdim.length; i++){
					      	        			     //System.out.println(xdim[i]);
					      	        			    } 
					      	        			   
					      	        			    //f = Double.parseDouble("200");
					      	        			    //f = Double.parseDouble(xdim[0]);
					      	        			    xlo = Double.valueOf(xdim[0]);
					      	        			    xhi = Double.valueOf(xdim[1]);
					      	        			    Lx = xhi-xlo;
					      	        			    //System.out.println("xlo, xhi : " + xlo + ", " + xhi);
					      	        			
					      	        			   //for RDF
					      	        			    L=Lx;
					      	    		       //L=Integer.valueOf(Lx);//The size of the box. In this 3 dimensional periodic box we have same size in all directions. But for other kind of boxes I should regulate the L   
					      	    		 
					      	        			}
					      	        			
					      	        			

					      	      if(counter2 == 6) {
					      	        			    
					      	        			    ydim = line4.split("\\s",2);
					      	        			     for (int i=0; i < ydim.length; i++){
					      	        			     	//System.out.println(ydim[i]);
					      	        			     } 
					      	        			   
					      	        			    ylo = Double.valueOf(ydim[0]);
					      	        			    yhi = Double.valueOf(ydim[1]);
					      	        			    Ly = yhi-ylo;
					      	        			    //System.out.println("ylo, yhi : " + ylo + ", " + yhi);
					      	        			  
					      	        			}
					      	        			
					      	        			

					      	       if(counter2 == 7) {
					      	        			    
					      	        			    zdim = line4.split("\\s",2);
					      	        			     for (int i=0; i < zdim.length; i++){
					      	        			     	//System.out.println(zdim[i]);
					      	        			     } 
					      	        			   
					      	        			    zlo = Double.valueOf(zdim[0]);
					      	        			    zhi = Double.valueOf(zdim[1]);
					      	        			    Lz = zhi-zlo;
					      	        			   // System.out.println("zlo, zhi : " + zlo + ", " + zhi);
					      	        			 // L=Lz;
					      	       }
					      	       
		  	        			counter2++;  //count until next time step 
		  	        			//System.out.println( counter2);	
		  		   }
		  		   
		  		   
		  		   
		
		           
		             
		           
		  		 double X1[]=new double [natoms+1];
		            double Y1[]=new double [natoms+1];
		            double Z1[]=new double [natoms+1];            
		           
		            double x1[]=new double [natoms+1];
		            double y1[]=new double [natoms+1];
		            double z1[]=new double [natoms+1];
		           
		            double mX1[]=new double [natoms+1];
		            double mY1[]=new double [natoms+1];
		            double mZ1[]=new double [natoms+1];
		           
		            int id1[]=new int [natoms+1];
		            double type1[]=new double [natoms+1];
		               
		            double X2[]=new double [natoms2+1];//wholeFrameNumber are the same for both input files but number of atoms for water input file is bigger. But since we want to have
		            //distance calculating from both X1 and X2 so the array size of both should be the same therefore we consider the highesst amount of atom number for both of arrays which is atom number of water
		            double Y2[]=new double [natoms2+1];
		            double Z2[]=new double [natoms2+1];
		       
		            double X1ordered[]=new double [natoms+1];
		            double Y1ordered[]=new double [natoms+1];
		            double Z1ordered[]=new double [natoms+1];
		           
		            double x1ordered[]=new double [natoms+1];
		            double y1ordered[]=new double [natoms+1];
		            double z1ordered[]=new double [natoms+1];
		                     
		            double X1Array[]=new double [natoms+1];
		            double Y1Array[]=new double [natoms+1];
		            double Z1Array[]=new double [natoms+1];
		           
		            double mX1ordered[]=new double [natoms+1];
		            double mY1ordered[]=new double [natoms+1];
		            double mZ1ordered[]=new double [natoms+1];
		           
		            int id1ordered[]=new int [natoms+1];
		            double type1ordered[]=new double [natoms+1];
		               
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
		       
		     
		    	    
		    	    ///////////////////////////////////////////
		    	   
		    	   // int L=0;
		    	    
		    		
	       		      double total_DimensionX2=0.0;
	                  double total_DimensionY2=0.0;
	                  double total_DimensionZ2=0.0;
		    	    
	                  Double  Del_x=0.0;
	                  Double  Del_y=0.0;
	                  Double  Del_z=0.0;
	                  
	                  
	                  
	                  //finding freq and distance
                      float Delta_R= (float) 0.1;
                      int bin=0;
                      
                      //Determine the number of bins by evaluating the histogram''s range and increment size 
                     // nBins = ceil((h.range(2)-h.range(1))/h.increment);
                     // int numbin= (int)(Math.round((L/Delta_R)));//to convert double to integer
                     //Set the values vector to be in the center of each bin
                     
                    
                      int numbin= (int)(Math.round((Math.floor(L/2)/Delta_R)+1)); 
                     // int numbin= (int)(Math.round((Math.floor(83.27/2)/Delta_R)+1)); 
                      /*correct. from http://www.pages.drexel.edu/~cfa22/msim/codes/rdf.cnB = (int)(rc2/dr) + 1;
                      and since rc in our work is Math.floor(L/2) we have this numbin*/
                     
                    
                      //Set all bins to zero
                      double [][] Freq1=new double[numbin+1][wholeFrameNumber+1];
                     // Arrays.fill(Freq1, 0);
                   
                      double [][] gs=new double[numbin+1][wholeFrameNumber+1];//polynomial of gr (radial)
	                  
                      double [] gsAve=new double[numbin+1];
	                  
                      double [] gsSum=new double[numbin+1];
	                  
	                  
		    	    
		    	    
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
				        		 time_counter2++; 
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
					        	   // int index1=Integer.valueOf(S1.toString());//Use above atom Id as index1
					              //  int index2=Integer.valueOf(S2.toString());//Use above atom Id as index2
					        	    
					        	    
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
					        		
				       		      // total_DimensionX2=((x2 + mx2)*Lx);
				                  // total_DimensionY2=((y2 + my2)*Ly);
				                  // total_DimensionZ2=((z2 + mz2)*Lz);
				           //for Static propertis like RDF or Hbond we do not need to consider priodic box condition so we not need to consider image flags. Thank GOD!
				                   total_DimensionX2=(x2*Lx);
				                   total_DimensionY2=(y2*Ly);
				                   total_DimensionZ2=(z2*Lz);

				                  
					        
					        // if(typeII==7){//for peo at interface of water/toluene atom type is different than atom type for peo at bulk.
					    	//  if(typeII==3){ 
					        	 if(typeII==5){// O of peo at bulk tol
					      // if(typeII==5){//for O of peo in tol
					    	          Ocounter++;
					    	          //System.out.println(Ocounter);
					        		  // if (counter2<=natoms2+8) { //to read one frame 
					    	          
					    	          X2[atom_counter2]=total_DimensionX2;
						         	  Y2[atom_counter2]=total_DimensionY2;
						         	  Z2[atom_counter2]=total_DimensionZ2;
						         	 /*
						         	  X2[atom_counter2]=x2;
						         	  Y2[atom_counter2]=y2;
						         	  Z2[atom_counter2]=z2;
						         	 */
						         	
					        		    X2ordered[Ocounter]=X2[atom_counter2];
	     				            	Y2ordered[Ocounter]=Y2[atom_counter2];
	     				              	Z2ordered[Ocounter]=Z2[atom_counter2];
						         	  //System.out.println(X2ordered[n]+"  "+n);
	     				              
	     				              //	System.out.println(time_counter2+"          "+Ocounter+"    "+X2ordered[Ocounter]);
					        	  }//if(typeII==7){
					          }//if(counter2 > 8){	 
					        // System.out.println(time_counter2+"    "+Ocounter+"   "+X2ordered[Ocounter]);
					      
					         
					         //Thank GOD!!!!!!
					         //GOD helped me using this part I can prepare the X2ordered[Ocounter] for the outside of while loop of input2 (I mean for use it in the while loop of input)
					            X2input[time_counter2][Ocounter]=X2ordered[Ocounter];
					            Y2input[time_counter2][Ocounter]=Y2ordered[Ocounter];
					            Z2input[time_counter2][Ocounter]=Z2ordered[Ocounter];
					           // System.out.println(time_counter2+"    "+Ocounter+"   "+  X2input[time_counter2][Ocounter]);
					      
					            
					        
					            
					         counter2++;
		       		}//if line3!=null 
		    	  
		    	  
		   ///////////////////////input///////////////////////////////////////////////////////////////////////
		    	
		    	
		    	   
		    	    	      if(line2.equals(beg_time)) {
				      	        		counter = 0; 
				      	        		//HbondCounter=0;// to start counting Hbond from beginning of each frame.
				      	        		 atom_counter = 0;
				      	        		 Ccounter=0;
				      	        		 time_counter++;
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
				      	        		  /*  if(!array_created){
				      	        				for (int i = 0; i < frame; i++) {
				      	        				    xarr[i] = new double[natoms];
				      	        				    yarr[i] = new double[natoms];
				      	        				    zarr[i] = new double[natoms];
				      	        				}
				      	        				array_created = true;  //array created, do not create again
				      	        			    }
		                                     */
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
				      	        			
				      	        			   //for RDF
				      	        			   // L=Lx;
				      	    		       //L=Integer.valueOf(Lx);//The size of the box. In this 3 dimensional periodic box we have same size in all directions. But for other kind of boxes I should regulate the L   
				      	    		 
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
				      	        			 // L=Lz;
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
					      	   	  	            /*
					      	        			    //we should consider image flag(ix, iy, iz) bc the x1, y1, z1 are just a portion of box dimension (lmax- lmin)
					      			                double total_DimensionX=((x + mx)*Lx);
					      			                double total_DimensionY=((y + my)*Ly);
					      			                double total_DimensionZ=((z + mz)*Lz); 
					      			             */
					      	        			 //but here where we investigate a static property like RDF we do not need to consider periodic condition so do not need to use image flags
					      			                double total_DimensionX=(x*Lx);
					      			                double total_DimensionY=(y*Ly);
					      			                double total_DimensionZ=(z*Lz);
					      			             
					      			             // m=0;
					      				// if(type==2) {
					      			//if(type==3) { //for C of ch3 in tol at interface 
					      		    if(type==1) { //for C of ch3 in tol at bulk 
					      				  	 Ccounter++;
					      				  	 
					      				  	
					      				  	X1[atom_counter]=total_DimensionX;
					      	              Y1[atom_counter]=total_DimensionY;
					      	                  Z1[atom_counter]=total_DimensionZ;
					      	                 
					      	                  x1[atom_counter]=x;//for printing of H coordinate at the end just we need x and not total_DimensionX which is x*Lx
					      	                  y1[atom_counter]=y;
					      	                  z1[atom_counter]=z;
					      	                 
					      	                  mX1[atom_counter]=mx;
					      	              mY1[atom_counter]=my;
					      	                  mZ1[atom_counter]=mz;
					      	                 
					      	                  id1[atom_counter]=id;
					      	                  type1[atom_counter]=type;
					      	                 
					      	                 
					      	                 
					      	                 
					      	                     X1ordered[ Ccounter]=X1[atom_counter];
					      	                Y1ordered[ Ccounter]=Y1[atom_counter];
					      	                  Z1ordered[ Ccounter]=Z1[atom_counter];
					      	                 
					      	                 
					      	                  x1ordered[ Ccounter]=x1[atom_counter];
					      	                  y1ordered[ Ccounter]=y1[atom_counter];
					      	                    z1ordered[ Ccounter]=z1[atom_counter];
					      	                 
					      	                 
					      	                  mX1ordered[ Ccounter]=mX1[atom_counter];
					      	                mY1ordered[ Ccounter]=mY1[atom_counter];
					      	                  mZ1ordered[ Ccounter]=mZ1[atom_counter];
					      	                 
					      	                id1ordered[ Ccounter]=id1[atom_counter];
					      	                    type1ordered[ Ccounter]=type1[atom_counter];
					      	         
						                    	
					      		     }//if 
					      					
					      	     }//if counter>8

					      	  
					      	     
					      	    
					      	 
					      	   
					      	              
					      	        	  if (atom_counter!=0) {//to just show lines where we have atom coords

									      	 if (atom_counter==natoms){	// by this one frame of input is complete bc one frame of input2 is already completed
									      	      
									      		 	
									      	     
									      	    double arrX[]=new double [natoms+1];
									      	    double arrY[]=new double [natoms+1];
									      	    double arrZ[]=new double [natoms+1];
									      	   
									      	   
									      	   
									      	    double arrMX[]=new double [natoms+1];
									      	    double arrMY[]=new double [natoms+1];
									      	    double arrMZ[]=new double [natoms+1];
									      	   
									      	    int arrID[]=new int [natoms+1];
									      	    double arrType[]=new double [natoms+1];
									      	   
									      	    int n = arrX.length;
									      	    int n2 = arrY.length;
									      	    int n3 = arrZ.length;
									      	                   
									      	    int nM = arrMX.length;
									      	    int nM2 = arrMY.length;
									      	    int nM3 = arrMZ.length;
									      	   
									      	    int nID = arrID.length;
									      	    int nType = arrType.length;
									      	    
									      	    
							                
							                    
					      	        for (int i=1; i<=Ccounter; i++) {					      	        	    		 
					      	        	      
					      	           //for (int j=1; j<=(natoms2/7); j++) { //for all O of peo when we want to see total number of c after a distance
					      	        	 for (int j=1; j<=1; j++) { //for one O of peo when we want to total number of C at specific distance (5A, 6A, ...)
					      	        	    	  
					      	        	
					      	        	    	  
					      	        	    		   Del_x=(double)(X2input[time_counter][j]-X1ordered[i]);//distance of O and H				      	        	    		
					      	        	    						      	        	    	
					      	        	    		
							                    	
							                    	   Del_y=(double)(Y2input[time_counter][j]-Y1ordered[i]);
								           	        
							                    	 
								        
								           	           Del_z=(double)(Z2input[time_counter][j]-Z1ordered[i]);
								                   
							                    
					   

							          	          distanceSqr[i][j]= Math.pow(Del_x,2) + Math.pow(Del_y, 2) + Math.pow(Del_z, 2);
							          	          distance[i][j]=Math.sqrt(distanceSqr[i][j]);
							          	    
							          	          
							          	      
							          	      //Make sure the data fits the range
							          	    // if (distance[i][j]<=4) {    
							          	     if (distance[i][j]>5 && distance[i][j]<=6) {//The cutoff distance should not be greater than half of the box size. to just track the atoms inside the periodic box
							          	     System.out.println(distance[i][j]);
							          	    	 arrX[i] = x1ordered[i]; // for reading the coordinates of H in water
							                     arrY[i] = y1ordered[i];
							                     arrZ[i] = z1ordered[i];              
							                                              
							                    
							                     arrMX[i] =mX1ordered[i];
							                     arrMY[i] =mY1ordered[i];
							                     arrMZ[i] =mZ1ordered[i];
							                    
							                     arrID[i] =  id1ordered[i];
							                     arrType[i] = type1ordered[i];  
								                  	    	
							          	      }//if        
							          	   
				      					   }	//for j								            
				      				   }  //for i
					      	        	
							      
					      	        	     //for removing dublicate atomas with distance <=8.5
					      	        	   
					      	                  n = removeDuplicates(arrX, n);					      	              
					      	                  n2 = removeDuplicates(arrY, n2);
					      	                  n3 = removeDuplicates(arrZ, n3);
					      	                  nM = removeDuplicates(arrMX, nM);
					      	                  nM2 = removeDuplicates(arrMY, nM2);
					      	                  nM3 = removeDuplicates(arrMZ, nM3);
					      	                  nID = removeDuplicates7(arrID, nID);            
					      	                  nType = removeDuplicates8(arrType, nType);
					      	        	     
					      	        	     
					      	        	   					      	                  

					      	                  for (int k=0; k<n2; k++) {
					      	                    if (arrY[k]!=0) {
					      	                
					      	        
					      	     //log (((arrX[k])+"\t"),"output8.txt"); //for X print separately and then will add it to the other parts
					      	     log (((arrID[k])+"\t "+(arrType[k])+"\t"+ (arrY[k])+"      "+(arrZ[k])+"        "+(arrMX[k])+"    "+(arrMY[k])+"     "+(arrMZ[k])),"output7.txt"); //for other than X
					      	     //type and x has problem so I should add them manually. for type I should make all types as 2.
					      	                                
					      	                       }
					      	                 }
					      	              
					      	                  
					      	                 for (int k=0; k<n; k++) {
					      	                    if (arrX[k]!=0) {	      	                  

					      	                
					      	        
					      	     log (((arrX[k])+"\t"),"output8.txt"); //for X print separately and then will add it to the other parts
					      	     
					      	     //type and x has problem so I should add them manually. for type I should make all types as 2.
					      	                                
					      	                       }
					      	                 }
					      	        	     
								             
					      	        	    	 } //if (atom_counter==natoms){ 
					      	        	   
					      	        	   } 
					      	        	
					      	        	
					 counter++;
					
					
					   
		    	  }//while (true)		    	  
		    	
		    
  ///Average of radials over number of frames/////////////////////////////////////////////////////
            
   
		    
		    
		   

		
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
	 
	 
	 static int removeDuplicates(double arrX[], int n)
	 {
	double[] temp = new double[n];

	// Start traversing elements
	int j = 0;
	for (int i=0; i<n-1; i++)
	// If current element is not equal
	// to next element then store that
	// current element
	if (arrX[i] != arrX[i+1])
	temp[j++] = arrX[i];

	// Store the last element as whether
	// it is unique or repeated, it hasn't
	// stored previously
	temp[j++] = arrX[n-1];

	// Modify original array
	for (int i=0; i<j; i++)
	arrX[i] = temp[i];

	return j;
	}

	 
	 static int removeDuplicates7(int arrID[], int nID)
	 {
	int[] temp = new int[nID];

	// Start traversing elements
	int j = 0;
	for (int i=0; i<nID-1; i++)
	// If current element is not equal
	// to next element then store that
	// current element
	if (arrID[i] != arrID[i+1])
	temp[j++] = arrID[i];

	// Store the last element as whether
	// it is unique or repeated, it hasn't
	// stored previously
	temp[j++] = arrID[nID-1];

	// Modify original array
	for (int i=0; i<j; i++)
	arrID[i] = temp[i];

	return j;
	}
	 
	 
	 static int removeDuplicates8(double arrType[], int nType)
	 {
	double[] temp = new double[nType];

	// Start traversing elements
	int j = 0;
	for (int i=0; i<nType-1; i++)
	// If current element is not equal
	// to next element then store that
	// current element
	if (arrType[i] != arrType[i+1])
	temp[j++] = arrType[i];

	// Store the last element as whether
	// it is unique or repeated, it hasn't
	// stored previously
	temp[j++] = arrType[nType-1];

	// Modify original array
	for (int i=0; i<j; i++)
	arrType[i] = temp[i];

	return j;
	}
}