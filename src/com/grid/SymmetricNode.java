package com.grid;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SymmetricNode
{
	public static int grid_size;
	
	public static Integer[] getSymmeticPositions()
	{
		Integer []pos = new Integer[4];
		
		pos[0] = (grid_size/4) * grid_size + (grid_size/4);
		
		pos[1] = (3 * grid_size/4) * grid_size + (grid_size/4);
		
		pos[2] = (grid_size/4) * grid_size + (3 * grid_size/4);
		
		pos[3] = (3 * grid_size/4) * grid_size + (3 * grid_size/4);
		
		return pos;
	}
	
	public static void init() throws IOException
	{
		String filename = "Min.txt";
		FileWriter fwrite  = new FileWriter("Optimal.txt");
		BufferedReader br = new BufferedReader(new FileReader(filename));
		
		String s = "";
		
		int counter = 0;
		while((s = br.readLine())!=null)
		{
			counter++;
			
			if(counter == 1)
				grid_size = Integer.parseInt(s);
			
			Integer []pos = getSymmeticPositions();
			
			String wirelessNodePos = pos[0].toString().concat(" ").
					concat(pos[1].toString()).concat(" ").
					concat(pos[2].toString()).concat(" ").
					concat(pos[3].toString());
			
			if(s.contains(wirelessNodePos))
			{
				System.out.println(s);
				fwrite.append("Optimal position : \n " + s + "\n");
				fwrite.append("Antenna position : \n");
				fwrite.append(pos[0] + "→       " + "←"+pos[2] + "\n");
				fwrite.append("↓ \\ \n");
				fwrite.append("\n\n\n\n\n\n");
				fwrite.append(" ↑        \\ \n");
				fwrite.append(pos[1] + "         " +pos[3] + "\n");
			}
		}
		
		fwrite.close();
		//System.out.println(grid_size);
	}
}
