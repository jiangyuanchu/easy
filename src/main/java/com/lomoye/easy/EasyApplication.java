package com.lomoye.easy;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.hikaricp.HikariCpPlugin;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
@MapperScan("com.lomoye.easy.dao")
public class EasyApplication {

	public static void main(String[] args) {
		//jfinal的Db插件配置
		HikariCpPlugin hikariCpPlugin = new HikariCpPlugin("jdbc:mysql://47.94.103.229:3306/easy?useSSL=false&useUnicode=true&characterEncoding=utf8&autoReconnect=true", "root", "jiang980212CHU");
		hikariCpPlugin.start();
		ActiveRecordPlugin arp = new ActiveRecordPlugin(hikariCpPlugin);
		arp.start();

		SpringApplication.run(EasyApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			ctx.getBean(JobEntry.class).start();
		};
	}
}
