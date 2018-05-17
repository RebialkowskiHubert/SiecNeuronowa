package application;

import javafx.beans.property.SimpleStringProperty;

public class Statystyka
{
	SimpleStringProperty iter;
	SimpleStringProperty blad;
	
	Statystyka(String iter, String blad)
	{
		this.iter=new SimpleStringProperty(iter);
		this.blad=new SimpleStringProperty(blad);
	}
}