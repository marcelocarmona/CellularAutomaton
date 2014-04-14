package ar.edu.unlp.CellularAutomaton.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShapeIO {
	
	public void save(){
		int[][] shape = new int[][] {{1,0}, {2,1}, {2,2}, {1,2}, {0,2}};
		File f = new File("C:/Users/Administrador/Desktop/hola.hola");
		
		FileWriter w;
		try {
			w = new FileWriter(f);

		BufferedWriter bw = new BufferedWriter(w);
		PrintWriter wr = new PrintWriter(bw);  

		wr.append("#Life 1.06 \n");
		for (int i = 0; i < shape.length; i++) {
			int col = shape[i][0];
			int row = shape[i][1];
			wr.append(Integer.toString(col)+" "+Integer.toString(row)+"\n");
		}

		wr.close();
		bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public int[][] load(){
		int[][] shape = new int[][] {{1,0}, {2,1}, {2,2}, {1,2}, {0,2}};
		File f = new File("C:/Users/Administrador/Desktop/hola.hola");
		
		FileReader r;
		try {
			r = new FileReader(f);

		BufferedReader br = new BufferedReader(r);
//		PrintWriter wr = new PrintWriter(bw); 
		

        String linea;
        int numOflines=0;
        while((linea=br.readLine())!=null)
        	numOflines++;
        numOflines--;
        br.close();
        int[][] array = new int[numOflines][2];
        br = new BufferedReader(new FileReader(f));		System.out.println(br.readLine());
        for (int i = 0; i < numOflines; i++) {
        	linea=br.readLine();
        	String[] numbers = linea.split(" ");
			System.out.println(numbers[0]+" "+numbers[1]);
			int col = Integer.parseInt(numbers[0]);
			int row = Integer.parseInt(numbers[1]);
			array[i][0] = col;
			array[i][1] = row;
		}
		shape = array;
		
		
//		List<List<Integer>> arrayList2 = new ArrayList<>();
//		while((linea=br.readLine())!=null){
//			List<Integer> arrayList = new ArrayList<>();
//			String[] numbers = linea.split(" ");
//			System.out.println(numbers[0]+" "+numbers[1]);
//			int col = Integer.parseInt(numbers[0]);
//			int row = Integer.parseInt(numbers[1]);
//			arrayList.add(col);
//			arrayList.add(row);
//			arrayList2.add(arrayList);
//		}
//		int i = 0;
//		Integer[][] array2 = new Integer[arrayList2.size()][];
//		for (List<Integer> list : arrayList2) {
//			Integer[] array = new Integer[list.size()];
//			list.toArray(array);
//			array2[i++] = array;
//		}
//		Integer[] array = new Integer[arrayList2.size()];
//		arrayList2.toArray(array);
//		System.out.println("---------------");

			System.out.println(Arrays.toString(array[1]));

            



		br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return shape;
		
	}
	public static void main(String[] args) {
		int i = 2147483647;
		System.out.println(Math.pow(2,32));
		ShapeIO sio = new ShapeIO();
		sio.load();
		
	}

}
