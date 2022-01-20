package mydbms;
/**
 * ʵ��insert����
 * 
 */
//import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
//import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import mydbms.Input;
import mydbms.Path;
import mydbms.ToolFunctions;

/**
 * �ڱ��в���һ��
 *
 */

public class Insert {
    private static String tableName;
    public static void insertSql(String sql){
        int index = sql.indexOf("(");//���ص�һ����������λ��
        String name = sql.substring(12, index);//��ȡ�ַ����н�������ָ���±�֮����ַ�,��ʼ12λ
        tableName = name.trim();//�Ƴ��ַ������ҿհ׷�
        //�ж��Ƿ��иñ�
        String path = Path.getNowPath();
        List<String> list = ToolFunctions.getAllTables(path);
        boolean tableexist = list.contains(tableName);
        if(tableexist) {
            //�õ���ǰ·��
            String nowPath = path + "\\" + tableName + ".txt";
            //��ȡinsert���������������е�����
            List<String> list1 = new ArrayList<>();
            Pattern pattern = Pattern.compile("\\(.*?\\)");//Pattern����������ڱ���������ʽ�󴴽�һ��ƥ��ģʽ. 
            Matcher matcher = pattern.matcher(sql);//Matcher��ʹ��Patternʵ���ṩ��ģʽ��Ϣ��������ʽ����ƥ�� Pattern��
            while (matcher.find()) {
                list1.add(matcher.group());
            }
            //�ж�����Ƿ���ȷ,�������ŵ���Ŀ
            if (list1.size() != 2) {//������������
                System.out.println("����: ��������д���");
                Input.get();
            }
            else {
                List<String> columnName = ToolFunctions.getColumn(nowPath, 1);//��ȡ����
                List<String> type = ToolFunctions.getColumn(nowPath, 2);//��ȡ����
                String strkey = list1.get(0).substring(1, list1.get(0).length()-1).trim();//��һ������,�ֶ�
                String strvalue = list1.get(1).substring(1, list1.get(1).length()-1).trim();//�ڶ�������,ֵ
                //���ַ�������ת��
                String newstrvalue = transMean(strvalue);
                String[] key = strkey.split(",");
                String[] value = newstrvalue.split(",");
                for(int i = 0; i < key.length; i++){
                    key[i] = key[i].trim();
                }
                for(int i = 0; i < value.length; i++){
                    value[i] = value[i].trim();
                }
                String sep = Path.getSeparate();
                int len = columnName.size();
                if(len == value.length) {
                	String s = "";      //����Ҫ�洢���ַ�������
                    int flagtype = 0;  //����ƥ��
                    for(int i = 0; i < len; i++){
                    	int flag = 0;//�ж��Ƿ��Ѿ�д���ַ�����
                        String s3 = columnName.get(i);//��ȡ��i������
                        for(int j = 0; j < key.length; j++){
                            if(key[j].equals(s3)){//�ж��ֶ���ͬ
                                String s4 = checkType(type.get(i), value[j]);//��������Ƿ�ƥ��
                                if(s4 == null){
                                    flagtype = 1;
                                    break;
                                }
                                else if(s4 == "notchar"){
                                    s += value[j] + sep;
                                    flag = 1;
                                }
                                else {
                                    s += s4 + sep;
                                    flag = 1;
                                }
                            }
                        }
                        if(flag == 0){
                            s += "null" + sep;
                        }
                        if(flagtype == 1){
                            break;
                        }
                    }
                    if(flagtype == 0) {
                        writeFile(s);
                        System.out.println("�ɹ����룡");
                    }
                    Input.get();
                }
                else {
                	System.out.println("�����ֵ�����ֵ��������ƥ�䣡");
                	Input.get();
                }
//                String s = "";      //����Ҫ�洢���ַ�������
//                int flagtype = 0;  //����ƥ��
//                for(int i = 0; i < len; i++){
//                	int flag = 0;//�ж��Ƿ��Ѿ�д���ַ�����
//                    String s3 = columnName.get(i);//��ȡ��i������
//                    for(int j = 0; j < key.length; j++){
//                        if(key[j].equals(s3)){//�ж��ֶ���ͬ
//                            String s4 = checkType(type.get(i), value[j]);//��������Ƿ�ƥ��
//                            if(s4 == null){
//                                flagtype = 1;
//                                break;
//                            }
//                            else if(s4 == "notchar"){
//                                s += value[j] + sep;
//                                flag = 1;
//                            }
//                            else {
//                                s += s4 + sep;
//                                flag = 1;
//                            }
//                        }
//                    }
//                    if(flag == 0){
//                        s += "null" + sep;
//                    }
//                    if(flagtype == 1){
//                        break;
//                    }
//                }
//                if(flagtype == 0) {
//                    writeFile(s);
//                    System.out.println("�ɹ����룡");
//                }
//                Input.get();
            }
        }
        else{
            System.out.println("����: �ñ�����");
            Input.get();
        }

    }

    /**
     * �Դ�����ַ�������ת��,����ַ������зָ���,����Ҫ�ڷָ���ǰ�ټ�һ���ָ���
     * 
     */
    private static String transMean(String str){
        String sep = Path.getSeparate();     //�ָ���
        String s = str.replaceAll(sep, sep + sep);
        return s;
    }
    /**
     * ���ַ���д�����ݿ��ļ���
     * 
     */
    private static void writeFile(String s){
        //��ȡ��ǰ���·��
        String path = Path.getNowPath();
        String nowPath = path + "\\" + tableName + ".txt";
        try{
            FileOutputStream fos = new FileOutputStream(
                    new File(nowPath), true);
            s += "\r\n";
            fos.write(s.getBytes());
            fos.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * �������ֵ��������֮ǰ�����ֶε������Ƿ�ƥ��
     * 
     */
    private static String checkType(String type, String str){
        boolean containschar = type.contains("char");
        if(containschar){
            Pattern pattern = Pattern.compile("\".*\"");
            Matcher matcher = pattern.matcher(str);
            if(matcher.find()){
                String s = str.replaceAll("\"", "");//ȥ���������ţ�����ֵs
                return s;
            }
            else{
                System.out.println("����: ��������ֵ�����Ͳ�ƥ�䣡");
                return null;
            }
        }
        else{
            return "notchar";
        }

    }
}
