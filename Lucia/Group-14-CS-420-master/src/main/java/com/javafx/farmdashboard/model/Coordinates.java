package com.javafx.farmdashboard.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coordinates {

    private double X;
    private double Y;
    private double offsetX;
    private double offsetY;

    public Coordinates(double X, double Y) {
        this.X = X;
        this.Y = Y;
    }

    public double getXAfterOffset() {
        return this.X + this.offsetX;
    }

    public double getYAfterOffset() {
        return this.Y + this.offsetY;
    }
}
