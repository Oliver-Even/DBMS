package mydbms;

import java.io.File;
import java.util.List;
//import java.util.Scanner;

import mydbms.Input;
import mydbms.Path;
import mydbms.ToolFunctions;

/**
 * 实现drop SQL语句
 * 删除数据库 drop database 数据库名;
 * 删除表 drop table 表名;
 * 
 */
public class Drop {
    private static String dbName;  //数据库名称
    private static String tableName;   //表名
    /**
     * 在该方法中对drop命令进行分类,并执行特定的子命令
     *
     */
    public static void dropSql(String sql){
        //如果是删除数据库的操作
        if(sql.contains(" database ")){
            dbName = sql.substring(14, sql.length()-1);
            //删除该数据库
            dropDatabase();
        }
        else if(sql.contains(" table ")){
            tableName = sql.substring(11, sql.length()-1);
            //删除该表
            dropTable();
        }
        else{
            System.out.println("错误: 无法识别该指令");
        }

    }

    /**
     * 对数据库进行删除
     * 
     */
    private static void dropDatabase(){
        //判断是否有该数据库
        List<String> dbList = ToolFunctions.getAllDatabases(Path.getPath());
        boolean dbexist = dbList.contains(dbName);
        if(dbexist) {
                String nowPath = Path.getPath() + "\\" + dbName;
                if(deleteFile(new File(nowPath))) {
                	System.out.println("成功删除数据库！");
                }
                else
                	System.out.println("删除数据库失败！");
                
        }
        else{
            System.out.println("错误: 该数据库不存在！");
        }
        Input.get();
    }

    /**
     * 递归删除文件夹中的文件
     * 
     */
    public static boolean deleteFile(File dirFile) {
        // 如果文件不存在，则退出
        if (!dirFile.exists()) {
            return false;
        }
        if (dirFile.isFile()) {
            return dirFile.delete();
        } 
        else {
            for (File file : dirFile.listFiles()) {
                deleteFile(file);//递归删除
            }
        }
        return dirFile.delete();
    }

    /**
     * 删除数据表
     * 
     */
    private static void dropTable(){
        //获得当前路径
        String path = Path.getNowPath();
        //判断是否有该数据表
        List<String> tableList = ToolFunctions.getAllTables(path);
        boolean talbeexist = tableList.contains(tableName);
        if(talbeexist) {
            String nowPath = path + "\\" + tableName + ".txt";
            File file = new File(nowPath);
            
            if(file.delete())
            	System.out.println("成功删除表！");
            else
            	System.out.println("删除表失败！");
        }
        else{
            System.out.println("错误: 该表不存在");
        }
        Input.get();
    }
}
