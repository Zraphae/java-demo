package cn.enn.test.sql;

import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Select;

@Slf4j
public class App {
    public static void main(String[] args) throws JSQLParserException {
        Statement parse = CCJSqlParserUtil.parse("select * from aaa");
        log.info("====>{}", parse);

    }
}
