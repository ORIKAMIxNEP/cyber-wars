package jp.ac.anan.procon.cyber_wars.config;

import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(
    basePackages = "jp.ac.anan.procon.cyber_wars.infrastructure.mapper.challenge",
    sqlSessionTemplateRef = "challengeSqlSessionTemplate")
public class ChallengeDataSourceConfig {
  @Bean
  @ConfigurationProperties("spring.datasource.challenge")
  public DataSource challengeDataSource() {
    return DataSourceBuilder.create().build();
  }

  @Bean
  public SqlSessionFactory challengeSqlSessionFactory(
      @Qualifier("challengeDataSource") DataSource dataSource) throws Exception {
    SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
    bean.setDataSource(dataSource);

    return bean.getObject();
  }

  @Bean
  public SqlSessionTemplate challengeSqlSessionTemplate(
      @Qualifier("challengeSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
    return new SqlSessionTemplate(sqlSessionFactory);
  }
}
