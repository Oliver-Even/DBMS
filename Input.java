package mydbms;

/**
 *   获取输入 SQL语句，对其进行规范化后传给MatchSQL进行匹配
 *
 */
import java.util.Scanner;
import mydbms.Format;
import mydbms.MatchSQL;

public class Input {
    public static void get(){
        Scanner scanner = new Scanner(System.in);
        String input = "";
        do{
            System.out.print("请输入SQL语句 : ");
            input += " " + scanner.nextLine();
            //解决;后有多个空格不能结束的问题
            input = input.replaceFirst("(\\s+)$", "");
        }while(!input.endsWith(";"));//以分号结尾
        //格式化字符串
        String sql = Format.sqlFromat(input);
        MatchSQL.analysis(sql);
        scanner.close();
    }

}
