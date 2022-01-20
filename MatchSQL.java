package mydbms;

/**
 * ����sql���,����ʵ��create, show, quit, insert, select, drop, delete, update
 * 
 */
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mydbms.Create;
import mydbms.Delete;
import mydbms.Drop;
import mydbms.Input;
import mydbms.Insert;
import mydbms.QuitDBMS;
import mydbms.Select;
import mydbms.Show;
import mydbms.Update;

public class MatchSQL {
    //����ƥ���SQL���
    private static final String create = "create";
    private static final String show = "show";
    private static final String quit = "quit";
    private static final String insert = "insert";
    private static final String select = "select";
    private static final String drop = "drop";
    private static final String delete = "delete";
    private static final String update = "update";


    public static void analysis(String sql){
        String start = "";
        //������ʽ��ƥ�����
        String regex = "^[a-z]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(sql);
        //��ȡƥ��ֵ
        while(matcher.find()){
            start = matcher.group();
        }
        //���ݵ�һ�������жϸ���������
        switch (start){
            case create:
                Create.createSql(sql);
                break;
            case show:
                Show.showSql(sql);
                break;
            case quit:
                QuitDBMS.quitSql();
                break;
            case insert:
                Insert.insertSql(sql);
                break;
            case select:
                Select.selectSql(sql);
                break;
            case drop:
                Drop.dropSql(sql);
                break;
            case delete:
                Delete.deleteSql(sql);
                break;
            case update:
                Update.updateSql(sql);
                break;
            default:
                System.out.println("�����sql����޷�ƥ�䣬���������룡");
                Input.get();
                break;
        }
    }
}
