package com.javafx.farmdashboard.helpers;

import com.javafx.farmdashboard.model.ItemI;

public class PurchasePriceVisitor implements VisitorI {

    @Override
    public double visit(ItemI item) {
        double price = item.getPrice();

        for(ItemI child: item.getChildren()) {
            price += this.visit(child);
        }

        //item.setPrice(price);
        return price;
    }
}
