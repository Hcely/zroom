package zr.gen.db;

import java.util.Map;

import v.Destoryable;
import v.Initializable;
import zr.gen.table.TableInfo;

public interface DatabaseFactory extends Initializable, Destoryable {
	public Map<String, TableInfo> getTables(String[] tables);
}
