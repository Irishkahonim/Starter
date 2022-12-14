package com.starter.fullstack.dao;

import com.starter.fullstack.api.Inventory;
import com.starter.fullstack.config.EmbedMongoClientOverrideConfig;
import java.util.List;
import java.util.Optional;
import javax.annotation.Resource;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Test Inventory DAO.
 */
@ContextConfiguration(classes = { EmbedMongoClientOverrideConfig.class })
@DataMongoTest
@RunWith(SpringRunner.class)
public class InventoryDAOTest {
  @Resource
  private MongoTemplate mongoTemplate;
  private InventoryDAO inventoryDAO;
  private static final String NAME = "Amber";
  private static final String PRODUCT_TYPE = "hops";

  @Before
  public void setup() {
    this.inventoryDAO = new InventoryDAO(this.mongoTemplate);
  }

  @After
  public void tearDown() {
    this.mongoTemplate.dropCollection(Inventory.class);
  }

/**
* Test Find All method.
*/
  @Test
  public void findAll() {
    Inventory inventory = new Inventory();
    inventory.setName(NAME);
    inventory.setProductType(PRODUCT_TYPE);
    this.mongoTemplate.save(inventory);
    List<Inventory> actualInventory = this.inventoryDAO.findAll();
    Assert.assertFalse(actualInventory.isEmpty());
  }

/**
* Test Create/Save method.
*/
  @Test
  public void save() {
    Inventory inventory1 = new Inventory();
    Inventory inventory2 = new Inventory();

    inventory1.setName("Veggies");
    inventory1.setProductType("Potato");

    inventory2.setName("Fruits");
    inventory2.setProductType("Apple");

    Inventory createdInventory1 = inventoryDAO.create(inventory1);
    Inventory createdInventory2 = inventoryDAO.create(inventory2);

    Assert.assertNotNull("Inventory1 should NOT be Null", createdInventory1);
    Assert.assertNotNull("Inventory2 should NOT be Null", createdInventory2);

    Assert.assertNotNull("ID should NOT be Null", createdInventory1.getId());
    Assert.assertNotNull("ID2 should NOT be Null", createdInventory2.getId());

    Assert.assertEquals(createdInventory1.getName(), "Veggies");
    Assert.assertEquals(createdInventory1.getProductType(), "Potato");

    Assert.assertEquals(createdInventory2.getName(), "Fruits");
    Assert.assertEquals(createdInventory2.getProductType(), "Apple");

    List<Inventory> actualInventory = this.inventoryDAO.findAll();

    Assert.assertEquals(actualInventory.stream().count(), 2);

  }
  
  /**
  * Test Delete method.
  */
  @Test
  public void delete() { 
    Inventory inventory = new Inventory();
    inventory.setName("Toyota");
    inventory.setProductType("Cars");
    System.out.println("IB Inventory before CREATE " + inventory);
    
    Inventory createdInventory = inventoryDAO.create(inventory);
    System.out.println("IB Inventory after CREATE - createdInventory " + createdInventory);
    
    //Optional type for Created Inventory - expected value
    Optional<Inventory> inventoryOptCreated = Optional.ofNullable(createdInventory);
    
    Assert.assertNotNull("Inventory should NOT be Null", createdInventory);
    Assert.assertNotNull("ID should NOT be Null", createdInventory.getId());

    //Optional type for Deleted Inventory - actual value
    Optional<Inventory> inventoryOptionalDeleted = this.inventoryDAO.delete(createdInventory.getId());  
    System.out.println("IB Optional Inventory after delete - will retun deleted inventory " + inventoryOptionalDeleted);

    //Checking what we have after deleting. Number of inventories should be equal to Zero
    List<Inventory> inventories = this.inventoryDAO.findAll();
    Assert.assertEquals(inventories.stream().count(), 0);
    
    //Checking that deleted inventory is the same as created inventory
    Assert.assertEquals(inventoryOptCreated, inventoryOptionalDeleted);   

  }
}