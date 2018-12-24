package com.todo.csvutil;


/**
 * Creates objects that hold an entire line of CSV data.
 * @author robal
 *
 */
public class CSVData implements  Comparable<CSVData>
{
	private String[] dataElements;
	private String CSVLine;
	private int compareRow;
	int index;
	
	
	/**
	 * 
	 * @param CSVLine row of CSV data
	 */
	public CSVData(String CSVLine)
	{
		dataElements = CSVLine.split(",");
		for(int i = 0; i < dataElements.length; i ++)
			this.CSVLine = CSVLine + "\n";
	
	}
	/**
	 * 
	 * @param CSVLine - row of CSV data
	 * @param row - row used to compare each line
	 * @param type - type of data being compared
	 */
	public <T> CSVData(String CSVLine, int row, String type)
	{
		dataElements = CSVLine.split(",");
		this.CSVLine = CSVLine + "\n";
		compareRow = row;

	
	}
	
	/**
	 * 
	 * @param CSVLine - Raw string representing a row in a CSV file
	 * @param row - row used for comparison
	 */
	public <T> CSVData(String CSVLine, int row)
	{
		dataElements = CSVLine.split(",");
		this.CSVLine = CSVLine + "\n";
		compareRow = row;
	}
	/**
	 * 
	 * @param i - column index
	 * @return data in column i
	 */
	public String get(int i)
	{
		return dataElements[i];
	}
	
	/**
	 * 
	 * @param i - index of column to change
	 * @param data - new data for the column
	 */
	public void set(int i, String data)
	{
		dataElements[i] = data;
	}
	
	/**
	 * 
	 * @return - number of elements in CSV file row
	 */
	public int size()
	{
		return dataElements.length;
	}
	
	/**
	 * Displays CSV file row in command line
	 */
	public String toString()
	{
		return CSVLine;
	}
	
	/**
	 * 
	 * @param tagType - Tag that encapsulates CSV data elemnts
	 * @return
	 */
	public String toHtmlString(String tagType)
	{
		String htmlData = "";
		htmlData += "<tr>";
		for(int i =0; i < dataElements.length; i++)
		{
			htmlData += "<" + tagType + ">";
			htmlData += dataElements[i];
			htmlData += "</" + tagType + ">";
		}
		
		htmlData +="</tr>";
		return htmlData;
	}
	/**
	 * 
	 * @param tagType - Tag used to encapsulate data element
	 * @param num - Row number indicator
	 * @return String - representing CSVData row in string format
	 */
	public String toHtmlString(String tagType, int num)
	{
		String htmlData = "";
		htmlData += "<tr>";
		htmlData +="<" + tagType + ">" + num + "</" + tagType + ">";
		for(int i =0; i < dataElements.length; i++)
		{
			htmlData += "<" + tagType + ">" + dataElements[i] + "</" + tagType + ">";
		}
		
		htmlData +="</tr>";
		return htmlData;
	}
	
	/**
	 * Function used by the java sort function to determine if x csvdata greater than y csvdata
	 */
	@Override
	public int compareTo(CSVData compareData) 
	{
		
		return this.dataElements[compareRow].compareTo(compareData.dataElements[compareData.compareRow]);
	}



}
