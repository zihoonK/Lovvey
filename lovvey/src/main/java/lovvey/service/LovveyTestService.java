package lovvey.service;

import java.util.List;

import lovvey.domain.Test;

public interface LovveyTestService {

	String insertTest(Test vo) throws Exception;
	String updateTest(Test vo) throws Exception;
	String deleteTest(Test vo) throws Exception;
	List<?> SelectAllTest(Test vo) throws Exception;
	
}
