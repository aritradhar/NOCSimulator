package com.grid;

import java.util.ArrayList;
import java.util.Arrays;

public class Matrix 
{
	static int grid_size  = 4;
	static int [][]node_matrix = new int[grid_size][grid_size];
	
	static ArrayList<Integer> distanceList;
	
	public static Integer getDistance(int x1, int y1, int x2, int y2)
	{
		Integer xd = (x1>x2)? (x1-x2) : (x2-x1);
		Integer yd = (y1>y2)? (y1-y2) : (y2-y1);
		return xd + yd;
	}
	
	
	
	public static Integer getMax()
	{
		Integer max = 0;
		for(int i = 0; i<distanceList.size();i++)
		{
			if(max< distanceList.get(i))
				max = distanceList.get(i);
		}
		return max;
	}
	
	public static void nodePlace()
	{
		int max_distance = 0, X=0,Y=0;
		for(int i = 0;i<grid_size;i++)
		{
			for(int j =0; j<grid_size; j++)
			{
				if(node_matrix[i][j] == 1)
					continue;
				
				if(max_distance < getDistance(0,0,i,j))
				{
					max_distance = getDistance(0,0,i,j);
					X = i;
					Y = j;
				}
			}
		}
		System.out.println("Max distance : " + max_distance+ " pos =("+X+", "+Y+")");
	}
	
	public static void main(String []ar)
	{
		//initialize
		node_matrix[0][0] = 1;
		distanceList = new ArrayList<>();
		
		nodePlace();
		
		
		//System.out.println(getMax());
	}
}
