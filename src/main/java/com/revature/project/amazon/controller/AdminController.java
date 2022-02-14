package com.revature.project.amazon.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.project.amazon.constants.ResponseCode;
import com.revature.project.amazon.exception.ProductCustomException;
import com.revature.project.amazon.model.Product;
import com.revature.project.amazon.model.ProductVarients;
import com.revature.project.amazon.model.VarientValues;
import com.revature.project.amazon.response.ProductResponse;
import com.revature.project.amazon.service.AdminService;
import com.revature.project.amazon.utility.AllProductInfoDTO;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;

	@PostMapping("/addProduct")
	public ResponseEntity<ProductResponse> addProduct(@Valid @RequestBody final Product product) {
		ProductResponse productResponse = new ProductResponse();
		try {
			System.out.print("sasi====>" + product.getEmail());
			adminService.addProduct(product);
			productResponse.setStatus(ResponseCode.SUCCESS_CODE);
			productResponse.setMessage(ResponseCode.ADD_SUCCESS_MESSAGE);
			productResponse.setSuccessErrorType(ResponseCode.SUCCESS);
			System.out.println(product);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ProductCustomException("Unable to save product details, please try again");
		}
		return new ResponseEntity<ProductResponse>(productResponse, HttpStatus.OK);

	}

	@GetMapping("/getProducts")
	public ResponseEntity<ProductResponse> getAllProduct() {
		ProductResponse productResponse = new ProductResponse();
		try {
			productResponse.setProductList(adminService.getProducts());
			List<Product> originalProductData = new ArrayList();
			productResponse.setVarientList(adminService.getVarients());
			productResponse.setVarientValuesList(adminService.getVarientValues());
			int totalcheck = 0;

			for (Product product : productResponse.getProductList()) {
				String totalPrice = product.getProductDiscountPrice();
				String radiocheck = "yes";
				product.setTotalPrice(totalPrice);

				List<ProductVarients> productVarients = adminService.getvarientByProductId(product.getProductId());

				for (ProductVarients varentvalue : productVarients) {

					List<VarientValues> list = adminService.getvarientValuesByVarientId(varentvalue.getVarientId());

					if (totalcheck == 0) {

						for (VarientValues values : list) {
							if (radiocheck.equalsIgnoreCase("yes")) {
								int FirstProductValue = Integer.parseInt(totalPrice)
										+ Integer.parseInt(values.getPrice());
								product.setTotalPrice(String.valueOf(FirstProductValue));
								product.setRadioCheck("yes");
								radiocheck = "no";
							}

							totalcheck++;
						}

					}
					totalcheck = 0;

					varentvalue.setVarientvalues(list);

				}

				product.setVarients(productVarients);

				originalProductData.add(product);

			}

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

	@DeleteMapping("/deleteProduct/{id}")
	public ResponseEntity<ProductResponse> deleteProduct(@PathVariable(value = "id") Long productId) {
		ProductResponse productResponse = new ProductResponse();

		List<ProductVarients> deleteVarientValues = adminService.getvarientByProductId(productId);

		for (ProductVarients deleteVarientValue : deleteVarientValues) {
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

	@PostMapping("/addVarient")
	public ResponseEntity<ProductResponse> addVarient(@RequestParam String productId, @RequestParam String value,
			@RequestParam String email) {
		ProductResponse productResponse = new ProductResponse();
		try {
			boolean addOrNot = true;
			List<ProductVarients> valuechecks = adminService.getvarientByProductId(Long.parseLong(productId));

			if (valuechecks != null) {
				for (ProductVarients valuecheck : valuechecks) {

					if (valuecheck.getValue().equalsIgnoreCase(value)) {
						addOrNot = false;
					}

				}
			}

			if (addOrNot == true) {
				Product product = adminService.getProductbyId(Long.parseLong(productId));
//			    	
				ProductVarients varients = new ProductVarients();
				varients.setValue(value);
				varients.setProductId(Integer.parseInt(productId));
				varients.setEmail(email);
				List<ProductVarients> ProductVarientsList = new ArrayList();
				ProductVarientsList.add(varients);
				product.setVarients(ProductVarientsList);
				adminService.addProduct(product);

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

	@PostMapping("/editVarient")
	public ResponseEntity<ProductResponse> editVarient(@RequestBody ProductVarients editVarints) {
		ProductResponse productResponse = new ProductResponse();
		try {
			boolean addOrNot = true;
			List<ProductVarients> valuechecks = adminService.getvarientByProductId(editVarints.getProductId());

			if (valuechecks != null) {
				for (ProductVarients valuecheck : valuechecks) {

					if (valuecheck.getValue().equalsIgnoreCase(editVarints.getValue())) {
						addOrNot = false;
					}

				}
			}

			if (addOrNot == true) {
				Product product = adminService.getProductbyId((long) editVarints.getProductId());
//			    	

				List<ProductVarients> ProductVarientsList = new ArrayList();
				ProductVarientsList.add(editVarints);
				product.setVarients(ProductVarientsList);
				adminService.addProduct(product);

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

	@DeleteMapping("/deleteVarient/{id}")
	public ResponseEntity<ProductResponse> deleteVarient(@PathVariable(value = "id") Long VarientId) {
		ProductResponse productResponse = new ProductResponse();
		try {

			adminService.deleteVarientValueByVarientId(VarientId);
			adminService.deleteVarientbyId(VarientId);

			productResponse.setStatus(ResponseCode.SUCCESS_CODE);
			productResponse.setMessage(ResponseCode.DEL_SUCCESS_MESSAGE);
		} catch (Exception e) {
			throw new ProductCustomException("Unable to delete product details, please try again");
		}
		return new ResponseEntity<ProductResponse>(productResponse, HttpStatus.OK);
	}

	@PostMapping("/addVarientValues")
	public ResponseEntity<ProductResponse> addVarientValues(@RequestParam String varientId, @RequestParam String name,
			@RequestParam String price, @RequestParam String email) {
		ProductResponse productResponse = new ProductResponse();
		try {
			boolean addOrNot = true;
			List<VarientValues> valuechecks = adminService.getvarientValuesByVarientId(Long.parseLong(varientId));

			if (valuechecks != null) {
				for (VarientValues valuecheck : valuechecks) {

					if (valuecheck.getName().equalsIgnoreCase(name)) {
						addOrNot = false;
					}

				}
			}

			if (addOrNot == true) {
				ProductVarients ProductVarients = adminService.getvarientValuesbyVarientId(Long.parseLong(varientId));
//			    	
				VarientValues varientvalues = new VarientValues();
				varientvalues.setVarientId(Long.parseLong(varientId));
				varientvalues.setName(name);
				varientvalues.setPrice(price);
				varientvalues.setEmail(email);
				List<VarientValues> ProductVarientsList = new ArrayList();
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

	@PostMapping("/editVarientValues")
	public ResponseEntity<ProductResponse> editVarientValues(@RequestBody VarientValues editVarintValues) {
		ProductResponse productResponse = new ProductResponse();
		try {
			boolean addOrNot = true;

			if (addOrNot == true) {
				ProductVarients ProductVarients = adminService
						.getvarientValuesbyVarientId(editVarintValues.getVarientId());
				VarientValues varientvalues = new VarientValues();
				varientvalues.setVarientValuesId(editVarintValues.getVarientValuesId());
				varientvalues.setVarientId(editVarintValues.getVarientId());
				varientvalues.setName(editVarintValues.getName());
				varientvalues.setPrice(editVarintValues.getPrice());
				List<VarientValues> ProductVarientsList = new ArrayList();
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

	@DeleteMapping("/deleteVarientValue/{id}")
	public ResponseEntity<ProductResponse> deleteVarientValue(@PathVariable(value = "id") String varientValueId) {
		ProductResponse productResponse = new ProductResponse();
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
