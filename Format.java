package mydbms;

/**
 *   ��ʽ������,ȥ�������ж���Ŀո��Լ�����������޹صķ���
 * 
 */
public class Format {
    public static String sqlFromat(String input){
        String sql = "";
        //�Ƴ��ַ������ҿհ׷�
        sql = input.trim();
        //���ַ�����ת��ΪСд
        String string = sql.toLowerCase();
        //ͨ��������ʽ������ո�ת��Ϊһ���ո�
        String str = string.replaceAll("\\s{2,}", " ");
        //ȥ��;ǰ�Ŀո�
        String s = str.replaceFirst("( ;)$", ";");
        return s;
    }


}
