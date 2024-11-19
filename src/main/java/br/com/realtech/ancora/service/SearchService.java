package br.com.realtech.ancora.service;

import br.com.realtech.ancora.model.PageResult;
import br.com.realtech.ancora.repository.SearchRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class SearchService {
    private final SearchRepository searchRepository;
    private final ObjectMapper objectMapper;

    private String licensePlate = "";
    private String manufacturer = "";
    private String product = "";
    private final int page = 0;
    private final int itemsPerPage = 100;

    @Autowired
    public SearchService(SearchRepository searchRepository, ObjectMapper objectMapper) {
        this.searchRepository = searchRepository;
        this.objectMapper = objectMapper;
    }

    public void setFilters(String licensePlate, String manufacturer, String product) {
        this.licensePlate = licensePlate;
        this.manufacturer = manufacturer;
        this.product = product;
    }

    private String buildSearchBody() {
        return """
            {
                "veiculoFiltro": {
                    "veiculoPlaca": "%s"
                },
                "produtoFiltro": {
                    "nomeFabricante": "%s"
                },
                "superbusca": "%s",
                "pagina": %d,
                "itensPorPagina": %d
            }
        """.formatted(this.licensePlate, this.manufacturer.toUpperCase(), this.product, this.page, this.itemsPerPage);
    }

    public PageResult search() throws IOException, InterruptedException {
        String requestBody = buildSearchBody();
        String responseBody = searchRepository.search(requestBody);

        Map<String, Object> responseMap = objectMapper.readValue(responseBody, Map.class);
        return objectMapper.convertValue(responseMap.get("pageResult"), PageResult.class);
    }
}
