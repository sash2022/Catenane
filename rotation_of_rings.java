
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
import org.apache.commons.*;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.BlockRealMatrix;
import org.apache.commons.math3.linear.SingularValueDecomposition;
import org.apache.commons.math3.linear.LUDecomposition;

/*for this code for each sample I should change: 
atom type which we want to check in backbone. for example in peo we should put the atom type of Oxygen
Here I could find easily the rotation of each ring separately. In previous code I could only have rotation of combination of two rings
*/
/*here with +-10 shift index I could capture forward and backward rotate. Since for peo in water we find this +-10 and we do not know it for ps and other solvents
 so we have it in this hard code format for shift index part*/
public class RMSD {
	
	
	 private static RealMatrix matrixA;
	 private static RealMatrix matrixB;
	 private static RealMatrix matrixA1;
	 private static RealMatrix matrixA2;

	 
	 private static RealMatrix expectedA;
	 private static RealMatrix expectedB;
	 private static RealMatrix expectedC;
	 private static RealMatrix AT;
	
	 private static RealMatrix cov;
	 private static RealMatrix multi;
	
	 
	 private static SingularValueDecomposition A;
	 private static RealMatrix U;
	 private static RealMatrix V;
	 private static RealMatrix sigma;
	 private static RealMatrix VT;
	 private static RealMatrix UT;
	
	 private static  LUDecomposition m;
	 private static RealMatrix optRotation;	 
	 private static  LUDecomposition OPTROTATION;
	 private static RealMatrix orthonormal;
	 private static RealMatrix matrixC;
	 private static RealMatrix matrixD;
	 private static RealMatrix matrixE;
	 private static RealMatrix matrixC1;
	 private static RealMatrix matrixD1;
	 private static RealMatrix matrixE1;
	 
	 private static RealMatrix matrixC2;
	 private static RealMatrix matrixD2;
	 private static RealMatrix matrixE2;
		

	 
	 
	 public static void main(String[] args) {

		 

	        String input_file="input.txt";//read from input file 

	        BufferedReader br = null;

	        String line = "";
	        String line1 = "";
	       

	        try {

	        @SuppressWarnings("resource")// in order to fix this problem (Resource leak: 'in2' is never closed) I add this line
			BufferedReader in = new BufferedReader(new FileReader(input_file));
	        @SuppressWarnings("resource")
			BufferedReader in2 = new BufferedReader(new FileReader(input_file));
	     
	       
	        ArrayList<String> boxdim = new ArrayList<String>();
	        
	            
	        
	        
	        
	        // make a jtable with header as its Column Identifier.
	        Vector<String> header = new Vector<String>(8);
	        header.add("AtmID");
	        header.add("AtmType");
	        header.add("X");
	        header.add("Y");
	        header.add("Z");
	        header.add("Xp");
	        header.add("Yp");
	        header.add("Zp");
	        	        
	        int numRows = 1 ;
	        DefaultTableModel model = new DefaultTableModel(numRows, 8) ;// I dont know why I put it 2 bc we have 8 columns? in 1/9/2018 :I change it to 8
	        model.setColumnIdentifiers(header);//Replaces the column identifiers in the model
	        JTable ATM_tbl = new JTable(model); 
	        
	        DefaultTableModel model1 = (DefaultTableModel) ATM_tbl.getModel();
	        
	        
	        
	        
	        
	        // make second jtable for reading  atom number and box dimension in first 9 lines of dump file//////////////////////////////////////////	        
	        Vector<String> header2 = new Vector<String>(2);
	        header2.add("lo");
	        header2.add("hi");
	      
	        int numRows2 = 1 ;
	        DefaultTableModel model2 = new DefaultTableModel(numRows2, 2) ;
	        model2.setColumnIdentifiers(header2);
	        JTable ATM_tbl2 = new JTable(model2);
	        
	        
	        
	        
	         
	          int numatm=0;
        	   ArrayList<Integer> timstp = new ArrayList<Integer>();  
        	   
        	   int frame=0;
        	   
        	   while((line1 = in2.readLine()) != null){      //for reading header of all frames I put the { after doing all activities. Since I want to use the header info for cooridinates of all frames
	        	  
	        	   
	//////////////////{reading header}///////////////////////////////////////////////////////////////////////////////////////////////////////////	 
	        	
	        	
  	        	   
        		   if (line1.contains("ITEM: TIMESTEP")) {   //for reading header of all frames After ITEM: TIMESTEP I put the { after doing all activities
    	        	   
    	        	   // for reading number of timesteps in header	        		
    	        	  
    	        			 while ((line1 = in2.readLine())!= null){
    	        				     if (line1.contains("ITEM: NUMBER OF ATOMS"))
    	 		        			 break; 
    	 		        		       timstp.add(Integer.parseInt(line1));		
    	 		        		       
    	 		        		       
    	        	     }			
    	        			// System.out.println(timstp);
    	        			 frame++;	 
    	        		    	
    		           }//end of item timestep	
	        	   
	        	   
	       
			        		 if (line1.contains("ITEM: NUMBER OF ATOMS")) {
			        			 while ((line1 = in2.readLine())!= null){
			        				     if (line1.contains("ITEM: BOX BOUNDS"))
			 		        			 break; 
			 		        		      numatm=Integer.parseInt(line1);	
			 		        		    
			        	}
			        			
			         }
			        		  
			        		 
			        		 
			        		 
			        		 if (line1.contains("ITEM: BOX BOUNDS")) {
			        			 while ((line1 = in2.readLine())!= null){			        				
			        				 if (line1.contains("ITEM: ATOMS"))
			 		        			 break;
			        				 line1=line1.trim();
					       	            boxdim.add(line1);			 //here add line1 to boxdime bc we want to make an array of line1 and the split the elements of line1 in that array       				
					       	         
			        			 }
			        			 
			        		 }//end of if boxdim	        
			        		
			        		
        	   }  //end of while null  of header
			        		
        	       //  int numMono=numatm/16;//for polystyrene
        	       // int numMono=numatm/7;//for peo
        	 // System.out.println(frame);          	
        	   
        	 // int numMono=4;//for circle
        	 // int numMono=8;//for two circles catenane
        	  //int numMono=120;//for two circles catenane
        	  
        	  
        	  
        	  
        	   
        	   for (String row : boxdim) {
	 	            row = row.trim();  //UPDATE	 	       
	 	            List<String> A = Arrays.asList(row.split("\\s+"));	 	
	 	           DefaultTableModel model3 = (DefaultTableModel) ATM_tbl2.getModel();
	 	            model3.addRow(new Object[]{(A.get(0)).trim(),(A.get(1)).trim()}); // in header we have two columns for box dimension so we just trim these two columns          
		        }
		
		        //read and name every index of the provided matrix
		        Object m1=ATM_tbl2.getModel().getValueAt(1, 0);// since numRows is 1 so row index start from 1 but as the regular method column index starts from 0
	          	  Object m2=ATM_tbl2.getModel().getValueAt(2, 0);//it means row 2 and column 1
	           	  Object m3=ATM_tbl2.getModel().getValueAt(3, 0);
		        		        
		          Object m4=ATM_tbl2.getModel().getValueAt(1, 1);// since numRows is 1 so row index start from 1 but as the regular method column index starts from 0
	          	  Object m5=ATM_tbl2.getModel().getValueAt(2, 1);//it means row 2 and column 2
	           	  Object m6=ATM_tbl2.getModel().getValueAt(3, 1);
	           	  
	           		           	  
	          	float LxMin=Float.parseFloat(m1.toString());
	          	float LyMin=Float.parseFloat(m2.toString());
	          	float LzMin=Float.parseFloat(m3.toString());
	          	
	          	float LxMax=Float.parseFloat(m4.toString());
	          	float LyMax=Float.parseFloat(m5.toString());
	          	float LzMax=Float.parseFloat(m6.toString());
	          	
	          	 //System.out.println(LzMax);
        	 
        	   
					          	
				//////////////////{reading coordinates}/////////////////////////////        

	        	 
	        	   while((line = in.readLine()) != null){
				    	
	        		   ArrayList<String> lines = new ArrayList<String>(); 	     	
	        	   
				        		 if (line.contains("ITEM: ATOMS")) {
						        	 
						        	 	
							        	while ((line = in.readLine())!= null){
							        	
							        	 if (line.contains("ITEM: TIMESTEP"))
							   	        	  break;
							        	        	
							        	line=line.trim();
							       	    lines.add(line);
							       	      
							            } // end of while !=null
							        	
							        	//System.out.println(lines);
							        	  
							        	
							        	 //split the columns of coordinates part and put them in the matrix
							        	 for (String row : lines) {
							     	            row = row.trim();  //UPDATE			     	     
							     	            List<String> A = Arrays.asList(row.split("\\s+"));			     	           
							     	            model1.addRow(new Object[]{(A.get(0)).trim(),(A.get(1)).trim(),(A.get(2)).trim(),(A.get(3)).trim(),(A.get(4)).trim(),(A.get(5)).trim(),(A.get(6)).trim(),(A.get(7)).trim()});
							     	            }
							        	
							        							        
					         }	//end of if (line.contains("ITEM: ATOMS")) of coords	
				        		
				        		 
				        		 
	        	   }//end of wile null of Item Atom
	        	   
	        	   	
	        	   
	        	   
	        	   
	        	   
	        	   
	        	           	   
	        	   
	        	   //int start_atm= 89374;
	       	   
	        	// int start_atm= 139336;
	        	  // int start_atm= 1;
	        	    int minStartAtm=10000000;
	          for(int i=0; i<frame; i++) { //largest for loop ( first large i)
		        			 
		          for(int k=1; k<=(numatm); k++){ //a for loop  to calculate base frame center of mass
		        			   	        			   
		        				
		        			 //for atomic coordinte  
		        			 int p=(numatm*i)+k;
		        			 
		        			 Object S1=ATM_tbl.getModel().getValueAt(p, 0);//these l and k are not row and column. they are counters of  for loop           
				            	
			            	 int Idi=Integer.valueOf(S1.toString());
			            	
			            	  
				               if (Idi<minStartAtm) {//find minimum of Idi 
				        	      minStartAtm=Idi;
			        			     
			        	              }//if
		                   }//k
		         
	        		  }  //i         
	        
	        	   
	        //  System.out.println( minStartAtm);
	        	
	        	   
	        	      int numMono=0;
	    			       	  for(int k=1; k<=(numatm); k++){
	    			       		        	
	    			       	  	             	
	    			       	  	            
	    			       			  Object T1=ATM_tbl.getModel().getValueAt(k, 1);//these l and k are not row and column. they are counters of  for loop
	    			       						 			      
	    			       	         if (T1.equals("3")){//from here we just check carbon of CH2 in backbone of PS or Oxygen in peo
	    			       	     // if (T1.equals("11")){    	
	    			       			// if (T1.equals("7") || T1.equals("13")){	  
	    			       	        	        	   
	    			       	          
	    			       	    	numMono++; 		       	  	 
	    			       	       }//if	       	     
	    			       	        	 
	    			       	   }//k
	        	 
	    			       //	System.out.println( numMono);
	        	   
	    			           					           	
	          //	double XoverallC=0;//for carbon of CH2 in backbone
	           //	double YoverallC=0;
	           //	double ZoverallC=0;	           	      
	        	   Double [] XoverallC=new Double [frame];
	        	   Double [] YoverallC=new Double [frame];               
	        	   Double [] ZoverallC=new Double [frame];
	     
	            
	            Double [] Xco=new Double [frame];
	            Double [] Yco=new Double [frame];
	            Double [] Zco=new Double [frame];   
	   
	       
	            
	            //atomic coordinates minus center of mass 
	            /*
	            Double [][] Xat_co=new Double[frame][numMono+1];//bc in i we go until <frame but for k we go until <=numatom 
	            Double [][] Yat_co=new Double[frame][numMono+1];
	            Double [][] Zat_co=new Double[frame][numMono+1];
	           */
	            
	               Double [][] Xat_co=new Double[frame][numatm+1];//bc in i we go until <frame but for k we go until <=numatom 
	 	            Double [][] Yat_co=new Double[frame][numatm+1];
	 	           Double [][] Zat_co=new Double[frame][numatm+1]; 
	            
	 	         
	 	           //we make Xat_co based on numMono bc we dont want to read all atoms of side groups like benzene. we just want to read backbone atoms.
	 	        
	 	           
	 	           
	 	           
	 	          Double Xi[][]=new Double[frame][numatm];
	        	   Double Yi[][]=new Double[frame][numatm];
	        	   Double Zi[][]=new Double[frame][numatm];   
	 	           
	     	  // Double Xi[][]=new Double[frame][numMono];
       	 //  Double Yi[][]=new Double[frame][numMono];
       	 //  Double Zi[][]=new Double[frame][numMono];
        	   
        	
        	   //for calculating optimal rotation matrix for ref frame. 
        	   // we use optR array bc the Xat-cm is an array based on i and k but want an array based on k and number of columns(3) so we have to use it as a convertor array. 
        	    //for puting all frames in one matrix
        	 
        	   
        	   
        	   
        	   double optR[][] =new double[numMono*frame][3];
        	   
        	  double optR1[][] =new double[numMono*frame][3];//bc in i we go until <frame but for k we go until <=numatom. //for second ring
     
        	  
              //we increase size of rows so it will cover all rows of all frames.
        	 //for calculating optimal rotation matrix for other frame
        	  
        	  //for putting each frame in one matrix
        	 //  double optR[][] =new double[numatm][3];
        
        	  
	        	double x1=0.0;
	        	double y1=0.0;
	        	double z1=0.0;
	        	
	        	double mx=0.0;
	        	double my=0.0;
	        	double mz=0.0;
        	    
	        	int IDi=0;
        	    
	        	
	        	int [][] index=new int[frame][numatm];
	        	int [][] indexC=new int[frame][numMono];
	        	
	        	
	   	     
				/* 
				 double[] sumX=new double[frame];
				 double[] sumY=new double[frame];
				 double[] sumZ=new double[frame];
				 
				 double[] meanX=new double[frame];
				 double[] meanY=new double[frame];
				 double[] meanZ=new double[frame];
				 
				 double[] minusX=new double[frame];
				 double[] minusY=new double[frame];
				 double[] minusZ=new double[frame];   
			    */ 
			
			// int p=0;
			// int v=0;

			 //fist ring
		        double Ex=0.0;
	          double Ey=0.0;
	          double Ez=0.0;
	          
	          double Ex2[]=new double [frame];
	          double Ey2[]=new double [frame];
	          double Ez2[]=new double [frame];
	          double LRmsd[]=new double[frame];
	          double LRMSD[]=new double[frame];
	          double LMSD[]=new double[frame];
	          
	          
	          //second ring
	          
	    	
	          
	          double Ex22[]=new double [frame];
	          double Ey22[]=new double [frame];
	          double Ez22[]=new double [frame];
	        
			 
	        	
	      
	         
  		     
  		       
  		 
	         
	     
	          
	          
   		     //ArrMatC means arry of matrix C. We want to convert matrixC to an array
   		       	// Double [][] ArrMatC=new Double[frame][numMono+1];
	          double ArrMatC[][] =new double[numMono*frame][3]; 
	         
   		       
   		 

 
		         //for shift index of atoms: 
	   		       /*  Double [][] shiftX1=new Double[frame][numMono+1];
	   		         Double [][] shiftY1=new Double[frame][numMono+1];
	   		         Double [][] shiftZ1=new Double[frame][numMono+1];
	   		         
	   		       Double [][] shiftX2=new Double[frame][numMono+1];
	 		         Double [][] shiftY2=new Double[frame][numMono+1];
	 		         Double [][] shiftZ2=new Double[frame][numMono+1];
	 		        */ 
	 		        Double [][] shiftX1=new Double[frame][numatm+1];
	  		         Double [][] shiftY1=new Double[frame][numatm+1];
	  		         Double [][] shiftZ1=new Double[frame][numatm+1];
	  		         
	  	
			         
			         
   		         //for puting the shifted index array in to the matrix
   		         double putMat1[][] =new double[numMono*frame][3];
   		       double putMat2[][] =new double[numMono*frame][3];  	
	        	
	        	
	        	
        	    
 //atomic position and centeroid ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   		          int w=0; 
   		        for(int i=0; i<frame; i++) { //largest for loop ( first large i)
	        		//  for(int i=0; i<1; i++) { //largest for loop ( first large i)
	        	
	        			  w=1;
	               for(int k=1; k<=(numatm); k++){ //a for loop  to calculate base frame center of mass
	        			   	        			   
	        				
	        			 //for atomic coordinte  
	        			 int p=(numatm*i)+k;
	        			 
	        			 Object S1=ATM_tbl.getModel().getValueAt(p, 0);//these l and k are not row and column. they are counters of  for loop           
			            	
		            	 int Idi=Integer.valueOf(S1.toString());
		            	 	 
		            	// IDi=Idi-start_atm;
		            	 IDi=Idi-minStartAtm;
		            	
	        			      S1=ATM_tbl.getModel().getValueAt(p, 2);//these l and k are not row and column. they are counters of  for loop
				          	  
			            	x1=Double.parseDouble(S1.toString());
			            	 
			            	 
			                 S1=ATM_tbl.getModel().getValueAt(p, 3);
			          	  
			                 y1=Double.parseDouble(S1.toString());
			                
			                 
			                 S1=ATM_tbl.getModel().getValueAt(p, 4);
			          	 
			                  z1=Double.parseDouble(S1.toString());  
			                 
			                 
			                 
			                 // for image flags
			                 S1=ATM_tbl.getModel().getValueAt(p, 5);				          	 
			                 mx=Double.parseDouble(S1.toString());
			                 
			                 S1=ATM_tbl.getModel().getValueAt(p, 6);				          	 
			                 my=Double.parseDouble(S1.toString());
			                 
			                 S1=ATM_tbl.getModel().getValueAt(p, 7);				          	 
			                 mz=Double.parseDouble(S1.toString());
			                  
			                 index[i][IDi]=IDi;//to put it in an array
			               
		       	  	            
			       			  Object T1=ATM_tbl.getModel().getValueAt(p, 1);//these l and k are not row and column. they are counters of  for loop
			       			// if (T1.equals("7") || T1.equals("13")){		 			      
			       	         if (T1.equals("3")){//here we define Xi of Oxygen atoms in peo
			       		//	if (T1.equals("11")){
			       	        	 Xi[i][index[i][IDi]]=x1;
			                 //Xi[i][index[i][IDi]]=((x1+mx)*(LxMax-LxMin));
				             Yi[i][index[i][IDi]]=((y1+my)*(LyMax-LyMin));
				             Zi[i][index[i][IDi]]=((z1+mz)*(LzMax-LzMin));
				          
			       	        /*	 
				            Xi[i][index[i][IDi]]=(x1);
				             Yi[i][index[i][IDi]]=(y1);
				            Zi[i][index[i][IDi]]=(z1);
				          */
				          //  indexC[i][w-1]=index[i][IDi]; //in this if statement index[i][IDi] depends on the statement but the indexC[i][w] does not depend so we can have it out of statement
				            
				             indexC[i][w-1]=index[i][IDi]; //in this if statement index[i][IDi] depends on the statement but the indexC[i][w] does not depend so we can have it out of statement
				           // System.out.println( Xi[i][index[i][IDi]]); 
				            w++; 
				          //  System.out.println(w-1+"    "+ index[i][IDi]); 
			        	      } //if (T1.equals("3"))	
			       	    //  System.out.println(w+"    "+ index[i][IDi]);
	  		             } //k
	              
	               
	                      //this part is for sorting the order of index of oxygen atoms of peo
		        		  for (int q=0; q<numMono; q++) {
		        			  	        			  
		        		        for (int z=q; z<numMono; z++) {
		        		        	
		        		        	
		        		       if(indexC[i][q] > indexC[i][z]) {

		        		        int temp=indexC[i][z];
		        		        indexC[i][z]=indexC[i][q];
		        		        indexC[i][q]=temp;
		        		               }
		        		            }
		        		       // System.out.println(q+"    "+indexC[i][q]);
		        		         }
		        	
		        		
	        		  }//end of large i    
	        	
	        		  
	        		
	        	  
		        	  
		  for(int i=0; i<frame; i++) { 	     
		            
		       	 
		       	  	    	XoverallC[i]=0.0;
		             		    
		            		 YoverallC[i]=0.0;
		             	
		             		 ZoverallC[i]=0.0;
		      
		     	        	    
		       	  for(int e=1; e<=(numMono); e++){     	
		       		//System.out.println(Xi[i][indexC[i][e-1]]);
		       		 	XoverallC[i]+= Xi[i][indexC[i][e-1]]; 
		       			 YoverallC[i]+= Yi[i][indexC[i][e-1]]; 
		           		ZoverallC[i]+= Zi[i][indexC[i][e-1]];           		
		       		  } //e          

		       //	System.out.println(XoverallC[i]);	    	 	 
		                    Xco[i]=XoverallC[i]/(numMono);//XoverallC is different for each frame so Xco will be different
		                    Yco[i]=YoverallC[i]/(numMono);
		                    Zco[i]=ZoverallC[i]/(numMono);
		                   // System.out.println(Xco[i]);
		       	  }//i
		        
		       	 // System.out.println(d);
		       	    
		       	    
		        
		       	   // for(int i=0; i<3; i++) {
		       	  for(int i=0; i<frame; i++) { //we need to have xi of all frames so we should close our previous for loop to have all xi and then start new loop to work over those xi. 
		       		 
		       			  for(int e=1; e<=(numMono); e++){
		                         //atomic-centeroid
		       		                Xat_co[i][indexC[i][e-1]]=Xi[i][indexC[i][e-1]]-Xco[i];				       			    
			       	  			    Yat_co[i][indexC[i][e-1]]=Yi[i][indexC[i][e-1]]-Yco[i];
			       	  				Zat_co[i][indexC[i][e-1]]=Zi[i][indexC[i][e-1]]-Zco[i];
			       	  				

		       	  			//System.out.println(Xat_co[i][indexC[i][e-1]]);
		       	      }//e
		       			//System.out.println(Xat_co[i][indexC[i][0]]);
		       			//System.out.println("--------------");
		       	  } //i    
		        
	        	  
	        	  
	        	
		       	  
		       	  
		       	  
		       	  
		       	  
		       	  
		       	  
		       	  
		       	  
		       	  
		        	 
		       	//covariance matrices/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

		       					  				 			 
		       		        	        int a=0;
		       					        int b=0;
		       			 for (int l=0; l < optR1.length; l++) {			//optR1 and optR2 are similar so I just put one of them here	
		       		  					//for (int m=0; m < optR[l].length; m++) {
		       		  						a=optR1.length;
		       		  						b=optR1[l].length;
		       		  					
		       		  					    matrixA = new BlockRealMatrix(numMono,b);	       		  						
	       		  						    matrixB = new BlockRealMatrix(numMono,b);
	       		  						    matrixC = new BlockRealMatrix(numMono,b);
	       		  						matrixD = new BlockRealMatrix(numMono,b);
	       		  					matrixE = new BlockRealMatrix(numMono,b);
		       		  						matrixA1 = new BlockRealMatrix(numMono,b);
		       		  						matrixA2 = new BlockRealMatrix(numMono,b);
		       		  						
		       		  					//	matrixB1 = new BlockRealMatrix(numMono,b);
		       		  					//	matrixB2 = new BlockRealMatrix(numMono,b);
		       		  						expectedA = new BlockRealMatrix(numMono,b);
		       		  						expectedB = new BlockRealMatrix(numMono,b);
		       		  					expectedC = new BlockRealMatrix(numMono,b);
		       		  						AT = new BlockRealMatrix(b,numMono);	  						
		       		  						multi = new BlockRealMatrix(b,b);
		       		  						cov = new BlockRealMatrix(b,b);
		       		  						U = new BlockRealMatrix(b,b);//since our cov matrix is 3*3 so all svd matrices are 3*3
		       		  						
		       		  						V = new BlockRealMatrix(b,b);
		       		  					
		       		  						sigma = new BlockRealMatrix(b,b);
		       		  						VT = new BlockRealMatrix(b,b);
		       		  						UT = new BlockRealMatrix(b,b);
		       		  						optRotation= new BlockRealMatrix(b,b);
		       		  						 orthonormal = new BlockRealMatrix(b,b);
		       		  					
		       		  						matrixC1 = new BlockRealMatrix(numMono,b);
		       		  						matrixD1 = new BlockRealMatrix(numMono,b);
		       		  						matrixE1 = new BlockRealMatrix(numMono,b);
		       		  						
		       		  						matrixC2 = new BlockRealMatrix(numMono,b);
		       		  						matrixD2 = new BlockRealMatrix(numMono,b);
		       		  						matrixE2 = new BlockRealMatrix(numMono,b);
		       					 }		
		       		        	 
		       		        	 
		       			    
		       			int ShiAmo=2;//Shift amount which we use. for example shift index of 4+ and 4- gives ShiAmo=8. It should be an EVEN number
		       			 
		       	        
		       			 //for shift over 256 index. we want searching up to ±8 index shifts and since we have two rings we need 16*16 combination of shift indexing
		       	       	
		       			int[] origin= new int[ShiAmo*ShiAmo];

		       	        for (int i=0; i< origin.length; i++)
		       	        {
		       	             origin[i] =i;//it is for shift index part(forward)	and min RMSD part(forward)	      
		       	            
		       	        }
		  
		       	   //Integer origin2[]= {59,58,57,56,55,54,53,52,51,50}  ; //it is for shift index part(backward)	   
		       	    // Integer origin2[]= {7,6,5,4}  ;
		      		 // Integer origin2[]= {7,6,5,4,3,2,1,0}  ; //it is for shift index part(backward)
		       	   
		       	 
		       	    
		       		int[] origin2= new int[numMono/2];
		            int u=0;
	       	        for (int j=(numMono/2)-1; j>=0 ; j--)
	       	        {
	       	             origin2[u] =j;//it is for shift index part(forward)	and min RMSD part(forward)	      
	       	       //  System.out.println(origin2[u]); 
	       	             u++; 	       	      
	       	        }
	       	   
		       	  
		       	
		      		// Integer origin2[]= {3,2,1}  ;
	

		      		//Integer origin3[]= {-1,-2,-3,-4,-5,-6,-7,-8,-9,-10}  ;//it is for min RMSD part(backward)
		       	    //Integer origin3[]= {-1,-2,-3,-4}  ;
		       	   //  Integer origin3[]= {-1,-2,-3,-4,-5,-6,-7,-8}  ;
		      		 
		    		int[] origin3= new int[numMono/2];
                      int count=0;
	       	       for (int i=-1; i>=-(numMono/2) ; i--)
	       	        {
	       	             origin3[count] =i;//it is for shift index part(forward)	and min RMSD part(forward)	      
	       	         // System.out.println(origin3[count]);    
	       	       count++;
	       	        }
		      		
		      		
	
		      		   
	       	    
			      	  int timestep=1;     
			      		 for(int i=0; i<frame-timestep; i+=timestep){ 	//the largest for loop
		      		          
		      					   double min=1000;
		      					   int minIndex=0;  
		      					  int  minIndex1=0;
		      					// double min2=1000;
		    					   int minIndex2=0;  
		    					
		    					   int o=0;//counter for y and y2	 
		                           int y=0;
		                           int y2=0;
		                           
		        for ( y=0; y<ShiAmo; y++){	
		        for ( y2=0; y2<ShiAmo; y2++){
		        	 		        	 
		        	 // for ( y=0; y<20; y++){	//we want searching up to ±8 index shifts in first ring
		 		     //    for ( y2=0; y2<20; y2++){//we want searching up to ±8 index shifts in second ring
		        	int t=i+timestep;
		      								 
		      						 Ex2[t]=0;
		      						 Ey2[t]=0;
		      						 Ez2[t]=0;
		      						 
		      		         
		      						 Ex22[t]=0;
		      						 Ey22[t]=0;
		      						 Ez22[t]=0;		   
		       			

		       				 
		       				 
		       		 // for(int e=1; e<=(numMono); e++){ //for shift index
		       			  for(int e=1; e<=(numMono); e++){ //for shift index       			     
		       		        			       int p=(numMono*i)+e-1;
		       				  				
		       		        
		       		        			      int v=(numMono*t)+e-1;
		       		        				     
		       		        				 //for making an 3*N matrices
		       				  				 // optR[p][0]=Xat_co[i][k-1];// both are function of i
		       		        			      optR[p][0]=Xat_co[i][indexC[i][e-1]];
		       				  				  optR[p][1]=Yat_co[i][indexC[i][e-1]];
		       				  				  optR[p][2]=Zat_co[i][indexC[i][e-1]];
		       				  				  
		       				  				   optR[v][0]=Xat_co[t][indexC[t][e-1]];
		       					  			   optR[v][1]=Yat_co[t][indexC[t][e-1]];
		       					  			   optR[v][2]=Zat_co[t][indexC[t][e-1]];
		       				  				  
		       				  
		       				  				  
		       				  
		       	        	       // then read each frame from optR array and put it in each matrices 
		       	
		       				  				matrixA.setEntry(e-1,0,optR[p][0]);// Matrix A And B are for the whole catenane( two rings) bc we want to superimpose (by optRot matrix) the catenane of first frame on catenane of second one. 
		       				  				matrixA.setEntry(e-1,1,optR[p][1]); 
		       				  				matrixA.setEntry(e-1,2,optR[p][2]);
		       				  				
		       				  				
		       				  				matrixB.setEntry(e-1,0,optR[v][0]);//to add rows of other frames I use numatm*i
		       				  				matrixB.setEntry(e-1,1,optR[v][1]); 
		       				  				matrixB.setEntry(e-1,2,optR[v][2]);	
		       				  				//System.out.println( matrixA);
		       				  		
		       				  				
		       		         }//e
		       		 
		     //  	 } //i
		       	   //matrixA and matrixB are just after translation of centeroid. Now we want to find optRotat to do alignment
		       			   
		       	
		       	    
  
					          
		 //Shift INdex////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////    	          
					        
					         

      						 
     
      //Shift Index of two Rings simultaneously//////////////////////////////////////////////////////////////////////  
		 				
      	// for(int e=1; e<=(numMono); e++){ //a for loop  to calculate first ring shift index
      		 for(int e=1; e<=(numMono); e++){ //a for loop  to calculate first ring shift index
				  				
		        
		        			      int  h=(numMono*t)+e-1;
		        	                                                                                                                                  		  

				  				  
				  				  
				  				  //we want to make three arrays of three columns of matrixC. it means to convert matrixC to array
				  				 ArrMatC[h][0]=matrixB.getEntry(e-1,0); //we just want to read frame 2. 
				  				 ArrMatC[h][1]=matrixB.getEntry(e-1,1); 
				  				 ArrMatC[h][2]=matrixB.getEntry(e-1,2); 
				  			
				  	
				  				 
				  				
									 shiftX1[t][indexC[t][e-1]]=ArrMatC[h][0];
								
				  				    shiftY1[t][indexC[t][e-1]]=ArrMatC[h][1];
				  				    shiftZ1[t][indexC[t][e-1]]=ArrMatC[h][2];
				  				 
				  					
					  				
				  				  
				  			 		
							  		 if (e<=(numMono/2)){//firstRing
							  			// if (y<2){
							  				
							  					// if (y<=(numMono/2)){
							  			   if (y<(ShiAmo/2)){//first 8 shift index is considered for forward rotation
							  					// putMat1[h][0]=shiftX1[t][indexC[t][(e-1 + origin[y]) % ((indexC[t].length)/2)]];	
							  					putMat1[h][0]=shiftX1[t][indexC[t][(e-1 + origin[y]) % ((indexC[t].length)/2)]];
							  					//System.out.println(e+"  "+y+"   "+ (e-1 + origin[y]) % ((indexC[t].length)/2));
							  					  matrixC.setEntry(e-1,0,putMat1[h][0]);
							  					}
							  					  			
							  			// if (y>=2){
							  			if (y>=(ShiAmo/2)){//second 8 shift index is considered for backward rotation
							  			
								  					//putMat1[h][0]=shiftX1[t][indexC[t][(e-1 + origin2[y-2]) % ((indexC[t].length)/2)]];	
								  					putMat1[h][0]=shiftX1[t][indexC[t][(e-1 + origin2[y-(ShiAmo/2)]) % ((indexC[t].length)/2)]];
								  					//System.out.println(e+"  "+y+"   "+  indexC[t].length );
								  					
								  					matrixC.setEntry(e-1,0,putMat1[h][0]);
								  					
							  				}//y
							  		
							  			 }//e
							  			    
							 
				  			 
				  				
				  					
							  		  
					  				  if (e<=(numMono/2)){//firstRing
					  					//if (y<=2){
					  						if (y<(ShiAmo/2)){
					  					putMat1[h][1]=shiftY1[t][indexC[t][(e-1 + origin[y]) % ((indexC[t].length)/2)]];	
					  					matrixC.setEntry(e-1,1,putMat1[h][1]); 
					  					}
					  					//if (y>2){
					  						if (y>=(ShiAmo/2)){
					  						//putMat1[h][1]=shiftY1[t][indexC[t][(e-1 + origin2[y-3]) % ((indexC[t].length)/2)]];	
					  						putMat1[h][1]=shiftY1[t][indexC[t][(e-1 + origin2[y-(ShiAmo/2)]) % ((indexC[t].length)/2)]];
						  					matrixC.setEntry(e-1,1,putMat1[h][1]); 	
					  					  }
				  						}
				  				     	
				  				
				  				  
					  				  
					  					if (e<=(numMono/2)){//firstRing
					  						//if (y<=2){
					  							if (y<(ShiAmo/2)){
					  				      putMat1[h][2]=shiftZ1[t][indexC[t][(e-1 + origin[y]) % ((indexC[t].length)/2)]];	
					  				     matrixC.setEntry(e-1,2,putMat1[h][2]);
					  						  }
					  						//if (y>2){
					  							if (y>=(ShiAmo/2)){
					  							// putMat1[h][2]=shiftZ1[t][indexC[t][(e-1 + origin2[y-3]) % ((indexC[t].length)/2)]];
					  							putMat1[h][2]=shiftZ1[t][indexC[t][(e-1 + origin2[y-(ShiAmo/2)]) % ((indexC[t].length)/2)]];
								  				  matrixC.setEntry(e-1,2,putMat1[h][2]);
					  						  }
					  						}
				  						
				  				   
				  					
				  				
				  			
								 // 	 for(int f=1; f<=(numMono); f++){			
				  	 for(int f=1; f<=(numMono); f++){	
				  					    int  g=(numMono*t)+f-1;
				  					 /// System.out.println(y+"   "+e+"      "+putMat1[h][0]);
				  					 // System.out.println(e+"  "+y+"   "+ putMat2[8][0]);
				  					    
				  					  //we want to make three arrays of three columns of matrixC. it means to convert matrixC to array
						  				 ArrMatC[g][0]=matrixB.getEntry(f-1,0); //we just want to read frame 2. 
						  				 ArrMatC[g][1]=matrixB.getEntry(f-1,1); 
						  				 ArrMatC[g][2]=matrixB.getEntry(f-1,2); 
						  			
						  				//	System.out.println(e+"   "+f+"   "+matrixD); 
						  				 
						  				
											 shiftX1[t][indexC[t][f-1]]=ArrMatC[g][0];
											// ArrMatC[v][0]=indexC[d-1];
						  				    shiftY1[t][indexC[t][f-1]]=ArrMatC[g][1];
						  				    shiftZ1[t][indexC[t][f-1]]=ArrMatC[g][2];
				  					    
						  				    
						  				    
						  				  if (f>(numMono/2)){//secondRing
						  						
							  					//if (y2<=2){
							  						if (y2<(ShiAmo/2)){//forward
							  					putMat2[g][0]=shiftX1[t][indexC[t][(f-1 + origin[y2]) % ((indexC[t].length)/2)+(numMono/2)]];	
							  					//System.out.println(f+"  "+y2+"   "+ indexC[t][(f-1 + origin[y2]) % ((indexC[t].length)/2)+(numMono/2)]);
							  				   matrixC.setEntry(f-1,0,putMat2[g][0]);
							  					}
							  					 //if (y2>2){
							  						if (y2>=(ShiAmo/2)){//backward
							  						putMat2[g][0]=shiftX1[t][indexC[t][(f-1 + origin2[y2-(ShiAmo/2)]) % ((indexC[t].length)/2)+(numMono/2)]];
							  						//we add numMono/2  for having index for second ring. Origin2{y2-4} is for backward shift index
							  					//	System.out.println(f+"  "+y2+"   "+ indexC[t][(f-1 + origin2[y2-4]) % ((indexC[t].length)/2)+(numMono/2)]); 
									  				   matrixC.setEntry(f-1,0,putMat2[g][0]);
							  					}
							  				 }
								
						  				//System.out.println(indexC[t].length);
					  				 
					  				//System.out.println(y+"   "+shiftX2[t][indexC[t][e-1]]); 	 

				  					 
						  				if (f>(numMono/2)){//secondRing
					  						   //if (y2<=2){
					  							if (y2<(ShiAmo/2)){
				  						putMat2[g][1]=shiftY1[t][indexC[t][(f-1 + origin[y2]) % ((indexC[t].length)/2)+(numMono/2)]];	
				  						matrixC.setEntry(f-1,1,putMat2[g][1]);	
				  						}
					  						if (y2>=(ShiAmo/2)){
					  							putMat2[g][1]=shiftY1[t][indexC[t][(f-1 + origin2[y2-(ShiAmo/2)]) % ((indexC[t].length)/2)+(numMono/2)]];	
						  						matrixC.setEntry(f-1,1,putMat2[g][1]);
					  						}
					  					}
							  		//		 else
							  		//	putMat2[g][1]=shiftY1[t][indexC[t][e-1]];
					  					
					  				     	
					  					
						  				if (f>(numMono/2)){//secondRing
					  						//if (y2<=2){
					  							if (y2<(ShiAmo/2)){
				  						putMat2[g][2]=shiftZ1[t][indexC[t][(f-1 + origin[y2]) % ((indexC[t].length)/2)+(numMono/2)]];								  									 
				  						matrixC.setEntry(f-1,2,putMat2[g][2]);
					  					}
					  						//if (y2>2){
					  							if (y2>=(ShiAmo/2)){
					  							putMat2[g][2]=shiftZ1[t][indexC[t][(f-1 + origin2[y2-(ShiAmo/2)]) % ((indexC[t].length)/2)+(numMono/2)]];								  									 
						  						matrixC.setEntry(f-1,2,putMat2[g][2]);	
					  						}
					  					}
							  		//		 else
							  		//	putMat2[g][2]=shiftZ1[t][indexC[t][e-1]];
					  						
					  				//	System.out.println("---------  ");
					  					// System.out.println(y+"   "+e+"   "+f+"   "+putMat1[h][0]); 
					  					
					  					// System.out.println(y+"   "+e+"   "+f+"   "+putMat2[g][0]); 
					  					//System.out.println(y+"   "+e+"   "+f+"   "+matrixD);				
		         }//f
					
      		
      	 }//e 

      		//System.out.println( shiftX1[t][indexC[t][0]]);
      //	System.out.println("---------  ");
     // System.out.println(y+"    "+matrixD);
      //	  matrixE = matrixD.subtract(matrixA);
   	
      	 
			  
      	//////////////////////////////////////////////////////////////////////////////////////////////////						  
      						  
      
      	 
      	 
      	 
    ///covariance//////////////////////////////////////////////////////////////////////////////////////////////////	
      	 
       
		 
		 double[] sumX=new double[frame];
		 double[] sumY=new double[frame];
		 double[] sumZ=new double[frame];
		 
		 double[] meanX=new double[frame];
		 double[] meanY=new double[frame];
		 double[] meanZ=new double[frame];
		 
		 double[] minusX=new double[frame];
		 double[] minusY=new double[frame];
		 double[] minusZ=new double[frame];   
      	 
    	 sumX[i]=0;
		 sumY[i]=0;
		 sumZ[i]=0;
		 
		 sumX[t]=0;
		 sumY[t]=0;
		 sumZ[t]=0;
		 				 
		 meanX[i]=0;
		 meanY[i]=0;
		 meanZ[i]=0;
		 
		 meanX[t]=0;
		 meanY[t]=0;
		 meanZ[t]=0;
		 
		 Ex2[t]=0;
		 Ey2[t]=0;
		 Ez2[t]=0;
		 
		 
		 
        	// for(int e=1; e<=(numMono); e++){ //a for loop  to calculate base frame center of mass
        		 for(int e=1; e<=(numMono); e++){ //a for loop  to calculate base frame center of mass	     
        	
		  				
		  				sumX[i]+=matrixA.getEntry(e-1,0);
		  				sumY[i]+=matrixA.getEntry(e-1,1);;
		  				sumZ[i]+=matrixA.getEntry(e-1,2);;		  				
		  				
		  				sumX[t]+=matrixC.getEntry(e-1,0);
		  				sumY[t]+=matrixC.getEntry(e-1,1);
		  				sumZ[t]+=matrixC.getEntry(e-1,2);
		  				
		  				
		  				 
		  				meanX[i]=sumX[i]/numMono;
		  				meanY[i]=sumY[i]/numMono;
		  				meanZ[i]=sumZ[i]/numMono;
		  				
		  				meanX[t]=sumX[t]/numMono;
		  				meanY[t]=sumY[t]/numMono;
		  				meanZ[t]=sumZ[t]/numMono;
		  				
         }//e
        		 //System.out.println(matrixC);
        		 //System.out.println(sumX[t]);
                      //a for loop  to calculate multi
      // for(int e=1; e<=(numMono); e++){ 
    	   for(int e=1; e<=(numMono); e++){
		  				minusX[i]=matrixA.getEntry(e-1,0)-meanX[i];
		  				minusY[i]=matrixA.getEntry(e-1,1)-meanY[i];
		  				minusZ[i]=matrixA.getEntry(e-1,2)-meanZ[i];
		  				
		  				minusX[t]=matrixC.getEntry(e-1,0)-meanX[t];
		  				minusY[t]=matrixC.getEntry(e-1,1)-meanY[t];
		  				minusZ[t]=matrixC.getEntry(e-1,2)-meanZ[t];
		  				
		  				expectedA.setEntry(e-1,0,minusX[i]);//to add rows of other frames I use numatm*i
		  				expectedA.setEntry(e-1,1,minusY[i]); 
		  				expectedA.setEntry(e-1,2,minusZ[i]);
		  			
		  				expectedC.setEntry(e-1,0,minusX[t]);//to add rows of other frames I use numatm*i
		  				expectedC.setEntry(e-1,1,minusY[t]); 
		  				expectedC.setEntry(e-1,2,minusZ[t]);
		  				
		  				 AT = expectedA.transpose();
		  				 multi=AT.multiply(expectedC);
		  	        	               
       }//d
    	  // System.out.println(expectedC);
    	   // System.out.println(multi);
    	   
    	   //System.out.println(matrixC);
                        //a for loop  to calculate cov
		          	      double devideA =0.0;
		          	  
			      for (int l=0; l < b; l++) {
  					   for (int m=0; m < b; m++) {
  						
		   					//devideA=(multi.getEntry(l,m))/(numatm-1);//we use n-1 to have unbiased variance
		   					devideA=(multi.getEntry(l,m))/(numMono);//we use n so we get same answer as short method cov=PTQ
		   					cov.setEntry(l,m,devideA);// this cov code has a problem that I should fix. 
		   				 
				}//m
			 
			 }//n
			  
			
		              // second cov method
	                  // cov = (matrixA.transpose()).multiply(matrixC);
	                  // System.out.println(cov);
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	     
	     
	     
	     
	     
//  opt Rot////////////////////////////////////////////////////////////////////////////////////////////////////////////////////   
		      
		 //calculating SVD
		 A = new SingularValueDecomposition(cov);// A is svd of cov nut we can not print it. We just can print each of three elements of it(u v sigma). 
		 U=A.getU();
		 V=A.getV();
		 VT=V.transpose();
		 UT=U.transpose(); //I should follow the same way as literature does so we have A=u(sigma)vT. So I should use VT not UT
		 sigma=A.getS();// the singular values
		 
		// System.out.println(cov);
		 // we want to calculate  det(U.VT) which is equal to det(U).det(V)   
	
		 
		 
		 //I should follow the same way as literature does so we have A=u(sigma)vT. So I should use VT not UT
		 RealMatrix M=V.multiply(UT);				
		 m = new LUDecomposition(M);// the library which we need to use for calculating determinant				
		 double detVUT= m.getDeterminant();
		 
		 
		//making identity matrix with considering detUVT as its last element.
		int dimension = 3;					
		RealMatrix identity = MatrixUtils.createRealIdentityMatrix(dimension);// to make identity matrix
		identity.setEntry(2,2,detVUT);//instead of writing (3,3,setUVT) we write this way bc indexing columns start from 0. so we have 0 1 2
		
		//optimal Rotation matrix				
		optRotation=V.multiply(identity).multiply(UT);
		// System.out.println(optRotation);
		 
		//to check if it is orthonormal matrix I should check determinant of it. if it is negetive I should correct my matrix.
		OPTROTATION = new LUDecomposition(optRotation);
		 double OPR= OPTROTATION.getDeterminant();
		
		 
		//to see if we have right hnded coordinates or not. we should have U*UT=I 					
		 orthonormal =optRotation.multiply(optRotation.transpose());
	
		
		//////////////////////////////////////////////////////////////////////////////////
		 
		
//Apply optRot//////////////////////////////////////////////////////////////////  
		 
		  // RealMatrix E = matrixA.subtract(matrixB);
  matrixD=matrixC.multiply(optRotation);// to apply our optimal rotation matrix on our second matrix and therefore
	  
  //matrixD=matrixC;	
	   //finding  the RMSD after applying kabcsh algorithm	

	   
//////end oF alignment /////////////////////////////////////////////////////////////////////////////////////////// 
	
//	System.out.println("-------");
	

//System.out.println(optRotation);

   	  
   	  
matrixE = matrixD.subtract(matrixA); 
   	  
      						  
      				 

      	 //RMSD///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////    	          
      									        
      									 					 
			//RMSD for first ring	////////////////////////////////////////////////////	            					        	  
		// for(int e=1; e<=numMono; e++){ 
			 for(int e=1; e<=numMono; e++){ 		        			    
					        			 //since in  Xi[IDi][i] the IDi is from 0 to 959 so we write k-1.					        			   
				                   //infact we defined Xi by IDi so if IDi is 5 so we have Xi[5][i]. Then when we write Xi[k-1][i] k-1 starts from 0 so we have Xi[0][i]and when it reaches 5 we have Xi[5][i]. Therefor
				                    // we have the Xi in a ordered manner. So we ordered the unodered dumpfile! Thanks God!	        			   
			 
								            					        			   
					        				Ex=matrixE.getEntry(e-1,0);//Ex is dx=(x2-x1)
							  				Ey=matrixE.getEntry(e-1,1);;//Ey is dy=(y2-y1)
							  				Ez=matrixE.getEntry(e-1,2);;
							  				
							  				/*
							  				 Ex2[i]+=Math.pow(Ex,2);//we use t bc we want to see rmsd of second frame with respect to first frame. and it is is for succesive frames.
								  				Ey2[i]+=Math.pow(Ey,2);
								  				Ez2[i]+=Math.pow(Ez,2);
								  	
								  				 LRmsd[i]= Ex2[i]+Ey2[i]+ Ez2[i];
								  				 */
							  				
							  			   Ex2[t]+=Math.pow(Ex,2);//we use t bc we want to see rmsd of second frame with respect to first frame. and it is is for succesive frames.
							  				Ey2[t]+=Math.pow(Ey,2);
							  				Ez2[t]+=Math.pow(Ez,2);
							  	
							  				 LRmsd[t]= Ex2[t]+Ey2[t]+ Ez2[t];
							  				
							  			
					 }//d
		
					        		 LMSD[t]= LRmsd[t]/(numMono);//bc t is from 1 to frame but i is from 0 to frame-1
					        		  LRMSD[t]=Math.pow(LMSD[t],0.5);// atomic rmsd after moving centeroid of rings to the origin of the coords
					        			  
					        			
						        	
					        			 if (LRMSD[t]<min) {//find minimum of RMSD and the index related to that min RMSD
					        				   min=LRMSD[t];
					        			      // minIndex=origin[y];
					        				   minIndex=origin[o];
					        				   
					        				  //first Ring
					        				   //if (y<2){
					        					   if (y<(ShiAmo/2)){//forward
						        					  minIndex1=origin[y];
						        					  //minIndex1=y;
						        				    }
	                                            // if (y>=2){
	                                            	 if (y>=(ShiAmo/2)){//backward
	                                            	   minIndex1=origin3[y-(ShiAmo/2)];
	                                            	  // minIndex1=y;
						        				   }
	                                             
	                                            	 
	                                            //Second ring	 
					        				  // if (y2<2){
					        					   if (y2<(ShiAmo/2)){//forward
					        					 
						        				   minIndex2=origin[y2];
						        				  // minIndex2=y2;
					        				    }
                                             // if (y2>=2){
                                              	if (y2>=(ShiAmo/2)){//backward
                                          	  
  					        				   minIndex2=origin3[y2-(ShiAmo/2)]; 
  					        				// minIndex2=y2;
					        				   }
					        				
					        	          }		
					     	//System.out.println("First Ring"+"  "+i+"  "+t + "   "+origin[o]+"    "+ LRMSD[t]+"                        "+min+"    "+ minIndex); 
					   //  System.out.println(origin[o]+"    "+ LRMSD[t]+"        "+minIndex1+"    "+ minIndex2); 
					        			//  System.out.println(y+"  "+matrixA+"   "+matrixB+"   "+optRotation);	
					        			
					      	
					      	
					      	
					        			   
			        			   
					        			   o++; //counter of y and y2  	
					        			   
					        			   
		     }//y2
		        
		   }//y
		          
		        	  System.out.println(i+"      "+(i+timestep)+"      "+ min+"    "+  minIndex1+"    "+  minIndex2); 
		        	  
	}//end of large i 
			     
		 
		 ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		 			
	        	   
        	   
			        
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


	
		/*  
        //for shift over 60 index
        int[] origin= new int[60];
       // Random random = new Random();

        for (int i=0; i< origin.length; i++)
        {
             origin[i] =i;
            // System.out.println(origin[i]);
        }
       */ 


	 //for showing the rotation distinctive for each ring. So 1.3 mean one rotation for first ring and 3 rotation for second ring
	 //Double origin[]= {0.0,0.1,0.2,0.3,1.0,1.1,1.2,1.3,2.0,2.1,2.2,2.3,3.0,3.1,3.2,3.3}  ;


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