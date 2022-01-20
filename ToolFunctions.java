package mydbms;

import java.io.BufferedReader;
/**
 *  ���ݿ��еĹ�����
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
     * �鿴·���������ļ��У���ȡ�������ݿ�����
     *
     */
    public static List<String> getAllDatabases(String path){
        List<String> list = new ArrayList<>();
        File file = new File(path);
        File[] dbList = file.listFiles();
        for(int i = 0; i < dbList.length; i++){
            if(dbList[i].isDirectory()){//�ж��Ƿ���һ��Ŀ¼
                list.add(dbList[i].getName());
            }
        }
        return list;
    }

    /**
     *  �鿴��ǰ·���µ������ļ�����ȡ��ǰ���ݿ��µ����б�
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
                    String tableName = name.substring(0, index);//ֻ���ȡ��������
                    list.add(tableName);
                }
            }
        }
        return list;
    }

    /**
     *  �ж��������������Ƿ�ƥ��
     * 
     */
    public static boolean bracketMatch(String sql){
        Stack<String> stack = new Stack<>();
        char[] charsql = sql.toCharArray();//��SQLתΪ�ַ�����
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
     * �����ļ��кţ���ȡ���е�ĳһ������
     * 
     */
    public static List<String> getTableOneLine(String path,int num){
        List<String> list = new ArrayList<>();
        try {
            File file = new File(path);
            FileReader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String str = "";
            int index = 0;//��Ϊ��ǣ���λ�к�
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
     * �ɻ�ȡ�ñ�����������Լ����ͣ��ָ��󷵻�ֵ
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
                    String sep = Path.getSeparate();     //��ȡ�ָ���
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
     * ���������ݿ��б��е�ֵ��������֮���Ԫ�������
     * 
     */
    public static List<String> getTableTuple(String path){
        List<String> list = new ArrayList<>();
        try {
            File file = new File(path);
            FileReader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String str = "";
            int index = 1;//��λ�е�λ��
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
     *��������ȫ������
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
     * ��ȡ���ǰ���е����ݣ���key��������
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
