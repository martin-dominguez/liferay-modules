<%@page import="com.liferay.portal.kernel.util.Validator"%>
<%@page import="com.liferay.semodules.organizationChart.configuration.OrgChartConfiguration"%>
<%@page import="com.liferay.portal.kernel.util.Constants" %>
<%@page import="com.liferay.petra.string.StringPool" %>
<%@page import="com.liferay.portal.kernel.model.PortletPreferences"%>

<%@ include file="/init.jsp"%>
 
<liferay-portlet:actionURL portletConfiguration="<%=true%>" var="configurationActionURL" />
<liferay-portlet:renderURL portletConfiguration="<%=true%>" var="configurationRenderURL" />


<div class="p-3">
	<aui:form action="<%=configurationActionURL%>" method="post" name="fm">
	    <aui:input name="<%=Constants.CMD%>" type="hidden" value="<%=Constants.UPDATE%>" />
	    <aui:input name="redirect" type="hidden" value="<%=configurationRenderURL%>" />
	 
	 	<div class="sheet sheet-lg">
			<div class="sheet-header">
				<div id="Title">
					<h3 class="sheet-subtitle">General Settings</h3>
				</div>
			</div>
			<div class="sheet-section">
				<div class="form-group">
				<!-- TODO: Insert HERE visual configs
				    <aui:fieldset>
				        <aui:input name="managerAttribute" label="Manager Attribute" value=""/>
				    </aui:fieldset>
				-->
				</div>
			</div>
	 
	    <aui:button-row>
	        <aui:button type="submit"></aui:button>
	    </aui:button-row>
	</aui:form>
</div>