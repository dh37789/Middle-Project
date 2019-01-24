package sqlMapConfig;
import java.io.IOException;
import java.io.Reader;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

public class SqlMapClientFactory {

	private static SqlMapClient client;
	
	static{
		// 1. ibatis의 환경설정파일을 읽어와 환경을 설정한다.
		// 1-1. xml문서 읽어오기
		Reader rd;
		try {
			rd = Resources.getResourceAsReader("sqlMapConfig/sqlMapConfig.xml");
			
			// 1-2. 위에서 읽어온 Reader객체를 이용하여 실제 작업을 진행할 객체 생성
			client  = SqlMapClientBuilder.buildSqlMapClient(rd);
		
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static  SqlMapClient getSqlMapClient(){
		return client;
	}
	
}




