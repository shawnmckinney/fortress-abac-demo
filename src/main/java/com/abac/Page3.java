/*
 * This is free and unencumbered software released into the public domain.
 */
package com.abac;


import com.abac.dao.Page3EO;
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
public class Page3 extends MyBasePage
{
    private static final Logger LOG = Logger.getLogger( Page3.class.getName() );
    private Form editForm;

    private String activatedCustomer;

    public Page3()
    {
        this.activatedCustomer = "";
        init();
    }

    public Page3(String activatedCustomer)
    {
        this.activatedCustomer = activatedCustomer;
        init();
    }

    private void init()
    {
        this.editForm = new Page3Form( "pageForm", new CompoundPropertyModel<>( new Page3EO() ) );
        this.editForm.setOutputMarkupId( true );
        add( this.editForm );
        setChildPage( ChildPage.PAGE3 );
    }

    public class Page3Form extends Form
    {
        private TextField customer;

        public Page3Form( String id, final IModel<Page3EO> model )
        {
            super( id, model );
            addDetailFields();
            add( new Label( "label1", "If you see this page, ROLE_ABAC_SUPER_USER or ROLE_ABAC_DEMO_P3 is activated within your session" ) );
            addButtons();
        }

        /**
         * Add the Page Buttons for CRUD
         */
        private void addButtons()
        {
            add( new SecureIndicatingAjaxButton( this, GlobalIds.BTN_PAGE_3_ADD, GlobalIds.PAGE3_OBJNAME, GlobalIds.ADD )
            {
                @Override
                protected void onSubmit( AjaxRequestTarget target )
                {
                    Page3EO page3EO = ( Page3EO ) editForm.getModel().getObject();
                    info( GlobalIds.BTN_PAGE_3_ADD );
                    if( page3EO != null && checkAccess( ) )
                    {
                        logIt( target, "Page3 Add for Customer " + activatedCustomer );
                    }
                    else
                    {
                        setAuthZError( "Authorization Failed", GlobalIds.PAGE3_OBJNAME, GlobalIds.ADD, activatedCustomer );
                    }
                }

                @Override
                public void onError( AjaxRequestTarget target )
                {
                    LOG.error( "submit failed: " + GlobalIds.BTN_PAGE_3_ADD );
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
                            LOG.info( "Page3.add Failure Handler, relocation string = " + szRelocation );
                            return szRelocation;
                        }
                    };
                    attributes.getAjaxCallListeners().add( ajaxCallListener );
                }
            } );
            add( new SecureIndicatingAjaxButton( this, GlobalIds.BTN_PAGE_3_UPDATE, GlobalIds.PAGE3_OBJNAME, GlobalIds.UPDATE )
            {
                @Override
                protected void onSubmit( AjaxRequestTarget target )
                {
                    Page3EO page3EO = ( Page3EO ) editForm.getModel().getObject();
                    info( GlobalIds.BTN_PAGE_3_UPDATE );
                    if( page3EO != null && checkAccess( ) )
                    {
                        logIt( target, "Page3 Update for Customer " + activatedCustomer );
                    }
                    else
                    {
                        setAuthZError( "Authorization Failed", GlobalIds.PAGE3_OBJNAME, GlobalIds.UPDATE, activatedCustomer );
                    }
                }

                @Override
                public void onError( AjaxRequestTarget target )
                {
                    LOG.error( "submit failed: " + GlobalIds.BTN_PAGE_3_UPDATE );
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
                            LOG.info( "Page3.update Failure Handler, relocation string = " + szRelocation );
                            return szRelocation;
                        }
                    };
                    attributes.getAjaxCallListeners().add( ajaxCallListener );
                }
            } );
            add( new SecureIndicatingAjaxButton( this, GlobalIds.BTN_PAGE_3_DELETE, GlobalIds.PAGE3_OBJNAME, GlobalIds.DELETE )
            {
                @Override
                protected void onSubmit( AjaxRequestTarget target )
                {
                    Page3EO page3EO = ( Page3EO ) editForm.getModel().getObject();
                    info( GlobalIds.BTN_PAGE_3_DELETE );
                    if( page3EO != null && checkAccess( ) )
                    {
                        logIt( target, "Page3 Delete for Customer " + activatedCustomer );
                    }
                    else
                    {
                        setAuthZError( "Authorization Failed", GlobalIds.PAGE3_OBJNAME, GlobalIds.DELETE, activatedCustomer );
                    }
                }

                @Override
                public void onError( AjaxRequestTarget target )
                {
                    LOG.error( "submit failed: " + GlobalIds.BTN_PAGE_3_DELETE );
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
                            LOG.info( "Page3.delete Failure Handler, relocation string = " + szRelocation );
                            return szRelocation;
                        }
                    };
                    attributes.getAjaxCallListeners().add( ajaxCallListener );
                }
            } );
            add( new SecureIndicatingAjaxButton( this, GlobalIds.BTN_PAGE_3_SEARCH, GlobalIds.PAGE3_OBJNAME, GlobalIds.SEARCH )
            {
                @Override
                protected void onSubmit( AjaxRequestTarget target )
                {
                    Page3EO page3EO = ( Page3EO ) editForm.getModel().getObject();
                    if( page3EO != null && checkAccess( ) )
                    {
                        logIt( target, "Page3 Search for Customer " + activatedCustomer );
                    }
                    else
                    {
                        setAuthZError( "Authorization Failed", GlobalIds.PAGE3_OBJNAME, GlobalIds.SEARCH, activatedCustomer );
                    }
                }

                @Override
                public void onError( AjaxRequestTarget target )
                {
                    LOG.error( "submit failed: " + GlobalIds.BTN_PAGE_3_SEARCH );
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
                            LOG.info( "Page3.search Failure Handler, relocation string = " + szRelocation );
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

            TextField attr_g = new TextField( "attr_g" );
            add( attr_g );
            attr_g.setRequired( false );

            TextField attr_h = new TextField( "attr_h" );
            add( attr_h );
            attr_h.setRequired( false );

            TextField attr_i = new TextField( "attr_i" );
            add( attr_i );
            attr_i.setRequired( false );
        }
    }
}
