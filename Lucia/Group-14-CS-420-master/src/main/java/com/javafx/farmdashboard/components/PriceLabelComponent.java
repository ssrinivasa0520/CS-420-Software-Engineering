package com.javafx.farmdashboard.components;

import com.javafx.farmdashboard.helpers.PurchasePriceVisitor;
import com.javafx.farmdashboard.helpers.TotalMarketValueVisitor;
import com.javafx.farmdashboard.model.ItemI;
import javafx.scene.control.Label;

public class PriceLabelComponent {

    private Label purchasePriceLabel;

    private Label currentMarketValueLabel;

    private final PurchasePriceVisitor purchasePriceVisitor = new PurchasePriceVisitor();

    private final TotalMarketValueVisitor marketValueVisitor = new TotalMarketValueVisitor();

    public PriceLabelComponent(Label purchasePriceLabel, Label currentMarketValueLabel, ItemViewComponent itemViewComponent) {
        this.purchasePriceLabel = purchasePriceLabel;
        this.currentMarketValueLabel = currentMarketValueLabel;

        this.initialize();
    }

    public void initialize() {
        purchasePriceLabel.setText("-");
        currentMarketValueLabel.setText("-");
    }

    public void refreshPurchasePrice(ItemI item) {
        purchasePriceLabel.setText(String.valueOf(purchasePriceVisitor.visit(item)));
    }

    public void refreshMarketValue(ItemI item) {
        currentMarketValueLabel.setText(String.valueOf(marketValueVisitor.visit(item)));
    }
}
