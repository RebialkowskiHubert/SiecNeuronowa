package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class SampleController
{
	static String wsp, bl, i;
	
	@FXML
	TextField twsp;
	@FXML
	TextField tb;
	@FXML
	TextField ti;
	@FXML
	TextField tbi;
	@FXML
	TextField tbb;
	@FXML
	TextArea td;
	@FXML
	Button b;
	
	@FXML
	TableView<Statystyka> tab=new TableView<Statystyka>();
	@FXML
	TableColumn<Statystyka, String> a;
	@FXML
	TableColumn<Statystyka, String> c;
	
	static ObservableList<Statystyka> ol=FXCollections.observableArrayList();
	
	@FXML
	void initialize()
	{
		a.setCellValueFactory(cellData -> cellData.getValue().iter);
		c.setCellValueFactory(cellData -> cellData.getValue().blad);
	}
	
	@FXML
	public void wykonaj()
	{
		wsp=twsp.getText();
		bl=tb.getText();
		i=ti.getText();
		
		Rozpoznanie r=new Rozpoznanie();
		td.setText(r.img);
		String it=String.valueOf(r.it);
		tbi.setText(it);
		String bc=String.valueOf(r.bc);
		tbb.setText(bc);
		tab.setItems(ol);
	}
}
