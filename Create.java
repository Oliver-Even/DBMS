package mydbms;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 实现create SQL语句
 * e.g. 
 * 创建数据库create database mydb
 * 创建表create table t1(name char(10) , age int);
 * 
 */

public class Create {
    private static String tName = "";
    /**
     * 创建数据库,使用一个文件夹表示一个数据库
     */
    public static void createSql(String sql){
        //判断是否是创建一个数据库
        boolean bdatabaseexist = sql.contains(" database ");
        if(bdatabaseexist){
            String dbName = sql.substring(16, sql.length()-1);
            createDir(dbName);
        }
        //判断是否是创建一个表格
        else{
            boolean tableexist = sql.contains(" table ");
            if(tableexist){
                //判断输入语句的括号是否匹配
                if(ToolFunctions.bracketMatch(sql)) {
                    createTable(sql);
                }
                else{
                    System.out.println("错误: 语句有错误");
                    Input.get();
                }
            }
        }
    }

    /**
     * 创建文件夹
     */
    private static void createDir(String dbName){
        File file = new File(Path.getPath() + "\\" + dbName);
        if(!file.exists()){
            file.mkdir();
        	Path.setNowPath(dbName);
            System.out.println("创建数据库成功！");
            Input.get();
        }
        else{
            System.out.println("错误：数据库已存在！");
            Input.get();
        }
    }

    /**
     *创建表,使用.txt文件来存储表
     */
    private static void createTable(String sql){
        //获取当前数据库所在路径
        String tablePath = Path.getNowPath();
        //获取表名
        int index = sql.indexOf("(");//获取左括号的位置
        if(index != -1){
            tName = sql.substring(13, index) + ".txt";
            tName.trim();//删除左右侧空字符
            //创建表
            File table = new File(tablePath, tName);
            if (!table.exists()) {
                try {
                    table.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //提取出两个括号之间的内容
                Pattern pattern = Pattern.compile("\\(.*\\)");
                Matcher matcher = pattern.matcher(sql);
                String s = "";
                if (matcher.find()) {
                    s = matcher.group(0);
                }
                //分割不同字段
                String s1 = s.substring(1, s.length() - 1);
                String[] strings = s1.split(",");
                //分别存放三行的信息
                List<String> list1 = new ArrayList<>();
                List<String> list2 = new ArrayList<>();
                //第一行存取列名,第二行存取类型
                for(String s2: strings){
                    String s3 = s2.trim();//去掉多余的空格
                    String[] strings1 = s3.split(" ");//以空格分隔列名和类型
                    list1.add(strings1[0]);
                    list2.add(strings1[1]);
                }
                //向表中写入字段信息
                writeFile(list1);
                writeFile(list2);
                System.out.println("创建表成功！");
            }
            else{
                System.out.println("错误: 该表已经存在！");
            }
        }
        else{
            System.out.println("错误: 输入SQL语句错误！");
        }

        Input.get();
    }

    /**
     * 将每一行内容写入文件，以分隔符分隔列间内容
     */
    private static void writeFile(List<String> list){
        //将字符串数组连接成一个字符串
        String str = "";
        //获取分隔符
        String sep = Path.getSeparate();
        //获取当前表的路径
        String path = Path.getNowPath();
        String nowPath = path + "\\" + tName;
        //使用分隔符连接字符串
        for(String s1: list){
            str += s1 + sep;
        }
        try{
            FileOutputStream file = new FileOutputStream(new File(nowPath), true);
            str += "\r\n";
            file.write(str.getBytes());
            file.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
