/**
 * Copyright (c) 2000-2015 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
package com.liferay.faces.showcase.component.tab.internal;

import java.io.IOException;

import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import com.liferay.faces.showcase.component.tab.Tab;
import com.liferay.faces.showcase.component.tabview.TabView;
import com.liferay.faces.util.component.Styleable;
import com.liferay.faces.util.render.RendererUtil;


/**
 * @author  Neil Griffin
 */
//J-
@FacesRenderer(componentFamily = Tab.COMPONENT_FAMILY, rendererType = Tab.RENDERER_TYPE)
@ResourceDependencies(
	{
		@ResourceDependency(library = "bootstrap", name = "css/bootstrap.min.css"),
		@ResourceDependency(library = "bootstrap", name = "css/bootstrap-responsive.min.css"),
	}
)
//J+
public class TabRenderer extends TabRendererBase {

	@Override
	public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// Encode the starting <div> element that represents the component.
		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.startElement("div", uiComponent);
		responseWriter.writeAttribute("id", uiComponent.getClientId(facesContext), "id");

		boolean selected = false;

		UIComponent uiComponentParent = uiComponent.getParent();

		if (uiComponentParent instanceof TabView) {
			TabView tabView = (TabView) uiComponentParent;
			Integer selectedIndex = tabView.getSelectedIndex();
			int rowIndex = tabView.getRowIndex();
			selected = (((selectedIndex == null) && (rowIndex == 0)) ||
					((selectedIndex != null) && (rowIndex == selectedIndex)));
		}

		if (selected) {
			RendererUtil.encodeStyleable(responseWriter, (Styleable) uiComponent, "tab-pane active");
		}
		else {
			RendererUtil.encodeStyleable(responseWriter, (Styleable) uiComponent, "tab-pane");
		}
	}

	@Override
	public void encodeChildren(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		UIComponent implicitPanelGroup = uiComponent.getFacet(TabHandler.IMPLICIT_FACET_NAME);

		// For more information, see TabHandler.
		if (implicitPanelGroup != null) {
			implicitPanelGroup.encodeAll(facesContext);
		}

		super.encodeChildren(facesContext, uiComponent);
	}

	@Override
	public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// Encode the closing </div> element.
		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.endElement("div");
	}

	@Override
	public boolean getRendersChildren() {
		return true;
	}
}