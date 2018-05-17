package application;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.core.events.LearningEvent;
import org.neuroph.core.events.LearningEventListener;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.BackPropagation;


//klasa rozpoznająca cyfry z obrazka
public class Rozpoznanie
{	
	int it;
	double bc;
	String img;
    public Rozpoznanie()
    {
    	double wsp=Double.parseDouble(SampleController.wsp);
    	double b=Double.parseDouble(SampleController.bl);
    	int i=Integer.parseInt(SampleController.i);
    	
    	//stworzenie zestawu szkoleniowego z cyfr
        DataSet ds = naucz();

        //zmienna określająca ile znaków buduje jedną cyfrę (iloczyn wierszy i kolumn tu 7 i 5)
        int we = Dane.wiersze * Dane.kolumny;
        
        //zmienna określająca możliwą ilość cyfr (tu 10, od 0 do 9)
        int wy = Dane.obrazki.length;
        
        //zmienna określająca liczbę ukrytych neuronów
        int ukryty = 19;
        
        //tworzenie sieci neuronowej
        MultiLayerPerceptron mp = new MultiLayerPerceptron(we, ukryty, wy);
        
        //utworzenie wstecznej propagacji błędów z sieci neuronowej
        BackPropagation bp = mp.getLearningRule();

        //ustawienie współczynnika uczenia na 0.5
        bp.setLearningRate(wsp);
        
        //ustawienie maksymalnego błędu na 0.001
        bp.setMaxError(b);
        
        //ustawienie maksymalnej iteracji na 5000
        bp.setMaxIterations(i);

        //dodanie learning listener w celu uzyskania informacji szkoleniowych
        bp.addListener(new LearningEventListener()
        {
            @Override
            public void handleLearningEvent(LearningEvent e)
            {
            	//utworzenie wstecznej propagacji błędów
                BackPropagation bp1 = (BackPropagation) e.getSource();

                //jeśli iteracje obliczeń zakończyły się, pokaż dane statystycze,
                //w przeciwnym wypadku pokaż numer iteracji i błąd
                if (e.getEventType().equals(LearningEvent.Type.LEARNING_STOPPED))
                {
                    it=bp1.getCurrentIteration();
                    bc=bp1.getTotalNetworkError();
                }
                else
                {
                    String iter=String.valueOf(bp1.getCurrentIteration());
                    String blad=String.valueOf(bp1.getTotalNetworkError());
                    Statystyka s=new Statystyka(iter, blad);
                    SampleController.ol.add(s);
                }
            }
        });

        //wywołanie metody uczącej sieć neuronową
        mp.learn(ds);

        //wywołanie metody, która uczy sieć według zestawu szkoleniowego
        testuj(mp, ds);

    }
    
    //metoda, która testuje podaną sieć według podanego zestawu szkoleniowego
    public void testuj(NeuralNetwork<?> nn, DataSet ds2) 
    {
        for (DataSetRow dsr : ds2.getRows())
        {   	
        	//ustawienie wejścia neuronu
            nn.setInput(dsr.getInput());
            
            //wywołanie obliczeń
            nn.calculate();

            //zmienna przechowująca cyfry
            int out = maxWy(nn.getOutput());

            //zmienna przechowująca "obrazek" złożony z 0
            String[] obr=Dane.zCyfryNaObrazek(dsr.getInput());

            for (int i=0; i<obr.length; i++)
            {
                if (i!=obr.length - 1)
                {
                    //System.out.println(obr[i]);
                	if(img!=null)
                		img=img+"\n"+obr[i];
                	else
                		img=obr[i];
                }
                else
                {
                	img=img+"\n"+obr[i]+"\nObrazek cyfry "+out+"\n";
                }
            }
            System.out.println("");
        }
    }

    //metoda, która uczy sieć neuronową
    public DataSet naucz()
    {
    	//ustawienie zestawu szkoleniowego
        DataSet ds3 = new DataSet(Dane.kolumny * Dane.wiersze, Dane.obrazki.length);

        for (int i=0; i<Dane.obrazki.length; i++)
        {
            // ustawienie wejścia
            DataSetRow wewiersz=Dane.zObrazkaNaCyfre(Dane.obrazki[i]);
            double[] we=wewiersz.getInput();

            // ustawienie wyjścia
            double[] wy = new double[Dane.obrazki.length];

            for (int j=0; j<Dane.obrazki.length; j++)
            {
                if (j==i)
                    wy[j]=1;
                else
                    wy[j]=0;
            }
            
            //stworzenie nowego elementu z ustalonym wejściem i wyjściem
            DataSetRow wiersz = new DataSetRow(we, wy);
            
            //dodanie wiersza do zestawu szkoleniowego
            ds3.addRow(wiersz);
        }
        return ds3;
    }

    //ustalenie maksymalnego wyjścia
    public int maxWy(double[] tablica)
    {
        double max=tablica[0];
        int index = 0;

        for (int i=0; i<tablica.length; i++)
        {
            if (tablica[i]>max)
            {
                index=i;
                max=tablica[i];
            }
        }
        return index;
    }
}
