package test;

import java.util.Random;

public class RandomString {
	 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String [] departmentID = {"2108825","2109061","2109062","2109063","2109064","2109065","2109066","2109067","2109068","2109069"};
			
		Random rad = null;
		int index = 0;
		
/*		for(int i=0;i<departmentID.length;i++){
			rad = new Random();
			index = rad.nextInt(departmentID.length);
			System.out.println("departmentID["+index+"]="+departmentID[index]);
		}*/
		
		
		StringBuffer [] sbs = new StringBuffer[10];
		sbs[0] = new StringBuffer();
		sbs[1] = new StringBuffer();
		sbs[2] = new StringBuffer();
		sbs[3] = new StringBuffer();
		sbs[4] = new StringBuffer();
		sbs[5] = new StringBuffer();
		sbs[6] = new StringBuffer();
		sbs[7] = new StringBuffer();
		sbs[8] = new StringBuffer();
		sbs[9] = new StringBuffer();
		sbs[0].append("2108825");
		sbs[1].append("2109061");
		sbs[2].append("2109062");
		sbs[3].append("2109063");
		sbs[4].append("2109064");
		sbs[5].append("2109065");
		sbs[6].append("2109066");
		sbs[7].append("2109067");
		sbs[8].append("2109068");
		sbs[9].append("2109069");
/*		for(int i=0;i<sbs.length;i++){
			rad = new Random();
			index = rad.nextInt(sbs.length);
			System.out.println("sbs["+index+"]="+sbs[index]);

		}*/
		
//		while(!(sbs[index].equals("2109069"))){
			while(!(sbs[index].toString().contentEquals("2109069"))){
			System.out.println("sbs["+index+"]="+sbs[index]);
			rad = new Random();
			index = rad.nextInt(sbs.length);
		}
			System.out.println("sbs["+index+"]="+sbs[index]);
		
	}

}
