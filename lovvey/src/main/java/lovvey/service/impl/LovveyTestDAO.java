package lovvey.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import lovvey.domain.Test;
import lovvey.service.LovveyTestService;

@Repository("lovveyTestDAO")
public class LovveyTestDAO extends EgovAbstractMapper implements LovveyTestService{
	private static final Logger LOGGER = LoggerFactory.getLogger(LovveyTestService.class);
	@Override
	public String insertTest(Test vo) throws Exception {
		// TODO Auto-generated method stub
		
		System.out.println("LovveyTestDAO insertTest");
		insert("insertTestLovvey", vo);
		return null;
	}

	@Override
	public String updateTest(Test vo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteTest(Test vo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> SelectAllTest(Test vo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
