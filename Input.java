package mydbms;

/**
 *   ��ȡ���� SQL��䣬������й淶���󴫸�MatchSQL����ƥ��
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
            System.out.print("������SQL��� : ");
            input += " " + scanner.nextLine();
            //���;���ж���ո��ܽ���������
            input = input.replaceFirst("(\\s+)$", "");
        }while(!input.endsWith(";"));//�ԷֺŽ�β
        //��ʽ���ַ���
        String sql = Format.sqlFromat(input);
        MatchSQL.analysis(sql);
        scanner.close();
    }

}
