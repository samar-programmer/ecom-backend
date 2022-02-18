package com.revature.project.Ecommerce.amazon;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.aspectj.weaver.ast.Var;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.project.amazon.AmazonApplication;
import com.revature.project.amazon.model.Product;
import com.revature.project.amazon.model.ProductVarient;
import com.revature.project.amazon.response.ProductResponse;
import com.revature.project.amazon.utility.ProductDto;

@ExtendWith({ RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
@AutoConfigureRestDocs(outputDir = "target")
@ContextConfiguration(classes = AmazonApplication.class)
public class AdminControllerTests {
	
	@Autowired
    private WebApplicationContext context;
    
	
    private MockMvc mockMvc;


    ProductDto  product=null;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
                      RestDocumentationContextProvider restDocumentation) {

        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();
        //change model
        product=new ProductDto((long) 117, "Electronics","Mobile","samsung","s37","http://sample", "1", "15000", "150", "2/16/17","Good Mobile", "sasi1@gmail.com");

        
    }
    
    
    @Test
    public void testAddProduct() throws Exception {
        String ordersJson=new ObjectMapper().writeValueAsString(product);
        mockMvc.perform(post("/api/admin/product")
                .content(ordersJson)
                .contentType("application/json")).andDo(print())
                .andExpect(status().isOk())
                .andDo(document("{methodName}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }
    

    @Test
    public void testGetProducts() throws Exception {
    	MvcResult result  =  mockMvc.perform(get("/api/admin/products")
                   .contentType("application/json")).andDo(print())
                   .andExpect(status().isOk())
                   .andDo(document("{methodName}",
                           preprocessRequest(prettyPrint()),
                           preprocessResponse(prettyPrint())))
                   .andReturn();
    	
    	int status = result.getResponse().getStatus();
    	assertEquals("Incorrect Response Status", HttpStatus.OK.value(), status);


    }

    @Test
    public void testAddVarient() throws Exception {
       mockMvc.perform(post("/api/admin/varient")
        		.param("productId", "12")
        		.param("value", "color762")//change
        		.param("email", "sasi1@gmail.com")
        		.param("model", "s20")).andDo(print())
                .andExpect(status().isOk())
                .andDo(document("{methodName}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }
    
    
   @Test
    public void testEditVarient() throws Exception {
    	ProductVarient varient = new ProductVarient();
    	varient.setEmail("samsung@gmail.com");
    	varient.setModel("s20");
    	varient.setProductId(12);
    	varient.setVarientId((long)39);
    	varient.setValue("Rams237s1"); //changes
    	String varientJson=new ObjectMapper().writeValueAsString(varient);
        mockMvc.perform(put("/api/admin/varient")
        		.content(varientJson)
                .contentType("application/json")).andDo(print())
                .andExpect(status().isOk())
                .andDo(document("{methodName}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }
    
    @Test
    public void testDeleteVarient() throws Exception {

        mockMvc.perform(delete("/api/admin/varient/{id}", 47)).andDo(print())
                .andExpect(status().isOk())
                .andDo(document("{methodName}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

}
