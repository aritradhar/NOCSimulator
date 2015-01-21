package com.grid;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

class NodePosition_TP implements Runnable 
{

	int start;
	int stop;
	int w1, w2, w3, w4;
	
	public NodePosition_TP(int start, int stop, int w1, int w2, int w3, int w4)
	{
		this.start = start;
		this.stop = stop;
		this.w1 = w1;
		this.w2 = w2;
		this.w3 = w3;
		this.w4 = w4;
	}
	@Override
	public void run() 
	{
		
	}
		
}
public class NodePositionThreadPool 
{
	static int grid_size = 8;
	static int max  = (grid_size * grid_size) - 1;
	static int [][]node_matrix = new int[grid_size][grid_size];

	static int mini_grid = grid_size /2;
	
	static int []r1 = new int[mini_grid * mini_grid];
	static int []r2 = new int[mini_grid * mini_grid];
	static int []r3 = new int[mini_grid * mini_grid];
	static int []r4 = new int[mini_grid * mini_grid];
	
	static HashSet<Integer> r1_hs = new HashSet<>();
	static HashSet<Integer> r2_hs = new HashSet<>();
	static HashSet<Integer> r3_hs = new HashSet<>();
	static HashSet<Integer> r4_hs = new HashSet<>();
	
	/*
	 * given source and sink get the distance
	 * between them
	 */
	public static Integer getDistance(int source, int sink)
	{
		int x1 = getPos(source)[0];
		int y1 = getPos(source)[1];
		
		int x2 = getPos(sink)[0];
		int y2 = getPos(sink)[1];
		
		
		Integer xd = (x1>x2)? (x1-x2) : (x2-x1);
		Integer yd = (y1>y2)? (y1-y2) : (y2-y1);
		return xd + yd;
	}
	
	
	public static void initialize()
	{
		int counter = 0;
		for(int i=0;i<grid_size;i++)
		{
			for(int j=0;j<grid_size;j++)
			{
				node_matrix[i][j] = counter++;
			}
		}
	}
	
	/*
	 * Given a node position get its x, y coordinates
	 */
	public static int[] getPos(int n)
	{
		int []ar = new int[2];
		ar[0] = n/grid_size;
		ar[1] = n%grid_size;
		
		return ar;
	}
	/*
	 * Given x,y coordinate get the node position
	 */
	
	public static int PosToInt(int []ar)
	{	
		return ar[0] * grid_size + ar[1];
	}
	
	/*
	 * Given a node position , get in which sub grid it belongs to
	 */
	public static int GetMiniGrid(int a)
	{
		if(r1_hs.contains(a))
			return 1;
		
		else if(r2_hs.contains(a))
			return 2;
		
		else if(r3_hs.contains(a))
			return 3;
		
		else if(r4_hs.contains(a))
			return 4;
		
		/*
		for(int i = 0; i< mini_grid*mini_grid;i++)
		{
			if(r1[i] == a)
			{
				return 1;
			}
			
			else if(r2[i] == a)
			{
				return 2;
			}
			
			else if(r3[i] == a)
			{
				return 3;
			}
			
			else if(r4[i] == a)
			{
				return 4;
			}
		}
		*/
		return 0;
	}
	
	/*
	 * Given two nodes, determine if they belong to same grid or not
	 * If they belong to the same grid then get which sub grid is that
	 */
	public static int[] isSameMiniGrid (int a, int b)
	{
		int []out = new int[2];
		out[1] = 0;

		if(r1_hs.contains(a) && r1_hs.contains(b))
		{
			out[0] = 1;
			out[1] = 1;
			return out;
		}
		
		else if(r2_hs.contains(a) && r2_hs.contains(b))
		{
			out[0] = 1;
			out[1] = 2;
			return out;
		}
		else if(r3_hs.contains(a) && r3_hs.contains(b))
		{
			out[0] = 1;
			out[1] = 3;
			return out;
		}
		
		else if(r4_hs.contains(a) && r4_hs.contains(b))
		{
			out[0] = 1;
			out[1] = 4;
			return out;
		}
		
		/*
		int f1 = 0, f2 = 0, f3 = 0, f4 = 0;
		for(int i = 0; i< mini_grid*mini_grid;i++)
		{
			if(r1[i] == a)
				f1++;
			if(r1[i] == b)
				f1++;
			
			if(r2[i] == a)
				f2++;
			if(r2[i] == b)
				f2++;
			
			if(r3[i] == a)
				f3++;
			if(r3[i] == b)
				f3++;
			
			if(r4[i] == a)
				f4++;
			if(r4[i] == b)
				f4++;
		}
		
		out[0] = (f1 == 2 || f2 == 2 || f3 == 2 || f4 == 2) ? 1 : 0;
		
		if(f1 == 2 )
			out[1] = 1;
		else if(f2 == 2 )
			out[1] = 2;
		else if(f3 == 2 )
			out[1] = 3;
		else if(f4 == 2 )
			out[1] = 4;
		*/
		return out;
	}
	/*
	 * Stupid main function as always
	 */
	public static void main(String []ar) throws IOException
	{	
		long start = System.currentTimeMillis();
		
		FileWriter fwriter = new FileWriter("Result.txt");
		FileWriter fwriter1 = new FileWriter("Min.txt");
		
		Integer grid = grid_size;
		fwriter1.write(grid + "\n");
		
		HashMap<Float, ArrayList<String>> hopMap= new HashMap<>();
		
		int counter = 0;
		
		/*
		 * Make sure that the 4 wireless nodes always moves
		 * in the 4 sub grids
		 */
		for(int i = 0; i<mini_grid; i++)
		{
			for(int j = 0; j<mini_grid; j++)
			{
				r1[counter++] = i*grid_size + j;
				r1_hs.add(i*grid_size + j);
			}
		}
		counter = 0;
		
		for(int i = mini_grid; i<grid_size; i++)
		{
			for(int j = 0; j<mini_grid; j++)
			{
				r2[counter++] = i*grid_size + j;
				r2_hs.add(i*grid_size + j);
			}
		}
		counter = 0;
		
		for(int i = 0; i<mini_grid; i++)
		{
			for(int j = mini_grid; j<grid_size; j++)
			{
				r3[counter++] = i*grid_size + j;
				r3_hs.add(i*grid_size + j);
			}
		}
		counter = 0;
		
		for(int i = mini_grid; i<grid_size; i++)
		{
			for(int j = mini_grid; j<grid_size; j++)
			{
				r4[counter++] = i*grid_size + j;
				r4_hs.add(i*grid_size + j);
			}
		}
		counter = 0;
		
		//System.out.println(isSameMiniGrid(15, 11)[0] + "  "+isSameMiniGrid(15, 11)[1]);
		//System.out.println(GetMiniGrid(9));
		
		//for(int i=0;i<mini_grid*mini_grid;i++)
		//	System.out.println(r1[i]);
		
		//System.out.println(r1_hs);
		
		/*
		 * Simulating for all the 4 wireless nodes positions
		 * in 4 different sub grids
		 */
		int progress = 0;
		/*
		int size = mini_grid*mini_grid*mini_grid*mini_grid*mini_grid*mini_grid*mini_grid*mini_grid, progress = 0;
		System.out.println(size);
		int k1 = 2;
		int  i1 = 0;	
		int v = size/(100/k1);
		*/
		for(int i = 0; i< mini_grid*mini_grid; i++)
		{
			for(int j = 0; j< mini_grid*mini_grid; j++)
			{
				for(int k = 0; k< mini_grid*mini_grid; k++)
				{
					for(int l = 0; l< mini_grid*mini_grid; l++)
					{
						progress++;
						if(progress % 500 == 0)
						{
							
							System.out.println(progress);
						}
						
						
						int sum = 0;
						
						int w1 = r1[i];
						int w2 = r2[j];
						int w3 = r3[k];
						int w4 = r4[l];
						
						/*
						 * simulate for all possible source and sink combinations
						 * for all wireless nodes positions in 4 sub grids
						 */
						for(int source = 0; source<grid_size*grid_size; source++)
						{
							for(int sink = 0; sink<grid_size*grid_size; sink++)
							{
								if(source == sink)
									continue;	
								if(source>sink)
									continue;
								/*
								 * If source and sink both belong to same sub grid then
								 * no need to use the wireless nodes, it simply will 
								 * be distance between source and sink node
								 */
								if(isSameMiniGrid(source, sink)[0] == 1)
								{
									//System.out.println(source+ " "+sink+"  "+getDistance(source, sink));
									sum += getDistance(source, sink);
								}
								/*
								 * In case the source and sink belong to different sub grids, 
								 * then following will happen :
								 * source -> grid wireless node
								 * source grid wireless node -> sink grid wireless node (one hop)
								 * sink grid wireless node -> sink node
								 */
								else
								{
									int dis1 = 0, dis2 = 0, total = 0;
									
									int minGridSource = GetMiniGrid(source);
									
									if(minGridSource == 1)
										dis1 = getDistance(source, w1);
									else if(minGridSource == 2)
										dis1 = getDistance(source, w2);
									else if(minGridSource == 3)
										dis1 = getDistance(source, w3);
									else if(minGridSource == 4)
										dis1 = getDistance(source, w4);
									
									int minGridSink = GetMiniGrid(sink);
									
									if(minGridSink == 1)
										dis2 = getDistance(sink, w1);
									else if(minGridSink == 2)
										dis2 = getDistance(sink, w2);
									else if(minGridSink == 3)
										dis2 = getDistance(sink, w3);
									else if(minGridSink == 4)
										dis2 = getDistance(sink, w4);
									
									total = dis1 + dis2 + 2;
									
									sum += total;
									
									//System.out.println(sum+" ");
								}
							}
						}
						/*
						 * Taking average of all possible source and sink combinations
						 * for a fixed wireless configuration
						 */
						float avg = (float) sum/(grid_size*grid_size*grid_size*grid_size - grid_size*grid_size);
						//System.out.println(w1+" "+w2+" "+w3+" "+w4+"  "+avg);
						
						fwriter.append(w1+" "+w2+" "+w3+" "+w4+"  "+ avg+"\n");
						String nodePostion = w1+" "+w2+" "+w3+" "+w4;
						if(!hopMap.containsKey(avg))
						{
							ArrayList<String> nodePosList = new ArrayList<>();
							nodePosList.add(nodePostion);
							hopMap.put(avg, nodePosList);
						}
						else
						{
							ArrayList<String> retArrayList = hopMap.get(avg);
							retArrayList.add(nodePostion);
							hopMap.put(avg, retArrayList);
						}
					}
				}
			}
		}
		
		//System.out.println(hopMap.entrySet());
		Float[] hopArray = new Float[hopMap.keySet().size()];
		Iterator<Float> it = hopMap.keySet().iterator();
		int c = 0;
		while(it.hasNext())
		{
			hopArray[c++] = it.next();
		}
		
		Arrays.sort(hopArray);
		
	    ArrayList<String> getNodeConfig = hopMap.get(hopArray[0]);
		Iterator<String> its = getNodeConfig.iterator();
		fwriter1.append("Min avg hop count = " + hopArray[0] + "\n");
		while(its.hasNext())
		{
			fwriter1.append(its.next() + "\n");
		}
		
	
		fwriter.close();
		fwriter1.close();
		long end = System.currentTimeMillis();
		System.out.println("Write complete");
		
		SymmetricNode.init();
		
		System.out.println("Total execution time : "+ (end - start) + " ms.");
		
	}
}
