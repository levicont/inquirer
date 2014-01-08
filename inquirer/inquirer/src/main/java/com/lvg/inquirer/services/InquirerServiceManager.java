package com.lvg.inquirer.services;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

import com.lvg.inquirer.mocks.InquirerDataBaseService;

public final class InquirerServiceManager {
	
	private static final Logger LOGGER = Logger.getLogger(InquirerServiceManager.class);
	private static final String INQUIRER_SERVICE_MANAGER = "INQUIRER_SERVICE_MANAGER";
	private static final InquirerServiceManager INSTANCE = new InquirerServiceManager();
	private static ServletContext context = null;
	
	private InquirerServiceManager(){
		init();
	}
	
	public static InquirerServiceManager getInstance(ServletContext context){
		INSTANCE.context = context;
		InquirerServiceManager instance = (InquirerServiceManager)context.getAttribute(INQUIRER_SERVICE_MANAGER);
		if(instance==null){
			context.setAttribute(INQUIRER_SERVICE_MANAGER, INSTANCE);
			instance = INSTANCE;
			
		}
		return instance;
	}
	
	private DataService dataService;
	public DataService getDataService(){
		return this.dataService;
	}
	public void  init(){
		dataService = new InquirerDataBaseService();
	}
	public static ServletContext getServletContext(){
		return INSTANCE.context;
	}
	
	public void startAllServices(){
		LOGGER.info("All Services have been started");
	}
	
	public void stopAllServices(){
		dataService.close();
		LOGGER.info("All services have been closed");
	}
}
