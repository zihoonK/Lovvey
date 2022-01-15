package lovvey.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import lovvey.domain.Test;
import lovvey.service.LovveyTestService;

@Service("lovveyTestService")
public class LovveyTestServiceImpl extends EgovAbstractServiceImpl implements LovveyTestService {
	private static final Logger LOGGER = LoggerFactory.getLogger(LovveyTestServiceImpl.class);
	
	@Resource(name="lovveyTestDAO")
	private LovveyTestDAO lovveyTestDAO;
	
	@Override
	public String insertTest(Test vo) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("lovveyTestService insertTest");
		lovveyTestDAO.insertTest(vo);
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
