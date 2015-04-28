package com.as.controller;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;

import com.as.services.SidebarPage;
import com.as.services.SidebarPageConfig;
import com.as.services.impl.SidebarPageConfigImpl;

public class SidebarController extends SelectorComposer<Component> {

	@Wire
	Grid fnList;

	SidebarPageConfig pageConfig = new SidebarPageConfigImpl();

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		Rows rows = fnList.getRows();

		for (SidebarPage page : pageConfig.getPages()) {

			Row row = constructSidebarRow(page.getLabel(), page.getIconUri(),
					page.getUri());
			rows.appendChild(row);
		}

	}

	private Row constructSidebarRow(String label, String imageSrc,
			final String locationUri) {

		// construct component and hierarchy
		Row row = new Row();
		Image image = new Image(imageSrc);
		Label lab = new Label(label);

		row.appendChild(image);
		row.appendChild(lab);

		row.setClass("sidebar-fn");
		// create and register event listener
		EventListener<Event> actionListener = new SerializableEventListener<Event>() {
			private static final long serialVersionUID = 1L;

			public void onEvent(Event event) throws Exception {
				// redirect current url to new location
				Executions.getCurrent().sendRedirect(locationUri);
			}
		};

		row.addEventListener(Events.ON_CLICK, actionListener);

		return row;
	}

}
