package mydbms;

import java.io.File;
import java.util.List;
//import java.util.Scanner;

import mydbms.Input;
import mydbms.Path;
import mydbms.ToolFunctions;

/**
 * ʵ��drop SQL���
 * ɾ�����ݿ� drop database ���ݿ���;
 * ɾ���� drop table ����;
 * 
 */
public class Drop {
    private static String dbName;  //���ݿ�����
    private static String tableName;   //����
    /**
     * �ڸ÷����ж�drop������з���,��ִ���ض���������
     *
     */
    public static void dropSql(String sql){
        //�����ɾ�����ݿ�Ĳ���
        if(sql.contains(" database ")){
            dbName = sql.substring(14, sql.length()-1);
            //ɾ�������ݿ�
            dropDatabase();
        }
        else if(sql.contains(" table ")){
            tableName = sql.substring(11, sql.length()-1);
            //ɾ���ñ�
            dropTable();
        }
        else{
            System.out.println("����: �޷�ʶ���ָ��");
        }

    }

    /**
     * �����ݿ����ɾ��
     * 
     */
    private static void dropDatabase(){
        //�ж��Ƿ��и����ݿ�
        List<String> dbList = ToolFunctions.getAllDatabases(Path.getPath());
        boolean dbexist = dbList.contains(dbName);
        if(dbexist) {
                String nowPath = Path.getPath() + "\\" + dbName;
                if(deleteFile(new File(nowPath))) {
                	System.out.println("�ɹ�ɾ�����ݿ⣡");
                }
                else
                	System.out.println("ɾ�����ݿ�ʧ�ܣ�");
                
        }
        else{
            System.out.println("����: �����ݿⲻ���ڣ�");
        }
        Input.get();
    }

    /**
     * �ݹ�ɾ���ļ����е��ļ�
     * 
     */
    public static boolean deleteFile(File dirFile) {
        // ����ļ������ڣ����˳�
        if (!dirFile.exists()) {
            return false;
        }
        if (dirFile.isFile()) {
            return dirFile.delete();
        } 
        else {
            for (File file : dirFile.listFiles()) {
                deleteFile(file);//�ݹ�ɾ��
            }
        }
        return dirFile.delete();
    }

    /**
     * ɾ�����ݱ�
     * 
     */
    private static void dropTable(){
        //��õ�ǰ·��
        String path = Path.getNowPath();
        //�ж��Ƿ��и����ݱ�
        List<String> tableList = ToolFunctions.getAllTables(path);
        boolean talbeexist = tableList.contains(tableName);
        if(talbeexist) {
            String nowPath = path + "\\" + tableName + ".txt";
            File file = new File(nowPath);
            
            if(file.delete())
            	System.out.println("�ɹ�ɾ����");
            else
            	System.out.println("ɾ����ʧ�ܣ�");
        }
        else{
            System.out.println("����: �ñ�����");
        }
        Input.get();
    }
}
