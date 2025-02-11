package de.seitenbau.govdata.user.menu.product.navigation.personal.menu;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.product.navigation.personal.menu.PersonalMenuEntry;

/**
 * Service Component CreateMetadataPersonalMenuEntry
 * @author rnoerenberg
 */
@Component(
    immediate = true, property = {
        "product.navigation.personal.menu.entry.order:Integer=100",
        "product.navigation.personal.menu.group:Integer=350"
    },
    service = PersonalMenuEntry.class
)
public class CreateMetadataPersonalMenuEntry extends BasePersonalMenuEntry
{
  @Reference
  private LayoutLocalService layoutLocalService;

  @Reference
  private GroupLocalService groupLocalService;

  @Override
  public String getLabel(Locale locale)
  {
    return LanguageUtil.get(locale, "od.page.edit");
  }

  @Override
  public String getPortletURL(HttpServletRequest request)
  {
    return FRIENDLY_URL_PAGE_CREATE_METADATA;
  }

  @Override
  public boolean isShow(PortletRequest portletRequest, PermissionChecker permissionChecker)
      throws PortalException
  {
    return super.canEditDatasets(portletRequest, permissionChecker, layoutLocalService, groupLocalService);
  }

}