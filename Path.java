package mydbms;
/**
 *  ���ݿ�·���Լ��ָ�������
 *  
 */
public class Path {
    //���ݿ�ĸ�·��
    private static final String path = "D:\\MyDatabase";
    //���ݿ�ĵ�ǰ·��
    private static String nowPath = path;
    //�Զ���ķָ���
    private static final String separate = "#";
    
    /**
     * ��ȡ·��
     *
     */
    public static String getPath(){
        return path;
    }
    /**
     * ��ȡ��ǰ·��
     *
     */
    public static String getNowPath(){
        return nowPath;
    }

    /**
     * ���õ�ǰ·��
     * 
     */
    public static void setNowPath(String name){
        nowPath = nowPath + "\\" + name;
    }

    /**
     * ��ȡ�ָ���
     * 
     */
    public static String getSeparate(){
        return separate;
    }


}
