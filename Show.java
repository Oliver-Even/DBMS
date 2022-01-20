package mydbms;

/**
 *��showΪ��ͷ������
 * 
 */
import java.util.ArrayList;
import java.util.List;

import mydbms.Input;
import mydbms.Path;
import mydbms.ShowTablelist;
import mydbms.ToolFunctions;

public class Show {
    public static void showSql(String sql){
        //�г��������ݿ�
        boolean dbend = sql.endsWith(" databases;");
        if(dbend){
            String path = Path.getPath();
            List<String> dbList = ToolFunctions.getAllDatabases(path);
            List<String> db = new ArrayList<>();
            db.add("Database");
            List<List<String>> list = new ArrayList<>();
            for(int i = 0; i < dbList.size(); i++){
                List<String> ls = new ArrayList<>();
                ls.add(dbList.get(i));
                list.add(ls);
            }
            System.out.println(ShowTablelist.generateTable(db, list));
            Input.get();
        }
        //�г��������ݿ������еı��
        else{
            boolean tableend = sql.endsWith(" tables;");
            if(tableend){
                String nowPath = Path.getNowPath();
                List<String> tableList = ToolFunctions.getAllTables(nowPath);
                List<String> list = new ArrayList<>();
                //��ȡ���ݿ���
                int index = nowPath.lastIndexOf("\\");
                String dbName = nowPath.substring(index+1, nowPath.length());
                list.add(dbName);
                List<List<String>> tList = new ArrayList<>();
                for(String t:tableList){
                    List<String> list1 = new ArrayList<>();
                    list1.add(t);
                    tList.add(list1);
                }
                System.out.println(ShowTablelist.generateTable(list, tList));
                Input.get();
            }
        }
    }
}
