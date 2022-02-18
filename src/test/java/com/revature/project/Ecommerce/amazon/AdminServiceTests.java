package com.revature.project.Ecommerce.amazon;

import static org.mockito.Mockito.doReturn;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import com.revature.project.amazon.AmazonApplication;
import com.revature.project.amazon.model.ProductVarient;
import com.revature.project.amazon.repository.VarientRepository;
import com.revature.project.amazon.service.impl.AdminServiceImpl;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
//@AutoConfigureRestDocs(outputDir = "target")
@ContextConfiguration(classes = AmazonApplication.class)
public class AdminServiceTests {
	
	@Autowired
    private AdminServiceImpl adminServiceImpl;
    
    
	@MockBean
    private VarientRepository varientRepository;
	
	
	List<ProductVarient> varients ;
	
	@BeforeEach
    public void setUp() {
		varients=Stream.of(new ProductVarient(101L, 15, "Ram", "sasi@gmail.com", "sss", null),
				new ProductVarient(102L, 15, "Ram", "sasi@gmail.com", "sss", null))
                .collect(Collectors.toList());
    }
    
    @Test
    public void adminServiceImplTest_getProducts() {
         doReturn(varients).when(varientRepository).findAll();
		List<ProductVarient> items = adminServiceImpl.getVarients();
		System.out.println(items);
		Assertions.assertEquals(2, varients.size(), "findAll should return 2 Varients");
    }
    
    @Test
    public void adminServiceImplTest_saveVarientsForProduct() {
    	ProductVarient varient = new ProductVarient(101L, 15, "Ram", "sasi@gmail.com", "sss", null);
    			 doReturn(varient).when(varientRepository).save(any());
		 adminServiceImpl.saveVarientsForProduct(varient);
		
    }
    
    
    @Test
    void testFindById() {
    	ProductVarient productVarient = new ProductVarient(76l, 15, "Ram", "sasi@gmail.com", "sss", null);
        doReturn(Optional.of(productVarient)).when(varientRepository).findById(76l);

        ProductVarient returnedproductVarient = adminServiceImpl.getvarientValuesbyVarientId(76l);
        
        //returnedproductVarient = null;
        Assertions.assertSame(returnedproductVarient, productVarient, "The product varient returned was not the same as the mock");
    }
    
    
    
 
    
 

    
    
}
