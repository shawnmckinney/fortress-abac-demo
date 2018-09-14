/*
 * This is free and unencumbered software released into the public domain.
 */
package com.mycompany;


import com.mycompany.dao.Page2EO;
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

import javax.servlet.http.HttpServletRequest;


/**
 * Example Page class for apache-fortress-demo Wicket sample project.  It contains security control functions to demonstrate ANSI RBAC security concepts.
 *
 * @author Shawn McKinney
 * @version $Rev$
 */
public class Page2 extends MyBasePage
{
    private static final Logger LOG = Logger.getLogger( Page2.class.getName() );
    private Form editForm;

    public Page2()
    {
        this.editForm = new Page2Form( "pageForm", new CompoundPropertyModel<Page2EO>( new Page2EO() ) );
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
                protected void onSubmit( AjaxRequestTarget target, Form form )
                {
                    Page2EO page2EO = ( Page2EO ) editForm.getModel().getObject();
                    info( GlobalIds.BTN_PAGE_2_ADD );
                    if( page2EO != null && checkAccess( page2EO.getCustomer() ) )
                    {
                        logIt( target, "Page2 Add for Customer " + page2EO.getCustomer() );
                    }
                    else
                    {
                        setAuthZError( "Authorization Failed", GlobalIds.PAGE2_OBJNAME, GlobalIds.ADD, page2EO.getCustomer() );
                    }
                }

                @Override
                public void onError( AjaxRequestTarget target, Form form )
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
                protected void onSubmit( AjaxRequestTarget target, Form form )
                {
                    Page2EO page2EO = ( Page2EO ) editForm.getModel().getObject();
                    info( GlobalIds.BTN_PAGE_2_UPDATE );
                    if( page2EO != null && checkAccess( page2EO.getCustomer() ) )
                    {
                        logIt( target, "Page2 Update for Customer " + page2EO.getCustomer() );
                    }
                    else
                    {
                        setAuthZError( "Authorization Failed", GlobalIds.PAGE2_OBJNAME, GlobalIds.UPDATE, page2EO.getCustomer() );
                    }
                }

                @Override
                public void onError( AjaxRequestTarget target, Form form )
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
                protected void onSubmit( AjaxRequestTarget target, Form form )
                {
                    Page2EO page2EO = ( Page2EO ) editForm.getModel().getObject();
                    info( GlobalIds.BTN_PAGE_2_DELETE );
                    if( page2EO != null && checkAccess( page2EO.getCustomer() ) )
                    {
                        logIt( target, "Page2 Delete for Customer " + page2EO.getCustomer() );
                    }
                    else
                    {
                        setAuthZError( "Authorization Failed", GlobalIds.PAGE2_OBJNAME, GlobalIds.DELETE, page2EO.getCustomer() );
                    }
                }

                @Override
                public void onError( AjaxRequestTarget target, Form form )
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
                protected void onSubmit( AjaxRequestTarget target, Form form )
                {
                    Page2EO page2EO = ( Page2EO ) editForm.getModel().getObject();
                    if( page2EO != null && checkAccess( page2EO.getCustomer() ) )
                    {
                        logIt( target, "Page2 Search for Customer " + page2EO.getCustomer() );
                    }
                    else
                    {
                        setAuthZError( "Authorization Failed", GlobalIds.PAGE2_OBJNAME, GlobalIds.SEARCH, page2EO.getCustomer() );
                    }
                }

                @Override
                public void onError( AjaxRequestTarget target, Form form )
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
            customer = new TextField( "customer" );
            add( customer );
            customer.setRequired( true );

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
