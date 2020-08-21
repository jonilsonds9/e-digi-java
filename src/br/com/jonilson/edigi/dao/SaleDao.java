package br.com.jonilson.edigi.dao;

import br.com.jonilson.edigi.model.Sale;

import java.util.HashSet;
import java.util.Set;

public class SaleDao {

    private Set<Sale> sales;

    public SaleDao() {
        this.sales = new HashSet<>();
    }

    public void add(Sale sale) {
        this.sales.add(sale);

        System.out.println("Venda realizada com sucesso! \nDetalhes da venda:");
        System.out.println(sale.infoSaleToString());
    }
}
