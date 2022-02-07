package com.starter.fullstack.dao;

import com.starter.fullstack.api.Inventory;
import com.starter.fullstack.config.EmbedMongoClientOverrideConfig;
import java.util.List;
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
		// Creating new inventory objects. I have 2 inventories to see if their IDs are
		// NOT same
		Inventory inventory1 = new Inventory();
		Inventory inventory2 = new Inventory();

		// Print to make sure inventories are null.
		System.out.println("IB - inventory1 before saving data " + inventory1);
		System.out.println("IB - inventory2 before saving data " + inventory2);

		// Setting name and product type for inventory1
		inventory1.setName("Veggies");
		inventory1.setProductType("Potato");

		// Setting name and product type for inventory2
		inventory2.setName("Fruits");
		inventory2.setProductType("Apple");

		// Saving mongo template with inventories data with "create" method from
		// InventoryDAO
		Inventory createdInventory1 = inventoryDAO.create(inventory1);
		Inventory createdInventory2 = inventoryDAO.create(inventory2);

		// Test if inventory is not blank
		Assert.assertNotNull("Inventory1 should NOT be Null", createdInventory1);
		Assert.assertNotNull("Inventory2 should NOT be Null", createdInventory2);

		// Test if ID is NOT null
		Assert.assertNotNull("ID should NOT be Null", createdInventory1.getId());
		Assert.assertNotNull("ID2 should NOT be Null", createdInventory2.getId());

		// Print to see inventories are saved with given data
		System.out.println("Hi IB, your createdInventory1 is " + createdInventory1);
		System.out.println("Hi IB, your createdInventory2 is " + createdInventory2);

		// Test to check inventory1 data is equal to saved one
		Assert.assertEquals(createdInventory1.getName(), "Veggies");
		Assert.assertEquals(createdInventory1.getProductType(), "Potato");

		// Test to check inventory2 data is equal to saved one
		Assert.assertEquals(createdInventory2.getName(), "Fruits");
		Assert.assertEquals(createdInventory2.getProductType(), "Apple");

		// To see all instances of current collection
		List<Inventory> actualInventory = this.inventoryDAO.findAll();
		// totalNumberOfInstances = actualInventory.stream().count();
		System.out.println("IB - You inventory count is " + actualInventory.stream().count());

		// Test is number of instances matches created ones. I have inventory1 and
		// inventory2. So count should be equal to 2
		Assert.assertEquals(actualInventory.stream().count(), 2);

		// this.inventoryDAO.delete(createdInventory.get()); - no need for Deleting
		// collection, as it will be deleted with @After

	}
}
