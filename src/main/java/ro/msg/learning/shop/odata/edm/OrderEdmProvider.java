package ro.msg.learning.shop.odata.edm;

import org.apache.olingo.odata2.api.edm.*;
import org.apache.olingo.odata2.api.edm.provider.*;
import org.apache.olingo.odata2.api.exception.ODataException;

import java.util.ArrayList;
import java.util.List;

public class OrderEdmProvider extends EdmProvider {

  public static final String ENTITY_SET_NAME_ORDERS = "Orders";
  public static final String ENTITY_SET_NAME_ORDER_DETAILS = "OrderDetails";
  public static final String ENTITY_SET_NAME_PRODUCTS = "Products";
  public static final String ENTITY_NAME_ORDER = "Order";
  public static final String ENTITY_NAME_ORDER_DETAIL = "OrderDetail";
  public static final String ENTITY_NAME_PRODUCT = "Product";

  private static final String NAMESPACE = "ro.msg.learning.shop.ODataOrders";

  private static final FullQualifiedName ENTITY_TYPE_1_1_ORDER =
      new FullQualifiedName(NAMESPACE, ENTITY_NAME_ORDER);
  private static final FullQualifiedName ENTITY_TYPE_1_2_ORDER_DETAIL =
      new FullQualifiedName(NAMESPACE, ENTITY_NAME_ORDER_DETAIL);
  private static final FullQualifiedName ENTITY_TYPE_1_3_PRODUCT =
      new FullQualifiedName(NAMESPACE, ENTITY_NAME_PRODUCT);

  public static final String ADDRESS = "Address";
  private static final FullQualifiedName ADDRESS_COMPLEX_TYPE =
      new FullQualifiedName(NAMESPACE, ADDRESS);

  private static final FullQualifiedName ASSOCIATION_ORDER_ORDER_DETAIL =
      new FullQualifiedName(NAMESPACE, "Order_OrderDetails_OrderDetail_Order");
  private static final FullQualifiedName ASSOCIATION_PRODUCT_ORDER_DETAIL =
      new FullQualifiedName(NAMESPACE, "Product_OrderDetails_OrderDetail_Product");

  private static final String ROLE_ORDER_ORDER_DETAILS = "Order_OrderDetails";
  private static final String ROLE_ORDER_DETAIL_ORDER = "OrderDetail_Order";

  private static final String ROLE_PRODUCT_ORDER_DETAILS = "Product_OrderDetails";
  private static final String ROLE_ORDER_DETAIL_PRODUCT = "OrderDetail_Product";

  private static final String ENTITY_CONTAINER_ORDERS = "ODataOrdersEntityContainer";

  private static final String ASSOCIATION_SET_ORDERS_ORDER_DETAILS = "Orders_OrderDetails";
  private static final String ASSOCIATION_SET_PRODUCTS_ORDER_DETAILS = "Products_OrderDetails";

  private static final String FUNCTION_IMPORT_CREATE_ORDER = "CreateOrder";
  public static final String ORDER_DETAILS = "OrderDetails";

  @Override
  public List<Schema> getSchemas() throws ODataException {
    List<Schema> schemas = new ArrayList<>();

    Schema schema = new Schema();
    schema.setNamespace(NAMESPACE);

    List<EntityType> entityTypes = new ArrayList<>();
    entityTypes.add(getEntityType(ENTITY_TYPE_1_1_ORDER));
    entityTypes.add(getEntityType(ENTITY_TYPE_1_2_ORDER_DETAIL));
    entityTypes.add(getEntityType(ENTITY_TYPE_1_3_PRODUCT));
    schema.setEntityTypes(entityTypes);

    List<ComplexType> complexTypes = new ArrayList<>();
    complexTypes.add(getComplexType(ADDRESS_COMPLEX_TYPE));
    schema.setComplexTypes(complexTypes);

    List<Association> associations = new ArrayList<>();
    associations.add(getAssociation(ASSOCIATION_PRODUCT_ORDER_DETAIL));
    associations.add(getAssociation(ASSOCIATION_ORDER_ORDER_DETAIL));
    schema.setAssociations(associations);

    List<EntityContainer> entityContainers = new ArrayList<>();
    EntityContainer entityContainer = new EntityContainer();
    entityContainer.setName(ENTITY_CONTAINER_ORDERS).setDefaultEntityContainer(true);

    List<EntitySet> entitySets = new ArrayList<>();
    entitySets.add(getEntitySet(ENTITY_CONTAINER_ORDERS, ENTITY_SET_NAME_ORDERS));
    entitySets.add(getEntitySet(ENTITY_CONTAINER_ORDERS, ENTITY_SET_NAME_ORDER_DETAILS));
    entitySets.add(getEntitySet(ENTITY_CONTAINER_ORDERS, ENTITY_SET_NAME_PRODUCTS));
    entityContainer.setEntitySets(entitySets);

    List<AssociationSet> associationSets = new ArrayList<>();
    associationSets.add(
        getAssociationSet(
            ENTITY_CONTAINER_ORDERS,
            ASSOCIATION_ORDER_ORDER_DETAIL,
            ENTITY_SET_NAME_ORDER_DETAILS,
                ROLE_ORDER_ORDER_DETAILS));
    associationSets.add(
        getAssociationSet(
            ENTITY_CONTAINER_ORDERS,
            ASSOCIATION_PRODUCT_ORDER_DETAIL,
            ENTITY_SET_NAME_ORDER_DETAILS,
                ROLE_PRODUCT_ORDER_DETAILS));
    entityContainer.setAssociationSets(associationSets);

    List<FunctionImport> functionImports = new ArrayList<>();
    functionImports.add(getFunctionImport(ENTITY_CONTAINER_ORDERS, FUNCTION_IMPORT_CREATE_ORDER));
    entityContainer.setFunctionImports(functionImports);

    entityContainers.add(entityContainer);
    schema.setEntityContainers(entityContainers);

    schemas.add(schema);

    return schemas;
  }

  @Override
  public EntityType getEntityType(FullQualifiedName edmFQName)  {
    if (NAMESPACE.equals(edmFQName.getNamespace())) {
      if (ENTITY_TYPE_1_1_ORDER.getNamespace().equals(edmFQName.getName())) {
        List<Property> properties = new ArrayList<>();
        properties.add(
            new SimpleProperty()
                .setName("Id")
                .setType(EdmSimpleTypeKind.Int64)
                .setFacets(new Facets().setNullable(false)));
        properties.add(
            new SimpleProperty()
                .setName("LocationId")
                .setType(EdmSimpleTypeKind.Int64)
                .setFacets(new Facets().setNullable(false)));
        properties.add(
            new SimpleProperty()
                .setName("CustomerId")
                .setType(EdmSimpleTypeKind.Int64)
                .setFacets(new Facets().setNullable(false)));

        properties.add(
            new ComplexProperty()
                .setName(ADDRESS)
                .setType(new FullQualifiedName(NAMESPACE, ADDRESS)));

        properties.add(
            new SimpleProperty()
                .setName("CreatedAt")
                .setType(EdmSimpleTypeKind.DateTime)
                .setFacets(
                    new Facets().setNullable(false).setConcurrencyMode(EdmConcurrencyMode.Fixed))
                .setCustomizableFeedMappings(
                    new CustomizableFeedMappings()
                        .setFcTargetPath(EdmTargetPath.SYNDICATION_UPDATED)));

        List<NavigationProperty> navigationProperties = new ArrayList<>();
        navigationProperties.add(
            new NavigationProperty()
                .setName(ORDER_DETAILS)
                .setRelationship(ASSOCIATION_ORDER_ORDER_DETAIL)
                .setFromRole(ROLE_ORDER_ORDER_DETAILS)
                .setToRole(ROLE_ORDER_DETAIL_ORDER));

        List<PropertyRef> keyProperties = new ArrayList<>();
        keyProperties.add(new PropertyRef().setName("Id"));
        Key key = new Key().setKeys(keyProperties);

        return new EntityType()
            .setName(ENTITY_TYPE_1_1_ORDER.getName())
            .setProperties(properties)
            .setKey(key)
            .setNavigationProperties(navigationProperties);
      } else if (ENTITY_TYPE_1_2_ORDER_DETAIL.getName().equals(edmFQName.getName())) {
        List<Property> properties = new ArrayList<>();
        properties.add(
            new SimpleProperty()
                .setName("Id")
                .setType(EdmSimpleTypeKind.Int64)
                .setFacets(new Facets().setNullable(false)));
        properties.add(
            new SimpleProperty()
                .setName("OrderId")
                .setType(EdmSimpleTypeKind.Int64)
                .setFacets(new Facets().setNullable(false)));
        properties.add(
            new SimpleProperty()
                .setName("ProductId")
                .setType(EdmSimpleTypeKind.Int64)
                .setFacets(new Facets().setNullable(false)));
        properties.add(
            new SimpleProperty()
                .setName("Quantity")
                .setType(EdmSimpleTypeKind.Int32)
                .setFacets(new Facets().setNullable(false)));

        List<NavigationProperty> navigationProperties = new ArrayList<>();
        navigationProperties.add(
            new NavigationProperty()
                .setName(ENTITY_NAME_ORDER)
                .setRelationship(ASSOCIATION_ORDER_ORDER_DETAIL)
                .setFromRole(ROLE_ORDER_DETAIL_ORDER)
                .setToRole(ROLE_ORDER_ORDER_DETAILS));
        navigationProperties.add(
            new NavigationProperty()
                .setName(ENTITY_NAME_PRODUCT)
                .setRelationship(ASSOCIATION_PRODUCT_ORDER_DETAIL)
                .setFromRole(ROLE_ORDER_DETAIL_PRODUCT)
                .setToRole(ROLE_PRODUCT_ORDER_DETAILS));

        List<PropertyRef> keyProperties = new ArrayList<>();
        keyProperties.add(new PropertyRef().setName("Id"));
        Key key = new Key().setKeys(keyProperties);

        return new EntityType()
            .setName(ENTITY_TYPE_1_2_ORDER_DETAIL.getName())
            .setProperties(properties)
            .setKey(key)
            .setNavigationProperties(navigationProperties);
      } else if (ENTITY_TYPE_1_3_PRODUCT.getName().equals(edmFQName.getName())) {
        List<Property> properties = new ArrayList<>();
        properties.add(
            new SimpleProperty()
                .setName("Id")
                .setType(EdmSimpleTypeKind.Int64)
                .setFacets(new Facets().setNullable(false)));
        properties.add(
            new SimpleProperty()
                .setName("Name")
                .setType(EdmSimpleTypeKind.String)
                .setFacets(new Facets().setNullable(false)));
        properties.add(
            new SimpleProperty()
                .setName("Description")
                .setType(EdmSimpleTypeKind.String)
                .setFacets(new Facets().setNullable(false).setMaxLength(1000)));
        properties.add(
            new SimpleProperty()
                .setName("Price")
                .setType(EdmSimpleTypeKind.Decimal)
                .setFacets(new Facets().setNullable(false)));
        properties.add(
            new SimpleProperty()
                .setName("Weight")
                .setType(EdmSimpleTypeKind.Double)
                .setFacets(new Facets().setNullable(false)));
        properties.add(
            new SimpleProperty()
                .setName("ProductCategoryId")
                .setType(EdmSimpleTypeKind.Int64)
                .setFacets(new Facets().setNullable(false)));
        properties.add(
            new SimpleProperty()
                .setName("SupplierId")
                .setType(EdmSimpleTypeKind.Int64)
                .setFacets(new Facets().setNullable(false)));
        properties.add(
            new SimpleProperty()
                .setName("ImageUrl")
                .setType(EdmSimpleTypeKind.String)
                .setFacets(new Facets().setNullable(false)));

        List<NavigationProperty> navigationProperties = new ArrayList<>();
        navigationProperties.add(
            new NavigationProperty()
                .setName(ORDER_DETAILS)
                .setRelationship(ASSOCIATION_PRODUCT_ORDER_DETAIL)
                .setFromRole(ROLE_PRODUCT_ORDER_DETAILS)
                .setToRole(ROLE_ORDER_DETAIL_PRODUCT));

        List<PropertyRef> keyProperties = new ArrayList<>();
        keyProperties.add(new PropertyRef().setName("Id"));
        Key key = new Key().setKeys(keyProperties);

        return new EntityType()
            .setName(ENTITY_TYPE_1_3_PRODUCT.getName())
            .setProperties(properties)
            .setNavigationProperties(navigationProperties)
            .setKey(key);
      }
    }
    return null;
  }

  @Override
  public Association getAssociation(FullQualifiedName edmFQName) {
    if (NAMESPACE.equals(edmFQName.getNamespace())) {
      if (ASSOCIATION_ORDER_ORDER_DETAIL.getName().equals(edmFQName.getName())) {
        return new Association()
            .setName(ASSOCIATION_ORDER_ORDER_DETAIL.getName())
            .setEnd1(
                new AssociationEnd()
                    .setType(ENTITY_TYPE_1_1_ORDER)
                    .setRole(ROLE_ORDER_ORDER_DETAILS)
                    .setMultiplicity(EdmMultiplicity.ONE))
            .setEnd2(
                new AssociationEnd()
                    .setType(ENTITY_TYPE_1_2_ORDER_DETAIL)
                    .setRole(ROLE_ORDER_DETAIL_ORDER)
                    .setMultiplicity(EdmMultiplicity.MANY));
      } else if (ASSOCIATION_PRODUCT_ORDER_DETAIL.getName().equals(edmFQName.getName())) {
        return new Association()
            .setName(ASSOCIATION_ORDER_ORDER_DETAIL.getName())
            .setEnd1(
                new AssociationEnd()
                    .setType(ENTITY_TYPE_1_3_PRODUCT)
                    .setRole(ROLE_PRODUCT_ORDER_DETAILS)
                    .setMultiplicity(EdmMultiplicity.ONE))
            .setEnd2(
                new AssociationEnd()
                    .setType(ENTITY_TYPE_1_2_ORDER_DETAIL)
                    .setRole(ROLE_ORDER_DETAIL_PRODUCT)
                    .setMultiplicity(EdmMultiplicity.MANY));
      }
    }
    return null;
  }

  @Override
  public EntityContainerInfo getEntityContainerInfo(String name) {
    if (name == null || ENTITY_CONTAINER_ORDERS.equals(name)) {
      return new EntityContainerInfo()
          .setName(ENTITY_CONTAINER_ORDERS)
          .setDefaultEntityContainer(true);
    }
    return null;
  }

  @Override
  public AssociationSet getAssociationSet(
      String entityContainer,
      FullQualifiedName association,
      String sourceEntitySetName,
      String sourceEntitySetRole) {
    if (ENTITY_CONTAINER_ORDERS.equals(entityContainer)) {
      if (ASSOCIATION_ORDER_ORDER_DETAIL.equals(association)) {
        return new AssociationSet()
            .setName(ASSOCIATION_SET_ORDERS_ORDER_DETAILS)
            .setAssociation(ASSOCIATION_ORDER_ORDER_DETAIL)
            .setEnd1(
                new AssociationSetEnd()
                    .setRole(ROLE_ORDER_ORDER_DETAILS)
                    .setEntitySet(ENTITY_SET_NAME_ORDERS))
            .setEnd2(
                new AssociationSetEnd()
                    .setRole(ROLE_ORDER_DETAIL_ORDER)
                    .setEntitySet(ENTITY_SET_NAME_ORDER_DETAILS));
      } else if (ASSOCIATION_PRODUCT_ORDER_DETAIL.equals(association)) {
        return new AssociationSet()
            .setName(ASSOCIATION_SET_PRODUCTS_ORDER_DETAILS)
            .setAssociation(ASSOCIATION_PRODUCT_ORDER_DETAIL)
            .setEnd1(
                new AssociationSetEnd()
                    .setRole(ROLE_PRODUCT_ORDER_DETAILS)
                    .setEntitySet(ENTITY_SET_NAME_PRODUCTS))
            .setEnd2(
                new AssociationSetEnd()
                    .setRole(ROLE_ORDER_DETAIL_PRODUCT)
                    .setEntitySet(ENTITY_SET_NAME_ORDER_DETAILS));
      }
    }
    return null;
  }

  @Override
  public EntitySet getEntitySet(String entityContainer, String name) {
    if (ENTITY_CONTAINER_ORDERS.equals(entityContainer)) {
      if (ENTITY_SET_NAME_ORDERS.equals(name)) {
        return new EntitySet().setName(name).setEntityType(ENTITY_TYPE_1_1_ORDER);
      } else if (ENTITY_SET_NAME_ORDER_DETAILS.equals(name)) {
        return new EntitySet().setName(name).setEntityType(ENTITY_TYPE_1_2_ORDER_DETAIL);
      } else if (ENTITY_SET_NAME_PRODUCTS.equals(name)) {
        return new EntitySet().setName(name).setEntityType(ENTITY_TYPE_1_3_PRODUCT);
      }
    }
    return null;
  }

  @Override
  public ComplexType getComplexType(FullQualifiedName edmFQName)  {
    if (NAMESPACE.equals(edmFQName.getNamespace()) && ADDRESS_COMPLEX_TYPE.getName().equals(edmFQName.getName())) {
        List<Property> properties = new ArrayList<>();
        properties.add(new SimpleProperty().setName("Country").setType(EdmSimpleTypeKind.String));
        properties.add(new SimpleProperty().setName("County").setType(EdmSimpleTypeKind.String));
        properties.add(new SimpleProperty().setName("City").setType(EdmSimpleTypeKind.String));
        properties.add(
            new SimpleProperty().setName("StreetAddress").setType(EdmSimpleTypeKind.String));
        return new ComplexType().setName(ADDRESS_COMPLEX_TYPE.getName()).setProperties(properties);

    }
    return null;
  }

  @Override
  public FunctionImport getFunctionImport(String entityContainer, String name) {
    if (ENTITY_CONTAINER_ORDERS.equals(entityContainer)&& FUNCTION_IMPORT_CREATE_ORDER.equals(name)) {
        return new FunctionImport()
            .setName(name)
            .setReturnType(
                new ReturnType()
                    .setTypeName(ENTITY_TYPE_1_1_ORDER)
                    .setMultiplicity(EdmMultiplicity.ONE))
            .setHttpMethod("POST");

    }
    return null;
  }
}
