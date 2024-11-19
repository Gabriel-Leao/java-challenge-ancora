package br.com.realtech.ancora;

import br.com.realtech.ancora.dto.SearchFilters;
import br.com.realtech.ancora.service.SearchService;
import br.com.realtech.ancora.util.CartUtil;
import br.com.realtech.ancora.util.SearchMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class AncoraApplication implements ApplicationRunner {
	private final SearchService searchService;
	private final ConfigurableApplicationContext context;

	@Autowired
	public AncoraApplication(SearchService searchService, ConfigurableApplicationContext context) {
		this.searchService = searchService;
		this.context = context;
	}

	public static void main(String[] args) {
		SpringApplication.run(AncoraApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		SearchMenu searchMenu = new SearchMenu();
		SearchFilters filters = searchMenu.displayMenu();

		searchService.setFilters(filters.licensePlate(), filters.manufacturer(), filters.product());

		CartUtil.displayProductsAndAddToCart(searchService.search());

		System.out.println("fim da aplicação");

		int exitCode = SpringApplication.exit(context, () -> 0);
		System.exit(exitCode);
	}
}
