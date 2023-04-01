/*
 * © 2023 iamfortress.net
 */
package com.abac;

import org.apache.commons.lang.StringUtils;
import org.apache.directory.fortress.core.*;
import org.apache.directory.fortress.core.SecurityException;
import org.apache.directory.fortress.core.model.RoleConstraint;
import org.apache.directory.fortress.core.model.User;
import org.apache.directory.fortress.realm.J2eePolicyMgr;
import org.apache.directory.fortress.web.control.SecUtils;
import org.apache.directory.fortress.web.control.SecureBookmarkablePageLink;
import org.apache.directory.fortress.web.control.SecureIndicatingAjaxButton;
import org.apache.directory.fortress.web.control.WicketSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.directory.fortress.core.model.Session;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * Base class for fortress-abac-demo Wicket sample project.  It contains security control functions to demonstrate ANSI
 * RBAC security concepts.
 *
 * @author Shawn McKinney
 * @version $Rev$
 */
public abstract class MyBasePage extends WebPage
{
    @SpringBean
    protected AccessMgr accessMgr;
    @SpringBean
    private J2eePolicyMgr j2eePolicyMgr;
    private static final Logger LOG = LoggerFactory.getLogger( MyBasePage.class.getName() );
    private Form myForm;
    private static final String LINKS_LABEL = "linksLabel";
    private String linksLabel = "Authorized Links";
    protected String customerNumber;


    /**
     * These are the child pages of this web application.
     */
    public enum ChildPage
    {
        PAGE1,
        PAGE2,
        PAGE3,
    }

    private ChildPage childPage;

    /**
     * All pages in this Wicket application extend this page.
     *
     */
    public MyBasePage()
    {
        addSecureLinks();
        final Link actionLink = new Link( GlobalIds.LOGOUT )
        {
            @Override
            public void onClick()
            {
                HttpServletRequest servletReq = ( HttpServletRequest ) getRequest().getContainerRequest();
                servletReq.getSession().invalidate();
                getSession().invalidate();
                setResponsePage( LoginPage.class );
            }
        };
        add( actionLink );
        add( new Label( "footer", GlobalIds.FOOTER ) );

        HttpServletRequest servletReq = ( HttpServletRequest ) getRequest().getContainerRequest();
        // RBAC Security Processing:
        Principal principal = servletReq.getUserPrincipal();

        // Is this a Java EE secured page && has the User successfully authenticated already?
        boolean isSecured = principal != null;
        if(isSecured)
        {
            linksLabel += " for " + principal.getName();
            if( !SecUtils.isLoggedIn( this ) )
            {
                try
                {
                    String szPrincipal = principal.toString();
                    // Pull the RBAC session from the realm and assert into the Web app's session along with user's perms:
                    SecUtils.initializeSession( this, j2eePolicyMgr, accessMgr, szPrincipal );
                }
                catch(SecurityException se)
                {
                    throw new RuntimeException( se );
                }
            }
        }
        myForm = new MyBasePageForm( "commonForm" );
        myForm.setOutputMarkupId( true );
        add( myForm );
        add( new Label( GlobalIds.INFO_FIELD ));
    }

    /**
     *
     */
    public class MyBasePageForm extends Form
    {
        private TextField constraintTextField;
        private String custA;


        public MyBasePageForm( String id )
        {
            super( id );
            addButton();
        }

        private void reinitializeSession(Component component, AccessMgr accessMgr, String value )
        {
            WicketSession wSes = ( WicketSession )getSession();
            Session realmSession = wSes.getSession();
            User user = new User( realmSession.getUserId() );

            RoleConstraint constraint = new RoleConstraint();
            constraint.setKey( "customer" );
            constraint.setValue( value );
            List<RoleConstraint> constraints = new ArrayList();
            constraints.add( constraint );
            Session session;
            try
            {
                session = accessMgr.createSession( user, constraints, true );
            }
            catch( SecurityException se )
            {
                throw new RuntimeException( se );
            }
            if(session != null)
            {
                synchronized ( ( WicketSession ) WicketSession.get() )
                {
                    LOG.info( "realmSession user: " + realmSession.getUserId() );
                    // Retrieve user permissions and attach RBAC session to Wicket session:
                    ( ( WicketSession ) WicketSession.get() ).setSession( session );
                    SecUtils.getPermissions( component, accessMgr );
                }
            }
        }


        private void addButton()
        {
            constraintTextField = new TextField( "custNum", Model.of( "" ));
            add( constraintTextField );
            constraintTextField.setRequired( true );
            add( new SecureIndicatingAjaxButton( this, GlobalIds.ROLES_ACTIVATE, "com.abac.MyBasePage", "activateConstraint" )
            {
                private static final long serialVersionUID = 1L;

                @Override
                protected void onSubmit( AjaxRequestTarget target )
                {
                    customerNumber = (String)constraintTextField.getDefaultModelObject();
                    logIt( target, "activate constraint: " + customerNumber);
                    reinitializeSession( this, accessMgr, customerNumber);
                    setMyResponsePage();
                    target.add( getForm() );

                }
                @Override
                protected void updateAjaxAttributes( AjaxRequestAttributes attributes )
                {
                    AjaxCallListener ajaxCallListener = new AjaxCallListener()
                    {
                        @Override
                        public CharSequence getFailureHandler( Component component )
                        {
                            String szRelocation = GlobalIds.getLocationReplacement( ( HttpServletRequest ) getRequest
                                ().getContainerRequest() );
                            LOG.info( "MyBasePage.addActiveRole Failure Handler, relocation string = " + szRelocation );
                            return szRelocation;
                        }
                    };
                    attributes.getAjaxCallListeners().add( ajaxCallListener );
                }
            } );
        }

        /**
         * Called during page form submission.
         */
        private void setMyResponsePage()
        {
            if ( childPage != null )
            {
                switch ( childPage )
                {
                    case PAGE1:
                        setResponsePage( new Page1(customerNumber) );
                        break;
                    case PAGE2:
                        setResponsePage( new Page2(customerNumber) );
                        break;
                    case PAGE3:
                        setResponsePage( new Page3(customerNumber) );
                        break;
                }
            }
            else
            {
                setResponsePage( new LaunchPage() );
            }
        }
    }


    /**
     * Called by the child of this class and used during page's submit operations to determine which page to reload.
     *
     * @param childPage
     */
    protected void setChildPage( ChildPage childPage )
    {
        this.childPage = childPage;
    }

    protected void setAuthZError( String error, String object, String operation, String id )
    {
        String msg = error;
        msg += " object name: " + object + ", operation name: " + operation;
        if ( StringUtils.isNotEmpty( id ) )
        {
            msg += " id: " + id;
        }
        PageParameters parameters = new PageParameters();
        parameters.add( "errorValue", msg );
        setResponsePage( AuthZErrorPage.class, parameters );
    }

    /**
     *
     */
    private void addSecureLinks()
    {
        add( new Label( LINKS_LABEL, new PropertyModel<String>( this, LINKS_LABEL ) ) );
        SecureBookmarkablePageLink page1Link = new SecureBookmarkablePageLink( GlobalIds.BTN_PAGE_1, Page1.class,
            GlobalIds.ROLE_SUPER + "," + GlobalIds.ROLE_PAGE1 );
        add( page1Link );
        SecureBookmarkablePageLink page2Link = new SecureBookmarkablePageLink( GlobalIds.BTN_PAGE_2, Page2.class,
            GlobalIds.ROLE_SUPER + "," + GlobalIds.ROLE_PAGE2 );
        add( page2Link );
        SecureBookmarkablePageLink page3Link = new SecureBookmarkablePageLink( GlobalIds.BTN_PAGE_3, Page3.class,
            GlobalIds.ROLE_SUPER + "," + GlobalIds.ROLE_PAGE3 );
        add( page3Link );
    }

    /**
     * Used by the child pages.
     *
     * @param target for modal panel
     * @param msg    to log and display user info
     */
    protected void logIt(AjaxRequestTarget target, String msg)
    {
        info( msg );
        LOG.info( msg );
        target.appendJavaScript( ";alert('" + msg + "');" );
    }
}
