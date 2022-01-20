package mydbms;
/**
 *  数据库路径以及分隔符设置
 *  
 */
public class Path {
    //数据库的根路径
    private static final String path = "D:\\MyDatabase";
    //数据库的当前路径
    private static String nowPath = path;
    //自定义的分隔符
    private static final String separate = "#";
    
    /**
     * 获取路径
     *
     */
    public static String getPath(){
        return path;
    }
    /**
     * 获取当前路径
     *
     */
    public static String getNowPath(){
        return nowPath;
    }

    /**
     * 设置当前路径
     * 
     */
    public static void setNowPath(String name){
        nowPath = nowPath + "\\" + name;
    }

    /**
     * 获取分隔符
     * 
     */
    public static String getSeparate(){
        return separate;
    }


}
