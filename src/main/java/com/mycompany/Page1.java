/*
 * This is free and unencumbered software released into the public domain.
 */
package com.mycompany;


import com.mycompany.dao.Page1EO;
import org.apache.directory.fortress.web.control.SecureIndicatingAjaxButton;
import org.apache.log4j.Logger;
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

import javax.servlet.http.HttpServletRequest;


/**
 * Example Page class for fortress-abac-demo Wicket sample project.  It contains security control functions to demonstrate ANSI RBAC security concepts.
 *
 * @author Shawn McKinney
 * @version $Rev$
 */
public class Page1 extends MyBasePage
{
    private static final Logger LOG = Logger.getLogger( Page1.class.getName() );
    private Form editForm;
    private String activatedCustomer;

    public Page1()
    {
        this.activatedCustomer = "";
        init();
    }

    public Page1(String activatedCustomer)
    {
        this.activatedCustomer = activatedCustomer;
        init();
    }

    private void init()
    {
        this.editForm = new Page1Form( "pageForm", new CompoundPropertyModel<>( new Page1EO() ) );
        this.editForm.setOutputMarkupId( true );
        add( this.editForm );
        setChildPage( ChildPage.PAGE1 );
    }

    public class Page1Form extends Form
    {
        private TextField customer;

        public Page1Form( String id, final IModel<Page1EO> model )
        {
            super( id, model );
            addDetailFields();
            add( new Label( "label1", "If you see this page, ROLE_ABAC_SUPER_USER or ROLE_ABAC_DEMO_P1 is activated within your session" ) );
            addButtons();
        }

        /**
         * Add the Page Buttons for CRUD
         */
        private void addButtons()
        {
            add( new SecureIndicatingAjaxButton( this, GlobalIds.BTN_PAGE_1_ADD, GlobalIds.PAGE1_OBJNAME, GlobalIds.ADD )
            {
                @Override
                protected void onSubmit( AjaxRequestTarget target, Form form )
                {
                    Page1EO page1EO = ( Page1EO ) editForm.getModel().getObject();
                    info( GlobalIds.BTN_PAGE_1_ADD );
                    if( page1EO != null && checkAccess() )
                    {
                        logIt( target, "Page1 Add for Customer " + page1EO.getCustomer() );
                    }
                    else
                    {
                        setAuthZError( "Authorization Failed", GlobalIds.PAGE1_OBJNAME, GlobalIds.ADD, page1EO.getCustomer() );
                    }
                }

                @Override
                public void onError( AjaxRequestTarget target, Form form )
                {
                    LOG.error( "submit failed: " + GlobalIds.BTN_PAGE_1_ADD );
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
                            LOG.info( "Page1.add Failure Handler, relocation string = " + szRelocation );
                            return szRelocation;
                        }
                    };
                    attributes.getAjaxCallListeners().add( ajaxCallListener );
                }
            } );
            add( new SecureIndicatingAjaxButton( this, GlobalIds.BTN_PAGE_1_UPDATE, GlobalIds.PAGE1_OBJNAME, GlobalIds.UPDATE )
            {
                @Override
                protected void onSubmit( AjaxRequestTarget target, Form form )
                {
                    Page1EO page1EO = ( Page1EO ) editForm.getModel().getObject();
                    info( GlobalIds.BTN_PAGE_1_UPDATE );
                    if( page1EO != null && checkAccess() )
                    {
                        logIt( target, "Page1 Add for Customer " + page1EO.getCustomer() );
                    }
                    else
                    {
                        setAuthZError( "Authorization Failed", GlobalIds.PAGE1_OBJNAME, GlobalIds.UPDATE, page1EO.getCustomer() );
                    }
                }

                @Override
                public void onError( AjaxRequestTarget target, Form form )
                {
                    LOG.error( "submit failed: " + GlobalIds.BTN_PAGE_1_UPDATE );
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
                            LOG.info( "Page1.update Failure Handler, relocation string = " + szRelocation );
                            return szRelocation;
                        }
                    };
                    attributes.getAjaxCallListeners().add( ajaxCallListener );
                }
            } );
            add( new SecureIndicatingAjaxButton( this, GlobalIds.BTN_PAGE_1_DELETE, GlobalIds.PAGE1_OBJNAME, GlobalIds.DELETE )
            {
                @Override
                protected void onSubmit( AjaxRequestTarget target, Form form )
                {
                    Page1EO page1EO = ( Page1EO ) editForm.getModel().getObject();
                    info( GlobalIds.BTN_PAGE_1_DELETE );
                    if( page1EO != null && checkAccess() )
                    {
                        logIt( target, "Page1 Add for Customer " + page1EO.getCustomer() );
                    }
                    else
                    {
                        setAuthZError( "Authorization Failed", GlobalIds.PAGE1_OBJNAME, GlobalIds.DELETE, page1EO.getCustomer() );
                    }
                }

                @Override
                public void onError( AjaxRequestTarget target, Form form )
                {
                    LOG.error( "submit failed: " + GlobalIds.BTN_PAGE_1_DELETE );
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
                            LOG.info( "Page1.delete Failure Handler, relocation string = " + szRelocation );
                            return szRelocation;
                        }
                    };
                    attributes.getAjaxCallListeners().add( ajaxCallListener );
                }
            } );
            add( new SecureIndicatingAjaxButton( this, GlobalIds.BTN_PAGE_1_SEARCH, GlobalIds.PAGE1_OBJNAME, GlobalIds.SEARCH )
            {
                @Override
                protected void onSubmit( AjaxRequestTarget target, Form form )
                {
                    Page1EO page1EO = ( Page1EO ) editForm.getModel().getObject();
                    if(page1EO != null && checkAccess())
                    {
                        logIt( target, "Page1 Search for Customer " + page1EO.getCustomer() );
                    }
                    else
                    {
                        setAuthZError( "Authorization Failed", GlobalIds.PAGE1_OBJNAME, GlobalIds.SEARCH, page1EO.getCustomer() );
                    }
                }

                @Override
                public void onError( AjaxRequestTarget target, Form form )
                {
                    LOG.error( "submit failed: " + GlobalIds.BTN_PAGE_1_SEARCH );
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
                            LOG.info( "Page1.search Failure Handler, relocation string = " + szRelocation );
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

            TextField attr_a = new TextField( "attr_a" );
            add( attr_a );
            attr_a.setRequired( false );

            TextField attr_b = new TextField( "attr_b" );
            add( attr_b );
            attr_b.setRequired( false );

            TextField attr_c = new TextField( "attr_c" );
            add( attr_c );
            attr_c.setRequired( false );
        }
    }
}
