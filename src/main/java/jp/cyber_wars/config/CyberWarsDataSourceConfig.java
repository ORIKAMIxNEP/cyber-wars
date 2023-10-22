package jp.cyber_wars.config;

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
import org.springframework.context.annotation.Primary;

@Configuration
@MapperScan(
    basePackages = "jp.ac.anan.procon.cyber_wars.infrastructure.mapper.cyber_wars",
    sqlSessionTemplateRef = "cyberWarsSqlSessionTemplate")
public class CyberWarsDataSourceConfig {
  @Bean
  @ConfigurationProperties("spring.datasource.cyber-wars")
  @Primary
  public DataSource cyberWarsDataSource() {
    return DataSourceBuilder.create().build();
  }

  @Bean
  @Primary
  public SqlSessionFactory cyberWarsSqlSessionFactory(
      @Qualifier("cyberWarsDataSource") DataSource dataSource) throws Exception {
    SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
    bean.setDataSource(dataSource);

    return bean.getObject();
  }

  @Bean
  @Primary
  public SqlSessionTemplate cyberWarsSqlSessionTemplate(
      @Qualifier("cyberWarsSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
    return new SqlSessionTemplate(sqlSessionFactory);
  }
}
