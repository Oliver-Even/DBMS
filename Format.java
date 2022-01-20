package mydbms;

/**
 *   格式化输入,去掉输入中多余的空格以及别的与内容无关的符号
 * 
 */
public class Format {
    public static String sqlFromat(String input){
        String sql = "";
        //移除字符串左右空白符
        sql = input.trim();
        //将字符串都转化为小写
        String string = sql.toLowerCase();
        //通过正则表达式将多个空格转换为一个空格
        String str = string.replaceAll("\\s{2,}", " ");
        //去掉;前的空格
        String s = str.replaceFirst("( ;)$", ";");
        return s;
    }


}
