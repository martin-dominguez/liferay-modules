package com.liferay.semodules.organizationChart.portlet;

import com.liferay.semodules.organizationChart.constants.OrganizationChartPortletKeys;

import com.liferay.social.kernel.exception.NoSuchRelationException;
import com.liferay.social.kernel.service.SocialRelationLocalService;
import com.liferay.contacts.constants.SocialRelationConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author mdominguez
 */

@Component(
    immediate = true,
    property = {
    	"javax.portlet.name=" + OrganizationChartPortletKeys.ORGANIZATIONCHART,
        "mvc.command.name=follow"
    },
    service = MVCActionCommand.class
)
public class FollowMVCActionCommand implements MVCActionCommand {

	@Override
	public boolean processAction(ActionRequest actionRequest, ActionResponse actionResponse) 
			throws PortletException {
		
		long userId = ParamUtil.getLong(actionRequest, "user");
		String action = ParamUtil.getString(actionRequest, "action");
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
				WebKeys.THEME_DISPLAY);
		
		if (userId != themeDisplay.getUserId()) {
			if (action.equals("follow")) {
				boolean blocked = socialRelationLocalService.hasRelation(
					userId, themeDisplay.getUserId(),
					SocialRelationConstants.TYPE_UNI_ENEMY);
				
				try {
					socialRelationLocalService.addRelation(
						themeDisplay.getUserId(), userId, SocialRelationConstants.TYPE_UNI_FOLLOWER);
					
					if (blocked) {
						socialRelationLocalService.addRelation(
							userId, themeDisplay.getUserId(), SocialRelationConstants.TYPE_UNI_FOLLOWER);
					}
				} catch (PortalException portalException) {
					if (_log.isDebugEnabled()) {
						_log.debug(
								portalException, portalException);
					}
				}
			} else {
				try {
					socialRelationLocalService.deleteRelation(
						themeDisplay.getUserId(), userId, SocialRelationConstants.TYPE_UNI_FOLLOWER);
				}
				catch (NoSuchRelationException noSuchRelationException) {

					if (_log.isDebugEnabled()) {
						_log.debug(
							noSuchRelationException, noSuchRelationException);
					}
				} catch (PortalException portalException) {
					if (_log.isDebugEnabled()) {
						_log.debug(
								portalException, portalException);
					}
				}
			}
		}
		
		return true;
	}
	
	@Reference
	protected SocialRelationLocalService socialRelationLocalService;
	
	private static final Log _log = LogFactoryUtil.getLog(
			OrganizationChartPortlet.class);
}
