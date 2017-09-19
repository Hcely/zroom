package zr.mybatis;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Repository;

import v.Clearable;
import v.Initializable;
import zr.mybatis.info.MapperConfigInfo;

@SuppressWarnings("rawtypes")
public class SpringMybatisHelper implements ApplicationContextAware, Initializable, Clearable {
	protected Map<MapperConfigInfo, SimpleMapper> mapperMap;
	protected ApplicationContext appContext;
	protected boolean ignoreEmpty = false;
	protected boolean insertAsMap = false;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.appContext = applicationContext;
	}

	public boolean isIgnoreEmpty() {
		return ignoreEmpty;
	}

	public void setIgnoreEmpty(boolean ignoreEmpty) {
		this.ignoreEmpty = ignoreEmpty;
	}

	public boolean isInsertAsMap() {
		return insertAsMap;
	}

	public void setInsertAsMap(boolean insertAsMap) {
		this.insertAsMap = insertAsMap;
	}

	@PostConstruct
	@Override
	public void init() {
		Map<String, Object> beans = appContext.getBeansWithAnnotation(Repository.class);

	}

	@PreDestroy
	@Override
	public void clear() {

	}
}
