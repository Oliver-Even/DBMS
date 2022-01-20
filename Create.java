package mydbms;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ʵ��create SQL���
 * e.g. 
 * �������ݿ�create database mydb
 * ������create table t1(name char(10) , age int);
 * 
 */

public class Create {
    private static String tName = "";
    /**
     * �������ݿ�,ʹ��һ���ļ��б�ʾһ�����ݿ�
     */
    public static void createSql(String sql){
        //�ж��Ƿ��Ǵ���һ�����ݿ�
        boolean bdatabaseexist = sql.contains(" database ");
        if(bdatabaseexist){
            String dbName = sql.substring(16, sql.length()-1);
            createDir(dbName);
        }
        //�ж��Ƿ��Ǵ���һ�����
        else{
            boolean tableexist = sql.contains(" table ");
            if(tableexist){
                //�ж��������������Ƿ�ƥ��
                if(ToolFunctions.bracketMatch(sql)) {
                    createTable(sql);
                }
                else{
                    System.out.println("����: ����д���");
                    Input.get();
                }
            }
        }
    }

    /**
     * �����ļ���
     */
    private static void createDir(String dbName){
        File file = new File(Path.getPath() + "\\" + dbName);
        if(!file.exists()){
            file.mkdir();
        	Path.setNowPath(dbName);
            System.out.println("�������ݿ�ɹ���");
            Input.get();
        }
        else{
            System.out.println("�������ݿ��Ѵ��ڣ�");
            Input.get();
        }
    }

    /**
     *������,ʹ��.txt�ļ����洢��
     */
    private static void createTable(String sql){
        //��ȡ��ǰ���ݿ�����·��
        String tablePath = Path.getNowPath();
        //��ȡ����
        int index = sql.indexOf("(");//��ȡ�����ŵ�λ��
        if(index != -1){
            tName = sql.substring(13, index) + ".txt";
            tName.trim();//ɾ�����Ҳ���ַ�
            //������
            File table = new File(tablePath, tName);
            if (!table.exists()) {
                try {
                    table.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //��ȡ����������֮�������
                Pattern pattern = Pattern.compile("\\(.*\\)");
                Matcher matcher = pattern.matcher(sql);
                String s = "";
                if (matcher.find()) {
                    s = matcher.group(0);
                }
                //�ָͬ�ֶ�
                String s1 = s.substring(1, s.length() - 1);
                String[] strings = s1.split(",");
                //�ֱ������е���Ϣ
                List<String> list1 = new ArrayList<>();
                List<String> list2 = new ArrayList<>();
                //��һ�д�ȡ����,�ڶ��д�ȡ����
                for(String s2: strings){
                    String s3 = s2.trim();//ȥ������Ŀո�
                    String[] strings1 = s3.split(" ");//�Կո�ָ�����������
                    list1.add(strings1[0]);
                    list2.add(strings1[1]);
                }
                //�����д���ֶ���Ϣ
                writeFile(list1);
                writeFile(list2);
                System.out.println("������ɹ���");
            }
            else{
                System.out.println("����: �ñ��Ѿ����ڣ�");
            }
        }
        else{
            System.out.println("����: ����SQL������");
        }

        Input.get();
    }

    /**
     * ��ÿһ������д���ļ����Էָ����ָ��м�����
     */
    private static void writeFile(List<String> list){
        //���ַ����������ӳ�һ���ַ���
        String str = "";
        //��ȡ�ָ���
        String sep = Path.getSeparate();
        //��ȡ��ǰ���·��
        String path = Path.getNowPath();
        String nowPath = path + "\\" + tName;
        //ʹ�÷ָ��������ַ���
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
