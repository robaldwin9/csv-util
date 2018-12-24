package com.todo.csvutil;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * 
 * @author robal
 *
 */
public class CSVDataContainer 
{
	ArrayList<CSVData> CSVContainer;
	int sortColumn;
	

	/**
	 * 
	 */
	public CSVDataContainer()
	{
		CSVContainer = new ArrayList<CSVData>();
	}
	
	/**
	 * 
	 * @param reader
	 * @param sortRow
	 * @throws IOException
	 */
	public CSVDataContainer(BufferedReader reader, int sortRow) throws IOException
	{
		CSVContainer = new ArrayList<CSVData>();
		String line;
		
		while((line = reader.readLine()) != null)
		{
			CSVData data = new CSVData(line,sortRow);
			CSVContainer.add(data);
			
		}
	}
	
	/**
	 * 
	 * @param reader
	 * @throws IOException
	 */
	public CSVDataContainer(BufferedReader reader) throws IOException
	{
		CSVContainer = new ArrayList<CSVData>();
		String line;
		while((line = reader.readLine()) != null)
		{
			CSVContainer.add(new CSVData(line));
		}
	}
	/**
	 * 
	 * @param i
	 * @return
	 */
	public  CSVData get(int i)
	{

		return CSVContainer.get(i);
	}
	
	/**
	 * 
	 * @param i
	 * @param data
	 */
	public void set(int i, CSVData data)
	{
		CSVContainer.set(i, data);
	}
	
	/**
	 * 
	 * @param data
	 */
	public void add(CSVData data)
	{
		CSVContainer.add(data);
	}
	
	/**
	 * 
	 */
	public void sort()
	{
		Collections.sort(CSVContainer.subList(0, CSVContainer.size()));
	}
	
	/**
	 * 
	 * @return
	 */
	public int size()
	{
		return CSVContainer.size();
	}
	/**
	 * 
	 * @param sortColumn
	 */
	public void setSortRow(int sortColumn) 
	{
		this.sortColumn = sortColumn;
	}
	

}
