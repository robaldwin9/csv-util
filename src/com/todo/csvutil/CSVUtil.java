package com.todo.csvutil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;



/**
 * CSVUtil handles specific operations that might be done to a csvFile.
 * This class provides abstraction so the user has no need to mess with data 
 * first hand.
 * @author robal
 *
 *
 */
public class CSVUtil 
{
	private File inputFile;						/**/
	private File outFile;						/**/
	private BufferedWriter writer;				/**/
	private BufferedReader reader;				/**/
	private CSVDataContainer CSVFileContainer;  /**/
	private String inputFileName;				/**/
	private CSVData title;						/**/
	private boolean fileLoaded;					/**/
	
	/**/
	public CSVUtil(String inputFileName, String outputFileName) throws IOException
	{
		inputFile = new File(inputFileName);
		outFile = new File(outputFileName);
		reader = new BufferedReader(new FileReader(inputFile));
		CSVFileContainer = new CSVDataContainer();
		this.inputFileName = inputFileName;
		fileLoaded = false;
	}
	
	/**/
	public CSVUtil(String inputFileName) throws IOException
	{
		inputFile = new File(inputFileName);
		outFile = new File(inputFileName);
		reader = new BufferedReader(new FileReader(inputFile));
		CSVFileContainer = new CSVDataContainer();
		this.inputFileName = inputFileName;
		fileLoaded = false;
	}
	
	/**/
	public void loadFile() throws IOException
	{
		title = new CSVData(reader.readLine());
		CSVFileContainer = new CSVDataContainer(reader);
		fileLoaded = true;
	}
	
	/**
	 * 
	 * @param column - column to sort rows by 
	 * @throws IOException - possibility from file reads
	 */
	public void sortAcending(int column) throws IOException
	{
		title = new CSVData(reader.readLine());
		CSVFileContainer = new CSVDataContainer(reader,column);
		CSVFileContainer.sort();
	}
	
	/**
	 * 
	 * @param column - column to sort rows by
	 * @throws IOException - exception caused by file read
	 */
	public void sortDecending(int column) throws IOException
	{
		title = new CSVData(reader.readLine());
		CSVFileContainer = new CSVDataContainer(reader,column);
		CSVFileContainer.sort();
		
		for(int i =0; i < CSVFileContainer.size(); i++)
		{
			CSVData tmpData = CSVFileContainer.get(i);
			CSVFileContainer.set(i, tmpData);
			CSVFileContainer.set(CSVFileContainer.size() -(1+i), tmpData);
		}
	}
	
	/**
	 * 
	 * @param row
	 */
	public void remove(int row )
	{
		
	}
	
	/**
	 * 
	 * @throws IOException
	 */
	public void writeChanges() throws IOException
	{
		writer = new BufferedWriter(new PrintWriter((inputFile)));
		writer.write(title.toString());
		for(int i = 1; i < CSVFileContainer.size();i++)
		{
			CSVData line =  CSVFileContainer.get(i);
			for(int j = 0; j < line.size(); j++)
			{
				writer.write(line.get(j));
				if(j != line.size() -1)
					writer.write(',');
			}
			writer.write("\n");
		}
		
		writer.flush();
	}
	
	/**
	 * 
	 * @throws IOException
	 */
	public void appendChanges() throws IOException
	{
		writer = new BufferedWriter(new PrintWriter(new FileWriter(inputFile, true)));
		writer.write(title.toString());
		for(int i = 1; i < CSVFileContainer.size();i++)
		{
			CSVData line =  CSVFileContainer.get(i);
			for(int j = 0; j < line.size(); j++)
			{
				writer.write(line.get(j));
				if(j != line.size() -1)
					writer.write(',');
			}
			writer.write("\n");
		}
		
		writer.flush();
		
	}
	
	/**
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException
	{
		if(reader != null)
		reader.close();
		if(writer != null)
		writer.close();
	}
	
	/**
	 * Display entire file
	 * @throws IOException - File read exception
	 */
	public void displayFile() throws IOException
	{
		if(!fileLoaded)
		{
			CSVFileContainer = new CSVDataContainer(reader);
		}
		for(int i =0; i < CSVFileContainer.size(); i++)
		{
			System.out.println(CSVFileContainer.get(i));
		}
	}
	
	/**
	 * Creates a copy of file loaded
	 * @throws IOException
	 */
	public void duplicateFile() throws IOException
	{
		File file = new File(inputFileName.substring(0, inputFileName.length()-4) + "cpy"
				+ ".csv");
		if(!file.exists())
			file.createNewFile();
		
		writer = new BufferedWriter(new FileWriter(file));
		writer.write(reader.readLine().toString());
		CSVFileContainer = new CSVDataContainer(reader);
		
		for(int i =0; i < CSVFileContainer.size(); i++)
		{
			writer.write(CSVFileContainer.get(i).toString());
		}
		
		writer.flush();
	}
	
	/**
	 * Exports the file to HTML tabel
	 * @throws IOException 
	 * 
	 */
	public void exportHTML() throws IOException
	{
		/*CSV header data*/
		title = new CSVData(reader.readLine());

		writer = new BufferedWriter(new PrintWriter( 
				new File(inputFileName.substring(0,inputFileName.length() - 4) + ".html")));
		
		/*HTML opening tags*/
		writer.write("<!DOCTYPE HTML");
		writer.write("<html>");
		writer.write("<body>");
		writer.write("<table>");
		
		/*Get <tr><th></th>.......</tr> data*/
		writer.write(title.toHtmlString("th"));
		
		CSVFileContainer = new CSVDataContainer(reader);
		
		/*get data in format <tr><td></td>...</tr>*/
		for(int i =0; i < CSVFileContainer.size(); i++)
		{
			writer.write(CSVFileContainer.get(i).toHtmlString("td"));
		}
		
		/*Write closing tags*/
		writer.write("</table>");
		writer.write("<body>");
		writer.write("<html>");
	}
	
	/**
	 * @throws IOException 
	 * 
	 */
	public void merge(ArrayList<String> filePaths, int row, String newFile) throws IOException
	{
		/*input*/
		File[] files = new File[filePaths.size()];
		BufferedReader[] readers = new BufferedReader[filePaths.size()];
		
		for(int i =0; i < filePaths.size(); i++)
		{
			files[i] = new File(filePaths.get(i));
			readers[i]= new BufferedReader(new FileReader(files[i]));
		}
		
		/*Output*/
		File outputFile = new File(newFile);
		if(!outputFile.exists())
		{
			BufferedWriter out = new BufferedWriter(new FileWriter(outFile));
		}
		
		/*Create CSV containers*/
		CSVDataContainer[] csvFiles = new CSVDataContainer[filePaths.size()];
		for(int i=0; i < csvFiles.length; i++)
		{
			csvFiles[i] = new CSVDataContainer(reader); 
		}
		
		
	}
	
	/**
	 * 
	 * @param col - column of the searched valued
	 * @param value - value to be searched for in the specified column
	 * @return number of times a row contains specified value
	 */
	public int Count(int col, String value)
	{
		
		int numInstances = 0;
		for(int i = 0; i < CSVFileContainer.size(); i++)
		{
			CSVData lineData = CSVFileContainer.get(i);
			if(lineData.size() - 1 > col && lineData.get(col).replaceAll("\\s", "").equals(value)) 
			{
				numInstances++;
			}
			
		}
		
		return numInstances;
	}
	
	/**
	 * 
	 * @param col - column of the searched value
	 * @param value - value to be searched for
	 * @return first csvData(row) of data containing the value in the specified column
	 */
	public int LinearFind(int col, String value)
	{
		
		int row = -1;
		for(int i = 0; i < CSVFileContainer.size(); i++)
		{
			CSVData lineData = CSVFileContainer.get(i);
			if(lineData.size() - 1 > col && lineData.get(col).replaceAll("\\s", "").equals(value)) 
			{
				row = i;
				break;
			}
			
		}
		
		return row;
	}
	
	/**
	 * 
	 * @param i - index of data element(row)
	 * @return
	 */
	public CSVData get(int i)
	{
		return CSVFileContainer.get(i);
	}
	
	/**
	 * 
	 * @param i - Row index
	 * @param j - Column index
	 * @return CSV string element at (i,j)
	 */
	public String CSVDataget(int i, int j)
	{
		return CSVFileContainer.get(i).get(j);
	}
	
	/**
	 * 
	 * @return container of CSVdata elements
	 */
	public CSVDataContainer getCSVDataContainer()
	{
		return CSVFileContainer;
	}
	
}
