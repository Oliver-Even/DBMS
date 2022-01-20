package mydbms;

import java.io.BufferedReader;
/**
 *  数据库中的工具类
 * 
 */
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ToolFunctions {
    /**
     * 查看路径下所有文件夹，获取所有数据库名称
     *
     */
    public static List<String> getAllDatabases(String path){
        List<String> list = new ArrayList<>();
        File file = new File(path);
        File[] dbList = file.listFiles();
        for(int i = 0; i < dbList.length; i++){
            if(dbList[i].isDirectory()){//判断是否是一个目录
                list.add(dbList[i].getName());
            }
        }
        return list;
    }

    /**
     *  查看当前路径下的所有文件，获取当前数据库下的所有表
     * 
     */
    public static List<String> getAllTables(String nowPath){
        List<String> list = new ArrayList<>();
        File file = new File(nowPath);
        File[] tableList = file.listFiles();
        if(tableList != null){
            for(int i = 0; i < tableList.length; i++) {
                if (tableList[i].isFile()) {
                    String name = tableList[i].getName();
                    int index = name.lastIndexOf(".");
                    String tableName = name.substring(0, index);//只需获取表名即可
                    list.add(tableName);
                }
            }
        }
        return list;
    }

    /**
     *  判断输入语句的括号是否匹配
     * 
     */
    public static boolean bracketMatch(String sql){
        Stack<String> stack = new Stack<>();
        char[] charsql = sql.toCharArray();//将SQL转为字符数组
        for(int i = 0; i < charsql.length; i++){
            if(charsql[i] == '('){
                stack.push("(");
            }
            else if(charsql[i] == ')'){
                if(stack.empty()){
                    return false;
                }
                else{
                    stack.pop();
                }
            }
        }
        return true;
    }


    /**
     * 给定文件行号，读取表中的某一行内容
     * 
     */
    public static List<String> getTableOneLine(String path,int num){
        List<String> list = new ArrayList<>();
        try {
            File file = new File(path);
            FileReader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String str = "";
            int index = 0;//作为标记，定位行号
            while(index < num && (str = bufferedReader.readLine()) != null){
                index++;
            }
            list.add(str);
            bufferedReader.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 可获取该表的所有列名以及类型，分隔后返回值
     * 
     */
    public static List<String> getColumn(String path, int num){
        List<String> list = new ArrayList<>();
        try {
            File file = new File(path);
            FileReader reader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(reader);
            String s = "";
            int index = 0;
            while((s = bufferedReader.readLine()) != null){
                index++;
                if(index == num) {
                    String sep = Path.getSeparate();     //获取分隔符
                    String[] strings = s.split(sep);
                    for(String s1:strings){
                        list.add(s1);
                    }
                    bufferedReader.close();
                    return list;
                }
            }
            bufferedReader.close();            
        }catch(IOException e){
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 仅读出数据库中表中的值，即两行之后的元组的内容
     * 
     */
    public static List<String> getTableTuple(String path){
        List<String> list = new ArrayList<>();
        try {
            File file = new File(path);
            FileReader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String str = "";
            int index = 1;//定位行的位置
            while((str = bufferedReader.readLine()) != null){
                index++;
                if(index > 3){
                    list.add(str);
                }
            }
            bufferedReader.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        return list;
    }
    /**
     *读出表中全部内容
     * 
     */
    public static List<String> getAllTableLine(String path){
        List<String> list = new ArrayList<>();
        try {
            File file = new File(path);
            FileReader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String str = "";
            while((str = bufferedReader.readLine()) != null){
                    list.add(str);
            }
            bufferedReader.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        return list;
    }
    
    /**
     * 获取表的前两行的内容，即key及其类型
     *
     */
    public static List<String> getTableHead(String path){
        List<String> list = new ArrayList<>();
        try {
            File file = new File(path);
            FileReader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String str = "";
            int index = 1;
            while((str = bufferedReader.readLine()) != null && index < 3){
                index++;
                list.add(str);
            }
            bufferedReader.close();
        }catch(IOException e){
            e.printStackTrace();
        }

        return list;
    }
}
