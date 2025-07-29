/*
 * © 2025 iamfortress.net
 */
package com.abac;

import jakarta.servlet.http.HttpServletRequest;

/**
 * ...
 *
 * @author Shawn McKinney
 * @version $Rev$
 */
public class GlobalIds
{
    public static final String ADD = "add";
    public static final String UPDATE = "update";
    public static final String DELETE = "delete";
    public static final String SEARCH = "search";
    public static final String READ = "read";

    public static final String SUPER_USER = "asuperuser";
    public static final String POWER_USER = "apoweruser";

    public static final String USER_1 = "auser1";
    public static final String USER_1_123 = "auser1_123";
    public static final String USER_1_456 = "auser1_456";
    public static final String USER_1_789 = "auser1_789";

    public static final String USER_2 = "auser2";
    public static final String USER_2_123 = "auser2_123";
    public static final String USER_2_456 = "auser2_456";
    public static final String USER_2_789 = "auser2_789";

    public static final String USER_3 = "auser3";
    public static final String USER_3_123 = "auser3_123";
    public static final String USER_3_456 = "auser3_456";
    public static final String USER_3_789 = "auser3_789";

    public static final String USER_123 = "auser123";
    public static final String USER_456 = "auser456";
    public static final String USER_789 = "auser789";

    public static final String USER_ID = "userId";
    public static final String PSWD_FIELD = "pswdField";
    public static final String LOGIN = "login";
    public static final String PAGE_1 = "PAGE1";
    public static final String BTN_PAGE_1 = "page1";

    public static final String PAGE1_OBJNAME = "com.abac.Page1";
    public static final String PAGE2_OBJNAME = "com.abac.Page2";
    public static final String PAGE3_OBJNAME = "com.abac.Page3";

    public static final String PAGE_2 = "PAGE2";
    public static final String BTN_PAGE_2 = "page2";

    public static final String PAGE_3 = "PAGE3";
    public static final String BTN_PAGE_3 = "page3";

    public static final String BTN_PAGE_1_ADD = "page1.add";
    public static final String BTN_PAGE_1_UPDATE = "page1.update";
    public static final String BTN_PAGE_1_DELETE = "page1.delete";
    public static final String BTN_PAGE_1_SEARCH = "page1.search";

    public static final String BTN_PAGE_2_ADD = "page2.add";
    public static final String BTN_PAGE_2_UPDATE = "page2.update";
    public static final String BTN_PAGE_2_DELETE = "page2.delete";
    public static final String BTN_PAGE_2_SEARCH = "page2.search";

    public static final String BTN_PAGE_3_ADD = "page3.add";
    public static final String BTN_PAGE_3_UPDATE = "page3.update";
    public static final String BTN_PAGE_3_DELETE = "page3.delete";
    public static final String BTN_PAGE_3_SEARCH = "page3.search";

    public static final String ROLE_PAGE1_123 = "PAGE1_123";
    public static final String ROLE_PAGE1_456 = "PAGE1_456";
    public static final String ROLE_PAGE1_789 = "PAGE1_789";
    public static final String ROLE_PAGE2_123 = "PAGE2_123";
    public static final String ROLE_PAGE2_456 = "PAGE2_456";
    public static final String ROLE_PAGE2_789 = "PAGE2_789";
    public static final String ROLE_PAGE3_123 = "PAGE3_123";
    public static final String ROLE_PAGE3_456 = "PAGE3_456";
    public static final String ROLE_PAGE3_789 = "PAGE3_789";

    public static final String CUSTOMER_EF_ID = "custNum";

    public static final String ROLE_SUPER = "ROLE_ABAC_SUPER_USER";
    public static final String ROLE_PAGE1 = "ROLE_ABAC_DEMO_P1";
    public static final String ROLE_PAGE2 = "ROLE_ABAC_DEMO_P2";
    public static final String ROLE_PAGE3 = "ROLE_ABAC_DEMO_P3";
    public static final String LOGOUT = "logout";
    public static final String ROLES_ACTIVATE = "roles.activate";
    public static final String FOOTER = "© 2025 iamfortress.net";
    public static final String INFO_FIELD = "infoField";
    public static final String AUTHZ_ERROR_MSG = "Authorization Failed";

    public static String getLocationReplacement(HttpServletRequest servletRequest)
    {
        return "window.location.replace(\"" + servletRequest.getContextPath() + "\");";
    }
}
