package mydbms;
/**
 *主入口，开始测试
 * 
 */
import mydbms.Input;

public class Maintest {
    public static void main(String[] args){
    	System.out.println("------------------欢迎进入--------------------");
    	System.out.println("注意：");
    	System.out.println("\t1、以分号结束语句输入！");
    	System.out.println("\t2、输入语句中符号皆为英文输入法下符号");
    	System.out.println("实现功能示例：");
    	System.out.println("创建数据库mydb：create database mydb; ");
    	System.out.println("创建表t1：create table t1(name char(10) , age int );");
    	System.out.println("在表中插入记录：insert into t1(name,age) values (\"wyf\",19);");
    	System.out.println("查询表中数据：select age from t1 where name=\"wyf\";");
    	System.out.println("删除表中数据：delete from t1 where name=\"wyf\";");
    	System.out.println("更新表中数据：update t1 set age=20 where name=\"wyf\";");
    	System.out.println("删除数据库：drop database mydb;");
    	System.out.println("删除表：drop table t1;");
    	System.out.println("显示路径下数据库：show databases;");
    	System.out.println("显示数据库下文件：show tables;");
    	System.out.println("");
    	System.out.println("");
        Input.get();
    }

}
