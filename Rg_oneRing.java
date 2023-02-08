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


public class Rg {

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
	        
	            
	        
	        
	        // make second jtable for reading  atom number and box dimension in first 9 lines of dump file//////////////////////////////////////////	        
	        Vector<String> header2 = new Vector<String>(2);
	        header2.add("lo");
	        header2.add("hi");
	      
	        int numRows2 = 1 ;
	        DefaultTableModel model2 = new DefaultTableModel(numRows2, 2) ;
	        model2.setColumnIdentifiers(header2);
	        JTable ATM_tbl2 = new JTable(model2);
	        DefaultTableModel model3 = (DefaultTableModel) ATM_tbl2.getModel(); //Returns the TableModel that provides the data displayed by this JTable.
	           
	           int numatm=1;//does not matter if I put it 0 or one bexouse I will put line1 inside it later
	           while((line1 = in2.readLine()) != null){
		     	
	        	// for reading number of atom from header
			        		 if (line1.contains("ITEM: NUMBER OF ATOMS")) {
			        			 while ((line1 = in2.readLine())!= null){
			        				     if (line1.contains("ITEM: BOX BOUNDS"))
			 		        			 break; 
			 		        		      numatm=Integer.parseInt(line1);
			 		        		      
			 		        		     
			        	}			        			
			         }
			    //for reading box dimention from header
			        		 if (line1.contains("ITEM: BOX BOUNDS")) {
			        			 while ((line1 = in2.readLine())!= null){
			        				 if (line1.contains("ITEM: ATOMS"))
			 		        			 break;
			        				 line1=line1.trim();
					       	            boxdim.add(line1);
					       	         
			        			 }
			        		 }
			        		 if (line1.contains("ITEM: ATOMS")) 
		            			 break;
		       }
 
	         //  System.out.println(boxdim);
	           
		    //split the columns of box dimension part of header and put them in the matrix
		        for (String row : boxdim) {
	 	            row = row.trim();  //UPDATE	 	       
	 	            List<String> A = Arrays.asList(row.split("\\s+"));	 	         
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
	          	
		        float L=LxMax;//(it is not correct for current code)The size of the box. In this 3 dimensional periodic box we have same size in all directions. But for other kind of boxes I should regulate the L   
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////       
		        

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
		        DefaultTableModel model1 = (DefaultTableModel) ATM_tbl.getModel();//Returns the TableModel that provides the data displayed by this JTable.
		        	        
		        //finding freq and distance
                 float Delta_R= (float) 0.1;
                 int ind=0;
                 int n= (Math.round((10*L/2)-1));
                 
              double [] Freq1=new double[n+1];
              Arrays.fill(Freq1, 0);
              double [] Freq2=new double[n+1];
              Arrays.fill(Freq2, 0);
              
              Double [][] Distance1=new Double[numatm+1][numatm+1];//Distance is two dimentional array so we write it as Double []
            
            
              //read all lines of coordinates (between "ITEM: ATOMS" and next "ITEM: TIMESTEP") and put in to lines
		          int frame=0;
			      while((line = in.readLine()) != null){
			    	
			       ArrayList<String> lines = new ArrayList<String>();	
			     	   
			         if (line.contains("ITEM: ATOMS")) {
			        	 	
			        	while ((line = in.readLine())!= null){
			        	
			        	 if (line.contains("ITEM: TIMESTEP"))
			   	        	  break;
			        	        	
			        	line=line.trim();
			       	            lines.add(line);
			       	      
			            } // end of while !=null
			        	
			        	 //split the columns of coordinates part and put them in the matrix
			        	 for (String row : lines) {
			     	            row = row.trim();  //UPDATE			     	     
			     	            List<String> A = Arrays.asList(row.split("\\s+"));			     	           
			     	            model1.addRow(new Object[]{(A.get(0)).trim(),(A.get(1)).trim(),(A.get(2)).trim(),(A.get(3)).trim(),(A.get(4)).trim(),(A.get(5)).trim(),(A.get(6)).trim(),(A.get(7)).trim()});
			     	            }
			        
 //////////////////{Rg average code}///////////////////////////////////////////////////////////////////////////////////////////////////////////
			        	 
				           				        					           					           	
				           	double Xoverall=0;
				           	double Yoverall=0;
				           	double Zoverall=0;
				           					            
				            double sumDeltaR=0;
				            
				            				           	
			            for(int l=1; l<=(numatm); l++){//we use numatm/2 bc we want to find Rg of one ring of catenane so we just use half of atoms.   
			         
			                //for atomic coordinates
			          	    Object S1=ATM_tbl.getModel().getValueAt(l, 2);//these l and k are not row and column. they are counters of  for loop
			          	  
			            	double x1=Double.parseDouble(S1.toString());
			          	
			                			          	     
			                 S1=ATM_tbl.getModel().getValueAt(l, 3);
			          	  
			                 double y1=Double.parseDouble(S1.toString());
			                 
			                 
			                 S1=ATM_tbl.getModel().getValueAt(l, 4);
			          	 
			                 double z1=Double.parseDouble(S1.toString());
			                 
			              
			                 // for image flags
			                 S1=ATM_tbl.getModel().getValueAt(l, 5);
				          	 
			                 double ix=Double.parseDouble(S1.toString());
			                 
			                 S1=ATM_tbl.getModel().getValueAt(l, 6);
				          	 
			                 double iy=Double.parseDouble(S1.toString());
			                 
			                 S1=ATM_tbl.getModel().getValueAt(l, 7);
				          	 
			                 double iz=Double.parseDouble(S1.toString());
			             
			                double total_DimensionX=((x1+ix)*(LxMax-LxMin));
			                double total_DimensionY=((y1+iy)*(LyMax-LyMin));
			                double total_DimensionZ=((z1+iz)*(LzMax-LzMin));

           	            Xoverall=Xoverall+total_DimensionX;
                   		
                   		Yoverall=Yoverall+total_DimensionY;
                   		
	              		Zoverall=Zoverall+total_DimensionZ;
			  			
			      }//end of first for loop
			            
			         
			            //coordinates of R(ave): it give us the geometrical center of the chain. I mean average of position of all atoms give us the center of all positions at the middle of chain coil. 
		                  double Xave=Xoverall/(numatm);
		  				  double Yave=Yoverall/(numatm);
		  				  double Zave=Zoverall/(numatm);
		  				  
		
		  				  //for finding difference of Ri and R(ave) I mean differencr of position of each atom from the geometrical center. 
		  			for(int l=1; l<=(numatm); l++){			           
		 			         		  			        
				          	     Object S1=ATM_tbl.getModel().getValueAt(l, 2);//these l and k are not row and column. they are counters of  for loop
				          	 
				          	     double x1=Double.parseDouble(S1.toString());
				          	
				                				          	     
				                 S1=ATM_tbl.getModel().getValueAt(l, 3);
				          	  
				                 double y1=Double.parseDouble(S1.toString());
				                 
				                 
				                 S1=ATM_tbl.getModel().getValueAt(l, 4);
				          	 
				                 double z1=Double.parseDouble(S1.toString());
				                 
				                 
				                 // for image flags
				                 S1=ATM_tbl.getModel().getValueAt(l, 5);
					          	 
				                 double ix=Double.parseDouble(S1.toString());
				                 
				                 S1=ATM_tbl.getModel().getValueAt(l, 6);
					          	 
				                 double iy=Double.parseDouble(S1.toString());
				                 
				                 S1=ATM_tbl.getModel().getValueAt(l, 7);
					          	 
				                 double iz=Double.parseDouble(S1.toString());
				             
				                double total_DimensionX=((x1+ix)*(LxMax-LxMin));
				                double total_DimensionY=((y1+iy)*(LyMax-LyMin));
				                double total_DimensionZ=((z1+iz)*(LzMax-LzMin));

	           	          				  				  
				                 
				                 //(Ri-R(ave))^2:
				               double DeltaR=Math.pow((total_DimensionX-Xave),2) + Math.pow((total_DimensionY-Yave),2)+ Math.pow((total_DimensionZ-Zave),2); 
				               
				               sumDeltaR =sumDeltaR+ DeltaR;    
				              
			        }//end of second for loop	
		  		 
		  		  		 
		  		 //Rg^2:
		      	double Rg2=sumDeltaR/(numatm); // we calculate ave of distance of all atoms of chain from the geometrical center of the chain as Rg
		     	System.out.println(Rg2);
		       
		  				  
			         }
			        
			  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////       
			     // I put 1 bc our jtable has one empty row which does not relate to lines and we dont want to delete it in the loop so we do it out of for loop		           
			          while(model1.getRowCount() > 1)
		     	          {
		     	        	  model1.removeRow(0);
		     	          }		     	      
			          
			           frame++;
			           
			       	          				     
			         }//End of while
			     // System.out.println(frame);
		  

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