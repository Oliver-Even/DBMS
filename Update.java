package mydbms;
/**
 * ʵ��update����
 *
 */
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mydbms.Input;
import mydbms.Path;

public class Update {
	private static String tableName;
	public static void updateSql(String sql){
		//�õ��ָ���
        String sep = Path.getSeparate();
        //��õ�ǰ·��,�����ݿ��ļ���
        String path = Path.getNowPath();
        int index1 = sql.indexOf("set");
        int index2 = sql.indexOf("where");
        String name = sql.substring(7, index1);
        tableName = name.trim();//�Ƴ��ַ����ұ߿հ��ַ�
        //��ǰ�������·��
        String nowPath = path + "\\" + tableName + ".txt";
        //�õ�����
        List<String> columnName = ToolFunctions.getColumn(nowPath, 1);//����       
        //�õ�set�Ӿ�����
        String setstr = sql.substring(index1+3 , index2);
        setstr = setstr.trim();
        String[] setstrvalue = setstr.split("=");//���where�Ⱥ����ߵ�����    
        //�ҵ�set�Ӿ����ڵ���λ��
        int setstrkeysite = 0;
        int mset = 0;
        boolean falgsetsite=false;
        while(mset<columnName.size()) {
    		if(setstrvalue[0].equals(columnName.get(mset))) {
    			falgsetsite=true;
    			setstrkeysite = mset; 
    			break;
    		}
    		else {
    			mset++;
    		}			
        }
        if(!falgsetsite) {
        	System.out.println("�Ҳ���set�Ӿ�����key");
        	Input.get();
        }
        //�ж��Ƿ���char���ͣ��������ȡ����֮�������
        //�����е�����
        List<String> settypelist = ToolFunctions.getTableOneLine(nowPath,2);
        String[] setonetypelist = settypelist.get(0).split(sep);//ÿһ�е�����
        if(setonetypelist[setstrkeysite].contains("char")) {//�ж��Ƿ���char����
	  		Pattern pattern = Pattern.compile("\"(.*?)\"");
	        Matcher matcher = pattern.matcher(setstrvalue[1]);
	        while(matcher.find()) {//��ȡ���ż���ַ���
	          	setstrvalue[1]= matcher.group(1);
	        }
        }
        
        //�õ�where�Ӿ�����
        String wherestr = sql.substring(index2+5 , sql.length()-1);
        wherestr = wherestr.trim();
        String[] wherestrvalue = wherestr.split("=");//���where�Ⱥ����ߵ�����
        //�ҵ�where�Ӿ����ڵ���λ��
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
        	System.out.println("�Ҳ���where�Ӿ�����key");
        	Input.get();
        }
        //�ж��Ƿ��char���ͣ��������ȡ����֮�������
        //�����е�����
        List<String> wheretypelist = ToolFunctions.getTableOneLine(nowPath,2);
        String[] whereonetypelist = wheretypelist.get(0).split(sep);//ÿһ�е�����
        if(whereonetypelist[wherestrkeysite].contains("char")) {//�ж��Ƿ���char����
    		Pattern pattern = Pattern.compile("\"(.*?)\"");
            Matcher matcher = pattern.matcher(wherestrvalue[1]);
            while(matcher.find()) {//��ȡ���ż���ַ���
            	wherestrvalue[1]= matcher.group(1);
            }
        }   
        updateline(wherestrvalue[1],wherestrkeysite,setstrvalue[1],setstrkeysite,nowPath);
        //��ӡ���º�ı�
        //��ñ��е�����
        List<String> list = ToolFunctions.getTableTuple(nowPath);
        List<List<String>> lists = new ArrayList<>();
        int i = 0;
        for (String s : list) {
            List<String> list1 = new ArrayList<>();
            String[] strings = list.get(i++).split(sep);
            for (String s1 : strings) {
                list1.add(s1);
            }
            lists.add(list1);
        }
        System.out.println(ShowTablelist.generateTable(columnName, lists));
        System.out.println("�ɹ����ģ�");
        Input.get();
	}

	
	/**
     *  �ҵ�where�Ӿ�������ݣ�ɾ����ӦԪ��
     * 
      * ����key����λ�ã��Լ�value��ֵ����ȡһ�У��ָ��λ���ж��Ƿ�ɾ��
     */
	
	 public static void updateline(String keyvalue,int keysite,String setvalue,int setkeysite,String nowPath){
	        try {
	        	
	            List<String> list = ToolFunctions.getAllTableLine(nowPath);
	            String sep = Path.getSeparate();     //��ȡ�ָ���
	            ListIterator<String> listiterator = list.listIterator();
	            while(listiterator.hasNext()) {
		            String strings = listiterator.next();
		            String[] sepstrings = strings.split(sep);
		            if(sepstrings[keysite].equals(keyvalue)){
		            	String oldsetvalue = sepstrings[setkeysite];	            		
		            	String newstrings = strings.replace(oldsetvalue, setvalue);	
		            	listiterator.set(newstrings);
		            }   
	            }
	            FileWriter fd = new FileWriter(nowPath, false);//append����false��ʾд������ʱ���Ḳ���ļ���֮ǰ���ڵ�����
                fd.write("");//ִ��ɾ��������д������ݸ���֮ǰ������
                fd.close();
                for (String newline : list) {
                    FileWriter fw = new FileWriter(nowPath, true);//append����true��ʾд������ʱ�����Ḳ���ļ���֮ǰ���ڵ����ݣ����µ�����д��֮ǰ���ݵĺ���
                    fw.write(newline);//ִ������д�����ݵĲ���
                    fw.write(System.getProperty("line.separator"));//�ڶ�������һ�����з�
                    fw.close();
                }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
    }
}
