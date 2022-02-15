package cn.enn.test.test;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class App {

	public static void main(String[] args) {


		String createVWSql = "CREATE VIEW `vw_person_2` AS SELECT `id` AS `id`, `name` AS `name`, `tel` AS `tel`, `city` AS `city` " +
				"FROM (select `person`.`id`,`person`.`name`, `person`.`tel`,`person`.`city` from `default`.`person`) `default.vw_person_2";

		String selectSql = StringUtils.substringBetween(createVWSql, "(", ")");
		log.info("===>{}", selectSql);

		String colSql = StringUtils.substringBetween(selectSql, "select", "from");
		String colSqlTrim = StringUtils.trim(colSql);
		log.info("===>{}", colSqlTrim);


		String[] split = StringUtils.split(colSqlTrim, ",");
		List<String> strings = Arrays.asList(split);
		log.info("===>{}", strings);




	}

}
