/*
 * © 2025 iamfortress.net
 */
package com.abac;


import com.abac.dao.Page2EO;
import org.apache.directory.fortress.web.control.SecureIndicatingAjaxButton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import jakarta.servlet.http.HttpServletRequest;


/**
 * Example Page class for fortress-abac-demo Wicket sample project.  It contains security control functions to demonstrate ANSI RBAC security concepts.
 *
 * @author Shawn McKinney
 * @version $Rev$
 */
public class Page2 extends MyBasePage
{
    private static final Logger LOG = LoggerFactory.getLogger( Page2.class.getName() );
    private Form editForm;

    private String activatedCustomer;

    public Page2()
    {
        this.activatedCustomer = "";
        init();
    }

    public Page2(String activatedCustomer)
    {
        this.activatedCustomer = activatedCustomer;
        init();
    }

    private void init()
    {
        this.editForm = new Page2Form( "pageForm", new CompoundPropertyModel<>( new Page2EO() ) );
        this.editForm.setOutputMarkupId( true );
        add( this.editForm );
        setChildPage( ChildPage.PAGE2 );
    }

    public class Page2Form extends Form
    {
        private TextField customer;

        public Page2Form( String id, final IModel<Page2EO> model )
        {
            super( id, model );
            addDetailFields();
            add( new Label( "label1", "If you see this page, ROLE_ABAC_SUPER_USER or ROLE_ABAC_DEMO_P2 is activated within your session" ) );
            addButtons();
        }

        /**
         * Add the Page Buttons for CRUD
         */
        private void addButtons()
        {
            //final String szBtn1 = GlobalUtils.BTN_PAGE_2 + "." + GlobalUtils.ADD;
            add( new SecureIndicatingAjaxButton( this, GlobalIds.BTN_PAGE_2_ADD, GlobalIds.PAGE2_OBJNAME, GlobalIds.ADD )
            {
                @Override
                protected void onSubmit( AjaxRequestTarget target )
                {
                    Page2EO page2EO = ( Page2EO ) editForm.getModel().getObject();
                    info( GlobalIds.BTN_PAGE_2_ADD );
                    if( page2EO != null && checkAccess( ) )
                    {
                        logIt( target, "Page2 Add for Customer " + activatedCustomer );
                    }
                    else
                    {
                        setAuthZError( "Authorization Failed", GlobalIds.PAGE2_OBJNAME, GlobalIds.ADD, activatedCustomer );
                    }
                }

                @Override
                public void onError( AjaxRequestTarget target )
                {
                    LOG.error( "submit failed: " + GlobalIds.BTN_PAGE_2_ADD );
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
                            LOG.info( "Page2.add Failure Handler, relocation string = " + szRelocation );
                            return szRelocation;
                        }
                    };
                    attributes.getAjaxCallListeners().add( ajaxCallListener );
                }
            } );
            add( new SecureIndicatingAjaxButton( this, GlobalIds.BTN_PAGE_2_UPDATE, GlobalIds.PAGE2_OBJNAME, GlobalIds.UPDATE )
            {
                @Override
                protected void onSubmit( AjaxRequestTarget target )
                {
                    Page2EO page2EO = ( Page2EO ) editForm.getModel().getObject();
                    info( GlobalIds.BTN_PAGE_2_UPDATE );
                    if( page2EO != null && checkAccess( ) )
                    {
                        logIt( target, "Page2 Update for Customer " + activatedCustomer );
                    }
                    else
                    {
                        setAuthZError( "Authorization Failed", GlobalIds.PAGE2_OBJNAME, GlobalIds.UPDATE, activatedCustomer );
                    }
                }

                @Override
                public void onError( AjaxRequestTarget target )
                {
                    LOG.error( "submit failed: " + GlobalIds.BTN_PAGE_2_UPDATE );
                }

                @Override
                protected void updateAjaxAttributes( AjaxRequestAttributes attributes )
                {
                    AjaxCallListener ajaxCallListener = new AjaxCallListener()
                    {
                        @Override
                        public CharSequence getFailureHandler( Component component )
                        {
                            String szRelocation = GlobalIds.getLocationReplacement( ( HttpServletRequest ) getRequest().getContainerRequest() );
                            LOG.info( "Page2.update Failure Handler, relocation string = " + szRelocation );
                            return szRelocation;
                        }
                    };
                    attributes.getAjaxCallListeners().add( ajaxCallListener );
                }
            } );
            add( new SecureIndicatingAjaxButton( this, GlobalIds.BTN_PAGE_2_DELETE, GlobalIds.PAGE2_OBJNAME, GlobalIds.DELETE )
            {
                @Override
                protected void onSubmit( AjaxRequestTarget target )
                {
                    Page2EO page2EO = ( Page2EO ) editForm.getModel().getObject();
                    info( GlobalIds.BTN_PAGE_2_DELETE );
                    if( page2EO != null && checkAccess( ) )
                    {
                        logIt( target, "Page2 Delete for Customer " + activatedCustomer );
                    }
                    else
                    {
                        setAuthZError( "Authorization Failed", GlobalIds.PAGE2_OBJNAME, GlobalIds.DELETE, activatedCustomer );
                    }
                }

                @Override
                public void onError( AjaxRequestTarget target )
                {
                    LOG.error( "submit failed: " + GlobalIds.BTN_PAGE_2_DELETE );
                }

                @Override
                protected void updateAjaxAttributes( AjaxRequestAttributes attributes )
                {
                    AjaxCallListener ajaxCallListener = new AjaxCallListener()
                    {
                        @Override
                        public CharSequence getFailureHandler( Component component )
                        {
                            String szRelocation = GlobalIds.getLocationReplacement( ( HttpServletRequest ) getRequest().getContainerRequest() );
                            LOG.info( "Page2.delete Failure Handler, relocation string = " + szRelocation );
                            return szRelocation;
                        }
                    };
                    attributes.getAjaxCallListeners().add( ajaxCallListener );
                }
            } );
            add( new SecureIndicatingAjaxButton( this, GlobalIds.BTN_PAGE_2_SEARCH, GlobalIds.PAGE2_OBJNAME, GlobalIds.SEARCH )
            {
                @Override
                protected void onSubmit( AjaxRequestTarget target )
                {
                    Page2EO page2EO = ( Page2EO ) editForm.getModel().getObject();
                    if( page2EO != null && checkAccess( ) )
                    {
                        logIt( target, "Page2 Search for Customer " + activatedCustomer );
                    }
                    else
                    {
                        setAuthZError( "Authorization Failed", GlobalIds.PAGE2_OBJNAME, GlobalIds.SEARCH, activatedCustomer );
                    }
                }

                @Override
                public void onError( AjaxRequestTarget target )
                {
                    LOG.error( "submit failed: " + GlobalIds.BTN_PAGE_2_SEARCH );
                }

                @Override
                protected void updateAjaxAttributes( AjaxRequestAttributes attributes )
                {
                    AjaxCallListener ajaxCallListener = new AjaxCallListener()
                    {
                        @Override
                        public CharSequence getFailureHandler( Component component )
                        {
                            String szRelocation = GlobalIds.getLocationReplacement( ( HttpServletRequest ) getRequest().getContainerRequest() );
                            LOG.info( "Page2.search Failure Handler, relocation string = " + szRelocation );
                            return szRelocation;
                        }
                    };
                    attributes.getAjaxCallListeners().add( ajaxCallListener );
                }
            } );
        }

        private void addDetailFields()
        {
            customer = new TextField( "customer", Model.of( activatedCustomer ) );
            add( customer );
            customer.setEnabled( false );
            TextField attr_d = new TextField( "attr_d" );
            add( attr_d );
            attr_d.setRequired( false );

            TextField attr_e = new TextField( "attr_e" );
            add( attr_e );
            attr_e.setRequired( false );

            TextField attr_f = new TextField( "attr_f" );
            add( attr_f );
            attr_f.setRequired( false );
        }
    }
}
