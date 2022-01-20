package mydbms;

/**
 *  实现select语句
 * 
 */
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
//import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import mydbms.Input;
import mydbms.Path;
import mydbms.ShowTablelist;
import mydbms.ToolFunctions;

public class Select {
	private static String tableName;
    public static void selectSql(String sql){
        //得到分隔符
        String sep = Path.getSeparate();
        //获得当前路径,到数据库文件夹
        String path = Path.getNowPath();   
        int index1 = sql.indexOf("from");//返回from所在位置
        int index2 = sql.indexOf("where");
        String name = sql.substring(index1 + 5, index2);
        tableName = name.trim();
        //得到where子句内容
        String wherestr = sql.substring(index2+5 , sql.length()-1);
        wherestr = wherestr.trim();
        String[] wherestrvalue = wherestr.split("=");//存放where等号两边的内容 
        //当前表的完整路径
        String nowPath = path + "\\" + tableName + ".txt";
        //获得表中的数据
        List<String> list = ToolFunctions.getTableTuple(nowPath);
        String targetlist = sql.substring(7, index1);//得到目标列
        targetlist = targetlist.trim();
        String[] targetkey = targetlist.split(",");//分隔后的key
        List<String> columnName = ToolFunctions.getColumn(nowPath, 1);//列名
        int j= 0;
        int[] targetkeysite = new int[targetkey.length];//存放目标列所在表头中在第几个位置
        boolean [] flag = new boolean[targetkey.length];//判断目标列是否在表中有
        for(int i= 0; i<targetkey.length;i++) {
        	j=0;
        	while(j<columnName.size()) {
        		if(targetkey[i].equals(columnName.get(j))) {
        			flag[i]=true;
        			targetkeysite[i] = j; 
        			break;
        		}
        		else
        			j++;
        	}
        }
        for(int i= 0; i<targetkey.length;i++) {//目标列不在表中，则重新输入
        	if(flag[i]==false) {
        		System.out.println("含有目标列不存在表中，请重新输入");
        		Input.get();
        		break;
        	}
        }
        List<String> targetkeylist = new ArrayList<>();//转换为list为了后面在显示传参时使用
        for(int i=0; i<targetkey.length;i++) {
        	targetkeylist.add(targetkey[i]);
        }
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
        //处理列的类型
        List<String> typelist = ToolFunctions.getTableOneLine(nowPath,2);
        String[] onetypelist = typelist.get(0).split(sep);//每一列的类型
        //处理表格中的数据,转化为List<List<String>>
        List<List<String>> lists = new ArrayList<>();
        int n = 0;
        for (String s : list) {
            List<String> list1 = new ArrayList<>();
            String[] strings = list.get(n++).split(sep);//分隔后的每一行
        	if(onetypelist[wherestrkeysite].contains("char")) {//判断是否是char类型
        		Pattern pattern = Pattern.compile("\"(.*?)\"");
                Matcher matcher = pattern.matcher(wherestrvalue[1]);
                while(matcher.find()) {//提取引号间的字符串
                	wherestrvalue[1]= matcher.group(1);
                }
            }
            	
            if(wherestrvalue[1].equals(strings[wherestrkeysite])) {
            	for(int v=0; v<targetkeylist.size();v++) {
            		list1.add(strings[targetkeysite[v]]);//插入目标元组
            	}
            }
            if(list1.size()!=0)	{//若为空则不写入
            	lists.add(list1);
            }
        }
        
        System.out.println("已成功选择！");
        System.out.println(ShowTablelist.generateTable(targetkeylist, lists));
            
        Input.get();
    }
}
