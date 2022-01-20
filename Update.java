package mydbms;
/**
 * 实现update命令
 *
 */
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mydbms.Input;
import mydbms.Path;

public class Update {
	private static String tableName;
	public static void updateSql(String sql){
		//得到分隔符
        String sep = Path.getSeparate();
        //获得当前路径,到数据库文件夹
        String path = Path.getNowPath();
        int index1 = sql.indexOf("set");
        int index2 = sql.indexOf("where");
        String name = sql.substring(7, index1);
        tableName = name.trim();//移除字符串右边空白字符
        //当前表的完整路径
        String nowPath = path + "\\" + tableName + ".txt";
        //得到列名
        List<String> columnName = ToolFunctions.getColumn(nowPath, 1);//列名       
        //得到set子句内容
        String setstr = sql.substring(index1+3 , index2);
        setstr = setstr.trim();
        String[] setstrvalue = setstr.split("=");//存放where等号两边的内容    
        //找到set子句所在的列位置
        int setstrkeysite = 0;
        int mset = 0;
        boolean falgsetsite=false;
        while(mset<columnName.size()) {
    		if(setstrvalue[0].equals(columnName.get(mset))) {
    			falgsetsite=true;
    			setstrkeysite = mset; 
    			break;
    		}
    		else {
    			mset++;
    		}			
        }
        if(!falgsetsite) {
        	System.out.println("找不到set子句所属key");
        	Input.get();
        }
        //判断是否是char类型，若是则读取引号之间的内容
        //处理列的类型
        List<String> settypelist = ToolFunctions.getTableOneLine(nowPath,2);
        String[] setonetypelist = settypelist.get(0).split(sep);//每一列的类型
        if(setonetypelist[setstrkeysite].contains("char")) {//判断是否是char类型
	  		Pattern pattern = Pattern.compile("\"(.*?)\"");
	        Matcher matcher = pattern.matcher(setstrvalue[1]);
	        while(matcher.find()) {//提取引号间的字符串
	          	setstrvalue[1]= matcher.group(1);
	        }
        }
        
        //得到where子句内容
        String wherestr = sql.substring(index2+5 , sql.length()-1);
        wherestr = wherestr.trim();
        String[] wherestrvalue = wherestr.split("=");//存放where等号两边的内容
        //找到where子句所在的列位置
        int wherestrkeysite = 0;
        int m = 0;
        boolean falgwheresite=false;
        while(m<columnName.size()) {
    		if(wherestrvalue[0].equals(columnName.get(m))) {
    			falgwheresite=true;
    			wherestrkeysite = m; 
    			break;
    		}
    		else {
    			m++;
    		}
    			
        }
        if(!falgwheresite) {
        	System.out.println("找不到where子句所属key");
        	Input.get();
        }
        //判断是否会char类型，若是则读取引号之间的内容
        //处理列的类型
        List<String> wheretypelist = ToolFunctions.getTableOneLine(nowPath,2);
        String[] whereonetypelist = wheretypelist.get(0).split(sep);//每一列的类型
        if(whereonetypelist[wherestrkeysite].contains("char")) {//判断是否是char类型
    		Pattern pattern = Pattern.compile("\"(.*?)\"");
            Matcher matcher = pattern.matcher(wherestrvalue[1]);
            while(matcher.find()) {//提取引号间的字符串
            	wherestrvalue[1]= matcher.group(1);
            }
        }   
        updateline(wherestrvalue[1],wherestrkeysite,setstrvalue[1],setstrkeysite,nowPath);
        //打印更新后的表
        //获得表中的数据
        List<String> list = ToolFunctions.getTableTuple(nowPath);
        List<List<String>> lists = new ArrayList<>();
        int i = 0;
        for (String s : list) {
            List<String> list1 = new ArrayList<>();
            String[] strings = list.get(i++).split(sep);
            for (String s1 : strings) {
                list1.add(s1);
            }
            lists.add(list1);
        }
        System.out.println(ShowTablelist.generateTable(columnName, lists));
        System.out.println("成功更改！");
        Input.get();
	}

	
	/**
     *  找到where子句符合内容，删除相应元组
     * 
      * 传入key所在位置，以及value的值，读取一行，分割，按位置判断是否删除
     */
	
	 public static void updateline(String keyvalue,int keysite,String setvalue,int setkeysite,String nowPath){
	        try {
	        	
	            List<String> list = ToolFunctions.getAllTableLine(nowPath);
	            String sep = Path.getSeparate();     //获取分隔符
	            ListIterator<String> listiterator = list.listIterator();
	            while(listiterator.hasNext()) {
		            String strings = listiterator.next();
		            String[] sepstrings = strings.split(sep);
		            if(sepstrings[keysite].equals(keyvalue)){
		            	String oldsetvalue = sepstrings[setkeysite];	            		
		            	String newstrings = strings.replace(oldsetvalue, setvalue);	
		            	listiterator.set(newstrings);
		            }   
	            }
	            FileWriter fd = new FileWriter(nowPath, false);//append传入false表示写入内容时将会覆盖文件中之前存在的内容
                fd.write("");//执行删除操作，写入空内容覆盖之前的内容
                fd.close();
                for (String newline : list) {
                    FileWriter fw = new FileWriter(nowPath, true);//append传入true表示写入内容时将不会覆盖文件中之前存在的内容，将新的内容写在之前内容的后面
                    fw.write(newline);//执行重新写入内容的操作
                    fw.write(System.getProperty("line.separator"));//在段落后添加一个换行符
                    fw.close();
                }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
    }
}
