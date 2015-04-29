/* 
	Description:
		ZK Essentials
	History:
		Created by dennis

Copyright (C) 2012 Potix Corporation. All Rights Reserved.
*/
package com.as.viewmodel;

import java.util.List;

import com.as.services.SidebarPage;
import com.as.services.SidebarPageConfig;
import com.as.services.impl.SidebarPageConfigAjaxBasedImpl;

public class SidebarAjaxbasedViewModel {

	//todo: wire service
	private SidebarPageConfig pageConfig = new SidebarPageConfigAjaxBasedImpl();
	
	public List<SidebarPage> getSidebarPages() {
		return pageConfig.getPages();
	}
}
