package productCrudApp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

import productCrudApp.DAO.productDAO;
import productCrudApp.model.Product;

@Controller
public class MainController {
	
	@Autowired
	private productDAO productdao;

	@RequestMapping("/")
	public String home(Model m) {
		List<Product> list = productdao.getProducts();
		m.addAttribute("list",list);
		return "index";
	}
	
	@RequestMapping("/add-product")
	public String addProduct(Model m) {
		m.addAttribute("title","Add Product");
		return "add_product_form";
	}
	
	@RequestMapping(value = "/handle-product", method = RequestMethod.POST)
	public RedirectView handleProduct(@ModelAttribute Product product, HttpServletRequest request) {
		System.out.println(product);
		productdao.createProduct(product);
		RedirectView redirectView = new RedirectView();
		redirectView.setUrl(request.getContextPath() + "/");
		return redirectView;
	}
	
	//delete
	@RequestMapping(value = "/delete-product/{productID}")
	public RedirectView deleteProduct(@PathVariable("productID") int productID, HttpServletRequest request) {
		this.productdao.deleteProduct(productID);
		RedirectView redirectView = new RedirectView();
		redirectView.setUrl(request.getContextPath() + "/");
		return redirectView;
}
	
	@RequestMapping(value = "/update-product/{productID}")
	public String updateProduct(@PathVariable("productID") int productID, Model model) {
		Product product = this.productdao.getProduct(productID);
		model.addAttribute("product",product);
		return "update-Form";
	}
}
