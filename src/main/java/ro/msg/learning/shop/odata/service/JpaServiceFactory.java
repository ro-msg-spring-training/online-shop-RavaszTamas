package ro.msg.learning.shop.odata.service;

import org.apache.olingo.odata2.jpa.processor.api.ODataJPAContext;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAServiceFactory;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;
import org.springframework.context.annotation.Scope;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class JpaServiceFactory extends ODataJPAServiceFactory {

  private final LocalContainerEntityManagerFactoryBean factoryBean;

  public JpaServiceFactory(LocalContainerEntityManagerFactoryBean factoryBean) {
    this.factoryBean = factoryBean;
  }

  @Override
  public ODataJPAContext initializeODataJPAContext() throws ODataJPARuntimeException {
    ODataJPAContext context = getODataJPAContext();
    context.setEntityManagerFactory(factoryBean.getObject());
    context.setJPAEdmMappingModel("odata2mapping.xml");
    context.setPersistenceUnitName("local");
    return context;
  }
}
