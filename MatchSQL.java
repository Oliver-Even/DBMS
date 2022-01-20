package mydbms;

/**
 * 解析sql语句,可以实现create, show, quit, insert, select, drop, delete, update
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
    //可以匹配的SQL语句
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
        //正则表达式的匹配规则
        String regex = "^[a-z]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(sql);
        //获取匹配值
        while(matcher.find()){
            start = matcher.group();
        }
        //根据第一个单词判断该语句的作用
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
                System.out.println("输入的sql语句无法匹配，请重新输入！");
                Input.get();
                break;
        }
    }
}
