package mydbms;
/**
 *����ڣ���ʼ����
 * 
 */
import mydbms.Input;

public class Maintest {
    public static void main(String[] args){
    	System.out.println("------------------��ӭ����--------------------");
    	System.out.println("ע�⣺");
    	System.out.println("\t1���ԷֺŽ���������룡");
    	System.out.println("\t2����������з��Ž�ΪӢ�����뷨�·���");
    	System.out.println("ʵ�ֹ���ʾ����");
    	System.out.println("�������ݿ�mydb��create database mydb; ");
    	System.out.println("������t1��create table t1(name char(10) , age int );");
    	System.out.println("�ڱ��в����¼��insert into t1(name,age) values (\"wyf\",19);");
    	System.out.println("��ѯ�������ݣ�select age from t1 where name=\"wyf\";");
    	System.out.println("ɾ���������ݣ�delete from t1 where name=\"wyf\";");
    	System.out.println("���±������ݣ�update t1 set age=20 where name=\"wyf\";");
    	System.out.println("ɾ�����ݿ⣺drop database mydb;");
    	System.out.println("ɾ����drop table t1;");
    	System.out.println("��ʾ·�������ݿ⣺show databases;");
    	System.out.println("��ʾ���ݿ����ļ���show tables;");
    	System.out.println("");
    	System.out.println("");
        Input.get();
    }

}
