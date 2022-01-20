package mydbms;

/**
 *  ʵ��select���
 * 
 */
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
//import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import mydbms.Input;
import mydbms.Path;
import mydbms.ShowTablelist;
import mydbms.ToolFunctions;

public class Select {
	private static String tableName;
    public static void selectSql(String sql){
        //�õ��ָ���
        String sep = Path.getSeparate();
        //��õ�ǰ·��,�����ݿ��ļ���
        String path = Path.getNowPath();   
        int index1 = sql.indexOf("from");//����from����λ��
        int index2 = sql.indexOf("where");
        String name = sql.substring(index1 + 5, index2);
        tableName = name.trim();
        //�õ�where�Ӿ�����
        String wherestr = sql.substring(index2+5 , sql.length()-1);
        wherestr = wherestr.trim();
        String[] wherestrvalue = wherestr.split("=");//���where�Ⱥ����ߵ����� 
        //��ǰ�������·��
        String nowPath = path + "\\" + tableName + ".txt";
        //��ñ��е�����
        List<String> list = ToolFunctions.getTableTuple(nowPath);
        String targetlist = sql.substring(7, index1);//�õ�Ŀ����
        targetlist = targetlist.trim();
        String[] targetkey = targetlist.split(",");//�ָ����key
        List<String> columnName = ToolFunctions.getColumn(nowPath, 1);//����
        int j= 0;
        int[] targetkeysite = new int[targetkey.length];//���Ŀ�������ڱ�ͷ���ڵڼ���λ��
        boolean [] flag = new boolean[targetkey.length];//�ж�Ŀ�����Ƿ��ڱ�����
        for(int i= 0; i<targetkey.length;i++) {
        	j=0;
        	while(j<columnName.size()) {
        		if(targetkey[i].equals(columnName.get(j))) {
        			flag[i]=true;
        			targetkeysite[i] = j; 
        			break;
        		}
        		else
        			j++;
        	}
        }
        for(int i= 0; i<targetkey.length;i++) {//Ŀ���в��ڱ��У�����������
        	if(flag[i]==false) {
        		System.out.println("����Ŀ���в����ڱ��У�����������");
        		Input.get();
        		break;
        	}
        }
        List<String> targetkeylist = new ArrayList<>();//ת��ΪlistΪ�˺�������ʾ����ʱʹ��
        for(int i=0; i<targetkey.length;i++) {
        	targetkeylist.add(targetkey[i]);
        }
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
        //�����е�����
        List<String> typelist = ToolFunctions.getTableOneLine(nowPath,2);
        String[] onetypelist = typelist.get(0).split(sep);//ÿһ�е�����
        //�������е�����,ת��ΪList<List<String>>
        List<List<String>> lists = new ArrayList<>();
        int n = 0;
        for (String s : list) {
            List<String> list1 = new ArrayList<>();
            String[] strings = list.get(n++).split(sep);//�ָ����ÿһ��
        	if(onetypelist[wherestrkeysite].contains("char")) {//�ж��Ƿ���char����
        		Pattern pattern = Pattern.compile("\"(.*?)\"");
                Matcher matcher = pattern.matcher(wherestrvalue[1]);
                while(matcher.find()) {//��ȡ���ż���ַ���
                	wherestrvalue[1]= matcher.group(1);
                }
            }
            	
            if(wherestrvalue[1].equals(strings[wherestrkeysite])) {
            	for(int v=0; v<targetkeylist.size();v++) {
            		list1.add(strings[targetkeysite[v]]);//����Ŀ��Ԫ��
            	}
            }
            if(list1.size()!=0)	{//��Ϊ����д��
            	lists.add(list1);
            }
        }
        
        System.out.println("�ѳɹ�ѡ��");
        System.out.println(ShowTablelist.generateTable(targetkeylist, lists));
            
        Input.get();
    }
}
