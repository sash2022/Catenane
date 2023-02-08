
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
     	         
	       	  
	        

       			
	           	
	        
	        	 String xdim[] = new String[2];
	        	 String ydim[] = new String[2];
	        	 String zdim[] = new String[2];
	        	 String atom_data[] = new String[8];
	        	 String atom_data2[] = new String[8];
	        	 
	        	 String beg_time = "ITEM: TIMESTEP";
	  
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
	        	 int time_count = 0;
	        	 int time_count2 = 0;
	        	 int atom_counter2 = 0;
	        	 int Hcounter=0;
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
		  	        	
		  	        	if(line.equals(beg_time)) {
		  	        		counter = 0; //for peo
		  	        		
		  	        	}
		  	        	
		  	        			if(counter == 1) {
		  	        		      time_counter++;
		  	        			  wholeFrameNumber++;
		  	        			 
		  	        			} 
		  	        			
		  	        			
		  	        			if(counter == 3){
			        			    natoms = Integer.valueOf(line);
			        		
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
		  		   
		         	 
		  		   
		  		   
		  			//////second while loop to just count atom number in the second input file(water)//////////////////////
		  		   while((line4 = in4.readLine()) != null){        	
		  	        	
		  	        		
		  	        	if(line4.equals(beg_time)) {
		  	        		counter2 = 0; 
		  	        	
		  	        	
		  	        	}
		  	        	
		  	      	if(counter2 == 1) {
	        			 
	        			  wholeFrameNumber2++;
	        			   
	        			} 
	        			
		  	        			
		  	        			if(counter2 == 3){
			        			    natoms2 = Integer.valueOf(line4);
			        			}
		  	        			
		  	        			
		  	        		   if(counter2 == 5){
	      	        			    
	      	        			    xdim = line4.split("\\s",2);
	      	        			     for (int i=0; i < xdim.length; i++){
	      	        			   
	      	        			    } 
	      	        			   
	      	        			  
	      	        			    xlo = Double.valueOf(xdim[0]);
	      	        			    xhi = Double.valueOf(xdim[1]);
	      	        			    Lx = xhi-xlo;
	      	        			  //  System.out.println("xlo, xhi : " + xlo + ", " + xhi);
	      	        			
	      	        			   //for RDF
	      	        			    L=Lx;
	      	    		       //L=Integer.valueOf(Lx);//The size of the box. In this 3 dimensional periodic box we have same size in all directions. But for other kind of boxes I should regulate the L   
	      
	      	        			}
		  	        			
		  	        		   
		  	        		   
		  	        		   
		  	        	if(counter2 > 8){
		  	  		        	// System.out.println(line1);
		  	  		        	
		  	        				  atom_data2=line4.split("\\s+",8);
						        		id2 = Integer.valueOf(atom_data2[0]);
					       			   
					       			    typeII = Integer.valueOf(atom_data2[1]);
					       			    x2 = Double.valueOf(atom_data2[2]);
					       			    y2 = Double.valueOf(atom_data2[3]);
					       			    z2 = Double.valueOf(atom_data2[4]);
					       			    
//I use this part to just check the area around PEO with thickness of 5 A. So we just investigate the H atoms in a shell around peo which has 5 A thickness arounf the chain. So our calculation would be and we do not check all the H of the whole box! Thank GOD!  
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
				        		 time_count2++; 
				        	}
				    	  
		    	            
				    	 	if(counter2 == 1) {
			    			    tstep2 = Integer.valueOf(line3);
			    			   
			    			
			    			}
				        	

				    	 	
				    		if(counter2 == 5){
		        			    
		        			    xdim = line3.split("\\s",2);
		        			     for (int i=0; i < xdim.length; i++){
		        			     	
		        			    } 
		        			   
		        			  
		        			    xlo = Double.valueOf(xdim[0]);
		        			    xhi = Double.valueOf(xdim[1]);
		        			    Lx = xhi-xlo;
		        			   
		        			}

		        			if(counter2 == 6) {
		        			    
		        			    ydim = line3.split("\\s",2);
		        			     for (int i=0; i < ydim.length; i++){
		        			   
		        			     } 
		        			   
		        			    ylo = Double.valueOf(ydim[0]);
		        			    yhi = Double.valueOf(ydim[1]);
		        			    Ly = yhi-ylo;
		        			   
		        			  
		        			}

		        			if(counter2 == 7) {
		        			    
		        			    zdim = line3.split("\\s",2);
		        			     for (int i=0; i < zdim.length; i++){
		        			     
		        			     } 
		        			   
		        			    zlo = Double.valueOf(zdim[0]);
		        			    zhi = Double.valueOf(zdim[1]);
		        			    Lz = zhi-zlo;
		        			    
		        			    //Lz = 83.27;
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
				       			
				       			 
				       			 atom_counter2++;
				       			
				           //for Static propertis like RDF or Hbond we do not need to consider priodic box condition so we not need to consider image flags. Thank GOD!
				                   total_DimensionX2=(x2*Lx);
				                   total_DimensionY2=(y2*Ly);
				                   total_DimensionZ2=(z2*Lz);

				                  
					        
					         if(typeII==7){//for peo at interface of water/toluene atom type is different than atom type for peo at bulk.
					    	//  if(typeII==3){ 		
					    	          Ocounter++;
					    	          
					    	          X2[atom_counter2]=total_DimensionX2;
						         	  Y2[atom_counter2]=total_DimensionY2;
						         	  Z2[atom_counter2]=total_DimensionZ2;
						         	
						         	
					        		    X2ordered[Ocounter]=X2[atom_counter2];
	     				            	Y2ordered[Ocounter]=Y2[atom_counter2];
	     				              	Z2ordered[Ocounter]=Z2[atom_counter2];
						         	
					        	  }//if(typeII==7){
					          }//if(counter2 > 8){	 
					       
					         
					         //Thank GOD!!!!!!
					         //GOD helped me using this part I can prepare the X2ordered[Ocounter] for the outside of while loop of input2 (I mean for use it in the while loop of input)
					            X2input[time_count2][Ocounter]=X2ordered[Ocounter];
					            Y2input[time_count2][Ocounter]=Y2ordered[Ocounter];
					            Z2input[time_count2][Ocounter]=Z2ordered[Ocounter];
					           
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
				      	        			
				      	        			}
				      	        	
				      	        	

				      	      if(counter == 3){
				      	        			    natoms = Integer.valueOf(line2);
				      	        	
				      	        			}
		
				      	        			

				      	      if(counter == 6) {
				      	        			    
				      	        			    ydim = line2.split("\\s",2);
				      	        			     for (int i=0; i < ydim.length; i++){
				      	        			     	//System.out.println(ydim[i]);
				      	        			     } 
				      	        			   
				      	        			    ylo = Double.valueOf(ydim[0]);
				      	        			    yhi = Double.valueOf(ydim[1]);
				      	        			    Ly = yhi-ylo;
				      	        			  
				      	        			}
				      	        			
				      	        			

				      	       if(counter == 7) {
				      	        			    
				      	        			    zdim = line2.split("\\s",2);
				      	        			     for (int i=0; i < zdim.length; i++){
				      	        			     	
				      	        			     } 
				      	        			   
				      	        			    zlo = Double.valueOf(zdim[0]);
				      	        			    zhi = Double.valueOf(zdim[1]);
				      	        			    Lz = zhi-zlo;
				      	        			 // Lz = 83.27;
				      	        			}

				      	        		 
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
					      	        		
					      	        			  atom_counter++;	        		
					      	   	  	                
					      	   	  	            /*
					      	        			    //we should consider image flag(ix, iy, iz) bc the x1, y1, z1 are just a portion of box dimension (lmax- lmin)
					      			                double total_DimensionX=((x + mx)*Lx);
					      			                double total_DimensionY=((y + my)*Ly);
					      			                double total_DimensionZ=((z + mz)*Lz); 
					      			             */
					      	        			 //but here where we investigate a static property like RDF we do not need to consider periodic condition so do not need to use image flags
					      	         if (x<=Xmax[time_count]+5 && y<=Ymax[time_count]+5 && z<=Zmax[time_count]+5 && x>=Xmin[time_count]-5 && y>=Ymin[time_count]-5 && z>=Zmin[time_count]-5 ) { 
										      	        				      			           
					      	        			    double total_DimensionX=(x*Lx);
					      			                double total_DimensionY=(y*Ly);
					      			                double total_DimensionZ=(z*Lz);
					      			         
					      				 if(type==2) {
					      				    
					      				  	 Hcounter++;
					      				  	 
					      				  	
			      				        	 X1[atom_counter]=total_DimensionX;
			      				        	Y1[atom_counter]=total_DimensionY;
	     				              		 Z1[atom_counter]=total_DimensionZ;	
	     				              		
		     				              	 
		     				              	    X1ordered[Hcounter]=X1[atom_counter];
			     				            	Y1ordered[Hcounter]=Y1[atom_counter];
			     				              	Z1ordered[Hcounter]=Z1[atom_counter];
					      				    
			     				              	
					      				       }//if 
					      				 
					      	             }//if Xmax
					      					
					      	      }//if counter>8

					      	  
					      	     
					      	    
					      	 
					      	   
					      	   
					      	        	  if (atom_counter!=0) {//to just show lines where we have atom coords

									      	 if (atom_counter==natoms){	// by this one frame of input is complete bc one frame of input2 is already completed
									      	      
									      		 	
					      /////////////////////////////////////////////////////////////////////////////	        	    	
/*I managed this histogram part and RDF part based on these references:
 * http://www.cchem.berkeley.edu/chem195/histogram_8m.html#aedd379efd57ae78820ad8787bfab0cce
 * http://www.cchem.berkeley.edu/chem195/radial_distribution_8m.html*/
									     
							          //////////////////////////////////////////////////////////////////////      
							               
							                    
					      	        	     for (int i=1; i<=Hcounter; i++) {					      	        	    		 
					      	        	      
					      	        	      for (int j=1; j<=(natoms2/7); j++) {
					      	        	    	  
					      	        	
					      	        	    		   Del_x=(double)(X2input[time_count][j]-X1ordered[i]);//distance of O and H				      	        	    		
					      	        	    		
							                    	   Del_y=(double)(Y2input[time_count][j]-Y1ordered[i]);
								           	        
								           	           Del_z=(double)(Z2input[time_count][j]-Z1ordered[i]);
								                   
							                    
							          	          distanceSqr[i][j]= Math.pow(Del_x,2) + Math.pow(Del_y, 2) + Math.pow(Del_z, 2);
							          	          distance[i][j]=Math.sqrt(distanceSqr[i][j]);
							          	          
							          	          
							          	   //////////////////////////////////////////////////
							          	       //Now that the histogram is initialized, add the data in the right bin
							          	          
							          	        
							          	      //Make sure the data fits the range
							          	     //  if (distance[i][j]<=Math.floor(L/2)) {//The cutoff distance should not be greater than half of the box size. to just track the atoms inside the periodic box
							          	    	 //if (distance[i][j]<=Math.floor(83.27/2)) {
							          	    		if (distance[i][j]<=Math.floor(92/2)) {//92 is the size of box of water in Z direction
							          	    	 
							          	    	          // Find the right bin position
							          	    	           bin= (int)Math.floor(distance[i][j]/Delta_R);//ind is the bin of our historam for O-H rdf. Bin size is Delata_R.So we devide our distance to bins with Delta_R size. So our distance consists of several bids with size Delt_R.
								                  	    	//And by reading each distance of H and O we have its bid and  we add one to that bin. 
							          	    	         
							          	    	         
							          	    	           //Add 1 to the bin
								                  	    	Freq1[bin][time_count]++;//.*ThankGOD* here each ind is one bin of our histogram.So when we see distance<L/2 for one ind we will add 
								                  	    	//one to frequency of that bin(ind)
								                  	    	
								                  	    	
							          	       }//if        
							          	   
				      					          }	//for j								            
				      					         }  //for i
					      	        	   
					      	        	  							                    
							                    
							                    
						 //Normalization step///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
					      	        	    	
					      	        	    	int numoxygen=(int)Math.floor(natoms2/7);//O in peo  
					      	        	    	//System.out.println (numoxygen);
					      	        	    	
					      	        	    	int numhydrogen=(int)Math.floor((natoms*2)/3);//H in water
					      	        	    	
					      	        	    	//overal density of the whole peo and water.(at interface although we have ps and toluene but since we just check peo at water side of interface so just density of peo water will be considered)
					      	        	    	
					      	        	    	
					      	        	  	double density1=(numhydrogen)/(L*L*92); //92 is the Z dimention of water side of the wat/Tol interface of solvents
					      	        	    	
					      	        	    	//density of 2 can be the number density of water at interface so I should calc it and put the correct number density here.????????
							                  
							                  
					      	        	    	
					      	        	    	
					      	        	    	
					      	        	    	Double [][] VolDen1=new Double[numbin+1][wholeFrameNumber+1];
							                   // Arrays.fill(VolDen1, 0.0);
							                    
							                    Double [][] volume1=new Double[numbin+1][wholeFrameNumber+1];// n is max of R which means max of bins (max number in x axies (distance or bin axies) of g(r) and distance diagram)w.
							                 	//Arrays.fill(volume1, 0.0);  
							                     
							                   //double [] r1=new double[numbin+1];//1D array of r. if we put n then it would consider 0 to n-1 but now it will consider 0 to (n+1)-1
							             	  // Arrays.fill(r1, 0);
							             	 
							             	   double [][] radial1=new double[numbin+1][wholeFrameNumber+1];
							             	   //Arrays.fill(radial1, 0.0); 
							             	 
							             	
							             	 
							           for (int i=0; i<numbin; i++){ //according to http://www.pages.drexel.edu/~cfa22/msim/codes/rdf.c   	 
							          
							        	   /* freq.length is the size of the freq array. so we want to make array of r 
							             	 with size of the freq array. */
							             //the two above method of for loop are correct and give the same result.	
							            	 
							        	     gs[i][time_count]=0;
							        	     
							            	 if (Freq1[i][time_count]!=0) { /*if freq at index i is not 0 so we set the r at index i as that i index:means we just put the distances which their 
							             	 freq are not zero, into the r array*/
							            		
							             		// r1[i]=i*Delta_R;
							            		 /* the eclipse suggested to put int. we multiple Delta_R to go back to the distance situation not just 0, 1, 2 ...
							             		 So r1[i] give us array of distances that we have frequency of presence of H. I mean distances from O at which we have H  */
							            	    //again convert freq to r as a distance
							             		 
							             		
							                     //here we find volume of sphere with radius of those distance and thickness of Delta_R
							                     //volume1[i]=( 4.0 / 3.0 ) * Math.PI * (Math.pow( (r1[i]+Delta_R), 3) - Math.pow( r1[i], 3));
							                     volume1[i][time_count]=(0.5*( 4.0 / 3.0 ) * Math.PI * ((i+1)*(i+1)*(i+1)-i*i*i)*Delta_R*Delta_R*Delta_R);
							                     //since at interface we can not have a complete sphere around O of peo with water inside it so we have the half of volume of a sphere 
							                     //from http://www.pages.drexel.edu/~cfa22/msim/codes/rdf.c
							                     //Both of above methods of calculating volume1 gives the same results and are correct. So we do not need r1[i] for calculating volume1
							                     
						      	        	    	
							                  
							                   	 VolDen1[i][time_count]=volume1[i][time_count]*density1;// is volume of a layer of sphere around O times the density of H in the whole box
							                     
							                   	 //according to ref : http://www.pages.drexel.edu/~cfa22/msim/codes/rdf.c
							                   	 //fprintf(stdout,"%.4lf %.4lf\n",i*dr,(double)(H[i])/(ngr*N*nid));
							                   	 
							                   //  radial1[i]=Freq1[i]/(VolDen1[i]*(numhydrogen+numoxygen));
							                     radial1[i][time_count]=Freq1[i][time_count]/(VolDen1[i][time_count]*numoxygen);
							                   //	radial1[i]=Freq1[i]/VolDen1[i];
							              
							            	   }// if (Freq1[i]!=0)
							            }//for
							              
							             // printRow2(radial1,"RadArr1.txt");//Calls the "printRow" function
							               
							         	       
							          	       
					      	/////////////////////////////////////////////////////////////////////////////////////////////////////  
							             // find the 5th degree polynomial smoothed distribution function 
							           //Thank God we use is to decrease the noise in the plot of radial to make it similar to RDF plots in literature.
							              
							             if (numbin >= 5 ) { //polynimial degree 5
							            	 
							            	 gs[1][time_count]= (69.0*radial1[1][time_count] + 4.0*radial1[2][time_count] - 6.0*radial1[3][time_count] + 4.0*radial1[4][time_count] - radial1[5][time_count]) / 70.0;
							            	 gs[2][time_count]= (2.0*radial1[1][time_count] + 27.0*radial1[2][time_count] + 12.0*radial1[3][time_count] - 8.0*radial1[4][time_count] + 2.0* radial1[5][time_count]) / 35.0;
							            	 
							            	 for (int i=3; i <= numbin-2; i++) {
							            		 gs[i][time_count]= (-3.0*radial1[i-2][time_count] + 12.0*radial1[i-1][time_count] + 17.0*radial1[i][time_count] + 12.0*radial1[i+1][time_count] - 3.0* radial1[i+2][time_count]) / 35.0;	 
							            	 }
							            	 
							            	 gs[numbin-1][time_count]= (2.0*radial1[numbin-4][time_count] - 8.0*radial1[numbin-3][time_count] + 12.0*radial1[numbin-2][time_count] + 27.0*radial1[numbin-1][time_count] + 2.0* radial1[numbin][time_count]) / 35.0;
							            	 gs[numbin][time_count]= (-radial1[numbin-4][time_count] + 4.0*radial1[numbin-3][time_count] - 6.0*radial1[numbin-2][time_count] + 4.0*radial1[numbin-1][time_count] + 69.0*radial1[numbin][time_count]) / 70.0;
							             
							            	 
							            	 
							            	 
							            	 for (int i=1; i < numbin; i++) {							            		 
							            	   if (gs[i][time_count]>0)//according to internet's code of gr: gs(i) = max(0.0d0,gs(i))
							                    gs[i][time_count]= gs[i][time_count]; 
							            	   
							            	   gsSum[i] += gs[i][time_count];							            	  
							            	 
							            	 }//for						            	
							             
							               }//if (numbin >= 5 ) 
							             
							                
					      	        	    	} //if (atom_counter==natoms){ 
							            	 }
					      	        
					 counter++;
				
		    	  }//while (true)		    	  
		    	
		    
		    
		   ///Average of radials over number of frames/////////////////////////////////////////////////////
            
            for (int i=1; i < numbin; i++) {      
       		 
       	   gsAve[i]= gsSum[i]/wholeFrameNumber;
       	   System.out.println((i/10)+"   "+gsAve[i]);       		    
       		 
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