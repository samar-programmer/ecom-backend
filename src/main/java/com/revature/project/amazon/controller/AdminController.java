package com.revature.project.amazon.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.project.amazon.constants.ResponseCode;
import com.revature.project.amazon.exception.ProductCustomException;
import com.revature.project.amazon.model.Product;
import com.revature.project.amazon.model.ProductVarient;
import com.revature.project.amazon.model.VarientValue;
import com.revature.project.amazon.response.ProductResponse;
import com.revature.project.amazon.service.AdminService;
import com.revature.project.amazon.utility.AllProductInfoDTO;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;
	
	Logger logger = LoggerFactory.getLogger(AdminController.class);
	
	
	@PostMapping("product")
	public ResponseEntity<ProductResponse> addProduct(@Valid @RequestBody final Product product) {
		logger.info("Inside addProduct Method");
		ProductResponse productResponse = new ProductResponse();
		try {
			adminService.addProduct(product);
			productResponse.setStatus(ResponseCode.SUCCESS_CODE);
			productResponse.setMessage(ResponseCode.ADD_SUCCESS_MESSAGE);
			productResponse.setSuccessErrorType(ResponseCode.SUCCESS);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ProductCustomException("Unable to save product details, please try again");
		}
		return new ResponseEntity<ProductResponse>(productResponse, HttpStatus.OK);

	}

	
	@GetMapping("/products")
	public ResponseEntity<ProductResponse> getAllProduct() {
		logger.info("Inside getAllProduct Method");
		ProductResponse productResponse = new ProductResponse();
		try {
			productResponse.setProductList(adminService.getProducts());

			productResponse.setVarientList(adminService.getVarients());
			productResponse.setVarientValuesList(adminService.getVarientValues());

			List<Product> originalProductData = adminService.getOriginalProductDrtails(productResponse);

			productResponse.setProductList(originalProductData);

			productResponse.setStatus(ResponseCode.SUCCESS_CODE);
			productResponse.setMessage(ResponseCode.GET_SUCCESS_MESSAGE);
			productResponse.setSuccessErrorType(ResponseCode.SUCCESS);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ProductCustomException("Unable to Fetch product details, please try again");
		}

		return new ResponseEntity<ProductResponse>(productResponse, HttpStatus.OK);
	}

	
	@DeleteMapping("/product/{id}")
	public ResponseEntity<ProductResponse> deleteProduct(@PathVariable(value = "id") Long productId) {
		logger.info("Inside deleteProduct Method");
		ProductResponse productResponse = new ProductResponse();

		List<ProductVarient> deleteVarientValues = adminService.getvarientByProductId(productId);

		for (ProductVarient deleteVarientValue : deleteVarientValues) {
			adminService.deleteVarientValueByVarientId(deleteVarientValue.getVarientId());
		}

		adminService.deleteproductVarientById(Integer.parseInt(productId.toString()));

		try {
			adminService.deleteProductbyId(productId);
			productResponse.setStatus(ResponseCode.SUCCESS_CODE);
			productResponse.setMessage(ResponseCode.DEL_SUCCESS_MESSAGE);
		} catch (Exception e) {
			throw new ProductCustomException("Unable to delete product details, please try again");
		}
		return new ResponseEntity<ProductResponse>(productResponse, HttpStatus.OK);
	}

	@PostMapping("/varient")
	public ResponseEntity<ProductResponse> addVarient(@RequestParam String productId, @RequestParam String value,
			@RequestParam String email, @RequestParam String model) {
		logger.info("Inside addVarient Method");
		ProductResponse productResponse = new ProductResponse();
		try {
			boolean addOrNot = true;
			List<ProductVarient> valuechecks = adminService.getvarientByProductId(Long.parseLong(productId));

			if (valuechecks != null) {
				for (ProductVarient valuecheck : valuechecks) {

					if (valuecheck.getValue().equalsIgnoreCase(value)) {
						addOrNot = false;
					}

				}
			}

			if (addOrNot == true) {
				Product product = adminService.getProductbyId(Long.parseLong(productId));
			    	
				ProductVarient varients = new ProductVarient();
				varients.setValue(value);
				varients.setProductId(Integer.parseInt(productId));
				varients.setEmail(email);
				varients.setModel(model);
				List<ProductVarient> ProductVarientsList = new ArrayList();
				ProductVarientsList.add(varients);
				product.setVarients(ProductVarientsList);
				adminService.addProduct(product);

				productResponse.setStatus(ResponseCode.SUCCESS_CODE);
				productResponse.setMessage("Varient Added SuccessFully");
				productResponse.setSuccessErrorType(ResponseCode.SUCCESS);
			} else {
				productResponse.setStatus("409");
				productResponse.setMessage("Varients already added");
				productResponse.setSuccessErrorType(ResponseCode.SUCCESS);
			}
			addOrNot = true;

		} catch (Exception e) {
			e.printStackTrace();
			throw new ProductCustomException("Unable to save varients details, please try again");
		}
		return new ResponseEntity<ProductResponse>(productResponse, HttpStatus.OK);

	}

	@PutMapping("/varient")
	public ResponseEntity<ProductResponse> editVarient(@RequestBody ProductVarient editVarints) {
		ProductResponse productResponse = new ProductResponse();
		logger.info("Inside editVarient Method");
		try {
			boolean addOrNot = true;
			List<ProductVarient> valuechecks = adminService.getvarientByProductId(editVarints.getProductId());

			if (valuechecks != null) {
				for (ProductVarient valuecheck : valuechecks) {

					if (valuecheck.getValue().equalsIgnoreCase(editVarints.getValue())) {
						addOrNot = false;
					}

				}
			}

			if (addOrNot == true) {
				Product product = adminService.getProductbyId((long) editVarints.getProductId());		    	

				List<ProductVarient> ProductVarientsList = new ArrayList();
				ProductVarientsList.add(editVarints);
				product.setVarients(ProductVarientsList);
				adminService.addProduct(product);

				productResponse.setStatus(ResponseCode.SUCCESS_CODE);
				productResponse.setMessage("Varient Updated SuccessFully");
				productResponse.setSuccessErrorType(ResponseCode.SUCCESS);
			} else {
				productResponse.setStatus("409");
				productResponse.setMessage("Varients already added");
				productResponse.setSuccessErrorType(ResponseCode.SUCCESS);
			}
			addOrNot = true;

		} catch (Exception e) {
			e.printStackTrace();
			throw new ProductCustomException("Unable to save varients details, please try again");
		}
		return new ResponseEntity<ProductResponse>(productResponse, HttpStatus.OK);

	}

	@DeleteMapping("/varient/{id}")
	public ResponseEntity<ProductResponse> deleteVarient(@PathVariable(value = "id") Long VarientId) {
		ProductResponse productResponse = new ProductResponse();
		logger.info("Inside deleteVarient Method");
		try {

			adminService.deleteVarientValueByVarientId(VarientId);
			adminService.deleteVarientbyId(VarientId);

			productResponse.setStatus(ResponseCode.SUCCESS_CODE);
			productResponse.setMessage("Varient Deleted SuccessFully");
		} catch (Exception e) {
			throw new ProductCustomException("Unable to delete product details, please try again");
		}
		return new ResponseEntity<ProductResponse>(productResponse, HttpStatus.OK);
	}

	@PostMapping("/varientvalue")
	public ResponseEntity<ProductResponse> addVarientValues(@RequestParam String varientId, @RequestParam String name,
			@RequestParam String price, @RequestParam String email, @RequestParam String model) {
		logger.info("Inside addVarientValues Method");
		ProductResponse productResponse = new ProductResponse();
		try {
			boolean addOrNot = true;
			List<VarientValue> valuechecks = adminService.getvarientValuesByVarientId(Long.parseLong(varientId));

			if (valuechecks != null) {
				for (VarientValue valuecheck : valuechecks) {

					if (valuecheck.getName().equalsIgnoreCase(name)) {
						addOrNot = false;
					}

				}
			}

			if (addOrNot == true) {
				ProductVarient ProductVarients = adminService.getvarientValuesbyVarientId(Long.parseLong(varientId));			    	
				VarientValue varientvalues = new VarientValue();
				varientvalues.setVarientId(Long.parseLong(varientId));
				varientvalues.setName(name);
				varientvalues.setPrice(price);
				varientvalues.setEmail(email);
				varientvalues.setModel(model);
				List<VarientValue> ProductVarientsList = new ArrayList();
				ProductVarientsList.add(varientvalues);
				ProductVarients.setVarientvalues(ProductVarientsList);
				adminService.addVarientValues(ProductVarients);

				productResponse.setStatus(ResponseCode.SUCCESS_CODE);
				productResponse.setMessage(ResponseCode.ADD_SUCCESS_MESSAGE);
				productResponse.setSuccessErrorType(ResponseCode.SUCCESS);
			} else {
				productResponse.setStatus("409");
				productResponse.setMessage("Varients already added");
				productResponse.setSuccessErrorType(ResponseCode.SUCCESS);
			}
			addOrNot = true;

		} catch (Exception e) {
			e.printStackTrace();
			throw new ProductCustomException("Unable to save varients details, please try again");
		}
		return new ResponseEntity<ProductResponse>(productResponse, HttpStatus.OK);

	}

	@PutMapping("/varientvalue")
	public ResponseEntity<ProductResponse> editVarientValues(@RequestBody VarientValue editVarintValues) {
		ProductResponse productResponse = new ProductResponse();
		logger.info("Inside editVarientValues Method");
		try {
			boolean addOrNot = true;

			if (addOrNot == true) {
				ProductVarient ProductVarients = adminService
						.getvarientValuesbyVarientId(editVarintValues.getVarientId());
				VarientValue varientvalues = new VarientValue();
				varientvalues.setVarientValuesId(editVarintValues.getVarientValuesId());
				varientvalues.setVarientId(editVarintValues.getVarientId());
				varientvalues.setName(editVarintValues.getName());
				varientvalues.setPrice(editVarintValues.getPrice());
				varientvalues.setEmail(editVarintValues.getEmail());
				varientvalues.setModel(editVarintValues.getModel());
				List<VarientValue> ProductVarientsList = new ArrayList();
				ProductVarientsList.add(varientvalues);
				ProductVarients.setVarientvalues(ProductVarientsList);
				adminService.addVarientValues(ProductVarients);

				productResponse.setStatus(ResponseCode.SUCCESS_CODE);
				productResponse.setMessage(ResponseCode.ADD_SUCCESS_MESSAGE);
				productResponse.setSuccessErrorType(ResponseCode.SUCCESS);
			} else {
				productResponse.setStatus("409");
				productResponse.setMessage("Varients already added");
				productResponse.setSuccessErrorType(ResponseCode.SUCCESS);
			}
			addOrNot = true;

		} catch (Exception e) {
			e.printStackTrace();
			throw new ProductCustomException("Unable to save varients details, please try again");
		}
		return new ResponseEntity<ProductResponse>(productResponse, HttpStatus.OK);

	}

	@DeleteMapping("/varientvalue/{id}")
	public ResponseEntity<ProductResponse> deleteVarientValue(@PathVariable(value = "id") String varientValueId) {
		ProductResponse productResponse = new ProductResponse();
		logger.info("Inside deleteVarientValue Method");
		try {
			adminService.deleteVarientValuebyId(Long.parseLong(varientValueId));
			productResponse.setStatus(ResponseCode.SUCCESS_CODE);
			productResponse.setMessage(ResponseCode.DEL_SUCCESS_MESSAGE);
		} catch (Exception e) {
			throw new ProductCustomException("Unable to delete product details, please try again");
		}
		return new ResponseEntity<ProductResponse>(productResponse, HttpStatus.OK);
	}

	@GetMapping("/getProductsForAdmin")
	public ResponseEntity<ProductResponse> getAllProductForAdmin(@RequestParam String email) {
		ProductResponse productResponse = new ProductResponse();
		logger.info("Inside getAllProductForAdmin Method");
		try {
			productResponse.setProductList(adminService.getProducts(email));
			productResponse.setVarientList(adminService.getVarients(email));
			productResponse.setVarientValuesList(adminService.getVarientValues(email));
			productResponse.setStatus(ResponseCode.SUCCESS_CODE);
			productResponse.setMessage(ResponseCode.GET_SUCCESS_MESSAGE);
			productResponse.setSuccessErrorType(ResponseCode.SUCCESS);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ProductCustomException("Unable to Fetch product details For Admin, please try again");
		}

		return new ResponseEntity<ProductResponse>(productResponse, HttpStatus.OK);
	}

}
