package mydbms;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import mydbms.Input;
import mydbms.Path;
import mydbms.ToolFunctions;

/**
 * 实现delete SQL语句
 * e.g. 
 * 删除表的一行（目前where子句只能有一个条件且为等式）delete from t1 where name="wyf";
 */
public class Delete {
	private static String tableName;
	public static void deleteSql(String sql){
        String sep = Path.getSeparate();
        //获取表名
        String path = Path.getNowPath();
        int index1 = sql.indexOf("from");
        int index2 = sql.indexOf("where");
        String name = sql.substring(index1+5, index2);
        tableName = name.trim();//移除字符串左右空白符
        //当前表的完整路径
        String nowPath = path + "\\" + tableName + ".txt";
        //得到列名
        List<String> columnName = ToolFunctions.getColumn(nowPath, 1);
        //得到where子句内容
        String wherestr = sql.substring(index2+5 , sql.length()-1);
        wherestr = wherestr.trim();
        String[] wherestrvalue = wherestr.split("=");//存放where等号两边的内容
        //找到where子句的key所在的列位置
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
        //判断是否含有“char”，若是则读取引号之间的内容
        //获取key的类型
        List<String> typelist = ToolFunctions.getColumn(nowPath,2);
        String[] onetypelist = typelist.get(0).split(sep);//每一列的类型
        if(onetypelist[wherestrkeysite].contains("char")) {//判断是否是char类型
    		Pattern pattern = Pattern.compile("\"(.*?)\"");
            Matcher matcher = pattern.matcher(wherestrvalue[1]);
            while(matcher.find()) {//提取引号间的字符串
            	wherestrvalue[1]= matcher.group(1);
            }
        }
        //删除相应的行
        deleteline(wherestrvalue[1],wherestrkeysite,nowPath);
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
        //打印删除后的表
        System.out.println(ShowTablelist.generateTable(columnName, lists));
        System.out.println("已成功删除表中的行！");
        Input.get();
	}
	/**
     * 找到where子句的值所匹配内容，删除相应元组
      * 传入key所在位置，以及value的值，读取一行，分割，按位置判断是否删除
     */
	
	 public static void deleteline(String keyvalue,int keysite,String nowPath){
	        try {
	        	
	        	//获得未删除时表中的数据
	        	List<String> list = ToolFunctions.getAllTableLine(nowPath);
	        	String sep = Path.getSeparate();     //获取分隔符
	            Iterator<String> iterator = list.iterator();
	            while(iterator.hasNext()) {
	            	String strings = iterator.next();
	            	String[] sepstrings = strings.split(sep);
	            	if(sepstrings[keysite].equals(keyvalue)){
	                	iterator.remove();
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
