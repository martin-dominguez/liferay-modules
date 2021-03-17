package com.liferay.dynamic.data.mapping.form.field.type.internal.userdata.field.form.field;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTemplateContextContributor;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Phone;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author mdominguez
 */

@Component(
		immediate = true,
		property = "ddm.form.field.type.name=userdataField",
		service = {
			DDMFormFieldTemplateContextContributor.class,
			UserDataFieldDDMFormFieldTemplateContextContributor.class
		}
)

public class UserDataFieldDDMFormFieldTemplateContextContributor implements DDMFormFieldTemplateContextContributor {

	@Override
	public Map<String, Object> getParameters(DDMFormField ddmFormField, 
			DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {
		Map<String, Object> parameters = new HashMap<>();
		
		String predefinedValue = null;
		
		if (ddmFormFieldRenderingContext.isReturnFullContext()) {
			try {
				predefinedValue = getPredefinedValue(
						ddmFormField, ddmFormFieldRenderingContext);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		
		parameters.put("predefinedValue", predefinedValue);
		return parameters;
	}
	
	protected String getPredefinedValue(
			DDMFormField ddmFormField,
			DDMFormFieldRenderingContext ddmFormFieldRenderingContext) 
					throws Exception {
		
		HttpServletRequest httpServletRequest = ddmFormFieldRenderingContext.getHttpServletRequest();
		ThemeDisplay themeDisplay = (ThemeDisplay) httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		String predefinedValue = null;
		
		if (themeDisplay.isSignedIn()) {

			if (_log.isDebugEnabled()) {
				_log.debug((String)ddmFormField.getProperty("userDataField"));
			}
			
			String methodName = ((String)ddmFormField.getProperty("userDataField"))
				.replace("[","").replace("]", "").replace("\"", "");
			User user = themeDisplay.getUser();

			List<Address> addresses = user.getAddresses();
			Address address = null;

			if (!addresses.isEmpty()) {
				address = addresses.get(0);
			}

			if (methodName.equals("getDateOfBirth")) {
				Date birthday = null;
				try {
					birthday = user.getBirthday();
					if (birthday != null) {
						DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
						predefinedValue = dateFormat.format(birthday);
					}
				}
				catch (PortalException portalException) {
					_log.error(portalException, portalException);
				}
			}
			else if (methodName.equals("getJobTitle")) {
				predefinedValue = user.getJobTitle();
			}
			else if (methodName.equals("getPolicyNumber")) {
				predefinedValue = getCustomField("Policy Number", predefinedValue, user);
			}
			else if (methodName.equals("getTaxId")) {
				predefinedValue = getCustomField("Tax ID", predefinedValue, user);
			}
			else if (methodName.equals("getPhones")) {
				predefinedValue = user.getPhones().size() > 0 ? user.getPhones().get(0).getNumber():"";
			} else if (methodName.equals("getAddresses")) {
					predefinedValue = (address != null)
						? address.getStreet1() +
						  ", " + address.getCity() +
						  ", " + address.getZip() +
						  ", " +
						  address.getCountry().getName()
						: "";
			} else if (methodName.equals("getStreet1")) {
				if (address != null) {
					predefinedValue = address.getStreet1();
				}
			} else if (methodName.equals("getStreet2")) {
				if (address != null) {
					predefinedValue = address.getStreet2();
				}
			} else if (methodName.equals("getStreet3")) {
				if (address != null) {
					predefinedValue = address.getStreet3();
				}
			} else if (methodName.equals("getCity")) {
				if (address != null) {
					predefinedValue = address.getCity();
				}
			} else if (methodName.equals("getCountryId")) {
				if (address != null) {
					predefinedValue = Long.toString(address.getCountryId());
				}
			} else if (methodName.equals("getCountryName")) {
				if (address != null) {
					predefinedValue = address.getCountry().getName();
				}
			} else if (methodName.equals("getRegionCode")) {
				if (address != null) {
					predefinedValue = address.getRegion().getRegionCode();
				}
			} else if (methodName.equals("getRegionId")) {
				if (address != null) {
					predefinedValue = Long.toString(address.getRegionId());
				}
			} else if (methodName.equals("getPostalCode")) {
				if (address != null) {
					predefinedValue = address.getZip();
				}
			} else {
				Method m = User.class.getMethod(methodName);
				predefinedValue = (String) m.invoke(user);
			}
		}
		
		return predefinedValue;
	}

	private String getCustomField(String fieldName, String predefinedValue, User user) {
		ExpandoBridge expandoBridge = user.getExpandoBridge();
		Enumeration<String> attributeNames =
			expandoBridge.getAttributeNames();
		boolean found = false;
		while (attributeNames.hasMoreElements()) {
			String attributeName = attributeNames.nextElement();
			if (fieldName.equals(attributeName)) {
				Serializable fieldValue = expandoBridge.getAttribute(fieldName);
				if (fieldValue != null) {
					predefinedValue = fieldValue.toString();
				}
				found = true;
				break;
			}
		}
		if (!found) {
			_log.error("Custom field '" + fieldName + "' does not exist");
		}

		return predefinedValue;
	}

	private static final Log _log = LogFactoryUtil.getLog(UserDataFieldDDMFormFieldTemplateContextContributor.class);

}
