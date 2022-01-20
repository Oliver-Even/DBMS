package mydbms;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import mydbms.Input;
import mydbms.Path;
import mydbms.ToolFunctions;

/**
 * ʵ��delete SQL���
 * e.g. 
 * ɾ�����һ�У�Ŀǰwhere�Ӿ�ֻ����һ��������Ϊ��ʽ��delete from t1 where name="wyf";
 */
public class Delete {
	private static String tableName;
	public static void deleteSql(String sql){
        String sep = Path.getSeparate();
        //��ȡ����
        String path = Path.getNowPath();
        int index1 = sql.indexOf("from");
        int index2 = sql.indexOf("where");
        String name = sql.substring(index1+5, index2);
        tableName = name.trim();//�Ƴ��ַ������ҿհ׷�
        //��ǰ�������·��
        String nowPath = path + "\\" + tableName + ".txt";
        //�õ�����
        List<String> columnName = ToolFunctions.getColumn(nowPath, 1);
        //�õ�where�Ӿ�����
        String wherestr = sql.substring(index2+5 , sql.length()-1);
        wherestr = wherestr.trim();
        String[] wherestrvalue = wherestr.split("=");//���where�Ⱥ����ߵ�����
        //�ҵ�where�Ӿ��key���ڵ���λ��
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
        //�ж��Ƿ��С�char�����������ȡ����֮�������
        //��ȡkey������
        List<String> typelist = ToolFunctions.getColumn(nowPath,2);
        String[] onetypelist = typelist.get(0).split(sep);//ÿһ�е�����
        if(onetypelist[wherestrkeysite].contains("char")) {//�ж��Ƿ���char����
    		Pattern pattern = Pattern.compile("\"(.*?)\"");
            Matcher matcher = pattern.matcher(wherestrvalue[1]);
            while(matcher.find()) {//��ȡ���ż���ַ���
            	wherestrvalue[1]= matcher.group(1);
            }
        }
        //ɾ����Ӧ����
        deleteline(wherestrvalue[1],wherestrkeysite,nowPath);
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
        //��ӡɾ����ı�
        System.out.println(ShowTablelist.generateTable(columnName, lists));
        System.out.println("�ѳɹ�ɾ�����е��У�");
        Input.get();
	}
	/**
     * �ҵ�where�Ӿ��ֵ��ƥ�����ݣ�ɾ����ӦԪ��
      * ����key����λ�ã��Լ�value��ֵ����ȡһ�У��ָ��λ���ж��Ƿ�ɾ��
     */
	
	 public static void deleteline(String keyvalue,int keysite,String nowPath){
	        try {
	        	
	        	//���δɾ��ʱ���е�����
	        	List<String> list = ToolFunctions.getAllTableLine(nowPath);
	        	String sep = Path.getSeparate();     //��ȡ�ָ���
	            Iterator<String> iterator = list.iterator();
	            while(iterator.hasNext()) {
	            	String strings = iterator.next();
	            	String[] sepstrings = strings.split(sep);
	            	if(sepstrings[keysite].equals(keyvalue)){
	                	iterator.remove();
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
