package com.javafx.farmdashboard.helpers;

import com.javafx.farmdashboard.model.ItemI;

public class TotalMarketValueVisitor implements VisitorI{

    @Override
    public double visit(ItemI item) {
        double marketValue = item.getMarketValue();

        for(ItemI child: item.getChildren()) {
            marketValue += this.visit(child);
        }

        return marketValue;
    }
}
