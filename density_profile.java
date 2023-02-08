
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
	       	    // String input_file2="input2.txt";//read from input file     
	       	        
	       	        
	       	        @SuppressWarnings("resource")// in order to fix this problem (Resource leak: 'in2' is never closed) I add this line
	       			BufferedReader in = new BufferedReader(new FileReader(input_file)); //will be used for counting the entire number of frames which is the same for the two dump files
	       	        @SuppressWarnings("resource")
	       			BufferedReader in2 = new BufferedReader(new FileReader(input_file));//will be used for the all parts of dump file like header and coordinate in the peo (input file)
	       	         
	       	     //    @SuppressWarnings("resource")
	       		 //	BufferedReader in3 = new BufferedReader(new FileReader(input_file2));//will be used for just count atom number in the water (input file2)
	       	    // @SuppressWarnings("resource")
     	        //	BufferedReader in4 = new BufferedReader(new FileReader(input_file2));//will be used for the all parts of dump file like header and coordinate in the water (input file2)
     	         
	       	     
                         
	        

             int type2=0;
             int type7=0;
       			           					           	
	           	double Xoverall=0;
	           	double Yoverall=0;
	           	double Zoverall=0;
	           	
	        
	        	 String xdim[] = new String[2];
	        	 String ydim[] = new String[2];
	        	 String zdim[] = new String[2];
	        	 String atom_data[] = new String[8]; //Catenane project
	        	 //String atom_data[] = new String[5];//janus project
	        	 String atom_data2[] = new String[8];
	        	 
	        	 String beg_time = "ITEM: TIMESTEP";
	        	// String beg_time2 = "ITEM: TIMESTEP";
	        	 int tstep = 0, natoms = 1; 
	        	 int tstep2 = 0, natoms2 = 1; 
	        	 int wholeFrameNumber=0; //is for both water and peo 
	        	 int wholeFrameNumber2=0;
	        	 int id, type,mx,my,mz;
	        	 double x,y,z,dx,dy,dz,rmsd,charge;
	        	 double ylo,yhi;
	        	 
	        	 double xlo=0;
	        	 double xhi=0;
	        	 
	        	 double zlo=0;
	        	 double zhi=0;
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
		  		   
		  		   
		  		   
		  		   
		  		   
		  		   
		  		 /*
		  		   
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
			        			    }

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
		  		   
		  		   
		  		   */
		
		           
		             
		           
		             double X1[]=new double [natoms+1];
		             double Y1[]=new double [natoms+1];
		             double Z1[]=new double [natoms+1];
		                
		             double X2[]=new double [natoms+1];//wholeFrameNumber are the same for both input files but number of atoms for water input file is bigger. But since we want to have 
		             //distance calculating from both X1 and X2 so the array size of both should be the same therefore we consider the highesst amount of atom number for both of arrays which is atom number of water
		             double Y2[]=new double [natoms+1];
		             double Z2[]=new double [natoms+1];
		  	       	 
		             double X3[]=new double [natoms+1];
		             double Y3[]=new double [natoms+1];
		             double Z3[]=new double [natoms+1];
		                
		             double X4[]=new double [natoms+1];
		             double Y4[]=new double [natoms+1];
		             double Z4[]=new double [natoms+1];
		                
		             double X5[]=new double [natoms+1];
		             double Y5[]=new double [natoms+1];
		             double Z5[]=new double [natoms+1];
		             
		             double X6[]=new double [natoms+1];
		             double Y6[]=new double [natoms+1];
		             double Z6[]=new double [natoms+1];
		                
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
									       			 //  X1[atom_counter]=0;
						      				         //  Y1[atom_counter]=0;
				     				              	 //  Z1[atom_counter]=0;	
				     				              		
				     				              
				     				            	   
				      	        			
				      	        		           int ID=0;//define it to put id in it so we have id out of if(counter > 8){ 
				      	        		        
					      	     if(counter > 8){//here we can read just coordinates of each frame.
					      	        		
					      	        			   // atom_data=line2.split("\\s+",8);//splits the string based on whitespace for Catenane
					      	        			  atom_data=line2.split("\\s+",8);//splits the string based on whitespace for Janus
					      	        			    id = Integer.valueOf(atom_data[0]);
					      	        			    ID=id;
					      	        			    type = Integer.valueOf(atom_data[1].trim());
					      	        			    x = Double.valueOf(atom_data[2].trim());
					      	        			    y = Double.valueOf(atom_data[3].trim());
					      	        			    z = Double.valueOf(atom_data[4].trim());
					      	        			    mx = Integer.valueOf(atom_data[5]);
					      	        			   my = Integer.valueOf(atom_data[6].trim());
					      	        			    mz = Integer.valueOf(atom_data[7].trim());  //may be a new line character after this
					      	        			  // System.out.println(x);
					      	        			  atom_counter++;	        		
					      	   	  	                //type2=0;
					      	   	  	            
					      	        			    //we should consider image flag(ix, iy, iz) bc the x1, y1, z1 are just a portion of box dimension (lmax- lmin)
					      			                double total_DimensionX=((x + mx)*Lx);
					      			                double total_DimensionY=((y + my)*Ly);
					      			                double total_DimensionZ=((z + mz)*Lz); 
					      			            /* 
					      	        			 //but here where we investigate a static property like RDF we do not need to consider periodic condition so do not need to use image flags
					      			                double total_DimensionX=(x*Lx);
					      			                double total_DimensionY=(y*Ly);
					      			                double total_DimensionZ=(z*Lz);
					      			            */  
					      			
					      			                		 
					      			                		 //System.out.println(j+"   "+x);
					      			                       if(type==1 || type==2 ) {// water
					      			                								      			                			
					      			                		     X1[atom_counter]=x;
									      				         Y1[atom_counter]=y;
							     				              	 Z1[atom_counter]=total_DimensionZ;	
							     				              		      			                	  
					      			                		   }
					      			                		
					      			                		if(type==3 || type==4 || type ==5 || type==6) {// tol
					      			                			
					      			                		  X2[atom_counter]=x;
									      				         Y2[atom_counter]=y;
							     				              	 Z2[atom_counter]=total_DimensionZ;
						      			                	    
					      			                		} 
						      			                	    
					      			                		if(type==7 || type==8 || type ==9) {// peo
						      			                			
						      			                		  X3[atom_counter]=x;
										      				         Y3[atom_counter]=y;
								     				              	 Z3[atom_counter]=total_DimensionZ;
							      			                	    
						      			                		}
						      			                		
						      			                		if(type==10  || type==11 || type ==12 || type==13 || type ==14) {// ps 
							      			                			
							      			                		  X4[atom_counter]=x;
											      				         Y4[atom_counter]=y;
									     				              	 Z4[atom_counter]=total_DimensionZ;
								      			                	    
							      			                		}
							      			                		
								      			              		if(type==5) {
								      			              			
								      			              		  X5[atom_counter]=x;
											      				         Y5[atom_counter]=y;
									     				              	 Z5[atom_counter]=total_DimensionZ;
								      			              											      			                	    
								      			              		}	   
					      			               
								      			              	if(type==6) {
							      			              			
								      			              		  X6[atom_counter]=x;
											      				         Y6[atom_counter]=y;
									     				              	 Z6[atom_counter]=total_DimensionZ;
								      			              											      			                	    
								      			              		}
								      			              	  	       			
					      					
					      	        	    }//if counter>8

					      	        	
					 counter++;
					
					
					   
		    	  }//while (true)		    	  
		    	
		    
		           
		  
		    
		  	 //devide the box to layers with thickness in the size of one bead
              // double layerThickness=0.71;//layer thickness is equal to bead size (r0) which is 0.71 A    			                
   	 
   	     
   	     double layerThickness=1.0;// to divide the box just into two layers to make code easier
               
               int Nl=(int)Math.floor(Lz/layerThickness);//number of layers.  we divide width of x direction by bead size ( layer thickness) to reach the number of layers. 
              // double total_DimensionLayer=total_DimensionX/Nl;
            
	           double [] numBead1Layers=new double [Nl+1]; //the summation of number of beads of type 1 in each layers. so we see 6 beads of type 5 in layer 1. this array is only a function of layers and not function of natoms
	         double [] numBead2Layers=new double [Nl+1];
	       double [] numBead3Layers=new double [Nl+1];
	     double [] numBead4Layers=new double [Nl+1];
	  // double [][] numBead5Layers=new double [natoms+1][Nl+1];//i wrong because is function of natoms and layers so give each atom in each layer but we want how many atoms in each layer
	   double [] numBead5Layers=new double [Nl+1];        
	   double [] numBead6Layers=new double [Nl+1];         
	   double [] densityWater=new double [Nl+1];
	  
	   double [] densityToluene=new double [Nl+1];
	   
	   double [] densityPEO=new double [Nl+1];
		  
	   double [] densityPS=new double [Nl+1];
	           // double [][] xlayer=new double [natoms+1][Nl+1];
	            double [] zlayer=new double [Nl+1];
	            
	            //zlayer[0]=zlo;
	             zlayer[0]=0;
	           
	              int volumeOfLayer=(1*103*103);
	               
			    
			    for (int j=1;j<=Nl; j++) {
	            	   
	            	  
	                //xlayer[i][j]=xlayer[i-1][j-1]+layerThickness;
	                zlayer[j]=zlayer[j-1]+layerThickness; 
	                //zlayer[j]=zlayer[j]*Lz;//total dimension
	                		// System.out.println(j+"   "+xlayer[j]);
			    }
			    

	           for (int i=1;i<=natoms; i++) {					      			                
	              for (int j=1;j<=Nl; j++) {
	            	   
	               
	                
	                	 
	                	 if ((Z1[i] > zlayer[j-1]) && (Z1[i] <= zlayer[j])  ) {	//water	      			                		
	                		 
	                		  
	                	         numBead1Layers[j]= numBead1Layers[j]+1; //for atom type 1
	                	        // densityWater[j]= (numBead1Layers[j]*18.015)/(92*103*103);// 18.015 is water molar mass and 92 A is the z of water side of the box
	                	        // numBead1Layers[j]= numBead1Layers[j]*3; 
	                	         densityWater[j]= ((numBead1Layers[j]*10)/volumeOfLayer); //*10 to normalize to density of water =1.0
	                	     }
	                	 
	                		
	                	if ((Z2[i] > zlayer[j-1]) && (Z2[i] <= zlayer[j])) {//tol
		                	    numBead2Layers[j]=numBead2Layers[j]+1;
		                	   // densityToluene[j]= ((numBead2Layers[j]*2/3)*92.14)/(120*103*103);
		                	    //numBead2Layers[j]= numBead2Layers[j]*15;
		                	    densityToluene[j]= ((numBead2Layers[j]*10)/volumeOfLayer);
	                		} 
	                	
		                	    
	                	if ((Z3[i] > zlayer[j-1]) && (Z3[i] <= zlayer[j])  ) {//peo
			                	    numBead3Layers[j]=numBead3Layers[j]+1;
			                	    densityPEO[j]= ((numBead3Layers[j]*10)/volumeOfLayer);
		                		}
		                		
	                	if ((Z4[i] > zlayer[j-1]) && (Z4[i] <= zlayer[j])  ) {//ps
	                		
	                	  
   			                	    numBead4Layers[j]=numBead4Layers[j]+1;
   			                	 densityPS[j]= ((numBead4Layers[j]*10)/volumeOfLayer);
	                	}
			                		
	                
	                	    //}
	                	//System.out.println(j+"   "+numBead1Layers[i][j]);
	                	 }//for j   
	                  }//for i
	           
	           
	           for (int j=1;j<=Nl; j++) {
	          // System.out.println(j+"   "+numBead1Layers[j]+"   "+numBead2Layers[j]+"   "+numBead3Layers[j]+"   "+numBead4Layers[j]+"   "+numBead5Layers[j]+"   "+numBead6Layers[j]);
	          // System.out.println(j+"   "+numBead1Layers[j]+"   "+numBead2Layers[j]+"   "+numBead3Layers[j]+"   "+numBead4Layers[j]);
	          // System.out.println(j+"   "+densityWater[j]+"   "+densityToluene[j]+"   "+numBead3Layers[j]+"   "+numBead4Layers[j]);
	           System.out.println(j+"   "+densityWater[j]+"   "+densityToluene[j]+"   "+densityPEO[j]+"   "+densityPS[j]); 
	          // System.out.println(j+"   "+numBead1Layers[j]*3+"   "+numBead2Layers[j]*15+"   "+numBead3Layers[j]*7+"   "+numBead4Layers[j]*18);
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