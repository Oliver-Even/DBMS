package mydbms;
/**
 * 实现insert命令
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
 * 在表中插入一行
 *
 */

public class Insert {
    private static String tableName;
    public static void insertSql(String sql){
        int index = sql.indexOf("(");//返回第一个括号所在位置
        String name = sql.substring(12, index);//提取字符串中介于两个指定下标之间的字符,开始12位
        tableName = name.trim();//移除字符串左右空白符
        //判断是否有该表
        String path = Path.getNowPath();
        List<String> list = ToolFunctions.getAllTables(path);
        boolean tableexist = list.contains(tableName);
        if(tableexist) {
            //得到当前路径
            String nowPath = path + "\\" + tableName + ".txt";
            //获取insert命令中两个括号中的内容
            List<String> list1 = new ArrayList<>();
            Pattern pattern = Pattern.compile("\\(.*?\\)");//Pattern类的作用在于编译正则表达式后创建一个匹配模式. 
            Matcher matcher = pattern.matcher(sql);//Matcher类使用Pattern实例提供的模式信息对正则表达式进行匹配 Pattern类
            while (matcher.find()) {
                list1.add(matcher.group());
            }
            //判断语句是否正确,根据括号的数目
            if (list1.size() != 2) {//两个括号里面
                System.out.println("错误: 输入语句有错误");
                Input.get();
            }
            else {
                List<String> columnName = ToolFunctions.getColumn(nowPath, 1);//获取列名
                List<String> type = ToolFunctions.getColumn(nowPath, 2);//获取类型
                String strkey = list1.get(0).substring(1, list1.get(0).length()-1).trim();//第一个括号,字段
                String strvalue = list1.get(1).substring(1, list1.get(1).length()-1).trim();//第二个括号,值
                //对字符串进行转义
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
                	String s = "";      //最终要存储的字符串对象
                    int flagtype = 0;  //类型匹配
                    for(int i = 0; i < len; i++){
                    	int flag = 0;//判断是否已经写入字符串中
                        String s3 = columnName.get(i);//获取第i个列名
                        for(int j = 0; j < key.length; j++){
                            if(key[j].equals(s3)){//判断字段相同
                                String s4 = checkType(type.get(i), value[j]);//检查类型是否匹配
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
                        System.out.println("成功插入！");
                    }
                    Input.get();
                }
                else {
                	System.out.println("输入的值与表中值的数量不匹配！");
                	Input.get();
                }
//                String s = "";      //最终要存储的字符串对象
//                int flagtype = 0;  //类型匹配
//                for(int i = 0; i < len; i++){
//                	int flag = 0;//判断是否已经写入字符串中
//                    String s3 = columnName.get(i);//获取第i个列名
//                    for(int j = 0; j < key.length; j++){
//                        if(key[j].equals(s3)){//判断字段相同
//                            String s4 = checkType(type.get(i), value[j]);//检查类型是否匹配
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
//                    System.out.println("成功插入！");
//                }
//                Input.get();
            }
        }
        else{
            System.out.println("错误: 该表不存在");
            Input.get();
        }

    }

    /**
     * 对传入的字符串进行转义,如果字符串中有分隔符,则需要在分隔符前再加一个分隔符
     * 
     */
    private static String transMean(String str){
        String sep = Path.getSeparate();     //分隔符
        String s = str.replaceAll(sep, sep + sep);
        return s;
    }
    /**
     * 将字符串写入数据库文件中
     * 
     */
    private static void writeFile(String s){
        //获取当前表的路径
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
     * 检查输入值的类型与之前定义字段的类型是否匹配
     * 
     */
    private static String checkType(String type, String str){
        boolean containschar = type.contains("char");
        if(containschar){
            Pattern pattern = Pattern.compile("\".*\"");
            Matcher matcher = pattern.matcher(str);
            if(matcher.find()){
                String s = str.replaceAll("\"", "");//去掉两个引号，返回值s
                return s;
            }
            else{
                System.out.println("错误: 存在输入值的类型不匹配！");
                return null;
            }
        }
        else{
            return "notchar";
        }

    }
}
