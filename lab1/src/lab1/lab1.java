a change in B1
package lab1;
import java.io.*;
import java.util.*;
import java.util.Map.Entry;




public class lab1 {
 
	public static HashMap<String,HashMap<String,Integer>> Graph=new HashMap<String,HashMap<String,Integer>>();
	public static String sss=null;
	public static void main(String []args)throws IOException
	{
		File f=new File("D:/aaa/help.txt");
		FileReader in =new FileReader(f);
		BufferedReader fr=new BufferedReader(in);
		String s=null;
		String str=" ";
	 
		while((s=fr.readLine())!=null){
			s=s.replaceAll("[^a-zA-Z]"," ");
			str=str+s+" ";	 
		}
		str=str.toLowerCase();
		str=str.trim();	
		String []list=str.split("\\s+");
		for(String ss:list){
			System.out.print(ss+" ");	
		}
		System.out.print("\n");
		
		fr.close();
		in.close();
		
		for (int i=0;i<list.length-1;i++)
		{
			if (Graph.containsKey(list[i])){
				if (Graph.get(list[i]).containsKey(list[i+1]))
				{
					int value=Graph.get(list[i]).get(list[i+1]);
					Graph.get(list[i]).put(list[i+1],value+1);
				}
				else
				{
					Graph.get(list[i]).put(list[i+1],1);
				}		
			}
			else	
			{
				HashMap<String,Integer> ad=new HashMap<String,Integer>();
				ad.put(list[i+1], 1);
				Graph.put(list[i],ad);
			}		
		}
		if(!Graph.containsKey(list[list.length-1])){
		Graph.put(list[list.length-1],null);}
		
		
		
		System.out.println("选择功能(-1:退出程序)：/n 1.展示有向图 /n 2.查询桥接词 /n 3.根据bridge word生成新文本/n 4.计算两个单词之间的最短路径/n 5.随机游走");
		 
		int  xi=0;
		while(xi!=-1){
			Scanner sii=new Scanner(System.in);
		if (sii.hasNextInt()){
		xi=sii.nextInt();
		}
		         
		switch (xi){
		case 1:
			Set<Entry<String, HashMap<String,Integer>>> entrySett =Graph.entrySet();
			for (Entry<String, HashMap<String,Integer>> entry : entrySett) {
				if (entry.getValue()!=null){
					System.out.println(entry.getKey()+"->"+entry.getValue());
				}
			}
			showDirectedGraph( Graph);
		break;
		
			case 2:	
		System.out.println("Please input the two words: ");
		Scanner scan=new Scanner(System.in);
		String a0=null,a1=null;
			if(scan.hasNext()){
				 a0=scan.next();
				 a1=scan.next();
			}
		  queryBridgeWords(a0,a1);
		 break;
		 
			case 3:
		System.out.println("Please input the text: ");
		Scanner sca=new Scanner(System.in);
		String st="";
		if(sca.hasNextLine()){
			 st=sca.nextLine();  
		}
		st=st.replaceAll("[^a-zA-Z]"," ");
		st=st.toLowerCase();
		System.out.println(generateNewText(st));
	 
		//sca.close();
		break;
		
			case 4:
		System.out.println("Please input the two words: ");
        Scanner scan1 =new Scanner(System.in );
        String text=" ";
        if (scan1.hasNextLine()){
        	text = scan1.nextLine();
        }
        String [] read =text.split("\\s+");
        if(read.length==2){
         sss = calcShortestPath(read[0],read[1]);}
        else{
        	sss = calcShortestPath(read[0],null);
        }
        
        if (sss==null){
        	System.out.println("不可达");
        }else{
        	if(sss==" "){
        		;
        	}else
        	{
        		System.out.println(sss);
        	}
        }
        if (sss!=null){
        String[] ss=sss.split("->");
        showgraph(Graph,ss,1);
        }
        break;
        
			case 5:
        String xet =randomWalk();
        if (xet!=null){
            String[] ssss=xet.split("->");
            showgraph(Graph,ssss,2);
        }
        System.out.println(xet);
        File fe = new File("D:/aaa/randompath.txt");
        FileOutputStream fop = new FileOutputStream(fe);
        OutputStreamWriter writer = new OutputStreamWriter(fop, "UTF-8");
        writer.append(xet);
        writer.close();
        break;   
		}
		}
	}
	 
	public static String queryBridgeWords(String word1, String word2)
	{
		String [] array=new String[100];
		int i=0;
		if (Graph.containsKey(word1) && Graph.containsKey(word2))
		{
			if(Graph.get(word1)!=null)
			{
			Set<Entry<String,Integer>> valueSet = Graph.get(word1).entrySet();
			for (Entry<String,Integer> entry : valueSet) {
				 if (Graph.get(entry.getKey())!=null && Graph.get(entry.getKey()).containsKey(word2)  ) 
				 {
					  array[i]= entry.getKey();	 
					  i++;
				 }
			}
			}
			if (i==0)
			{
				System.out.println("No bridge words from "+"\""+word1+"\""+" to "+"\""+ word2+"\""+"!");
				return null;
			}
			else if (i==1)
			{
			 
				System.out.println("The bridge words from "+ "\""+word1+"\""+ " to "+ "\""+word2+"\""+" is:"+array[0]);
				return array[0];
			}
			else  
			{
				System.out.print("The bridge words from "+ "\""+word1+"\""+ " to "+ "\""+word2+"\""+" are:");
				for (int j=0;j<i-1;j++)
				{
					System.out.print(array[j]+",");
				}
				System.out.println("and "+array[i-1]+".");
				
				return array[0];
			} 
		}
		else
		{
			if (!Graph.containsKey(word1))
			{
				System.out.println("No"+"\""+word1+"\"" +"in the graph!");
			}
			else
			{
				System.out.println("No"+"\""+word2+"\"" +"in the graph!");
			}
			return null;		
		}
	}
	
	
	public static String queryBridgeWord(String word1, String word2)
	{
		String [] array=new String[100];
		int i=0;
		if (Graph.containsKey(word1) && Graph.containsKey(word2))
		{
			if(Graph.get(word1)!=null)
			{
			Set<Entry<String,Integer>> valueSet = Graph.get(word1).entrySet();
			for (Entry<String,Integer> entry : valueSet) {
				 if (Graph.get(entry.getKey())!=null && Graph.get(entry.getKey()).containsKey(word2)  ) 
				 {
					  array[i]= entry.getKey();	 
					  i++;
				 }
			}
			}
			if (i==0)
			{
				return null;
			}
			else  
				return array[0];
		}
		else
			return null;		
		}
	
	
	public static String generateNewText(String inputText)
	{
		String[] wordlist=inputText.split("\\s+");
		String xx="";
		for(int i =0;i<wordlist.length-1;i++){
			String ss=queryBridgeWord(wordlist[i],wordlist[i+1]);
			if(ss==null)
			{xx=xx+wordlist[i]+" ";}
			else
			{
				xx=xx+wordlist[i]+" "+ss+" ";
			}		 
		}
		return xx+wordlist[wordlist.length-1];
		
	}
	static String calcShortestPath(String word1, String word2){
		HashMap<String,String> MinPath=new HashMap<String,String>();
		HashMap<String,Integer> MinDis=new HashMap<String,Integer>();
		if(Graph.containsKey(word1)) {

				if (Graph.get(word1)==null){
					return null;
				}
				MinPath.put(word1, word1);
				MinDis.put(word1, 0);
				String [] nextNode=new String[1000];
				int i=0,j=0;
				Set<Entry<String, Integer>> entrySet = Graph.get(word1).entrySet();
				for (Entry<String, Integer> entry : entrySet) {
					MinDis.put(entry.getKey(),MinDis.get(word1)+ entry.getValue());
	        		MinPath.put(entry.getKey(),MinPath.get(word1)+"->"+entry.getKey());
	        		nextNode[i]=entry.getKey();
	        		i++;
		        }
			    while(Graph.size()>MinDis.size() && j<Graph.size()){
			    	if(Graph.get(nextNode[j])!=null){
			    	entrySet=Graph.get(nextNode[j]).entrySet();
			        for (Entry<String, Integer> entry : entrySet) {
			        	if(!MinDis.containsKey(entry.getKey())){
			        		MinDis.put(entry.getKey(),MinDis.get(nextNode[j])+ entry.getValue());
			        		MinPath.put(entry.getKey(),MinPath.get(nextNode[j])+"->"+entry.getKey());
			        		nextNode[i]=entry.getKey();
			        		i++;
			        	}
			        	else
			        	{
			        		if(MinDis.get(nextNode[j])+entry.getValue()< MinDis.get(entry.getKey())){
			        			MinDis.put(entry.getKey(), MinDis.get(nextNode[j])+entry.getValue());
			        			MinPath.put(entry.getKey(), MinPath.get(nextNode[j])+"->"+entry.getKey());
			        		}
			        	}
			        }
			        }
			        
			       j++;
			        
			    }
			    //输出所有点的最短路径。    
				if(Graph.containsKey(word2)){
					return MinPath.get(word2);
				}
				else{
					if(word2==null){
						 Set<Entry<String, String>> entryS = MinPath.entrySet();     //打印图
					        for (Entry<String, String> entry : entryS) {
					        	System.out.println(entry.getKey()+"的最短路径: ");
					        	System.out.println(entry.getValue()+"     最短路径长为 "+MinDis.get(entry.getKey()));
					        }
					        return " ";
					}
					else{
						return null;
					}
				}
			
		}
		else{
			return null;}
	}
	
	
	static String randomWalk(){
	    String [] nodeList = new String[Graph.size()];String Walk;
	    HashMap<Integer,String> eg=new HashMap<Integer,String>();
	    int i=0;int count=0;
	    Set<Entry<String, HashMap<String,Integer>>> entrySet = Graph.entrySet(); 
	    for (Entry<String, HashMap<String,Integer>> entry : entrySet) {
        	nodeList[i]=entry.getKey();
        	i++;
        }
	    Random r =new Random();
	    int j=r.nextInt(Graph.size());
	    String word=nodeList[j];String next="";
	    Walk=word;
	    while(Graph.get(word)!=null){
	    	i=0;
	    	Set<Entry<String, Integer>> ent = Graph.get(word).entrySet(); 
		    for (Entry<String, Integer> entry : ent) {
	        	nodeList[i]=entry.getKey();
	        	i++;
	        }
		    j=r.nextInt(Graph.get(word).size());
		    next=nodeList[j];
		    if(!eg.containsValue(word+" "+next)){
		    	eg.put(count, word+" "+ next);
		    	Walk=Walk+"->"+next;
		    	count++;
		    	word=next;
		    }else{
		    	Walk=Walk+"->"+next;
		    	break;
		    }
		    
	    }
	    
		return Walk;
	}
	
	static void showDirectedGraph(HashMap<String,HashMap<String,Integer>> Graph)
	{
		GraphViz gv = new GraphViz();
	      gv.addln(gv.start_graph());
		Set<Entry<String, HashMap<String,Integer>>> entrySet =Graph.entrySet();
		for (Entry<String, HashMap<String,Integer>> entry : entrySet) {
			if(entry.getValue()!=null){
			Set<Entry<String,Integer>> entrs =entry.getValue().entrySet();
			for (Entry<String, Integer> entr : entrs) {
				 gv.addln(entry.getKey()+"[style=\"filled\", color=\"black\", fillcolor=\"chartreuse\"];");
				 gv.addln(entry.getKey()+"->"+entr.getKey()+"[label="+entr.getValue()+",color=\"red\"];");
				 gv.addln(entr.getKey()+"[style=\"filled\", color=\"black\", fillcolor=\"chartreuse\"];");
		}}}

	      gv.addln(gv.end_graph());
	     // System.out.println(gv.getDotSource());
	      String type = "gif";
//	      String type = "dot";
//	      String type = "fig";    // open with xfig
//	      String type = "pdf";
//	      String type = "ps";
//	      String type = "svg";    // open with inkscape
//	      String type = "png";
//	      String type = "plain";
//	      File out = new File("/tmp/out." + type);   // Linux
	      File out = new File("d:/aaa/graph." + type);    // Windows
	      gv.writeGraphToFile( gv.getGraph( gv.getDotSource(), type ), out );
	}
	
	static void showgraph(HashMap<String,HashMap<String,Integer>> Graph,String[] s,int mm){
		GraphViz gv1 = new GraphViz();
	    gv1.addln(gv1.start_graph());
	    int sum=0;   
		Set<Entry<String, HashMap<String,Integer>>> entrySet1 =Graph.entrySet();
		for (Entry<String, HashMap<String,Integer>> entry : entrySet1) {
			if (entry.getValue()!=null){
			Set<Entry<String,Integer>> entrs =entry.getValue().entrySet();
			for (Entry<String, Integer> entr : entrs) {
				int x=0;
				for (int i=0;i<s.length-1;i++){
					if (entry.getKey().equals(s[i]) && entr.getKey().equals(s[i+1]))
					{
						x=1;
						break;
					}	
				 
				}
				 
				if(x==1){
					gv1.addln(entry.getKey()+"[style=\"filled\", color=\"black\", fillcolor=\"red\"];");
					gv1.addln(entr.getKey()+"[style=\"filled\", color=\"black\", fillcolor=\"red\"];");
					gv1.addln(entry.getKey()+"->"+entr.getKey()+"[label="+entr.getValue()+",color=\"red\"];");
					sum+=entr.getValue();
				}
				else{
					 
					gv1.addln(entry.getKey()+"->"+entr.getKey()+"[label="+entr.getValue()+"];");
				}	 
		}	
		}}
		gv1.addln( sum  +"[shape=\"record\",style=\"filled\", color=\"black\", fillcolor=\"red\"];");
	      gv1.addln(gv1.end_graph());
	      //System.out.println(gv1.getDotSource());
	      String type1 = "gif";
//	      String type = "dot";
//	      String type = "fig";    // open with xfig
//	      String type = "pdf";
//	      String type = "ps";
//	      String type = "svg";    // open with inkscape
//	      String type = "png";
//	      String type = "plain";
//	      File out = new File("/tmp/out." + type);   // Linux
	      File out1 = new File("d:/aaa/graph"+mm+"." + type1);    // Windows
	      gv1.writeGraphToFile( gv1.getGraph( gv1.getDotSource(), type1 ), out1 );
        }
}




  final class GraphViz
 {
	   /**
	    * The dir. where temporary files will be created.
	    */
	   //private static String TEMP_DIR = "/tmp"; // Linux
	   private static String TEMP_DIR = "d:/graphviz/"; // Windows

	/**
	    * Where is your dot program located? It will be called externally.
	    */
	  // private static String DOT = "/usr/bin/dot"; // Linux
	   private static String DOT = "D:\\graphviz\\bin\\dot.exe"; // Windows

	/**
	    * The source of the graph written in dot language.
	    */
	 private StringBuilder graph = new StringBuilder();

	/**
	    * Constructor: creates a new GraphViz object that will contain
	    * a graph.
	    */
	   public GraphViz() {
	   }

	/**
	    * Returns the graph's source description in dot language.
	    * @return Source of the graph in dot language.
	    */
	   public String getDotSource() {
	      return graph.toString();
	   }

	/**
	    * Adds a string to the graph's source (without newline).
	    */
	   public void add(String line) {
	      graph.append(line);
	   }

	/**
	    * Adds a string to the graph's source (with newline).
	    */
	   public void addln(String line) {
	      graph.append(line + "\n");
	   }

	/**
	    * Adds a newline to the graph's source.
	    */
	   public void addln() {
	      graph.append('\n');
	   }

	/**
	    * Returns the graph as an image in binary format.
	    * @param dot_source Source of the graph to be drawn.
	    * @param type Type of the output image to be produced, e.g.: gif, dot, fig, pdf, ps, svg, png.
	    * @return A byte array containing the image of the graph.
	    */
	   public byte[] getGraph(String dot_source, String type)
	   {
	      File dot;
	      byte[] img_stream = null;
	   
	      try {
	         dot = writeDotSourceToFile(dot_source);
	         if (dot != null)
	         {
	            img_stream = get_img_stream(dot, type);
	            if (dot.delete() == false) 
	               System.err.println("Warning: " + dot.getAbsolutePath() + " could not be deleted!");
	            return img_stream;
	         }
	         return null;
	      } catch (java.io.IOException ioe) { return null; }
	   }

	/**
	    * Writes the graph's image in a file.
	    * @param img   A byte array containing the image of the graph.
	    * @param file  Name of the file to where we want to write.
	    * @return Success: 1, Failure: -1
	    */
	   public int writeGraphToFile(byte[] img, String file)
	   {
	      File to = new File(file);
	      return writeGraphToFile(img, to);
	   }

	/**
	    * Writes the graph's image in a file.
	    * @param img   A byte array containing the image of the graph.
	    * @param to    A File object to where we want to write.
	    * @return Success: 1, Failure: -1
	    */
	   public int writeGraphToFile(byte[] img, File to)
	   {
	      try {
	         FileOutputStream fos = new FileOutputStream(to);
	         fos.write(img);
	         fos.close();
	      } catch (java.io.IOException ioe) { ioe.printStackTrace();return -1; }
	      return 1;
	   }

	/**
	    * It will call the external dot program, and return the image in
	    * binary format.
	    * @param dot Source of the graph (in dot language).
	    * @param type Type of the output image to be produced, e.g.: gif, dot, fig, pdf, ps, svg, png.
	    * @return The image of the graph in .gif format.
	    */
	   private byte[] get_img_stream(File dot, String type)
	   {
	      File img;
	      byte[] img_stream = null;

	try {
	         img = File.createTempFile("graph_", "."+type, new File(GraphViz.TEMP_DIR));
	         Runtime rt = Runtime.getRuntime();
	         
	         // patch by Mike Chenault
	         String[] args = {DOT, "-T"+type, dot.getAbsolutePath(), "-o", img.getAbsolutePath()};
	         Process p = rt.exec(args);
	         
	         p.waitFor();

	FileInputStream in = new FileInputStream(img.getAbsolutePath());
	         img_stream = new byte[in.available()];
	         in.read(img_stream);
	         // Close it if we need to
	         if( in != null ) in.close();

	if (img.delete() == false) 
	            System.err.println("Warning: " + img.getAbsolutePath() + " could not be deleted!");
	      }
	      catch (java.io.IOException ioe) {
	         System.err.println("Error:    in I/O processing of tempfile in dir " + GraphViz.TEMP_DIR+"\n");
	         System.err.println("       or in calling external command");
	         ioe.printStackTrace();
	      }
	      catch (java.lang.InterruptedException ie) {
	         System.err.println("Error: the execution of the external program was interrupted");
	         ie.printStackTrace();
	      }

	return img_stream;   }
	   /**
	    * Writes the source of the graph in a file, and returns the written file
	    * as a File object.
	    * @param str Source of the graph (in dot language).
	    * @return The file (as a File object) that contains the source of the graph.
	    */
	   public File writeDotSourceToFile(String str) throws java.io.IOException
	   {
	      File temp;
	      try {
	         temp = File.createTempFile("graph_", ".dot.tmp", new File(GraphViz.TEMP_DIR));
	         FileWriter fout = new FileWriter(temp);
	         fout.write(str);
	         fout.close();
	      }
	      catch (Exception e) {
	         System.err.println("Error: I/O error while writing the dot source to temp file!");
	         return null;
	      }
	      return temp;
	   }

	/**
	    * Returns a string that is used to start a graph.
	    * @return A string to open a graph.
	    */
	   public String start_graph() {
	      return "digraph G {" ;
	   }

	/**
	    * Returns a string that is used to end a graph.
	    * @return A string to close a graph.
	    */
	   public String end_graph() {
	      return "}";
	   }

	/**
	    * Read a DOT graph from a text file.
	    * 
	    * @param input Input text file containing the DOT graph
	    * source.
	    */
	   public void readSource(String input)
	   {
	    StringBuilder sb = new StringBuilder();
	    
	    try
	    {
	     FileInputStream fis = new FileInputStream(input);
	     DataInputStream dis = new DataInputStream(fis);
	     BufferedReader br = new BufferedReader(new InputStreamReader(dis));
	     String line;
	     while ((line = br.readLine()) != null) {
	      sb.append(line);
	     }
	     dis.close();
	    } 
	    catch (Exception e) {
	     System.err.println("Error: " + e.getMessage());
	    }
	    
	    this.graph = sb;
	   }
	   
	} 
	
	// end of class GraphViz

	 
